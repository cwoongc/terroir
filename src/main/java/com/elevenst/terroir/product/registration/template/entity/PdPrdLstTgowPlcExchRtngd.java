package com.elevenst.terroir.product.registration.template.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrdLstTgowPlcExchRtngd implements Serializable {

    private long prdAddrObjNo;
    private String prdAddrObjClfCd;
    private long memNo;
    private long addrSeq;
    private String prdAddrClfCd;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
    private String mbAddrLocation;

}
