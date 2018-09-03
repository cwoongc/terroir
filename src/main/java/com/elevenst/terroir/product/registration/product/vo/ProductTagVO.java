package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ProductTagVO implements Serializable {
    private static final long serialVersionUID = 6801961809060865810L;

    private long prdNo;
    private String tagNm;
    private Date createDt;
    private long createNo;

    private List<String> tagNmList;
}
