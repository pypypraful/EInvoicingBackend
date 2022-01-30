package com.pypypraful.einvoicing.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.pypypraful.einvoicing.model.request.WorkflowMetadataRequest;
import com.pypypraful.einvoicing.model.response.WorkflowMetadataResponse;
import com.pypypraful.einvoicing.persistence.repository.DynamoDBInventoryRepository;
import com.pypypraful.einvoicing.persistence.repository.InventoryRepository;

public class StoreWorkflowMetadataHandler
        implements RequestHandler<WorkflowMetadataRequest, WorkflowMetadataResponse> {
    private InventoryRepository inventoryRepository;

    @Override
    public WorkflowMetadataResponse handleRequest(WorkflowMetadataRequest workflowMetadataRequest, Context context) {
        inventoryRepository = new DynamoDBInventoryRepository();
        return inventoryRepository.saveWorkflowMetadata(workflowMetadataRequest);
    }
}
