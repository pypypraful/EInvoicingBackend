package com.pypypraful.einvoicing.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.pypypraful.einvoicing.model.request.GetProductInCartRequest;
import com.pypypraful.einvoicing.model.response.UpdateProductInCartResponse;
import com.pypypraful.einvoicing.persistence.repository.DynamoDBInventoryRepository;
import com.pypypraful.einvoicing.persistence.repository.InventoryRepository;

public class GetCustomerCartHandler
        implements RequestHandler<GetProductInCartRequest, UpdateProductInCartResponse> {
    private InventoryRepository inventoryRepository;

    @Override
    public UpdateProductInCartResponse handleRequest(GetProductInCartRequest productInCartRequest, Context context) {
        inventoryRepository = new DynamoDBInventoryRepository();
        return inventoryRepository.getCustomerCart(productInCartRequest);
    }
}
