package com.elevenst.terroir.product.registration.template.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class InventoryVO implements Serializable {
    private List inventoryOrdBasiDeliCostVOList = new ArrayList();                  // 배송비 목록
    private List inventoryProductDescVOList = new ArrayList();                      // 상세설명 목록
    private List inventoryProductAddressVOList = new ArrayList();                   // 출고지/배송지 주소 목록
}
