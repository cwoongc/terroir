package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductRmaterialIngredVO implements Serializable{
    private static final long serialVersionUID = -8419348856738971987L;

    private long rmaterialSeqNo;
    private long ingredSeqNo;
    private String ingredNm;
    private String orgnCountry;
    private long content;
}
