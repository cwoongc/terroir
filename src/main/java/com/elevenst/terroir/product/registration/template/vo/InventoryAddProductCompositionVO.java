package com.elevenst.terroir.product.registration.template.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class InventoryAddProductCompositionVO implements Serializable {

    /**
     * 무엇을 mapping할지 몰라 기존 정보 가지고 옴
     ***/

    private long prdCompNo;
    private long mainPrdNo;
    private String optStr;
    private String chkSelBgnDy;      //템플릿 상품등록 반영 체크를 위해 추가
    private String chkSelEndDy;      //템플릿 상품등록 반영 체크를 위해 추가
    private String chkSelStatCd;     //템플릿 상품등록 반영 체크를 위해 추가

    PdPrdLstAddPrdCompVO pdPrdLstAddPrdCompVO;
}
