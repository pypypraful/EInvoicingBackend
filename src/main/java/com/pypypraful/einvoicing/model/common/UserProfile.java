package com.pypypraful.einvoicing.model.common;

import com.pypypraful.einvoicing.model.enums.ProfileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private String username;
    private ProfileType profileType;
    private Integer pincode;
    private ClientAdditionalDetail clientAdditionalDetail;
    private String name;
    private String phoneNumber;
    private String addressLine;
    private String city;
    private String state;
}
