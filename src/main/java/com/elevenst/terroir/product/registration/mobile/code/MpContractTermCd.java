package com.elevenst.terroir.product.registration.mobile.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum MpContractTermCd implements GroupCodeInterface {
    NON_CONTRACT("01","무약정"),
    TWELVE_MONTH("02","12개월"),
    EIGHTEEN_MONTH("03","18개월"),
    TWENTYFOUR_MONTH("04","24개월"),
    THIRTH_MONTH("05","30개월"),
    THIRTHSIX_MONTH("06","36개월")
    ;

    private String dtlsCd;
    private String dtlsComNm;

    MpContractTermCd(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    @Override
    public String getGrpCd() {
        return "PD120";
    }

    @Override
    public String getGrpCdNm() {
        return "휴대폰 약정할인";
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
