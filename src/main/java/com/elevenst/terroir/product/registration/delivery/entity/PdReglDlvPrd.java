package com.elevenst.terroir.product.registration.delivery.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdReglDlvPrd implements Serializable {
    private static final long serialVersionUID = -6020353665287225710L;

    private long prdNo;
    private String reglDlvTypCd;
    private String reglDlvMthdCd;
    private String imdtDlvYn;
    private String reglDlvDy;
    private String reglDlvCnWkdy;
    private String useYn;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
}
