package com.pypypraful.einvoicing.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class WorkflowMetadataResponse {
    private String customerId;
    private String status;
    private String executionArn;
    private String executionName;
    private String workflowType;
    private String stepTaskToken;
}
