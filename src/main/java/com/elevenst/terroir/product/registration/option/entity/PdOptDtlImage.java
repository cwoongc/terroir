package com.elevenst.terroir.product.registration.option.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdOptDtlImage implements Serializable {

    private static final long serialVersionUID = -4944192736975746234L;
    private long eventNo;
    private long prdNo;
    private long optItemNo;
    private long optValueNo;
    private String optValueNm;
    private String dtl1ExtNm;
    private String dtl2ExtNm;
    private String dtl3ExtNm;
    private String dtl4ExtNm;
    private String dtl5ExtNm;
    private String dgstExtNm;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
}
