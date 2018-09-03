package com.elevenst.terroir.product.registration.lifeplus.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BsnsHrMgtDetailExctVO implements Serializable {
    private static final long serialVersionUID = 3085985857596520001L;

    private long strNo;
    private String wkdyCd;
    private String exctBgnHm;
    private String exctEndHm;
    private long createNo;
    private long updateNo;
}
