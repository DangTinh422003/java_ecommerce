package com.springcommerce.controller.api;

import com.springcommerce.dto.request.DefaultRequest;
import com.springcommerce.dto.request.ProductRequest;
import com.springcommerce.dto.response.MessageResponse;
import com.springcommerce.dto.response.ProductResponse;
import com.springcommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ApiProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.addProduct(request));
    }

    @PostMapping("/get_product")
    public ResponseEntity<ProductResponse> getProductById(@RequestBody DefaultRequest request) {
        return ResponseEntity.ok(productService.getProductById(request));
    }

    @PostMapping("/get_product/{name}")
    public ResponseEntity<ProductResponse> getProductByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(productService.getProductByName(name));
    }


    @PostMapping("/get_all_products")
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

    @GetMapping("/filter_product")
    public ResponseEntity<List<ProductResponse>> filterProduct(
            @RequestParam(name = "filter", value = "filter", defaultValue = "", required = false) String filter,
            @RequestParam(name = "minPrice", value = "minPrice", defaultValue = "0", required = false) int minPrice,
            @RequestParam(name = "maxPrice", value = "maxPrice", defaultValue = "0", required = false) int maxPrice,
            @RequestParam(name = "category", value = "category", defaultValue = "", required = false) String category
            ) {
            if (filter.isEmpty() && minPrice == 0 && maxPrice == 0 && category.isEmpty()) {
                return ResponseEntity.ok(productService.getAllProducts(0, 10));
            } else if (filter.isEmpty() && minPrice == 0 && maxPrice == 0) {
                return ResponseEntity.ok(productService.filterProductByCategory(category));
            } else {
                return ResponseEntity.ok(productService.filterProduct(filter, minPrice, maxPrice));
            }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> deleteProduct(
            @PathVariable("id") String id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest request, @PathVariable("id") String id) {
        return ResponseEntity.ok(productService.updateProduct(request, id));
    }

    @DeleteMapping("/delete_all")
    public ResponseEntity<MessageResponse> deleteAllProduct() {
        return ResponseEntity.ok(productService.deleteAllProduct());
    }

    @PutMapping("/update_quantity/{id}")
    public ResponseEntity<ProductResponse> updateQuantity(@PathVariable("id") String id, @RequestBody String quantity) {
        return ResponseEntity.ok(productService.updateProductQuantity(id, quantity));
    }

    @PutMapping("/update_quantity_again/{id}")
    public ResponseEntity<ProductResponse> updateQuantityAgain(@PathVariable("id") String id, @RequestBody String quantity) {
        return ResponseEntity.ok(productService.addQuantity(id, quantity));
    }
}
