package com.elevenst.terroir.product.registration.image.entity;

import lombok.Data;

@Data
public class PdPrdImage {

	private long prdNo;
	private String urlPath;
	private String basicExtNm;
    private String add1ExtNm;
    private String add2ExtNm;
    private String add3ExtNm;
    private String l170ExtNm;
    private String l80ExtNm;
    private int cutCnt = 1;
    private String pngExtNm;
    private String orgExtNm;
    private String styleExtNm;
    private String wirelssDtlExtNm;
    private long verNo;
    private String basicZoominImgYn;
    private String add1ZoominImgYn;
    private String add2ZoominImgYn;
    private String add3ZoominImgYn;
    private String dtlBasicExtNm;
    private String moblBasicExtNm;
    private String moblAdd1ExtNm;
    private String moblAdd2ExtNm;
    private String moblAdd3ExtNm;
    private String moblAdd4ExtNm;
    private String cardVwImgUrl;
    private String ptnrExusImgUrl;
    private String imgScoreEftvYn;
    private long baseImgScore;
    private long dtlBaseImgScore;
    private int baseImgWdthVal;
    private int baseImgVrtclVal;
    private int dtlBaseImgWdthVal;
    private int dtlBaseImgVrtclVal;
    private String createDt;
    private String updateDt;
}