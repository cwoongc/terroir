package com.elevenst.terroir.product.registration.image.code;

public enum ImageKind {
    /**
     * B    기본이미지
     * A    추가 공통
     * A1~3 추가이미지1~3
     * D    카드뷰 이미지
     * L300 목록이미지
     * WD   모바일
     * U1   제휴사 전문관
     * O    여행
     * S    상품 TOPSTYLE
     * G    GIFT
     * E
     */
    B("B"), A("A"), A1("A1"), A2("A2"), A3("A3"), L300("L300"), L80("L80"), L170("L170"), S("S"), WD("WD"), U1("U1"), D("D"), O("O"), G("G"), E("E");

    String imageKind;
    ImageKind(String imageKind){this.imageKind = imageKind;}

    @Override
    public String toString() {
        return imageKind;
    }
}
