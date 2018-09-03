package com.elevenst.terroir.product.registration.deal.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShockDealStateVO implements Serializable {
    private static final long serialVersionUID = -5085589455604946732L;

    private boolean updableAplInfo;		// 쇼킹딜 신청정보 수정가능여부
    private boolean viewableRqstPrdInfo;	// 쇼킹딜 상품정보 보기 가능여부
    private boolean updablePrdInfo;		// 쇼킹딜 상품정보 수정가능여부
    private boolean viewableOrgPrdInfo;		//	쇼킹딜 종료 후 상품정보 보기 가능여부
    private boolean viewableSelStatCd;		// 쇼킹딜 종료 후 판매상태 보기 가능여부
    private boolean updableSelStatCd;	// 쇼킹딜 종료 후 판매상태 수정가능여부
    private boolean updableSelPrc;		// 상품정보 판매가 수정가능여부
    private boolean updableDscAmtPercnt;	// 상품정보 기본즉시할인 수정가능여부
    private boolean updableDispCtgrNo;	// 상품정보 카테고리 수정가능여부
    private boolean updableDlvCst;		// 상품정보 배송비 수정가능여부
    private boolean updablePrdNm;		// 상품정보 상품명 수정가능여부
    private boolean updablePrdImg;		// 상품정보 상품이미지 수정가능여부
}
