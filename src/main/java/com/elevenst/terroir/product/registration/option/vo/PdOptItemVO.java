package com.elevenst.terroir.product.registration.option.vo;

import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PdOptItemVO implements Serializable {
    private static final long serialVersionUID = 3153817434615781062L;

    private long prdNo;
    private long optItemNo;
    private String optClfCd;
    private String optItemNm;
    private long stockClmnPos;
    private String statCd;
    private String prdExposeClfCd;
    private String useYn;

    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
    private double cstmOptAplPrc;
    private String optTypCd;

    private String martPrdYn;			// 서비스상품, 마트상품 일 경우 Y
    private String groupNumber;
    private String dgstExtNm;

    // 옵션 Item 존재여부
    private boolean isExistOptItem = false;

    private List<PdOptValueVO> pdOptValueVOList = new ArrayList<PdOptValueVO>();
    private List<PdOptMartComboVO> PdOptMartComboVOList = new ArrayList<PdOptMartComboVO>();

    private String histAplEndDt;	// 이력적용 종료일시
    private String chgType;			// 이력변경타입 (I:등록, U:수정, D:삭제)

    private int tuneOptItemNo; 	    // 변경될OptItemNo (해외명품직매입  간략PO에서 옵션 수정시...)
    private long eventNo; // 쇼킹딜 신청번호 (0보다 큰 경우 쇼킹딜 페이지로 간주)
    //private ProductConstDef.PrdInfoType prdInfoType = ProductConstDef.PrdInfoType.ORIGINAL;
    private String prdInfoType = ProductConstDef.PrdInfoType.ORIGINAL.name();
    private long stdOptNo;

    /**
     * @param optItemNm the optItemNm to set
     */
    public void setOptItemNm(String optItemNm) {
        if(optItemNm != null){
            this.optItemNm = optItemNm.replaceAll("\n", "").replaceAll("\r", "");
        }
        else{
            this.optItemNm = optItemNm;
        }
    }
}
