package com.elevenst.terroir.product.registration.option.code;

public interface OptionConstDef {
    public static final String NULL_STRING = "";
    public static final String OPTION_NAME = "옵션명";
    public static final String OPTION_VALUE = "옵션값";
    public static final String OPTION_PRC = "옵션가격";
    public static final String USE_Y = "Y";
    public static final String USE_N = "N";
    public static final String USE_W = "W";
    public static final String USE_V = "V";
    public static final String USE_P = "P";
    public static final String USE_I = "I";
    public static final String USE_U = "U";
    public static final String USE_D = "D";
    public static final String TYPE_01 = "01";
    public static final String TYPE_02 = "02";
    public static final int MAX_OPTION_CNT = 5;	// 구매자 선택형 옵션 최대 등록 수
    public static final int MAX_IDP_OPTIONVAL_CNT = 100;	// 구매자 독립형 옵션값 최대 등록 수
    public static final int MAX_COM_OPTIONSTCK_CNT = 2000;	// 구매자 조합형 옵션재고 최대 등록 수
    public static final int MAX_COM_OPTIONSTCK_EX_CNT = 6000;	// 구매자 조합형 옵션재고 최대 등록 수 (예외셀러 최대개수)
    public static int MAX_ADD_PRC = 10000000;	// 옵션가 최대치

    public static final long MAX_IDEP_STOCK_CNT = 99999999;	// 독립형 옵션 최고 재고 수량

    public static String OPTION_ITEM_VALUE_DELIMITER = ":";	// OptionConstDef 옵션명/옵션값 구분자
    public static String OPTION_DELIMITER	= "†";			// String Split 옵션 구분자
    public static String OPTION_DELIMITER_N	= "\n";			// excel 파일에서 옵션 구분자(new line)
    public static String OPTION_DELIMITER_V	= "|";			// excel 파일에서 옵션 구분자(Verical Bar)

    public static String OPT_CLF_CD_MIXED	= "01";		// 조합형 옵션
    public static String OPT_CLF_CD_IDEP	= "02";		// 독립형 옵션
    public static String OPT_CLF_CD_CUST	= "03";		// 작성형 옵션

    public static String PRD_STCK_STAT_CD_USE		= "01";		// 재고사용함
    public static String PRD_STCK_STAT_CD_SOLDOUT	= "02";		// 재고품절
    public static String PRD_STCK_STAT_CD_NOUSE		= "03";		// 재고사용안함 - 이전 데이터

    public static final String OPT_ITEM_STAT_CD_USE = "01";

    public static String HIST_INSERT = "I";	// 히스토리 등록
    public static String HIST_UPDATE = "U";	// 히스토리 수정
    public static String HIST_DELETE = "D";	// 히스토리 삭제
    public static String HIST_ONLY_MAPPING_UPDATE = "MU"; // 옵션의 매핑 정보만 수정되는 경우

    public static String OPT_TYPE_EXCEL_SELECT		= "1";		// 선택형 옵션 Excel 양식 구분값
    public static String OPT_TYPE_EXCEL_CUST		= "2";		// 작성형 옵션 Excel 양식 구분값

    public static String OPT_TYP_CD_DAY				= "01";		// 날짜형 : 옵션 구분 코드 (PD141)
    public static String OPT_TYP_CD_CALC			= "02"; 		// 계산형 : 옵션 구분 코드 (PD141)
    public static String OPT_TYP_CD_RENT			= "03"; 		// 렌탈형 : 옵션 구분 코드 (PD141)

    public static String BUNDLE_PRD_OPT_ITEM_NM = "선택";	// 묶음 상품의 옵션명

    //2016-09-27 hhk 생활+내재화 대량등록 수정 - 홍현기S
    public static String BRANCH_DELIMITER_N	= "\n";			// excel 파일에서 지점정보 구분자(new Bar)
    public static String RSV_SCHDL_DELIMITER_N	= "\n";		// excel 파일에서 예약스케쥴 구분자(new Bar)
    public static String RSV_SCHDL_DELIMITER_V	= "|";		// excel 파일에서 예약스케쥴 구분자(Verical Bar)
    public static String RSV_SCHDL_CLF_CD_FOR_DATE = "02";	// 예약일정구분코드 (날짜단위)
    //2016-09-27 hhk 생활+내재화 대량등록 수정 - 홍현기E

    public static String CTGR_OPT_RESTRICT_VALUE = "20";
    public static String CTGR_OPT_RESTRICT_PERCENT = "2000";

    public static String OPTION_RESTRICT_SPECIAL_STRING = "['|\"|%|&|<|>|#|†|\\\\|∏|‡]";
    public static String OPTION_RESTRICT_SPECIAL_STRING_ADD_COMMA = "['|\"|%|&|<|>|#|†|\\\\|∏|‡]";
    public static long MAX_WGHT = 3000;
    public static long MIM_OPT_PRC = 10;
    public static long MAX_OPT_PRC = 10000000;
    public static long MAX_CALC_PRC = 1000000;
    public static long LONG_ZERO = 0;
    public static int INT_ZERO = 0;
    public static double DOUBLE_ZERO = 0;

    public static int MAX_OPT_NM_LEN = 40;
    public static int MAX_OPT_VAL_LEN = 50;
    public static int MAX_OPT_VAL_EX_LEN = 80;
    public static int MAX_OPT_WRITE_VAL_LEN = 20;

    public static long CHECK_STANDARD_SEL_PRC_FOR_OPT = 10000;	// 판매가 10000원에 대한 옵션가 체크 상수
    public static long CHECK_STANDARD_OPT_PRC = 10000;			// 옵션가 10000원에 대한 체크 상수
    public static long CHECK_MIN_SEL_PRC_FOR_OPT = 2000;		// 판매가 2000원이하 일 경우 옵션가 체크 상수

    public static String CODE_PD052_0200 = "0200";
    public static String CODE_PD129 = "PD129";

    public static final String CERT_TYPE_100 = "100";		// 내부인증번호
    public static final String CERT_TYPE_101 = "101";		// 엑셀 업로드 인증번호 관리
    public static final String CERT_TYPE_102 = "102";		// 외부인증번호
}
