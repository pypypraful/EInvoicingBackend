package com.pypypraful.einvoicing.handler;

import com.pypypraful.einvoicing.model.request.GetProductListRequest;
import org.junit.Ignore;
import org.junit.Test;

public class GetSellerInventoryHandlerTest {

    private GetSellerInventoryHandler getSellerInventoryHandler;

    @Test
    @Ignore
    public void testGetSellerInventoryHandler() {
        getSellerInventoryHandler = new GetSellerInventoryHandler();
        GetProductListRequest getProductListRequest = GetProductListRequest.builder()
                .username("Praful")
                .build();
        getSellerInventoryHandler.handleRequest(getProductListRequest, null);
    }
}
