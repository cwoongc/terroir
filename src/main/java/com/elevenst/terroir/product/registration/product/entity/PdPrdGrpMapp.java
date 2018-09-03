package com.elevenst.terroir.product.registration.product.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrdGrpMapp implements Serializable{
    private static final long serialVersionUID = 1508061878754635908L;

    private long prdGrpNo = 0;
    private long prdNo = 0;
    private long dispPrrt = 0;
    private String repPrdYn = "";
    private String useYn = "";
    private String createDt = "";
    private long createNo = 0;
    private String updateDt = "";
    private long updateNo = 0;
}
