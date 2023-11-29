package com.springcommerce.repository;

import com.springcommerce.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    @Query(value = "SELECT * FROM order_detail WHERE order_id = ?1", nativeQuery = true)
    List<OrderDetail> findByOrderId(String order_id);

    OrderDetail findByOrdersIdAndProductsId(String order_id, String product_id);
}
