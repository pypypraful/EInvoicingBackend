package com.pypypraful.einvoicing.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessProfile {
    private String businessName;
    private Address businessAddress;
    private String businessPhoneNumber;
    private String panNumber;
    private String gstIN;
}
