package com.pypypraful.einvoicing.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.pypypraful.einvoicing.facade.DiscardProductsInCheckoutFacade;
import com.pypypraful.einvoicing.model.request.DiscardProductsInCheckoutRequest;

public class DiscardProductsInCheckoutHandler
        implements RequestHandler<DiscardProductsInCheckoutRequest, String> {

    @Override
    public String handleRequest(DiscardProductsInCheckoutRequest discardProductsInCheckoutRequest, Context context) {
        DiscardProductsInCheckoutFacade discardProductsInCheckoutFacade = new DiscardProductsInCheckoutFacade();
        discardProductsInCheckoutFacade.discardProductsInOrder(discardProductsInCheckoutRequest);
        return "Products Discarded Successfully";
    }
}
