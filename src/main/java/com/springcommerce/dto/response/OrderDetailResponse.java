package com.springcommerce.dto.response;

import com.springcommerce.entity.OrderDetailKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private OrderDetailKey id;
    private String product;
    private int total;
    private Date date_created;
    private Date date_updated;
}
