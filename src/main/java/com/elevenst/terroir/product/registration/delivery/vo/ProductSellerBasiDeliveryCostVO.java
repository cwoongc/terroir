package com.elevenst.terroir.product.registration.delivery.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductSellerBasiDeliveryCostVO implements Serializable {

    private long dlvCstInstNo;
    private long selMnbdNo;
    private long ordBgnAmt;
    private long ordEndAmt;
    private long dlvCst;
    private long addrSeq;
    private String mbAddrLocation;
    private String 	createDt;
    private long 	createNo;
    private String 	updateDt;
    private long 	updateNo;
    private String histAplBgnDt;
    private String histAplEndDt;
}
