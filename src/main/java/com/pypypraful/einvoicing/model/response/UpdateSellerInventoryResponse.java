package com.pypypraful.einvoicing.model.response;

import com.pypypraful.einvoicing.model.common.SellerInventory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class UpdateSellerInventoryResponse {
    private List<SellerInventory> sellerInventories;
}
