package com.pypypraful.einvoicing.model.request;

import com.pypypraful.einvoicing.model.common.SellerInventory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSellerInventoryRequest {
    private List<SellerInventory> sellerInventories;
}
