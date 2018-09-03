package com.elevenst.terroir.product.registration.wms.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WmDstStck implements Serializable {
    private static final long serialVersionUID = -3639177317516470115L;

    private long stckNo;
    private long prdNo;
    private String dstBarcdNo;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
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
    private String dstStckStatCd;
    private String boxUntMngYn;
    private String volMngYn;
}
