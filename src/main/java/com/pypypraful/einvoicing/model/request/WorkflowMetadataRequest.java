package com.pypypraful.einvoicing.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowMetadataRequest {
    private String customerId;
    private String status;
    private String executionArn;
    private String executionName;
    private String workflowType;
    private String stepTaskToken;
}
