package com.pypypraful.einvoicing.persistence.dynamodb.model.adapter;

import com.pypypraful.einvoicing.model.common.Address;
import com.pypypraful.einvoicing.model.common.BusinessProfile;
import com.pypypraful.einvoicing.model.common.CustomerProfile;
import com.pypypraful.einvoicing.model.request.UpdateUserProfileRequest;
import com.pypypraful.einvoicing.model.response.GetUserProfileResponse;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBUserProfile;
import com.pypypraful.einvoicing.persistence.dynamodb.model.common.DBAddress;
import com.pypypraful.einvoicing.persistence.dynamodb.model.common.DBBusinessProfile;
import com.pypypraful.einvoicing.persistence.dynamodb.model.common.DBCustomerProfile;

public class UserProfileDBAdapter {

    public static DBUserProfile convertUserProfileToDBModel(UpdateUserProfileRequest updateUserProfileRequest) {
        return DBUserProfile.builder()
                .username(updateUserProfileRequest.getUsername())
                .customerProfile(DBCustomerProfile.builder()
                        .customerName(updateUserProfileRequest.getCustomerProfile().getCustomerName())
                        .customerPhoneNumber(updateUserProfileRequest.getCustomerProfile().getCustomerPhoneNumber())
                        .homeAddress(DBAddress.builder()
                                .addressLine(updateUserProfileRequest.getCustomerProfile().getHomeAddress().getAddressLine())
                                .city(updateUserProfileRequest.getCustomerProfile().getHomeAddress().getCity())
                                .state(updateUserProfileRequest.getCustomerProfile().getHomeAddress().getState())
                                .pincode(updateUserProfileRequest.getCustomerProfile().getHomeAddress().getPincode())
                                .build()
                        )
                        .build()
                )
                .businessProfile(DBBusinessProfile.builder()
                        .businessName(updateUserProfileRequest.getBusinessProfile().getBusinessName())
                        .businessPhoneNumber(updateUserProfileRequest.getBusinessProfile().getBusinessPhoneNumber())
                        .panNumber(updateUserProfileRequest.getBusinessProfile().getPanNumber())
                        .gstIN(updateUserProfileRequest.getBusinessProfile().getGstIN())
                        .businessAddress(DBAddress.builder()
                                .addressLine(updateUserProfileRequest.getBusinessProfile().getBusinessAddress().getAddressLine())
                                .city(updateUserProfileRequest.getBusinessProfile().getBusinessAddress().getCity())
                                .state(updateUserProfileRequest.getBusinessProfile().getBusinessAddress().getState())
                                .pincode(updateUserProfileRequest.getBusinessProfile().getBusinessAddress().getPincode())
                                .build()
                        )
                        .build()
                )
                .build();
    }

    public static GetUserProfileResponse convertDBUserProfileToUserProfileResponse(DBUserProfile dbUserProfile) {
        return GetUserProfileResponse.builder()
                .username(dbUserProfile.getUsername())
                .businessProfile(BusinessProfile.builder()
                        .businessName(dbUserProfile.getBusinessProfile().getBusinessName())
                        .businessPhoneNumber(dbUserProfile.getBusinessProfile().getBusinessPhoneNumber())
                        .panNumber(dbUserProfile.getBusinessProfile().getPanNumber())
                        .gstIN(dbUserProfile.getBusinessProfile().getGstIN())
                        .businessAddress(Address.builder()
                                .addressLine(dbUserProfile.getBusinessProfile().getBusinessAddress().getAddressLine())
                                .city(dbUserProfile.getBusinessProfile().getBusinessAddress().getCity())
                                .state(dbUserProfile.getBusinessProfile().getBusinessAddress().getState())
                                .pincode(dbUserProfile.getBusinessProfile().getBusinessAddress().getPincode())
                                .build()
                        )
                        .build()
                )
                .customerProfile(CustomerProfile.builder()
                        .customerName(dbUserProfile.getCustomerProfile().getCustomerName())
                        .customerPhoneNumber(dbUserProfile.getCustomerProfile().getCustomerPhoneNumber())
                        .homeAddress(Address.builder()
                                .addressLine(dbUserProfile.getCustomerProfile().getHomeAddress().getAddressLine())
                                .city(dbUserProfile.getCustomerProfile().getHomeAddress().getCity())
                                .state(dbUserProfile.getCustomerProfile().getHomeAddress().getState())
                                .pincode(dbUserProfile.getCustomerProfile().getHomeAddress().getPincode())
                                .build()
                        )
                        .build()
                ).build();
    }
}
