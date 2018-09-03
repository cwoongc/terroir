package com.elevenst.terroir.product.registration.common.vine.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductPriceV1 implements Serializable{

    private static final long serialVersionUID = 8411030651177467345L;

    private long dscAmt;
    private double dscRt;
    private String cupnDscMthdCd;
    private String cupnIssStartDy;		//쿠폰지급기간 시작일
    private String cupnIssEndDy;		//쿠폰지급기간 종료일
    private long cupnUseLmtQty;

    /* 필요없는 기존 로직
    private long prdPrcNo;
    private long prdNo;
    private long evntNo;
    private String aplEndDy;
    private String aplBgnDy;
    private long selPrc;
    private double cstmAplPrc;
    private long buylCnPrc;
    private long dscAmt;
    //private long dscRt;
    private double dscRt;
    private double mrgnRt;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
    private String cupnDscMthdCd;
    private String histAplEndDt;
    private long cupnUseLmtQty;
    private String useYn;
    // MO 즉시할인쿠폰 관련
    private long moDscAmt;
    private long moDscRt;
    private String moCupnDscMthdCd;

    //고객혜택 쿠폰부분 추가 ==================================
    private CouponBO directProdCurrCoupon;
    private String cupnIssStartDy;		//쿠폰지급기간 시작일
    private String cupnIssEndDy;		//쿠폰지급기간 종료일

    //매입상품 부분 추가 ==================================
    private String mrgnPolicyCd;	// 마진정책코드
    private long puchPrc;			// 매입가
    private long mrgnAmt;			// 마진금액

    // 최초판매가 추가 (2010.08.20)
    private long initSelPrc;

    // 시중가
    private long maktPrc;

    private Date histAplBgnDt;		// 이력적용시작일시
    private Date histAplEndDtD;		// 이력적용종료일시
    private String memId;

    //해외 명품 직매입
    private double frSelPrc;	  	//현지 판매가
    private String currencyCd;		//통화

    //중고상품
    private long paidSelPrc;			// 구입당시 판매가

    private String prdUpdateDtYn = "N";			// PD_PRD의 UPDATE_DT = SYSDATE 업데이트 여부

    // 휴대폰 가격표시제 시행을 위한 가격 표기 추가
    private long	prmtDscAmt;								// 프로모션 할인
    private String mpContractTypCd; //휴대폰 약정유형 코드.
    */
}
