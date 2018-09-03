package com.elevenst.terroir.product.registration.delivery.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MbMemVO implements Serializable {

    private long rtnDlvFee;
    private long chngDlvFee;
    private String initDlvNoFeeMth;
}
