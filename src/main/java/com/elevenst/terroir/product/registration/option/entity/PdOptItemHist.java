package com.elevenst.terroir.product.registration.option.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdOptItemHist implements Serializable {
    private static final long serialVersionUID = 8797466815864882586L;

    private long prdNo;
    private Date histAplBgnDt;
    private String chgType;
    private long optItemNo;
    private String optClfCd;
    private String optItemNm;
    private long stockClmnPos;
    private String statCd;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
    private String prdExposeClfCd;
    private String optTypCd;
    private long stdOptNo;
}
