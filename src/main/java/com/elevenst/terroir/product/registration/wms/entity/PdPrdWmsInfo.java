package com.elevenst.terroir.product.registration.wms.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrdWmsInfo implements Serializable{

    private	long		prdNo;			        //상품번호
    private	String		ssnlPrdYn;		        //시즌성상품여부
    private	String		frigTypCd;		        //보관구분
    private String		incmAllwTrmRt;	        //입고가능기한율
    private	String		exprTrmChkYn;	        //유통기한관리여부
    private	String		exprTrmClfCd;	        //유통기한구분
    private	String		exprTrmUnt;		        //유통기한적용단위
    private	long		exprTrmVal;		        //유통기한적용값
    private	String		rtnTwYn;		        //반품수거여부
    private	String		selUnt;			        //판매단위
    private	long		empNo;			        //담당 MD
    private	long		createNo;		        //수정자번호
    private	long		updateNo;		        //수정자번호
    private	String		updateNm;		        //수장자명
    private	String		wmsPrdNm;		        //관리상품명
    private	String		repPtnrMemNo;	        //대표거래처
    private	String		onePackYn;		        //합포장여부
    private long        whinSflifeResidDcnt;    //입고유통기한잔여일수
    private long        whoutSfliceResidDcnt;   //출고유통기한잔여일수
    private	String		seasonClfCd;	        //시즌구분코드
    private String		stckAttrRegYn;      	//재고속성등록여부
    private String		dstPrdClfCd;	        //물류상품구분코드
    private String      dlvExpcDdExpYn;         //배송예정일노출여부
}
