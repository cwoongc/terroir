package com.elevenst.terroir.product.registration.category.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DpGnrlDispVO implements Serializable {
    private static final long serialVersionUID = -1730002044764278408L;

    private long dispCtgrNo;
    private long prdNo;
    private String dispBgnDy;
    private String dispEndDy;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
    private String mvCtgrYn;       //카테고리 이동여부

    private String dispCtgrNm;  //카테고리명 추가

    private String selStatCd;
    private String selMthdCd;
    private long selMnbdNo;
    private long stckQty = -1;
    private String optCd;
    private long addPrdQty = -1;
    private String dlvCstPayTypCd;
    private String dlvCstInstBasiCd;
    private String minorSelCnYn;
    private String brandCd;
    private long increaseQty;
    private String disableUpdateYn;
    private String ctgrNoPath;

    private String bsnDealClf; // 거래유형
    private String dlvClf; // 거래유형

    private String omPrdYn;				// OM상품여부
    private String svcAreaCd;		// 서비스영역코드

    private String hideYn;		// 리스트 노출여부

    private String gblDlvYn;		//전세계배송여부  - 2012.05.16
    private String engDispYn;   //11번가 영문 노출 여부.

    private String setTypCd;	// 상품 구성
    private boolean isSellableBundlePrdSeller;	// 직매입 11번가배송 관리자 셀러(묶음상품 가능한 셀러)
}
