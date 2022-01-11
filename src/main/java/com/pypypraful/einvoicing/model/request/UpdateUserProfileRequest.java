package com.pypypraful.einvoicing.model.request;

import com.pypypraful.einvoicing.model.common.ClientAdditionalDetail;
import com.pypypraful.einvoicing.model.enums.ProfileType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UpdateUserProfileRequest {
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
