package com.Blog.Blog.controllers;

import com.Blog.Blog.payload.ApiResponse;
import com.Blog.Blog.payload.CategoryDto;
import com.Blog.Blog.services.CategoryService;
//import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Create Category
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(
          @Valid @RequestBody CategoryDto categoryDto) {

        CategoryDto createCategory =
                this.categoryService.createCategory(categoryDto);

        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }

    // Update Category
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(
           @Valid @RequestBody CategoryDto categoryDto,
            @PathVariable Integer catId) {

        CategoryDto updatedCategory =
                this.categoryService.updateCategory(categoryDto, catId);

        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    // Delete Category
    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(
            @PathVariable Integer catId) {

        this.categoryService.deleteCategory(catId);

        return new ResponseEntity<>(
                new ApiResponse(
                        "Category deleted successfully !!",
                        true),
                HttpStatus.OK);
    }

    // Get Single Category
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategory(
            @PathVariable Integer catId) {

        CategoryDto categoryDto =
                this.categoryService.getCategory(catId);

        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    // Get All Categories
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories() {

        List<CategoryDto> categories =
                this.categoryService.getCategories();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}