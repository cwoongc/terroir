package com.elevenst.terroir.product.registration.option;

import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.option.code.OptionConstDef;
import com.elevenst.terroir.product.registration.option.exception.OptionException;
import com.elevenst.terroir.product.registration.option.message.OptionServiceExceptionMessage;
import com.elevenst.terroir.product.registration.option.service.OptionServiceImpl;
import com.elevenst.terroir.product.registration.option.validate.OptionValidate;
import com.elevenst.terroir.product.registration.option.vo.OptionMixVO;
import com.elevenst.terroir.product.registration.option.vo.PdOptItemVO;
import com.elevenst.terroir.product.registration.option.vo.PdOptValueVO;
import com.elevenst.terroir.product.registration.option.vo.ProductStockSetInfoVO;
import com.elevenst.terroir.product.registration.option.vo.ProductStockVO;
import com.elevenst.terroir.product.registration.option.vo.StandardOptionGroupMappingVO;
import com.elevenst.terroir.product.registration.option.vo.StandardOptionItemVO;
import com.elevenst.terroir.product.registration.option.vo.StandardOptionValueVO;
import com.elevenst.terroir.product.registration.product.code.CreateCdTypes;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.common.util.DateUtil;
import com.elevenst.terroir.product.registration.seller.service.SellerServiceImpl;
import com.elevenst.terroir.product.registration.seller.validate.SellerValidate;
import com.elevenst.terroir.product.registration.seller.vo.SellerAuthVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.beanutils.BeanUtils;

import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class CombinationOption extends AbstractOption {

    @Autowired
    OptionServiceImpl optionService;

    @Autowired
    OptionValidate optionValidate;

    @Autowired
    SellerValidate sellerValidate;

    @Autowired
    SellerServiceImpl sellerServiceImpl;

    @Override
    public Map<String, Object> processProductOption() throws OptionException {
        return null;
    }






    //public Map<String, Object> processProductOption(ProductVO productVO) throws OptionException {
    @Deprecated
    public void processProductOption(ProductVO productVO) throws OptionException {
        if(productVO == null && productVO.getOptionVO() == null) throw new OptionException(OptionServiceExceptionMessage.SET_OPTION_DATA_ERROR);

        //TODO: ASIS - TOBE 간 데이터비교 필수
        //String createCd = StringUtils.defaultIfEmpty(StringUtil.nvl(dataBox.getString("createCd"), CODE_PD052_0200), CODE_PD052_0200);
        String createCd = StringUtils.defaultIfEmpty(StringUtil.nvl(productVO.getBaseVO().getCreateCd(), CreateCdTypes.MO_SIMPLEREG.getDtlsCd()), CreateCdTypes.MO_SIMPLEREG.getDtlsCd());
        boolean isApi = "01".equals(createCd.substring(0,2));

        //Map<String, Object> optionMap = new HashMap<String, Object>();

        long userNo				= productVO.getSelMnbdNo(); // 현재 접속자 NO
        long selPrc 			= productVO.getPriceVO().getSelPrc();    // 상품판매가
        double prdCstmAplPrc	= productVO.getPriceVO().getPrdCstmAplPrc();				// 상품신고가
        String mobile1WonYn		= StringUtil.nvl(productVO.getBaseVO().getMobile1wonYn(), "N");		// 1원 상품 여부
        String minusOptLimitYn	= StringUtil.nvl(productVO.getMinusOptLimitYn(), "N");		// 마이너스 옵션 제한 여부
        long soCupnAmt 			= productVO.getPriceVO().getSoCupnAmt();						// 기본즉시할인가
        String prdExposeClfCd	= StringUtil.nvl(productVO.getOptionVO().getPrdExposeClfCd(), "00");		// 옵션노출방식 (등록순, 션값 가나다순...)
        prdExposeClfCd 			= ("".equals(prdExposeClfCd)) ? "00" : prdExposeClfCd;	// 초기화 작업
        boolean globalItgSeller	= productVO.getSellerVO().isGlobalItgSeller();			// 해외 통합출고지가 가능한 해외셀러인지 여부 flag
        String optTypCd			= productVO.getOptionVO().getOptTypCd();					// 옵션 구분 코드 (01 : 날짜형, 02: 계산형)
        String optionSaveYn		= productVO.getOptionSaveYn();				// 옵션 팝업에서 저장 여부.
        String prdTypCd			= productVO.getBaseVO().getPrdTypCd();					// 상품구분코드

        String[] colTitle		= getArray4Option(productVO.getOptionVO().getColTitle());		// 조합형 옵션명      ex)색상†사이즈
        String[] colQty			= getArray4Option(productVO.getOptionVO().getColCount());		// 조합형 재고수량 ex)10†10†10†10†10†10†10†10†10†10†10†10†10†10
        String[] oriColQty		= getArray4Option(productVO.getOptionVO().getOriColCount());	// 조합형 재고수량(수정 이전의) ex)10†10†10†10†10†10†10†10†10†10†10†10†10†10
        String[] colAddPrc		= getArray4Option(productVO.getOptionVO().getColOptPrice());	// 조합형 옵션가격 ex)0†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000
        String[] colPuchPrc		= getArray4Option(productVO.getOptionVO().getColPuchPrc());		// 조합형 매입가 ex)0†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000
        String[] colMrgnRt		= getArray4Option(productVO.getOptionVO().getColMrgnRt());		// 조합형 마진율 ex)0†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000
        String[] colMrgnAmt		= getArray4Option(productVO.getOptionVO().getColMrgnAmt());		// 조합형 마진가 ex)0†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000
        String[] colAprvStatCd	= getArray4Option(productVO.getOptionVO().getColAprvStatCd());  // 조합형 가격승인상태 ex)0†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000†1000
        String[] prdStckStatCd	= getArray4Option(productVO.getOptionVO().getPrdStckStatCds());	// 조합형 옵션상태 ex)01†01†01†01†01†01†01†01†01†01†01†01†01†01
        String[] colBarCode		= getArray4Option(productVO.getOptionVO().getColBarCode());		// 조합형 바코드
        String[] colOptWght		= getArray4Option(productVO.getOptionVO().getColOptWght());		// 조합형 옵션무게
        String[] colCstmAplPrc	= getArray4Option(productVO.getOptionVO().getColCstmAplPrc());	// 조합형 상품신고가
        String[] colSellerStockCd = null;
        if(isApi) {
            colSellerStockCd = getArray4Option(productVO.getOptionVO().getColSellerStockCd()); // 조합형 셀러재고번호
        } else {
            colSellerStockCd = getArray4OptionAddNullValue(productVO.getOptionVO().getColSellerStockCd()); // 조합형 셀러재고번호
        }
        String prdSellerStockCd	= productVO.getOptionVO().getPrdSellerStockCd();			// 단품일경우 지정배송일코드

        String[] groupNumber	= getArray4Option(productVO.getOptionVO().getGroupNumber());	// 조합형 옵션 그룹 번호
        String[] summaryImage	= getArray4Option(productVO.getOptionVO().getSummaryImage());	// 조합형 옵션 요약이미지 정보
        String[] summaryImageStr	= getArray4Option(productVO.getOptionVO().getSummaryImageStr());// 조합형 옵션 요약이미지 값
        String[] detailImage	= getArray4Option(productVO.getOptionVO().getDetailImage());	// 조합형 옵션 상세이미지 정보
        String[] detailImageStr	= getArray4Option(productVO.getOptionVO().getDetailImageStr());	// 조합형 옵션 상세이미지 정보 값

        String[] colSetTypCd	= getArray4Option(productVO.getOptionVO().getColSetTypCd()); // 조합형 상품구성코드
        String[] colCompInfoJson= getArray4Option(productVO.getOptionVO().getColCompInfoJson()); // 조합형 상품구성정보

        String martPrdYn		= productVO.getMartPrdYn();
        String[] colCtlgNo		= getArray4Option(productVO.getOptionVO().getColCtlgNo());			// 조합형 카탈로그 번호
        String[] colAddDesc		= getArray4Option(productVO.getOptionVO().getColAddDesc());			// 조합형 추가설명
        String[] colSpplBnftCd	= getArray4Option(productVO.getOptionVO().getColSpplBnftCd());	// 조합형 추가혜택, 제공혜택

        ArrayList<String[]> colValues	= new ArrayList<String[]>();					// 조합형 옵션명당 옵션값 배열을 담은 리스트 ex)색상의 조합 옵션값 배열, 사이즈의 조합 옵션값 배열
        String ctgrOptPrcVal			= StringUtil.nvl(productVO.getOptionVO().getCtgrOptPrcVal(), "1");
        String ctgrOptPrcPercent		= StringUtil.nvl(productVO.getOptionVO().getCtgrOptPrcPercent(), "100%");
        int optCnt = colTitle.length;	// 조합형 옵션명 수
        int colValueCnt = 0;			// 조합형 옵션값 수
        //String bsnDealClf = dataBox.getString("bsnDealClf");
        String bsnDealClf = productVO.getBaseVO().getBsnDealClf();
        boolean isPurchase = Arrays.asList(ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE, ProductConstDef.BSN_DEAL_CLF_SPECIFIC_PURCHASE, ProductConstDef.BSN_DEAL_CLF_TRUST_PURCHASE).contains(bsnDealClf);
        boolean isFreshCategory = productVO.getCtlgVO().isFreshCategory();
        //String dlvClf = dataBox.getString("dlvClf");
        String dlvClf = productVO.getBaseVO().getDlvClf();
        //long checkSelMnbdNo = dataBox.getLong("selMnbdNo", LONG_ZERO);
        boolean isRetailPrd = ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(dlvClf) || (ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(bsnDealClf) && sellerValidate.isDirectBuyingMark(userNo));

        // 조합형 옵션명이 조합된 옵션값 추출
        for(int i = 0; i < optCnt;i++){
            if(i == 0) {
                colValues.add(getArray4Option(productVO.getOptionVO().getColValue1()));		// 조합형 옵션값 ex)빨간색†빨간색†빨간색†노란색†노란색†노란색†파란색†파란색†파란색†검정색†검정색†검정색†흰색†흰색, 95†100†105†95†100†105†95†100†105†95†100†105†100†110
            } else if(i == 1) {
                colValues.add(getArray4Option(productVO.getOptionVO().getColValue2()));		// 조합형 옵션값 ex)빨간색†빨간색†빨간색†노란색†노란색†노란색†파란색†파란색†파란색†검정색†검정색†검정색†흰색†흰색, 95†100†105†95†100†105†95†100†105†95†100†105†100†110
            } else if(i == 2) {
                colValues.add(getArray4Option(productVO.getOptionVO().getColValue3()));		// 조합형 옵션값 ex)빨간색†빨간색†빨간색†노란색†노란색†노란색†파란색†파란색†파란색†검정색†검정색†검정색†흰색†흰색, 95†100†105†95†100†105†95†100†105†95†100†105†100†110
            } else if(i == 3) {
                colValues.add(getArray4Option(productVO.getOptionVO().getColValue4()));		// 조합형 옵션값 ex)빨간색†빨간색†빨간색†노란색†노란색†노란색†파란색†파란색†파란색†검정색†검정색†검정색†흰색†흰색, 95†100†105†95†100†105†95†100†105†95†100†105†100†110
            } else if(i == 4) {
                colValues.add(getArray4Option(productVO.getOptionVO().getColValue5()));		// 조합형 옵션값 ex)빨간색†빨간색†빨간색†노란색†노란색†노란색†파란색†파란색†파란색†검정색†검정색†검정색†흰색†흰색, 95†100†105†95†100†105†95†100†105†95†100†105†100†110
            }
        }

        if (userNo == LONG_ZERO)	throw new OptionException(OptionServiceExceptionMessage.UNKNOWN_USER_ERROR);
        else if (optCnt > MAX_OPTION_CNT)	throw new OptionException(OptionServiceExceptionMessage.MIX_OPTION_OVER_CNT_ERROR);	// 조합형 옵션명 수가 구매자 선택형 옵션 최대 등록 수를 넘을 경우

        if (optCnt > 0) colValueCnt = colValues.get(0).length;

        // 신선 카테고리이고 카탈로그 번호가 넘어오지 않았다면 강제로 -1 세팅
        if( (colCtlgNo == null || colCtlgNo.length == 0) && isFreshCategory && colValueCnt > 0 ) {
            colCtlgNo = new String[colValueCnt];
            colAddDesc = new String[colValueCnt];
            colSpplBnftCd = new String[colValueCnt];
            martPrdYn = "Y";

            for(int i = 0 ; i < colValueCnt ; i++) {
                colCtlgNo[i] = "-1";
            }
        }

        // 조합형 옵션명당 옵션값 배열의 데이터수와 각각 조합형 옵션정보 수량 일치 여부 확인
        boolean isCntNotMatched = false;
        if(productVO.getMemberVO().isCertStockSeller()) {
            if (colValueCnt != colQty.length || colValueCnt != colAddPrc.length || colValueCnt != prdStckStatCd.length
                || (colBarCode != null && colBarCode.length>0 && colValueCnt != colBarCode.length)
                || (colOptWght != null && colOptWght.length>0 && colValueCnt != colOptWght.length)
                || (colCstmAplPrc != null && colCstmAplPrc.length>0 && colValueCnt != colCstmAplPrc.length)
                || (isPurchase && (colValueCnt != colPuchPrc.length || colValueCnt != colMrgnRt.length || colValueCnt != colAprvStatCd.length))) isCntNotMatched = true;
        } else {
            if (colValueCnt != colQty.length || colValueCnt != colAddPrc.length || colValueCnt != prdStckStatCd.length
                || (colBarCode != null && colBarCode.length>0 && colValueCnt != colBarCode.length)
                || (colOptWght != null && colOptWght.length>0 && colValueCnt != colOptWght.length)
                || (colCstmAplPrc != null && colCstmAplPrc.length>0 && colValueCnt != colCstmAplPrc.length)
                || (colSellerStockCd != null && colSellerStockCd.length>0 && colValueCnt != colSellerStockCd.length)
                || (isPurchase && (colValueCnt != colPuchPrc.length || colValueCnt != colMrgnRt.length || colValueCnt != colAprvStatCd.length))) isCntNotMatched = true;
        }

        if(!isCntNotMatched && productVO.isBundleProduct() && (colValueCnt != colSetTypCd.length || colValueCnt != colCompInfoJson.length)){
            isCntNotMatched = true;
        }

        if(isCntNotMatched) throw new OptionException(OptionServiceExceptionMessage.CANT_SAME_MIX_OPTION_CNT_ERROR);

        int index = 1;
        for (String[] colValue : colValues) {
            if (colValueCnt != colValue.length) throw new OptionException("조합형 옵션 정보 중, 옵션명 "+index+"째 옵션값 정보 수가 일치하지 않습니다.");
            index++;
        }

        String checkMsg = NULL_STRING;	// 에러 메세지
        String optNm 	= NULL_STRING;	// 옵션명
        String optValue = NULL_STRING;	// 옵션값

        Map<String, PdOptItemVO> optItemMap	= new LinkedHashMap<String, PdOptItemVO>();	// 옵션명 (아이템) 맵
        Map<String, ArrayList<String>> optValueMap	= new LinkedHashMap<String, ArrayList<String>>();	// 옵션명에 따른 옵션값리스트 맵
        Map<String, String> optItemImageMap	= new LinkedHashMap<String, String>();	// 스마트옵션 이미지 맵
        Map<String, String> optItemDetailImageMap	= new LinkedHashMap<String, String>();	// 스마트옵션 자세히보기 이미지 맵
        Map<String, Integer> stockIndexMap			= new LinkedHashMap<String, Integer>();			// 조합형 재고에 중복 데이터 유무 체크 맵
        ArrayList<ProductStockVO> prdStockVOList		= new ArrayList<ProductStockVO>();			// 조합형 재고 ArrayList

        PdOptItemVO optItemBO;
        //if(checkSelMnbdNo <= 0) checkSelMnbdNo = userNo;
        /**
         * 1. 조합형 옵션명 정보 유효성 체크 및 옵션명 정보 설정 (옵션명의 중복 여부 체크 및 옵션 아이템 BO(ProductOptItemBO)를 리스트에 담기)
         * 조합형 옵션명 수만큼 반복문 수행.
         **/
        for (int i = 0; i < optCnt; i ++) {

            optNm = colTitle[i].trim();	// 옵션명

            checkMsg = optionValidate.checkMsgOptNmVal(USE_N, optNm, userNo);	// 옵션 아이템 명의 사용 유효성 체크

            if (StringUtil.isNotEmpty(checkMsg)) throw new OptionException((i+1) + "번째 옵션명을 확인 하십시오.\n" + checkMsg);

            // 옵션항목구분코드:옵션명 ex) 01:색상
            String optItemKey = OPT_CLF_CD_MIXED + OPTION_ITEM_VALUE_DELIMITER + optNm;
            optItemBO = optItemMap.get(optItemKey);

            // 옵션명 (아이템)맵에 해당 옵션명의 옵션아이템 BO가 존재하지 않을 경우 새로운 옵션아이템BO 생성
            if (optItemBO == null) {

                optItemBO = setPdOptItemVO(i+1, optNm, OPT_CLF_CD_MIXED, PRD_STCK_STAT_CD_USE, userNo, prdExposeClfCd);	// 옵션 아이템 BO 설정

                // 날짜형 옵션이고 1번째 항목일 경우 옵션 구분 코드 추가
                if( i == 0 && OPT_TYP_CD_DAY.equals(optTypCd) ) {
                    optItemBO.setOptTypCd(optTypCd);
                } else if(i == (optCnt-1) && OPT_TYP_CD_RENT.equals(optTypCd) ){
                    optItemBO.setOptTypCd(optTypCd);
                } else if(OPT_TYP_CD_CALC.equals(optTypCd)) {
                    optItemBO.setOptTypCd(optTypCd);
                }

                // 옵션정보 중복 체크를 위한 LinkedHashMap 객체 설정
                optItemMap.put(optItemKey, optItemBO);				// 옵션명, 옵션 아이템 BO
                optValueMap.put(optNm, new ArrayList<String>());	// 옵션명, 옵션값 ArrayList
            } else {
                throw new OptionException("중복된 옵션명[" + optNm + "]이 존재합니다.");
            }
        }

        ArrayList<String> colAddPrcArray = new ArrayList<String>(Arrays.asList(colAddPrc));
        if (!OPT_TYP_CD_RENT.equals(optTypCd) && colAddPrcArray.size() > INT_ZERO && colAddPrcArray.indexOf("0") < INT_ZERO) throw new OptionException("옵션가격이 0원인 옵션이 하나 이상이 있어야 합니다");

        String _stockKey = NULL_STRING;		// 재고정보 중복 체크 생성키
        int minDate = INT_ZERO;			// 사용기간 시작일
        int maxDate = INT_ZERO;			// 사용기간 종료일
        int tempValue = INT_ZERO;
        String optCheckMsg = null;

        /**
         * 2. 조합형 옵션값/재고 유효성 체크 및 데이터 정보 설정 (옵션 값과 재고에 대한 중복 여부 체크 및 재고 BO(ProductStockBO)를 리스트에 담기)
         * 조합형 옵션값 수만큼 반복문 수행.
         **/
        for (int i = 0; i < colValueCnt; i++) {
            // variable initialization
            //ProductStockBO prdStckBO = ProductUtil.createBO(ProductStockBO.class);
            ProductStockVO prdStckVO = new ProductStockVO();
            _stockKey = ""; /*재고정보 중복 체크 생성키*/

            // 조합형 옵션명 수만큼 반복문 수행 (역순으로 처리)
            for(int j = optCnt - 1; j >= 0; --j) {

                optNm		= colTitle[j].trim();					// 옵션명
                optValue	= colValues.get(j)[i].trim();			// 옵션값
                setOptionValueBO(i, optNm, optValue, userNo, optItemMap, optValueMap); // 옵션 아이템 BO에 있는 ProductOptValueBOList 리스트 객체에 새로 생성한 옵션값 BO를 설정 (ex.사이즈 옵션 아이템 BO에 중복되지 않는 N개의 옵션값BO 존재)

                // 옵션항목구분코드:옵션명 ex) 01:색상
                String optItemKey = OPT_CLF_CD_MIXED + OPTION_ITEM_VALUE_DELIMITER + optNm;
                optItemBO = optItemMap.get(optItemKey);				// 옵션 아이템 BO

                // 날짜형 옵션 일 경우 1번째 항목 날짜 유효한지 체크
                if( optItemBO.getOptItemNo() == 1 && OPT_TYP_CD_DAY.equals(optTypCd) ) {
                    // 날짜형 옵션 데이터 체크
                    optCheckMsg = optionValidate.checkDayOptVal(optValue, optNm, i + 1);
                    if (StringUtil.isNotEmpty(optCheckMsg)) throw new OptionException(optCheckMsg);

                    // 초기 상품 등록 시 오늘 날짜보다 이전인지 체크
                    if( !"Y".equals(optionSaveYn) && Integer.parseInt(optValue.replaceAll("-", ""), 10) < Integer.parseInt(DateUtil.getFormatString("yyyyMMdd"), 10)) {
                        throw new OptionException(optNm + "의" + (i + 1) + "번째 옵션값 [" + optValue + "]을 확인 하십시오.\n오늘보다 이전 날짜는 등록할 수 없습니다.");
                    }

                    // 옵션 팝업에서 저장시에만 처리
                    if( "Y".equals(optionSaveYn) && ProductConstDef.PRD_TYP_CD_TOWN_SALES.equals(prdTypCd) ) {
                        tempValue = Integer.parseInt(optValue.replaceAll("-", ""), 10);
                        if( minDate == 0 ) {
                            minDate = tempValue;
                            maxDate = tempValue;
                        } else {
                            if( tempValue < minDate ) {
                                minDate = tempValue;
                            } else if( tempValue > maxDate ) {
                                maxDate = tempValue;
                            }
                        }
                    }
                }

                int optValueIdx = optValueMap.get(optNm).indexOf(optValue);	// {사이즈=[95, 100, 105, 110. 90], 색상=[빨간색, 노란색, 파란색, 검정색, 흰색]}
                Object[] obj ={optItemBO.getOptItemNo(), (optValueIdx > -1 ? ((PdOptValueVO) optItemBO.getPdOptValueVOList().get(optValueIdx)).getOptValueNo() : 0), optValue};	// {옵션아이템 NO, 옵션값 NO, 옵션값}
                setPrdStckBOMethodInvoke(prdStckVO, obj, j+1);		// ProductStockBO 에 옵션 아이템 순번, 옵션값 순번, 옵션값을 설정함. setOptItemNo, setOptValueNo, setOptValueNm ex)(2, 1, 95사이즈), (1, 1, 빨간색), (2, 2, 100사이즈), (1, 1, 빨간색)

                // 재고정보 중복 체크를 위한 키 생성 (사이즈:95 -> 색상:빨간색†사이즈:95)
                _stockKey = optNm + OPTION_ITEM_VALUE_DELIMITER + optValue + ((StringUtil.isNotEmpty(_stockKey)) ? OPTION_DELIMITER : "") + _stockKey;

            }

            // 재고정보 중복 체크 (Key => 색상:빨간색†사이즈:95)
            if (stockIndexMap.get(_stockKey) == null) {
                if("Y".equals(martPrdYn)) {
                    prdStckVO.setCtlgNo(StringUtil.getLong(colCtlgNo[i]));								// 마트상품, 카탈로그번호
                    prdStckVO.setAddDesc("null".equals(colAddDesc[i])?"":colAddDesc[i]);				// 마트상품, 추가설명
                    prdStckVO.setSpplBnftCd("null".equals(colSpplBnftCd[i])?"":colSpplBnftCd[i]);		// 마트상품, 추가혜택 코드
                }

                prdStckVO.setStckQty(StringUtil.getLong(colQty[i].replaceAll(",", "")));	// 재고수량
                prdStckVO.setAddPrc(StringUtil.getLong(colAddPrc[i].replaceAll(",", "")));	// 옵션가격
                if(isPurchase){
                    prdStckVO.setPuchPrc(StringUtil.getLong(colPuchPrc[i].replaceAll(",", "")));	// 옵션 매입가
                    prdStckVO.setMrgnRt(StringUtil.getDouble(colMrgnRt[i].replaceAll(",", "")));	// 옵션 마진율
                    prdStckVO.setMrgnAmt(StringUtil.getLong(colMrgnAmt[i].replaceAll(",", "")));	// 옵션 마진가
                    prdStckVO.setAprvStatCd(colAprvStatCd[i]);	// 옵션 가격 승인상태
                }

                prdStckVO.setAddPrc(StringUtil.getLong(colAddPrc[i].replaceAll(",", "")));	// 옵션가격
                if(OptionConstDef.OPT_TYP_CD_RENT.equals(optTypCd)) {
                    prdStckVO.setRntlCst(StringUtil.getLong(colAddPrc[i].replaceAll(",", "")));	// 렌탈가격
                    prdStckVO.setAddPrc(0);	// 옵션가격
                }
                prdStckVO.setPrdStckStatCd(prdStckStatCd[i]);	// 옵션상태
                prdStckVO.setBarCode(colBarCode != null && colBarCode.length>0 ? (colBarCode[i] != null ? ("".equals(colBarCode[i].trim()) ? null : colBarCode[i].trim()) : colBarCode[i]) : null);	// 조합형 바코드
                prdStckVO.setOptWght(StringUtil.getLong(colOptWght != null && colOptWght.length>0 ? (colOptWght[i] != null ? ("".equals(colOptWght[i].trim()) ? "0" : colOptWght[i].trim().replaceAll(",", "")) : "0") : "0"));	// 조합형 무게
                prdStckVO.setCstmOptAplPrc(StringUtil.getDouble(StringUtil.nvl(colCstmAplPrc != null && colCstmAplPrc.length>0 ? (colCstmAplPrc[i] != null ? ("".equals(colCstmAplPrc[i].trim()) ? null : colCstmAplPrc[i].trim()) : null) : null,"0").replaceAll(",", "")));
                prdStckVO.setRegRnk(i+1);								// 재고 등록순서
                prdStckVO.setCreateNo(userNo);							// 생성자
                prdStckVO.setUpdateNo(userNo);							// 수정자
                if(isApi || (productVO.getMemberVO().isCertStockSeller() && colSellerStockCd != null && colSellerStockCd.length > 0)) prdStckVO.setSellerStockCd(colSellerStockCd[i]);
                else if(productVO.getMemberVO().isCertStockSeller() && prdSellerStockCd != null && !"".equals(prdSellerStockCd)) prdStckVO.setSellerStockCd(prdSellerStockCd);

                // 옵션가격 체크
                if(!OPT_TYP_CD_RENT.equals(optTypCd)) {
                    checkMsg = optionValidate.checkMsgOptPrice(Long.toString(prdStckVO.getAddPrc()), selPrc, mobile1WonYn, minusOptLimitYn, ctgrOptPrcVal, ctgrOptPrcPercent, soCupnAmt, productVO.getOptionVO().getMrgnPolicyCd(), prdTypCd.equals(ProductConstDef.PRD_TYP_CD_RENTAL), userNo);
                }
                if(isPurchase && StringUtil.isEmpty(checkMsg) && productVO.getMemberVO().isFrSeller() && prdStckVO.getAddPrc() != 0){
                    checkMsg = "특정매입의 해외명품은 옵션가격 입력이 불가합니다.";
                }
                if (StringUtil.isNotEmpty(checkMsg)) throw new OptionException((i+1) + "번째 옵션가격을 확인 하십시오.\n" + checkMsg);

                // 옵션재고수량 체크
                if(ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(bsnDealClf) || ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(dlvClf)){
                    if(oriColQty == null || oriColQty.length == 0){
                        if(prdStckVO.getStckQty() > 0) checkMsg = (ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(dlvClf)?"셀러위탁배송 ":"직매입 ")+"상품은 재고수량 입력이 불가합니다.";
                    }else{
                        if((StringUtil.isEmpty(oriColQty[i]) && prdStckVO.getStckQty() > 0)
                            || (StringUtil.isNotEmpty(oriColQty[i]) && prdStckVO.getStckQty() != StringUtil.getLong(oriColQty[i]))) {
                            checkMsg = (ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(dlvClf)?"셀러위탁배송 ":"직매입 ")+"상품은 재고수량 입력이 불가합니다.";
                        }
                    }
                }
                if(productVO.isBundleProduct()){
                    // 현재 묶음 상품의 재고 정보의 재고수량은 체크 안함
                }else{
                    if (StringUtil.isEmpty(checkMsg)) checkMsg = optionValidate.checkMsgOptStock(Long.toString(prdStckVO.getStckQty()), prdStckVO.getPrdStckStatCd(), bsnDealClf, dlvClf);
                    if (StringUtil.isNotEmpty(checkMsg)) throw new OptionException((i+1) + "번째 재고수량을 확인 하십시오.\n" + checkMsg);
                }
                //재고수량 수정 여부 값 설정.(디폴트 값 true)
                if(oriColQty != null && oriColQty.length != 0 && prdStckVO.getStckQty() == StringUtil.getLong(oriColQty[i])) {
                    prdStckVO.setStckQtyModYn(USE_N);
                }

                if(isPurchase){
                    if(productVO.isBundleProduct()){
                        // 현재 묶음 상품의 재고 정보의 매입가 및 마진율은 무의미함.
                        // 만약 재고마다 정확한 매입가 및 마진율이 필요하다면 계산하여 넣어줌
                    }else{
                        // 옵션매입가,마진율 체크
                        prdStckVO.setAddedSelPrc(selPrc+prdStckVO.getAddPrc());
                        //prdStckVO.setMrgnPolicyCd(dataBox.getString("mrgnPolicyCd"));
                        prdStckVO.setMrgnPolicyCd(productVO.getOptionVO().getMrgnPolicyCd());
                        prdStckVO.setBsnDealClf(bsnDealClf);
                        prdStckVO.setSelMnbdNo(userNo);
                        checkMsg = checkMsgPurchaseAndMargin(prdStckVO);
                        if (StringUtil.isNotEmpty(checkMsg)) throw new OptionException((i+1) + "번째  "+checkMsg);
                    }
                }
                if(isRetailPrd && !productVO.isBundleProduct() && StringUtil.isEmpty(prdStckVO.getBarCode())){
                    throw new OptionException((i+1) + "번째 바코드를 입력 하십시오.");
                }

                // 옵션 신고가 체크
                checkMsg = optionValidate.checkMsgOptCstmAplPrc(Double.toString(prdStckVO.getCstmOptAplPrc()), globalItgSeller, prdCstmAplPrc);
                if (StringUtil.isNotEmpty(checkMsg)) throw new OptionException((i+1) + "번째  옵션 상품신고가를 확인 하십시오.\n" + checkMsg);

                //요약/상세/그룹번호 저장
                // 옵션 그룹번호
                if( groupNumber != null && groupNumber.length > 0 ) {
                    prdStckVO.setGroupNumber(groupNumber[i].replaceAll(",", NULL_STRING));
                }
                // 옵션 요약정보이미지
                if( summaryImage != null && summaryImage.length > 0 ) {
                    prdStckVO.setSummaryImage(summaryImage[i].replaceAll(",", NULL_STRING));
                }
                // 옵션 상세정보이미지
                if( detailImage != null && detailImage.length > 0 ) {
                    prdStckVO.setDetailImage(detailImage[i].replaceAll(",", NULL_STRING));
                }
                // 옵션 상세정보이미지 값
                if( detailImageStr != null && detailImageStr.length > 0 ) {
                    prdStckVO.setDetailImageStr(detailImageStr[i]);
                }

                if( summaryImage != null && summaryImage.length > 0 && summaryImage[i].replaceAll(",", NULL_STRING).indexOf("등록됨") > -1 ) {
                    optItemImageMap.put(prdStckVO.getOptValueNm1(), summaryImageStr[i].replaceAll(",", NULL_STRING));
                }

                if( detailImage != null && detailImage.length > 0 && detailImage[i].replaceAll(",", NULL_STRING).indexOf("등록됨") > -1 ) {
                    optItemDetailImageMap.put(prdStckVO.getOptValueNm1(), detailImageStr[i].replaceAll(",", NULL_STRING));
                }

                // 상품구성체크
                if(productVO.isBundleProduct()){
                    prdStckVO.setSetTypCd(colSetTypCd[i]);
                    String compInfoJson = colCompInfoJson[i];

                    if(ProductConstDef.SetTypCd.getSetTypCd(prdStckVO.getSetTypCd()) == null) throw new OptionException("유효하지 않은 상품 구성 코드입니다.");
                    if(StringUtil.isEmpty(compInfoJson)) throw new OptionException((i+1) + "번째 옵션 구성 정보를 확인 하십시오.");

                    prdStckVO.setStockSetInfoListVO(new ArrayList<ProductStockSetInfoVO>());
                    try {
                        JSONArray obj= JSONArray.fromObject(URLDecoder.decode(compInfoJson, "UTF-8"));
                        int totDscRt = 0;
                        for(int j=0; j<obj.size(); j++){
                            ProductStockSetInfoVO productStockSetInfoVO = new ProductStockSetInfoVO();
                            Map map = BeanUtils.describe(JSONObject.toBean((JSONObject)obj.get(j)));
                            BeanUtils.populate(productStockSetInfoVO, map);

                            if(productStockSetInfoVO.getCompStckNo() <= 0) throw new OptionException((i+1) + "번째 옵션 구성 정보를 확인 하십시오.");
                            if(ProductConstDef.SetTypCd.SET.equals(prdStckVO.getSetTypCd()) && (productStockSetInfoVO.getSetDscRt() < 0 || productStockSetInfoVO.getSetDscRt() > 100)) throw new OptionException((i+1) + "번째 옵션의 할인율을 확인해주세요.");
                            if(productStockSetInfoVO.getSetUnitQty() <=0) throw new OptionException((i+1) + "번째 옵션의 구성수량을 확인해주세요.");

                            prdStckVO.getStockSetInfoListVO().add(productStockSetInfoVO);

                            totDscRt += productStockSetInfoVO.getSetDscRt();
                        }

                        if(ProductConstDef.SetTypCd.SET.equals(prdStckVO.getSetTypCd())){
                            if(totDscRt >= 100*obj.size()) throw new OptionException((i+1) + "번째 옵션의 할인율을 확인해주세요.");
                        }
                    }catch (OptionException e) {
                        throw e;
                    } catch (Exception e) {
                        throw new OptionException("상품 구성 정보 변환 오류.");
                    }
                }

                prdStockVOList.add(prdStckVO); // ProductStockBO List 객체 추가 (ex.14개 조합형 재고 등록)

                // 조합형 재고정보 중복 체크를 위한 LinkedHashMap 객체 설정 ({색상:빨간색†사이즈:95, 0}, {색상:빨간색†사이즈:100, 1})
                stockIndexMap.put(_stockKey, prdStockVOList.size() - 1);

            }
            else {// 재고 정보 중복
                throw new OptionException((i+1) + "번째 리스트 정보에 중복된 데이터가 존재합니다.\n" + _stockKey);
            }
        }

        // 옵션/재고정보 BO 전달
        /*
        optionMap.put("optItemMap", optItemMap);					// 옵션명 (아이템) 맵(옵션명, 옵션 아이템 BO)
        optionMap.put("productStockBOList", prdStockVOList);		// 조합형 재고 BO ArrayList
        // 옵션 적용시 변경분에 존재여부 확인을 위해..
        optionMap.put("optValueMap", optValueMap);					// 옵션명에 따른 옵션값리스트 맵  {사이즈=[95, 100, 105, 110. 90], 색상=[빨간색, 노란색, 파란색, 검정색, 흰색]}
        optionMap.put("stckIndexMap", stockIndexMap);				// 조합형 재고에 중복 데이터 유무 체크 맵 ({색상:빨간색†사이즈:95, 0}, {색상:빨간색†사이즈:100, 1})
        optionMap.put("selOptCnt", String.valueOf(optCnt));			// 조합형 옵션명 수
        optionMap.put("optItemImageMap", optItemImageMap);	// 스마트옵션 요약이미지 MAP
        optionMap.put("optItemDetailImageMap", optItemDetailImageMap);	// 스마트옵션 요약이미지 MAP
        */
        productVO.getOptionVO().setOptItemMap(optItemMap);
        productVO.getOptionVO().setProductStockVOList(prdStockVOList);
        productVO.getOptionVO().setOptValueMap((HashMap<String, ArrayList<String>>) optValueMap);
        productVO.getOptionVO().setStckIndexMap((HashMap<String, Integer>) stockIndexMap);
        productVO.getOptionVO().setOptColCnt(optCnt);
        productVO.getOptionVO().setOptItemImageMap(optItemImageMap);
        productVO.getOptionVO().setOptItemDetailImageMap(optItemDetailImageMap);


        // 옵션 팝업에서 저장시에만 처리
        if( USE_Y.equals(optionSaveYn) && ProductConstDef.PRD_TYP_CD_TOWN_SALES.equals(prdTypCd) && OPT_TYP_CD_DAY.equals(optTypCd) ) {
            String useBgnDy = String.valueOf(minDate);
            String useEndDy = String.valueOf(maxDate);

            int daysBetween = INT_ZERO;

            try {
                daysBetween = DateUtil.daysBetween(useBgnDy, useEndDy, "yyyyMMdd")+1;
            } catch (ParseException e) {
                throw new OptionException("날짜형식이 올바르지 않습니다.\nYYYY-MM-DD형식으로 입력해 주십시오.");
            }

            // 70일 초과하는지 체크
            if( daysBetween > ProductConstDef.USE_DAY_TERM_CERT ) {
                throw new OptionException("날짜형 설정 기간은 "+ProductConstDef.USE_DAY_TERM_CERT+"일 까지 설정 가능합니다.");
            }

            // 판매시작일보다 이전인지 체크
            //if( Integer.parseInt(useBgnDy) < Integer.parseInt(dataBox.getString("selBgnDy")) ) {
            if( Integer.parseInt(useBgnDy) < Integer.parseInt(productVO.getBaseVO().getSelBgnDy()) ) {
                throw new OptionException("옵션날짜 시작일이 판매기간 시작일보다 늦어야 합니다.");
            }

            // 판매종료일보다 이전인지 체크
            //if( Integer.parseInt(useBgnDy) < Integer.parseInt(dataBox.getString("selEndDy")) ) {
            if( Integer.parseInt(useEndDy) < Integer.parseInt(productVO.getBaseVO().getSelEndDy()) ) {
                throw new OptionException("옵션날짜 종료일이 판매기간 종료일보다 늦어야 합니다.");
            }

            useBgnDy = useBgnDy.substring(0, 4) + "/" + useBgnDy.substring(4, 6) + "/" + useBgnDy.substring(6);
            useEndDy = useEndDy.substring(0, 4) + "/" + useEndDy.substring(4, 6) + "/" + useEndDy.substring(6);

            /*
            optionMap.put("useBgnDy", useBgnDy);
            optionMap.put("useEndDy", useEndDy);
            */
            productVO.getOptionVO().setUseBgnDy(useBgnDy);
            productVO.getOptionVO().setUseEndDy(useEndDy);
        }

        //String msg = productVaildation(dataBox);
        //if (!"".equals(msg)) throw new OptionException(msg);

        //return optionMap;
    }






    /**
     * 옵션 아이템 BO에 있는 ProductOptValueBOList 리스트 객체에 새로 생성한 옵션값 BO를 설정 (ex.사이즈 옵션 아이템 BO에 중복되지 않는 N개의 옵션값BO 존재)
     * @param: 옵션값 순번, 옵션명, 옵션값, 생성자NO, 옵션명 (아이템) 맵, 옵션명에 따른 옵션값리스트 맵
     */
    private void setOptionValueBO(int optNo, String itmNm, String optValue, long userNo, Map<String, PdOptItemVO> optItemMap,
                                  Map<String, ArrayList<String>> optValueMap) throws OptionException {

        PdOptValueVO pdOptValueVO;	// 옵션값 BO(PdOptValueVO)

        // 옵션항목구분코드:옵션명 ex) 01:색상
        String optItemKey = OPT_CLF_CD_MIXED + OPTION_ITEM_VALUE_DELIMITER + itmNm;
        PdOptItemVO optItemBO = optItemMap.get(optItemKey);	// 옵션 아이템 순서 (0, 1)

        if (optItemBO == null) {
            throw new OptionException("(INNER)옵션명 [" + itmNm + "] 정보가 존재하지 않습니다.");
        }

        // 옵션값 유효성 체크
        String optCheckMsg = optionValidate.checkMsgOptNmVal(OptionConstDef.USE_V, optValue, userNo);
        if (StringUtil.isNotEmpty(optCheckMsg)) throw new OptionException(itmNm + "의" + (optNo+1) + "번째 옵션값 [" + optValue + "]을 확인 하십시오.\n" + optCheckMsg);

        int optValueIdx = optValueMap.get(itmNm).indexOf(optValue);	// 옵션명에 따른 옵션값리스트 맵  {사이즈=[95, 100, 105, 110. 90], 색상=[빨간색, 노란색, 파란색, 검정색, 흰색]}

        // 옵션명에 따른 옵션값리스트 맵에 해당 옵션값이 없을 경우에만 옵션value BO 생성, 옵션값리스트에 해당 옵션값을 저장.
        if (optValueIdx < OptionConstDef.INT_ZERO) {
            pdOptValueVO = setPdOptValueVO(optItemBO, optValue, PRD_STCK_STAT_CD_USE, userNo);	// 옵션값 BO(ProductOptValueB) 설정(필요없을지도)
            optItemBO.getPdOptValueVOList().add(pdOptValueVO);									// 옵션 value BO 리스트에 위라인에서 생성한 옵션값 BO setting
            optValueMap.get(itmNm).add(optValue); 													// 옵션명에 따른 옵션값리스트에 옵션값 저장
        }
    }











    public void processProductOptionNew(ProductVO productVO) throws OptionException {
        if(productVO == null && productVO.getOptionVO() == null) throw new OptionException(OptionServiceExceptionMessage.SET_OPTION_DATA_ERROR);

        //TODO: ASIS - TOBE 간 데이터비교 필수
        String createCd = StringUtils.defaultIfEmpty(StringUtil.nvl(productVO.getBaseVO().getCreateCd(), CreateCdTypes.MO_SIMPLEREG.getDtlsCd()), CreateCdTypes.MO_SIMPLEREG.getDtlsCd());
        boolean isApi = "01".equals(createCd.substring(0,2));

        long userNo				= productVO.getSelMnbdNo(); // 현재 접속자 NO
        long selPrc 			= productVO.getPriceVO().getSelPrc();    // 상품판매가
        double prdCstmAplPrc	= productVO.getPriceVO().getPrdCstmAplPrc();				// 상품신고가
        String mobile1WonYn		= StringUtil.nvl(productVO.getBaseVO().getMobile1wonYn(), "N");		// 1원 상품 여부
        String minusOptLimitYn	= StringUtil.nvl(productVO.getMinusOptLimitYn(), "N");		// 마이너스 옵션 제한 여부
        long soCupnAmt 			= productVO.getPriceVO().getSoCupnAmt();						// 기본즉시할인가
        String prdExposeClfCd	= StringUtil.nvl(productVO.getOptionVO().getPrdExposeClfCd(), "00");		// 옵션노출방식 (등록순, 션값 가나다순...)
        prdExposeClfCd 			= ("".equals(prdExposeClfCd)) ? "00" : prdExposeClfCd;	// 초기화 작업
        boolean globalItgSeller	= productVO.getSellerVO().isGlobalItgSeller();			// 해외 통합출고지가 가능한 해외셀러인지 여부 flag
        String optTypCd			= productVO.getOptionVO().getOptTypCd();					// 옵션 구분 코드 (01 : 날짜형, 02: 계산형)
        String optionSaveYn		= productVO.getOptionSaveYn();				// 옵션 팝업에서 저장 여부.
        String prdTypCd			= productVO.getBaseVO().getPrdTypCd();					// 상품구분코드
        String prdSellerStockCd	= productVO.getOptionVO().getPrdSellerStockCd();			// 단품일경우 지정배송일코드
        String martPrdYn		= productVO.getMartPrdYn();
        OptionMixVO optionMixVO = productVO.getOptionVO().getOptionMixVO();
        if(optionMixVO == null) optionMixVO = new OptionMixVO();

        ArrayList<List<String>> colValues	= new ArrayList<List<String>>();					// 조합형 옵션명당 옵션값 배열을 담은 리스트 ex)색상의 조합 옵션값 배열, 사이즈의 조합 옵션값 배열
        String ctgrOptPrcVal			= StringUtil.nvl(productVO.getOptionVO().getCtgrOptPrcVal(), "1");
        String ctgrOptPrcPercent		= StringUtil.nvl(productVO.getOptionVO().getCtgrOptPrcPercent(), "100%");
        int optCnt = 0;
        if(productVO.getOptionVO().getOptionSelectList() != null && productVO.getOptionVO().getOptionSelectList().size() > 0) {
            optCnt = productVO.getOptionVO().getOptionSelectList().size();	// 조합형 옵션명 수
        }
        int colValueCnt = 0;			// 조합형 옵션값 수
        String bsnDealClf = productVO.getBaseVO().getBsnDealClf();
        boolean isPurchase = Arrays.asList(ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE, ProductConstDef.BSN_DEAL_CLF_SPECIFIC_PURCHASE, ProductConstDef.BSN_DEAL_CLF_TRUST_PURCHASE).contains(bsnDealClf);
        boolean isFreshCategory = productVO.getCtlgVO().isFreshCategory();
        String dlvClf = productVO.getBaseVO().getDlvClf();
        boolean isRetailPrd = ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(dlvClf) || (ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(bsnDealClf) && sellerValidate.isDirectBuyingMark(userNo));

        // 조합형 옵션명이 조합된 옵션값 추출
        for(int i = 0; i < optCnt;i++){
            if(i == 0) {
                colValues.add(optionMixVO.getColValue1());		// 조합형 옵션값1 ex)[빨간색,빨간색,파란색,파란색]
            } else if(i == 1) {
                colValues.add(optionMixVO.getColValue2());		// 조합형 옵션값2 ex)[95,100,95,100]
            } else if(i == 2) {
                colValues.add(optionMixVO.getColValue3());		// 조합형 옵션값3
            } else if(i == 3) {
                colValues.add(optionMixVO.getColValue4());		// 조합형 옵션값4
            } else if(i == 4) {
                colValues.add(optionMixVO.getColValue5());		// 조합형 옵션값5
            }
        }

        if (userNo == LONG_ZERO)	throw new OptionException(OptionServiceExceptionMessage.UNKNOWN_USER_ERROR);
        else if (optCnt > MAX_OPTION_CNT)	throw new OptionException(OptionServiceExceptionMessage.MIX_OPTION_OVER_CNT_ERROR);	// 조합형 옵션명 수가 구매자 선택형 옵션 최대 등록 수를 넘을 경우

        if (optCnt > 0) colValueCnt = optionMixVO.getColValue1().size();

        // 신선 카테고리이고 카탈로그 번호가 넘어오지 않았다면 강제로 -1 세팅
        if( (optionMixVO.getColCtlgNo() == null || optionMixVO.getColCtlgNo().size() == 0) && isFreshCategory && colValueCnt > 0 ) {
            optionMixVO.setColCtlgNo(new ArrayList<String>());
            optionMixVO.setColAddDesc(new ArrayList<String>());
            optionMixVO.setColSpplBnftCd(new ArrayList<String>());
            martPrdYn = "Y";

            List<String> colCtlgNoList = new ArrayList<String>();
            for(int i = 0 ; i < colValueCnt ; i++) {
                colCtlgNoList.add("-1");
            }
            optionMixVO.setColCtlgNo(colCtlgNoList);
        }

        // 조합형 옵션명당 옵션값 배열의 데이터수와 각각 조합형 옵션정보 수량 일치 여부 확인
        boolean isCntNotMatched = false;
        if (colValueCnt != optionMixVO.getColCount().size() || colValueCnt != optionMixVO.getColOptPrice().size() || colValueCnt != optionMixVO.getPrdStckStatCd().size()
            || (optionMixVO.getBarCode() != null && optionMixVO.getBarCode().size()>0 && colValueCnt != optionMixVO.getBarCode().size())
            || (optionMixVO.getColOptWght() != null && optionMixVO.getColOptWght().size()>0 && colValueCnt != optionMixVO.getColOptWght().size())
            || (optionMixVO.getColCstmAplPrc() != null && optionMixVO.getColCstmAplPrc().size()>0 && colValueCnt != optionMixVO.getColCstmAplPrc().size())
            || (isPurchase && (colValueCnt != optionMixVO.getColPuchPrc().size() || colValueCnt != optionMixVO.getColMrgnRt().size() || colValueCnt != optionMixVO.getColAprvStatCd().size()))) isCntNotMatched = true;
        if(!productVO.getMemberVO().isCertStockSeller()) {
            if(optionMixVO.getColSellerStockCd() != null && optionMixVO.getColSellerStockCd().size()>0 && colValueCnt != optionMixVO.getColSellerStockCd().size()) isCntNotMatched = true;
        }
        /*
        if(productVO.getMemberVO().isCertStockSeller()) {
            if (colValueCnt != colQty.length || colValueCnt != colAddPrc.length || colValueCnt != prdStckStatCd.length
                || (colBarCode != null && colBarCode.length>0 && colValueCnt != colBarCode.length)
                || (colOptWght != null && colOptWght.length>0 && colValueCnt != colOptWght.length)
                || (colCstmAplPrc != null && colCstmAplPrc.length>0 && colValueCnt != colCstmAplPrc.length)
                || (isPurchase && (colValueCnt != colPuchPrc.length || colValueCnt != colMrgnRt.length || colValueCnt != colAprvStatCd.length))) isCntNotMatched = true;
        } else {
            if (colValueCnt != colQty.length || colValueCnt != colAddPrc.length || colValueCnt != prdStckStatCd.length
                || (colBarCode != null && colBarCode.length>0 && colValueCnt != colBarCode.length)
                || (colOptWght != null && colOptWght.length>0 && colValueCnt != colOptWght.length)
                || (colCstmAplPrc != null && colCstmAplPrc.length>0 && colValueCnt != colCstmAplPrc.length)
                || (colSellerStockCd != null && colSellerStockCd.length>0 && colValueCnt != colSellerStockCd.length)
                || (isPurchase && (colValueCnt != colPuchPrc.length || colValueCnt != colMrgnRt.length || colValueCnt != colAprvStatCd.length))) isCntNotMatched = true;
        }
        */

        if(!isCntNotMatched && productVO.isBundleProduct() && (colValueCnt != optionMixVO.getColSetTypCd().size() || colValueCnt != optionMixVO.getColCompInfoJson().size())){
            isCntNotMatched = true;
        }

        if(isCntNotMatched) throw new OptionException(OptionServiceExceptionMessage.CANT_SAME_MIX_OPTION_CNT_ERROR);

        int index = 1;
        for (List<String> colValue : colValues) {
            if (colValueCnt != colValue.size()) throw new OptionException("조합형 옵션 정보 중, 옵션명 "+index+"째 옵션값 정보 수가 일치하지 않습니다.");
            index++;
        }

        String checkMsg = NULL_STRING;	// 에러 메세지
        String optNm 	= NULL_STRING;	// 옵션명
        String optValue = NULL_STRING;	// 옵션값

        Map<String, PdOptItemVO> optItemMap	= new LinkedHashMap<String, PdOptItemVO>();	// 옵션명 (아이템) 맵
        Map<String, ArrayList<String>> optValueMap	= new LinkedHashMap<String, ArrayList<String>>();	// 옵션명에 따른 옵션값리스트 맵
        Map<String, String> optItemImageMap	= new LinkedHashMap<String, String>();	// 스마트옵션 이미지 맵
        Map<String, String> optItemDetailImageMap	= new LinkedHashMap<String, String>();	// 스마트옵션 자세히보기 이미지 맵
        Map<String, Integer> stockIndexMap			= new LinkedHashMap<String, Integer>();			// 조합형 재고에 중복 데이터 유무 체크 맵
        ArrayList<ProductStockVO> prdStockVOList		= new ArrayList<ProductStockVO>();			// 조합형 재고 ArrayList

        PdOptItemVO optItemBO;
        //if(checkSelMnbdNo <= 0) checkSelMnbdNo = userNo;
        /**
         * 1. 조합형 옵션명 정보 유효성 체크 및 옵션명 정보 설정 (옵션명의 중복 여부 체크 및 옵션 아이템 BO(ProductOptItemBO)를 리스트에 담기)
         * 조합형 옵션명 수만큼 반복문 수행.
         **/
        for (int i = 0; i < optCnt; i ++) {
            optNm = productVO.getOptionVO().getOptionSelectList().get(i).getName().trim();  // 옵션명

            checkMsg = optionValidate.checkMsgOptNmVal(USE_N, optNm, userNo);	// 옵션 아이템 명의 사용 유효성 체크
            if (StringUtil.isNotEmpty(checkMsg)) throw new OptionException((i+1) + "번째 옵션명을 확인 하십시오.\n" + checkMsg);

            // 옵션항목구분코드:옵션명 ex) 01:색상
            String optItemKey = OPT_CLF_CD_MIXED + OPTION_ITEM_VALUE_DELIMITER + optNm;
            optItemBO = optItemMap.get(optItemKey);

            // 옵션명 (아이템)맵에 해당 옵션명의 옵션아이템 BO가 존재하지 않을 경우 새로운 옵션아이템BO 생성
            if (optItemBO == null) {
                optItemBO = setPdOptItemVO(i+1, optNm, OPT_CLF_CD_MIXED, PRD_STCK_STAT_CD_USE, userNo, prdExposeClfCd);	// 옵션 아이템 BO 설정

                // 날짜형 옵션이고 1번째 항목일 경우 옵션 구분 코드 추가
                if( i == 0 && OPT_TYP_CD_DAY.equals(optTypCd) ) {
                    optItemBO.setOptTypCd(optTypCd);
                } else if(i == (optCnt-1) && OPT_TYP_CD_RENT.equals(optTypCd) ){
                    optItemBO.setOptTypCd(optTypCd);
                } else if(OPT_TYP_CD_CALC.equals(optTypCd)) {
                    optItemBO.setOptTypCd(optTypCd);
                }

                // 옵션정보 중복 체크를 위한 LinkedHashMap 객체 설정
                optItemMap.put(optItemKey, optItemBO);				// 옵션명, 옵션 아이템 BO
                optValueMap.put(optNm, new ArrayList<String>());	// 옵션명, 옵션값 ArrayList
            } else {
                throw new OptionException("중복된 옵션명[" + optNm + "]이 존재합니다.");
            }
        }

        //ArrayList<String> colAddPrcArray = new ArrayList<String>(Arrays.asList(colAddPrc));
        List<String> colAddPrcArray = optionMixVO.getColOptPrice();
        if (!OPT_TYP_CD_RENT.equals(optTypCd) && colAddPrcArray.size() > INT_ZERO && colAddPrcArray.indexOf("0") < INT_ZERO) throw new OptionException("옵션가격이 0원인 옵션이 하나 이상이 있어야 합니다");

        String _stockKey = NULL_STRING;		// 재고정보 중복 체크 생성키
        int minDate = INT_ZERO;			// 사용기간 시작일
        int maxDate = INT_ZERO;			// 사용기간 종료일
        int tempValue = INT_ZERO;
        String optCheckMsg = null;

        /**
         * 2. 조합형 옵션값/재고 유효성 체크 및 데이터 정보 설정 (옵션 값과 재고에 대한 중복 여부 체크 및 재고 BO(ProductStockBO)를 리스트에 담기)
         * 조합형 옵션값 수만큼 반복문 수행.
         **/
        for (int i = 0; i < colValueCnt; i++) {
            // variable initialization
            ProductStockVO prdStckVO = new ProductStockVO();
            _stockKey = ""; /*재고정보 중복 체크 생성키*/

            // 조합형 옵션명 수만큼 반복문 수행 (역순으로 처리)
            for(int j = optCnt - 1; j >= 0; --j) {
                optNm		= productVO.getOptionVO().getOptionSelectList().get(j).getName().trim();					// 옵션명
                optValue	= colValues.get(j).get(i).trim();       // 옵션값

                setOptionValueBO(i, optNm, optValue, userNo, optItemMap, optValueMap); // 옵션 아이템 BO에 있는 ProductOptValueBOList 리스트 객체에 새로 생성한 옵션값 BO를 설정 (ex.사이즈 옵션 아이템 BO에 중복되지 않는 N개의 옵션값BO 존재)

                // 옵션항목구분코드:옵션명 ex) 01:색상
                String optItemKey = OPT_CLF_CD_MIXED + OPTION_ITEM_VALUE_DELIMITER + optNm;
                optItemBO = optItemMap.get(optItemKey);				// 옵션 아이템 BO

                // 날짜형 옵션 일 경우 1번째 항목 날짜 유효한지 체크
                if( optItemBO.getOptItemNo() == 1 && OPT_TYP_CD_DAY.equals(optTypCd) ) {
                    // 날짜형 옵션 데이터 체크
                    optCheckMsg = optionValidate.checkDayOptVal(optValue, optNm, i + 1);
                    if (StringUtil.isNotEmpty(optCheckMsg)) throw new OptionException(optCheckMsg);

                    // 초기 상품 등록 시 오늘 날짜보다 이전인지 체크
                    if( !"Y".equals(optionSaveYn) && Integer.parseInt(optValue.replaceAll("-", ""), 10) < Integer.parseInt(DateUtil.getFormatString("yyyyMMdd"), 10)) {
                        throw new OptionException(optNm + "의" + (i + 1) + "번째 옵션값 [" + optValue + "]을 확인 하십시오.\n오늘보다 이전 날짜는 등록할 수 없습니다.");
                    }

                    // 옵션 팝업에서 저장시에만 처리
                    if( "Y".equals(optionSaveYn) && ProductConstDef.PRD_TYP_CD_TOWN_SALES.equals(prdTypCd) ) {
                        tempValue = Integer.parseInt(optValue.replaceAll("-", ""), 10);
                        if( minDate == 0 ) {
                            minDate = tempValue;
                            maxDate = tempValue;
                        } else {
                            if( tempValue < minDate ) {
                                minDate = tempValue;
                            } else if( tempValue > maxDate ) {
                                maxDate = tempValue;
                            }
                        }
                    }
                }

                int optValueIdx = optValueMap.get(optNm).indexOf(optValue);	// {사이즈=[95, 100, 105, 110. 90], 색상=[빨간색, 노란색, 파란색, 검정색, 흰색]}
                Object[] obj ={optItemBO.getOptItemNo(), (optValueIdx > -1 ? ((PdOptValueVO) optItemBO.getPdOptValueVOList().get(optValueIdx)).getOptValueNo() : 0), optValue};	// {옵션아이템 NO, 옵션값 NO, 옵션값}
                setPrdStckBOMethodInvoke(prdStckVO, obj, j+1);		// ProductStockBO 에 옵션 아이템 순번, 옵션값 순번, 옵션값을 설정함. setOptItemNo, setOptValueNo, setOptValueNm ex)(2, 1, 95사이즈), (1, 1, 빨간색), (2, 2, 100사이즈), (1, 1, 빨간색)

                // 재고정보 중복 체크를 위한 키 생성 (사이즈:95 -> 색상:빨간색†사이즈:95)
                _stockKey = optNm + OPTION_ITEM_VALUE_DELIMITER + optValue + ((StringUtil.isNotEmpty(_stockKey)) ? OPTION_DELIMITER : "") + _stockKey;

            }

            // 재고정보 중복 체크 (Key => 색상:빨간색†사이즈:95)
            if (stockIndexMap.get(_stockKey) == null) {
                if("Y".equals(martPrdYn)) {
                    prdStckVO.setCtlgNo(StringUtil.getLong(optionMixVO.getColCtlgNo().get(i)));								// 마트상품, 카탈로그번호
                    prdStckVO.setAddDesc("null".equals(optionMixVO.getColAddDesc().get(i))?"":optionMixVO.getColAddDesc().get(i));				// 마트상품, 추가설명
                    prdStckVO.setSpplBnftCd("null".equals(optionMixVO.getColSpplBnftCd().get(i))?"":optionMixVO.getColSpplBnftCd().get(i));		// 마트상품, 추가혜택 코드
                }

                prdStckVO.setStckQty(StringUtil.getLong(optionMixVO.getColCount().get(i).replaceAll(",", "")));	// 재고수량
                prdStckVO.setAddPrc(StringUtil.getLong(optionMixVO.getColOptPrice().get(i).replaceAll(",", "")));	// 옵션가격
                if(isPurchase){
                    prdStckVO.setPuchPrc(StringUtil.getLong(optionMixVO.getColPuchPrc().get(i).replaceAll(",", "")));	// 옵션 매입가
                    prdStckVO.setMrgnRt(StringUtil.getDouble(optionMixVO.getColMrgnRt().get(i).replaceAll(",", "")));	// 옵션 마진율
                    prdStckVO.setMrgnAmt(StringUtil.getLong(optionMixVO.getColMrgnAmt().get(i).replaceAll(",", "")));	// 옵션 마진가
                    prdStckVO.setAprvStatCd(optionMixVO.getColAprvStatCd().get(i));	// 옵션 가격 승인상태

                    productVO.getOptionVO().setPuchPrc(StringUtil.getLong(optionMixVO.getColPuchPrc().get(i).replaceAll(",", "")));	// 옵션 매입가
                    productVO.getOptionVO().setMrgnRt(StringUtil.getDouble(optionMixVO.getColMrgnRt().get(i).replaceAll(",", "")));	// 옵션 마진율
                    productVO.getOptionVO().setMrgnAmt(StringUtil.getLong(optionMixVO.getColMrgnAmt().get(i).replaceAll(",", "")));	// 옵션 마진가
                    productVO.getOptionVO().setAprvStatCd(optionMixVO.getColAprvStatCd().get(i));	// 옵션 가격 승인상태
                }

                prdStckVO.setAddPrc(StringUtil.getLong(optionMixVO.getColOptPrice().get(i).replaceAll(",", "")));	// 옵션가격
                if(OptionConstDef.OPT_TYP_CD_RENT.equals(optTypCd)) {
                    prdStckVO.setRntlCst(StringUtil.getLong(optionMixVO.getColOptPrice().get(i).replaceAll(",", "")));	// 렌탈가격
                    prdStckVO.setAddPrc(0);	// 옵션가격
                }
                prdStckVO.setPrdStckStatCd(optionMixVO.getPrdStckStatCd().get(i));	// 옵션상태
                prdStckVO.setBarCode(optionMixVO.getBarCode() != null && optionMixVO.getBarCode().size()>0 ? (optionMixVO.getBarCode().get(i) != null ? ("".equals(optionMixVO.getBarCode().get(i).trim()) ? null : optionMixVO.getBarCode().get(i).trim()) : optionMixVO.getBarCode().get(i)) : null);	// 조합형 바코드
                prdStckVO.setOptWght(StringUtil.getLong(optionMixVO.getColOptWght() != null && optionMixVO.getColOptWght().size()>0 ? (optionMixVO.getColOptWght().get(i) != null ? ("".equals(optionMixVO.getColOptWght().get(i).trim()) ? "0" : optionMixVO.getColOptWght().get(i).trim().replaceAll(",", "")) : "0") : "0"));	// 조합형 무게
                prdStckVO.setCstmOptAplPrc(StringUtil.getDouble(StringUtil.nvl(optionMixVO.getColCstmAplPrc() != null && optionMixVO.getColCstmAplPrc().size()>0 ? (optionMixVO.getColCstmAplPrc().get(i) != null ? ("".equals(optionMixVO.getColCstmAplPrc().get(i).trim()) ? null : optionMixVO.getColCstmAplPrc().get(i).trim()) : null) : null,"0").replaceAll(",", "")));
                prdStckVO.setRegRnk(i+1);								// 재고 등록순서
                prdStckVO.setCreateNo(userNo);							// 생성자
                prdStckVO.setUpdateNo(userNo);							// 수정자
                if(isApi || (productVO.getMemberVO().isCertStockSeller() && optionMixVO.getColSellerStockCd() != null && optionMixVO.getColSellerStockCd().size() > 0)) prdStckVO.setSellerStockCd(optionMixVO.getColSellerStockCd().get(i));
                else if(productVO.getMemberVO().isCertStockSeller() && prdSellerStockCd != null && !"".equals(prdSellerStockCd)) prdStckVO.setSellerStockCd(prdSellerStockCd);

                // 옵션가격 체크
                if(!OPT_TYP_CD_RENT.equals(optTypCd)) {
                    checkMsg = optionValidate.checkMsgOptPrice(Long.toString(prdStckVO.getAddPrc()), selPrc, mobile1WonYn, minusOptLimitYn, ctgrOptPrcVal, ctgrOptPrcPercent, soCupnAmt, productVO.getOptionVO().getMrgnPolicyCd(), prdTypCd.equals(ProductConstDef.PRD_TYP_CD_RENTAL), userNo);
                }
                if(isPurchase && StringUtil.isEmpty(checkMsg) && productVO.getMemberVO().isFrSeller() && prdStckVO.getAddPrc() != 0){
                    checkMsg = "특정매입의 해외명품은 옵션가격 입력이 불가합니다.";
                }
                if (StringUtil.isNotEmpty(checkMsg)) throw new OptionException((i+1) + "번째 옵션가격을 확인 하십시오.\n" + checkMsg);

                // 옵션재고수량 체크
                if(ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(bsnDealClf) || ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(dlvClf)){
                    if(optionMixVO.getOriColCount() == null || optionMixVO.getOriColCount().size() == 0){
                        if(prdStckVO.getStckQty() > 0) checkMsg = (ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(dlvClf)?"셀러위탁배송 ":"직매입 ")+"상품은 재고수량 입력이 불가합니다.";
                    }else{
                        if((StringUtil.isEmpty(optionMixVO.getOriColCount().get(i)) && prdStckVO.getStckQty() > 0)
                            || (StringUtil.isNotEmpty(optionMixVO.getOriColCount().get(i)) && prdStckVO.getStckQty() != StringUtil.getLong(optionMixVO.getOriColCount().get(i)))) {
                            checkMsg = (ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(dlvClf)?"셀러위탁배송 ":"직매입 ")+"상품은 재고수량 입력이 불가합니다.";
                        }
                    }
                }
                if(productVO.isBundleProduct()){
                    // 현재 묶음 상품의 재고 정보의 재고수량은 체크 안함
                }else{
                    if (StringUtil.isEmpty(checkMsg)) checkMsg = optionValidate.checkMsgOptStock(Long.toString(prdStckVO.getStckQty()), prdStckVO.getPrdStckStatCd(), bsnDealClf, dlvClf);
                    if (StringUtil.isNotEmpty(checkMsg)) throw new OptionException((i+1) + "번째 재고수량을 확인 하십시오.\n" + checkMsg);
                }
                //재고수량 수정 여부 값 설정.(디폴트 값 true)
                if(optionMixVO.getOriColCount() != null && optionMixVO.getOriColCount().size() != 0 && prdStckVO.getStckQty() == StringUtil.getLong(optionMixVO.getOriColCount().get(i))) {
                    prdStckVO.setStckQtyModYn(USE_N);
                }

                if(isPurchase){
                    if(productVO.isBundleProduct()){
                        // 현재 묶음 상품의 재고 정보의 매입가 및 마진율은 무의미함.
                        // 만약 재고마다 정확한 매입가 및 마진율이 필요하다면 계산하여 넣어줌
                    }else{
                        // 옵션매입가,마진율 체크
                        prdStckVO.setAddedSelPrc(selPrc+prdStckVO.getAddPrc());
                        prdStckVO.setMrgnPolicyCd(productVO.getOptionVO().getMrgnPolicyCd());
                        prdStckVO.setBsnDealClf(bsnDealClf);
                        prdStckVO.setSelMnbdNo(userNo);
                        checkMsg = checkMsgPurchaseAndMargin(prdStckVO);
                        if (StringUtil.isNotEmpty(checkMsg)) throw new OptionException((i+1) + "번째  "+checkMsg);
                    }
                }
                if(isRetailPrd && !productVO.isBundleProduct() && StringUtil.isEmpty(prdStckVO.getBarCode())){
                    throw new OptionException((i+1) + "번째 바코드를 입력 하십시오.");
                }

                // 옵션 신고가 체크
                checkMsg = optionValidate.checkMsgOptCstmAplPrc(Double.toString(prdStckVO.getCstmOptAplPrc()), globalItgSeller, prdCstmAplPrc);
                if (StringUtil.isNotEmpty(checkMsg)) throw new OptionException((i+1) + "번째  옵션 상품신고가를 확인 하십시오.\n" + checkMsg);

                //요약/상세/그룹번호 저장
                // 옵션 그룹번호
                if( optionMixVO.getGroupNumber() != null && optionMixVO.getGroupNumber().size() > 0 ) {
                    prdStckVO.setGroupNumber(optionMixVO.getGroupNumber().get(i).replaceAll(",", NULL_STRING));
                }
                // 옵션 요약정보이미지
                if( optionMixVO.getSummaryImage() != null && optionMixVO.getSummaryImage().size() > 0 ) {
                    prdStckVO.setSummaryImage(optionMixVO.getSummaryImage().get(i).replaceAll(",", NULL_STRING));
                }
                // 옵션 상세정보이미지
                if( optionMixVO.getDetailImage() != null && optionMixVO.getDetailImage().size() > 0 ) {
                    prdStckVO.setDetailImage(optionMixVO.getDetailImage().get(i).replaceAll(",", NULL_STRING));
                }
                // 옵션 상세정보이미지 값
                if( optionMixVO.getDetailImageStr() != null && optionMixVO.getDetailImageStr().size() > 0 ) {
                    prdStckVO.setDetailImageStr(optionMixVO.getDetailImageStr().get(i));
                }

                if( optionMixVO.getSummaryImage() != null && optionMixVO.getSummaryImage().size() > 0 && optionMixVO.getSummaryImage().get(i).replaceAll(",", NULL_STRING).indexOf("등록됨") > -1 ) {
                    optItemImageMap.put(prdStckVO.getOptValueNm1(), optionMixVO.getSummaryImageStr().get(i).replaceAll(",", NULL_STRING));
                }

                if( optionMixVO.getDetailImage() != null && optionMixVO.getDetailImage().size() > 0 && optionMixVO.getDetailImage().get(i).replaceAll(",", NULL_STRING).indexOf("등록됨") > -1 ) {
                    optItemDetailImageMap.put(prdStckVO.getOptValueNm1(), optionMixVO.getDetailImageStr().get(i).replaceAll(",", NULL_STRING));
                }

                // 상품구성체크
                if(productVO.isBundleProduct()){
                    prdStckVO.setSetTypCd(optionMixVO.getColSetTypCd().get(i));
                    String compInfoJson = optionMixVO.getColCompInfoJson().get(i);

                    if(ProductConstDef.SetTypCd.getSetTypCd(prdStckVO.getSetTypCd()) == null) throw new OptionException("유효하지 않은 상품 구성 코드입니다.");
                    if(StringUtil.isEmpty(compInfoJson)) throw new OptionException((i+1) + "번째 옵션 구성 정보를 확인 하십시오.");

                    prdStckVO.setStockSetInfoListVO(new ArrayList<ProductStockSetInfoVO>());
                    try {
                        JSONArray obj= JSONArray.fromObject(URLDecoder.decode(compInfoJson, "UTF-8"));
                        int totDscRt = 0;
                        for(int j=0; j<obj.size(); j++){
                            ProductStockSetInfoVO productStockSetInfoVO = new ProductStockSetInfoVO();
                            Map map = BeanUtils.describe(JSONObject.toBean((JSONObject)obj.get(j)));
                            BeanUtils.populate(productStockSetInfoVO, map);

                            if(productStockSetInfoVO.getCompStckNo() <= 0) throw new OptionException((i+1) + "번째 옵션 구성 정보를 확인 하십시오.");
                            if(ProductConstDef.SetTypCd.SET.equals(prdStckVO.getSetTypCd()) && (productStockSetInfoVO.getSetDscRt() < 0 || productStockSetInfoVO.getSetDscRt() > 100)) throw new OptionException((i+1) + "번째 옵션의 할인율을 확인해주세요.");
                            if(productStockSetInfoVO.getSetUnitQty() <=0) throw new OptionException((i+1) + "번째 옵션의 구성수량을 확인해주세요.");

                            prdStckVO.getStockSetInfoListVO().add(productStockSetInfoVO);

                            totDscRt += productStockSetInfoVO.getSetDscRt();
                        }

                        if(ProductConstDef.SetTypCd.SET.equals(prdStckVO.getSetTypCd())){
                            if(totDscRt >= 100*obj.size()) throw new OptionException((i+1) + "번째 옵션의 할인율을 확인해주세요.");
                        }
                    }catch (OptionException e) {
                        throw e;
                    } catch (Exception e) {
                        throw new OptionException("상품 구성 정보 변환 오류.");
                    }
                }

                prdStockVOList.add(prdStckVO); // ProductStockBO List 객체 추가 (ex.14개 조합형 재고 등록)

                // 조합형 재고정보 중복 체크를 위한 LinkedHashMap 객체 설정 ({색상:빨간색†사이즈:95, 0}, {색상:빨간색†사이즈:100, 1})
                stockIndexMap.put(_stockKey, prdStockVOList.size() - 1);

            }
            else {// 재고 정보 중복
                throw new OptionException((i+1) + "번째 리스트 정보에 중복된 데이터가 존재합니다.\n" + _stockKey);
            }
        }

        // 옵션/재고정보 BO 전달
        productVO.getOptionVO().setOptItemMap(optItemMap);
        productVO.getOptionVO().setProductStockVOList(prdStockVOList);
        productVO.getOptionVO().setOptValueMap((HashMap<String, ArrayList<String>>) optValueMap);
        productVO.getOptionVO().setStckIndexMap((HashMap<String, Integer>) stockIndexMap);
        productVO.getOptionVO().setOptColCnt(optCnt);
        productVO.getOptionVO().setOptItemImageMap(optItemImageMap);
        productVO.getOptionVO().setOptItemDetailImageMap(optItemDetailImageMap);


        // 옵션 팝업에서 저장시에만 처리
        if( USE_Y.equals(optionSaveYn) && ProductConstDef.PRD_TYP_CD_TOWN_SALES.equals(prdTypCd) && OPT_TYP_CD_DAY.equals(optTypCd) ) {
            String useBgnDy = String.valueOf(minDate);
            String useEndDy = String.valueOf(maxDate);

            int daysBetween = INT_ZERO;

            try {
                daysBetween = DateUtil.daysBetween(useBgnDy, useEndDy, "yyyyMMdd")+1;
            } catch (ParseException e) {
                throw new OptionException("날짜형식이 올바르지 않습니다.\nYYYY-MM-DD형식으로 입력해 주십시오.");
            }

            // 70일 초과하는지 체크
            if( daysBetween > ProductConstDef.USE_DAY_TERM_CERT ) {
                throw new OptionException("날짜형 설정 기간은 "+ProductConstDef.USE_DAY_TERM_CERT+"일 까지 설정 가능합니다.");
            }

            // 판매시작일보다 이전인지 체크
            if( Integer.parseInt(useBgnDy) < Integer.parseInt(productVO.getBaseVO().getSelBgnDy()) ) {
                throw new OptionException("옵션날짜 시작일이 판매기간 시작일보다 늦어야 합니다.");
            }

            // 판매종료일보다 이전인지 체크
            if( Integer.parseInt(useEndDy) < Integer.parseInt(productVO.getBaseVO().getSelEndDy()) ) {
                throw new OptionException("옵션날짜 종료일이 판매기간 종료일보다 늦어야 합니다.");
            }

            useBgnDy = useBgnDy.substring(0, 4) + "/" + useBgnDy.substring(4, 6) + "/" + useBgnDy.substring(6);
            useEndDy = useEndDy.substring(0, 4) + "/" + useEndDy.substring(4, 6) + "/" + useEndDy.substring(6);

            productVO.getOptionVO().setUseBgnDy(useBgnDy);
            productVO.getOptionVO().setUseEndDy(useEndDy);
        }

        if("Y".equals(productVO.getBaseVO().getStdPrdYn())) {
            //optionMap.put("dispCtgrNo", dataBox.getLong("dispCtgrNo", 0));
            //bindStandardOptionInfo(optionMap);
            bindStandardOptionInfo(productVO);
        }

        //String msg = productVaildation(dataBox);
        //if (!"".equals(msg)) throw new OptionException(msg);

        //return optionMap;
    }

    private void bindStandardOptionInfo(ProductVO productVO) throws OptionException {
        StandardOptionGroupMappingVO paramBO = new StandardOptionGroupMappingVO();
        paramBO.setDispCtgrNo(productVO.getDispCtgrNo());

        List<StandardOptionItemVO> optItemList = optionService.getOptInfoListAtStd(paramBO);
        if(StringUtil.isEmpty(optItemList)) return;

        //Map<String, PdOptItemVO> optItemMap = (Map<String, PdOptItemVO>)optionMap.get("optItemMap");
        Map<String, PdOptItemVO> optItemMap = (Map<String, PdOptItemVO>) productVO.getOptionVO().getOptItemMap();
        Set<String> optItemKey = optItemMap.keySet();

        int matchCnt = 0;
        for(String key : optItemKey){
            PdOptItemVO productOptItemBO = optItemMap.get(key);

            for(StandardOptionItemVO standardOptionItemBO : optItemList){
                if(productOptItemBO.getOptItemNm().equals(standardOptionItemBO.getStdOptNm())){
                    productOptItemBO.setStdOptNo(standardOptionItemBO.getStdOptNo());

                    for(PdOptValueVO productOptValueBO : productOptItemBO.getPdOptValueVOList()){
                        productOptValueBO.setStdOptNo(standardOptionItemBO.getStdOptNo());

                        for(StandardOptionValueVO standardOptionValueBO : standardOptionItemBO.getStdOptValList()){
                            if(productOptValueBO.getOptValueNm().equals(standardOptionValueBO.getStdOptValNm())){
                                productOptValueBO.setStdOptValNo(standardOptionValueBO.getStdOptValNo());
                            }
                        }
                    }
                    matchCnt++;
                }
            }
        }

        if(matchCnt != optItemKey.size()) throw new OptionException("표준상품 옵션 매핑에 실패 했습니다.[옵션명 불일치]");
    }
}
