package com.elevenst.terroir.product.registration.common.vine.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SellerDefaultV1 implements Serializable{

    private static final long serialVersionUID = 8895278478339290363L;

    private long memberNo;
    private String memberId;
    private String name;
    private String memberClassification;
    private String memberType;
    private String memberStatus;
    private String memberStatusDetail;
    private String representativeSellerNo;
    private String chinaApplyStatusCode;
    private String businessDealClassificationCode;
    private String wmsUseClassificationCode;
}
