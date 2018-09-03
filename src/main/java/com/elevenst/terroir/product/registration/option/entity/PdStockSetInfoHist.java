package com.elevenst.terroir.product.registration.option.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdStockSetInfoHist implements Serializable {
    private static final long serialVersionUID = -5460674224272630496L;

    private long mstPrdNo;
    private long mstStckNo;
    private long compPrdNo;
    private long compStckNo;
    private Date histAplDt;
    private String chgTypCd;
    private long setDscRt;
    private long setUnitQty;
    private String setTypCd;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
}
