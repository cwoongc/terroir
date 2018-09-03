package com.elevenst.terroir.product.registration.catalog.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PrdModelVO extends CatalogCmnVO implements Serializable {
    private static final long serialVersionUID = -3986086089742057181L;
    private long    prdNo;
    private long    modelNo;
    private String  modelCd;
    private String  modelNm;
    private String  mnbdClfCd;
    private long    memNo;
    private long    ctlgModelNo;
    private long  ctlgNo;
    private long  ctlgGrPno;
    private long  hgrnkCtlgNo;
    private String  dispModelNm;
    private String  basiAmt;
    private String  basiUntCd;
    private String  basiUntDispYn;
    private String  imgPath;
    private String  prdMatchCnt;
    private String  prdRecmCnt;
    private String  ctlgStatCd;
    private String  barCode;
    private String  regTypCd;
    private String  useYn;
    private String  aprvNo;
    private String  etcInfo;
    private String  brandInfo;
    private String  makerInfo;
    private String  dispCtgrLevel;
    private String  dispCtgr1No;
    private String  dispCtgr1Nm;
    private String  dispCtgr2No;
    private String  dispCtgr2Nm;
    private String  dispCtgr3No;
    private String  dispCtgr3Nm;
    private String  dispCtgr4No;
    private String  dispCtgr4Nm;
    private String  ctlgSvcClf;
    private String  ctlgTypCd;

    private String modelCdVal;
    private long minPrice;
    private String brandCd;
    private String ctlgGrpNm;
    private String modelSpecSummury;
    private String imgUrl;
}
