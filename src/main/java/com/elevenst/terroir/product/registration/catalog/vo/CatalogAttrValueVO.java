package com.elevenst.terroir.product.registration.catalog.vo;

import lombok.Data;

import java.util.List;

@Data
public class CatalogAttrValueVO {
    private long ctlgAttrValNo;
    private String ctlgAttrValNm;
    private String selectYn;


    /*
    PCFAV.CTLG_ATTR_VAL_NO AS ctlgAttrValNo ,
          NVL(PCFAV.FLTR_DISP_ATTR_NM, PCAV.CTLG_ATTR_VALUE_NM) AS ctlgAttrValNm

            (select decode(count(1),0,'N','Y') from PD_STD_PRD_ATTR_COMP
            where CTLG_ATTR_NO = PCAV.CTLG_ATTR_NO
            and CTLG_ATTR_VALUE_NO = PCAV.CTLG_ATTR_VALUE_NO
            and prd_no = #{prdNo} ) as prdSelectYn ,
            (select decode(count(1),0,'N','Y') from PD_CTLG_ATTR_COMP
            where CTLG_ATTR_NO = PCAV.CTLG_ATTR_NO
            and CTLG_ATTR_VALUE_NO = PCAV.CTLG_ATTR_VALUE_NO
            and prd_no = #{ctlgNo} ) as ctlgSelectYn
     */

}
