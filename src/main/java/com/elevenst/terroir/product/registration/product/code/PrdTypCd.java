package com.elevenst.terroir.product.registration.product.code;

import com.elevenst.common.util.GroupCodeInterface;

/**
 * Created by 1003811 on 2017. 10. 27..
 */
public enum PrdTypCd implements GroupCodeInterface {
    NORMAL(                         "01","일반배송상품"),
    SERVICE_VOUCHER(                "02","서비스이용권상품"),
    COMPOSITE(                      "03", "복합상품"),
    SERVICE(                        "04","서비스상품"),
    PROMOTE(                        "05","홍보상품"),
    CUSTOMER_PERSONALIZED(          "06","고객맞춤상품"),
    SERVICE_COUPON(                 "07","서비스쿠폰상품"),
    SELLER_TARGET(                  "08","판매자대상상품"),
    SMS_AND_PRINT_COUPON(           "09","SMS 전송&쿠폰출력 상품"),
    SMS_SEND(                       "10","SMS 전송"),
    BRANCH_DELIVERY_MART(           "11","지점배송 마트상품"),
    DIGITAL_CATEGORY(               "12","디지털카테고리상품"),
    BP_TRAVEL(                      "13","제휴사 여행 상품"),
    POINT(                          "14","포인트 상품권"),
    SKI(                            "15","스키시즌권"),
    DELIVERY(                       "16","택배서비스상품"),
    SOCIAL_INTANGIBLE(              "17","소셜무형상품"),
    SOCIAL_TANGIBLE(                "18","소셜유형상품"),
    PIN_11ST_SEND(                  "19","PIN번호(11번가발송)"),
    PIN_ZERO_COST(                  "20","PIN번호(0원상품)"),
    TOWN_DELIVERY(                  "21","타운배달상품"),
    BP_TICKET(                      "22","제휴사 티켓 상품"),
    ELEVEN_PAY(                     "23","11PAY"),
    SOHO(                           "24","소호"),
    EMAIL_SMS_SEND(                 "25","E-mail&SMS 전송 상품"),
    RENTAL(                         "26","렌탈상품"),
    ELEVEN_PAY_B2B(                 "27","11PAY B2B 상품"),
    MART(                           "28","MART 상품"),
    TRAVEL_INTERNAL(                "29","여행내재화상품"),
    LODGE_INTERNAL(                 "30","숙박내재화상품"),
    ONECLICK_CHECKOUT_BASIC(        "31","원클릭 체크아웃 기본형"),
    ONECLICK_CHECKOUT_SERVICE(      "32","원클릭 체크아웃 서비스형"),
    ONECLICK_CHECKOUT_PRODUCT(      "33","원클릭 체크아웃 상품형"),
    ONECLICK_CHECKOUT_RESERVATION(  "34","원클릭 체크아웃 예약형"),
    STORE_VISIT(                    "35","매장방문형"),
    VISITING_SERVICE(               "36","출장서비스형"),
    VISITING_PICKUP_DELIVERY(       "37","출장수거배달형")
    ;

    private String dtlsCd;
    private String dtlsComNm;

    PrdTypCd(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    @Override
    public String getGrpCd() {
        return "PD018";
    }

    @Override
    public String getGrpCdNm() {
        return "상품구분코드";
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
