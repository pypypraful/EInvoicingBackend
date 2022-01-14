package com.pypypraful.einvoicing.model.request;

import com.pypypraful.einvoicing.model.common.UserProfile;
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
    private UserProfile userProfile;
}
