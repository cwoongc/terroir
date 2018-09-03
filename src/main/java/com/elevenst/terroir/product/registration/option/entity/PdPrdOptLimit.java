package com.elevenst.terroir.product.registration.option.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdPrdOptLimit implements Serializable {
    private static final long serialVersionUID = -944872823184093494L;

    private long optLmtObjNo;
    private String optLmtClfCd;
    private long dispCtgrNo;
    private long combOptNmCnt;
    private long combOptValueCnt;
    private long combOptCnt;
    private long idepOptNmCnt;
    private long idepOptValueCnt;
    private long writeOptNmCnt;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
    private long combOptvalCntNo1;
    private long combOptvalCntNo2;
    private long combOptvalCntNo3;
    private long combOptvalCntNo4;
    private long combOptvalCntNo5;
}
