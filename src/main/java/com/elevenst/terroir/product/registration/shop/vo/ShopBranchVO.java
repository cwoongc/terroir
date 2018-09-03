package com.elevenst.terroir.product.registration.shop.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 지점
 */
@Data
public class ShopBranchVO implements Serializable{



    /**
     * 상점번호
     */
    private long shopNo;
    private String shopNm;
    private long prdNo;

    /**
     * 지점번호
     */
    private long shopBranchNo;

    /**
     * 지점유형코드 [DI173]:본점(01),지점(02)
     */
    private String shopBranchTypCd;

    /**
     * 지점종류코드 [DI174]:일반상점(01), 배달상점02)
     */
    private String shopBranchKindCd;

    /**
     * 지점명
     */
    private String shopBranchNm;

    /**
     * 지점코드
     */
    private String shopBranchCd;

    /**
     * 비밀번호
     */
    private String shopScrtNo;

    /**
     * 우편번호
     */
    private String mailNo;

    /**
     * 우편번호 순번
     * (derived)
     */
    private String mailNoSeq;

    /**
     * 기본주소
     */
    private String baseAddr;

    /**
     * 상세주소
     */
    private String dtlsAddr;


    /**
     * 전시우선순위
     */
    private long dispPrrtRnk;


    /**
     * 전시 대 카테고리 번호
     */
    private long dispCtgr1NoArea;

    /**
     * 전시 중 카테고리 번호
     */
    private long dispCtgr2NoArea;

    /**
     * 전시 소 카테고리 번호
     */
    private long dispCtgr3NoArea;

    /**
     * 전시 세 카테고리 번호
     */
    private long dispCtgr4NoArea;

    /**
     * 상태 YN
     */
    private String useYn;

    /**
     * 등록일시
     */
    private String createDt;

    /**
     * 등록자번호
     */
    private long createNo;

    /**
     * 수정일시
     */
    private String updateDt;

    /**
     * 수정자번호
     */
    private long updateNo;


    /**
     * Map 위치 X(경도)
     */
    private long mapPointX;

    /**
     * Map 위치 Y(위도)
     */
    private long mapPointY;

    /**
     * TMap 위치 X(경도)
     */
    private String mapPosX;

    /**
     * TMap 위치 Y(위도)
     */
    private String mapPosY;

    /**
     * TMap 위치 X(경도, GRS80GEO)
     */
    private String mapPosXLifePlus;

    /**
     * TMap 위치 Y(위도, GRS80GEO)
     */
    private String mapPosYLifePlus;

    /**
     * 지점이미지(URL)
     */
    private String strImgUrl;

    /**
     * 광고문구내용
     */
    private String advrtStmtCont;

    /**
     * 길안내문구
     */
    private String strtGuideStmtCont;

    /**
     * 주차가능여부
     */
    private String parkingCanYn;

    /**
     * 지점소개내용
     */
    private String strIntrImgUrl;

    /**
     * 상점전화번호 : 대표번호, 추가번호1
     */
    private String telNoStr;

    /**
     * 지역카테고리이름 연결
     */
    private String dispCtgrNmAreaDE;

    /**
     * 회원번호
     */
    private long memNo;

    /**
     * 셀러번호(판매자번호)
     */
    private long selMnbdNo;

    /**
     * 판매자 ID
     */
    private String memId;


    /**
     * 판매자 명
     */
    private String memNm;

    private long saleTimeCnt;
    private long promotionCnt;
    private boolean requiredLifeplusColumn;






























}
