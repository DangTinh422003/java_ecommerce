package com.springcommerce.service;

import com.springcommerce.dto.request.CategoryRequest;
import com.springcommerce.dto.request.DefaultRequest;
import com.springcommerce.dto.response.CategoryResponse;
import com.springcommerce.dto.response.MessageResponse;
import com.springcommerce.dto.response.ProductResponse;
import com.springcommerce.entity.Category;
import com.springcommerce.entity.Product;
import com.springcommerce.repository.CategoryRepository;
import com.springcommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    public CategoryResponse convertCategory(Category category, List<ProductResponse> products) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .products(products)
                .date_created(category.getDate_created())
                .date_updated(category.getDate_updated())
                .build();
    }

    public CategoryResponse convertCategory(Category category) {
        List<ProductResponse> productResponses = category.getProducts().stream().map(product -> {
            return productService.convertProduct(product);
        }).toList();
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .products(productResponses)
                .date_created(category.getDate_created())
                .date_updated(category.getDate_updated())
                .build();
    }

    public CategoryResponse addCategory(CategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .date_created(Date.from(java.time.Instant.now()))
                .build();
        categoryRepository.save(category);
        return convertCategory(category, new ArrayList<ProductResponse>());
    }

    public List<CategoryResponse> getAllCategories(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size).withSort(Sort.by("name").ascending());
        List<CategoryResponse> categories = categoryRepository.findAll(pageRequest).stream().map(category -> {
            List<ProductResponse> productResponses = new ArrayList<ProductResponse>();
            for (Product p : category.getProducts()) {
                ProductResponse productResponse = productService.convertProduct(p);
                productResponses.add(productResponse);
            }
            return convertCategory(category, productResponses);
        }).toList();
        return categories;
    }

    public CategoryResponse getCategoryByProductId(String id) {
        var product = productRepository.findById(id).orElseThrow();
        var category = categoryRepository.findCategoriesById(product.getCategory().getId());
        return convertCategory(category);
    }

    public MessageResponse updateCategory(CategoryRequest request, String id) {
        var category = categoryRepository.findCategoriesById(id);
        category.setName(request.getName());
        category.setDate_updated(Date.from(java.time.Instant.now()));
        categoryRepository.save(category);
        return MessageResponse.builder()
                .message("Update category successfully")
                .build();
    }

    public List<CategoryResponse> deleteCategoryById(String id, int page, int size) {
        Category category = categoryRepository.findById(id).orElseThrow();
//        productService.deleteProductOfCategory(category);
        categoryRepository.delete(category);
        return getAllCategories(page, size);
    }

    public MessageResponse deleteCategory(String id) {
        var category = categoryRepository.findCategoriesById(id);
        if (category == null) {
            return MessageResponse.builder()
                    .message("Category not found")
                    .build();
        }
        productService.deleteProductOfCategory(category);
        categoryRepository.delete(category);
        return MessageResponse.builder()
                .message("Delete category successfully")
                .build();
    }

}
