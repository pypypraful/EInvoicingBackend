package com.pypypraful.einvoicing.workflows.stateMachine;

public interface StateMachineRepository {

    void startCheckoutStateMachine(String orderId, String customerId);

    void sendSuccessTaskToken(String taskToken);
}
