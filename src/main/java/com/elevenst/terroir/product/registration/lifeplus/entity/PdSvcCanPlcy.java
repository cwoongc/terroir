package com.elevenst.terroir.product.registration.lifeplus.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdSvcCanPlcy implements Serializable {
    private static final long serialVersionUID = 1681368377645752371L;

    private long svcCanPlcySeq;
    private long sellerMemNo;
    private String svcCanPlcyClfCd;
    private long svcCanPlcyObjNo;
    private String rgnAddrCdList;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
}
