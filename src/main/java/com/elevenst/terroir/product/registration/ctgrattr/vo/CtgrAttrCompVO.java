package com.elevenst.terroir.product.registration.ctgrattr.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CtgrAttrCompVO implements Serializable {


    private static final long serialVersionUID = 6495806425317898777L;

    /**
     * 속성번호
     */
    private long prdAttrValueNo;

    /**
     * 상품번호
     */
    private long objPrdNo;

    /**
     * 상위속성번호
     */
    private long hgrnkAttrValNo;

    /**
     * 전시카테고리번호
     */
    private long dispCtgrNo;


    /**
     * 입력방식(01:선택형, 02:선택형+입력형, 03: 입력형(문자), 04:입력형(숫자), 05:복수선택형)
     */
    private String prdAttrEntyCd;


    /**
     * 속성 번호 PD_CTGR_ATTR.ATTR_NO
     */
    private long prdAttrNo;

    /**
     * 속성 코드
     */
    private String prdAttrCd;

    /**
     * 속성 명
     */
    private String prdAttrNm;

    /**
     * 속성값 코드
     */
    private String prdAttrValueCd;

    /**
     * 속성값 명
     */
    private String prdAttrValueNm;

    /**
     * 적용대상구분 코드
     */
    private String valueObjClfCd;

    /**
     * 등록자 구분
     */
    private String mnbdClfCd;

    private long createNo;

    private long updateNo;

    /**
     * 속성 타입 (null, 01): 기존속성, (02): 상품제공고시 속성
     */
    private String AttrType;

    /**
     * 상품정보제공 유형 카테고리 번호
     */
    private long infoTypeCtgrNo;

    /**
     * 중카테고리번호
     */
    private long dispCtgr2No;

    /**
     * 여행속성 여부
     */
    private String tourAttrYn;




}
