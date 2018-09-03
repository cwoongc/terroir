package com.elevenst.terroir.product.registration.etc.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PrdOthersVO implements Serializable {

    private static final long serialVersionUID = 8483454409999306693L;

    private long prdNo;
    private long createNo;
    private Date createDt;
    private long updateNo;
    private Date updateDt;
    private String healthItmAuthNum;//medNum1
    private String healthSaleCoNum; //medNum2
    private String preAdvChkNum;    //medNum3
    private long templatePrdNo;
    private String selPrdClfFpCd;
    private String beefTraceNo;
    private String buyDsblDyCd;
    private long prdRiousQty;
    private String addQueryCont;
    private String addQueryHintCont;
    private String svcCanRgnClfCd;
    private long dlvProcCanDd;
    private String rsvSchdlYn;
    private String rsvSchdlClfCd;
    private String ptnrPrdClfCd;
    private int selPrdRiousQty = 0;
    private boolean isQCVerifiedStat;
    private String beefTraceStat;
    private String beefTraceDtl;
}
