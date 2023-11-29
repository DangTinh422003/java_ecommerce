package com.springcommerce.controller.api;

import com.springcommerce.dto.request.CategoryRequest;
import com.springcommerce.dto.request.DefaultRequest;
import com.springcommerce.dto.response.CategoryResponse;
import com.springcommerce.dto.response.MessageResponse;
import com.springcommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class ApiCategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody CategoryRequest request){
        return ResponseEntity.ok(categoryService.addCategory(request));
    }

    @PostMapping("/get_all_categories")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){

        return ResponseEntity.ok(categoryService.getAllCategories(page, size));
    }


    @PostMapping("/get_category_by_product_id")
    public ResponseEntity<CategoryResponse> getCategoryByProductId(@RequestBody String id){
        return ResponseEntity.ok(categoryService.getCategoryByProductId(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessageResponse> updateCategory(@RequestBody CategoryRequest request, @PathVariable("id") String id){
        return ResponseEntity.ok(categoryService.updateCategory(request, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> deleteCategory(
            @PathVariable("id") String id){
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}
