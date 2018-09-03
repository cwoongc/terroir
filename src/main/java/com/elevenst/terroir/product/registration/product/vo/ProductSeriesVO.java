package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductSeriesVO implements Serializable {
    private static final long serialVersionUID = -8270595385776112920L;

    private long seriesNo;
    private String seriesNm;
    private long selMnbdNo;
    private String sellerSeriesCd;
    private String seriesKdCd;
    private String delYn;
    private int rtncode;
    private String dispYn;
    private long createNo;
    private long updateNo;
    private String mgChgrNo;
    private long seriesPrdCnt;
    private int start;
    private int end;
    private int limit;
    private long prdNo;
    private String sellerPrdCd;
    private String[] 	chkPrdNo;
    private String[] 	chkSellerPrdCode;
    private List prdNoList = new ArrayList();
    private String createDt ;
    private String seriesGubunTxt;
    private String searchType;
    private String seriesType;
    private String createDtTo;
    private long totalCount = 0;
    private long num = 0;
    private String		fnSellerPdDscInfo;
    private long         selPrc;
    private String selStatCd;
    private String dispCtgrPath;
    private String prdCreateDt;
    private String memNm;
    private long dispPrir;
    private String prdNm;
    private String mnfcDy;
    private int seriesCnt;
    private List SeriesCompositionBOList = new ArrayList();
    private String prdNoBox;
    private String prdNoView;
    private String imageUrl;
}
