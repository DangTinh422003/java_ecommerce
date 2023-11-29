package com.springcommerce.service;

import com.springcommerce.dto.request.DefaultRequest;
import com.springcommerce.dto.request.ProductRequest;
import com.springcommerce.dto.response.MessageResponse;
import com.springcommerce.dto.response.ProductResponse;
import com.springcommerce.entity.Category;
import com.springcommerce.entity.Product;
import com.springcommerce.exception.CategoryExistException;
import com.springcommerce.exception.ProductExistException;
import com.springcommerce.repository.CartDetailRepository;
import com.springcommerce.repository.CategoryRepository;
import com.springcommerce.repository.ProductRepository;
import com.springcommerce.util.ProductSpecification;
import com.springcommerce.util.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CartDetailService cartDetailService;

    @Autowired
    private CartDetailRepository cartDetailRepository;

    public ProductResponse convertProduct(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .brand(product.getBrand())
                .color(product.getColor())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .categoryName(product.getCategory().getName())
                .date_created(product.getDate_created())
                .date_updated(product.getDate_updated())
                .build();
    }

    public ProductResponse addProduct(ProductRequest request) {
        Product pro = productRepository.findByName(request.getName());
        Category cate = categoryRepository.findById(request.getCategory_id()).orElse(null);
        if (pro == null) {
            if (cate != null) {
                Product product = Product.builder()
                        .name(request.getName())
                        .brand(request.getBrand())
                        .color(request.getColor())
                        .price(request.getPrice())
                        .quantity(request.getQuantity())
                        .date_created(Date.from(java.time.Instant.now()))
                        .category(cate)
                        .build();
                productRepository.save(product);
                cate.getProducts().add(product);
                categoryRepository.save(cate);
                return convertProduct(product);
            } else {
                throw new CategoryExistException("Category not found");
            }
        } else {
            throw new ProductExistException("Product already exists");
        }
    }

    public ProductResponse getProductById(DefaultRequest request) {
        Product product = productRepository.findById(request.getId()).orElseThrow();
        return convertProduct(product);
    }

    public List<ProductResponse> filterProductByCategory(String categoryName) {
        var product = productRepository.findByCategoryName(categoryName).stream().map(this::convertProduct);
        return product.toList();
    }

    public List<ProductResponse> filterProduct(String filter, int minPrice, int maxPrice) {
        var fields = Product.class.getDeclaredFields();
        var searchCriteria = new ArrayList<SearchCriteria>();
        for (var field : fields) {
            if (field.getType() == String.class) {
                if (filter == null || filter.isEmpty()) {
                    continue;
                }
                System.out.println(field.getName());
                searchCriteria.add(SearchCriteria.builder().key(field.getName()).operation(":").value(filter).build());
            } else if (field.getType() == Float.class) {
               searchCriteria.add(SearchCriteria.builder().key(field.getName()).operation("><").value(minPrice).secondValue(maxPrice).build());
            }
        }
        Specification<Product> productSpetifications = Specification.where(null);
        for (var criteria : searchCriteria) {
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                productSpetifications = productSpetifications.or(new ProductSpecification(criteria));
            } else if (criteria.getOperation().equalsIgnoreCase("><")) {
                System.out.println(criteria);
                System.out.println("minPrice: " + criteria.getValue() + " maxPrice: " + criteria.getSecondValue());
                productSpetifications = productSpetifications.and(new ProductSpecification(criteria));
            }
        }
        return productRepository.findAll(productSpetifications).stream().map(this::convertProduct).toList();
    }

    public ProductResponse getProductByName(String name) {
        Product product = productRepository.findByName(name);
        return convertProduct(product);
    }

    public List<ProductResponse> getAllProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        var products = productRepository.findAll(pageRequest).stream().map(this::convertProduct);

        return products.toList();
    }

    public ProductResponse updateProduct(ProductRequest request, String id) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setColor(request.getColor());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setDate_updated(Date.from(java.time.Instant.now()));
        productRepository.save(product);
        return convertProduct(product);
    }

    public ProductResponse updateProductQuantity(String id, String quantity){
        Product product = productRepository.findById(id).orElseThrow();
        if (product.getQuantity() < Integer.parseInt(quantity)){
            throw new RuntimeException("Quantity is not enough");
        }
        product.setQuantity(product.getQuantity() - Integer.parseInt(quantity));
        if (product.getQuantity() == 0) {
            throw new RuntimeException("Product is out of stock");
        }
        product.setDate_updated(Date.from(java.time.Instant.now()));
        productRepository.save(product);
        return convertProduct(product);
    }

    public ProductResponse addQuantity(String id, String quantity){
        Product product = productRepository.findById(id).orElseThrow();
        product.setQuantity(product.getQuantity() + Integer.parseInt(String.valueOf(quantity)));
        product.setDate_updated(Date.from(java.time.Instant.now()));
        productRepository.save(product);
        return convertProduct(product);
    }

    public List<ProductResponse> deleteProductById(String id, int page, int size){
        Product product = productRepository.findById(id).orElseThrow();
        cartDetailService.deleteCartDetailOfProduct(product);
        productRepository.delete(product);
        return getAllProducts(page,size);
    }
    public void deleteProductOfCategory(Category cate){
        List<Product> product = productRepository.findByCategory(cate);
        productRepository.deleteAll(product);
        MessageResponse.builder().message("Delete product of category successfully").build();
    }

    public MessageResponse deleteProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow();
        cartDetailService.deleteCartDetailOfProduct(product);
        productRepository.delete(product);
        return MessageResponse.builder().message("Delete product successfully").build();
    }

    public MessageResponse deleteAllProduct(){
        cartDetailRepository.deleteAll();
        productRepository.deleteAll();
        return MessageResponse.builder().message("Delete all product successfully").build();
    }
}
