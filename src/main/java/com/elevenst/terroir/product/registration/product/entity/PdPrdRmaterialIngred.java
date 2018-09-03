package com.elevenst.terroir.product.registration.product.entity;

import lombok.Data;

@Data
public class PdPrdRmaterialIngred {

	private long prdNo;
	private long rmaterialSeqNo;
	private long ingredSeqNo;
	private String ingredNm;
	private String orgnCountry;
	private long content;
}