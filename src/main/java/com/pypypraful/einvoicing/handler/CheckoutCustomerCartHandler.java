package com.pypypraful.einvoicing.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.pypypraful.einvoicing.facade.CheckoutCustomerCartFacade;
import com.pypypraful.einvoicing.model.request.CheckoutCustomerCartRequest;
import com.pypypraful.einvoicing.model.response.CheckoutCustomerCartResponse;

public class CheckoutCustomerCartHandler
        implements RequestHandler<CheckoutCustomerCartRequest, CheckoutCustomerCartResponse> {

    CheckoutCustomerCartFacade checkoutCustomerCartFacade;

    @Override
    public CheckoutCustomerCartResponse handleRequest(CheckoutCustomerCartRequest checkoutCustomerCartRequest, Context context) {
        checkoutCustomerCartFacade = new CheckoutCustomerCartFacade();
        return checkoutCustomerCartFacade.checkoutCustomerCart(checkoutCustomerCartRequest);
    }
}
