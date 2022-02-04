package com.pypypraful.einvoicing.facade;

import com.pypypraful.einvoicing.model.enums.OrderStatus;
import com.pypypraful.einvoicing.model.request.ConfirmOrderRequest;
import com.pypypraful.einvoicing.model.response.ConfirmOrderResponse;
import com.pypypraful.einvoicing.persistence.repository.DynamoDBInventoryRepository;
import com.pypypraful.einvoicing.persistence.repository.InventoryRepository;
import com.pypypraful.einvoicing.workflows.stateMachine.StateMachineRepository;
import com.pypypraful.einvoicing.workflows.stateMachine.StateMachineRepositoryImpl;

public class ConfirmOrderFacade {
    private final InventoryRepository inventoryRepository;
    private final ConfirmOrderResponse confirmOrderResponse;
    private final StateMachineRepository stateMachineRepository;

    public ConfirmOrderFacade() {
        this.inventoryRepository = new DynamoDBInventoryRepository();
        this.confirmOrderResponse = new ConfirmOrderResponse();
        this.stateMachineRepository = new StateMachineRepositoryImpl();
    }

    public ConfirmOrderResponse confirmOrder(ConfirmOrderRequest orderRequest) {
        confirmOrderResponse.setCustomerId(orderRequest.getCustomerId());
        inventoryRepository.updateOrderStatus(OrderStatus.ORDERED, orderRequest.getTempOrderId());
        confirmOrderResponse.setCustomerId(orderRequest.getCustomerId());
        stateMachineRepository.sendSuccessTaskToken(getTaskTokenForResumeWorkflow(orderRequest.getTempOrderId()));
        return confirmOrderResponse;
    }

    private String getTaskTokenForResumeWorkflow(String orderId) {
        return inventoryRepository.getWorkflowMetadata(orderId).getStepTaskToken();
    }
}
