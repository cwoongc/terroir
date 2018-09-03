package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class StdPrdAttrCompVO implements Serializable{

    private long stdAttrCompNo = 0;
    private String stdAttrValueNm = "";
    private String attrClfCd = "";
    private long ctlgAttrNo = 0;
    private long ctlgAttrValueNo = 0;
}
