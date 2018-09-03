package com.elevenst.terroir.product.registration.benefit.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerBenefitVO {

    //PD_CUST_BNFT_INFO 테이블 프로퍼티
    private long 	custBnftNo;
    private long 	prdNo;
    private Date    aplBgnDy;
    private Date 	aplEndDy;
    private long 	mallPntRt;
    private long 	mallPnt;
    private long 	spplChpQty;
    private String 	chpSpplMthdCd;
    private String 	dlvCstFrYn = "N";       //배송비 무료 여부
    private String 	custBnftLttrYn = "N";   //이벤트 추첨 여부
    private Date 	createDt;
    private long 	createNo;
    private Date 	updateDt;
    private long 	updateNo;
    private long 	pntSpplLmtQty;
    private long 	pntSpplQty;
    private long 	chpSpplLmtQty;
    private long 	chpSpplQty;
    private String 	intfreeMonClfCd;        //무이자할무 코드(PD008)
    private long 	intfreeAplLmtQty;
    private long 	intfreeAplQty;
    private String 	dblPntSpplYn = "N";     //더블포인트 지급여부
    private String 	spplWyCd;
    private long 	evntNo;
    private String 	chpKdCd;
    private String 	useYn;
    private long 	ocbPntRt;
    private long 	ocbPnt;
    private long 	ocbSpplLmtQty;
    private long 	ocbSpplQty;
    private String 	ocbWyCd;
    private long 	mileagePntRt;
    private long 	mileagePnt;
    private long 	mileageSpplLmtQty;
    private long 	mileageSpplQty;
    private String 	mileageWyCd;
    private String 	pntDfrmMnbdClfCd;
    private long 	hopeShpPntRt;
    private long 	hopeShpPnt;
    private long 	hopeShpSpplLmtQty;
    private long 	hopeShpSpplQty;
    private String 	hopeShpWyCd;
    private long 	bnftSelMnbdNo;


    //PD_PRD_PLU_DSC 테이블 프로퍼티
//    private long 	PluDscNo;
//    private String 	PluTypCd;
//    private String 	PluDscAplYn;    //복수구매 할인 유무
//    private Date 	PluAplBgnDy;
//    private Date 	PluAplEndDy;
//    private String 	PluDscCd;
//    private long 	PluDscBasis;
//    private String 	PluDscWyCd;
//    private long 	PluDscRt;
//    private long 	PluDscAmt;
//    private long 	SelMnbdNo;


    //혜택설정을 위해 convert영역에서 세팅해 주는 플래그 값들
    private String pointYn;
    private String chipYn;
    private String intFreeYn;
    private String ocbYn;
    private String mileageYn;
    private String hopeShpYn;

    //복구구매할인
    private long    pluDscNo;				// 복수구매 시퀀스번호
    private String	pluYN = "N";						// 복수구매 여부
    private String  pluDscCd;							// 할인구분코드 01:수량 ,02:금액
    private String  pluDscBasis;                        // 복수구매 단위
    private long	pluDscAmtPercnt;					// 복구구매할인금액(률)
    private long	pluDscAmt;							// 복구구매할인금액
    private long	pluDscPercnt;						// 복구구매할인률
    private String	pluDscMthdCd;						// 단위 원 또는 %
    private String	pluUseLmtDyYn = "N";				// 할인기간 설정여부
    private String	pluIssStartDy;						// 할인 시작일
    private String	pluIssEndDy;						// 할인 종료일
    private int     pluTotCnt;				// 복수구매할인 기준 건수
}
