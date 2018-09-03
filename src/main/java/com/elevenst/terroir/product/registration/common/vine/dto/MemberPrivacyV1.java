package com.elevenst.terroir.product.registration.common.vine.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemberPrivacyV1 implements Serializable{
    private static final long serialVersionUID = 5684146992478430000L;

    private long memberNo;
    private String name;
    private String sex;
    private String birthYear;
    private String birthDay;
    private String overseaMemberType;
    private boolean adult;
}
