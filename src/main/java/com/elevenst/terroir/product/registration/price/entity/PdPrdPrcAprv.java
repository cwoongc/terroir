package com.elevenst.terroir.product.registration.price.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrdPrcAprv implements Serializable{

    private static final long serialVersionUID = -726526175910242903L;

    private long prdPrcAprvNo = 0;
    private long prdNo = 0;
    private String aplyBgnDy = "";
    private String aprvStatCd = "";
    private String aprvStatRsn = "";
    private long selPrc = 0;
    private long maktPrc = 0;
    private long puchPrc = 0;
    private long mrgnRt = 0;
    private long mrgnAmt = 0;
    private String cupnAplyYn = "";
    private String cupnDscMthdCd = "";
    private long cupnDscVal = 0;
    private String cupnIssMnbdClfCd = "";
    private String cupnCostDfrmMnbdClfCd = "";
    private long moCostDfrmRt = 0;
    private String issCnBgnDt = "";
    private String issCnEndDt = "";
    private String requestMnbdClfCd = "";
    private String requestDt = "";
    private long requestNo = 0;
    private String approveDt = "";
    private long approveNo = 0;
    private String updateDt = "";
    private long updateNo = 0;
    private long selMnbdNo = 0;
    private long frSelPrc = 0;
    private long stockNo = 0;
    private String aprvObjCd = "";
    private String firstRqstYn = "";
    private long addPrc = 0;

    private String svcUsePolicy = "";

}
