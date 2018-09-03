package com.elevenst.terroir.product.registration.product.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdPrdTempSave implements Serializable {
    private static final long serialVersionUID = 5037106281412504322L;

    private long sellerNo;
    private long tempSaveSeq;
    private String tempSaveJson;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
}
