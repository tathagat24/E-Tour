package com.etour.tour_service_api.service.implementation;

import com.etour.tour_service_api.dto.TourSubcategoryDto;
import com.etour.tour_service_api.entity.TourCategoryEntity;
import com.etour.tour_service_api.entity.TourSubcategoryEntity;
import com.etour.tour_service_api.enumeration.TourCategory;
import com.etour.tour_service_api.exception.ApiException;
import com.etour.tour_service_api.repository.TourCategoryRepository;
import com.etour.tour_service_api.repository.TourSubCategoryRepository;
import com.etour.tour_service_api.service.TourSubCategoryService;
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

import static com.etour.tour_service_api.constant.ApiConstant.TOUR_SUBCATEGORY_IMAGE_FILE_STORAGE;
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
public class TourSubCategoryServiceImpl implements TourSubCategoryService {
    private final TourSubCategoryRepository tourSubCategoryRepository;
    private final TourCategoryRepository tourCategoryRepository;

    @Override
    public void createTourSubCategory(TourCategory categoryName, String subCategoryName, MultipartFile imageFile) {
        TourCategoryEntity tourCategoryEntity = tourCategoryRepository.findByCategoryName(categoryName).orElseThrow(() -> new ApiException("Tour category not found"));
        TourSubcategoryEntity tourSubcategoryEntity = new TourSubcategoryEntity();
        tourSubcategoryEntity.setSubCategoryName(subCategoryName);
        tourSubcategoryEntity.setImageUrl(photoFunction.apply(tourSubcategoryEntity.getReferenceId(), imageFile));
        tourSubcategoryEntity.setTourCategoryEntity(tourCategoryEntity);
        tourSubCategoryRepository.save(tourSubcategoryEntity);
    }

    @Override
    public List<TourSubcategoryDto> getAllTourSubcategories() {
        return tourSubCategoryRepository.findAll().stream().map(TourUtils::fromTourSubcategoryEntity).toList();
    }

    @Override
    public List<TourSubcategoryDto> getAllTourSubcategoriesByTourCategoryId(Long tourCategoryId) {
        TourCategoryEntity tourCategoryEntity = tourCategoryRepository.findById(tourCategoryId).orElseThrow(() -> new ApiException("Tour category not found"));
        return tourSubCategoryRepository.findAllByTourCategoryEntity(tourCategoryEntity).stream().map(TourUtils::fromTourSubcategoryEntity).toList();
    }

    private final BiFunction<String, MultipartFile, String> photoFunction = (tourSubCategoryId, file) -> {
        String fileName = tourSubCategoryId + ".png";
        try {
            Path fileStorageLocation = Paths.get(TOUR_SUBCATEGORY_IMAGE_FILE_STORAGE).toAbsolutePath().normalize();
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }
            Files.copy(file.getInputStream(), fileStorageLocation.resolve(fileName), REPLACE_EXISTING);
            // /tour-category/image/964d4475-ebd7-1a06-9842-37143e2c2ceb.png?timestamp=1738144361295
            return "http://localhost:8085/api/v1/tour-service/tour-subcategory/image/" + fileName;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("Unable to save image");
        }
    };

}
