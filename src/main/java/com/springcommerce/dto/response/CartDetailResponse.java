package com.springcommerce.dto.response;

import com.springcommerce.entity.CartDetailKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailResponse {
    private CartDetailKey id;
    private String product;
    private Float price;
    private int quantity;
    private Date date_created;
    private Date date_updated;
}
