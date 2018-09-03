package com.elevenst.terroir.product.registration.category.code;

public enum DispCtgrKindCd {
    DISP_CTGR_KIND_CD_01("01", "전시"),
    DISP_CTGR_KIND_CD_02("02", "프로모션"),
    DISP_CTGR_KIND_CD_03("03", "브랜드"),
    DISP_CTGR_KIND_CD_05("05", "추가전시");

    public final String groupCode = "?";
    public final String groupCodeName = "배송쿠폰코드";
    public final String code;
    public final String codeName;


    DispCtgrKindCd(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }
}
