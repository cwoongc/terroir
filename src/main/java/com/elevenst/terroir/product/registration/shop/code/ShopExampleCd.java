package com.elevenst.terroir.product.registration.shop.code;


/**
 * PD999 예제코드
 * PD_PRD_EXAMPLE.shopCd
 */
public enum ShopExampleCd {

    EXAMPLE_PRICE("01","예제가"),
    EXAMPLE_CARD("02","예제카드"),

    ;


    public final String groupCode = "PD999";
    public final String groupCodeName = "예제코드";
    public final String code;
    public final String codeName;


    ShopExampleCd(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }



}
