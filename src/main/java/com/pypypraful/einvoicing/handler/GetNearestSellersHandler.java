package com.pypypraful.einvoicing.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.pypypraful.einvoicing.model.request.GetNearestSellersProfileRequest;
import com.pypypraful.einvoicing.model.response.GetNearestSellersProfileResponse;
import com.pypypraful.einvoicing.persistence.repository.DynamoDBInventoryRepository;
import com.pypypraful.einvoicing.persistence.repository.InventoryRepository;

public class GetNearestSellersHandler
        implements RequestHandler<GetNearestSellersProfileRequest, GetNearestSellersProfileResponse> {

    private InventoryRepository inventoryRepository;

    @Override
    public GetNearestSellersProfileResponse handleRequest(GetNearestSellersProfileRequest request, Context context) {
        inventoryRepository = new DynamoDBInventoryRepository();
        return inventoryRepository.getSellersProfile(request);
    }
}
