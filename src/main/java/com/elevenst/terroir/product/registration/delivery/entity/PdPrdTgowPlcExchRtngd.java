package com.elevenst.terroir.product.registration.delivery.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PdPrdTgowPlcExchRtngd {

    private long prdNo;
    private long memNo;
    private long addrSeq;

    private String prdAddrClfCd;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
    private String mbAddrLocation;
    private String frCtrCd;

}
