package com.elevenst.terroir.product.registration.category.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdObjTypProp implements Serializable {

    private long    objNo;
    private String  objTypCd;
    private String  objVal1;
    private String  objVal2;
    private String  objVal3;
    private long    createNo;
    private long    updateNo;
}
