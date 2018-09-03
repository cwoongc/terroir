package com.elevenst.terroir.product.registration.catalog.vo;

import lombok.Data;

import java.util.List;

@Data
public class CatalogAttrVO {
    private long dispCtgrNo;
    private long ctlgAttrNo;
    private String ctlgAttrNm;
    private String useYn;
    private String inputMthdCd;
    private String requiredYn;
    private String attrClfCd;
    private long ctlgAttrValueNo;
    private String stdAttrValueNm;
    private List<CatalogAttrValueVO> catalogAttrValueVOList;
    /*
    ATTR.DISP_CTGR_NO as "dispCtgrNo",
            ATTR.CTLG_ATTR_NO as "attrNo",
            NVL(ATTR.FLTR_DISP_ATTR_NM, CTAT.CTLG_ATTR_NM) AS "attrNm",
            ATTR.USE_YN as "useYn",
            ATTR.INPUT_MTHD_CD AS "inputMthdCd",
            ATTR.NESS_ATTR_YN as "reqiredYn"
     */



}
