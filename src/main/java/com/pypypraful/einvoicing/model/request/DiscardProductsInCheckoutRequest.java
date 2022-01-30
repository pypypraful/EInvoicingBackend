package com.pypypraful.einvoicing.model.request;

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
public class DiscardProductsInCheckoutRequest {
    private String orderId;
    private String customerId;
}
