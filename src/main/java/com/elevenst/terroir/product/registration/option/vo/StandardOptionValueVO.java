package com.elevenst.terroir.product.registration.option.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StandardOptionValueVO implements Serializable {
    private static final long serialVersionUID = 5901311590790842275L;

    private long stdOptNo;
    private long stdOptValNo;
    private String stdOptValNm;
    private String htmlColorCdVal;
    private String useYn;
    private long createNo;
    private Date createDt;
    private long updateNo;
    private Date updateDt;

    private PdOptValueVO productOptValueBO;
}
