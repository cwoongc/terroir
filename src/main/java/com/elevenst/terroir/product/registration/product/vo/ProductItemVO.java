package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductItemVO implements Serializable{
    private static final long serialVersionUID = 3956470653243013357L;

    private long prdItmNo = 0;
    private long dispItmNo = 0;             //화면에서 받음
    private String svcBgnDy = "";           //화면에서 받음
    private String svcEndDy = "";           //화면에서 받음
    private String useYn = "";
    private String displayTermClfCd = "";   //화면에서 받음
    private long itmSelMnbdNo = 0;
    private String itmPremiumYn = "";       //화면에서 받음
    private String cupnIssCnYn = "";        //화면에서 받음

    private String aplBgnDt;
    private String aplEndDt;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
    private long feeItmNo;
    private long svcFee;
    private long orgSvcFee;
    private List feeStlObjList;
    private long dscCouponIssNo;
    private long dscCouponAmount;
    private String histAplEndDt;
    private String svcEndHH24;
    private String feeResultYn;     //수수료 결제시 create일경우 결제를 할지 않할지 여부
    private String feeAddResultYn;  //아이템 부가수수료 결제시 추가로 결제할경우 추가 결제 여부

    //전시아이템 히스토리 정보
    private String dispItmNm;
    private String svcDate;

    //전시아이템개편 수정
    private String displayTermClfNm;

    private long displayTerm;
    private String selMthdCd;
    private String selMthdNm;
    private String oldDispItemYn;
    private long feeItemNo;
    private long dispFee;
    private long newFeeItmNo;
    private String addExtendYn; // 전시아아템추가연장여부 - 전시아이템 적용전 : N(변경/등록), 전시아이템적용기간 : Y(연장)
    private long itmRemain; // 전시아이템 전시기간 남은 일수

    private String dispItmTypCd;
    private String dispItmTypNm;

    private String premiumYn;
    private String gifYn;
    private String expiredYn;
}
