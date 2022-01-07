package com.pypypraful.einvoicing.persistence.repository;

import com.pypypraful.einvoicing.model.request.GetInventoryForUserRequest;
import com.pypypraful.einvoicing.model.request.UpdateInventoryForUserRequest;
import com.pypypraful.einvoicing.model.response.GetInventoryForUserResponse;

public interface InventoryRepository {

    void updateInventoryRecord(UpdateInventoryForUserRequest updateInventoryForUserRequest);

    GetInventoryForUserResponse getInventoryRecord(GetInventoryForUserRequest getInventoryForUserRequest);
}
