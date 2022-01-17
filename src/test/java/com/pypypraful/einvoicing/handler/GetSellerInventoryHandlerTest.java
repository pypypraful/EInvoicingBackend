package com.pypypraful.einvoicing.handler;

import com.pypypraful.einvoicing.model.request.GetProductListRequest;
import org.junit.Ignore;
import org.junit.Test;

public class GetSellerInventoryHandlerTest {

    private GetInventoryHandler getSellerInventoryHandler;

    @Test
    @Ignore
    public void testGetSellerInventoryHandler() {
        getSellerInventoryHandler = new GetInventoryHandler();
        GetProductListRequest getProductListRequest = GetProductListRequest.builder()
                .username("Praful")
                .build();
        getSellerInventoryHandler.handleRequest(getProductListRequest, null);
    }
}
