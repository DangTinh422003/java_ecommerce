package com.springcommerce.controller.api;

import com.springcommerce.dto.request.CartDetailRequest;
import com.springcommerce.dto.response.CartDetailResponse;
import com.springcommerce.dto.response.MessageResponse;
import com.springcommerce.service.CartDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart_detail")
@RequiredArgsConstructor
public class ApiCartDetailController {
    @Autowired
    private CartDetailService cartDetailService;

    @PostMapping("/add")
    public ResponseEntity<CartDetailResponse> addCartDetail(@RequestBody CartDetailRequest request){
        return ResponseEntity.ok(cartDetailService.addToCart(request));
    }

    @PostMapping("/get_all_cart_detail")
    public ResponseEntity<List<CartDetailResponse>> getAllCartDetail(){
        return ResponseEntity.ok(cartDetailService.getAllCartDetail());
    }

    @PostMapping("/get_cart_detail_of_cart")
    public ResponseEntity<List<CartDetailResponse>> getCartDetailOfCart(@RequestBody String id){
        return ResponseEntity.ok(cartDetailService.getCartDetailOfCart(id));
    }

    @DeleteMapping("/delete_cart_detail_of_cart")
    public ResponseEntity<MessageResponse> deleteCartDetailOfCart(@RequestBody String id){
        return ResponseEntity.ok(cartDetailService.deleteCartDetailByCartId(id));
    }

    @DeleteMapping("/delete_product_of_cart_detail")
    public ResponseEntity<MessageResponse> deleteProductOfCartDetail(@RequestBody CartDetailRequest request){
        return ResponseEntity.ok(cartDetailService.deleteCartDetailById(request));
    }

}
