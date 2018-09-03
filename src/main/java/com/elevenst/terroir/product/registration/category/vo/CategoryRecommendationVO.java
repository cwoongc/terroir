package com.elevenst.terroir.product.registration.category.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryRecommendationVO implements Serializable {
    private static final long serialVersionUID = 4753223270447579100L;

    private long dispCtgrNo;
    private long hgrnkCtgrNo;
    private String dispCtgrNm;
    private int level;
    private int isLeaf;
    private double score;
    private String sellerAuthYn;
    private boolean sellerAuthorized;
    private String url;
}
