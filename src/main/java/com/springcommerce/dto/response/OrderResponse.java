package com.springcommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String id;
    private String email;
    private String phone;
    private String address;
    private int total;
    private Date date_created;
    private Date date_updated;
    private List<OrderDetailResponse> order_detail;
}