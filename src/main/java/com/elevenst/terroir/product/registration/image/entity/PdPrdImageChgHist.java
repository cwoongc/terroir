package com.elevenst.terroir.product.registration.image.entity;

import lombok.Data;

@Data
public class PdPrdImageChgHist {

	private long prdNo;
	private String histDt;
    private String baseImgChgYn;
    private String dtlBaseImgChgYn;
    private String add1ImgChgYn;
    private String add2ImgChgYn;
    private String add3ImgChgYn;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
}