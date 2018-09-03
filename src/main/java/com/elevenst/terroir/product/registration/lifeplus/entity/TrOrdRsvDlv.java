package com.elevenst.terroir.product.registration.lifeplus.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TrOrdRsvDlv implements Serializable {
    private static final long serialVersionUID = 3803494155272415469L;

    private long rsvNo;
    private long rsvTmtr;
    private String rsvDlvStatCd;
    private String rsvDlvCnfrmDt;
    private String dlvChgrNm;
    private String dlvPtnrEmpNo;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
}
