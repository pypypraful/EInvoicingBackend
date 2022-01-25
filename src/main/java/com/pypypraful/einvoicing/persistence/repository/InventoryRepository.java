package com.pypypraful.einvoicing.persistence.repository;

import com.pypypraful.einvoicing.model.request.GetProductInCartRequest;
import com.pypypraful.einvoicing.model.request.GetProductListRequest;
import com.pypypraful.einvoicing.model.request.GetUserProfileRequest;
import com.pypypraful.einvoicing.model.request.UpdateProductInCartRequest;
import com.pypypraful.einvoicing.model.request.UpdateSellerInventoryRequest;
import com.pypypraful.einvoicing.model.request.UpdateUserProfileRequest;
import com.pypypraful.einvoicing.model.response.GetProductListResponse;
import com.pypypraful.einvoicing.model.response.GetUserProfileResponse;
import com.pypypraful.einvoicing.model.response.UpdateProductInCartResponse;

public interface InventoryRepository {

    void updateSellerInventoryRecord(UpdateSellerInventoryRequest updateSellerInventoryRequest);

    GetProductListResponse getProductList(GetProductListRequest getProductListRequest);

    void updateUserProfileRecord(UpdateUserProfileRequest updateUserProfileRequest);

    GetUserProfileResponse getUserProfileRecord(GetUserProfileRequest getUserProfileRequest);

    UpdateProductInCartResponse updateCustomerCart(UpdateProductInCartRequest productInCartRequest);

    UpdateProductInCartResponse getCustomerCart(GetProductInCartRequest productInCartRequest);
}
