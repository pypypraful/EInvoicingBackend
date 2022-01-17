package com.pypypraful.einvoicing.persistence.repository;

import com.pypypraful.einvoicing.model.request.*;
import com.pypypraful.einvoicing.model.response.GetProductListResponse;
import com.pypypraful.einvoicing.model.response.GetUserProfileResponse;

public interface InventoryRepository {

    void updateSellerInventoryRecord(UpdateSellerInventoryRequest updateSellerInventoryRequest);

    GetProductListResponse getProductList(GetProductListRequest getProductListRequest);

    void updateUserProfileRecord(UpdateUserProfileRequest updateUserProfileRequest);

    GetUserProfileResponse getUserProfileRecord(GetUserProfileRequest getUserProfileRequest);
}
