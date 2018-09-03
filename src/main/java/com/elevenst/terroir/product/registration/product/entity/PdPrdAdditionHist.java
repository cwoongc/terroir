package com.elevenst.terroir.product.registration.product.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdPrdAdditionHist implements Serializable{

    private long prdNo;
    private long prdWght;
    private Date updateDt;
    private long updateNo;
}
