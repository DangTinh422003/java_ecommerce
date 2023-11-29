package com.springcommerce.repository;

import com.springcommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

   Category findCategoriesById(String id);

   Category findByName(String name);
}
