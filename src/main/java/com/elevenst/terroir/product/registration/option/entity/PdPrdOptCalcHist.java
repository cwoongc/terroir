package com.elevenst.terroir.product.registration.option.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdPrdOptCalcHist implements Serializable {
    private static final long serialVersionUID = 3121767553196262767L;

    private long prdNo;
    private long optItemNo;
    private Date histAplDt;
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
}
