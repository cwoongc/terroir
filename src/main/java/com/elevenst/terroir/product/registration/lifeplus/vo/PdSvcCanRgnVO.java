package com.elevenst.terroir.product.registration.lifeplus.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdSvcCanRgnVO implements Serializable {
    private static final long serialVersionUID = -4870029268445145598L;

    private long svcCanRgnSeq;
    private long svcCanPlcySeq;

    private String rgnClfCd;
    private String rgnAddrCd;
    private String sidoNm;
    private String sigunguNm;
    private String ueupmyonNm;
    private String ueupmyonCd;


    private String lawDongCd;
    private String mailNo;
    private String mailNoSeq;
    private boolean shopData;
    private String rhyeeNm;
    private String ueupMyonCd;
    private String buildMngNo;

}
