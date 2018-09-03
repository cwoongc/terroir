package com.elevenst.terroir.product.registration.product.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrdAgency implements Serializable{

    private static final long serialVersionUID = -3976546180159676752L;

    private long prdNo;
    private String agencyBsnsNo;
    private String bsnsBgnDy;
    private String agencyNm;
    private String agencyBsnsSt;
    private String agencyItm;
    private String rptvNm;
    private String rptvTlphnNo;
    private String rptvEmailAddr;
    private String enpPlcAddr;
    private String useYn;
    private long createNo;
}
