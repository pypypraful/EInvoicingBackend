package com.pypypraful.einvoicing.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.pypypraful.einvoicing.model.request.GetProductListRequest;
import com.pypypraful.einvoicing.model.response.GetProductListResponse;
import com.pypypraful.einvoicing.persistence.repository.DynamoDBInventoryRepository;
import com.pypypraful.einvoicing.persistence.repository.InventoryRepository;

public class GetInventoryHandler implements RequestHandler<GetProductListRequest, GetProductListResponse> {

    private InventoryRepository inventoryRepository;

    public GetProductListResponse handleRequest(
            GetProductListRequest getProductListRequest, Context context) {
        inventoryRepository = new DynamoDBInventoryRepository();
        return inventoryRepository.getProductList(getProductListRequest);
    }
}
