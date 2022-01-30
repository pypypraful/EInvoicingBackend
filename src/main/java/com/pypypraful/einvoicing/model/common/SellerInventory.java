package com.pypypraful.einvoicing.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellerInventory {

    private String username;
    private String productDescription;
    private Integer productQuantity;
    private String productSubCategory;
    private String productCategory;
    private Double productPrice;
    private Integer productWeightPerUnitInGrams;
    private String productId;
    private String productName;
}
