package com.springcommerce.controller.api;

import com.springcommerce.dto.request.OrderDetailRequest;
import com.springcommerce.dto.response.MessageResponse;
import com.springcommerce.dto.response.OrderDetailResponse;
import com.springcommerce.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order_detail")
@RequiredArgsConstructor
public class ApiOrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/add")
    public ResponseEntity<OrderDetailResponse> addOrderDetail(@RequestBody OrderDetailRequest request) {
        return ResponseEntity.ok().body(orderDetailService.addOrderDetail(request));
    }

    @PostMapping("/get_all")
    public ResponseEntity<List<OrderDetailResponse>> getAllOrderDetail() {
        return ResponseEntity.ok().body(orderDetailService.getAllOrderDetail());
    }

    @PostMapping("/get_by_order_id")
    public ResponseEntity<List<OrderDetailResponse>> getOrderDetailByOrderId(@RequestBody String order_id) {
        return ResponseEntity.ok().body(orderDetailService.getOrderDetailByOrderId(order_id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<MessageResponse> deleteOrderDetail(@RequestBody OrderDetailRequest request) {
        return ResponseEntity.ok().body(orderDetailService.deleteOrderDetail(request));
    }
}
