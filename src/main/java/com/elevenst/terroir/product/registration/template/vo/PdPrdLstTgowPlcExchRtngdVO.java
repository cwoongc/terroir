package com.elevenst.terroir.product.registration.template.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrdLstTgowPlcExchRtngdVO implements Serializable {

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
