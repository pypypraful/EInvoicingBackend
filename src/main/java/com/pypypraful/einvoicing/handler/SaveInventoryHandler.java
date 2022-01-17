package com.pypypraful.einvoicing.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.pypypraful.einvoicing.model.request.UpdateSellerInventoryRequest;
import com.pypypraful.einvoicing.model.response.UpdateSellerInventoryResponse;
import com.pypypraful.einvoicing.persistence.repository.DynamoDBInventoryRepository;
import com.pypypraful.einvoicing.persistence.repository.InventoryRepository;

public class SaveInventoryHandler
        implements RequestHandler<UpdateSellerInventoryRequest, UpdateSellerInventoryResponse> {

    private InventoryRepository inventoryRepository;

    public UpdateSellerInventoryResponse handleRequest(
            UpdateSellerInventoryRequest updateInventoryForUserRequest, Context context) {

        inventoryRepository = new DynamoDBInventoryRepository();
        inventoryRepository.updateSellerInventoryRecord(updateInventoryForUserRequest);
        UpdateSellerInventoryResponse updateSellerInventoryResponse = new UpdateSellerInventoryResponse();
        updateSellerInventoryResponse.setSellerInventories(updateInventoryForUserRequest.getSellerInventories());
        return updateSellerInventoryResponse;
    }
}