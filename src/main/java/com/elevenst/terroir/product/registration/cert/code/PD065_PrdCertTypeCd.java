package com.elevenst.terroir.product.registration.cert.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum PD065_PrdCertTypeCd implements GroupCodeInterface {
    /**
     * PD065 상품인증유형코드
     * */
    PD065_101_LIFE_SAFE_CERT(                        "101", "[생활용품] 안전인증"),
    PD065_102_ELECTRONIC_SAFE_CERT(                  "102", "[전기용품] 안전인증"),
    PD065_103_LIFE_SAFE_CONFIRM(                     "103", "[생활용품] 안전확인"),
    PD065_104_ELECTRONIC_SAFE_CONFIRM(               "104", "[전기용품] 안전확인"),
    PD065_105_BROADCAST_EQUIPMENT_SUITABLE(          "105", "[방송통신기자재] 적합성평가 (적합인증 적합등록)"),
    PD065_108_AGRICULTURE_ORGANIC(                   "108", "유기농산물"),
    PD065_109_AGRICULTURE_PESTICIDE_FREE(            "109", "무농약농산물"),
    PD065_110_AGRICULTURE_PESTICIDE_LOW(             "110", "저농약농산물"),
    PD065_111_LIVESTOCK_ORGANIC(                     "111", "유기축산물"),
    PD065_112_LIVESTOCK_ANTIBIOTIC_FREE(             "112", "무항생제축산물"),
    PD065_113_FISHERY_ENVIRONMENT_FRIENDLY(          "113", "친환경수산물"),
    PD065_114_HAZARD_CONTROL(                        "114", "위해요소 중점관리(HACCP)"),
    PD065_115_AGRICULTURE_GOOD_CERT(                 "115", "농산물 우수관리인증(GAP)"),
    PD065_116_PROCESSED_FOOD_STANDARD_CERT(          "116", "가공식품 표준화인증(KS)"),
    PD065_117_PROCESSED_FOOD_ORGANIC_CERT(           "117", "유기가공식품 인증"),
    PD065_118_FISHERY_QUALITY_CERT(                  "118", "수산물 품질인증"),
    PD065_119_SPECIAL_FISHERY_QUALITY_CERT(          "119", "수산특산물 품질인증"),
    PD065_120_TRADITIONAL_FISHERY_QUALITY_CERT(      "120", "수산전통식품 품질인증"),
    PD065_122_HEALTH_PRODUCT_ADVERT_ACCEPT(          "122", "건강기능식품 광고심의"),
    PD065_123_LIFE_CHILD_PROTECT_PACK(               "123", "[생활용품] 어린이보호포장"),
    PD065_124_LIFE_PRODUCER_SUITABLE(                "124", "[생활용품] 공급자적합성확인"),
    PD065_127_ELECTRONIC_PRODUCER_SUITABLE(          "127", "[전기용품] 공급자적합성확인"),
    PD065_128_CHILD_CERT_CERT(                       "128", "[어린이제품] 안전인증"),
    PD065_129_CHILD_SAFE_CONFIRM(                    "129", "[어린이제품] 안전확인"),
    PD065_130_CHILD_PRODUCER_SUITABLE(               "130", "[어린이제품] 공급자적합성확인"),
    PD065_131_NOT_APPLICABLE(                        "131", "해당없음(대상이 아닌 경우)"),
    PD065_132_SEE_DETAILS(                           "132", "상품상세설명 참조"),
    PD065_133_RISK_CONCERN_SELF_CHECK(               "133", "[위해우려제품]자가검사번호");

    private String dtlsCd;
    private String dtlsComNm;
    PD065_PrdCertTypeCd(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    public String getDtlsCd() {
        return dtlsCd;
    }

    public String getDtlsComNm() {
        return dtlsComNm;
    }

    public String getGrpCd() {
        return "PD065";
    }

    public String getGrpCdNm() {
        return "상품인증유형코드";
    }
}
