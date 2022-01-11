package com.pypypraful.einvoicing.persistence.dynamodb.model.adapter;

import com.pypypraful.einvoicing.model.common.ClientAdditionalDetail;
import com.pypypraful.einvoicing.model.enums.ProfileType;
import com.pypypraful.einvoicing.model.request.UpdateUserProfileRequest;
import com.pypypraful.einvoicing.model.response.GetUserProfileResponse;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBUserProfile;
import com.pypypraful.einvoicing.persistence.dynamodb.model.common.DBClientAdditionalDetail;

public class UserProfileDBAdapter {

    public static DBUserProfile convertUserProfileToDBModel(UpdateUserProfileRequest updateUserProfileRequest) {
        return DBUserProfile.builder()
                .username(updateUserProfileRequest.getUsername())
                .profileType(updateUserProfileRequest.getProfileType().toString())
                .pincode(updateUserProfileRequest.getPincode())
                .clientAdditionalDetail(DBClientAdditionalDetail.builder()
                        .panNumber(updateUserProfileRequest.getClientAdditionalDetail().getPanNumber())
                        .gstIN(updateUserProfileRequest.getClientAdditionalDetail().getGstIN())
                        .build())
                .name(updateUserProfileRequest.getName())
                .phoneNumber(updateUserProfileRequest.getPhoneNumber())
                .addressLine(updateUserProfileRequest.getAddressLine())
                .city(updateUserProfileRequest.getCity())
                .state(updateUserProfileRequest.getState())
                .build();
    }

    public static GetUserProfileResponse convertDBUserProfileToUserProfileResponse(DBUserProfile dbUserProfile) {
        return GetUserProfileResponse.builder()
                .username(dbUserProfile.getUsername())
                .profileType(ProfileType.valueOf(dbUserProfile.getProfileType()))
                .pincode(dbUserProfile.getPincode())
                .clientAdditionalDetail(ClientAdditionalDetail.builder()
                        .gstIN(dbUserProfile.getClientAdditionalDetail().getGstIN())
                        .panNumber(dbUserProfile.getClientAdditionalDetail().getPanNumber())
                        .build())
                .name(dbUserProfile.getName())
                .phoneNumber(dbUserProfile.getPhoneNumber())
                .addressLine(dbUserProfile.getAddressLine())
                .city(dbUserProfile.getCity())
                .state(dbUserProfile.getState())
                .build();
    }
}
