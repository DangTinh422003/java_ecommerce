package com.springcommerce.repository;

import com.springcommerce.entity.Category;
import com.springcommerce.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    Product findByName(String name);

    List<Product> findByCategory(Category category);

    List<Product> findAllByNameContainingIgnoreCase(String name);


    List<Product> findAll(Specification<Product> specification);

    List<Product> findByCategoryName(String name);

}
