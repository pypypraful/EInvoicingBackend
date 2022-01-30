package com.pypypraful.einvoicing.handler;

import com.pypypraful.einvoicing.model.request.WorkflowMetadataRequest;
import org.junit.Ignore;
import org.junit.Test;

public class StoreWorkflowMetadataHandlerTest {

    private StoreWorkflowMetadataHandler storeWorkflowMetadata;

    @Test
    @Ignore
    public void testStoreWorkflowMetadataHandler() {
        storeWorkflowMetadata = new StoreWorkflowMetadataHandler();
        WorkflowMetadataRequest workflowMetadataRequest = WorkflowMetadataRequest.builder()
                .customerId("qwerty")
                .build();
        storeWorkflowMetadata.handleRequest(workflowMetadataRequest, null);
    }
}
