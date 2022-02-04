package com.pypypraful.einvoicing.workflows.stateMachine;

import com.pypypraful.einvoicing.workflows.stateMachine.request.CheckoutOrder;
import com.pypypraful.einvoicing.workflows.stepfunctions.CheckoutStateMachine;

public class StateMachineRepositoryImpl implements StateMachineRepository {

    private final CheckoutStateMachine checkoutStateMachine;

    public StateMachineRepositoryImpl() {
        this.checkoutStateMachine = new CheckoutStateMachine();
    }

    @Override
    public void startCheckoutStateMachine(String orderId, String customerId) {
        checkoutStateMachine.startExecution(CheckoutOrder.builder()
                .customerId(customerId)
                .orderId(orderId)
                .build());
    }

    @Override
    public void sendSuccessTaskToken(String taskToken) {
        checkoutStateMachine.sendSuccessTaskToken(taskToken);
    }
}
