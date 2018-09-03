package com.elevenst.terroir.product.registration.product.entity;

import lombok.Data;

@Data
public class PdPrdItm {

    private long prdItmNo = 0;
    private long prdNo = 0;
    private long dispItmNo = 0;
    private String svcBgnDy = "";
    private String svcEndDy = "";
    private String useYn = "";
    private String createDt = "";
    private long createNo = 0;
    private String updateDt = "";
    private long updateNo = 0;
    private String displayTermClfCd = "";
    private long itmSelMnbdNo = 0;
    private String itmPremiumYn = "";
    private String cupnIssCnYn = "";
}
