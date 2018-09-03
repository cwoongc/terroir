package com.elevenst.terroir.product.registration.product.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum SelMthdCd implements GroupCodeInterface{

    /**
     * GRP_CD : PD012
     * GRP_CD_NM : 판매방식코드
     * PD_PRD.SEL_MTHD_CD
     */

    FIXED("01", "고정가 판매")             // 고정가 판매
    ,COBUY("02", "공동구매")            // 공동구매 (사용안합)
    ,AUCT("03", "경매")             // 경매
    ,PLN("04", "예약판매")              // 예약판매 (사용안합)
    ,USED("05", "중고판매")             // 중고판매
    ,TOWN("99", "타운 판매")             // 타운 판매 (DB에는 없음 소스에서만 관리)
    ;

    private String dtlsCd;
    private String dtlsComNm;


    SelMthdCd(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    public static SelMthdCd fromString(String text) {
        for (SelMthdCd selMthdCdCdType : SelMthdCd.values()) {
            if (selMthdCdCdType.dtlsCd.equalsIgnoreCase(text)) {
                return selMthdCdCdType;
            }
        }
        return null;
    }


    public static SelMthdCd forValue(String code) {
        return fromString(code);
    }

    @Override
    public String getGrpCd() {
        return "PD012";
    }

    @Override
    public String getGrpCdNm() {
        return "판매방식코드";
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
