package com.pypypraful.einvoicing.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.pypypraful.einvoicing.model.request.UpdateInventoryForUserRequest;
import com.pypypraful.einvoicing.model.response.InventoryForUserResponse;
import com.pypypraful.einvoicing.persistence.repository.DynamoDBInventoryRepository;
import com.pypypraful.einvoicing.persistence.repository.InventoryRepository;

public class SaveInventoryHandler
        implements RequestHandler<UpdateInventoryForUserRequest, InventoryForUserResponse> {

    private InventoryRepository inventoryRepository;

    public InventoryForUserResponse handleRequest(
            UpdateInventoryForUserRequest updateInventoryForUserRequest, Context context) {

        inventoryRepository = new DynamoDBInventoryRepository();
        inventoryRepository.updateInventoryRecord(updateInventoryForUserRequest);
        InventoryForUserResponse inventoryForUserResponse = new InventoryForUserResponse();
        inventoryForUserResponse.setMessage("Saved Successfully!!!");
        return inventoryForUserResponse;
    }
}