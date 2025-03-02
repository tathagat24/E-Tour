package com.etour.tour_service_api.resource;

import com.etour.tour_service_api.dto.TourSubcategoryDto;
import com.etour.tour_service_api.enumeration.TourCategory;
import com.etour.tour_service_api.payload.response.Response;
import com.etour.tour_service_api.service.TourSubCategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.etour.tour_service_api.constant.ApiConstant.TOUR_SUBCATEGORY_IMAGE_FILE_STORAGE;
import static com.etour.tour_service_api.utils.RequestUtils.getResponse;
import static java.util.Collections.emptyMap;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 29-01-2025
 */

@RestController
@RequestMapping(path = { "/tour-subcategory" })
@RequiredArgsConstructor
public class TourSubcategoryResource {
    private final TourSubCategoryService tourSubCategoryService;

    @PostMapping(path = "/create")
    public ResponseEntity<Response> createTourCategory(
            @RequestParam(value = "categoryName") TourCategory categoryName,
            @RequestParam(value = "subCategoryName") String subCategoryName,
            @RequestParam(value = "imageFile") MultipartFile imageFile,
            HttpServletRequest request
    ) {
        tourSubCategoryService.createTourSubCategory(categoryName, subCategoryName, imageFile);
        return ResponseEntity.created(getUri()).body(getResponse(request, emptyMap(), "Tour Sub Category created successfully", CREATED));
    }

    @GetMapping(path = "/list")
    public ResponseEntity<Response> getAllTourCategories(HttpServletRequest request) {
        List<TourSubcategoryDto> tourSubcategories = tourSubCategoryService.getAllTourSubcategories();
        return ResponseEntity.ok().body(getResponse(request, of("tourSubcategories", tourSubcategories), null, OK));
    }

    @GetMapping(path = "/tour-category/{tourCategoryId}")
    public ResponseEntity<Response> getAllTourCategoriesByTourCategoryId(@PathVariable(value = "tourCategoryId") Long tourCategoryId, HttpServletRequest request) {
        List<TourSubcategoryDto> tourSubcategories = tourSubCategoryService.getAllTourSubcategoriesByTourCategoryId(tourCategoryId);
        return ResponseEntity.ok().body(getResponse(request, of("tourSubcategories", tourSubcategories), "Tour Sub categories retrieved", OK));
    }

    @GetMapping(path = "/image/{fileName}", produces = { IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE })
    public byte[] getPhoto(@PathVariable(value = "fileName") String fileName ) throws IOException {
        return Files.readAllBytes(Paths.get(TOUR_SUBCATEGORY_IMAGE_FILE_STORAGE + fileName));
    }

    private URI getUri() {
        return URI.create("");
    }

}
