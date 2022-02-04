package com.pypypraful.einvoicing.persistence.repository;

import com.pypypraful.einvoicing.model.enums.OrderStatus;
import com.pypypraful.einvoicing.model.request.GetNearestSellersProfileRequest;
import com.pypypraful.einvoicing.model.request.GetProductInCartRequest;
import com.pypypraful.einvoicing.model.request.GetProductListRequest;
import com.pypypraful.einvoicing.model.request.GetUserProfileRequest;
import com.pypypraful.einvoicing.model.request.UpdateProductInCartRequest;
import com.pypypraful.einvoicing.model.request.UpdateSellerInventoryRequest;
import com.pypypraful.einvoicing.model.request.UpdateUserProfileRequest;
import com.pypypraful.einvoicing.model.request.WorkflowMetadataRequest;
import com.pypypraful.einvoicing.model.response.GetNearestSellersProfileResponse;
import com.pypypraful.einvoicing.model.response.GetProductListResponse;
import com.pypypraful.einvoicing.model.response.UpdateProductInCartResponse;
import com.pypypraful.einvoicing.model.response.UpdateUserProfileResponse;
import com.pypypraful.einvoicing.model.response.WorkflowMetadataResponse;
import com.pypypraful.einvoicing.persistence.dynamodb.InventoryDao;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBCustomerCart;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBOrder;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBSellerInventory;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBUserProfile;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBWorkflowMetadata;
import com.pypypraful.einvoicing.persistence.dynamodb.model.adapter.InventoryDBAdapter;
import com.pypypraful.einvoicing.persistence.dynamodb.model.adapter.UserProfileDBAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.pypypraful.einvoicing.persistence.dynamodb.model.adapter.CustomerCartDBAdapter.getCartProductsFromDBCustomerCart;

public class DynamoDBInventoryRepository implements InventoryRepository {

    private final InventoryDao inventoryDao;
    private final InventoryDBAdapter inventoryDBAdapter;
    private List<DBUserProfile> dbUserProfiles;

    public DynamoDBInventoryRepository(){
        inventoryDao = new InventoryDao();
        inventoryDBAdapter = new InventoryDBAdapter();
    }

    @Override
    public void updateSellerInventoryRecord(UpdateSellerInventoryRequest updateSellerInventoryRequest) {
        inventoryDao.saveSellerInventory(inventoryDBAdapter.convertSellerInventoryToDBModel(updateSellerInventoryRequest));
    }

    @Override
    public GetProductListResponse getProductList(GetProductListRequest getProductListRequest) {
        List<DBSellerInventory> dbSellerInventory = inventoryDao.getSellerInventoryByUsername(
                getProductListRequest.getUsername());
        return inventoryDBAdapter.convertDBSellerInventoryToProductListResponse(dbSellerInventory);
    }

    @Override
    public void updateUserProfileRecord(UpdateUserProfileRequest updateUserProfileRequest) {
        inventoryDao.updateUserProfileInDB(UserProfileDBAdapter.convertUserProfileToDBModel(updateUserProfileRequest));
    }

    @Override
    public UpdateUserProfileResponse getUserProfileRecord(GetUserProfileRequest getUserProfileRequest) {
        dbUserProfiles = inventoryDao.getUserProfileByUsernameFromDB(
                    getUserProfileRequest.getUsername(), getUserProfileRequest.getProfileType());
        return UserProfileDBAdapter.convertDBUserProfileToUserProfileResponse(dbUserProfiles);
    }

    @Override
    public GetNearestSellersProfileResponse getSellersProfile(GetNearestSellersProfileRequest getNearestSellersProfileRequest) {
        dbUserProfiles = inventoryDao.getUserProfilesByPincodeFromDB(
                getNearestSellersProfileRequest.getPincode(), getNearestSellersProfileRequest.getProfileType());
        return UserProfileDBAdapter.convertDBUserProfileToSellersProfileResponse(dbUserProfiles);
    }

    @Override
    public UpdateProductInCartResponse getCustomerCart(GetProductInCartRequest productInCartRequest) {
        List<DBCustomerCart> dbCustomerCartList =
                inventoryDao.getCustomerCartByCustomerId(productInCartRequest.getCustomerId());
        List<Object> dbSellerInventoryList = inventoryDao.getSellerProductByMultipleProductIds(
                getAllProductIdsOfProductsInCart(dbCustomerCartList));
        syncCustomerCartWithSellerInventory(dbCustomerCartList, dbSellerInventoryList);
        return UpdateProductInCartResponse.builder()
                .customerId(productInCartRequest.getCustomerId())
                .products(getCartProductsFromDBCustomerCart(dbCustomerCartList, dbSellerInventoryList))
                .build();
    }

    @Override
    public List<DBCustomerCart> getCustomerCartByCustomerId(String customerId) {
        return inventoryDao.getCustomerCartByCustomerId(customerId);
    }

    @Override
    public DBSellerInventory getSellerProductByProductId(String productId) {
        return inventoryDao.getSellerProductByProductId(productId).get(0);
    }

    @Override
    public UpdateProductInCartResponse updateCustomerCart(UpdateProductInCartRequest productInCartRequest) {
        List<DBCustomerCart> dbCustomerCartList =
                syncDBCustomerCartWithNewCartRequest(
                        inventoryDao.getCustomerCartByCustomerId(productInCartRequest.getCustomerId()),
                        productInCartRequest
                );
        List<Object> dbSellerInventoryList = inventoryDao.getSellerProductByMultipleProductIds(
                getAllProductIdsOfProductsInCart(dbCustomerCartList));
        syncCustomerCartWithSellerInventory(dbCustomerCartList, dbSellerInventoryList);
        return UpdateProductInCartResponse.builder()
                .customerId(productInCartRequest.getCustomerId())
                .products(getCartProductsFromDBCustomerCart(dbCustomerCartList, dbSellerInventoryList))
                .build();
    }

    @Override
    public void checkoutProductFromCustomerCart(DBSellerInventory dbSellerInventory,
                                                DBCustomerCart customerCart, String orderId) {
        dbSellerInventory.setProductQuantity(dbSellerInventory.getProductQuantity() - customerCart.getQuantity());
        DBOrder dbOrder = DBOrder.builder()
                .sellerId(dbSellerInventory.getUsername())
                .customerId(customerCart.getCustomerId())
                .productId(dbSellerInventory.getProductId())
                .price(dbSellerInventory.getProductPrice())
                .orderId(orderId)
                .orderStatus(OrderStatus.CHECKOUT.toString())
                .quantity(customerCart.getQuantity())
                .build();
        inventoryDao.removeProductFromSellerInventoryAndAddToOrder(dbOrder);
    }

    @Override
    public void updateOrderStatus(OrderStatus orderStatus, String orderId) {
        inventoryDao.updateOrderStatus(DBOrder.builder()
                .orderStatus(orderStatus.toString())
                .orderId(orderId)
                .build());
    }

    @Override
    public void discardProductFromCheckout(DBOrder dbOrder) {
        inventoryDao.removeProductFromOrderAndAddToInventory(dbOrder);
    }

    @Override
    public WorkflowMetadataResponse saveWorkflowMetadata(WorkflowMetadataRequest workflowMetadataRequest) {
        DBWorkflowMetadata dbWorkflowMetadata =
                DBWorkflowMetadata.builder()
                        .workflowType(workflowMetadataRequest.getWorkflowType())
                        .executionArn(workflowMetadataRequest.getExecutionArn())
                        .executionName(workflowMetadataRequest.getExecutionName())
                        .status(workflowMetadataRequest.getStatus())
                        .customerId(workflowMetadataRequest.getCustomerId())
                        .stepTaskToken(workflowMetadataRequest.getStepTaskToken())
                        .build();
        inventoryDao.storeWorkflowMetadata(dbWorkflowMetadata);
        return WorkflowMetadataResponse.builder()
                .customerId(workflowMetadataRequest.getCustomerId())
                .workflowType(workflowMetadataRequest.getWorkflowType())
                .executionName(workflowMetadataRequest.getExecutionName())
                .executionArn(workflowMetadataRequest.getExecutionArn())
                .status(workflowMetadataRequest.getStatus())
                .build();
    }

    @Override
    public WorkflowMetadataResponse getWorkflowMetadata(String executionName) {
        DBWorkflowMetadata dbWorkflowMetadata =
                inventoryDao.getWorkflowMetadata(executionName).get(0);
        return WorkflowMetadataResponse.builder()
                .customerId(dbWorkflowMetadata.getCustomerId())
                .workflowType(dbWorkflowMetadata.getWorkflowType())
                .executionName(dbWorkflowMetadata.getExecutionName())
                .executionArn(dbWorkflowMetadata.getExecutionArn())
                .stepTaskToken(dbWorkflowMetadata.getStepTaskToken())
                .status(dbWorkflowMetadata.getStatus())
                .build();
    }

    @Override
    public List<DBOrder> getDBOrderByOrderIdAndCustomerId(String customerId, String orderId) {
        return inventoryDao.getDBOrderByOrderIdAndCustomerId(orderId, customerId);
    }

    private void syncCustomerCartWithSellerInventory(
            List<DBCustomerCart> dbCustomerCartList, List<Object> dbSellerInventoryList) {
        if (dbCustomerCartList.size() == 0) return;
        for (Object dbSellerInventory : dbSellerInventoryList) {
            for (DBCustomerCart dbCustomerCart: dbCustomerCartList) {
                if (((DBSellerInventory) dbSellerInventory).productId.equals(dbCustomerCart.getProductId())
                        && ((DBSellerInventory) dbSellerInventory).productQuantity < dbCustomerCart.quantity) {
                    dbCustomerCart.quantity = ((DBSellerInventory) dbSellerInventory).productQuantity;
                }
            }
        }
        inventoryDao.updateMultipleProductsInCustomerCart(dbCustomerCartList);
    }

    private List<DBCustomerCart> syncDBCustomerCartWithNewCartRequest(
            List<DBCustomerCart> dbCustomerCart, UpdateProductInCartRequest productInCartRequest) {
        List<DBCustomerCart> updatedDBCustomerCart = new ArrayList<>();
        boolean IsProductAlreadyPresentInDBCart = false;
        for (DBCustomerCart customerCart : dbCustomerCart) {
            if (customerCart.getProductId().equals(productInCartRequest.getProductId())) {
                customerCart.setQuantity(productInCartRequest.getQuantity());
                IsProductAlreadyPresentInDBCart = true;
            }
            updatedDBCustomerCart.add(customerCart);
        }
        if (!IsProductAlreadyPresentInDBCart)
            updatedDBCustomerCart.add(
                    DBCustomerCart.builder()
                            .customerId(productInCartRequest.getCustomerId())
                            .productId(productInCartRequest.getProductId())
                            .quantity(productInCartRequest.getQuantity())
                            .build());
        return updatedDBCustomerCart;
    }

    private List<String> getAllProductIdsOfProductsInCart(List<DBCustomerCart> dbCustomerCartList) {
        List<String> productIds = new ArrayList<>();
        for (DBCustomerCart dbCustomerCart : dbCustomerCartList) {
            productIds.add(dbCustomerCart.getProductId());
        }
        return productIds;
    }
}
