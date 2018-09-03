package com.elevenst.terroir.product.registration.fee.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SeFeeItm implements Serializable {

    private static final long serialVersionUID = 7930725010460654645L;
    private long feeItmNo;
    private String feeItmNm;
    private String aplBgnDy;
    private String aplEndDy;
    private String feeKdCd;
    private String feeTypCd;
    private String feeAplUnitCd;
    private double fee;
    private String frcEndYn;
    private String feeItmDesc;
    private String rngUseYn;
    private String prgrsAplYn;
    private String selFeeAplObjClfCd;
    private String selFeeAplObjNo;
    private long dispCtgrNo;
    private String selMthdCd;
    private String basiSelFeeYn;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
    private String createIp;
    private String updateIp;
    private String requestDt;
    private long requestNo;
    private String approveDt;
    private long approveNo;
    private double addPrdFee;
    private String consultNo;
    private String sellerAgreeYn;
    private String prolAgreeYn;

    // 히스토리 저장시 사용 변수들
    private String dmlTypCd = "";               // I/U/D 삽입/갱신/삭제 코드
    private String histOccrDt = "";				// 히스토리 저장일시

    private List<SeFeeAplRng> seFeeAplRngBOList; // 구간적용된경우 구간에 대한 정보리스트

}
