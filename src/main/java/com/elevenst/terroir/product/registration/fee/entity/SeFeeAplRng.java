package com.elevenst.terroir.product.registration.fee.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SeFeeAplRng implements Serializable{

    private static final long serialVersionUID = -4577629749252441430L;

    private long feeItmNo;
    private long aplRngBgnVal;
    private long aplRngEndVal;
    private double fee;
    private String createDt;
    private long  createNo;
    private String updateDt;
    private long  updateNo;
}
