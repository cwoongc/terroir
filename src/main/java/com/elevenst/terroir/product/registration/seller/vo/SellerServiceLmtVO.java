package com.elevenst.terroir.product.registration.seller.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SellerServiceLmtVO implements Serializable{

    private static final long serialVersionUID = -7922463675634624480L;

    private long memNo = 0;
    private String service = "";
    private long lmtCnt = 0;
    private long accessCnt = 0;
}
