package com.elevenst.terroir.product.registration.benefit.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PdCustBnftInfo {
    private long 	custBnftNo;
    private long 	prdNo;
    private String  aplBgnDy;
    private String  aplEndDy;
    private long 	mallPntRt;
    private long 	mallPnt;
    private long 	spplChpQty;
    private String 	chpSpplMthdCd;
    private String 	dlvCstFrYn;
    private String 	custBnftLttrYn;
    private Date 	createDt;
    private long 	createNo;
    private Date 	updateDt;
    private long 	updateNo;
    private long 	pntSpplLmtQty;
    private long 	pntSpplQty;
    private long 	chpSpplLmtQty;
    private long 	chpSpplQty;
    private String 	intfreeMonClfCd;        //무이자할무 코드(PD008)
    private long 	intfreeAplLmtQty;
    private long 	intfreeAplQty;
    private String 	dblPntSpplYn;
    private String 	spplWyCd;
    private long 	evntNo;
    private String 	chpKdCd;
    private String 	useYn;
    private long 	ocbPntRt;
    private long 	ocbPnt;
    private long 	ocbSpplLmtQty;
    private long 	ocbSpplQty;
    private String 	ocbWyCd;
    private long 	mileagePntRt;
    private long 	mileagePnt;
    private long 	mileageSpplLmtQty;
    private long 	mileageSpplQty;
    private String 	mileageWyCd;
    private String 	pntDfrmMnbdClfCd;
    private long 	hopeShpPntRt;
    private long 	hopeShpPnt;
    private long 	hopeShpSpplLmtQty;
    private long 	hopeShpSpplQty;
    private String 	hopeShpWyCd;
    private long 	bnftSelMnbdNo;
}
