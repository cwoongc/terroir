package com.elevenst.terroir.product.registration.ctgrattr.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 상품 속성 정보  (PD_PRD_ATTR_COMP)
 */
@Data
public class PdPrdAttrComp implements Serializable {


    private static final long serialVersionUID = -3051847635406836030L;
    /**
     * 상품번호
     */
    private long objPrdNo;

    /**
     * 속성 코드
     */
    private String prdAttrCd;

    /**
     * 속성값 코드
     */
    private String prdAttrValueCd;

    /**
     * 적용대상구분코드(01:일반상품,02:마스터상품,03:물품대장)
     */
    private String objClfCd;

    /**
     * 상품 속성값명
     */
    private String prdAttrValueNm;

    /**
     * 상품속성이미지번호
     */
    private long prdImgNo;

    private long createNo;

    private long updateNo;


}
