package com.pypypraful.einvoicing.handler;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.pypypraful.einvoicing.model.request.GetUserProfileRequest;
import com.pypypraful.einvoicing.model.response.GetUserProfileResponse;
import com.pypypraful.einvoicing.persistence.repository.DynamoDBInventoryRepository;
import com.pypypraful.einvoicing.persistence.repository.InventoryRepository;

public class GetUserProfileHandler implements RequestHandler<GetUserProfileRequest, GetUserProfileResponse> {

    private InventoryRepository inventoryRepository;

    @Override
    public GetUserProfileResponse handleRequest(GetUserProfileRequest getUserProfileRequest, Context context) {
        inventoryRepository = new DynamoDBInventoryRepository();
        return inventoryRepository.getUserProfileRecord(getUserProfileRequest);
    }
}
