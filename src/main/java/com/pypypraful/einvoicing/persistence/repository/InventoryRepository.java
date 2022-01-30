package com.pypypraful.einvoicing.persistence.repository;

import com.pypypraful.einvoicing.model.request.GetProductInCartRequest;
import com.pypypraful.einvoicing.model.request.GetProductListRequest;
import com.pypypraful.einvoicing.model.request.GetUserProfileRequest;
import com.pypypraful.einvoicing.model.request.UpdateProductInCartRequest;
import com.pypypraful.einvoicing.model.request.UpdateSellerInventoryRequest;
import com.pypypraful.einvoicing.model.request.UpdateUserProfileRequest;
import com.pypypraful.einvoicing.model.request.WorkflowMetadataRequest;
import com.pypypraful.einvoicing.model.response.GetProductListResponse;
import com.pypypraful.einvoicing.model.response.GetUserProfileResponse;
import com.pypypraful.einvoicing.model.response.UpdateProductInCartResponse;
import com.pypypraful.einvoicing.model.response.WorkflowMetadataResponse;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBCustomerCart;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBOrder;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBSellerInventory;

import java.util.List;

public interface InventoryRepository {

    void updateSellerInventoryRecord(UpdateSellerInventoryRequest updateSellerInventoryRequest);

    GetProductListResponse getProductList(GetProductListRequest getProductListRequest);

    void updateUserProfileRecord(UpdateUserProfileRequest updateUserProfileRequest);

    GetUserProfileResponse getUserProfileRecord(GetUserProfileRequest getUserProfileRequest);

    UpdateProductInCartResponse updateCustomerCart(UpdateProductInCartRequest productInCartRequest);

    UpdateProductInCartResponse getCustomerCart(GetProductInCartRequest productInCartRequest);

    List<DBCustomerCart> getCustomerCartByCustomerId(String customerId);

    DBSellerInventory getSellerProductByProductId(String productId);

    void checkoutProductFromCustomerCart(DBSellerInventory dbSellerInventory, DBCustomerCart customerCart, String orderId);

    void discardProductFromCheckout(DBOrder dbOrder);

    WorkflowMetadataResponse saveWorkflowMetadata(WorkflowMetadataRequest workflowMetadataRequest);

    List<DBOrder> getDBOrderByOrderIdAndCustomerId(String customerId, String orderId);
}
