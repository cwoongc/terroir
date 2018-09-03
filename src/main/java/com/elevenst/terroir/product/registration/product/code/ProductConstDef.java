package com.elevenst.terroir.product.registration.product.code;

import com.elevenst.common.util.StringUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductConstDef {

    /**
     * 판매자 member type
     */
    public static String PRD_MEM_TYPE_PROVIDER				= "02";	// 사업자회원 (구매 및 판매 활동하는 회원)
    public static String PRD_MEM_TYPE_GLOBAL				= "03";	// 해외쇼핑 카테고리 등록가능 판매자
    public static String PRD_MEM_TYPE_SUPPLIER				= "04";	// 공급업체ID
    public static String PRD_MEM_TYPE_INTEGRATION			= "05";	// 통합로그인ID

    public static long BOOK_DVD_CTGR_NO						= 2967;			// 도서/음반/DVD 카테고리
    public static long BOOK_DVD_CTGR_NO_GLOBAL				= 10040;		// 도서/음반/DVD 해외쇼핑 카테고리
    public static long HELTH_DRINK_CTGR_NO					= 2790;			// 건강/음료/가공식품
    public static long RICE_FRUIT_CTGR_NO					= 14617; 		// 쌀/과일/농수축산물 카테고리
    public static long RENTAL_WATER_FILTER_CTGR_NO			= 128644;		// 렌탈 /정수기 카테고리 128644  - 기존 27645
    public static long HELTH_DIET_HONGSAM_CTGR_NO			= 127648;		// 건강식품/다이어트/홍삼 카테고리
    public static long BABY_ITEM_CTGR_NO					= 2072;			// 분유/기저귀/물티슈
    public static long GOLD_SHOP_CTGR_NO					= 4708;			// 시계/주얼리> 순금매장
    public static String GLOBAL_LCTGR_NO					= "|8286|";		// 해외카테고리
    public static long BRAND_ADD_CTGR_NO					= 948206;		// 브랜드 추가 전시 카테고리

    /**
     * 상품구분 코드 관련 (PD018)
     */
    public static String PRD_TYP_CD							= "PD018";		// 상품구분코드
    public static String PRD_TYP_CD_NORMAL					= "01";			// 일반배송상품
    public static String PRD_TYP_CD_SVC_CUPN				    = "07";			// 서비스쿠폰상품
    public static String PRD_TYP_CD_SVC_CTGR				    = "09";			// 서비스카테고리상품
    public static String PRD_TYP_CD_TOUR					    = "13";			// 여행제휴상품
    public static String PRD_TYP_CD_DIGITAL					= "12";			// 디지털 컨텐츠 상품
    public static String PRD_TYP_CD_MART					    = "11";			// 마트 상품 -> 지점배송 마트상품 으로 변경
    public static String PRD_TYP_CD_POINT					    = "14";			// 포인트 상품권
    public static String PRD_TYP_CD_SKI						= "15";			// 스키시즌권
    public static String PRD_TYP_CD_DELIVERY				    = "16";			// 택배서비스상품
    public static String PRD_TYP_CD_SOCIAL_NET 				= "17";			// 소셜 네트워크 상품(소셜무형상품)
    public static String PRD_TYP_CD_SMS 				        = "10";			// SMS 전송상품
    public static String PRD_TYP_CD_SMS_CUPN 				    = "09";			// SMS 전송&쿠폰출력 상품
    public static String PRD_TYP_CD_SOCIAL_TANGIBLE			= "18";			// 소셜 네트워크 상품(소셜유형상품) 2011.12.15
    public static String PRD_TYP_CD_TOWN_SALES				= "19";			// 타운 판매용 상품 2012.03.28
    public static String PRD_TYP_CD_TOWN_PROMOTE			    = "20";			// 타운 홍보용 상품 2012.03.28
    public static String PRD_TYP_CD_TOWN_DELIVERY			    = "21";			// 타운 배달용 상품 2012.03.28
    public static String PRD_TYP_CD_TICKET					= "22";			// 제휴 티켓 상품 2012.09.15
    public static String PRD_TYP_CD_11PAY					= "23";			// 11PAY 상품
    public static String PRD_TYP_CD_SOHO					= "24";			// 소호 상품 2013.05.04
    public static String PRD_TYP_CD_RENTAL					= "26";			// 렌탈 2013.03.29
    public static String PRD_TYP_CD_EMAIL_SMS 				= "25";			// E-mail&SMS 이용권 전송
    public static String PRD_TYP_CD_NEW_MART				= "28";			// 마트 상품, new
    public static String PRD_TYP_CD_TOUR_INTER				= "29";			// 여행 내재화 여행상품
    public static String PRD_TYP_CD_TOUR_HOTEL_INTER		= "30";			// 여행 내재화 숙박상품

    public static String PRD_TYP_CD_LIFE_DEFAULT 				= "31";		// 원클릭 체크아웃 LIFE 기본형.
    public static String PRD_TYP_CD_SERVICE_OUTSOURCING 	= "32";		// 원클릭 체크아웃 LIFE 서비스.	용역 서비스 형.
    public static String PRD_TYP_CD_LIFE_DELIVERY				= "33";		// 원클릭 체크아웃 LIFE 배송상품형.
    public static String PRD_TYP_CD_LIFE_REQ_RESERVE			= "34";		// 원클릭 체크아웃 LIFE 예약 신청형.
    public static String PRD_TYP_CD_LIFE_TOWN_VISIT				= "35";		// LIFE 매장방문형.
    public static String PRD_TYP_CD_LIFE_BUSINESS_TRIP_SERVICE  = "36";		// LIFE 출장서비스형.
    public static String PRD_TYP_CD_LIFE_PICKUP_N_DEVLIVERY  = "37";		// LIFE 출장수거배달형.

    /**
     * 인증상태
     * @author leegt80
     */
    public static final String CERT_STATUS_100 = "100";	// 주문가능
    public static final String CERT_STATUS_101 = "101";	// 미인증
    public static final String CERT_STATUS_102 = "102";	// 인증완료
    public static final String CERT_STATUS_103 = "103";	// 인증취소
    public static final String CERT_STATUS_104 = "104";	// 인증불가(반품완료)
    public static final String CERT_STATUS_105 = "105";	// 인증중지

    /**
     * 인증 SMS 상태
     * @author leegt80
     */
    public static final String CERT_SMS_100 = "100";	// 미전송
    public static final String CERT_SMS_101 = "101";	// 인증번호전송
    public static final String CERT_SMS_102 = "102";	// 인증번호재전송
    public static final String CERT_SMS_103 = "103";	// 인증확인전송
    public static final String CERT_SMS_104 = "104";	// 인증중지전송
    public static final String CERT_SMS_105 = "105";	// 인증취소전송
    public static final String CERT_SMS_106 = "106";	// 인증취소전송

    /**
     * 주문상태
     * @author leegt80
     */
    public static final String PRD_ORD_STAT_202 = "202";	// 결제완료
    public static final String PRD_ORD_STAT_501 = "501";	// 발송완료
    public static final String PRD_ORD_STAT_601 = "601";	// 클레임 진행
    public static final String PRD_ORD_STAT_901 = "901";	// 구매확정

    /**
     *
     */
    public static final String CERT_TYPE_100 = "100";		// 내부인증번호
    public static final String CERT_TYPE_101 = "101";		// 엑셀 업로드 인증번호 관리
    public static final String CERT_TYPE_102 = "102";		// 외부인증번호

    /**
     * 인증상태 처리에 따른 Desc
     */
    public static final String CERT_STATUS_DESC1 = "정상적으로 인증 완료되었습니다.";						// 인증 완료/반품 거부 처리 Description
    public static final String CERT_STATUS_DESC2 = "정상적으로 인증 취소되었습니다.";						// 인증 취소 처리 Description
    public static final String CERT_STATUS_DESC3 = "정상적으로 반품 완료되었습니다.";						// 인증 반품완료 처리 Description
    public static final String CERT_STATUS_DESC4 = "상점폐쇠로 인해 정상적으로 인증 중지되었습니다.";	// 인증 중지 처리 Description

    /**
     * 인증 SMS 발송 전화번호
     */
    public static final String SEND_TO_SMS_MESSAGE_PHONE = "15990110";	// Call Center

    /**
     * SMS 전송 TID (Template ID)
     */
    public static final int CERT_SMS_TID1 = 6007;	// 인증확인 SMS TID
    public static final int CERT_SMS_TID2 = 6008;	// 인증취소 SMS TID

    /**
     * 원산지 코드 관련 (PD003)
     */
    public static String PRD_ORGN_TYP_CD					= "PD003";
    public static String PRD_ORGN_TYP_CD_LOCAL				= "01";			// 국내
    public static String PRD_ORGN_TYP_CD_ABRD				= "02";			// 해외
    public static String PRD_ORGN_TYP_CD_EXT				= "03";			// 기타

    public static String PRD_ORGN_DTL_TYP_CD				= "PD054";		// 원산지 상세코드
    public static String FR_PRD_ORGN_DTL_TYP_CD				= "FR004";		// 원산지 상세코드 (영문버전)

    /**
     * 과세면세 구분 코드 관련 (PD020)
     */
    public static String PRD_SUPL_DTYFR_PRD_CLF_CD			= "PD020";
    public static String PRD_SUPL_DTYFR_PRD_CLF_CD_TAX		= "01";			// 과세
    public static String PRD_SUPL_DTYFR_PRD_CLF_CD_TAX_FREE	= "02";			// 면세
    public static String PRD_SUPL_DTYFR_PRD_CLF_CD_SMALL	= "03";			// 영세
    public static String PRD_SUPL_DTYFR_PRD_CLF_CD_NON_TAX	= "04";			// 비과세

    public static boolean isValidCompPrdVatCd(String compPrdVatCd) {
        return compPrdVatCd != null &&
            (compPrdVatCd.equals(ProductConstDef.PRD_SUPL_DTYFR_PRD_CLF_CD_TAX)
                || compPrdVatCd.equals(ProductConstDef.PRD_SUPL_DTYFR_PRD_CLF_CD_TAX_FREE)
                || compPrdVatCd.equals(ProductConstDef.PRD_SUPL_DTYFR_PRD_CLF_CD_SMALL));
    }
    /**
     * 상품상태 코드 관련 (PD019)
     */
    public static String PRD_STAT_CD						= "PD019";
    public static String PRD_STAT_CD_NEW					= "01";			// 새상품
    public static String PRD_STAT_CD_USED					= "02";			// 중고상품
    public static String PRD_STAT_CD_IMPROVE				= "03";			// 재고상품
    public static String PRD_STAT_CD_REFUR					= "04";			// 리퍼상품
    public static String PRD_STAT_CD_EXHIBIT				= "05";			// 전시(진열)상품
    public static String PRD_STAT_CD_RARITY					= "07";			// 희귀소장품
    public static String PRD_STAT_CD_RETURN					= "08";			// 반품상품
    public static String PRD_STAT_CD_SCRATCH				= "09";			// 스크래치상품
    public static String PRD_STAT_CD_ORDER					= "10";			// 주문제작상품
    public static String PRD_STAT_CD_INFO					= "11";			// PIN(정보입력)상품

    /**
     * 상품 판매방식코드 관련 (PD012)
     */
    public static String PRD_SEL_MTHD_CD					= "PD012";
    public static String PRD_SEL_MTHD_CD_FIXED				= "01";			// 고정가
    public static String PRD_SEL_MTHD_CD_COBUY				= "02";			// 공동구매
    public static String PRD_SEL_MTHD_CD_AUCT				= "03";			// 경매
    public static String PRD_SEL_MTHD_CD_PLN				= "04";			// 예약판매
    public static String PRD_SEL_MTHD_CD_USED				= "05";			// 중고판매
    public static String PRD_SEL_MTHD_CD_TOWN				= "99";			// 타운판매 : DB 데이터 없이 소스에서만 사용
    /**
     * 상품 판매상태코드 관련 (PD014)
     */
    public static String PRD_SEL_STAT_CD					= "PD014";
    public static String PRD_SEL_STAT_CD_BEFORE_APPROVE		= "101";		// 승인대기
    public static String PRD_SEL_STAT_CD_BEFORE_DISPLAY		= "102";		// 전시전
    public static String PRD_SEL_STAT_CD_SALE				= "103";		// 판매중
    public static String PRD_SEL_STAT_CD_SOLDOUT			= "104";		// 품절
    public static String PRD_SEL_STAT_CD_STOP_DISPLAY		= "105";		// 판매중지 - 전시중지
    public static String PRD_SEL_STAT_CD_CLOSE_SALE			= "106";		// 판매종료  - 판매정상종료
    public static String PRD_SEL_STAT_CD_STOP_SALE			= "107";		// 판매강제종료    - SO 개편시 사용안함 됨.
    public static String PRD_SEL_STAT_CD_BAN_SALE			= "108";		// 판매금지
    public static String PRD_SEL_STAT_CD_REJECT_APPROVE		= "109";		// 승인거부
    //public static String PRD_SEL_STAT_CD_APPROVE			= "110";		// 승인완료  - SO 개편시 삭제처리 됨.
    public static String FR_PRD_SEL_STAT_CD					= "FR003";		// 상품 판매상태코드 (영문버전)
    public static String FR_PRD_SEL_STAT_CD_TEMPORARY		= "100";		// 임시저장  - 해외직구매상품 관련 추가(2011.09.20)

    /** 상품 판매상태코드명 */
    public static String PRD_SEL_STAT_NM_BEFORE_APPROVE		= "승인대기";	// 승인대기
    public static String PRD_SEL_STAT_NM_BEFORE_DISPLAY		= "전시전";		// 전시전
    public static String PRD_SEL_STAT_NM_SALE				= "판매중";		// 판매중
    public static String PRD_SEL_STAT_NM_SOLDOUT			= "품절";		// 품절
    public static String PRD_SEL_STAT_NM_STOP_DISPLAY		= "판매중지";	// 판매중지  - 전시중지
    public static String PRD_SEL_STAT_NM_CLOSE_SALE			= "판매종료";	// 판매종료  - 판매정상종료
    public static String PRD_SEL_STAT_NM_STOP_SALE			= "판매강제종료";	// 판매강제종료    - SO 개편시 사용안함 됨.
    public static String PRD_SEL_STAT_NM_BAN_SALE			= "판매금지";	// 판매금지
    public static String PRD_SEL_STAT_NM_REJECT_APPROVE		= "승인거부";	// 승인거부
    //public static String PRD_SEL_STAT_NM_APPROVE			= "승인완료";	// 승인완료  - SO 개편시 삭제처리 됨.

    // 경매상품 판매상태코드
    public static String PRD_AUCT_SEL_STAT_CD_BEFORE_APPROVE	= "201";	// 승인대기
    public static String PRD_AUCT_SEL_STAT_CD_BEFORE_SALE		= "202";	// 전시전 - 진행예정
    public static String PRD_AUCT_SEL_STAT_CD_SALE				= "203";	// 판매중 - 진행중
    public static String PRD_AUCT_SEL_STAT_CD_SOLD				= "204";	// 낙찰정상종료
    public static String PRD_AUCT_SEL_STAT_CD_DONT_SELL			= "205";	// 유찰정상종료
    public static String PRD_AUCT_SEL_STAT_CD_CLOSE_SALE		= "206";	// 경매중지
    public static String PRD_AUCT_SEL_STAT_CD_STOP_SALE			= "207";	// 판매중지 - 경매강제종료
    public static String PRD_AUCT_SEL_STAT_CD_REJECT_APPROVE	= "208";	// 승인거부
    //public static String PRD_AUCT_SEL_STAT_CD_APPROVE			= "209";	// 승인완료  - SO 개편시 삭제처리 됨.


    /**
     * 출고지/반품지 주소구분코드 (회원용 Table기준)
     */
    public static String MEM_PRD_ADDR_CD_OUT				= "02";		// 출고지 주소구분
    public static String MEM_CONSIGN_PRD_ADDR_CD_OUT		= "12";		// 위탁 출고지 주소구분
    public static String MEM_PRD_ADDR_CD_IN					= "03";		// 반품지 주소구분
    public static String MEM_CONSIGN_PRD_ADDR_CD_IN			= "13";		// 위탁 반품지 주소구분
    public static String MEM_PRD_ADDR_CD_VISIT				= "04";		// 방문수령지 주소구분
    /**
     * 출고지/반품지 주소 상품설정 정보 저장용
     */
    public static String PRD_ADDR_CLF_CD                    = "PD035";		// 출고지 주소구분
    public static String PRD_ADDR_CLF_CD_OUT				= "01";		// 출고지 주소구분
    public static String PRD_ADDR_CLF_CD_IN					= "02";		// 반품지 주소구분
    public static String PRD_ADDR_CLF_CD_VISIT				= "03"; 	// 방문수령지 주소구분
    public static String PRD_GLB_ADDR_CLF_CD_OUT			= "04";		// 판매자 해외 출고지 주소구분
    public static String PRD_GLB_ADDR_CLF_CD_IN				= "05";		// 판매자 해외 반품지 주소구분

    /**
     * 판매기간 코드 관련 (PD029)
     */
    public static String PRD_SEL_PRD_CLF_CD					= "PD029";
    public static String PRD_SEL_PRD_CLF_CD_CUST			= "100";		// 판매기간 직접입력
    public static String PRD_SEL_PRD_CLF_CD_NOT_USE_END_DT	= "29991231235959";	// 판매기간 설정안함 종료일

    public static String PRD_SEL_PRD_CLF_CD_CUST_PLN		= "400";		// 판매기간 직접입력(예약)
    public static String PRD_SEL_PRD_CLF_CD_CUST_TOWN		= "500";		// 판매기간 직접입력(타운)

    /**
     * 사용기간 코드 관련 (PD113)
     */
    public static String PRD_TOWN_LMT_CLF_CD				= "PD113";
    public static String PRD_TOWN_LMT_CLF_CD_CUST			= "01";			//사용기간 직접입력
    public static String PRD_TOWN_LMT_CLF_CD_LMT_DY			= "02";			//사용제한일입력
    /**
     * 자동연장 코드 관련 (PD032)
     */
    public static String PRD_AUTO_PRCS_INTRVL_CD			= "PD032";
    /**
     * 자동연장횟수 코드 관련 (PD042)
     */
    public static String PRD_AUTO_PRCS_TMTR_CD				= "PD042";

    /**
     * 최대구매수량 제한
     */
    public static String SEL_LIMIT_TYP_CD_NONE				= "00";		// 제한 안함
    public static String SEL_LIMIT_TYP_CD_TIMES				= "01";		// 1회 제한
    public static String SEL_LIMIT_TYP_CD_PERSON			= "02";		// 1인제한-기간제한

    /**
     * 최대구매수량 1인 기간제한 기본 일수
     */
    public static int SEL_LIMIT_DEFAULT_DAY = 30;

    /**
     * 최소구매수량 제한
     */
    public static String SEL_MIN_LIMIT_TYP_CD_NONE			= "00";		// 제한 안함
    public static String SEL_MIN_LIMIT_TYP_CD_TIMES			= "01";		// 1회 제한

    /**
     * 상품설명유형 구분 코드 (PD022)
     */
    public static String PRD_DESC_TYP_CD					= "PD022";
    public static String PRD_DESC_TYP_CD_PRD_DTL			= "02";			// 상세설명
    public static String PRD_DESC_TYP_CD_PRD_CAUTIONS		= "04";			// 유의사항
    public static String PRD_DESC_TYP_CD_AS_DTL				= "06";			// A/S안내
    public static String PRD_DESC_TYP_CD_RTNG_EXCH_DTL		= "07";			// 반품교환안내
    public static String PRD_DESC_TYP_CD_INSTALL_COST_DTL	= "08";			// 설치비안내
    public static String PRD_DESC_TYP_CD_USED_DTL			= "09";			// 외관,기능상 특이사항
    public static String PRD_DESC_TYP_CD_DLV_DTL			= "10";			// 착불/설치비 안내
    public static String PRD_DESC_TYP_CD_PIN_ADD_INFO		= "11";			// PIN 상품 추가정보
    public static String PRD_INTRO_DESC_TYP_CD				= "12";			// 스마트옵션 인트로 상세설명
    public static String PRD_OUTRO_DESC_TYP_CD				= "13";			// 스마트옵션 아웃트로 상세설명
    public static String PRD_DESC_TYP_CD_MW_PRD_DTL			= "14";			// 모바일상세설명
    public static String PRD_INTRO_DESC_TYP_CD_MW			= "15";			// 모바일스마트옵션인트로상세설명
    public static String PRD_OUTRO_DESC_TYP_CD_MW			= "16";			// 모바일스마트옵션아웃트로상세설명
    public static String PRD_DESC_TYP_CD_E_CP_INFO			= "17";			// E쿠폰상품이용안내


    /**
     * 상품인증기관 구분 코드 (PD065)
     */
    public static String PRD_CERT_CD						= "PD065";

    /**
     * 상품관련 파일 업로드 Path 정보 property 명
     */
    public static String PRD_UPLOAD_PATH_PROP_NM			= "data1.upload.product";

    /**
     * Flex Grid Column 구분자
     */
    public static String FLEX_GRID_COLOUM_DELIMITER			= "\t";

    /**
     * OptionConstDef 구분자
     */
//	public static String OPTION_DELIMITER					= "†";
//	public static String OPTION_DELIMITER_N					= "\n";	//excel 파일에서 옵션 구분자(new line)
//	public static String OPTION_DELIMITER_V					= "|";	//excel 파일에서 옵션 구분자(Verical Bar)
    /**
     * OptionConstDef 옵션명/옵션값 구분자
     */
//	public static String OPTION_ITEM_VALUE_DELIMITER		= ":";
    /**
     * 상품옵션구분
     */
//	public static String OPT_CLF_CD_MIXED					= "01";		// 조합형 옵션
//	public static String OPT_CLF_CD_IDEP					= "02";		// 독립형 옵션
//	public static String OPT_CLF_CD_CUST					= "03";		// 작성형 옵션
//
//	public static String OPT_TYPE_EXCEL_SELECT				= "1";		// 선택형 옵션 Excel 양식 구분값
//	public static String OPT_TYPE_EXCEL_CUST				= "2";		// 작성형 옵션 Excel 양식 구분값
//
//	public static String PRD_STCK_STAT_CD_USE				= "01";		// 재고사용함
//	public static String PRD_STCK_STAT_CD_SOLDOUT			= "02";		// 재고품절
//	public static String PRD_STCK_STAT_CD_NOUSE				= "03";		// 재고사용안함 - 이전 데이터

    /**
     * 옵션가격 +100%/-50% 체크 기준 일자
     */
    public static long DATE_CHECK_OPTION_PRICE				= 20100311;

    /**
     * 상품 판매가 40억까지 제한
     */
    public static long PRD_SEL_PRC_LIMIT					= 4000000000L; // 상품 판매가 40억까지 제한
    public static long PRD_CSTM_APL_PRC_LIMIT				= 1000000000L; // 상품신고가 10억까지 제한
    /**
     * 매입 상품 판매가 10억까지 제한
     */
    public static long PUR_PRD_SEL_PRC_LIMIT				= 1000000000L; // 상품 판매가 10억까지 제한


    /* ####################################################################################
     * 상품 템플릿 관련
     * ####################################################################################*/
    public static String PRD_INFO_TMPLT_CLF_CD_DLV			= "01";		// 배송정보 템플릿
    public static String PRD_INFO_TMPLT_CLF_CD_OPT			= "02";		// 옵션정보 템플릿
    public static String PRD_INFO_TMPLT_CLF_CD_ADDPRD		= "03";		// 추가구성정보 템플릿
    public static String PRD_INFO_TMPLT_CLF_CD_QNA			= "04";		// 추가구성정보 템플릿


	/* ####################################################################################
	 * 배송정보 관련
	 * ####################################################################################*/
    /**
     * 배송비 설정 코드 관련 (PD005)
     */
    public static String DLV_CST_INST_BASI_CD				= "PD005";
    public static String DLV_CST_INST_BASI_CD_FREE			= "01";		// 무료
    public static String DLV_CST_INST_BASI_CD_COLLECT		= "02";		// 고정배송비
    public static String DLV_CST_INST_BASI_CD_PRD_COND_FREE	= "03";		// 상품조건부무료
    public static String DLV_CST_INST_BASI_CD_QTY_GRADE		= "04";		// 수량별차등
    public static String DLV_CST_INST_BASI_CD_UNIT_PRC		= "05";		// 상품1개당 과금
    public static String DLV_CST_INST_BASI_CD_SLR_COND_FREE	= "06";		// 판매자조건부무료
    public static String DLV_CST_INST_BASI_CD_SELLER		= "07";		// 판매자조건부기준(구매금액별)
    public static String DLV_CST_INST_BASI_CD_ADDR			= "08";		// 출고지조건부기준(구매금액별)
    public static String DLV_CST_INST_BASI_CD_ITG_ADDR		= "09";		// 통합출고지조건부기준(구매금액별)
    public static String DLV_CST_INST_BASI_CD_GLB_ITG_ADDR	= "10";		// 해외 통합출고지조건부기준
    public static String DLV_CST_INST_BASI_CD_FR_ITG_ADDR	= "11";		// 해외 명품직매입 통합출고지조건부기준
    public static String DLV_CST_INST_BASI_CD_NOW	        = "12";		// NOW배송

    public static List<String> INVALID_DLV_CD = Arrays.asList(DLV_CST_INST_BASI_CD_QTY_GRADE, DLV_CST_INST_BASI_CD_SLR_COND_FREE,
            DLV_CST_INST_BASI_CD_GLB_ITG_ADDR, DLV_CST_INST_BASI_CD_FR_ITG_ADDR); // API, 대량등록으로 등록불가

    public static List<String> INVALID_CONSIGN_DLV_CD = Arrays.asList(DLV_CST_INST_BASI_CD_SLR_COND_FREE, DLV_CST_INST_BASI_CD_SELLER, DLV_CST_INST_BASI_CD_ADDR
            , DLV_CST_INST_BASI_CD_ITG_ADDR, DLV_CST_INST_BASI_CD_GLB_ITG_ADDR, DLV_CST_INST_BASI_CD_FR_ITG_ADDR); // 셀러위탁배송 시 설정불가

    public static List<String> EXT_CTGR_ATTR_CD = Arrays.asList("03", "05"); // 예외 카테고리 속성 코드

    /**
     * 배송비가능지역 코드 (PD015)
     */
    public static String DLV_CN_AREA_CD						= "PD015";
    public static String DLV_CN_AREA_CD_ALL					= "01";		// 전국

    /**
     * 배송방법 코드 (PD016)
     */
    public static String DLV_WY_CD							= "PD016";
    public static String DLV_WY_CD_DLV						= "01";		// 택배
    public static String DLV_WY_CD_DLV_POST					= "02";		// 우편(소포/등기)
    public static String DLV_WY_CD_NONE_DLV					= "05";		// 배송필요없음

    /**
     * 결제방법 코드 (PD017)
     */
    public static String DLV_CST_PAY_TYP_CD					= "PD017";
    public static String DLV_CST_PAY_TYP_CD_PRE_PAY			= "01";		// 선결제가능
    public static String DLV_CST_PAY_TYP_CD_NONE_PRE_PAY	= "02";		// 선결제불가
    public static String DLV_CST_PAY_TYP_CD_ESS_PRE_PAY		= "03";		// 선결제필수


    /**
     * 배송비추가안내 코드 (PD055)
     */
    public static String DLV_CST_INFO_CD					= "PD055";


    /* ####################################################################################
     * 쿠폰 관련
     * ####################################################################################*/
    public static String CUPN_DSC_MTHD_CD_WON				= "01";		// 정액할인
    public static String CUPN_DSC_MTHD_CD_PERCNT			= "02";		// 정률할인

    public static String CUPN_COMMISSION_TYPE_COBUY			= "01";		// 공동구매 수수료 쿠폰
    public static String CUPN_COMMISSION_TYPE_ITEM			= "02";		// 리스팅광고 수수료쿠폰

    public static String CUPN_EFTV_OBJ_CLF_CD_ITEM			= "06";		// 쿠폰유효대상구분리스팅광고

    /* ####################################################################################
     * 포인트 관련
     * ####################################################################################*/
    public static String POINT_SPPL_WY_CD_WON				= "02";		// 포인트 지급액
    public static String POINT_SPPL_WY_CD_PERCNT			= "01";		// 포인트 지급률

    /* ####################################################################################
     * OK캐쉬백 관련
     * ####################################################################################*/
    public static String OCB_SPPL_WY_CD_WON				= "02";		// OCB 지급액
    public static String OCB_SPPL_WY_CD_PERCNT			    = "01";		// OCB 지급률

    /* ####################################################################################
     * 마일리지 관련
     * ####################################################################################*/
    public static String MILEAGE_SPPL_WY_CD_WON				= "02";		// 마일리지 지급액
    public static String MILEAGE_SPPL_WY_CD_PERCNT			= "01";		// 마일리지 지급률

    /* ####################################################################################
     * 희망쇼핑 관련
     * ####################################################################################*/
    public static String HOPE_SHP_SPPL_WY_CD_WON				= "02";		// 희망후원 기부액
    public static String HOPE_SHP_SPPL_WY_CD_PERCNT			= "01";		// 희망후원 기부율

    /* ####################################################################################
     * 무이자 관련
     * ####################################################################################*/
    public static String INTFREE_MON_CLF_CD					= "PD008";	// 무이자할부기간 코드


    /* ####################################################################################
     * 상품속성 관련
     * ####################################################################################*/
    public static String ATTR_ENT_WY_CD_SELECT				= "01";		// 속성값 입력방식코드  01: 선택형
    public static String ATTR_ENT_WY_CD_SELNINPUT			= "02";		// 속성값 입력방식코드  02:선택형+입력형
    public static String ATTR_ENT_WY_CD_TEXT				= "03";		// 속성값 입력방식코드  03:입력형(문자)
    public static String ATTR_ENT_WY_CD_NUMBER				= "04";		// 속성값 입력방식코드  04:입력형(숫자)
    public static String ATTR_ENT_WY_CD_MULTISEL			= "05";		// 속성값 입력방식코드  05:복수선택형

	/* ####################################################################################
	 * 카테고리 관련
	 * ####################################################################################*/
    /**
     * 카테고리유형 코드 (DI013)
     */
    public static String DISP_CTGR_KIND_CD_DISP				= "01";		// 전시카테고리

    /**
     * 카테고리 전시 상태  (DI014)
     */
    public static String DISP_CTGR_STAT_CD_DISP				= "03";		// 전시중
    public static String DISP_CTGR_STAT_CD_NON_DISP		= "04";		// 전시안함
    public static String DISP_CTGR_STAT_CD_BEFORE_DISP	= "05";		// 전시대기

    public static String DISP_CTGR_USED_REG_ALL			= "00";	//모두가능
    public static String DISP_CTGR_USED_REG_ONLY			= "01";	//중고만
    public static String DISP_CTGR_USED_REG_NOT			= "02";	//중고불가


    /* ####################################################################################
     * 상품정보관리 판매가수정 관련
     * ####################################################################################*/
    public static String PRD_SEL_PRC_UP						= "01";		// 가격 인상
    public static String PRD_SEL_PRC_DOWN					= "02";		// 가격 인하
    public static String PRD_SEL_PRC_KEEP					= "03";		// 그대로 적용
    public static String PRD_SEL_PRC_WON					= "01";		// 원
    public static String PRD_SEL_PRC_PERCNT					= "02";		// 퍼센트(%)

    /**
     * 브랜드 속성 관련
     */
    public static long PRD_ATTR_CD_BRAND					= 11821;	//브랜드 속성코드

    /**
     * TOP STYLE 관련
     */
    public static long TOPSTYLE_STYLE_CTGRNO	= 195046;		// STYLE 중카
    public static long TOPSTYLE_TIP_CTGRNO		= 195047;		// TIP 중카
    public static String PRD_ATTR_CD_STYLE 		= "2547285"; 	// STYLE 속성코드
    public static String PRD_ATTR_CD_TIP 		= "11256539";	// STYLE 속성코드

    /* 거래유형 코드 */
    public static String BSN_DEAL_CLF_COMMISSION			= "01";	// 수수료 유형(일반상품)
    public static String BSN_DEAL_CLF_DIRECT_PURCHASE		= "02";	// 직매입 유형
    public static String BSN_DEAL_CLF_SPECIFIC_PURCHASE		= "03";	// 특정매입 유형
    public static String BSN_DEAL_CLF_TRUST_PURCHASE		= "04";	// 위탁판매 유형

    /* 배송유형 */
    public static String DLV_CLF_CD						= "PD090";
    public static String DLV_CLF_IN_DELIVERY			= "01";	// 자체배송
    public static String DLV_CLF_OUT_DELIVERY			= "02";	// 업체배송
    public static String DLV_CLF_GLOBAL_DELIVERY		= "03";	// 11번가 해외배송
    public static String DLV_CLF_CONSIGN_DELIVERY		= "04";	// 셀러위탁배송

    /* 해외 입고 유형 */
    public static String ABRD_IN_CD_FREE			= "01";	// 무료픽업
    public static String ABRD_IN_CD_SELLER			= "02";	// 판매자발송
    public static String ABRD_IN_CD_AGENCY			= "03";	// 구매대행

    /* 해외 배송유형 구분 */
    public static String OFFSHORE_DLV_CLF_IN_DELIVERY		= "01";	// 자체배송
    public static String OFFSHORE_DLV_CLF_OUT_DELIVERY		= "02";	// 업체배송

    /* 매입 마진 정책 코드 */
    public static String MRGN_POLICY_CD_PERCNT				= "01"; // 마진율
    public static String MRGN_POLICY_CD_WON					= "02"; // 공급가

    public static String AUTH_SELLER_LOGIN = "|4000|";	//수수료셀러그룹

    /* 도서셀러 (morning01) */
    public static long BOOK_SELLER = 10006134;

    /* 도서상품 구분코드 (01:신간Free, 02: 신간, 03: 구간, 04:음반,DVD(morning01), 05:일반상품 */
    public final static String BOOK_MNFC_CODE1 = "01";
    public final static String BOOK_MNFC_CODE2 = "02";
    public final static String BOOK_MNFC_CODE3 = "03";
    public final static String BOOK_MNFC_CODE4 = "04";
    public final static String BOOK_MNFC_CODE5 = "05";

    // 셀러샵으로 로그인 여부(셀러샵이면 true, 파트너오피스면 false 리턴함)
    public static boolean isSellerShopLogin(String authGrpCD){
        if(AUTH_SELLER_LOGIN.indexOf("|"+authGrpCD+"|") >=0)
            return true;
        else
            return false;
    }

    /** 원재료 유형 코드 관련 */
    public static String PRD_RMATERIAL_TYP_CD	= "PD098"; //원재료 유형 코드.
    public static String PRD_RMATERIAL_TYP_CD_FARM	= "01"; //농산물
    public static String PRD_RMATERIAL_TYP_CD_FISH	= "02";	 //수산물
    public static String PRD_RMATERIAL_TYP_CD_PROC	= "03";	 //가공품
    public static String PRD_RMATERIAL_TYP_CD_EXTRA = "04";	 //원산지 의무 표시대상 아님
    public static String PRD_RMATERIAL_TYP_CD_REFER = "05"; //상품별 원산지는 상세설명 참조

    /** 원재료 유형 코드 배열 */
    public static String [] PRD_RMATERIAL_TYP_CD_ARR = {"01", "02", "03", "04", "05"};

    /** 원재료 유형 코드를 입력해야 하는 루트 카테고리 번호 배열 */
    public static long [] PRD_RMATERIAL_TYP_CD_REQ_CTGR_ARR = {2072, 2790, 14617, 27925, 117162, 127648};


    /** 원재료 유형 코드 관련 */
    public static String PRD_GLOBAL_PROXY_CD	= "PD099"; //해외구매대행코드 2011.09.27
    public static String PRD_GLOBAL_PROXY_CD_LOCAL	= "01"; //일반상품
    public static String PRD_GLOBAL_PROXY_CD_GLB	= "02";	 //해외구매대행코드

    /** 속성 레벨 구분 코드 관련 */
    public static String ATTR_CLSF_CD	= "PD101"; //속성 구분 공통코드 그룹 코드.

    public static String CURRENCY_EUR	= "EUR"; //유로
    public static String CURRENCY_USD	= "USD"; //달러

    /** 복수구매할인   */
    public static String PLU_DSC_MTHD_CD	= "PD104"; //복수구매 유형 코드.
    public static String PLU_DSC_MTHD_CD_WON			= "02";		// 정액할인
    public static String PLU_DSC_MTHD_CD_PERCNT			= "01";		// 정률할인

    public static String PLU_DSC_BASIS_CNT		= "01";		    // 수량기준
    public static String PLU_DSC_BASIS_WON			= "02";		// 금액기준

    public static long PRD_WGHT_LIMIT					= 30000L; // 상품 판매가 40억까지 제한

    /** 중고상품특이사항 */
    public static Set PRD_EXTERIOR_SPECIAL_NOTE = new HashSet(){
        {
            add("외관일부파손");
            add("기능일부하자");
            add("특이사항없음");
        }
    };

    /** 서비스 카테고리 상품 구분. */
    public static String SVC_CTGR_TYP_CD				= "PD056";		// 서비스 카테고리 상품구분코드
    public static String SVC_CTGR_TYP_CD_SMS_CUPN		= "01";			// SMS 전송&쿠폰출력 상품
    public static String SVC_CTGR_TYP_CD_SKI			= "02";			// 스키시즌권
    public static String SVC_CTGR_TYP_CD_MOBILE		= "03";			// 휴대폰
    public static String SVC_CTGR_TYP_CD_RENTAL		= "04";			// 렌탈

    /** 전시 영역 코드. */
    public static final String OM_AREA_CD = "00";	// OM 영역 코드
    public static final String B2B_AREA_CD = "01";	// B2B 영역 코드
    public static final String MOBILE_AREA_CD = "02";	// 모바일전용전시 영역 코드
    public static final String TOWN_AREA_CD = "03";	// TOWN 영역코드

    /** 초기배송비 무료시 부과방법 코드 */
    public static String RTNGD_DLV_CD			= "PD111";		// 초기배송비 무료시 부과방법 코드.
    public static String RTNGD_DLV_CD_RT		= "01";			// 왕복.
    public static String RTNGD_DLV_CD_OW		= "02";			// 편도.

    /** 단말기출고가 */
    public static long MOBILE_PHONE_PRC_LIMIT			= 10000000L; // 1천만원미만까지 제한

    /** 타운 지점 팝업 탭 구분 */
    public static String TOWN_BRANCH_LIST		= "L";			// 지점 리스트
    public static String TOWN_BRANCH_MAP		= "M"; 			// 지도보기

    /** 타운 지도 기본 위치 */
    public static String TOWN_MAP_DEFAULT_X		= "14128598.694616";
    public static String TOWN_MAP_DEFAULT_Y		= "4508612.3594859";

    /** 상품간편등록 템플릿 설정 항목 관련 코드 */
    public static String TEMPLATE_ITEM_CD           = "PD114";	 // 상품 간편등록 템플릿 항목 코드.

    /** 상품간편등록 템플릿 구분 관련 코드 */
    public static String TEMPLATE_CLSF_CD       = "PD115"; // 상품간편등록 템플릿 구분 코드.
    public static String TEMPLATE_CLSF_CD_ALL   = "00";	 // 전체.
    public static String TEMPLATE_CLSF_CD_CTGR  = "01";	 // 카테고리.
    public static String TEMPLATE_CLSF_CD_SELLER = "02";	 // 셀러등록.

    /** 상품간편등록 템플릿 판매상태 관련 코드 */
    public static String TEMPLATE_SEL_MTHD_CD       = "PD116"; // 상품간편등록 템플릿 판매상태 코드.
    public static String TEMPLATE_SEL_MTHD_CD_FIXS = "01";	 // 고정가/공동구매/예약판매.
    public static String TEMPLATE_SEL_MTHD_CD_USED = "05";	 // 중고판매.

    public static String TEMPLATE_DP_DISP_CTGR_EXPT_TYPE = "107"; //'PD095'의 dtls_cd(상품간편등록 사용불가 카테고리)

    public static String TEMPLATE_PRD_STAT_COL_CD = "116";  //템플릿 입력항목인 '상품상태'의 컬럼 코드.

    public static String PRD_MAIN_HTML_GEN = "TheyAreComing";

    /** 휴대폰 요금제 약정 유형 코드 */
    public static String MP_CONTRACT_TYP_CD = "PD119";
    public static String MP_CONTRACT_TYP_CD_NORMAL = "01";  //일반.
    public static String MP_CONTRACT_TYP_CD_FEE = "02";     //요금제.

    /** 휴대폰 요금제 약정 기간 코드 */
    public static String MP_CONTRACT_TERM_CD = "PD120";

    /**
     * SO 상품조회 중 조회조건 유형 2번의 필터링 파라미터 배열
     * IX10_DP_GNRL_DISP 인덱스의 컬럼으로 사용되는 조건 파라미터인
     * selMnbdNo, createDt, selStatCd, selMthdCd, svcAreaCd 는 포함되면 안됨.
     */
    public static String [] SO_SCH_CONDITION_TYPE_2_FILTERING_PARAM_ARR = {"stckQty", "dlvCstPayTypCd", "dlvCstInstBasiCd", "boldfaceAplDt", "premiumAplDt", "bgColorAplDt", "adMovieDt", "premiumPlusAplDt", "gifImageAplDt", "dlvClf", "mobilePrdYn", "gblDlvYn", "omPrdYn", "engDispYn"};

    /** 국가 코드 타입. */
    public static String NT_CODE_TYPE_US = "300";   //미국.
    public static String NT_CODE_TYPE_KR = "304";   //한국.
    public static String NT_CODE_TYPE_BENEPIA = "305"; // 베네피아

    public static String ORD_CN_STEP_CD_00 = "00"; // 주문 취소단계 코드(취소불가)
    public static String ORD_CN_STEP_CD_01 = "01"; // 주문 취소단계 코드-결제완료 단계까지 취소가능
    public static String ORD_CN_STEP_CD_02 = "02"; // 주문 취소단계 코드-배송준비중단계까지취소가능
    public static String ORD_CN_STEP_CD_03 = "03"; // 주문 취소단계 코드-쿠팡상품
    public static String ORD_CN_STEP_CD_04 = "04"; // 주문 취소단계 코드-직매입상품
    public static String ORD_CN_STEP_CD_05 = "05"; // 주문 취소단계 코드-해외쇼핑상품
    public static String ORD_CN_STEP_CD_06 = "06"; // 주문 취소단계 코드-수량부분취소불가
    public static String ORD_CN_STEP_CD_07 = "07"; // 주문 취소단계 코드-명품직매입
    public static String ORD_CN_STEP_CD_08 = "08"; // 주문 취소단계 코드-AK몰,YES24
    public static String ORD_CN_STEP_CD_09 = "09"; // 타운상품권 상품 재결재 가능
    public static String ORD_CN_STEP_CD_10 = "10"; // 에너자이저이벤트
    public static String ORD_CN_STEP_CD_11 = "11"; // 주문제작상품

    public static String DP_DISP_CTGR_EXPT_TYPE_111 = "111"; // 카테고리 예외 대상 코드(타운 예외카테고리)


    /** 상품 히트수 로깅 여부 KEY (sy_properties의 key) */
    public static String KEY_PRD_HITS_LOGGING = "PRODUCT_HITS_LOG";    //

    /** 카탈로 히트수 로깅 여부 KEY (sy_properties의 key) */
    public static String KEY_CTLG_HITS_LOGGING = "CATALOG_HITS_LOG";

    /** 판매자 분담 프로모션 신청 예외 판매자 목록 KEY (sy_properteis의 key) */
    public static String KEY_SHARE_PROMOTION_EXCEPT_SELLER_LIST = "SHARE_PROMOTION_EXCEPT_SELLER_LIST";

    /** 판매자 분담 프로모션 신청 예외자를 등록할 수 있는 부서 */
    public static String SHARE_PROMOTION_EXCEPT_SELLER_REG_DEPT_CD = "PD135";

    /**
     * 추가카테고리 유형 코드
     */
    public static String ADD_CTGR_TYP_CD = "PD106";
    public static String ADD_CTGR_TYP_ITEM = "001";
    public static String ADD_CTGR_TYP_BRAND = "002";
    public static String ADD_CTGR_TYP_THEME = "003";
    public static String ADD_CTGR_TYP_SHOCKING_DEAL = "004";
    public static String ADD_CTGR_TYP_MART = "006";

    public static long CATALOG_BRAND_CD = 1023;

    /**
     * 이메일 템플릿 코드 (바로마트 승인거부시 발송메일)
     */
    public static int EMAIL_TEMPLATE_ID_DIRECT_MART_REJECT = 10154;

    /**
     * 이메일 템플릿 코드 (바로마트 승인거부시 발송메일)
     */
    public static int EMAIL_TEMPLATE_ID_PRMT_APRV = 10165;

    /**
     * 가격승인 코드 (승인대기)
     */
    public static String PD_PRD_PRC_APRV_REQ = "01";

    /**
     * 가격승인 코드 (승인)
     */
    public static String PD_PRD_PRC_APRV = "03";

    /**
     * 쇼킹딜 승인 상태 코드
     * REQ_APRV(01):승인대기, RTN_APRV(02):승인반려, COMP_APRV(03):승인완료, REQ_CNL(04):신청취소, REQ_CNFM(05):확정대기,
     * RTN_CNFM(06):확정반려, COMP_CNFM(07):확정완료, INST_CLS(08):즉시종료, MML_CLS(09):정상종료
     *
     */
    public enum APRV_STAT_CD{
        REQ_APRV("01"), RTN_APRV("02"), COMP_APRV("03"), REQ_CNL("04"), REQ_CNFM("05")
        , RTN_CNFM("06"), COMP_CNFM("07"), INST_CLS("08"), MML_CLS("09");

        public static final String GRP_CD = "PD106";
        private String code;
        private APRV_STAT_CD(String code){
            this.code = code;
        }

        public String getDtlsCd(){
            return code;
        }

        public boolean equals(String code){
            return this.code.equals(code);
        }

        /**
         * 승인완료 전,후 판별
         * @param code
         * @return
         */
        public static boolean preApprovalComplete(String code){
            List<String> preStatCds = Arrays.asList(REQ_APRV.getDtlsCd(), RTN_APRV.getDtlsCd(), REQ_CNL.getDtlsCd());
            return preStatCds.contains(code);
        }

        /**
         * 확정완료 전,후 판별
         * @param code
         * @return
         */
        public static boolean preConfirmComplete(String code){
            List<String> afterStatCds = Arrays.asList(COMP_CNFM.getDtlsCd(), INST_CLS.getDtlsCd(), MML_CLS.getDtlsCd());
            return !afterStatCds.contains(code);
        }

        /**
         * 쇼킹딜 종료 전,후 판별
         * - 확정완료이고 prdAddStatCd 별 처리는 별도 처리요함
         * @param code
         * @return
         */
        public static boolean preCloseDeal(String code){
            List<String> afterStatCds = Arrays.asList(INST_CLS.getDtlsCd(), MML_CLS.getDtlsCd());
            return !afterStatCds.contains(code);
        }

        /**
         * 어떤 것도 불가능 한 상태
         * @param code
         * @return
         */
        public static boolean impossibleState(String code){
            List<String> statCds = Arrays.asList(RTN_APRV.getDtlsCd(), REQ_CNL.getDtlsCd());
            return statCds.contains(code);
        }

    }

    /**
     * 쇼킹딜 신청가 근거 코드
     * 01:11번가판매가, 02:자체운영몰판매가, 03:가격비교판매가, 04:온라인판매가, 05:기타
     */
    public enum REQ_PRC_BASE_CD{
        EVN_ST_PRC("01"), SELF_MNMT_MALL_PRC("02"), COST_CMP_PRC("03"), ONLINE_PRC("04"), ETC("05");

        public static final String GRP_CD = "PD137";
        private String code;
        private REQ_PRC_BASE_CD(String code){
            this.code = code;
        }

        public String getDtlsCd(){
            return code;
        }

        public boolean equals(String code){
            return this.code.equals(code);
        }
    }


    /** 브랜드 속성코드 */
    public static String ATTR_CD_BRAND = "11821";

    /** 제조사 속성코드 */
    public static String ATTR_CD_COMPANY = "11905";

    /** 모델명 속성코드 */
    public static String ATTR_CD_MODEL = "11800";

    /** 발행처 속성코드 */
    public static String ATTR_CD_PBLCNNM = "11811";

    /** 티켓명 속성코드 */
    public static String ATTR_CD_TCKNM = "11942";

    /** 종류 속성코드 */
    public static String ATTR_CD_KNDNM = "11908";

    /**음반/DVD_아티스트 속성코드*/
    public static String ATTR_CD_ARTIST = "54863639";

    /**음반_제조사 속성코드*/
    public static String ATTR_CD_MAKER = "11904";

    /**음반_제조사 속성코드*/
    public static String ATTR_CD_DVDTITLE = "11934";

    /**음반_앨범명 속성코드*/
    public static String ATTR_CD_ALBUMNM = "11865";

    /**도서/외서/음반/DVD 구분코드 */
    public static String DP_DISP_CTGR_BOOK_CLF_CD_BOOK = "01";
    public static String DP_DISP_CTGR_BOOK_CLF_CD_FOREIGN_BOOK = "02";
    public static String DP_DISP_CTGR_BOOK_CLF_CD_MUSIC = "03";
    public static String DP_DISP_CTGR_BOOK_CLF_CD_DVD = "04";

    /** 카테고리상품번호*/
    public static String PRD_CTGR_NO_BAROMART = "940530"; //바로마트 대카테코리 번호

    public static String PRD_CTGR_NO_LIFEPLUS = "1001122";//생활+ 대카테고리 번호
    public static String PRD_CTGR_NO_TOUR = "2878"; // 여행 대카테고리번호

    /** 이벤트상품적용구분코드(PD091) */
    public static String PRD_APL_CD_ALL 			= "00";  //전체
    public static String PRD_APL_CD_CTGR 			= "01";  //카테고리
    public static String PRD_APL_CD_SPEC_PRD	= "02";  //특정상품
    public static String PRD_APL_CD_EX_PRD 		= "03";  //일부상품 제외
    public static String PRD_APL_CD_EX_CTGR 	= "04";  //일부카테고리 제외

    public static int USE_DAY_TERM_CERT	= 70;				// 외부인증번호 사용 시 사용기간 설정 기간

    /* [PD143] 이미지검수상태코드*/
    public final static String IMAGE_ISPT_STAT_CD = "PD143";
    public final static String IMAGE_ISPT_DONE_STAT_CD = "03";		// 검수완료
    public final static String IMAGE_ISPT_REJECT_STAT_CD = "04";	// 검수거절

    /** 카테고리 옵션가 설정 코드 */
    public final static String CTGR_OPT_PRC_CD = "PD152";

    /** 목록 통관 관부과세 부과 제한 금액 */
    public final static int CSTM_APL_PRC_LIMIT = 200;

    /**
     * 주문 대상 제한 코드
     * 01:NIKE WE RUN SEOUL 10K, 02:맘앤대디클럽 전용 상품, 03:시즌 락커, 04:패션클럽 전용상품, 05:선착순 판매 상품
     * 06:에너자이저 나이트런, 07:리복 스파르탄 레이스, 08:이벤트 주문 도메인, 09:제휴이벤트 상품
     */
    public enum LIMIT_TYP_CD{
        NIKE_WE_RUN("01"), MOM_DAD_CLUB("02"), SEASON_LOCKER("03"), PASSION_CLUB("04"), PRIOR_SELL("05")
        , ENERGIZER_NIGHT_RUN("06"), REEBOK_SPARTAN_LACE("07"), EVENT_ORDER_DOMAIN("08"), PARTNER_EVENT("09");

        public static final String GRP_CD = "PD125";
        private String code;
        private LIMIT_TYP_CD(String code){
            this.code = code;
        }

        public String getDtlsCd(){
            return code;
        }

        public boolean equals(String code){
            return this.code.equals(code);
        }
    }

    /**
     * 환불 타입 코드
     * 01:70%포인트환불, 02:100%자동환불, 03:환불불가
     */
    public enum RFND_TYP_CD{
        POINT("01"), AUTO("02"), NOT("03");

        public static final String GRP_CD = "PD145";
        private String code;
        private RFND_TYP_CD(String code){
            this.code = code;
        }
        public String getDtlsCd(){
            return code;
        }
        public boolean equals(String code){
            return this.code.equals(code);
        }
        public static boolean contains(String code){
            return false;
            //return TmallCommonCode.getCodeDetail(GRP_CD, code) != null;
        }
    }

    /**
     * 상품 정보를 조회 할 테이블 영역
     * - ORIGINAL : 현재 PD_PRD 의 상품정보
     * - EVENT : 쇼킹딜 판매 시작시 적용되는 상품정보
     * - ROLLBACK : 쇼킹딜 종료 후 원복되는 상품정보
     */
    public static enum PrdInfoType {
        ORIGINAL, EVENT, ROLLBACK;

        public static PrdInfoType getPrdInfoType(String type){
            if(StringUtil.isEmpty(type)) return ORIGINAL;
            return PrdInfoType.valueOf(type);
        }
    }

    /**
     * 쇼킹딜 API 연동업체 예외 대상 (sy_properties)
     */
    public static String SHOCKINGDEAL_EXCP_OBJ = "SHOCKINGDEAL_EXCP_OBJ";

    /**
     * 쇼킹딜 API 연동업체 재고 예외 대상 (sy_properties)
     */
    public static String SHOCKINGDEAL_STOCK_EXCP_OBJ = "SHOCKINGDEAL_STOCK_EXCP_OBJ";

    /**
     * 쇼킹딜 SO/BO 구분
     */
    public static String BACKOFFICE_TYPE = "BO";
    public static String SELLEROFFICE_TYPE = "SO";
    public static String PARTNEROFFICE_TYPE = "PO";
    public static String API_TYPE = "API";

    /**
     * 쇼킹딜 이벤트 프로모션 번호
     */
    public static String SHOCKINGDEAL_PRMT_NO = "SHOCKINGDEAL_PRMT_NO";

    /**
     * ESO 캐시사용
     */
    public static final String ESO_CLUSTER_NAME = "farm1";
    public static final String ESO_SERVICE_NAME = "PRODUCT";
    public static final String ESO_DB_SWITCH_PREFIX = "DBSWITCH_PREFIX_";
    public static final int ESO_PRD_DTL_CACHE_TIME = 600;
    public static final int ESO_PRD_DTL_CACHE_TIME_1H = 3600;

    /**
     * ESO Cache Key
     */
    public static final String ESO_EVT_LIMIT_LIST = "EVT_LIMIT_LIST";		// 이벤트 그룹 한도 목록

    /**
     * 상품상세 구분 코드 (PD160)
     */
    public static final String PRD_DTL_TYP_CD_OPT = "01";
    public static final String PRD_DTL_TYP_CD_OPT_DAN2 = "02";
    public static final String PRD_DTL_TYP_CD_OPT_DT_NOT_USE_DAN1 = "03";
    public static final String PRD_DTL_TYP_CD_OPT_DT_NOT_USE_DAN2 = "04";
    public static final String PRD_DTL_TYP_CD_OPT_DT_USE_DAN1 = "05";
    public static final String PRD_DTL_TYP_CD_OPT_DT_USE_DAN2 = "06";
    public static final String PRD_DTL_TYP_CD_OPT_DT_USE_HL_DAN1 = "07";
    public static final String PRD_DTL_TYP_CD_DE_NOT = "08";
    public static final String PRD_DTL_TYP_CD_DE_DESC = "09";
    public static final String PRD_DTL_TYP_CD_OPT_DE_USE_HL_DAN1 = "10";
    public static final String PRD_DTL_TYP_CD_OPT_DE_USE_HL_DAN2 = "11";
    public static final String PRD_DTL_TYP_CD_OPT_DE_USE_HL_DAN3 = "12";

    public static List<String> DESIGN_EDITOR_TYPE = Arrays.asList(PRD_DTL_TYP_CD_DE_NOT, PRD_DTL_TYP_CD_DE_DESC,
            PRD_DTL_TYP_CD_OPT_DE_USE_HL_DAN1, PRD_DTL_TYP_CD_OPT_DE_USE_HL_DAN2, PRD_DTL_TYP_CD_OPT_DE_USE_HL_DAN3); // 디자인편집기 유형 그룹
    public static List<String> DESIGN_SMART_OPTION_TYPE = Arrays.asList(PRD_DTL_TYP_CD_OPT_DE_USE_HL_DAN1, PRD_DTL_TYP_CD_OPT_DE_USE_HL_DAN2, PRD_DTL_TYP_CD_OPT_DE_USE_HL_DAN3); // 디자인편집기 유형 그룹
    public static List<String> SMART_OPTION_TYPE = Arrays.asList(PRD_DTL_TYP_CD_OPT_DT_NOT_USE_DAN1, PRD_DTL_TYP_CD_OPT_DT_NOT_USE_DAN2,
            PRD_DTL_TYP_CD_OPT_DT_USE_DAN1, PRD_DTL_TYP_CD_OPT_DT_USE_DAN2, PRD_DTL_TYP_CD_OPT_DT_USE_HL_DAN1,
            PRD_DTL_TYP_CD_OPT_DE_USE_HL_DAN1, PRD_DTL_TYP_CD_OPT_DE_USE_HL_DAN2, PRD_DTL_TYP_CD_OPT_DE_USE_HL_DAN3); // New스마트옵션 유형 그룹

    public static List<String> ALL_SMART_OPTION_TYPE = Arrays.asList(PRD_DTL_TYP_CD_OPT, PRD_DTL_TYP_CD_OPT_DAN2, PRD_DTL_TYP_CD_OPT_DT_NOT_USE_DAN1
            , PRD_DTL_TYP_CD_OPT_DT_NOT_USE_DAN2,
            PRD_DTL_TYP_CD_OPT_DT_USE_DAN1, PRD_DTL_TYP_CD_OPT_DT_USE_DAN2, PRD_DTL_TYP_CD_OPT_DT_USE_HL_DAN1,
            PRD_DTL_TYP_CD_OPT_DE_USE_HL_DAN1, PRD_DTL_TYP_CD_OPT_DE_USE_HL_DAN2, PRD_DTL_TYP_CD_OPT_DE_USE_HL_DAN3);

    //PD213 (카테고리속성 서비스유형코드)
    public static final String CATE_ATTR_SVC_TYP_CD = "01";
    public static final String CATE_LC_ATTR_SVC_TYP_CD = "02";

    //PD214 (카테고리속성 노출방식코드)
    public static final String CATE_ATTR_EXP_WY_CD_RADIO = "01";
    public static final String CATE_ATTR_EXP_WY_CD_DATE = "02";
    public static final String CATE_ATTR_EXP_WY_CD_CHECK = "03";
    public static final String CATE_ATTR_EXP_WY_CD_TEXT = "04";

    public static final long TOUR_DISP_CTGR2_NO_PACKAGE_START_CITY_ATTR_NO = 235042;
    public static final long TOUR_DISP_CTGR2_NO_PACKAGE_CITY_ATTR_NO = 242029;
    public static final long TOUR_DISP_CTGR2_NO_PACKAGE_FREE_ATTR_NO = 242030;
    public static final long TOUR_DISP_CTGR2_NO_PACKAGE_START_FLIGHT_ATTR_NO = 235038;
    public static final long TOUR_DISP_CTGR2_NO_PACKAGE_START_DATE_ATTR_NO = 242024;
    public static final long TOUR_DISP_CTGR2_NO_PACKAGE_END_DATE_ATTR_NO = 242027;
    public static final long TOUR_DISP_CTGR2_NO_FREE_START_CITY_ATTR_NO = 235043;
    public static final long TOUR_DISP_CTGR2_NO_FREE_START_FLIGHT_ATTR_NO = 235039;
    public static final long TOUR_DISP_CTGR2_NO_FREE_START_DATE_ATTR_NO = 242025;
    public static final long TOUR_DISP_CTGR2_NO_FREE_END_DATE_ATTR_NO = 242028;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_HOTEL_LODGE_CITY_ATTR_NO = 235049;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_HOTEL_FIRST_CHECKIN_DATE_ATTR_NO = 242031;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_HOTEL_LAST_CHECKIN_DATE_ATTR_NO = 242035;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_HOTEL_MAIN_SERVICE_ATTR_NO = 242044;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_TOUR_LODGE_CITY_ATTR_NO = 269361;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_TOUR_START_FLIGHT_ATTR_NO = 269356;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_TOUR_START_CITY_ATTR_NO = 269358;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_TOUR_START_DATE_ATTR_NO = 269364;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_TOUR_END_DATE_ATTR_NO = 269365;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_TOUR_PRD_TYPE_ATTR_NO = 269352;
    public static final long TOUR_DISP_CTGR2_NO_HOTEL_FIRST_CHECKIN_DATE_ATTR_NO = 242032;
    public static final long TOUR_DISP_CTGR2_NO_HOTEL_LAST_CHECKIN_DATE_ATTR_NO = 242036;
    public static final long TOUR_DISP_CTGR2_NO_HOTEL_MAIN_SERVICE_ATTR_NO = 242045;
    public static final long TOUR_DISP_CTGR2_NO_RESORT_FIRST_CHECKIN_DATE_ATTR_NO = 242033;
    public static final long TOUR_DISP_CTGR2_NO_RESORT_LAST_CHECKIN_DATE_ATTR_NO = 242037;
    public static final long TOUR_DISP_CTGR2_NO_RESORT_MAIN_SERVICE_ATTR_NO = 242046;
    public static final long TOUR_DISP_CTGR2_NO_PENSION_FIRST_CHECKIN_DATE_ATTR_NO = 242034;
    public static final long TOUR_DISP_CTGR2_NO_PENSION_LAST_CHECKIN_DATE_ATTR_NO = 242038;
    public static final long TOUR_DISP_CTGR2_NO_PENSION_MAIN_SERVICE_ATTR_NO = 242047;
    public static final long TOUR_DISP_CTGR2_NO_DEMESTIC_LODGE_FIRST_CHECKIN_DATE_ATTR_NO = 269550;
    public static final long TOUR_DISP_CTGR2_NO_DEMESTIC_LODGE_LAST_CHECKIN_DATE_ATTR_NO = 269551;
    public static final long TOUR_DISP_CTGR2_NO_DEMESTIC_LODGE_MAIN_SERVICE_ATTR_NO = 269552;
    public static final long TOUR_DISP_CTGR2_NO_HONEYMOON_CITY_ATTR_NO = 269424;
    public static final long TOUR_DISP_CTGR2_NO_HONEYMOON_START_FLIGHT_ATTR_NO = 269419;
    public static final long TOUR_DISP_CTGR2_NO_HONEYMOON_START_CITY_ATTR_NO = 269421;
    public static final long TOUR_DISP_CTGR2_NO_HONEYMOON_START_DATE_ATTR_NO = 269427;
    public static final long TOUR_DISP_CTGR2_NO_HONEYMOON_END_DATE_ATTR_NO = 269428;
    public static final long TOUR_DISP_CTGR2_NO_HONEYMOON_PRD_TYPE_ATTR_NO = 269415;
    public static final long TOUR_DISP_CTGR2_NO_CRUISE_CITY_ATTR_NO = 269490;
    public static final long TOUR_DISP_CTGR2_NO_CRUISE_START_FLIGHT_ATTR_NO = 269485;
    public static final long TOUR_DISP_CTGR2_NO_CRUISE_START_CITY_ATTR_NO = 269487;
    public static final long TOUR_DISP_CTGR2_NO_CURISE_START_DATE_ATTR_NO = 269493;
    public static final long TOUR_DISP_CTGR2_NO_CURISE_END_DATE_ATTR_NO = 269494;
    public static final long TOUR_DISP_CTGR2_NO_CURISE_PRD_TYPE_ATTR_NO = 269481;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_GOLF_CITY_ATTR_NO = 269457;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_GOLF_START_FILGHT_ATTR_NO = 269452;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_GOLF_START_CITY_ATTR_NO = 269454;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_GOLF_START_DATE_ATTR_NO = 269460;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_GOLF_END_DATE_ATTR_NO = 269461;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_GOLF_PRD_TYPE_ATTR_NO = 269448;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_PILGRIMAGE_CITY_ATTR_NO = 269523;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_PILGRIMAGE_START_FLIGHT_ATTR_NO = 269518;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_PILGRIMAGE_START_CITY_ATTR_NO = 269520;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_PILGRIMAGE_START_DATE_ATTR_NO = 269526;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_PILGRIMAGE_END_DATE_ATTR_NO = 269527;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_PILGRIMAGE_PRD_TYPE_ATTR_NO = 269514;
    public static final long TOUR_DISP_CTGR2_NO_ABROAD_HOTEL = 952281;
    public static final long TOUR_DISP_CTGR2_NO_PACKAGE = 952052;
    public static final long TOUR_DISP_CTGR2_NO_FREE = 952055;
    public static final String TOUR_DISP_CTGR2_NO_HONEYMOON = "1018689";
    public static final String TOUR_DISP_CTGR2_NO_CRUISE = "1018683";
    public static final String TOUR_DISP_CTGR2_NO_ABROAD_GOLF = "1018690";
    public static final String TOUR_DISP_CTGR2_NO_ABROAD_PILGRIMAGE = "1018695";
    public static final String TOUR_DISP_CTGR2_NO_HOTEL = "952030";
    public static final String TOUR_DISP_CTGR2_NO_RESORT = "952041";
    public static final String TOUR_DISP_CTGR2_NO_PENSION = "952049";
    public static final String TOUR_DISP_CTGR2_NO_TRIP_ABROAD = "1017958";
    public static final String TOUR_DISP_CTGR2_NO_DOMESTIC_LODGE = "1017895";
    //public static long[] TOUR_CITY_ATTR_NOS = {TOUR_DISP_CTGR2_NO_PACKAGE_CITY_ATTR_NO, TOUR_DISP_CTGR2_NO_PACKAGE_FREE_ATTR_NO, TOUR_DISP_CTGR2_NO_ABROAD_TOUR_LODGE_CITY_ATTR_NO, TOUR_DISP_CTGR2_NO_HONEYMOON_CITY_ATTR_NO, TOUR_DISP_CTGR2_NO_CRUISE_CITY_ATTR_NO, TOUR_DISP_CTGR2_NO_ABROAD_GOLF_CITY_ATTR_NO, TOUR_DISP_CTGR2_NO_ABROAD_PILGRIMAGE_CITY_ATTR_NO};
    //public static long[] START_CITY_ATTR_NOS = {TOUR_DISP_CTGR2_NO_PACKAGE_START_FLIGHT_ATTR_NO, TOUR_DISP_CTGR2_NO_FREE_START_FLIGHT_ATTR_NO, TOUR_DISP_CTGR2_NO_ABROAD_TOUR_START_FLIGHT_ATTR_NO, TOUR_DISP_CTGR2_NO_HONEYMOON_START_FLIGHT_ATTR_NO, TOUR_DISP_CTGR2_NO_CRUISE_START_FLIGHT_ATTR_NO, TOUR_DISP_CTGR2_NO_ABROAD_GOLF_START_FILGHT_ATTR_NO, TOUR_DISP_CTGR2_NO_ABROAD_PILGRIMAGE_START_FLIGHT_ATTR_NO};
    //public static long[] TOUR_CTGR_NOS = {TOUR_DISP_CTGR2_NO_PACKAGE, TOUR_DISP_CTGR2_NO_FREE, Long.parseLong(TOUR_DISP_CTGR2_NO_TRIP_ABROAD), Long.parseLong(TOUR_DISP_CTGR2_NO_HONEYMOON), Long.parseLong(TOUR_DISP_CTGR2_NO_CRUISE), Long.parseLong(TOUR_DISP_CTGR2_NO_ABROAD_GOLF), Long.parseLong(TOUR_DISP_CTGR2_NO_ABROAD_PILGRIMAGE)};
    public static final String TOUR_DISP_CTGR3_NO_HOTEL = "1017902";
    public static final String TOUR_DISP_CTGR3_NO_RESORT = "1017904";
    public static final String TOUR_DISP_CTGR3_NO_PENSION = "1017905";

    /**
     * 카탈로그 상품군 코드
     */
    public static final String PRD_GRP_CD = "PD163";

    /**
     * 카탈로그 속성 입력 방법
     */
    public enum INPUT_MTHD_CD{
        SELECT("01"), SELECT_TEXT("02"), TEXT("03"), NUMBER("04"), UNIT_SELECT("05"), MULTI_SELECT("06"), LONG_TEXT("07");

        public static final String GRP_CD = "PD162";
        private String code;
        private INPUT_MTHD_CD(String code){
            this.code = code;
        }
        public String getDtlsCd(){
            return code;
        }
        public boolean equals(String code){
            return this.code.equals(code);
        }
        public static boolean contains(String code){
            List<INPUT_MTHD_CD> typeValues = Arrays.asList(ProductConstDef.INPUT_MTHD_CD.values());
            for(INPUT_MTHD_CD type : typeValues){
                if(type.getDtlsCd().equals(code)){
                    return true;
                }
            }

            return false;
        }
        public static boolean containsBySelect(String code){
            List<INPUT_MTHD_CD> typeValues = Arrays.asList(ProductConstDef.INPUT_MTHD_CD.SELECT
                    ,ProductConstDef.INPUT_MTHD_CD.MULTI_SELECT);
            for(INPUT_MTHD_CD type : typeValues){
                if(type.getDtlsCd().equals(code)){
                    return true;
                }
            }

            return false;
        }
        public static boolean containsByInput(String code){
            List<INPUT_MTHD_CD> typeValues = Arrays.asList(ProductConstDef.INPUT_MTHD_CD.TEXT
                    ,ProductConstDef.INPUT_MTHD_CD.NUMBER
                    ,ProductConstDef.INPUT_MTHD_CD.LONG_TEXT);
            for(INPUT_MTHD_CD type : typeValues){
                if(type.getDtlsCd().equals(code)){
                    return true;
                }
            }

            return false;
        }
        public static boolean containsByCont(String code){
            List<INPUT_MTHD_CD> typeValues = Arrays.asList(ProductConstDef.INPUT_MTHD_CD.LONG_TEXT);
            for(INPUT_MTHD_CD type : typeValues){
                if(type.getDtlsCd().equals(code)){
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * 쇼킹딜 딜연장 기준 결제건수
     */
    public static final long INCR_BASI_PAY_MIN_CNT = 1;
    public static final long INCR_BASI_PAY_MAX_CNT = 99999;

    /**
     * 쇼킹딜 전용 상품명 최대 길이
     */
    public static final int EVENT_PRD_NM_MAX_LENGTH = 36;

    public static final String EVENT_PRD_NM_ALLOW_SPECIAL_CHAR = "EVENT_PRD_NM_ALLOW_SPECIAL_CHAR";

    public static final String SHOCKING_DEAL_USE_API_SELLER = "SHOCKING_DEAL_USE_API_SELLER";

    /**
     * 카탈로그 상품 추천 승인 상태 코드
     */
    public enum PD_CTLG_APRV_STAT_CD{
        REQ_APRV("01"), RTN_APRV("02"), COMP_APRV("03");

        public static final String GRP_CD = "PD176";
        private String code;
        private PD_CTLG_APRV_STAT_CD(String code){
            this.code = code;
        }
        public String getDtlsCd(){
            return code;
        }
        public boolean equals(String code){
            return this.code.equals(code);
        }
        public static boolean contains(String code){
            List<PD_CTLG_APRV_STAT_CD> typeValues = Arrays.asList(ProductConstDef.PD_CTLG_APRV_STAT_CD.values());
            for(PD_CTLG_APRV_STAT_CD type : typeValues){
                if(type.getDtlsCd().equals(code)){
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * 카탈로그 상품 추천 승인 구분 코드
     */
    public enum PD_CTLG_APRV_TYP_CD{
        BO("01"), SYSTEM("02"), BO_CHG_MATCHING("03");

        public static final String GRP_CD = "PD175";
        private String code;
        private PD_CTLG_APRV_TYP_CD(String code){
            this.code = code;
        }
        public String getDtlsCd(){
            return code;
        }
        public boolean equals(String code){
            return this.code.equals(code);
        }
        public static boolean contains(String code){
            List<PD_CTLG_APRV_TYP_CD> typeValues = Arrays.asList(ProductConstDef.PD_CTLG_APRV_TYP_CD.values());
            for(PD_CTLG_APRV_TYP_CD type : typeValues){
                if(type.getDtlsCd().equals(code)){
                    return true;
                }
            }

            return false;
        }
    }

    /** 이벤트 프로모션 채널 비트 전체 최대 값  */
    public static final int MAX_CHNL_BIT = 15;
    /** 이벤트 프로모션 채널 값 */
    public static final int CHNL_BIT_PC = 1;
    public static final int CHNL_BIT_MWEB = 2;
    public static final int CHNL_BIT_MAPP = 4;
    public static final int CHNL_BIT_SAPP = 8;
    public static final int[] CHNL_BIT_ARR = {CHNL_BIT_PC, CHNL_BIT_MWEB, CHNL_BIT_MAPP, CHNL_BIT_SAPP};

    /** 이벤트 프로모션 채널 값 명칭  */
    public static final String[] CHNL_BIT_NM_ARR = {"PC", "모바일웹", "모바일APP", "쇼킹딜APP"};

    /** 카드추가할인 최대한도 */
    public static final String CARD_ADD_LIMIT_AMT = "CARD_ADD_LIMIT_AMT";

    /** 시럽페이 결제수단코드 (TR019) */
    public static final String STL_MNS_CLF_CD_SYRUP = "33";
    public static final String STL_MNS_CLF_CD_SYRUP_BANK = "36";
    public static final String STL_MNS_CLF_CD_SYRUP_PHONE = "37";
    public static final String [] STL_MNS_CLF_CD_SYRUP_GRP = {"33", "36", "37"};

    /** 구매불가일 유형코드 */
    public static final String BUY_DSBL_DY_CD = "PD180";

    public static List<Long> brandNotiCtgr = Arrays.asList(1454L, 1611L, 1683L, 14605L, 14608L, 1930L, 14611L, 14612L);

    /** YES24 기본정보 */
    public static String[] YES24_DEFAULT = {"07237","서울시 영등포구 여의도동","1156011000100150015031131","Yeui-do dong Yeung-deung-po gu, Seoul"};

    /** 교보문고 기본정보 */
    public static String[] KYOBO_DEFAULT = {"03154","서울시 종로구 종로1가 교보생명빌딩","1111012600100010000000001","Kyobo Life Insurance Co. Bldg., Jongno 1-ga, Jongno-gu, Seoul"};

    /** 옵션값 상태값 PD_OPT_VALUE.PRD_STCK_STAT_CD */
    public static final String PD_OPT_VALUE_CD = "PD034";
    public static final String PD_OPT_VALUE_USE_CD = "01"; 		//사용
    public static final String PD_OPT_VALUE_SOLDOUT_CD = "02";	//품절
    public static final String PD_OPT_VALUE_NOT_USE_CD = "03";	//사용안함

    /** 제휴사 구분 코드 */
    public static final String PCS_TYP_CD = "PD183";
    public static final String PCS_TYP_CD_DAUM = "01";
    public static final String PCS_TYP_CD_NAVER = "02";
    public static final String PCS_TYP_CD_DANAWA = "03";
    public static final String PCS_TYP_CD_ENURI = "04";

    /** 모델 상태 코드 */
    public static final String MODEL_STAT_CD = "PD184";
    public static final String MODEL_STAT_CD_APRV = "103";
    public static final String MODEL_STAT_CD_APRV_STOP_DISPLAY = "104";
    public static final String MODEL_STAT_CD_UN_APRV = "105";

    /** 카탈로그 구분 코드 */
    public static final String CTLG_TYP_CD = "PD185";

    /** 컨텐츠 구분 코드 */
    public static final String CONT_TYP_CD = "PD186";
    public static final String CONT_TYP_CD_IMAGE = "01";

    /** 속성 코드 */
    public static final long ATTR_NO_BRAND = 1023;
    public static final long ATTR_NO_FAMILY_BRAND = 1024;
    public static final long ATTR_NO_MNFC_BRAND = 5006;
    public static final long ATTR_NO_MNFC = 2010;
    public static final long ATTR_NO_NEW_ITEM = 2014;
    public static final long ATTR_NO_ADULT = 2013;
    public static final long ATTR_NO_UNIQ_MODEL_CD = 2012;
    public static final long ATTR_NO_CATALOG_NAME = 2011;


    public static boolean containsByBrandAttr(long attrNo){
        List<Long> typeValues = Arrays.asList(ProductConstDef.ATTR_NO_BRAND
                ,ProductConstDef.ATTR_NO_FAMILY_BRAND
                ,ProductConstDef.ATTR_NO_MNFC_BRAND);
        for(Long brandAttrNo : typeValues){
            if(brandAttrNo == attrNo){
                return true;
            }
        }

        return false;
    }

    /** 속성값 코드 */
    public static final long ATTR_VALUE_NO_ADULT_Y = 4316;
    public static final long ATTR_VALUE_NO_ADULT_N = 4317;
    public static final long ATTR_VALUE_NO_BRAND_NOT_MATCH = 329;

    /** 출처 정보 코드 */
    public static final String SRC_TYP_CD_ADMIN = "06";

    /** 카탈로그 그룹 코드 */
    public static final long CTLG_GRP_NO_PCS_MODEL = 3009;

    /** 카탈로그 등록 형태 */
    public static final String REG_TYP_CD_OPERATOR = "100";
    public static final String REG_TYP_CD_PCS_MODEL = "301";

    /** 카탈로그 구분 */
    public static final String CTLG_TYP_CD_OPT = "01";
    public static final String CTLG_TYP_CD_PRD = "02";

    /** 제휴사 카탈로그 상세 URL */
    public static final String PCS_CTLG_DTL_URL_DANAWA = "http://prod.danawa.com/info/?pcode=";
    public static final String PCS_CTLG_DTL_URL_NAVER = "http://shopping.naver.com/detail/detail.nhn?nv_mid=";
    public static final String PCS_CTLG_DTL_URL_DAUM = "http://shopping.daum.net/product/";
    public static final String PCS_CTLG_DTL_URL_ENURI = "http://www.enuri.com/view/Detailmulti.jsp?modelno=";

    /** 상품 모델 주체 구분 코드 */
    public static final String MNBD_CLF_CD_PCS = "05";
    public static final String MNBD_CLF_CD_OPT = "04";
    public static final String MNBD_CLF_CD_SELLER = "06";
    public static final String MNBD_CLF_CD_APRV = "07";
    public static final String MNBD_CLF_CD_REJECT = "08";
    public static final String MNBD_CLF_CD_MART_SELLER = "09";
    public static final String MNBD_CLF_CD_TEXT_INPUT = "10";

    /** 카탈로그 서비스 구분 */
    public static final String CTLG_SVC_CLF_MART = "01";
    public static final String CTLG_SVC_CLF_BRAND = "02";

    /** 카탈로그 특수문자 제한 KEY (SY_PROPERTIES) */
    public static final String CATALOG_LIMITED_SPECIAL_CHAR = "CATALOG_LIMITED_SPECIAL_CHAR";

    // 9월간 사용예정(내맘대로 프로모션 채널고정)
    public static String PRMT_CHANNEL_LOCK = "PRMT_CHANNEL_LOCK";

    /** 카탈로그 엑셀 다운로드 제한 수 KEY (SY_PROPERTIES) */
    public static final String CTLG_EXCEL_DOWN_LIMIT = "CTLG_EXCEL_DOWN_LIMIT";
    public static final String CTLG_EXCEL_DOWN_LIMIT_BIDB = "CTLG_EXCEL_DOWN_LIMIT_BIDB";

    /** 카탈로그 상태 코드 */
    public static final String CTLG_STAT_CD = "PD166";

    /** 카탈로그 상품 매칭 구분 코드 */
    public static final String MATCH_CLF_CD_PRD = "01";

    /** 셀러 상품 관련 등록 제한 코드 */
    public static final String SELLER_SERVICE_LMT_NORMAL_PRD_REGIST = "01";
    public static final String SELLER_SERVICE_LMT_API_PRD_REGIST = "02";
    public static final String SELLER_SERVICE_LMT_PRD_SALE = "03";

    /** 카테고리 문자열 SPLIT 구분자 */
    public static final String CTGR_SPLIT_DELIMETER = "‡";

    /** 회원 공급업체구분(MB149) */
    public static final String MEM_SUPL_CMP_CLF_CD = "02";	//직매입 관리

    /**
     * 상품 구성 방식
     */
    public enum SetTypCd{
        MASTER("01"), BUNDLE("02"), SET("03");

        public static final String GRP_CD = "PD202";
        private String code;
        private SetTypCd(String code){
            this.code = code;
        }

        public String value(){
            return code;
        }

        public static SetTypCd getSetTypCd(String type){
            if(StringUtil.isEmpty(type)) return null;

            SetTypCd[] values = SetTypCd.values();
            for(SetTypCd setTypCd : values){
                if(setTypCd.equals(type)) return setTypCd;
            }
            return null;
        }

        public boolean equals(String code){
            return this.code.equals(code);
        }
    }

    /**
     * 가격승인상태
     */
    public enum AprvStatCd{
        SEL_COMPLATE_APPROVE("00"), BEFORE_APPROVE("01"), APPROVE("02")
        , COMPLATE("03"), REJECT_APPROVE("04"), EXPIRATION("05"), CANCEL_APPROVE("06");

        public static final String GRP_CD = "PD071";
        private String code;
        private AprvStatCd(String code){
            this.code = code;
        }

        public String value(){
            return code;
        }

        public static AprvStatCd getSetTypCd(String type){
            if(StringUtil.isEmpty(type)) return null;

            AprvStatCd[] values = AprvStatCd.values();
            for(AprvStatCd aprvStatCd : values){
                if(aprvStatCd.equals(type)) return aprvStatCd;
            }
            return null;
        }

        public boolean equals(String code){
            return this.code.equals(code);
        }
    }

    /** 비회원 구매 불가 카테고리 (SY_PROPERTIES) */
    public static final String NON_MEMBER_BUY_EXPT_CTGR = "NON_MEMBER_BUY_EXPT_CTGR";

    /** 제휴사 유입시 프로모션 제한 KEY (SY_PROPERTIES) */
    public static final String PARTNER_XSITE_EXCEPT = "PARTNER_XSITE_EXCEPT";

    /**
     * 정기배송 유형코드 (PD_PRD_REGL_DLV_INFO.REGL_DLV_TYP_CD)
     */
    public enum REGL_DLV_TYP_CD{
        SHIPPING("01"), DELIVERY("02");

        public static final String GRP_CD = "PD210";
        private String code;
        private REGL_DLV_TYP_CD(String code){
            this.code = code;
        }
        public String getDtlsCd(){
            return code;
        }
        public boolean equals(String code){
            return this.code.equals(code);
        }
        public static boolean contains(String code){
            List<REGL_DLV_TYP_CD> typeValues = Arrays.asList(ProductConstDef.REGL_DLV_TYP_CD.values());
            for(REGL_DLV_TYP_CD type : typeValues){
                if(type.getDtlsCd().equals(code)){
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * 정기배송일 설정방식코드 (PD_PRD_REGL_DLV_INFO.REGL_DLV_MTHD_CD)
     */
    public enum REGL_DLV_MTHD_CD{
        BUYER("01"), SELLER("02");

        public static final String GRP_CD = "PD211";
        private String code;
        private REGL_DLV_MTHD_CD(String code){
            this.code = code;
        }
        public String getDtlsCd(){
            return code;
        }
        public boolean equals(String code){
            return this.code.equals(code);
        }
        public static boolean contains(String code){
            List<REGL_DLV_MTHD_CD> typeValues = Arrays.asList(ProductConstDef.REGL_DLV_MTHD_CD.values());
            for(REGL_DLV_MTHD_CD type : typeValues){
                if(type.getDtlsCd().equals(code)){
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * 사업자 구분 (MB_BSN_ETPRS_MEM.ENPR_CLF)
     */
    public enum MEM_ENPR_CLF {
        PERSONAL("01", "개인사업자 "), CORPORATION("02", "법인사업자 "), SIMPLICITY("03", "간이과세자 "), ELSE("99", "개인판매자 ");

        private final String code;
        private final String desc;
        MEM_ENPR_CLF(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public String getCode() {
            return code;
        }
        public String getDesc() { return desc; }
    }

    /** 셀러 권한 구분 코드 (PD_SELLER_AUTH.AUTH_TYP_CD) */
    public static final String AUTH_TYP_CD = "PD173";
    public static final String AUTH_TYP_CD_REGL_DLV = "06";
    public static final String AUTH_TYP_CD_BARCODE_DUP_CHK = "07";
    public static final String AUTH_TYP_CD_SPECIAL_RENTAL = "08";
    public static final String AUTH_TYP_CD_SELL_STOCK_CD = "09";
    public static final String AUTH_TYP_CD_OPT_ADD_PRC = "10";
    public static final String AUTH_TYP_CD_GIFT_EXPT_SELLER = "11";
    public static final String AUTH_TYP_CD_SYRUP_EXPT_SELLER = "12";
    public static final String AUTH_TYP_CD_PROMOTION_INVOICE_SELLER = "14";
    public static final String AUTH_TYP_CD_BIG_SELLER = "15";
    public static final String AUTH_TYP_CD_BROADCAST_SELLER = "16";
    public static final String AUTH_TYP_CD_STOCK_SELLER = "18";
    public static final String AUTH_TYP_CD_NOT_REG_MOBILE_CATEGORY = "19";


    /** 셀러 대상 구분 코드 (PD_SELLER_AUTH.OBJ_CLF_CD) */
    public static final String OBJ_CLF_CD = "PD171";
    public static final String OBJ_CLF_CD_REGL_DLV = "08";
    public static final String OBJ_CLF_CD_BARCODE_DUP_CHK_SELLER = "09";
    public static final String OBJ_CLF_CD_SELL_STOCK_CD = "11";
    public static final String OBJ_CLF_CD_OPT_ADD_PRC = "12";
    public static final String OBJ_CLF_CD_GIFT_EXPT_SELLER = "13";
    public static final String OBJ_CLF_CD_SYRUP_EXPT_SELLER = "14";
    public static final String OBJ_CLF_CD_PROMOTION_INVOICE_SELLER = "16";
    public static final String OBJ_CLF_CD_BIG_SELLER = "17";
    public static final String OBJ_CLF_CD_BROADCAST_SELLER = "19";
    public static final String OBJ_CLF_CD_STOCK_SELLER = "21";

    /** 셀러 등록 구분 코드 (PD_SELLER_AUTH.SEL_REG_CD) */
    public static final String SEL_REG_CD = "PD172";

    /** 정기결제 설정 가능 셀러 캐시 키 */
    public static final String REGL_DLV_SELLER_AUTH_CACHE_KEY = "REGL_DLV_SELLER_AUTH_";

    /** 현대 셀러 ID */
    public static final String HYUNDAI_DEPT_SELLER_ID = "hyundaidepart";
    public static final String HYUNDAI_SELLER_ID = "HYUNDAI_SELLER_ID";
    public static final List<String> HYUNDAI_SELLER_ID_LIST = Arrays.asList("hyundaihmall", "hyundaidepart", "hyundaihome");

    /**
     * 현대백화점관 아이콘 노출을 위한 Key (SY_PROPERTIES)
     * 아래 두 Key의 값을 ,(콤마)로 자른 후 해당 값을 찾아서 CSS 명을 찾는다.
     */
    public static final String HYUNDAI_DEPT_ATTR_VALUE_NM = "HYUNDAIDEPT_ATTR_VALUE_NM";
    public static final String HYUNDAI_DEPT_ATTR_CSS_NM = "HYUNDAIDEPT_ATTR_CSS_NM";

    /** 현대백화점관 상품 속성 번호 */
    public static final long HYUNDAI_DEPT_PRD_ATTR_NO = 18503993;

    /** 검수구분코드 PD217 */
    public static final String PRODUCT_ADD_STAT_IST = "01";

    /** 검수구분코드 PD216 */
    public static final String PRODUCT_STAT_IST_WAIT     = "01";
    public static final String PRODUCT_STAT_IST_REQUEST  = "02";
    public static final String PRODUCT_STAT_IST_PROCESS  = "03";
    public static final String PRODUCT_STAT_IST_COMPLETE = "04";

    /** 인증번호 등록가능 최대 갯수 */
    public static final int MAX_PRODUCT_CERTINFO_CNT = 100;

    /** API 상품명 제한 길이 */
    public static final long PRD_NM_LMT_API = 100;

    /** 상품 정책 카테고리 목록 캐시 키 */
    public static final String PRD_LIMIT_CTGR_LIST_CACHE_KEY = "PRD_LIMIT_CTGR_LIST";

    /** 대상속성 구분코드 관련 (PD223) */
    public static final String OBJ_ATTR_CLF_CD						= "PD223";			// 대상속성 구분코드
    public static final String OBJ_ATTR_CLF_CD_CTLG_ATTR			= "01";				// 카탈로그+카탈로그속성
    public static final String OBJ_ATTR_CLF_CD_CTLG_ATTR_TXT		= "04";				// 카탈로그 속성 텍스트
    public static final String OBJ_ATTR_CLF_CD_CTLG_ADDT_ATTR_TXT	= "05";				// 카탈로그 부가 텍스트

    /** 시럽페이 제외 파트너 코드 */
    public static final String SYRUP_EXPT_PARTNER_CD = "PD222";

    /** 공지대상구분코드 (PD228) */
    public static final String NTCE_OBJ_CLF_CD = "PD228";
    public static final String NTCE_OBJ_CLF_CD_SELLER = "01";

    /** 대량등록 추가구성상품 등록가능 목록 캐시 키 */
    public static final String BULK_ADDITION_PRD_LIST_CACHE_KEY = "BULK_ADDITION_PRD_LIST";

    /** 영업시간 구분코드 */
    public static final String SALE_HM_LIST_CD			= "LC046";		//영업시간 구분코드
    /** 영업시간 예약불가조건 구분코드 */
    public static final String RSV_DISAB_CNDT_LIST_CD	= "LC047";		//예약불가조건 구분코드
    /** 영업시간 예약불가시 구분코드 */
    public static final String RSV_DISAB_HH_LIST_CD		= "LC048";		//예약불가시 구분코드
    /** 영업시간 영업요일 구분코드 */
    public static final String WOFF_WKDY_LIST_CD		= "LC049";		//영업요일 구분코드
    /** 예약스케쥴 구분코드 */
    public static final String RSV_SCHDL_CLF_CD_TIME	= "01";		//시간 구분코드

    /** 결합유형코드 (PD209) */
    public static final String MSTR_OBJ_TYPE = "PD209";
    public static final String MSTR_OBJ_TYPE_RECOMMEND_PRODUCT = "01";//추천상품
    public static final String MSTR_OBJ_TYPE_SMART_MATCHING = "02";//스마트매칭
    public static final String MSTR_OBJ_TYPE_BIND_PRODUCT = "03";//묶음형상품

    /** 베네피아 할인가 관련 */
    public static final String LNKG_SVC_CLF_CD_BNPA = "01";	// 할인영역 구분코드 (베네피아)
    public static final String DSC_OBJ_CLF_CD_PRD = "01";	// 할인대상 구분코드 (상품)
    public static final String DSC_TYP_CD_RT = "01";		// 할인 구분코드 (정율)

    /** 베네피아 파트너 코드 */
    public static final String PARTNER_CD_BENEPIA = "1528";

    /** 쿠폰적용가 시럽페이 쿠폰 제외 추가정보 */
    public static final String MYCUPN_SYRUP_EX_ADD_INFO = "C02:F";

    /** 상품 등록/수정 카테고리 상품군 정보 **/
    public static final String DISP_CTGR_META_NO	= "153603";

    /** 지정일배송셀러 코드정보 **/
    public static final String PTNR_PRD_CLF_CD_SELLER = "PD243";

    /** 파트너(제휴사) 코드정보 (PD244) **/
    public static final String PTNR_PRD_CLF_CD_SPEC_DLV = "01";

    /** SKT NUGU PARTNER_CD **/
    public static final String SKT_NUGU_PARTNER_CD = "2121";

    /** SKT NUGU 전시구좌 ID **/
    public static final String DISP_SPCE_ID_NUGU = "DP_NUGUDEALPRD_8094";

    /** 접속경로 모바일웹 : 웹'01';모바일웹'02';쇼킹딜앱'03';모바일앱'04' **/
    public static final String CNCT_PATH_MO_WEB = "02";

    /** 상품품질지표 : 품질관리코드 */
    public static final String ERR_TYP_CD_ALL = "00"; //구분없음 (전체)
    public static final String QC_CD_SUCC = "01"; //성공
    public static final String QC_CD_FAIL = "02"; //실패

    /** 2016 카테고리 개편으로 인한 카테고리 매핑 추가 영역 */
    public static long[] BEEF_NEW_CTGR_NO = {20788, 1001481};
    public static long[] ORGN_NEW_CTGR_NO = {14617, 2790, 127648, 1001337, 1001334, 1001335, 1001336, 1001339, 1001342, 1001343};
    public static long[] HELTH_DRINK_NEW_CTGR_NO    = {2790,1001339};                                   // 건강/음료/가공식품 (기존 2790)
    public static long[] GOLD_SHOP_NEW_CTGR_NO      = {4708,1001894,1001896,1001892,1001893,1001895};   // 시계/주얼리> 순금매장
    //아래 2개 처리 필요
    public static long[] RICE_FRUIT_NEW_CTGR_NO     = {14617,1001337,1001334,1001335,1001336};          // 쌀/과일/농수축산물 카테고리 (기존 14617)
    public static long[] BABY_ITEM_NEW_CTGR_NO      = {2072,1001344, 1001345, 1001361};                 // 분유/기저귀/물티슈 (기존 2072)
    //여기까지 처리필요

    public static long[] HELTH_DIET_HONGSAM_NEW_CTGR_NO   = {127648,1001342,1001343};                   // 건강식품/다이어트/홍삼 카테고리 (기존 127648)

    public static long [] PRD_RMATERIAL_TYP_CD_REQ_NEW_CTGR_ARR = {2072, 2790, 14617, 27925, 117162, 127648, 1001344, 1001345, 1001361, 1001339, 1001337, 1001334,1001335,1001336, 1001406,1001398,1001405,1001399,1001395,1001403,1001342,1001343};           // 기존 2072, 2790, 14617, 27925, 117162, 127648
    public static List<Long> brandNotiNewCtgr = Arrays.asList(1454L, 1611L, 1683L, 14605L, 14608L, 1930L, 14611L, 14612L, 1001311L, 1001312L, 1001315L, 1001314L, 1001317L, 1001316L, 1001318L, 1001321L, 1001322L, 1001389L, 1001388L, 1001390L, 1001394L, 1001393L, 1001392L);

    /**
     * 인증 유형
     */

    //인증유형 필수카테고리
    public static String COMMON_CODE_CERT_REQ_CTGR_NO = "PD247";

    public static boolean isContainCategory(long[] baseCategoryObj, long targetCategory) {
        boolean isCategory = false;
        if (baseCategoryObj == null || baseCategoryObj.length <= 0) return isCategory;
        for (int index = 0; index < baseCategoryObj.length; index++) {
            if (baseCategoryObj[index] == targetCategory) {
                isCategory = true;
                break;
            }
        }
        return isCategory;
    }

    // 인증유형 define
    public static String PRD_CERTINFO_API_ELECTROMAGNETIC = "105";
    public static String PRD_CERTINFO_API_SAFETY_CERT_CHILD = "128";
    public static String PRD_CERTINFO_API_SAFETY_CONFIRM_CHILD = "129";
    public static final List<String> PRD_CERTINFO_API_CONFIRM = Arrays.asList(PRD_CERTINFO_API_ELECTROMAGNETIC, PRD_CERTINFO_API_SAFETY_CERT_CHILD,
            PRD_CERTINFO_API_SAFETY_CONFIRM_CHILD);

    /**
     * 상품등록 서비스코드
     */
    public static String PRD_CREATE_CD_SELLER_API = "0100";
    public static String PRD_CREATE_CD_SO_FORMREG = "0200";
    public static String PRD_CREATE_CD_SO_BULKREG = "0202";
    public static String PRD_CREATE_CD_SO_FROMMSO = "0203";

    /**
     * 상품부가정보
     */
    public static String COMMON_CODE_PRD_ADDT_INFO = "PD248";
    public static String[] PRD_ADDT_INFO_PHONE = { "01", "02", "03" }; // 휴대폰 부가정보에 대한 PD248의 코드값 (코드 PD248은 범용사용예정으로
    // 휴대폰부가정보 이외에도 사용됨.)

    /** xsite info */
    public static final String XSITE_PC = "1000966141";
    public static final String XSITE_MW = "1000132495";
    public static final String XSITE_11ST_APP = "1000132496";
    public static final String XSITE_SHOCKING_DEAL_APP = "1000981148";

    /** NOW 배송 상품 무료배송 기준*/
    public static final long NOW_DLV_BASIC_PRC	= 20000;

    /** PD_OBJ_TYP_PROP.OBJ_TYP_CD 구분값 */
    public static final String OBJ_TYP_CD_CTLG_RECM_REASON = "02";

    /** PD250 : 대카테고리 별 모바일 스냅샷 노출 개수 */
    public static final String OBJ_TYP_CD_CATE_SNAPSHOT_CNT = "01";

    /** 방송 상품 코드*/
    public static final String ADDT_INFO_CLF_CD_TV_PRD	= "04";

    /** 쿠폰 적용 불가 코드*/
    public static final String ISSUE_CUPN_EXPT_CD_ALL	= "01";	// 전체 쿠폰 제외
    public static final String ISSUE_CUPN_EXPT_CD_EVT	= "02";	// 이벤트 쿠폰 제외
    public static final String ISSUE_CUPN_EXPT_CD_BSKET	= "03";	// 장바구니 쿠폰 제외

    /** 상품상세구분코드(PD_PRD_DESC.PRD_DTL_TYP_CD) 의 디자인템플릿적용 */
    public static String PRODUCT_DETAIL_TYP_05	= "05";		// 디자인템플릿적용+템플릿타입기본+세로1단
    public static String PRODUCT_DETAIL_TYP_06  = "06";		// 디자인템플릿적용+템플릿타입기본+세로2단
    public static String PRODUCT_DETAIL_TYP_07  = "07";		// 디자인템플릿적용+템플릿타입이미지강조+세로1단

    public static List<String> PRODUCT_DETAIL_TYP_CD = Arrays.asList(PRODUCT_DETAIL_TYP_05, PRODUCT_DETAIL_TYP_06, PRODUCT_DETAIL_TYP_07);

    /** 장바구니 제한 제외 셀러 코드*/
    public static final String BSKET_CON_EXPT_CD		= "100";	// 장바구니 제한 제외 코드(PD173)

    /** 화장품 셀러*/
    public static long PRD_MEMBER_SICNIC = 19439146L;
    public static long PRD_MEMBER_HIBA = 18712609L;
    public static long PRD_MEMBER_ROLLINE = 20470376L;

    /** 시리즈 셀러*/
    public static List<String> PRD_MEMBER_SERIESE_SELLERS = Arrays.asList("10000276","38677742"); //crewmate, yes24_book

    /** 도착예정일 조회 코드 */
    public static final String DLV_DUE_DATE		    = "01";	// 도착 예정일만 조회
    public static final String CENTER_DEAD_LINE		= "02";	// 센터 마감시간만 조회
    public static final String DLV_BOTH		        = "99";	// 둘다 조회

    /** 백화점 셀러 ID */
    public static final List<String> DEPARTMENT_SELLER_ID_LIST = Arrays.asList("hyundaidepart", "edebec", "akplaza1", "akplaza2", "akplaza3", "akplaza4",
            "galleria0", "iparkm", "lottedep", "shinsegae01");

    /** OM 상품 도착 예정일 API */
    public static final String DLV_DATE_BASE_URL = "http://iapis.11st.co.kr:8765/intelligence/v1";

    /** 메타 카테고리 번호 */
    public static final long META_CTRG_NO_BEAUTY = 153607;

    /** 겟잇뷰티 카탈로그 속성 및 속성값 번호 */
    public static final long CTLG_ATTR_NO_GETITBEAUTY = 32597;
    public static final long CTLG_ATTR_VALUE_NO_GETITBEAUTY = 135571;

    public static String SY_CO_GRPCD_ISSUE_CUPN_EXPT_CD = "PD251"; // 발급쿠폰제외코드(PD251)

    public static long MOBILE_SELLER_CASH_MIN = 100000L;		//1원폰 등록을 위한 셀러의 소유캐시 최저액
    public static long RENTAL_SELLER_CASH_MIN = 300000L;		//렌탈상품 등록을 위한 셀러의 소유캐시 최저액

    public enum TRANS_LANG_TYPE_ENUM{
        ENGLISH("01"), CHINA("02");

        private String code;
        private TRANS_LANG_TYPE_ENUM(String code){
            this.code = code;
        }
        public String getCode() {
            return code;
        }
    }


    //상품(or 본점)별 등록 가능 최대 서비스가능지역 개수
    public static final int MAX_SVC_CN_AREA_CNT = 550;

    /** 카탈로그 상태 (승인완료) */
    public static final String CTLG_STAT_CD_APRV = "103";

    // FDS 관련 스위치
    public static final String SWITCH_FDS_MASTER = "FDS_MASTER_SWITCH_ON";
    public static final String SWITCH_PRD_FDS = "FDS_PRD_SWITCH_ON";

    public static final String PD_USE_ORDER_DB = "PD_USE_ORDER_DB";
    public static final String IMG_UPLOAD_URL = "http://i.011st.com";
    // 상품 등록 시 판매자 상품코드로 자동 매칭 여부 스위치
    public static final String SWITCH_PRD_CTLG_AUTO_MATCH = "SWH_PRD_AUTO_MATCH";
    public static final String[] DEFAULT_VALUE_Y_N = {"Y", "N"};

    // 상품그룹 - 상품유형 사용 셀러 (개발용 예외셀러)
    public static final List<Long> PRD_GRP_USE_PRD_GRP_TYP_CD_SELMNBDNO = Arrays.asList(10000276L);

    // 상품 배송비 예외 셀러
    public static final String PRD_DLV_CST_ECT_SELLER = "PRD_DLV_CST_ECT_SELLER";

    /* 수량별 차등 배송정책의 구매수량 마지막 구간 최대값 */
    public static final long DLV_QTY_GRADE_LAST_CNT_MAX = 999999999L;
    // 상품명 제한 정책 스위치
    public static final String SWITCH_PRD_NM_LMT = "SWH_PRD_NM_LMT";


    /* 상품그룹 대카 등록 카테고리 */
    public static final String COMMON_CODE_PRD_GRP_TYP_CD = "PD261";
    public static final String COMMON_CODE_PRD_GRP_LEVEL1_CTGR_NO = "PD262";

    public static final String CTLG_MODEL_NOT_ALLOW_SPECIAL_CHAR[] = {"`","@","#","<",">"};
}