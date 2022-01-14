package com.pypypraful.einvoicing.model.response;

import com.pypypraful.einvoicing.model.common.UserProfile;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserProfileResponse {
    private UserProfile userProfile;
}
