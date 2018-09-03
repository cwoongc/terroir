package com.elevenst.terroir.product.registration.product.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdPrdStatHist implements Serializable {
    private static final long serialVersionUID = -7976741446592178733L;

    private long prdNo;
    private String aplEndDt;
    private String aplBgnDt;
    private String prdStatCd;
    private String statChgRsn;
    private String selMthdCd;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
    private String svcAreaCd;
}
