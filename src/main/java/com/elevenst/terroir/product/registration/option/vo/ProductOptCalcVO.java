package com.elevenst.terroir.product.registration.option.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProductOptCalcVO implements Serializable {
    private static final long serialVersionUID = -8427396304072801031L;

    private long eventNo;
    private long prdNo;
    private long optItemNo;
    private String optItemNm;
    private long optItemMinValue;
    private long optItemMaxValue;
    private long optUnitPrc;
    private String optUnitCd;
    private long optSelUnit;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;

    private long optSeq;
    private String optNm;
    private String optMinVal;
    private String optMaxVal;
    private long optUnitLen;

    private String useOptCalc;
    private String optCalcTranType;
    private String optItem1Nm;
    private long optItem1MinValue;
    private long optItem1MaxValue;
    private String optItem2Nm;
    private long optItem2MinValue;
    private long optItem2MaxValue;
}
