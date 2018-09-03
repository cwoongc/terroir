package com.elevenst.terroir.product.registration.shop.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 상품 지점 연결
 * [PD_TOWN_SHOP_BRANCH_COMP]
 */
@Data
public class PdTownShopBranchComp implements Serializable{

    /**
     * 상품번호
     */
    private long prdNo;

    /**
     * 상점번호
     */
    private long shopNo;

    /**
     * 지점번호
     */
    private long shopBranchNo;


    public PdTownShopBranchComp() {
    }

    public PdTownShopBranchComp(long prdNo, long shopNo, long shopBranchNo) {
        this.prdNo = prdNo;
        this.shopNo = shopNo;
        this.shopBranchNo = shopBranchNo;
    }
}
