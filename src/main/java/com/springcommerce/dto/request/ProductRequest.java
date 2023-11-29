package com.springcommerce.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotEmpty(message = "Name is required")
    private String name;
    private Float price;
    private String brand;
    private String color;
    private int quantity;
    @NotEmpty(message = "Category is required")
    private String category_id;
}