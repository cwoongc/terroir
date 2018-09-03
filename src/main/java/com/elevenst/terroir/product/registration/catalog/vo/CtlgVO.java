package com.elevenst.terroir.product.registration.catalog.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CtlgVO implements Serializable {

    private long ctlgNo;
    private String ctlgOptNm;
    private String optValue;
    private long totalCount;
    private long num;
    private boolean freshCategory = false;
}
