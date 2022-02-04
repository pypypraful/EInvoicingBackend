package com.pypypraful.einvoicing.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.pypypraful.einvoicing.facade.ConfirmOrderFacade;
import com.pypypraful.einvoicing.model.request.ConfirmOrderRequest;
import com.pypypraful.einvoicing.model.response.ConfirmOrderResponse;

public class ConfirmOrderHandler implements RequestHandler<ConfirmOrderRequest, ConfirmOrderResponse> {

    ConfirmOrderFacade confirmOrderFacade;

    public ConfirmOrderResponse handleRequest(ConfirmOrderRequest request, Context context) {
        confirmOrderFacade = new ConfirmOrderFacade();
        return confirmOrderFacade.confirmOrder(request);
    }
}
