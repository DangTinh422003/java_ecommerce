package com.springcommerce.controller.api;

import com.springcommerce.dto.response.CartResponse;
import com.springcommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class ApiCartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/get_all_cart")
    public ResponseEntity<List<CartResponse>> getAllCarts(){
        return ResponseEntity.ok(cartService.getAllCarts());
    }

    @PostMapping("/get_cart_of_user")
    public ResponseEntity<CartResponse> getCartById(@RequestBody String id){
        return ResponseEntity.ok(cartService.getCartOfUser(id));
    }
}
