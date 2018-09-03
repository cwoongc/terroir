package com.elevenst.terroir.product.registration.etc.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdPrdOthers implements Serializable{
    private static final long serialVersionUID = -7879403020090581312L;

    private long prdNo;
    private long createNo;
    private Date createDt;
    private long updateNo;
    private Date updateDt;
    private String healthItmAuthNum;//medNum1   의료기기 품목허가번호
    private String healthSaleCoNum; //medNum2   의료기기 판매업신고 기관 및 번호
    private String preAdvChkNum;    //medNum3   의료기기 사전광고심의번호
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
}
