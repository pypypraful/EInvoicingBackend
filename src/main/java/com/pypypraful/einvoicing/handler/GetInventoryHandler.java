package com.pypypraful.einvoicing.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.pypypraful.einvoicing.model.request.GetInventoryForUserRequest;
import com.pypypraful.einvoicing.model.response.GetInventoryForUserResponse;
import com.pypypraful.einvoicing.persistence.repository.DynamoDBInventoryRepository;
import com.pypypraful.einvoicing.persistence.repository.InventoryRepository;

public class GetInventoryHandler implements RequestHandler<GetInventoryForUserRequest, GetInventoryForUserResponse> {

    private InventoryRepository inventoryRepository;

    public GetInventoryForUserResponse handleRequest(
            GetInventoryForUserRequest getInventoryForUserRequest, Context context) {

        inventoryRepository = new DynamoDBInventoryRepository();
        return inventoryRepository.getInventoryRecord(getInventoryForUserRequest);
    }
}
