package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ListingItemVO implements Serializable{

    private static final long serialVersionUID = -8073104819744147926L;
    private String itemNo;              //화면에서 받음
    private String svcBgnDy;            //화면에서 받음
    private String svcEndDy;            //화면에서 받음
    private String displayTermClfCd;    //화면에서 받음

    private String itemNos;
    private String feeItemNo;
    private String prdItmNo;
    private String itemFee;
    private String orgSvcFee;
    private String dscCouponAmountTot;

    private String addExtendYn;
    private String premiumYn;
    private String gifYn;
    private String dispItmTypCd;
    private String expiredPrdItmNo;
    private String expiredDispItmNo;

    private String[] couponNos;
    private String[] couponQty;
    private String[] dscCouponAmount;

}
