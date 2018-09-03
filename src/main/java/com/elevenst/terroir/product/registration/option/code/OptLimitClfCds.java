package com.elevenst.terroir.product.registration.option.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum OptLimitClfCds implements GroupCodeInterface {

    CATEGORY("01", "카테고리")
    ,PRD("02", "상품")
    ,SELLPLUSCATEGORY("03", "셀러+카테고리")
    ,SHOCKINGDEAL("04", "쇼킹딜")
    ,PO("05", "PO")
    ,BASIC("06", "기본")
    ,STANDARD("07", "표준상품")
    ;

    private final String dtlsCd;
    private final String dtlsComNm;

    OptLimitClfCds(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    @Override
    public String getDtlsCd() {
        return dtlsCd;
    }

    @Override
    public String getDtlsComNm() {
        return dtlsComNm;
    }

    @Override
    public String getGrpCd() {
        return "PD208";
    }

    @Override
    public String getGrpCdNm() {
        return "옵션최대개수제한코드";
    }
}
