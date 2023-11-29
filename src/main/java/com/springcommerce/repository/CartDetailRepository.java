package com.springcommerce.repository;

import com.springcommerce.entity.Cart;
import com.springcommerce.entity.CartDetail;
import com.springcommerce.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetail, String> {
    List<CartDetail> findByProducts(Product product);

    List<CartDetail> findByCarts(Cart cart);

    @Query(value = "SELECT * FROM cart_detail WHERE cart_id = ?1", nativeQuery = true)
    List<CartDetail> findByCartId(String cart_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cart_detail WHERE cart_id = ?1 AND product_id = ?2", nativeQuery = true)
    void deleteProductByCartId(String cart_id, String product_id);

    // @Transactional
    // @Query(value = "SELECT * FROM cart_detail WHERE cart_id = ?1 AND product_id =
    // ?1", nativeQuery = true)
    // CartDetail findByCartIdAndProductId(String cart_id, String product_id);

    CartDetail findCartDetailByCartsIdAndProductsId(String cart_id, String product_id);
}
