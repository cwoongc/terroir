package com.elevenst.terroir.product.registration.seller.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SellerAuthVO implements Serializable {
    private static final long serialVersionUID = 5402822973890208381L;

    private long selMnbdNo;
    private long selMnbdNckNmSeq;
    private String authTypCd;
    private String authObjNo;
    private String objClfCd;
    private String selRegCd;
    private String useYn;
    private long createNo;
    private String createDt;
    private long updateNo;
    private String updateDt;

    private String mode;
    private String bsnDealClf;

}
