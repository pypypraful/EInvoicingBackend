package com.pypypraful.einvoicing.model.response;

import com.pypypraful.einvoicing.model.common.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutCustomerCartResponse {
    private String customerId;
    private List<Product> acceptedProducts;
    private List<Product> rejectedProducts;
}
