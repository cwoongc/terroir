package com.elevenst.terroir.product.registration.cert.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductCertParamVO implements Serializable {

    private long prdNo;
    private int certInfoCnt;
    private long dispCtgrNo;
    private List<String> certTypeCd = new ArrayList<String>();
    private List<String> certKey = new ArrayList<String>();

}
