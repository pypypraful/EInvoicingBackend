package com.pypypraful.einvoicing.persistence.repository;

import com.pypypraful.einvoicing.model.request.*;
import com.pypypraful.einvoicing.model.response.GetProductListResponse;
import com.pypypraful.einvoicing.model.response.GetUserProfileResponse;
import com.pypypraful.einvoicing.model.response.UpdateProductInCartResponse;
import com.pypypraful.einvoicing.persistence.dynamodb.InventoryDao;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBCustomerCart;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBSellerInventory;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBUserProfile;
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
    public GetUserProfileResponse getUserProfileRecord(GetUserProfileRequest getUserProfileRequest) {
        if (getUserProfileRequest.getUsername() != null)
            dbUserProfiles = inventoryDao.getUserProfileByUsernameFromDB(
                    getUserProfileRequest.getUsername(), getUserProfileRequest.getProfileType());
        else if (getUserProfileRequest.getPincode() != null)
            dbUserProfiles = inventoryDao.getUserProfilesByPincodeFromDB(
                    getUserProfileRequest.getPincode(), getUserProfileRequest.getProfileType());
        return UserProfileDBAdapter.convertDBUserProfileToUserProfileResponse(dbUserProfiles);
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
        if (dbCustomerCart.size() == 0) {
            updatedDBCustomerCart.add(
                    DBCustomerCart.builder()
                            .customerId(productInCartRequest.getCustomerId())
                            .productId(productInCartRequest.getProductId())
                            .quantity(productInCartRequest.getQuantity())
                            .build()
            );
            return updatedDBCustomerCart;
        }
        for (DBCustomerCart customerCart : dbCustomerCart) {
            if (customerCart.getProductId().equals(productInCartRequest.getProductId())) {
                customerCart.setQuantity(productInCartRequest.getQuantity());
            }
            updatedDBCustomerCart.add(customerCart);
        }
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
