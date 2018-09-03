package com.elevenst.terroir.product.registration.seller.vo;

import lombok.Data;

@Data
public class SellerVO {

    private SellerAuthVO sellerAuthVO = null;
    private SellerServiceLmtVO sellerServiceLmtVO = null;

    // 판매주체닉네임일련번호
    private long selMnbdNckNmSeq = 0;

    private boolean sellableBundlePrdSeller;	// 상품 구성 세팅 가능한 셀러
    private boolean onlyUsableSetTypCd;	// 상품 구성만 사용 가능한 셀러
    private boolean globalItgSeller;
    private long cyberMoney = 0;
    private boolean mobileRegSeller; // 모바일 등록 셀러 여부
    private boolean roadShopSeller; // 11ED 사용가능셀러 여부(연관상품, 태그)
    private boolean freeImgMember; // 이미지 사이즈 자유셀러 여부

    // 제주/도서산간 정보 세팅
    private String jejuDlvCst;
    private String islandDlvCst;
    private String islandUseYn;

    // 반품/교환 배송비 (+ 초기 배송비 무료시 부과방법)
    private String rtngdDlvCst;
    private String exchDlvCst;
    private String rtngdDlvCd;
}
