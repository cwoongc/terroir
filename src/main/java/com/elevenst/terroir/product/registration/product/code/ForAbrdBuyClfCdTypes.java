package com.elevenst.terroir.product.registration.product.code;

import com.elevenst.common.util.GroupCodeInterface;

/**
 * Created by 1003811 on 2017. 10. 25..
 */
public enum ForAbrdBuyClfCdTypes implements GroupCodeInterface{
    NORMAL("01", "일반상품"),
    ABRD_BUY_PROXY_PRD("02", "해외구매대행상품")
    ;

    private String dtlsCd;
    private String dtlsComNm;


    ForAbrdBuyClfCdTypes(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }


    @Override
    public String getGrpCd() {
        return "PD099";
    }

    @Override
    public String getGrpCdNm() {
        return "해외구매대행상품구분코드";
    }

    @Override
    public String getDtlsCd() {
        return dtlsCd;
    }

    @Override
    public String getDtlsComNm() {
        return dtlsComNm;
    }

}
