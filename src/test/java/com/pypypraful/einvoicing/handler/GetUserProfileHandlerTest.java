package com.pypypraful.einvoicing.handler;

import com.pypypraful.einvoicing.model.request.GetUserProfileRequest;
import org.junit.Ignore;
import org.junit.Test;

public class GetUserProfileHandlerTest {

    private GetUserProfileHandler getUserProfileHandler;

    @Test
    @Ignore
    public void testGetUserProfileHandler() {
        getUserProfileHandler = new GetUserProfileHandler();
        GetUserProfileRequest getUserProfileRequest = GetUserProfileRequest.builder()
                .username("Praful")
                .profileType("SELLER")
                .build();
        getUserProfileHandler.handleRequest(getUserProfileRequest, null);
    }
}
