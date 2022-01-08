package com.pypypraful.einvoicing.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfile {
    private String customerName;
    private Address homeAddress;
    private String customerPhoneNumber;
}
