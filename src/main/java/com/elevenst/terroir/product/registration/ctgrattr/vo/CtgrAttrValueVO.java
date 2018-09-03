package com.elevenst.terroir.product.registration.ctgrattr.vo;

import lombok.Data;

@Data
public class CtgrAttrValueVO {
    private long prdAttrNo;
    private long uprdAttrNo;
    private long prdAttrValueNo;
    private String hgrnkAttrValNo = "0";
    private String hgrnkAttrValNm;
    private String prdAttrValueCd;
    private String prdAttrValueNm;
    private String valueMnbdNo;
    private String valueMnbdClfCd;
    private String useYn;
    private String attrSrchSvcAplYn;
    private String createDt;
    private String createNo;
    private String updateDt;
    private String updateNo;
    private String selectYn;
    private long objPrdNo;
    private String valueObjClfCd;
    private long totalCount = 0;
    private long dupCount;
    private String attrEntWyCd;
    private String DispCtgrNo;
}
