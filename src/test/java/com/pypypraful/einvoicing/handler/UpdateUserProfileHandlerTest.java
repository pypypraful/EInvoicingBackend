package com.pypypraful.einvoicing.handler;

import com.pypypraful.einvoicing.model.common.ClientAdditionalDetail;
import com.pypypraful.einvoicing.model.enums.ProfileType;
import com.pypypraful.einvoicing.model.request.UpdateUserProfileRequest;
import org.junit.Ignore;
import org.junit.Test;

public class UpdateUserProfileHandlerTest {

    private UpdateUserProfileHandler updateUserProfileHandler;

    @Test
    @Ignore
    public void testUpdateUserProfileHandler() {
        updateUserProfileHandler = new UpdateUserProfileHandler();
        UpdateUserProfileRequest updateUserProfileRequest = UpdateUserProfileRequest.builder()
                .username("Praful")
                .profileType(ProfileType.valueOf("SELLER"))
                .pincode(123456)
                .addressLine("Address")
                .clientAdditionalDetail(ClientAdditionalDetail.builder()
                        .panNumber("PAN Card")
                        .gstIN("GSTIN")
                        .build())
                .name("Business Name")
                .phoneNumber("Phone Number")
                .city("City")
                .state("State")
                .build();
        updateUserProfileHandler.handleRequest(updateUserProfileRequest, null);
    }
}
