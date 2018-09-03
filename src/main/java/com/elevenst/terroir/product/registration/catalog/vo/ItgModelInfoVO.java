package com.elevenst.terroir.product.registration.catalog.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItgModelInfoVO extends CatalogCmnVO implements Serializable {

    private static final long serialVersionUID = -6121234958398801792L;
    private long itgModelNo = 0;
    private long matchCtlgNo = 0;
    private long itgPrdCnt = 0;
    private long matchNo = 0;
    private long dispCtgr1No = 0;
    private long dispCtgr2No = 0;
    private long dispCtgr3No = 0;
    private long dispCtgr4No = 0;
    private long pcsModelCnt = 0;
    private long prdNo = 0;
    private long prdCnt = 0;
    private long dscPrc = 0;
    private long prrtRnk = 0;
    private long attrNo = 0;
    private long attrValueNo = 0;
    private long exptItgModelNo = 0;
    private long imgContCnt = 0;
    private long uniqModelNo = 0;
    private long score = 0;
    private long dupPrdCnt = 0;
    private long brandAttrNo = 0;
    private long mnfcAttrNo = 0;
    private long newItemAttrNo = 0;
    private long adultAttrNo = 0;
    private long uniqModelCdAttrNo = 0;
    private long catalogNameAttrNo = 0;
    private long dispCtgrNoAttrNo = 0;
    private long rnum = 0;
    private long tgtItgModelNo = 0;

    private String itgModelNm = null;
    private String modelId = null;
    private String pcsTypCd = null;
    private String useYn = null;
    private String modelStatCd = null;
    private String matchDt = null;
    private String searchDateType = null;
    private String searchBgnDate = null;
    private String searchEndDate = null;
    private String dispCtgrNm = null;
    private String imgContPath = null;
    private String brandNm = null;
    private String mnfcNm = null;
    private String modelStatCdNm = null;
    private String newItemYn = null;
    private String adultYn = null;
    private String prdNm = null;
    private String sellerNm = null;
    private String sellerId = null;
    private String selStatCd = null;
    private String selStatCdNm = null;
    private String createCdNm = null;
    private String empId = null;
    private String empNm = null;
    private String histAplDt = null;
    private String contTypCd = null;
    private String dispYn = null;
    private String contPath = null;
    private String attrValueNm = null;
    private String srcTypCd = null;
    private String srcTypCdNm = null;
    private String brandCd = null;
    private String brandTypCd = null;
    private String synonymNm = null;
    private String brandTypCdNm = null;
    private String newItemAttrDate = null;
    private String uniqModelCd = null;
    private String pcsTypCdNm = null;
    private String modelNm = null;
    private String groupYn = null;
    private String sameYn = null;
    private String failMsg = null;
    private String exptYn = null;
    private String updateType = null;
    private String prdNoStr = null;
    private String otherPrdYn = null;
    private String itgModelNoStr = null;
    private String mnbdClfCd = null;
    private String minorSelCnYn = null;
    private String updateMode = null;
    private String categorySearchYn = null;
    private String brandSearchYn = null;
    private String indexType = null;
    private String subIndexType = null;
    private String matchClfCd = null;
    private String ctntDesc = null;				//이미지 상세설명

    private long nextPrrtRnk = 0;		//20161017 스마트 카탈로그 - 카탈로그 통합
    private long stdCtlgNo = 0;			//20161017 스마트 카탈로그 - 카탈로그 통합(기준카탈로그코드)

    // [신규] 20160.10.28 카탈로그-상품 매칭 정보 등록 코드
    private String matchTrtClfCd = null;
}
