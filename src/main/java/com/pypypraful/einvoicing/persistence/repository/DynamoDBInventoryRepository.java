package com.pypypraful.einvoicing.persistence.repository;

import com.pypypraful.einvoicing.model.request.GetInventoryForUserRequest;
import com.pypypraful.einvoicing.model.request.GetUserProfileRequest;
import com.pypypraful.einvoicing.model.request.UpdateInventoryForUserRequest;
import com.pypypraful.einvoicing.model.request.UpdateUserProfileRequest;
import com.pypypraful.einvoicing.model.response.GetInventoryForUserResponse;
import com.pypypraful.einvoicing.model.response.GetUserProfileResponse;
import com.pypypraful.einvoicing.persistence.dynamodb.InventoryDao;
import com.pypypraful.einvoicing.persistence.dynamodb.model.DBInventory;
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
    public void updateInventoryRecord(UpdateInventoryForUserRequest updateInventoryForUserRequest) {
        inventoryDao.persistData(inventoryDBAdapter.convertInventoryToDBModel(updateInventoryForUserRequest));
    }

    @Override
    public GetInventoryForUserResponse getInventoryRecord(GetInventoryForUserRequest getInventoryForUserRequest) {
        DBInventory dbInventory = inventoryDao.getData(inventoryDBAdapter.convertGetInventoryForUserRequestToDBModel(getInventoryForUserRequest));
        return inventoryDBAdapter.convertInventoryToUserResponse(dbInventory);
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
