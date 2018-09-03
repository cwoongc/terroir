package com.elevenst.terroir.product.registration.ctgrattr.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CtgrAttrVO implements Serializable{
    private static final long serialVersionUID = 5970025563733132916L;

    /**
     * 전시속성번호(PD_CTGR_ATTR.ATTR_NO)
     */
    private long prdAttrNo;

    /**
     * 상위전시속성번호(PD_CTGR_ATTR.HGRNK_ATTR_NO)
     */
    private long hgrnkAttrNo;

    /**
     * 전시속성코드(PD_CTGR_ATTR.ATTR_CD)
     */
    private String prdAttrValueCd;

    /**
     * 마스터상품 필수 속성여부(PD_CTGR_ATTR.MSTR_PRD_KEY_ATTR_YN)
     */
    private String keyAttrYn;

    /**
     * 속성값 입력방식 코드 (PD025, PD_CTGR_ATTR.ATTR_ENT_WY_CD)
     */
    private String attrEntWyCd;

    /**
     * 속성단위코드 (PD045, PD_CTGR_ATTR.ATTR_UNIT_CD)
     */
    private String prdAttrUnitCd;

    /**
     * 속성값 멀티입력 가능여부 (PD_CTGR_ATTR.PLNO_SLCT_CN_YN)
     */
    private String plnoSlctCnYn;

    /**
     * 속성값 이미지 적용여부 (PD_CTGR_ATTR.ATTR_VALUE_IMG_APL_YN )
     */
    private String attrValImgAplYn;

    /**
     * 전시속성코드 (PD_CTGR_ATTR.ATTR_CD 에 해당하는 속성명(PD_PRD_ATTR_VALUE_CD.PRD_ATTR_VALUE_NM)
     */
    private String prdAttrValueNm;

    /**
     * 상품 속성값명 (PD_PRD_ATTR_COMP.PRD_ATTR_VALUE_NM OR PD_PRD_ATTR_COMP_TXT.PRD_ATTR_VALUE_NM )
     */
    private String prdAttrValueCompValueNm;

    /**
     * 상품 속성값 코드 (PD_PRD_ATTR_COMP.PRD_ATTR_VALUE_CD)
     */
    private String prdAttrValueCompValueCd;

    /**
     * 전시카테고리번호 (PD_CTGR_ATTR.DISP_CTGR_NO)
     */
    private long dispCtgrNo;

    /**
     * 속성 전시우선순(PD_CTGR_ATTR.DISP_PRRT_RNK)
     */
    private long dispPrrtRnk;

    /**
     * 전시카테고리 종류코드 (전시(01), 프로모션(02), 브랜드(03) (DI135)  ,  DP_DISP_CTGR.DISP_CTGR_KIND_CD)
     */
    private String dispCtgrKindCd;

    /**
     * 전시카테고리 유형코드 (DI013) ,  DP_DISP_CTGR.DISP_CTGR_TYP_CD )
     */
    private String dispCtgrTypCd;

    /**
     * 상품속성타입 (상품속성 타입 (NULL, 01) 기준속성, (02) 상품정보고시 직접입력 속성 , PD_PRD_ATTR_VALUE_CD.ATTR_TYPE)
     */
    private String attrType;

    /**
     * 속성설명 (PD_PRD_ATTR_VALUE_CD.ATTR_DESC)
     */
    private String attrDesc;

    /**
     * 기준속성여부 (PD_PRD_ATTR_VALUE_CD.CM_CD_YN )
     */
     private String cmCdYn;

    /**
     *
     */
     private String svcTypCd;

    /**
     *
     */
    private String expWyCd;

    /**
     *
     */
    private String hgrnkAttrNoValue;

    /**
     *
     */
    private String hgrnkAttrNoValueCd;

    /**
     *
     */
    private long hgrnkAttrValueNo;

    /**
     * PD_CTGR_ATTR.SELLER_PRD_KEY_ATTR_YN
     */
    private String sellerPrdKeyAttrYn;

    private long objPrdNo;



    /**
     * 속성의 세팅 가능한 속성값 목록
     */
    private List<CtgrAttrValueVO> ctgrAttrValueVOList;
}
