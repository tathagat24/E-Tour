package com.etour.tour_service_api.resource;

import com.etour.tour_service_api.dto.TourCategoryDto;
import com.etour.tour_service_api.enumeration.TourCategory;
import com.etour.tour_service_api.payload.response.Response;
import com.etour.tour_service_api.service.TourCategoryService;
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

import static com.etour.tour_service_api.constant.ApiConstant.TOUR_CATEGORY_IMAGE_FILE_STORAGE;
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
@RequestMapping(path = { "/tour-category" })
@RequiredArgsConstructor
public class TourCategoryResource {
    private final TourCategoryService tourCategoryService;

    @PostMapping(path = "/create")
    public ResponseEntity<Response> createTourCategory(
            @RequestParam(value = "categoryName") TourCategory categoryName,
            @RequestParam(value = "imageFile") MultipartFile imageFile,
            HttpServletRequest request
    ) {
        tourCategoryService.createTourCategory(categoryName, imageFile);
        return ResponseEntity.created(getUri()).body(getResponse(request, emptyMap(), "Tour Category created successfully", CREATED));
    }

    @GetMapping(path = "/list")
    public ResponseEntity<Response> getAllTourCategories(HttpServletRequest request) {
        List<TourCategoryDto> tourCategories = tourCategoryService.getAllTourCategories();
        return ResponseEntity.ok().body(getResponse(request, of("tourCategories", tourCategories), "Tour categories retrieved", OK));
    }

    @GetMapping
    public ResponseEntity<Response> getTourCategoryByCategoryName(@RequestParam(value = "name") TourCategory tourCategory, HttpServletRequest request) {
        TourCategoryDto tourCategoryDto = tourCategoryService.getTourCategoryByCategoryName(tourCategory);
        return ResponseEntity.ok().body(getResponse(request, of("tourCategory", tourCategoryDto), "Tour category retrieved", OK));
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<Response> updateTourCategory(
            @PathVariable(value = "id") Long categoryId,
            @RequestParam(value = "categoryName") TourCategory categoryName,
            @RequestParam(value = "imageFile") MultipartFile imageFile,
            HttpServletRequest request
    ) {
        TourCategoryDto tourCategoryDto = tourCategoryService.updateTourCategory(categoryId, categoryName, imageFile);
        return ResponseEntity.ok().body(getResponse(request, of("tourCategory", tourCategoryDto), "Tour category updated successfully", OK));
    }

    @GetMapping(path = "/image/{fileName}", produces = { IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE })
    public byte[] getPhoto(@PathVariable(value = "fileName") String fileName ) throws IOException {
        return Files.readAllBytes(Paths.get(TOUR_CATEGORY_IMAGE_FILE_STORAGE + fileName));
    }

    private URI getUri() {
        return URI.create("");
    }

}
