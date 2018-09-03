package com.elevenst.terroir.product.registration.option.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdOptItem implements Serializable {
    private static final long serialVersionUID = 6345941446402338293L;

    private long prdNo;
    private long optItemNo;
    private String optClfCd;
    private String optItemNm;
    private long stockClmnPos;
    private String useYn;
    private String statCd;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
    private String prdExposeClfCd;
    private String optTypCd;
    private long stdOptNo;

    private String prdInfoType;
}
