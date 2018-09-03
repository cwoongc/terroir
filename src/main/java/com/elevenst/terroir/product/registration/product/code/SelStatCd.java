package com.elevenst.terroir.product.registration.product.code;

import com.elevenst.common.util.GroupCodeInterface;

/**
 *  판매상태코드
 */
public enum SelStatCd implements GroupCodeInterface {
    PRODUCT_COMFIRM_WAIT(           "101", "승인대기"),
    PRODUCT_DISPLAY_BEFORE(         "102", "전시전"),
    PRODUCT_SELLING(                "103", "판매중"),
    PRODUCT_SOLD_OUT(               "104", "품절"),
    PRODUCT_SELLING_STOP(           "105", "판매중지"),
    PRODUCT_SELLING_END(            "106", "판매종료"),
    PRODUCT_FORCE_SELLING_END(      "107", "판매강제종료"),
    PRODUCT_SELLING_PROHIBIT(       "108", "판매금지"),
    PRODUCT_CONFIRM_DENY(           "109", "승인거부"),
    PRODUCT_CONFIRM_COMPLETE(       "110", "승인완료"),

    AUCTION_COMFIRM_WAIT(           "201", "승인전"),
    AUCTION_DISPLAY_BEFORE(         "202", "진행예정"),
    AUCTION_SELLING(                "203", "진행중"),
    AUCTION_BID_SUCCESS_END(        "204", "경매낙찰종료"),
    AUCTION_MISCARRIAGE_END(        "205", "경매유찰종료"),
    AUCTION_END(                    "206", "경매중지"),
    AUCTION_FORCE_END(              "207", "경매강제종료"),
    AUCTION_CONFIRM_DENY(           "208", "승인거부"),
    AUCTION_CONFIRM_COMPLETE(       "209", "승인완료"),
    ;

    private String dtlsCd;
    private String dtlsComNm;
    SelStatCd(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    @Override
    public String getGrpCd() {
        return "PD014";
    }

    @Override
    public String getGrpCdNm() {
        return "판매상태코드";
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
