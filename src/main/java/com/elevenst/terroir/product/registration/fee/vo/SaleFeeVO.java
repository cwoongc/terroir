package com.elevenst.terroir.product.registration.fee.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SaleFeeVO implements Serializable{
    private static final long serialVersionUID = 7980545111079989743L;

    private double addPrdFee;
    private String aplBgnDy;
    private String aplEndDy;
    private long dispCtgr2No;
    private double fee;
    private String feeAplUnitCd;
    private long prdNo;
    private String selMthdCd;
    private long memNo;
    private String selFeeAplObjClfCd;
}
