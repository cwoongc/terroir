package com.elevenst.terroir.product.registration.product.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrdGrp implements Serializable {

    private static final long serialVersionUID = -6492485943991314759L;

    private long prdGrpNo = 0;
    private String prdGrpNm = "";
    private long selMnbdNo = 0;
    private long basiCtgrNo = 0;
    private long basiCtgrLevel = 0;
    // 기준배송방식코드 [PD016]
    private String basiDlvMthdCd = "";
    // 기준상푸구분코드 [PD018]
    private String basiPrdClfCd = "";
    private String basiDlvCanRgnCd = "";
    private String minorBuyCanYn = "";
    private String dispYn = "";
    private String dispBgnDt = "";
    private String dispEndDt = "";
    // 상품그룹유형코드 [PD261]
    private String prdGrpTypCd = "";
    private String grpDtlImgUrl = "";
    private String grpDispTypCd = "";
    private String createDt = "";
    private long createNo = 0;
    private String updateDt = "";
    private long updateNo = 0;
}
