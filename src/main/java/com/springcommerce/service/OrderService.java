package com.springcommerce.service;

import com.springcommerce.dto.request.OrderRequest;
import com.springcommerce.dto.response.MessageResponse;
import com.springcommerce.dto.response.OrderResponse;
import com.springcommerce.entity.Order;
import com.springcommerce.entity.OrderDetail;
import com.springcommerce.entity.User;
import com.springcommerce.exception.UserNotFoundException;
import com.springcommerce.repository.OrderDetailRepository;
import com.springcommerce.repository.OrderRepository;
import com.springcommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public OrderResponse convertOrder(Order order){
        return OrderResponse.builder()
                .id(order.getId())
                .email(order.getUser().getEmail())
                .phone(order.getPhone())
                .address(order.getAddress())
                .total(order.getTotal())
                .order_detail(order.getOrder_detail().stream().map(orderDetailService::convertOrderDetail).toList())
                .date_created(order.getDate_created())
                .date_updated(order.getDate_updated())
                .build();
    }

    public OrderResponse convertAddOrder(Order order){
        return OrderResponse.builder()
                .id(order.getId())
                .email(order.getUser().getEmail())
                .phone(order.getPhone())
                .address(order.getAddress())
                .total(order.getTotal())
                .date_created(order.getDate_created())
                .date_updated(order.getDate_updated())
                .build();
    }
    public OrderResponse addOrder(OrderRequest request){
        User user = userRepository.findUserById(request.getUser_id());
        if(user != null){
            Order order = Order.builder()
                    .phone(request.getPhone())
                    .address(request.getAddress())
                    .total(request.getTotal())
                    .date_created(Date.from(java.time.Instant.now()))
                    .user(user)
                    .build();
            orderRepository.save(order);
            user.getOrder().add(order);
            userRepository.save(user);
            return convertAddOrder(order);
        } else {
            throw new UserNotFoundException("User not found");
        }

    }
    public List<OrderResponse> getAllOrder(){
        return orderRepository.findAll().stream().map(this::convertOrder).toList();
    }
    public List<OrderResponse> getAllOrderOfUser(String user_id){
        User user = userRepository.findUserById(user_id);
        return orderRepository.findByUser(user).stream().map(this::convertOrder).toList();
    }
    public OrderResponse updateOrder(OrderRequest request, String id){
        Order order = orderRepository.findById(id).orElseThrow();
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setTotal(request.getTotal());
        order.setDate_updated(Date.from(java.time.Instant.now()));
        orderRepository.save(order);
        return convertOrder(order);
    }

    public MessageResponse deleteOrderById(String id){
        Order order = orderRepository.findById(id).orElseThrow();
        var orderDetails = orderDetailRepository.findByOrderId(id);
        if (orderDetails.isEmpty()){
            orderRepository.delete(order);
            return new MessageResponse("Delete order successfully");
        }
        orderDetailRepository.deleteAll();
        orderRepository.delete(order);
        return new MessageResponse("Delete order successfully");
    }
    public void deleteOrderOfUser(User user){
        List<Order> order = orderRepository.findByUser(user);
        orderRepository.deleteAll(order);
    }
    public void save(List<Order> orderModels) {
        orderRepository.saveAll(orderModels);
    }
}
