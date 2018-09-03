package com.elevenst.terroir.product.registration.category.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DpDispCtgrMetaVO implements Serializable {
    private static final long serialVersionUID = 7274465375804872038L;

    /** 카테고리그룹군코드 */
    private String ctgrGrpCd;

    /** 카테고리레벨 */
    private int ctgrLv;

    /** 참조카테고리코드(전시카테고리) */
    private String refCtgrCd;

    /**************************************/
    /** DP_DISP_CTGR_META TABLE */
    /**************************************/
    /** 카테고리번호 */
    private long ctgrNo;

    /** 참조카테고리번호(전시카테고리) */
    private long refCtgrNo;

    /** 상위카테고리번호 */
    private long hgCtgrNo;

    /** 카테고리명 */
    private String ctgrNm;

    /** 기본 카테고리 아이콘 **/
    private String ctgrIconUrl = "";

    /** 카테고리 선택시 아이콘 **/
    private String ctgrIconSlctUrl = "";

    /** 카테고리영문명 */
    private String ctgrEngNm;

    /** 영문사이트 노출여부 */
    private String engDispYn;

    /** 전시상태 */
    private String ctgrStatCd;

    /** 카테고리코드 */
    private String ctgrCd;

    /** 전시우선순위 */
    private int dispRnk;

    /** 등록일 */
    private String createDt;

    /** 등록자 */
    private int createNo;

    /** 수정일 */
    private String updateDt;

    /** 수정자 */
    private int updateNo;

    /** 카테고리종류코드(01:메타 | 02:전시 | 03:프로모션) */
    private String ctgrKindCd;

    /** 카테고리종류코드(01:전시 | 05:추가전시) */
    private String refCtgrKindCd;

    /** 카테고리레벨 */
    private int refCtgrLevel;

    /** 연결 카테고리 아이콘 이미지 */
    private String refCtgrIconUrl;

    /** 사용여부 */
    private String useYn;

    /** 카테고리 강조아이콘코드(01:NEW | 02:HOT) */
    private String ctgrIconCd;

    /** 카테고리 강조글자색 */
    private String ctgrTxtColor;

    /** 카테고리 강조볼드사용여부(Y/N) */
    private String ctgrBoldUseYn;

    /** 카테고리 링크URL */
    private String ctgrLinkUrl;

    /** 카테고리 링크팝업여부(Y/N) */
    private String ctgrLinkPopupYn;

    /** 부가정보 노출사용여부(Y/N) */
    private String addInfoUseYn;

    /** 부가정보 노출정보(Class) */
    private String addInfoClassNm;

    /** 대체텍스트(String) */
    private String addInfoClassText;

    /** 부가정보 링크사용여부(Y/N) */
    private String addInfoLinkUseYn;

    /** 부가정보 링크URL */
    private String addInfoLinkUrl;

    /** 부가정보 링크팝업여부(Y/N) */
    private String addInfoLinkPopupYn;

    /** 뎁스2 카운트 */
    private int depth2Count;

    private String isLeaf;
    private String lev;
    private String nodeType;
    private String empNo;
    private String empNm;
    private String empId;

    /** 카테고리 영문명 **/
    private String dispCtgrEngNm;

//    private List<skt.tmall.business.browsing.listings.domain.DisplayCategoryBO> subDisplayCategoryBOList;
    private List<DpDispCtgrMetaVO> subLargeCtgrList;

    private String gblDlvYn;
    private int tMemberShipRate;

    private String bestSellerType;

    private String createNm;
    //페이징
    private int start;
    private int limit;
    private int totalCnt;

    private String selectedYn;
}
