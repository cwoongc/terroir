package com.elevenst.terroir.product.registration.option.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdEventRqstCalc implements Serializable {
    private static final long serialVersionUID = -8629718745067299972L;

    private long eventNo;
    private long prdNo;
    private long optItemNo;
    private String optItemNm;
    private long optItemMinValue;
    private long optItemMaxValue;
    private double optUnitPrc;
    private String optUnitCd;
    private long optSelUnit;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
}
