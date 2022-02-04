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
public class ConfirmOrderRequest {
    private String tempOrderId;
    private String customerId;
}
