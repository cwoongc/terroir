package com.elevenst.terroir.product.registration.product.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrdSvcArea implements Serializable {
    private static final long serialVersionUID = 6506821249614228569L;

    private long prdNo;
    private String svcAreaCd;
    private long selMnbdNo;
    private String prdTypCd;
    private long shopNo;
    private String certDownYn;
    private String certType;
    private String allBranchUseYn;
    private long townSelLmtDy;
}
