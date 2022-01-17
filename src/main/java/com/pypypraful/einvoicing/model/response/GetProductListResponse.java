package com.pypypraful.einvoicing.model.response;

import com.pypypraful.einvoicing.model.common.SellerInventory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetProductListResponse {
    private List<SellerInventory> productList;
}
