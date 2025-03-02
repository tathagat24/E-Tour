package com.etour.tour_service_api.service.implementation;

import com.etour.tour_service_api.dto.TourCategoryDto;
import com.etour.tour_service_api.entity.TourCategoryEntity;
import com.etour.tour_service_api.enumeration.TourCategory;
import com.etour.tour_service_api.exception.ApiException;
import com.etour.tour_service_api.repository.TourCategoryRepository;
import com.etour.tour_service_api.service.TourCategoryService;
import com.etour.tour_service_api.utils.TourUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.BiFunction;

import static com.etour.tour_service_api.constant.ApiConstant.TOUR_CATEGORY_IMAGE_FILE_STORAGE;
import static com.etour.tour_service_api.utils.TourUtils.fromTourCategoryEntity;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 29-01-2025
 */

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class TourCategoryServiceImpl implements TourCategoryService {
    private final TourCategoryRepository tourCategoryRepository;

    @Override
    public void createTourCategory(TourCategory categoryName, MultipartFile imageFile) {
        TourCategoryEntity tourCategoryEntity = new TourCategoryEntity();
        tourCategoryEntity.setCategoryName(categoryName);
        String imageUrl = photoFunction.apply(tourCategoryEntity.getReferenceId(), imageFile);
        tourCategoryEntity.setImageUrl(imageUrl + "?timestamp=" + System.currentTimeMillis());
        tourCategoryRepository.save(tourCategoryEntity);
    }

    @Override
    public List<TourCategoryDto> getAllTourCategories() {
        return tourCategoryRepository.findAll().stream().map(TourUtils::fromTourCategoryEntity).toList();
    }

    @Override
    public TourCategoryDto getTourCategoryByCategoryName(TourCategory tourCategory) {
        return tourCategoryRepository.findByCategoryName(tourCategory).map(TourUtils::fromTourCategoryEntity).orElseThrow(() -> new ApiException("Tour Category not found"));
    }

    @Override
    public TourCategoryDto updateTourCategory(Long categoryId, TourCategory categoryName, MultipartFile imageFile) {
        TourCategoryEntity tourCategoryEntity = getTourCategoryEntityById(categoryId);
        tourCategoryEntity.setCategoryName(categoryName);
        String imageUrl = photoFunction.apply(tourCategoryEntity.getReferenceId(), imageFile);
        tourCategoryEntity.setImageUrl(imageUrl + "?timestamp=" + System.currentTimeMillis());
        return fromTourCategoryEntity(tourCategoryRepository.save(tourCategoryEntity));
    }

    private TourCategoryEntity getTourCategoryEntityById(Long categoryId) {
        return tourCategoryRepository.findById(categoryId).orElseThrow(() -> new ApiException("Tour Category not found"));
    }

    private final BiFunction<String, MultipartFile, String> photoFunction = (tourCategoryId, file) -> {
        String fileName = tourCategoryId + ".png";
        try {
            Path fileStorageLocation = Paths.get(TOUR_CATEGORY_IMAGE_FILE_STORAGE).toAbsolutePath().normalize();
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }
            Files.copy(file.getInputStream(), fileStorageLocation.resolve(fileName), REPLACE_EXISTING);
            // /tour-category/image/964d4475-ebd7-1a06-9842-37143e2c2ceb.png?timestamp=1738144361295
            return "http://localhost:8085/api/v1/tour-service/tour-category/image/" + fileName;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("Unable to save image");
        }
    };
}
