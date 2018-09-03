package com.elevenst.terroir.product.registration.common.vine.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SellerBusinessV1 implements Serializable{
    private static final long serialVersionUID = -3794246809434289212L;

    private String sellerType = "";
    private String corporateRegistrationNo = "";
    private String businessName = "";
    private String representativeRegistrationNo = "";
    private String representativeName = "";
    private String representativePhoneNumber = "";
    private String item = "";
    private String businessField = "";
    private String managerName = "";
    private String businessPlaceZipNo = "";
    private String businessPlaceAddress = "";
    private String businessPlaceDetailAddress = "";
    private String faxNo = "";
    private String onlineBusinessDeclarationNo = "";
    private String overseaMemberType = "";
    private String hotLinePhoneNo = "";
    private String supplierType = "";
    private String ocbZoneApprovalCode = "";
}
