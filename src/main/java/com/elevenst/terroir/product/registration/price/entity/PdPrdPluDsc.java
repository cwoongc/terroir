package com.elevenst.terroir.product.registration.price.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrdPluDsc implements Serializable{

    private static final long serialVersionUID = 6759898127445584389L;

    private long pluDscNo = 0;
    private long prdNo = 0;
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
    private long selMnbdNo = 0;
    private long createNo = 0;
    private String createDt = "";
    private long updateNo = 0;
    private String updateDt = "";
}
