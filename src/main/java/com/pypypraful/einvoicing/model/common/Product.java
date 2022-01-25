package com.pypypraful.einvoicing.model.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Product {
    private String productName;
    private Integer quantity;
    private Double price;
    private String productId;
}
