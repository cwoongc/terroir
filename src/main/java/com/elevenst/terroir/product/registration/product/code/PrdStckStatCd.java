package com.elevenst.terroir.product.registration.product.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum PrdStckStatCd implements GroupCodeInterface{
    USING("01", "사용함"),
    SOLD_OUT("02", "품절")
    ;

    private String dtlsCd;
    private String dtlsComNm;
    PrdStckStatCd(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    @Override
    public String getGrpCd() {
        return "PD034";
    }

    @Override
    public String getGrpCdNm() {
        return "상품재고상태코드";
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
