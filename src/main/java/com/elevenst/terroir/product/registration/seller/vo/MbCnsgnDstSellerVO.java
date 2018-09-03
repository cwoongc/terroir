package com.elevenst.terroir.product.registration.seller.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MbCnsgnDstSellerVO implements Serializable {
    private static final long serialVersionUID = 3235733719888174733L;

    private long memNo;
    private String cnsgnYn;
    private Date contrBgnDt;
    private Date contrEndDt;
    private long contrEmpNo;
    private String contrEmpNm;
    private String dstChgrNm;
    private String dstChgrMailAddr;
    private String emailAddrCd;
    private String drctInputEmailAddr;
    private String dstChgrTelno;
    private String dstChgrCpno;
    private String dstSmsRcvYn;
    private String dstUfeeClfCd;
    private float dstUfeeRt;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
    private String[] parseDstChgrTelno;
    private String[] parseDstChgrCpno;
}
