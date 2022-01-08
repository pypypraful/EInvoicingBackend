package com.pypypraful.einvoicing.handler;

import com.pypypraful.einvoicing.model.common.Address;
import com.pypypraful.einvoicing.model.common.BusinessProfile;
import com.pypypraful.einvoicing.model.common.CustomerProfile;
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
                .businessProfile(BusinessProfile.builder()
                        .businessName("Bhagwati Traders")
                        .panNumber("Pan Number")
                        .gstIN("GST IN")
                        .businessPhoneNumber("Phone Number")
                        .businessAddress(Address.builder()
                                .addressLine("Address Line")
                                .city("Agra")
                                .state("UP")
                                .pincode(282007)
                                .build()
                        )
                        .build()
                )
                .customerProfile(CustomerProfile.builder()
                        .customerName("Praful Goyal")
                        .customerPhoneNumber("Phone Number")
                        .homeAddress(Address.builder()
                                .addressLine("Address Line")
                                .city("Agra")
                                .state("UP")
                                .pincode(282007)
                                .build()
                        )
                        .build()
                )
                .username("Praful")
                .build();
        updateUserProfileHandler.handleRequest(updateUserProfileRequest, null);
    }
}
