package com.pypypraful.einvoicing.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.pypypraful.einvoicing.model.common.UserProfile;
import com.pypypraful.einvoicing.model.request.UpdateUserProfileRequest;
import com.pypypraful.einvoicing.model.response.UpdateUserProfileResponse;
import com.pypypraful.einvoicing.persistence.repository.DynamoDBInventoryRepository;
import com.pypypraful.einvoicing.persistence.repository.InventoryRepository;

public class UpdateUserProfileHandler implements RequestHandler<UpdateUserProfileRequest, UpdateUserProfileResponse> {

    private InventoryRepository inventoryRepository;

    public UpdateUserProfileResponse handleRequest(
            UpdateUserProfileRequest updateUserProfileRequest, Context context
    ) {
        inventoryRepository = new DynamoDBInventoryRepository();
        inventoryRepository.updateUserProfileRecord(updateUserProfileRequest);
        return UpdateUserProfileResponse.builder()
                .userProfile(UserProfile.builder()
                    .username(updateUserProfileRequest.getUserProfile().getUsername())
                    .profileType(updateUserProfileRequest.getUserProfile().getProfileType())
                    .pincode(updateUserProfileRequest.getUserProfile().getPincode())
                    .name(updateUserProfileRequest.getUserProfile().getName())
                    .addressLine(updateUserProfileRequest.getUserProfile().getAddressLine())
                    .clientAdditionalDetail(updateUserProfileRequest.getUserProfile().getClientAdditionalDetail())
                    .phoneNumber(updateUserProfileRequest.getUserProfile().getPhoneNumber())
                    .city(updateUserProfileRequest.getUserProfile().getCity())
                    .state(updateUserProfileRequest.getUserProfile().getState())
                    .build()
                )
                .build();
    }

}
