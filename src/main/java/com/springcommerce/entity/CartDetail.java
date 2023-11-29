package com.springcommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@Table(name = "cart_detail")
@AllArgsConstructor
@NoArgsConstructor
public class CartDetail {
    @EmbeddedId
    private CartDetailKey id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    private Cart carts;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product products;

    private Float price;

    private Integer quantity;

    private Date date_created;

    private Date date_updated;
}
