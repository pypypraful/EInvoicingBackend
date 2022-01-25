package com.pypypraful.einvoicing.model.response;

import com.pypypraful.einvoicing.model.common.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class UpdateProductInCartResponse {
    private String customerId;
    private List<Product> products;
}
