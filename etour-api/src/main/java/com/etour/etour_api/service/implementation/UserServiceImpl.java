package com.etour.etour_api.service.implementation;

import com.etour.etour_api.cache.CacheStore;
import com.etour.etour_api.dto.User;
import com.etour.etour_api.entity.AddressEntity;
import com.etour.etour_api.entity.ConfirmationEntity;
import com.etour.etour_api.entity.UserEntity;
import com.etour.etour_api.enumeration.LoginType;
import com.etour.etour_api.enumeration.Role;
import com.etour.etour_api.event.UserEvent;
import com.etour.etour_api.exception.ApiException;
import com.etour.etour_api.repository.AddressRepository;
import com.etour.etour_api.repository.ConfirmationRepository;
import com.etour.etour_api.repository.UserRepository;
import com.etour.etour_api.service.UserService;
import com.etour.etour_api.utils.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.BiFunction;

import static com.etour.etour_api.constant.ApiConstant.USER_IMAGE_FILE_STORAGE;
import static com.etour.etour_api.enumeration.EventType.REGISTRATION;
import static com.etour.etour_api.enumeration.EventType.RESETPASSWORD;
import static com.etour.etour_api.enumeration.Role.ROLE_USER;
import static com.etour.etour_api.utils.UserUtils.createUserEntity;
import static com.etour.etour_api.utils.UserUtils.fromUserEntity;
import static com.etour.etour_api.validation.UserValidation.verifyAccountStatus;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * @version 1.0
 * @project etour-api
 * @since 27-01-2025
 */

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final AddressRepository addressRepository;
    private final ApplicationEventPublisher publisher;
    private final CacheStore<String, Integer> userCache;
    private final ConfirmationRepository confirmationRepository;

    @Override
    public void createUser(String firstName, String middleName, String lastName, String email, String password) {
        if (userRepository.existsByEmailIgnoreCase(email)) { throw new ApiException("Email already exist. Please use different email."); }
        UserEntity userEntity = createUserEntity(firstName, middleName, lastName, email, ROLE_USER);
        userEntity.setPassword(encoder.encode(password));
        UserEntity savedUserEntity = userRepository.save(userEntity);
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine(EMPTY);
        addressEntity.setCity(EMPTY);
        addressEntity.setState(EMPTY);
        addressEntity.setCountry(EMPTY);
        addressEntity.setZipCode(EMPTY);
        addressEntity.setUserEntity(savedUserEntity);
        addressRepository.save(addressEntity);
        ConfirmationEntity confirmationEntity = new ConfirmationEntity(userEntity);
        confirmationRepository.save(confirmationEntity);
        publisher.publishEvent(new UserEvent(savedUserEntity, REGISTRATION, of("key", confirmationEntity.getKey())));
    }

    @Override
    public void verifyAccountKey(String key) {
        ConfirmationEntity confirmationEntity = getUserConfirmation(key);
        UserEntity userEntity = getUserEntityByEmail(confirmationEntity.getUserEntity().getEmail());
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
        confirmationRepository.delete(confirmationEntity);
    }

    @Override
    public void updateLoginAttempt(String email, LoginType loginType) {
        UserEntity userEntity = getUserEntityByEmail(email);
        switch (loginType) {
            case LOGIN_ATTEMPT -> {
                if (userCache.get(userEntity.getEmail()) == null) {
                    userEntity.setLoginAttempts(0);
                    userEntity.setAccountNonLocked(true);
                }
                userEntity.setLoginAttempts(userEntity.getLoginAttempts() + 1);
                userCache.put(userEntity.getEmail(), userEntity.getLoginAttempts());
                if (userCache.get(userEntity.getEmail()) > 5) {
                    userEntity.setAccountNonLocked(false);
                }
            }
            case LOGIN_SUCCESS -> {
                userEntity.setAccountNonLocked(true);
                userEntity.setLoginAttempts(0);
                userEntity.setLastLogin(now());
                userCache.evict(userEntity.getEmail());
            }
        }
        userRepository.save(userEntity);
    }

    @Override
    public User getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findUserByUserId(userId).orElseThrow(() -> new ApiException("User not found"));
        return fromUserEntity(userEntity);
    }

    @Override
    public User getUserByEmail(String email) {
        return fromUserEntity(getUserEntityByEmail(email));
    }

    @Override
    public String getUserCredentialById(Long id) {
        UserEntity userEntity = getUserEntityById(id);
        return userEntity.getPassword();
    }

    @Override
    public void resetPassword(String email) {
        UserEntity userEntity = getUserEntityByEmail(email);
        ConfirmationEntity confirmationEntity = getUserConfirmation(userEntity);
        if (confirmationEntity != null) {
            publisher.publishEvent(new UserEvent(userEntity, RESETPASSWORD, of("key", confirmationEntity.getKey())));
        } else {
            confirmationEntity = new ConfirmationEntity(userEntity);
            confirmationRepository.save(confirmationEntity);
            publisher.publishEvent(new UserEvent(userEntity, RESETPASSWORD, of("key", confirmationEntity.getKey())));
        }
    }

    @Override
    public User verifyPasswordKey(String key) {
        ConfirmationEntity confirmationEntity = getUserConfirmation(key);
        UserEntity userEntity = getUserEntityByEmail(confirmationEntity.getUserEntity().getEmail());
        verifyAccountStatus(userEntity);
        confirmationRepository.delete(confirmationEntity);
        return fromUserEntity(userEntity);
    }

    @Override
    public void updatePassword(String userId, String newPassword, String confirmNewPassword) {
        if (!newPassword.equals(confirmNewPassword)) { throw new ApiException("Password and confirm password don't match. Please try again."); }
        UserEntity userEntity = getUserEntityByUserId(userId);
        userEntity.setPassword(encoder.encode(newPassword));
    }

    @Override
    public User updateUser(String userId, String firstName, String middleName, String lastName, String email, String phone, String bio, String addressLine, String city, String state, String country, String zipCode) {
        UserEntity userEntity = getUserEntityByUserId(userId);
        userEntity.setFirstName(firstName);
        userEntity.setMiddleName(middleName);
        userEntity.setLastName(lastName);
        userEntity.setEmail(email);
        userEntity.setPhone(phone);
        userEntity.setBio(bio);
        AddressEntity addressEntity = userEntity.getAddressEntity();
        addressEntity.setAddressLine(addressLine);
        addressEntity.setCity(city);
        addressEntity.setState(state);
        addressEntity.setCountry(country);
        addressEntity.setZipCode(zipCode);
        addressEntity.setUserEntity(userEntity);
        addressRepository.save(addressEntity);
        return fromUserEntity(userRepository.save(userEntity));
    }

    @Override
    public void updateRole(String userId, Role role) {
        UserEntity userEntity = getUserEntityByUserId(userId);
        userEntity.setRole(role);
        userRepository.save(userEntity);
    }

    @Override
    public void toggleAccountExpired(String userId) {
        UserEntity userEntity = getUserEntityByUserId(userId);
        userEntity.setAccountNonExpired(!userEntity.isAccountNonExpired());
        userRepository.save(userEntity);
    }

    @Override
    public void toggleAccountLocked(String userId) {
        UserEntity userEntity = getUserEntityByUserId(userId);
        userEntity.setAccountNonLocked(!userEntity.isAccountNonLocked());
        userRepository.save(userEntity);
    }

    @Override
    public void toggleAccountEnabled(String userId) {
        UserEntity userEntity = getUserEntityByUserId(userId);
        userEntity.setEnabled(!userEntity.isEnabled());
        userRepository.save(userEntity);
    }

    @Override
    public void updatePassword(String userId, String password, String newPassword, String confirmNewPassword) {
        if (!newPassword.equals(confirmNewPassword)) { throw new ApiException("Password and confirm password don't match. Please try again."); }
        UserEntity userEntity = getUserEntityByUserId(userId);
        verifyAccountStatus(userEntity);
        if (!encoder.matches(password, userEntity.getPassword())) { throw new ApiException("Existing passwords is incorrect. Please try again."); }
        userEntity.setPassword(encoder.encode(newPassword));
        userRepository.save(userEntity);
    }

    @Override
    public String uploadPhoto(String userId, MultipartFile file) {
        UserEntity userEntity = getUserEntityByUserId(userId);
        String photoUrl = photoFunction.apply(userId, file);
        userEntity.setImageUrl(photoUrl + "?timestamp=" + System.currentTimeMillis());
        userRepository.save(userEntity);
        return photoUrl;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll().stream().map(UserUtils::fromUserEntity).toList();
    }

    @Override
    public User getUserById(Long id) {
        return fromUserEntity(getUserEntityById(id));
    }

    private final BiFunction<String, MultipartFile, String> photoFunction = (userId, file) -> {
        String fileName = userId + ".png";
        try {
            Path fileStorageLocation = Paths.get(USER_IMAGE_FILE_STORAGE).toAbsolutePath().normalize();
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }
            Files.copy(file.getInputStream(), fileStorageLocation.resolve(fileName), REPLACE_EXISTING);
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/user/image/" + fileName)
                    .toUriString();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("Unable to save image");
        }
    };

    private UserEntity getUserEntityByUserId(String userId) {
        return userRepository.findUserByUserId(userId).orElseThrow(() -> new ApiException("User not found"));
    }

    private UserEntity getUserEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ApiException("User not found"));
    }

    private UserEntity getUserEntityByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new ApiException("User not found"));
    }

    private ConfirmationEntity getUserConfirmation(String key) {
        return confirmationRepository.findByKey(key).orElseThrow(() -> new ApiException("Confirmation key not found"));
    }

    private ConfirmationEntity getUserConfirmation(UserEntity userEntity) {
        return confirmationRepository.findByUserEntity(userEntity).orElse(null);
    }

}
