package com.elevenst.terroir.product.registration.lifeplus.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdRsvLmtQtyVO implements Serializable{
    private static final long serialVersionUID = 3608751151087460365L;
    private long prdNo;
    private String bgnDt;
    private String wkdyCd;
    private String hhmm;
    private long rsvLmtQty;
}
