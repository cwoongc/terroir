package com.elevenst.terroir.product.registration.price.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlusDscVO implements Serializable{

    private static final long serialVersionUID = 3199231084983459982L;
    private long pluDscNo = 0;
    private String pluTypCd = "";
    private String pluDscAplYn = "";
    private String pluAplBgnDy = "";
    private String pluAplEndDy = "";
    private String pluDscCd = "";
    private long pluDscBasis = 0;
    private String pluDscWyCd = "";
    private double pluDscRt = 0.0;
    private long pluDscAmt = 0;
    private String useYn = "";
}
