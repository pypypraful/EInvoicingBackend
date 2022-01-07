package com.pypypraful.einvoicing.persistence.repository;

import com.pypypraful.einvoicing.model.request.UpdateInventoryForUserRequest;

public interface InventoryRepository {

    void updateInventoryRecord(UpdateInventoryForUserRequest updateInventoryForUserRequest);
}
