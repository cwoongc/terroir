package com.elevenst.terroir.product.registration.option.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdOptValueVO implements Serializable {
    private static final long serialVersionUID = 207085399094496723L;

    private long prdNo;
    private long optItemNo;
    private long optValueNo;
    private String mixOptItemNo;
    private String mixOptValueNo;
    private String optValueNm;
    private long addPrc;
    private String prdStckStatCd;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
    private String optItemNm;
    private long regRnk;
    private long optWght;
    private long stckQty;
    private long rntlCst;

    private long ctlgNo;		// 마트옵션, 카탈로그번호
    private String ctlgOptNm;	// 마트옵션, 카탈로그 속성, 옵션정보
    private String addDesc;		// 마트옵션, 추가설명
    private String spplBnftCd;	// 마트옵션, 추가혜택 코드 PD167
    private String spplBnftNm;	// 마트옵션, 추가혜택 명
    private String dispModelNm;
    private String optValue;

    // 페이징처리시 필요 2010.12.08 모바일SO에서 사용됨
    private long totalCount; // 페이징때 사용되는 카운트
    private long start = -1; // -1로 초기화
    private long limit = -1; // -1로 초기화

    private long eventNo;	// 쇼킹딜 신청번호

    private String smartRepImageUrl;	//스마트 옵션 대표 이미지
    private String smartDetailImageUrl;	//스마트 옵션 상세 이미지
    private String userOptName1;
    private String userOptName2;
    private String userOptName3;
    private String introImageUrl;
    private String outroImageUrl;
    private int smartOptCnt;
    private String sellerStockCd;
    private String dgstExtNm;
    private long optValueSeq;

    private long minAddPrc;
    private long basiAmt;
    private String basiUntNm;
    private String secondOptItemNm;
    private String secondOptValueNm;
    private String thirdOptItemNm;
    private String thirdOptValueNm;
    private String retDtlExtNm;
    private long selSumQty;
    private long rnk;
    private long stckQtyRatio;
    private Date createDtDate;
    private long stdOptNo;
    private long stdOptValNo;

    /**
     * @param optValueNm the optValueNm to set
     */
    public void setOptValueNm(String optValueNm) {
        if(optValueNm != null){
            this.optValueNm = optValueNm.replaceAll("\n", "").replaceAll("\r", "");
        }
        else{
            this.optValueNm = optValueNm;
        }
    }
}
