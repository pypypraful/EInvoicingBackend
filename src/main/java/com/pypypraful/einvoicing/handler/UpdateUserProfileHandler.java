package com.pypypraful.einvoicing.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
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
                .username(updateUserProfileRequest.getUsername())
                .businessProfile(updateUserProfileRequest.getBusinessProfile())
                .customerProfile(updateUserProfileRequest.getCustomerProfile())
                .build();
    }

}
