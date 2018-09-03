package com.elevenst.terroir.product.registration.lifeplus.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdSvcCanRgn implements Serializable {
    private static final long serialVersionUID = 20601892452782117L;

    private long svcCanRgnSeq;
    private long svcCanPlcySeq;
    private String rgnClfCd;
    private String rgnAddrCd;
    private String sidoNm;
    private String sigunguNm;
    private String ueupmyonNm;
    private String ueupmyonCd;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
}
