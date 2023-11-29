package com.springcommerce.service;

import com.springcommerce.dto.response.CartDetailResponse;
import com.springcommerce.dto.response.CartResponse;
import com.springcommerce.entity.Cart;
import com.springcommerce.entity.User;
import com.springcommerce.exception.UserNotFoundException;
import com.springcommerce.repository.CartRepository;
import com.springcommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailService cartDetailService;

    public CartResponse convertCart(Cart cart){
        return CartResponse.builder()
                .id(cart.getId())
                .total(cart.getTotal())
                .date_created(cart.getDate_created())
                .user(cart.getUser().getId())
                .cart_detail(cart.getCart_detail().stream().map(cartDetailService::convertCartDetail).toList())
                .date_created(cart.getDate_created())
                .date_updated(cart.getDate_updated())
                .build();
    }

    public void addCart(Cart request){
        User user = userRepository.findUserById(request.getUser().getId());
        Cart cart = Cart.builder()
                .id(request.getId())
                .total(request.getTotal())
                .date_created(request.getDate_created())
                .user(user)
                .build();
        cartRepository.save(cart);
    }
    public void deleteCartOfUser(User user){
        Cart cart = cartRepository.findByUser(user).orElseThrow();
        cartDetailService.deleteCartDetailOfCart(cart);
        cartRepository.delete(cart);
    }

    public CartResponse getCartOfUser(String id){
        User user = userRepository.findUserById(id);
        if (user != null) {
            Cart cart = cartRepository.findByUser(user).orElseThrow();
            return convertCart(cart);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public List<CartResponse> getAllCarts() {
        var carts = cartRepository.findAll().stream().map(this::convertCart);
        return carts.toList();
    }
}
