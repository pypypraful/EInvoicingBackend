package com.pypypraful.einvoicing.persistence.repository;

import com.pypypraful.einvoicing.model.request.GetInventoryForUserRequest;
import com.pypypraful.einvoicing.model.request.GetUserProfileRequest;
import com.pypypraful.einvoicing.model.request.UpdateInventoryForUserRequest;
import com.pypypraful.einvoicing.model.request.UpdateUserProfileRequest;
import com.pypypraful.einvoicing.model.response.GetInventoryForUserResponse;
import com.pypypraful.einvoicing.model.response.GetUserProfileResponse;

public interface InventoryRepository {

    void updateInventoryRecord(UpdateInventoryForUserRequest updateInventoryForUserRequest);

    GetInventoryForUserResponse getInventoryRecord(GetInventoryForUserRequest getInventoryForUserRequest);

    void updateUserProfileRecord(UpdateUserProfileRequest updateUserProfileRequest);

    GetUserProfileResponse getUserProfileRecord(GetUserProfileRequest getUserProfileRequest);
}
