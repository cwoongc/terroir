package com.elevenst.terroir.product.registration.delivery.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TrGlobalPrdAvgDeli {

    private long prdNo;
    private long avgDeliDays;
    private Date createDt;
    private Date updateDt;

}
