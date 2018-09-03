package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProductRmaterialVO {

    private long rmaterialSeqNo;
    private String rmaterialNm;

	private List<ProductRmaterialIngredVO> rmaterialIngredList;

}