package com.pypypraful.einvoicing.workflows.stepfunctions;

import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;

public class StepFunctionClient {

    private static AWSStepFunctions stepFunctionsClient;

    public static AWSStepFunctions getAwsStepFunctionClient() {
        if (stepFunctionsClient == null) {
            stepFunctionsClient = AWSStepFunctionsClientBuilder.defaultClient();
        }
        return stepFunctionsClient;
    }
}
