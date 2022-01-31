package com.pypypraful.einvoicing.handler;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.pypypraful.einvoicing.model.request.GetUserProfileRequest;
import com.pypypraful.einvoicing.model.response.UpdateUserProfileResponse;
import com.pypypraful.einvoicing.persistence.repository.DynamoDBInventoryRepository;
import com.pypypraful.einvoicing.persistence.repository.InventoryRepository;

public class GetUserProfileHandler implements RequestHandler<GetUserProfileRequest, UpdateUserProfileResponse> {

    private InventoryRepository inventoryRepository;

    @Override
    public UpdateUserProfileResponse handleRequest(GetUserProfileRequest getUserProfileRequest, Context context) {
        inventoryRepository = new DynamoDBInventoryRepository();
        return inventoryRepository.getUserProfileRecord(getUserProfileRequest);
    }
}
