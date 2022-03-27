package fpt.com.fresher.recruitmentmanager.controller.api;

import fpt.com.fresher.recruitmentmanager.object.filter.CategoryFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.CategoryMapper;
import fpt.com.fresher.recruitmentmanager.object.request.CategoryRequest;
import fpt.com.fresher.recruitmentmanager.object.response.CategoryResponse;
import fpt.com.fresher.recruitmentmanager.service.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryControllerApi {

    private final CategoryServiceImpl categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    ResponseEntity<?> getAllCategories() {
        List<CategoryResponse> responses = categoryService.findAllCategories().stream()
                .map(categoryMapper::entityToCategoryResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    ResponseEntity<?> getAllCategories(@RequestBody Optional<CategoryFilter> filter) {
        Page<CategoryResponse> responses = categoryService.findAllCategories(filter.orElse(new CategoryFilter()))
                .map(categoryMapper::entityToCategoryResponse);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        CategoryResponse response =
                categoryMapper.entityToCategoryResponse(categoryService.findCategoryById(id));
        return ResponseEntity.ok(response);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    ResponseEntity<?> createCategory(@ModelAttribute @Valid CategoryRequest requestBody) {
        System.err.println("Category: " + requestBody);
        CategoryResponse response =
                categoryMapper.entityToCategoryResponse(categoryService.createCategory(requestBody));
        return ResponseEntity.ok(response);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    ResponseEntity<?> updateCategory(@PathVariable Long id, @ModelAttribute @Valid CategoryRequest requestBody) {
        CategoryResponse response =
                categoryMapper.entityToCategoryResponse(categoryService.updateCategory(id, requestBody));
        return ResponseEntity.ok(response);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

}
