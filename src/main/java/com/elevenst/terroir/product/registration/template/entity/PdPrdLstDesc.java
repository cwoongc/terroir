package com.elevenst.terroir.product.registration.template.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrdLstDesc implements Serializable {

    private long prdDescObjNo;
    private String  prdDescObjClfCd;
    private String prdDescTypCd;
    private String prdDescContVc;
    private String prdDescContClob;
    private String clobTypYn;
    private long createNo;

}
