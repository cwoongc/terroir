package com.elevenst.terroir.product.registration.template.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdPrdLstOrdBasiDlvCst implements Serializable {

    private long dlvCstInstNo;
    private long prdInfoTmpltNo;
    private String dlvCstInstObjClfCd;
    private long ordBgnQty;
    private long ordEndQty;
    private long dlvCst;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
}
