package com.elevenst.terroir.product.registration.lifeplus.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdSvcCanPlcyVO implements Serializable{

    private static final long serialVersionUID = 4635076028126052056L;
    private long svcCanPlcySeq = 0;
    private long sellerMemNo = 0;
    private String svcCanPlcyClfCd = "";
    private long svcCanPlcyObjNo = 0;
    private String rgnAddrCdList = "";
}
