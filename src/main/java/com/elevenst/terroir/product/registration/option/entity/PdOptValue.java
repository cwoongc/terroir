package com.elevenst.terroir.product.registration.option.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdOptValue implements Serializable {
    private static final long serialVersionUID = -4510590210506876740L;

    private long prdNo;
    private long optItemNo;
    private long optValueNo;
    private String optValueNm;
    private String prdStckStatCd;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
    private long addPrc;
    private long regRnk;
    private String dgstExtNm;
    private long optValueSeq;
    private long stdOptNo;
    private long stdOptValNo;
}
