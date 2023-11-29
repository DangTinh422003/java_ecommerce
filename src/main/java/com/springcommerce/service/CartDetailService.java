package com.springcommerce.service;

import com.springcommerce.dto.request.CartDetailRequest;
import com.springcommerce.dto.request.DefaultRequest;
import com.springcommerce.dto.response.CartDetailResponse;
import com.springcommerce.dto.response.MessageResponse;
import com.springcommerce.entity.Cart;
import com.springcommerce.entity.CartDetail;
import com.springcommerce.entity.CartDetailKey;
import com.springcommerce.entity.Product;
import com.springcommerce.repository.CartDetailRepository;
import com.springcommerce.repository.CartRepository;
import com.springcommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public CartDetailResponse convertCartDetail(CartDetail cartDetail) {
        return CartDetailResponse.builder()
                .id(cartDetail.getId())
                .product(cartDetail.getProducts().getName())
                .price(cartDetail.getPrice())
                .quantity(cartDetail.getQuantity())
                .date_created(cartDetail.getDate_created())
                .build();
    }

    public CartDetailResponse addToCart(CartDetailRequest request) {
        Cart cartModel = cartRepository.findById(request.getCart_id()).orElseThrow();
        var cartDetail = cartDetailRepository.findCartDetailByCartsIdAndProductsId(request.getCart_id(), request.getProduct_id());
        Product productModel = productRepository.findById(request.getProduct_id()).orElseThrow();
        if (cartDetail == null) {
            CartDetailKey key = CartDetailKey.builder()
                    .cartId(cartModel.getId())
                    .productId(productModel.getId())
                    .build();
            CartDetail cartDetailModel = CartDetail.builder()
                    .id(key)
                    .quantity(request.getQuantity())
                    .price(productModel.getPrice() * request.getQuantity())
                    .carts(cartModel)
                    .products(productModel)
                    .date_created(Date.from(java.time.Instant.now()))
                    .build();
            cartDetailRepository.save(cartDetailModel);
            cartModel.getCart_detail().add(cartDetailModel);
            cartModel.setTotal(getTotalPrice(request.getCart_id()));
            cartRepository.save(cartModel);
            return convertCartDetail(cartDetailModel);
        } else {
            System.out.println(cartDetail.getQuantity());
            cartDetail.setQuantity(cartDetail.getQuantity() + request.getQuantity());
            cartDetail.setPrice(cartDetail.getPrice() + productModel.getPrice() * request.getQuantity());
            cartDetailRepository.save(cartDetail);
            cartModel.setTotal(getTotalPrice(request.getCart_id()));
            cartRepository.save(cartModel);
            return convertCartDetail(cartDetail);
        }

    }

    public Float getTotalPrice(String cart_id) {
        Float totalPrice = 0f;
        List<CartDetail> cartDetailModels = cartDetailRepository.findByCartId(cart_id);
        for (CartDetail cartDetailModel : cartDetailModels) {
            totalPrice += cartDetailModel.getPrice();
        }
        return totalPrice;
    }

    public List<CartDetailResponse> getCartDetailOfCart(String cart_id) {
        return cartDetailRepository.findByCartId(cart_id).stream().map(this::convertCartDetail).toList();
    }

    public List<CartDetailResponse> getAllCartDetail() {
        return cartDetailRepository.findAll().stream().map(this::convertCartDetail).toList();
    }

    public List<CartDetailResponse> getAllProductOfCart(String cart_id) {
        return cartDetailRepository.findByCartId(cart_id).stream().map(this::convertCartDetail).toList();
    }

    public List<CartDetailResponse> deleteProductOfCart(CartDetailKey id) {
        cartDetailRepository.deleteProductByCartId(id.getCartId(), id.getProductId());
        Cart cart = cartRepository.findById(id.getCartId()).orElseThrow();
        cart.setTotal(getTotalPrice(cart.getId()));
        return getAllProductOfCart(id.getCartId());
    }

    public MessageResponse deleteCartDetailById(CartDetailRequest request) {
        cartDetailRepository.deleteProductByCartId(request.getCart_id(), request.getProduct_id());
        return MessageResponse.builder()
                .message("Delete cart detail successfully")
                .build();
    }

    public void deleteCartDetailOfProduct(Product product) {
        List<CartDetail> cartDetail = cartDetailRepository.findByProducts(product);
        cartDetail.forEach(c -> cartDetailRepository.deleteProductByCartId(c.getId().getCartId(), c.getId().getProductId()));
    }

    public void deleteCartDetailOfCart(Cart cart) {
        List<CartDetail> cartDetail = cartDetailRepository.findByCarts(cart);
        cartDetailRepository.deleteAll(cartDetail);
    }

    public MessageResponse deleteCartDetailByCartId(String cart_id) {
       var cartDetail = cartDetailRepository.findByCartId(cart_id);
       for (CartDetail c : cartDetail) {
           cartDetailRepository.deleteProductByCartId(c.getId().getCartId(), c.getId().getProductId());
       }
            return MessageResponse.builder()
                    .message("Delete cart detail of cart successfully")
                    .build();

    }

}
