package com.springcommerce.service;

import com.springcommerce.dto.request.OrderDetailRequest;
import com.springcommerce.dto.response.MessageResponse;
import com.springcommerce.dto.response.OrderDetailResponse;
import com.springcommerce.entity.*;
import com.springcommerce.repository.OrderDetailRepository;
import com.springcommerce.repository.OrderRepository;
import com.springcommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public OrderDetailResponse convertOrderDetail(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .product(orderDetail.getProducts().getName())
                .total(orderDetail.getOrders().getTotal())
                .date_created(orderDetail.getDate_created())
                .date_updated(orderDetail.getDate_updated())
                .build();
    }

    public OrderDetailResponse addOrderDetail(OrderDetailRequest request) {
        Order orderDetail = orderRepository.findById(request.getOrder_id()).orElseThrow();
        Product productModel = productRepository.findById(request.getProduct_id()).orElseThrow();
        OrderDetailKey orderDetailKey = OrderDetailKey.builder()
                .orderId(orderDetail.getId())
                .productId(productModel.getId())
                .build();
        OrderDetail orderDetailModel = OrderDetail.builder()
                .id(orderDetailKey)
                .products(productModel)
                .orders(orderDetail)
                .date_created(Date.from(java.time.Instant.now()))
                .date_updated(null)
                .build();
        orderDetailRepository.save(orderDetailModel);
        orderDetail.getOrder_detail().add(orderDetailModel);
        orderDetail.setTotal(getTotalPrice(request.getOrder_id()));
        orderRepository.save(orderDetail);
        return convertOrderDetail(orderDetailModel);
    }

    public int getTotalPrice(String order_id) {
        int totalPrice = 0;
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order_id);
        for (OrderDetail orderDetail : orderDetails) {
            totalPrice += orderDetail.getProducts().getPrice();
        }
        return totalPrice;
    }

    public List<OrderDetailResponse> getAllOrderDetail() {
        return orderDetailRepository.findAll().stream().map(this::convertOrderDetail).toList();
    }

    public List<OrderDetailResponse> getOrderDetailByOrderId(String order_id) {
        return orderDetailRepository.findByOrderId(order_id).stream().map(this::convertOrderDetail).toList();
    }

    public MessageResponse deleteOrderDetail(OrderDetailRequest request) {
           OrderDetail orderDetail = orderDetailRepository.findByOrdersIdAndProductsId(request.getOrder_id(), request.getProduct_id());
            orderDetailRepository.delete(orderDetail);
           return MessageResponse.builder()
                   .message("Delete order detail successfully")
                   .build();
    }
}
