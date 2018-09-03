package com.elevenst.terroir.product.registration.template.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrdLstDescVO implements Serializable {

    private long prdDescObjNo;
    private String  prdDescObjClfCd;
    private String prdDescTypCd;
    private String prdDescContVc;
    private String prdDescContClob;
    private String clobTypYn;
    private long createNo;

}
