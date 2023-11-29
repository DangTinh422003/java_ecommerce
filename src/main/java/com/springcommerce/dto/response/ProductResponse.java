package com.springcommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private Float price;
    private String brand;
    private String color;
    private Integer quantity;
    private String categoryName;

    private Date date_created;

    private Date date_updated;
}
