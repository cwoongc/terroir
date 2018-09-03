package com.elevenst.terroir.product.registration.option.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdStockSetInfo implements Serializable {
    private static final long serialVersionUID = 6085534272322383420L;

    private long mstPrdNo;
    private long mstStckNo;
    private long compPrdNo;
    private long compStckNo;
    private long setDscRt;
    private long setUnitQty;
    private String setTypCd;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
}
