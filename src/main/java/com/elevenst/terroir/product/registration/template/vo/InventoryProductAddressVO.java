package com.elevenst.terroir.product.registration.template.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class InventoryProductAddressVO implements Serializable {
    private long prdLstAddrNo;
    private long prdAddrObjNo;
    private String prdAddrObjClfCd;
    private long memNo;
    private long addrSeq;
    private String addrNm;
    private String prdAddrClfCd;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
    private String addr;
    private long itgAddrSeq;	// 통합출고지,반품교환지를 묶기 위한 출고지 순번
    private String freePickupYn;	// 무료픽업 대상 여부

    //주소체계변경
    private String mbAddrLocation;
}
