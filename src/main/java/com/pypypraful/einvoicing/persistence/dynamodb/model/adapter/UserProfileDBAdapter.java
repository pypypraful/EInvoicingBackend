package com.pypypraful.einvoicing.persistence.dynamodb.model.adapter;

import com.pypypraful.einvoicing.model.common.ClientAdditionalDetail;
import com.pypypraful.einvoicing.model.common.UserProfile;
import com.pypypraful.einvoicing.model.enums.ProfileType;
import com.pypypraful.einvoicing.model.request.UpdateUserProfileRequest;
import com.pypypraful.einvoicing.model.response.GetNearestSellersProfileResponse;
import com.pypypraful.einvoicing.model.response.UpdateUserProfileResponse;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBUserProfile;
import com.pypypraful.einvoicing.persistence.dynamodb.model.common.DBClientAdditionalDetail;

import java.util.ArrayList;
import java.util.List;

public class UserProfileDBAdapter {

    public static DBUserProfile convertUserProfileToDBModel(UpdateUserProfileRequest updateUserProfileRequest) {
        return DBUserProfile.builder()
                .username(updateUserProfileRequest.getUserProfile().getUsername())
                .profileType(updateUserProfileRequest.getUserProfile().getProfileType().toString())
                .pincode(updateUserProfileRequest.getUserProfile().getPincode())
                .clientAdditionalDetail(DBClientAdditionalDetail.builder()
                        .panNumber(updateUserProfileRequest.getUserProfile().getClientAdditionalDetail().getPanNumber())
                        .gstIN(updateUserProfileRequest.getUserProfile().getClientAdditionalDetail().getGstIN())
                        .build())
                .name(updateUserProfileRequest.getUserProfile().getName())
                .phoneNumber(updateUserProfileRequest.getUserProfile().getPhoneNumber())
                .addressLine(updateUserProfileRequest.getUserProfile().getAddressLine())
                .city(updateUserProfileRequest.getUserProfile().getCity())
                .state(updateUserProfileRequest.getUserProfile().getState())
                .build();
    }

    public static UpdateUserProfileResponse convertDBUserProfileToUserProfileResponse(List<DBUserProfile> dbUserProfiles) {
        return UpdateUserProfileResponse.builder()
                .userProfile(UserProfile.builder()
                        .username(dbUserProfiles.get(0).getUsername())
                        .profileType(ProfileType.valueOf(dbUserProfiles.get(0).getProfileType()))
                        .pincode(dbUserProfiles.get(0).getPincode())
                        .clientAdditionalDetail(ClientAdditionalDetail.builder()
                                .gstIN(dbUserProfiles.get(0).getClientAdditionalDetail().getGstIN())
                                .panNumber(dbUserProfiles.get(0).getClientAdditionalDetail().getPanNumber())
                                .build())
                        .name(dbUserProfiles.get(0).getName())
                        .phoneNumber(dbUserProfiles.get(0).getPhoneNumber())
                        .addressLine(dbUserProfiles.get(0).getAddressLine())
                        .city(dbUserProfiles.get(0).getCity())
                        .state(dbUserProfiles.get(0).getState())
                        .build())
                .build();
    }

    public static GetNearestSellersProfileResponse convertDBUserProfileToSellersProfileResponse(List<DBUserProfile> dbUserProfiles) {
        List<UserProfile> sellersProfile = new ArrayList<>();
        for (DBUserProfile dbUserProfile : dbUserProfiles) {
            sellersProfile.add(UserProfile.builder()
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
                    .build());
        }
        return GetNearestSellersProfileResponse.builder()
                .sellerProfiles(sellersProfile)
                .build();
    }
}
