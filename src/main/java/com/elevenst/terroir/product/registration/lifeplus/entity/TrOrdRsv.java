package com.elevenst.terroir.product.registration.lifeplus.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TrOrdRsv implements Serializable {
    private static final long serialVersionUID = 1354531810951113576L;

    private long rsvNo;
    private long sellerMemNo;
    private long buyMemNo;
    private String buyMemNm;
    private String rsvpeTelNo;
    private long ordNo;
    private String buyAddr;
    private String dtlAddr;
    private String mailNo;
    private String mailNoSeq;
    private String buldMngNo;
    private Date rsvHopeDt;
    private Date rsvDlvHopeDt;
    private String addDemndCont;
    private String prdAddInfoCont;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
}
