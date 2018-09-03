package com.elevenst.terroir.product.registration.lifeplus.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdRsvSchdlInfo implements Serializable {
    private static final long serialVersionUID = 345572465138593342L;

    private long prdNo;
    private String bgnDt;
    private String endDt;
    private String dtlDesc;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
}
