package com.elevenst.terroir.product.registration.product.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum DlvCstInstBasiCd implements GroupCodeInterface{
    FREE("01","무료"),
    FIXED_DELIVERY("02", "고정배송비"),
    PRODUCT_CONDITIONAL_FREE("03", "상품조건부무료"),
    QUANTITY_GRADE("04", "수량별차등"),
    DELIVERY_COST_PER_ONE("05", "1개당배송비"),
    SELLER_CONDITIONAL_FREE("06","판매자조건부무료"),
    SELLER_CONDITIONAL_STANDARD("07","판매자조건부기준"),
    EX_WAREHOUSE_CONDITIONAL_STANDARD("08","출고지조건부기준"),
    UNITED_EX_WAREHOUSE_CONDITIONAL_STANDARD("09","통합출고지조건부기준"),
    ELEVENST_GLOBAL_DELIVERY_CONDITIONAL_STANDARD("10", "11번가해외배송조건부기준"),
    ABROAD_LUXURY_DIRECT_BUY_STANDARD("11", "해외명품직매입기준"),
    NOW_DELIVERY("12","NOW배송")
    ;

    private String dtlsCd;
    private String dtlsComNm;
    DlvCstInstBasiCd(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    @Override
    public String getGrpCd() {
        return "PD005";
    }

    @Override
    public String getGrpCdNm() {
        return "배송비설정기준코드";
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
