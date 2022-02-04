package com.pypypraful.einvoicing.workflows.stepfunctions;

import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.model.SendTaskSuccessRequest;
import com.amazonaws.services.stepfunctions.model.SendTaskSuccessResult;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pypypraful.einvoicing.workflows.stateMachine.request.CheckoutOrder;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

public class CheckoutStateMachine {

    private final AWSStepFunctions sfnClient;
    private final String stateMachineArn = "arn:aws:states:ap-south-1:307005857184:stateMachine:CheckoutStateMachine";

    public CheckoutStateMachine() { sfnClient = StepFunctionClient.getAwsStepFunctionClient(); }

    @SneakyThrows
    public void startExecution(CheckoutOrder checkoutOrder) {
        sfnClient.startExecution(
                new StartExecutionRequest()
                    .withStateMachineArn(stateMachineArn)
                    .withInput(new ObjectMapper().writeValueAsString(checkoutOrder))
                    .withName(checkoutOrder.getOrderId())
        );
    }

    @SneakyThrows
    public void sendSuccessTaskToken(String taskToken) {
        Map<String, String> map = new HashMap<>();
        map.put("Custom", "Praful");
        sfnClient.sendTaskSuccess(
                new SendTaskSuccessRequest()
                        .withOutput(new ObjectMapper().writeValueAsString(map))
                        .withTaskToken(taskToken));
    }
}
