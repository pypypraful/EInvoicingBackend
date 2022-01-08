package com.pypypraful.einvoicing.model.request;

import com.pypypraful.einvoicing.model.common.BusinessProfile;
import com.pypypraful.einvoicing.model.common.CustomerProfile;
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
    private BusinessProfile businessProfile;
    private CustomerProfile customerProfile;
}
