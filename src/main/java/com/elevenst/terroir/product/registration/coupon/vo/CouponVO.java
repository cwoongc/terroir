package com.elevenst.terroir.product.registration.coupon.vo;

import lombok.Data;

import java.io.Serializable;

@Deprecated
@Data
public class CouponVO implements Serializable{

    private String issCnBgnDt;
    private String issCnEndDt;
    private String cupnDscMthdCd;
    private long dscAmt;
    private double dscRt;
}
