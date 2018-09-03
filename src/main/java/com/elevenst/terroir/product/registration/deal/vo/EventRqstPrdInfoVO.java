package com.elevenst.terroir.product.registration.deal.vo;

import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import lombok.Data;

import java.io.Serializable;

@Data
public class EventRqstPrdInfoVO implements Serializable {

    private static final long serialVersionUID = -3151193850315777272L;
    private long eventNo;
    private long prdNo;
    private String prdNm;
    private String prdDescContClob;

    private String dtlBasicExtNm;
    private String basicZoomInImgYN;

    private long mallPnt;
    private long mallPntRt;
    private String pointValue;
    private String spplWyCd;

    private long ocbPnt;
    private long ocbPntRt;
    private String ocbValue;
    private String ocbWyCd;

    private long mileagePnt;
    private long mileagePntRt;
    private String mileageValue;
    private String mileageWyCd;

    private long hopeShpPnt;
    private long hopeShpPntRt;
    private String hopeShpWyCd;

    private String createDt;
    private long   createNo;
    private String updateDt;
    private long   updateNo;

    private long selMnbdNo;

    private boolean isBackOffice;
    private ProductConstDef.PrdInfoType prdInfoType = ProductConstDef.PrdInfoType.ORIGINAL;	// 현재 상품정보의 타입
    private String prdTypCd;
    private String prdDtlTypCd;		// 상품상세 구분 코드 (PD160)
}
