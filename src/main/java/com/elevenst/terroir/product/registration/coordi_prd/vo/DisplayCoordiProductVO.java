package com.elevenst.terroir.product.registration.coordi_prd.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 전시 코디상품
 */
@Data
public class DisplayCoordiProductVO implements Serializable {

    private static final long serialVersionUID = 354672571708915163L;

    /**
     * 전시 코디 상품 번호
     */
    private long prdNo;

    /**
     * 전시 코디 상품 명
     */
    private String prdNm;

    /**
     * 전시 코디 상품 판매가
     */
    private long selPrc;

    /**
     * 전시 코디 상품 최종구매가능가
     */
    private long finalDscPrc;

    /**
     * 전시 코디 상품 배송쿠폰코드
     */
    private String dlvCupnCd;

    /**
     * 전시 코디 상품 이미지 URL 02 (170*170)
     */
    private String imgUrl02;

    /**
     * 전시 코디 상품 판매가 - 최종구매가능가 상이 여부
     */
    private String dscYn;

}
