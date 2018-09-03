package com.elevenst.terroir.product.registration.template.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class InventoryProductDescVO implements Serializable{
    private long prdDescNo;
    private long prdDescObjNo;
    private String  prdDescObjClfCd;
    private String prdDescTypCd;
    private String prdDescContVc;
    private String prdDescContClob;
    private String clobTypYn;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
}
