package com.elevenst.terroir.product.registration.common.code;

/**
 * 배송쿠폰코드 [DP_LIVE_PRD.DLV_CUPN_CD]
 */
public enum DlvCupnCd {

    PAY_DELIVERY_AND_COUPON("01","무료배송N+쿠폰Y"),
    FREE_DELIVERY("10","무료배송 Y + 쿠폰N"),
    FREE_DELIVERY_AND_COUPON("11","무료배송Y + 쿠폰Y");


    public final String groupCode = "?";
    public final String groupCodeName = "배송쿠폰코드";
    public final String code;
    public final String codeName;


    DlvCupnCd(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }
}
