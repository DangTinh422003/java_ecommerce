package com.springcommerce.controller.api;

import com.springcommerce.dto.request.OrderRequest;
import com.springcommerce.dto.response.MessageResponse;
import com.springcommerce.dto.response.OrderResponse;
import com.springcommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class ApiOrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<OrderResponse> addOrder(@RequestBody @Valid OrderRequest request){
        return ResponseEntity.ok(orderService.addOrder(request));
    }

    @PostMapping("/get_all")
    public ResponseEntity<List<OrderResponse>> getAllOrder(){
        return ResponseEntity.ok(orderService.getAllOrder());
    }

    @PostMapping("/get_by_id")
    public ResponseEntity<List<OrderResponse>> getOrderById(@RequestBody String id){
        return ResponseEntity.ok(orderService.getAllOrderOfUser(id));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> deleteOrderById(@PathVariable String id){
        return ResponseEntity.ok(orderService.deleteOrderById(id));
    }
}
