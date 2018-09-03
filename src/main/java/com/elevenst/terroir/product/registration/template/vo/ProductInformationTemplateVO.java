package com.elevenst.terroir.product.registration.template.vo;

import lombok.Data;

@Data
public class ProductInformationTemplateVO {

    private long prdInfoTmpltNo;
    private String prdInfoTmpltClfCd;
    private long memNo;
    private String addrBasiDlvCstTxt;

    SellInformationVO sellInformationVO;				//판매정보템플릿(PD_SEL_INFO)
    PrdInfoTmplVO prdInfoTmplVO;
    InventoryVO inventoryVO;
}
