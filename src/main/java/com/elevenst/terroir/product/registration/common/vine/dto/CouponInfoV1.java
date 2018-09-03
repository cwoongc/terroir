package com.elevenst.terroir.product.registration.common.vine.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CouponInfoV1 implements Serializable{
    private static final long serialVersionUID = -3855079905681446120L;

    private CouponBO couponBO = new CouponBO();



    @Data
    public class CouponBO implements Serializable{
        private long couponNo;
        private String couponName;
    }
}
