package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AgencyVO implements Serializable{
    private static final long serialVersionUID = -2035558367940011788L;

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
