package com.elevenst.terroir.product.registration.template.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrdLstAddPrdCompVO implements Serializable {

    private long compPrdNo;
    private String compPrdNm;
    private long compPrdStckNo;
    private String mainPrdYn;
    private long selPrc;
    private long addCompPrc;
    private long compPrdQty;
    private long dispPrrtRnk;
    private String prdCompTypCd;
    private long selQty;
    private String useYn;
    private String prdCompObjClfCd;
    private long prdCompObjNo;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
}
