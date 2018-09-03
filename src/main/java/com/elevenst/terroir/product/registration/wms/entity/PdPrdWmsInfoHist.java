package com.elevenst.terroir.product.registration.wms.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdPrdWmsInfoHist implements Serializable {
    private static final long serialVersionUID = -5310066223975553019L;

    private	long		prdNo;
    private Date        histAplDt;
    private	String		ssnlPrdYn;
    private	String		frigTypCd;
    private String		incmAllwTrmRt;
    private	String		exprTrmChkYn;
    private	String		exprTrmClfCd;
    private	String		exprTrmUnt;
    private	long		exprTrmVal;
    private	String		rtnTwYn;
    private	String		selUnt;
    private	long		empNo;
    private	String		createNo;
    private	long		updateNo;
    private	String		updateNm;
    private	String		wmsPrdNm;
    private	String		repPtnrMemNo;
    private	String		onePackYn;
    private long        whinSflifeResidDcnt;
    private long        whoutSfliceResidDcnt;
    private	String		seasonClfCd;
    private String		stckAttrRegYn;
    private String		dstPrdClfCd;
    private String      dlvExpcDdExpYn;
}
