package com.springcommerce.repository;

import com.springcommerce.entity.Cart;
import com.springcommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findByUser(User user);

    Cart findByUserId(String id);
}
