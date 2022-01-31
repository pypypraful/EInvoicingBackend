package com.pypypraful.einvoicing.model.response;

import com.pypypraful.einvoicing.model.common.UserProfile;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Setter
public class GetNearestSellersProfileResponse {
    private List<UserProfile> sellerProfiles;
}
