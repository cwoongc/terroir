package com.elevenst.terroir.product.registration.lifeplus.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdRsvLmtQty implements Serializable {
    private static final long serialVersionUID = -3674150228487827789L;

    private long prdNo;
    private String bgnDt;
    private String wkdyCd;
    private String hhmm;
    private long rsvLmtQty;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
}
