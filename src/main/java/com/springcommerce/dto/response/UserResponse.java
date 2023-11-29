package com.springcommerce.dto.response;

import com.springcommerce.entity.Order;
import com.springcommerce.util.Role;
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
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private Role role;
    private List<OrderResponse> order;
    private Date date_created;
    private Date date_updated;
}
