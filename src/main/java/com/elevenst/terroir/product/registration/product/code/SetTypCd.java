package com.elevenst.terroir.product.registration.product.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum SetTypCd implements GroupCodeInterface {
    MASTER("01","마스터"),
    BUNDLE("02","묶음"),
    SET("03", "세트")
    ;

    private String dtlsCd;
    private String dtlsComNm;
    SetTypCd(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    @Override
    public String getGrpCd() {
        return "PD202";
    }

    @Override
    public String getGrpCdNm() {
        return "세트타입코드";
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
