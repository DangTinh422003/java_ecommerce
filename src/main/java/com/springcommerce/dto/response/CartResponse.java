package com.springcommerce.dto.response;

import com.springcommerce.entity.CartDetail;
import com.springcommerce.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private String id;

    private Float total;

    private Date date_created;

    private Date date_updated;

    private String user;

    private List<CartDetailResponse> cart_detail;

}
