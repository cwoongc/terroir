package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductAddCompositionVO implements Serializable{

    private long prdCompNo;
    private long mainPrdNo;
    private long compPrdNo;
    private long compPrdStckNo;
    private long evntNo;
    private String aplBgnDy;
    private String aplEndDy;
    private String mainPrdYn;
    private String addPrdOptNm;
    private long selPrc;
    private long addCompPrc;
    private long compPrdQty;
    private long dispPrrtRnk;
    private String prdCompTypCd;
    private long selQty;
    private long stckQty;
    private String useYn;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
    private String compPrdNm;
    private long addPrdGrpNo;
    private String addPrdGrpNm;
    private long addPrdWght;
    private String sellerAddPrdCd;
    private double addCstmAplPrc;		//상품신고가
    private String addGblDlvYn;			//전세계해외배송
    private String mainGblDlvYn;		//main상품의 전세계해외배송
    private String prdNm;
    private String prdCompTyp;
    private long gnrlPrdNo;
    private String optStr;
    private long increaseQty;       //증감수량
    private String selStatCd;
    private String compPrdVatCd;     //부가세 구분 코드
    private String offerDispLmtYn;

    private long totalCount; // 페이징때 사용되는 카운트
    private long start = -1; // -1로 초기화
    private long limit = -1; // -1로 초기화

}
