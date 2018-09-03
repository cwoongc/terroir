package com.elevenst.terroir.product.registration.option.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdStockPtnrInfo implements Serializable {
    private static final long serialVersionUID = -6664569205158179076L;

    private long stockNo;
    private long selMnbdNo;
    private String sellerStockCd;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
    private String uniqChkCd;
}
