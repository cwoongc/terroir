package com.elevenst.terroir.product.registration.deal.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class EventOrgPrdInfoVO implements Serializable {

    private static final long serialVersionUID = -7353031951210757353L;
    private long eventNo;
    private long prdNo;
    private String prdNm;
    private String prdDescContClob;

    private String dtlBasicExtNm;
    private String basicZoomInImgYN;

    private long dlvCst;
    private long dlvBasiAmt;
    private String dlvCstInstBasiCd;
    private String dlvCstPayTypCd;
    private String dlvCstInfoCd;
    private String bndlDlvCnYn;

    private long cupnNo;
    private long selPrc;
    private long dscAmt;
    private double dscRt;
    private String cupnDscMthdCd;
    private String cupnIssStartDy;		//쿠폰지급기간 시작일
    private String cupnIssEndDy;		//쿠폰지급기간 종료일

    private long mallPnt;
    private long mallPntRt;
    private String spplWyCd;

    private long ocbPnt;
    private long ocbPntRt;
    private String ocbWyCd;

    private long mileagePnt;
    private long mileagePntRt;
    private String mileageWyCd;

    private long hopeShpPnt;
    private long hopeShpPntRt;
    private String hopeShpWyCd;

    private String createDt;
    private long   createNo;
    private String updateDt;
    private long   updateNo;
}
