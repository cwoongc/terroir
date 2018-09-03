package com.elevenst.terroir.product.registration.catalog.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrdModel implements Serializable {
    private long prdNo;
    private String mnbdClfCd;
    private long   modelNo;
    private long   createNo;
    private String modelNm;
    private String modelCdVal;
}
