package com.pypypraful.einvoicing.persistence.repository;

import com.pypypraful.einvoicing.model.request.GetProductListRequest;
import com.pypypraful.einvoicing.model.request.GetUserProfileRequest;
import com.pypypraful.einvoicing.model.request.UpdateSellerInventoryRequest;
import com.pypypraful.einvoicing.model.request.UpdateUserProfileRequest;
import com.pypypraful.einvoicing.model.response.GetProductListResponse;
import com.pypypraful.einvoicing.model.response.GetUserProfileResponse;
import com.pypypraful.einvoicing.persistence.dynamodb.InventoryDao;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBSellerInventory;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBUserProfile;
import com.pypypraful.einvoicing.persistence.dynamodb.model.adapter.InventoryDBAdapter;
import com.pypypraful.einvoicing.persistence.dynamodb.model.adapter.UserProfileDBAdapter;

import java.util.List;

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
}
