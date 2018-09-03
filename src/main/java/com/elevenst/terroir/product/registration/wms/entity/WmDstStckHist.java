package com.elevenst.terroir.product.registration.wms.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WmDstStckHist implements Serializable {
    private static final long serialVersionUID = 4415369593692852293L;

    private long stckNo;
    private Date histDt;
    private long prdNo;
    private String dstBarcdNo;
    private long boxUntgdsCunt;
    private String boxWhoutCanYn;
    private String boxBarcdNo;
    private long wdthLen;
    private long vrtclLen;
    private long hghtLen;
    private long stckWght;
    private long maxLoadFlrCunt;
    private long maxLoadHghtLen;
    private long maxPlltBoxCunt;
    private String dstStckStatChgYn;
    private String dstStckStatCd;
    private String chgCont;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
    private String boxUntMngYn;
    private String volMngYn;
}
