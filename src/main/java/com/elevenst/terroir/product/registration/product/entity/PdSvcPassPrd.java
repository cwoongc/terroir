package com.elevenst.terroir.product.registration.product.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdSvcPassPrd implements Serializable {
    private static final long serialVersionUID = -6202345001617719454L;

    private long prdNo;
    private String svcTicktTypCd;
    private String svcPassPrdEftvPrdClfCd;
    private long eftvDdQty;
    private String eftvBgnDy;
    private String eftvEndDy;
    private long usePrc;
    private String mnfcCo;
    private String coUsePlc;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
}
