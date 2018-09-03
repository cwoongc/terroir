package com.elevenst.terroir.product.registration.common.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum MbAddrLocation implements GroupCodeInterface{

    DOMESTIC("01","국내"),
    OVERSEA("02", "해외");

    public final String groupCode = "";
    public final String groupCodeName = "회원 주소지 국내 해외 구분";
    public final String code;
    public final String codeName;



    MbAddrLocation(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    @Override
    public String getGrpCd() {
        return groupCode;
    }

    @Override
    public String getGrpCdNm() {
        return groupCodeName;
    }

    @Override
    public String getDtlsCd() {
        return code;
    }

    @Override
    public String getDtlsComNm() {
        return codeName;
    }



}
