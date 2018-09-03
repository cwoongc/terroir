package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ProductGroupVO {
    private static final long serialVersionUID = -5521595584523309313L;

    // DB컬럼 데이터
    private long prdGrpNo;
    private String prdGrpNm;
    private long selMnbdNo;
    private long basiCtgrNo;
    private long basiCtgrLevel = 2;
    private String basiDlvMthdCd;
    private String basiPrdClfCd;
    private String basiDlvCanRgnCd;
    private String minorBuyCanYn;
    private String dispYn;
    private String dispBgnDt;
    private String dispEndDt;
    private String prdGrpTypCd;
    private String grpDtlImgUrl;
    private String grpDispTypCd;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;

    // 화면 표시용
    private String basiCtgrPath;
    // private long basePrdNo;
    private long repPrdNo;
    private long dispCtgr1No;
    private long dispCtgr2No;
    private long prdCount = 0;

    // 그리드 조회용
    private long totalCount = 0;
    private int page;
    private int start = 1;
    private int end = 11;
    private int limit = 10;
    private String dateType; // 기간조회구분
    private String dispStatCd; // 전시상태
    private String createDtTo;

    private long productListLimit = 50;
    //private List<GroupProductBO> productList = new ArrayList();
    private List<Long> grpNoList = new ArrayList();
    private boolean UsePrdGrpTypCd = false;
    private String prdStatCd;
}
