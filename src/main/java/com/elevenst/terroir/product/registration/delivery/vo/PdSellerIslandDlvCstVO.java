package com.elevenst.terroir.product.registration.delivery.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdSellerIslandDlvCstVO implements Serializable {

    private static final long serialVersionUID = 6829311067245803390L;

    private long selMnbdNo;
    private long jejuDlvCst;
    private long islandDlvCst;
    private String useYn;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
}
