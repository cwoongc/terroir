package com.elevenst.terroir.product.registration.product.validate;

import com.elevenst.common.properties.PropMgr;
import com.elevenst.common.util.DateUtil;
import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.NumberUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.terroir.product.registration.category.code.TR184_DispCtgrAttrCd;
import com.elevenst.terroir.product.registration.category.service.CategoryServiceImpl;
import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import com.elevenst.terroir.product.registration.common.code.BsnDealClf;
import com.elevenst.terroir.product.registration.common.service.CommonServiceImpl;
import com.elevenst.terroir.product.registration.common.vo.SyCoDetailVO;
import com.elevenst.terroir.product.registration.customer.service.CustomerServiceImpl;
import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.etc.vo.PrdOthersVO;
import com.elevenst.terroir.product.registration.option.code.OptionConstDef;
import com.elevenst.terroir.product.registration.option.vo.ProductOptLimitVO;
import com.elevenst.terroir.product.registration.product.code.ForAbrdBuyClfCdTypes;
import com.elevenst.terroir.product.registration.product.code.PrdStatCd;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.code.SelMthdCd;
import com.elevenst.terroir.product.registration.product.code.SelPrdClfCd;
import com.elevenst.terroir.product.registration.product.code.SelStatCd;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.mapper.ProductMapper;
import com.elevenst.terroir.product.registration.product.message.OrgnExceptionMessage;
import com.elevenst.terroir.product.registration.product.service.ProductServiceImpl;
import com.elevenst.terroir.product.registration.product.vo.ProductTagVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.service.SellerServiceImpl;
import com.elevenst.terroir.product.registration.seller.validate.SellerValidate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import skt.tmall.auth.Auth;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ProductValidate {

    private enum ProductExceptionEnumTypes implements ExceptionEnumTypes {
        SEL_MTHD_NULL(                  "PRD_REG", "SELMTHD01",     "판매방식은 반드시 선택하셔야 합니다.")
        ,SEL_MTHD_INVALID(               "PRD_REG", "SELMTHD02",     "유효하지 않은 판매방식 코드 입니다.")
        ,CTGR_NO_NULL(                   "PRD_REG", "REG01",         "상품 등록수정 시, 상품번호 또는 카테고리번호는 반드시 입력되어야 합니다.")
        ,PRD_TYP_INVALID(                "PRD_REG", "REG02",         "상품의 현재 카테고리에 등록할 수 없는 상품유형입니다.")
        ,SELLER_TYP_INVALID(             "PRD_REG", "REG03",         "현재 셀러가 선택할 수 없는 서비스 상품입니다.")
        ,CANNOT_MOVE_PRD(                "PRD_REG", "REG04",         "변경할 수 없는 서비스 상품입니다.")
        ,CANNOT_CHANGE_STDPRD(           "PRD_REG", "REG05",         "올바르지 않은 수정요청입니다.")
        ,NOT_YOUR_PRD(                   "PRD_REG", "REG06",         "다른셀러가 등록한 상품을 조회요청 하셨습니다.")
        ,NOT_UPDATE_STAT_PRD(            "PRD_REG", "REG07",         "수정처리 할수 없는 상품을 선택하셨습니다. 상태 [{$1}]")
        ,NOT_ENOUGH_CASH(                "PRD_REG", "REG08",         "셀러캐시가 10만원 미만입니다.")
        ,NOT_USED_COBUY("PRD_REG", "SELMTHD03", "공동구매 판매방식 폐지로 공동구매 상품 등록/수정, 판매방식 변경, 공동구매 연장이 불가합니다. 고정가 상품으로 등록해 주세요.")
        ,DIFF_DLV_CST( "PRD_REG", "REG09","11번가해외배송은 제주/도서산간배송비가 통합ID의  제주/도서산간배송비와 같아야 합니다. (제주:{$1}원, 도서산간:{$2}원)")
        ,HAVE_NOT_ITG_ID( "PRD_REG", "REG10","해당 하는 통합ID가 없습니다. (제주/도서산간배송비 체크 오류)")
        ,MAX_LEN_PRD_NM( "PRD_REG", "REG11","상품명은 {$1}Byte 이하로 등록이 가능합니다.")
        ,TNS_PRD_NM( "PRD_REG", "REG12","상품명에 금칙어가 사용 되었습니다. [ {$1} ]")
        ,MAX_LEN_PRD_NM_ENG( "PRD_REG", "REG12","영문 상품명은 {$1}바이트 이상 입력하실 수 없습니다.")
        ;

        private final String msgGroupId;
        private final String code;
        private final String message;
        ProductExceptionEnumTypes(String msgGroupId, String code, String message) {
            this.msgGroupId = msgGroupId;
            this.code = code;
            this.message = message;
        }

        @Override
        public String getMsgGroupId() {
            return msgGroupId;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    @Autowired
    ProductServiceImpl productServiceImpl;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CustomerServiceImpl customerServiceImpl;

    @Autowired
    CategoryServiceImpl categoryServiceImpl;

    @Autowired
    SellerServiceImpl sellerService;

    @Autowired
    CommonServiceImpl commonService;

    @Autowired
    SellerValidate sellerValidate;

    @Autowired
    PropMgr propMgr;

    //productVO.isLifeSvcTyp() 로 대체 할것
    @Deprecated
    public boolean checkLifeSvcTyp(ProductVO productVO) {
        if(productVO == null) return false;

        boolean isRetTyp = false;
        if(productVO != null && ProductConstDef.PRD_TYP_CD_LIFE_TOWN_VISIT.equals(productVO.getBaseVO().getPrdTypCd()) || ProductConstDef.PRD_TYP_CD_LIFE_BUSINESS_TRIP_SERVICE.equals(productVO.getBaseVO().getPrdTypCd()) || ProductConstDef.PRD_TYP_CD_LIFE_PICKUP_N_DEVLIVERY.equals(productVO.getBaseVO().getPrdTypCd())) {
            isRetTyp = true;
        }

        return isRetTyp;
    }

    public boolean checkLifeSvcTyp(String prdTypCd) {
        if(prdTypCd == null || "".equals(prdTypCd)) return false;

        boolean isRetTyp = false;
        if(prdTypCd != null && ProductConstDef.PRD_TYP_CD_LIFE_TOWN_VISIT.equals(prdTypCd) || ProductConstDef.PRD_TYP_CD_LIFE_BUSINESS_TRIP_SERVICE.equals(prdTypCd) || ProductConstDef.PRD_TYP_CD_LIFE_PICKUP_N_DEVLIVERY.equals(prdTypCd)) {
            isRetTyp = true;
        }

        return isRetTyp;
    }

    // 재고수량  ===========================================================================
    public boolean checkPrdSelQty(String updYn, String prdSelQty, boolean isMixedOpt, String bsnDealClf, String isTownExcelUser, String dlvClf) throws Exception {
        String msg = checkMsgPrdSelQty(updYn, prdSelQty, isMixedOpt, bsnDealClf, isTownExcelUser, dlvClf);

        if (StringUtil.isNotEmpty(msg))  {
            throw new ProductException(msg);
        }

        return true;
    }
    // 재고수량  ===========================================================================
    public boolean checkPrdSelQty(String optClfCd, String updYn, String prdSelQty, boolean isMixedOpt, String bsnDealClf, String isTownExcelUser, String dlvClf) throws Exception {
        if(OptionConstDef.OPT_CLF_CD_IDEP.equals(optClfCd))
            return true;

        return checkPrdSelQty(updYn, prdSelQty, isMixedOpt, bsnDealClf, isTownExcelUser, dlvClf);
    }

    public String checkMsgPrdSelQty(String updYn, String prdSelQty, boolean isMixedOpt, String bsnDealClf, String isTownExcelUser, String dlvClf) throws Exception {
        long selQty = 0;
        // 직매입, 외부인증번호 (엑셀업로드 셀러), 위탁배송 일 경우 재고 수량 0
        if(ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(bsnDealClf) || "Y".equals(isTownExcelUser) || "04".equals(dlvClf)) {
            if (StringUtil.isEmpty(prdSelQty))	prdSelQty = "0";

            selQty = StringUtil.getLong(prdSelQty);
            if ("N".equals(updYn) && (!"0".equals(prdSelQty) || selQty != 0))	return "재고수량은 입력하실 수 없습니다.";
        } else {
            if (StringUtil.isEmpty(prdSelQty) && !isMixedOpt)	return "재고수량은 반드시 입력하셔야 합니다.";

            selQty = StringUtil.getLong(prdSelQty);
            if ("N".equals(updYn) && ("0".equals(prdSelQty) || selQty == 0) && !isMixedOpt && dlvClf != "04")	return "재고수량은 반드시 입력하셔야 합니다.";
        }

        if (isMixedOpt && selQty > 1000000000)	return "재고 수량은 1,000,000,000개 이하로 입력하셔야 합니다.";

        return "";
    }

    // 해외셀러의 해외 통합아이디 존재여부 체크  (현재 접속자)==================================================================
    public boolean checkGlbItgIdsBySeller(long userNo) throws ProductException {
        boolean isValid = true;
        try {
            String msg = checkMsgGlbItgIdsBySeller(userNo);

            if (StringUtil.isNotEmpty(msg))  {
                isValid = false;
            }
        } catch (Exception e) {
            return false;
        }

        return isValid;
    }

    // 해외셀러의 해외 통합아이디 존재여부 체크  (현재 접속자)==================================================================
    public String checkMsgGlbItgIdsBySeller(long userNo) throws Exception {
        long cnt = customerServiceImpl.getGlbItgIdsBySeller(userNo);

        if (cnt <= 0){
            throw new ProductException("해당 해외 셀러의 해외 통합아이디가 존재하지 않습니다.");
        }

        return "";
    }

    // 상품으로 해외셀러의 해외 통합아이디 존재여부 체크  ==================================================================
    public boolean checkGlbItgIdsByPrdNo(long prdNo) throws Exception {
        boolean isValid = true;
        try {
            String msg = checkMsgGlbItgIdsByPrdNo(prdNo);

            if (StringUtil.isNotEmpty(msg))  {
                isValid = false;
            }
        } catch (Exception e) {
            return false;
        }

        return isValid;
    }

    // 상품으로 해외셀러의 해외 통합아이디 존재여부 체크  ==================================================================
    public String checkMsgGlbItgIdsByPrdNo(long prdNo) throws Exception {
        long cnt = customerServiceImpl.getGlbItgIdsByPrdNo(prdNo);
        if (cnt <= 0){
            throw new ProductException("해외 셀러의 해외 통합아이디가 존재하지 않는 상품 입니다.");
        }

        return "";
    }

    /**
     * 카테고리 조회한 객체 재사용하기 위해 체크 후 Return
     * @param obj
     * @param dispCtgrNo
     * @return DisplayCategoryBO
     * @throws Exception
     */
    public CategoryVO getDisplayCategoryBO(Object obj, long dispCtgrNo) throws Exception {

        if( dispCtgrNo <= 0 ) {
            return null;
        }

        CategoryVO dispCtgrBO = null;
        String dataKey = "dispCtgrBOObj";

        try {
            if( obj == null ) {
                dispCtgrBO = categoryServiceImpl.getServiceDisplayCategoryInfo(dispCtgrNo);
            } else if( obj instanceof ProductVO ) {
                ProductVO productVO = (ProductVO)obj;

                if( productVO.getCategoryVO() != null ) {
                    dispCtgrBO = productVO.getCategoryVO();
                } else {
                    dispCtgrBO = categoryServiceImpl.getServiceDisplayCategoryInfo(dispCtgrNo);

                    if( dispCtgrBO != null && dispCtgrBO.getDispCtgrNo() > 0 ) {
                        productVO.setCategoryVO(dispCtgrBO);
                    }
                }
            } else if( obj instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest)obj;

                if( request.getAttribute(dataKey) != null ) {
                    dispCtgrBO = (CategoryVO)request.getAttribute(dataKey);
                } else {
                    dispCtgrBO = categoryServiceImpl.getServiceDisplayCategoryInfo(dispCtgrNo);

                    if( dispCtgrBO != null && dispCtgrBO.getDispCtgrNo() > 0 )  {
                        request.setAttribute(dataKey, dispCtgrBO);
                    }
                }
            }

            // 저장되어 있는 카테고리와 조회하려는 카테고리가 다를 경우 DB 조회
            if( dispCtgrBO != null && dispCtgrBO.getDispCtgrNo() != dispCtgrNo ) {
                dispCtgrBO = categoryServiceImpl.getServiceDisplayCategoryInfo(dispCtgrNo);
            }
        } catch(Exception e) {
            throw new ProductException("카테고리 조회한 객체 재사용하기 위해 체크 후 Return 오류", e);
        }

        return dispCtgrBO;
    }

    //공급업체
    public boolean checkSuplMemNo(long data) {

        if (data == 0) {
            throw new ProductException("공급업체는 반드시 선택하셔야 합니다.");
        }

        return true;
    }

    //판매방식
    public boolean checkSelMthdCd(String selMthdCd) {
        if (StringUtil.isEmpty(selMthdCd)) {
            throw new ProductException(ProductExceptionEnumTypes.SEL_MTHD_NULL);
        }
        else if (!GroupCodeUtil.isContainsDtlsCd(selMthdCd, SelMthdCd.class)) {
            throw new ProductException(ProductExceptionEnumTypes.SEL_MTHD_INVALID);
        }
        return true;
    }

    private boolean isPinServiceType(String... prdTypCds) {

        for(String elem : prdTypCds) {
            if(!GroupCodeUtil.isContainsDtlsCd(elem, PrdTypCd.PIN_11ST_SEND, PrdTypCd.PIN_ZERO_COST)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 상품타입 체크
     *      - 상품수정 시점에,  기존 상품의 상품정보가 없으면 throw.
     *      - 카테고리에 유효한 상품인지 체크. 체크 통과하지 못하면 throw.
     *      - PIN번호상품 유효성 체크. 체크 통과하지 못하면 throw.
     *      - 서비스 상품 변경에 대한 체크
     * */
    public boolean checkProductType(ProductVO registProductBO) {

        if(registProductBO == null) {
            throw new ProductException("상품타입 체크 오류. 파라미터를 확인하세요.");
        }

        //초기화 및 초기변수 세팅
        ProductVO copyRegProductBO = new ProductVO();
        BeanUtils.copyProperties(copyRegProductBO, registProductBO); //파라미터 영향 없게 세팅
        long regPrdNo = copyRegProductBO.getPrdNo();
        long regDispCtgrNo = copyRegProductBO.getDispCtgrNo();
        String regPrdTypCd = StringUtil.isEmpty(copyRegProductBO.getBaseVO().getPrdTypCd()) ? "01" : copyRegProductBO.getBaseVO().getPrdTypCd() ;


        //카테고리 관련 체크
        checkCtgrValid(regDispCtgrNo, regPrdNo, regPrdTypCd);

        //PIN번호 서비스 상품 유효성 체크
        if(isPinServiceType(regPrdTypCd)) {
            String regBsnDealClf = copyRegProductBO.getBaseVO().getBsnDealClf();
            String regSelMthdCd = copyRegProductBO.getBaseVO().getSelMthdCd();
            checkPinProductType(regBsnDealClf, regPrdTypCd, regSelMthdCd);
        }

        //서비스 상품 변경에 대한 체크
        ProductVO preProductVO = getPreProductVO(regPrdNo);
        if(preProductVO != null) {
            checkCanChangeProductType(regPrdTypCd, preProductVO.getBaseVO().getPrdTypCd());
        }

        return true;
    }

    private void checkCanChangeProductType(String regPrdTypCd, String prePrdTypCd) {
        if((!isPinServiceType(regPrdTypCd, prePrdTypCd)) && !StringUtil.equals(regPrdTypCd, prePrdTypCd) ) {
            throw new ProductException(ProductExceptionEnumTypes.CANNOT_MOVE_PRD);
        }
    }

    private void checkCtgrValid(long dispCtgrNo, long prdNo, String prdTypCd) {  //ProductVO regProductVO, ProductVO preProductVO) {

        CategoryVO categoryVO = new CategoryVO();
        long paramDispCtgrNo = 0;

        if(dispCtgrNo > 0) { //카테고리번호 존재하는 경우 "product.getSimpleCategoryInfo" 호출
            paramDispCtgrNo = dispCtgrNo;
        }
        else if(prdNo > 0) { // 카테고리번호 미존재 && 상품번호 존재
            paramDispCtgrNo = productServiceImpl.getProduct(prdNo).getDispCtgrNo();
        }
        else { //카테고리번호 미존재 && 상품번호 미존재
            throw new ProductException(ProductExceptionEnumTypes.CTGR_NO_NULL);
        }
        categoryVO = categoryServiceImpl.getSimpleCategoryInfo(paramDispCtgrNo);

        String dispCtgrAttrCd = categoryVO.getDispCtgrAttrCd();
        boolean isPinServiceType = isPinServiceType(prdTypCd);
        boolean isValidCtgrAttrCd = GroupCodeUtil.isContainsDtlsCd(dispCtgrAttrCd,
                                                                   TR184_DispCtgrAttrCd.POINT_PRD__03,
                                                                   TR184_DispCtgrAttrCd.POINT_VA_PAY__05);
        //"product.checkCtgrRegYn" 호출
        boolean isValidPrdType = categoryServiceImpl.canRegistProductTypeToCategory(prdTypCd,dispCtgrNo);

        if((isPinServiceType && isValidCtgrAttrCd) || !isValidPrdType) {
            throw new ProductException(ProductExceptionEnumTypes.PRD_TYP_INVALID);
        }

    }

    private void checkPinProductType(String bsnDealClf, String prdTypCd, String selMthdCd) {

        switch(GroupCodeUtil.fromString(BsnDealClf.class, bsnDealClf)) {
            case DIRECT_PURCHASE:
            case SPECIFIC_PURCHASE:
                throw new ProductException("현재 셀러가 선택할 수 없는 서비스 상품입니다.");
            case COMMISSION:
                if(GroupCodeUtil.equalsDtlsCd(prdTypCd, PrdTypCd.PIN_ZERO_COST)) {
                    throw new ProductException("현재 셀러가 선택할 수 없는 서비스 상품입니다.");
                }
                break;
        }

        if(!GroupCodeUtil.equalsDtlsCd(selMthdCd, SelMthdCd.FIXED)) {
            throw new ProductException("현재 셀러가 선택할 수 없는 서비스 상품입니다.");
        }

    }

    private ProductVO getPreProductVO(long prdNo) {
        ProductVO result = null;
        if(prdNo > 0) {
            result = productServiceImpl.getProduct(prdNo);
            if (result == null) {
                throw new ProductException("상품번호["+ prdNo +"]의 상품정보가 존재하지 않습니다.");
            }
        }
        return result;
    }



    public String checkMsgPuchPrc(ProductVO productVO) throws Exception {
        if(StringUtil.isEmpty(productVO.getOptionVO().getMrgnPolicyCd())) return "마진정책은 반드시 선택하셔야 합니다.";
        if(!(ProductConstDef.MRGN_POLICY_CD_PERCNT.equals(productVO.getOptionVO().getMrgnPolicyCd()) || ProductConstDef.MRGN_POLICY_CD_WON.equals(productVO.getOptionVO().getMrgnPolicyCd()))) return "유효하지 않은 마진정책 코드입니다.";

        if(ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(productVO.getBaseVO().getBsnDealClf()) && !ProductConstDef.MRGN_POLICY_CD_WON.equals(productVO.getOptionVO().getMrgnPolicyCd())) {
            return "직매입의 경우 매입가기준 정책만 선택 가능합니다.";
        }

        long compMrgnAmt = 0;
        double compMrgnRt = 0;
        long compPuchPrc = 0;
        long selPrc = productVO.getPriceVO().getSelPrc();

        if(ProductConstDef.MRGN_POLICY_CD_PERCNT.equals(productVO.getOptionVO().getMrgnPolicyCd())) {
            double mrgnRt = productVO.getOptionVO().getMrgnRt();
            if(mrgnRt == 0) return "마진율은 반드시 입력하셔야 합니다.";
            if(!sellerValidate.chkYes24DirectAuth(productVO.getSelMnbdNo()) && mrgnRt < 0){
                return "마진율은 0보다 크게 입력하셔야 합니다.";
            }

            int dot = String.valueOf(productVO.getOptionVO().getMrgnRt()).indexOf(".");
            if(dot > -1) {
                String str = String.valueOf(productVO.getOptionVO().getMrgnRt()).substring(dot+1);
                if(str.length() > 2) {
                    return "마진율은 소수점 2자리까지 입력 가능합니다.";
                }
            }

            compMrgnAmt = NumberUtil.change_1won((selPrc*mrgnRt*0.01), "R");
            compPuchPrc = selPrc - compMrgnAmt;

            if(compPuchPrc < 0) return "매입가는 0보다 크게 입력하셔야 합니다.";
            if(productVO.getOptionVO().getPuchPrc() <= 0) {
                if(compPuchPrc != productVO.getOptionVO().getPuchPrc()) return "매입가 계산이 잘못되었습니다.";
            } else {
                productVO.getOptionVO().setPuchPrc(compPuchPrc);
            }

            if(productVO.getOptionVO().getMrgnAmt() <= 0) {
                if(compMrgnAmt != productVO.getOptionVO().getMrgnAmt()) return "마진 계산이 잘못되었습니다.";
            } else{
                productVO.getOptionVO().setMrgnAmt(compMrgnAmt);
            }
        } else {
            String puchPrcStr = String.valueOf(productVO.getOptionVO().getPuchPrc()).trim().replaceAll(",", "");
            if (StringUtil.isEmpty(puchPrcStr))	return "매입가는 반드시 입력하셔야 합니다.";

            long puchPrc = StringUtil.getLong(puchPrcStr);
            if (puchPrc == 0) return "매입가는 반드시 입력하셔야 합니다.";
            if (puchPrc < 0) return "매입가는 0보다 크게 입력하셔야 합니다.";

            compMrgnAmt = selPrc - puchPrc;
            compMrgnRt = (double) Math.round(((double)compMrgnAmt/(double)selPrc)*10000) /100;

            if(!sellerValidate.chkYes24DirectAuth(productVO.getSelMnbdNo())){
                if(compMrgnRt < 0) return "마진율은  0보다 크게 입력하셔야 합니다.";
            }

            if(productVO.getOptionVO().getMrgnRt() <= 0) {
                if(compMrgnRt != productVO.getOptionVO().getMrgnRt()) return "마진율 계산이 잘못되었습니다.";
            } else {
                productVO.getOptionVO().setMrgnRt(compMrgnRt);
            }

            if(productVO.getOptionVO().getMrgnAmt() <= 0) {
                if(compMrgnAmt != productVO.getOptionVO().getMrgnAmt()) return "마진 계산이 잘못되었습니다.";
            } else {
                productVO.getOptionVO().setMrgnAmt(compMrgnAmt);
            }
        }

        return "";
    }

    /**
     * 해외 판매대행코드 체크
     * 01:일반 , 02:해외판매대행
     */
    public boolean checkForAbrdBuyClf(String forAbrdBuyClf) {
        if (GroupCodeUtil.isContainsDtlsCd(forAbrdBuyClf, ForAbrdBuyClfCdTypes.class)) {
            return false;
        }
        return true;
    }

    public static boolean checkMobilePrd(long selPrc, long soCupnAmt, long pointAmt, String mobileYn, String mobile1WonYn) {
        String msg = null;

         msg = checkMsgMobilePrd(selPrc, soCupnAmt, pointAmt, mobileYn, mobile1WonYn);

        if (StringUtil.isNotEmpty(msg))  {
            throw new ProductException(msg);
        }
        return true;
    }

    public static String checkMsgMobilePrd(long selPrcStr, long soCupnAmt, long pointAmt, String mobileYn, String mobile1WonYn) throws ProductException {
        if (selPrcStr <= 0)return "판매가는 반드시 입력하셔야 합니다.";

        // 핸드폰 카테고리
        if ("Y".equals(mobileYn)) {

            if ("Y".equals(mobile1WonYn)) {
                if (selPrcStr != 1)	return "10원 미만 상품 등록 승인요청 시, 판매가는 1원만 입력하실 수 있습니다.";
            }
            else {
                if (selPrcStr % 10 != 0)	return "판매가는  10원 단위로 입력하셔야 합니다.";
                if (selPrcStr < 1000)	return "해당 카테고리 상품은  최소 1,000원부터 입력 가능합니다.";

                if (selPrcStr - soCupnAmt < 1000)	return " 기본즉시할인금액은 판매가 적용시 1,000원 이상만 입력하실 수 있습니다.";
            }
        }
        // 일반카테고리
        else {
            if (selPrcStr % 10 != 0)	return "판매가는  10원 단위로 입력하셔야 합니다.";
        }

        if(selPrcStr > ProductConstDef.PRD_SEL_PRC_LIMIT)return "판매가는 "+ NumberUtil.getCommaString(ProductConstDef.PRD_SEL_PRC_LIMIT)+"원 보다 크게 입력할 수 없습니다."; // 2010/10/29일 반영 - 검색이슈
        if(selPrcStr <= soCupnAmt) return "기본즉시할인금액은 판매가 이상으로 설정할 수 없습니다.";
        if(selPrcStr <= pointAmt) return "포인트금액은 판매가 이상으로 설정할 수 없습니다.";

        return "";
    }

    public ProductConstDef.PrdInfoType getStrPrdInfoType(String prdInfoType){
        if(prdInfoType == null) return ProductConstDef.PrdInfoType.ORIGINAL;

        return ProductConstDef.PrdInfoType.getPrdInfoType(prdInfoType);
    }

    /** 정기결제 설정 가능한 서비스 상품 유형 */
    public List<String> POSSIBLE_PRD_TYP_CD = Arrays.asList(ProductConstDef.PRD_TYP_CD_NORMAL, ProductConstDef.PRD_TYP_CD_NEW_MART, ProductConstDef.PRD_TYP_CD_SOHO);

    /**
     * 거래유형이 매입유형 여부
     * @return
     */
    public boolean isPurchaseType(String bsnDealClf){
        return Arrays.asList(new String[]{ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE, ProductConstDef.BSN_DEAL_CLF_SPECIFIC_PURCHASE, ProductConstDef.BSN_DEAL_CLF_TRUST_PURCHASE}).contains(bsnDealClf);
    }

    public void checkStdPrdChangeYn(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if ("Y".equals(preProductVO.getBaseVO().getStdPrdYn()) != "Y".equals(productVO.getBaseVO().getStdPrdYn())) {
            throw new ProductException(ProductExceptionEnumTypes.CANNOT_CHANGE_STDPRD);
        }
    }

    public void checkSellerPrd(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if(preProductVO.getSelMnbdNo() != productVO.getSelMnbdNo()){
            throw new ProductException(ProductExceptionEnumTypes.NOT_YOUR_PRD);
        }
    }

    public void checkStdPrdInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getBsnDealClf(), BsnDealClf.COMMISSION)
        || !ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(productVO.getBaseVO().getSelStatCd())){
            if(this.isCtgrMoveProduct(preProductVO, productVO)){
                throw new ProductException(ProductExceptionEnumTypes.CANNOT_CHANGE_STDPRD, " [카테고리]");
            }
            try {
                boolean canChangeStdPrdNm = DateUtil.daysBetween(preProductVO.getBaseVO().getStdBaseDt(), DateUtil.getFormatString("yyyyMMdd")) < 15 ? true : false;
                if (!canChangeStdPrdNm && !StringUtil.equals(productVO.getBaseVO().getStdPrdNm(), preProductVO.getBaseVO().getStdPrdNm())) {
                    throw new ProductException(ProductExceptionEnumTypes.CANNOT_CHANGE_STDPRD, " [상품명]");
                }
            } catch (ParseException e) {
                throw new ProductException(ProductExceptionEnumTypes.CANNOT_CHANGE_STDPRD, " [상품명]");
            }


//            if(!StringUtil.nvl(preProductVO.getBaseVO().getBrandNm()).equals(StringUtil.nvl(productVO.getBaseVO().getBrandNm()))){
//                throw new ProductException(ProductExceptionEnumTypes.CANNOT_CHANGE_STDPRD, " [브랜드]");
//            }
//            if(!StringUtil.nvl(preProductVO.getBaseVO().getModelNm()).equals(StringUtil.nvl(productVO.getBaseVO().getModelNm()))){
//                throw new ProductException(ProductExceptionEnumTypes.CANNOT_CHANGE_STDPRD, " [모델]");
//            }
        }
    }


    public void checkUpdatePrdStat(ProductVO preProductBO, ProductVO productVO) throws ProductException{
        //템플릿 상품의 경우는 검증하지 않음.
        if("Y".equals(preProductBO.getBaseVO().getTemplateYn())) return;

        if ("Y".equals(productVO.getReNewYn())){
            if (preProductBO.getBaseVO().getSelStatCd().equals("108")
                || preProductBO.getBaseVO().getSelStatCd().equals("109")
                || preProductBO.getBaseVO().getSelStatCd().equals("110")
                || preProductBO.getBaseVO().getSelStatCd().equals("206")
                || preProductBO.getBaseVO().getSelStatCd().equals("207")
                || preProductBO.getBaseVO().getSelStatCd().equals("208")
                || preProductBO.getBaseVO().getSelStatCd().equals("209"))
            {
                throw new ProductException(ProductExceptionEnumTypes.NOT_UPDATE_STAT_PRD, preProductBO.getBaseVO().getSelStatCd());
            }
        } else {
            if (preProductBO.getBaseVO().getSelStatCd().equals("106")
                || preProductBO.getBaseVO().getSelStatCd().equals("107")
                || preProductBO.getBaseVO().getSelStatCd().equals("108")
                || preProductBO.getBaseVO().getSelStatCd().equals("109")
                || preProductBO.getBaseVO().getSelStatCd().equals("110")
                || preProductBO.getBaseVO().getSelStatCd().equals("204")
                || preProductBO.getBaseVO().getSelStatCd().equals("205")
                || preProductBO.getBaseVO().getSelStatCd().equals("206")
                || preProductBO.getBaseVO().getSelStatCd().equals("207")
                || preProductBO.getBaseVO().getSelStatCd().equals("208")
                || preProductBO.getBaseVO().getSelStatCd().equals("209"))
            {
                throw new ProductException(ProductExceptionEnumTypes.NOT_UPDATE_STAT_PRD, preProductBO.getBaseVO().getSelStatCd());
            }
        }
    }

    public void checkEnoughSellerCash(ProductVO productVO) throws ProductException{
        if(ProductConstDef.MOBILE_SELLER_CASH_MIN > sellerService.getSellerCash(productVO)) {
            throw new ProductException(ProductExceptionEnumTypes.NOT_ENOUGH_CASH);
        }
    }

    public boolean isCtgrMoveProduct(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if(preProductVO.getDispCtgrNo() == productVO.getDispCtgrNo()){
            return false;
        }else{
            return true;
        }
    }

    @Deprecated
    public void preProductChkTemplate(ProductVO productVO) {
        if("Y".equals(productVO.getBaseVO().getTemplateYn())) {
            productVO.getBaseVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_BAN_SALE);
            productVO.getDpGnrlDispVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_BAN_SALE);

        }
    }

    public void checkProductBaseInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{

        if(productVO.isUpdate()){
            if(preProductVO == null){
                throw new ProductException("상품번호[" + productVO.getPrdNo() + "]의 상품정보가 존재하지 않습니다.");
            }
        }
        if(StringUtil.isNotEmpty(productVO.getBaseVO().getLowPrcCompExYn())){
            if(!StringUtil.isContains(ProductConstDef.DEFAULT_VALUE_Y_N, productVO.getBaseVO().getLowPrcCompExYn())){
                throw new ProductException("유효하지 않은 가격비교사이트등록 구분코드 입니다.");
            }
        }


        // 장바구니 담기제한
        if (StringUtil.isNotEmpty(productVO.getBaseVO().getBcktExYn())) {
            if(!StringUtil.isContains(ProductConstDef.DEFAULT_VALUE_Y_N, productVO.getBaseVO().getBcktExYn())){
                throw new ProductException("유효하지 않은 장바구니담기제한 구분코드 입니다.");
            }
        }
        this.checkSuplDtyfrPrdClfCd(productVO);
        this.checkForAbrdBuyClf(productVO);
        this.checkPrdStatCd(productVO);
        this.checkMinorSelCnYn(productVO);
        this.checkSuplDtyfrPrdClfCd(productVO);
        this.checkReviewDispYn(productVO);
        this.checkMnfcEftvDy(productVO);
        this.checkAbrdBrandYn(productVO);
        this.checkSelTermDy(productVO);
        this.checkSelMinLimit(productVO);
        this.checkSelMaxLimit(productVO);
        this.checkSelLimitDy(productVO);

    }

    private void checkSelLimitDy(ProductVO productVO) throws ProductException{
        if(ProductConstDef.SEL_LIMIT_TYP_CD_PERSON.equals(productVO.getBaseVO().getSelLimitTypCd())){
            int maxLimitDy = productVO.isTown() ? 99 : 30;
            productVO.getBaseVO().setTownSelLmtDy(productVO.getBaseVO().getSelLimitTerm());
            if(productVO.getBaseVO().getSelLimitTerm() < 1 || productVO.getBaseVO().getSelLimitTerm() > maxLimitDy){
                throw new ProductException("재구매 제한기간은 1~"+maxLimitDy+"일 까지 설정 가능합니다.");
            }
        }
    }

    private void checkSelMaxLimit(ProductVO productVO) throws ProductException{
        if (StringUtil.isEmpty(productVO.getBaseVO().getSelLimitTypCd())){
            productVO.getBaseVO().setSelLimitTypCd(ProductConstDef.SEL_LIMIT_TYP_CD_NONE);
        }
        if (StringUtil.isEmpty(productVO.getBaseVO().getSelLimitTypCd())){
            throw new ProductException("최대구매수량 코드는 반드시 입력하셔야 합니다.");
        }

        if (ProductConstDef.SEL_LIMIT_TYP_CD_TIMES.equals(productVO.getBaseVO().getSelLimitTypCd())
            || ProductConstDef.SEL_LIMIT_TYP_CD_PERSON.equals(productVO.getBaseVO().getSelLimitTypCd())) {
            if (productVO.getBaseVO().getSelLimitQty() <= 0) {
                throw new ProductException("최대구매수량은 반드시 입력하셔야 합니다.");
            }
            if(productVO.isTown()){
                if (productVO.getBaseVO().getSelLimitQty() > 10){
                    throw new ProductException("최대구매수량은 10개까지만 가능합니다.");
                }
            }
        }
        else if (!ProductConstDef.SEL_LIMIT_TYP_CD_NONE.equals(productVO.getBaseVO().getSelLimitTypCd())){
            throw new ProductException("유효하지 않은 최대구매수량 코드입니다.");
        }
        if(productVO.isTown()){
            if(ProductConstDef.SEL_LIMIT_TYP_CD_NONE.equals(productVO.getBaseVO().getSelLimitTypCd())){
                throw new ProductException("유효하지 않은 최대구매수량 코드입니다.");
            }
        }
    }

    private void checkSelMinLimit(ProductVO productVO) throws ProductException{

        if (StringUtil.isEmpty(productVO.getBaseVO().getSelMinLimitTypCd())){
            productVO.getBaseVO().setSelMinLimitTypCd(ProductConstDef.SEL_MIN_LIMIT_TYP_CD_NONE);
        }
        if (StringUtil.isEmpty(productVO.getBaseVO().getSelMinLimitTypCd())) {
            throw new ProductException("최소구매수량 코드는 반드시 입력하셔야 합니다.");
        }

        if (ProductConstDef.SEL_MIN_LIMIT_TYP_CD_TIMES.equals(productVO.getBaseVO().getSelMinLimitTypCd())) {
            if (productVO.getBaseVO().getSelMinLimitQty() <= 0){
                throw new ProductException("최소구매수량은 반드시 입력하셔야 합니다.");
            }
        }
        else if (!ProductConstDef.SEL_MIN_LIMIT_TYP_CD_NONE.equals(productVO.getBaseVO().getSelMinLimitTypCd())) {
            throw new ProductException("유효하지 않은 최소구매수량 코드입니다.");
        }

        if (productVO.getBaseVO().getSelLimitQty() > 0) {
            if(productVO.getBaseVO().getSelMinLimitQty() > productVO.getBaseVO().getSelLimitQty()) {
                throw new ProductException("최소구매수량은 최대구매수량보다 같거나 작아야 합니다.");
            }
        }
    }

    private void checkSelTermDy(ProductVO productVO) throws ProductException{

        if(productVO.isTown()){
            if("".equals(StringUtil.nvl(productVO.getBaseVO().getSelPrdClfCd())))
                productVO.getBaseVO().setSelPrdClfCd("100");
        }
        if (StringUtil.isEmpty(productVO.getBaseVO().getSelPrdClfCd())){
            throw new ProductException("판매기간은 반드시 선택하셔야 합니다.");
        }

        if (StringUtil.isEmpty(productVO.getBaseVO().getSelPrdClfCd()) || StringUtil.isEmpty(productVO.getBaseVO().getSelTermDy())){
            throw new ProductException("판매기간은 반드시 선택하셔야 합니다.");
        }
        if (StringUtil.isEmpty(productVO.getBaseVO().getSelBgnDy())){
            throw new ProductException("판매 시작일은 반드시 입력하셔야 합니다.");
        }
        if (StringUtil.isEmpty(productVO.getBaseVO().getSelEndDy())){
            throw new ProductException("판매 종료일은 반드시 입력하셔야 합니다.");
        }
        if (StringUtil.parseLong(StringUtil.left(productVO.getBaseVO().getSelEndDy(),8)) < StringUtil.parseLong(StringUtil.left(productVO.getBaseVO().getSelBgnDy(),8))){
            throw new ProductException("판매 종료일은 판매 시작일 이전으로  입력하실 수 없습니다.");
        }


        String selBgnDy = StringUtil.left(productVO.getBaseVO().getSelBgnDy(),8);
        String selEndDy = StringUtil.left(productVO.getBaseVO().getSelEndDy(),8);
        String currDy = DateUtil.getFormatString("yyyyMMdd");

        if(!this.isPassCheckTermDy(productVO)){
            if(!productVO.isTown()){
                if(!this.checkSelPrdClfCd(productVO)){
                    throw new ProductException("판매방식에 따른 판매기간 구분 코드가 잘못되었습니다.");
                }

                String calEndDy = DateUtil.addDays(selBgnDy, Integer.parseInt(productVO.getBaseVO().getSelTermDy()), "yyyyMMdd");
                if(StringUtil.parseInt(selEndDy) != StringUtil.parseInt(calEndDy)){
                    throw new ProductException("판매종료일자는 판매기간구분코드로 계산된 날짜와 같아야 합니다."
                                                   +"(판매기간:" + calEndDy + "일, 판매시작일자:" + selBgnDy + ", 판매종료일자:" + selEndDy + ")");
                }
            }
        }
        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelStatCd(), SelStatCd.PRODUCT_DISPLAY_BEFORE)
            || StringUtil.isEmpty(productVO.getBaseVO().getSelStatCd())) {

            if (StringUtil.parseInt(selBgnDy) < StringUtil.parseInt(currDy) && !productVO.isUpdate()) {
                throw new ProductException("판매 시작일은 현재일 이전으로 입력하실 수 없습니다.");
            }
        }
        if(ProductConstDef.BACKOFFICE_TYPE.equals(productVO.getChannel())
            && GroupCodeUtil.isContainsDtlsCd(productVO.getBaseVO().getSelStatCd(),
                                              SelStatCd.PRODUCT_SELLING,
                                              SelStatCd.PRODUCT_SOLD_OUT,
                                              SelStatCd.PRODUCT_SELLING_STOP)){
            if(Integer.parseInt(selBgnDy) > Integer.parseInt(currDy)
                || Integer.parseInt(selEndDy) < Integer.parseInt(currDy)) {
                throw new ProductException("현재일자가 판매시작일자과  판매종료일자 사이에 있지 않습니다.");
            }
        }
        if(productVO.isTown()){
            //TODO CYB 타운(PIN) 일단 스킵 ProductValidate.checkPrdUseTerm(prdRegBO);
        }

        if(GroupCodeUtil.isContainsDtlsCd(productVO.getBaseVO().getSelMthdCd(), SelMthdCd.PLN, SelMthdCd.COBUY)){
            if(productVO.getPrdOthersVO() == null){
                productVO.setPrdOthersVO(new PrdOthersVO());
            }
            productVO.getPrdOthersVO().setSelPrdClfFpCd(productVO.getBaseVO().getSelPrdClfCd());
        }
        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelMthdCd(), SelMthdCd.PLN)){
            if (StringUtil.isEmpty(productVO.getPlanProductVO().getWrhsPlnDy())) throw new ProductException("입고예정일은 반드시 선택하셔야 합니다.");
            if(StringUtil.parseInt(productVO.getBaseVO().getSelBgnDy()) > StringUtil.parseInt(productVO.getPlanProductVO().getWrhsPlnDy())){
                throw new ProductException("입고예정일은 판매종료일 이전으로 설정할 수 없습니다.");
            }
        }

    }

    private boolean isPassCheckTermDy(ProductVO productVO) throws ProductException{

        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelMthdCd(), SelMthdCd.FIXED)
            && ProductConstDef.PRD_SEL_PRD_CLF_CD_CUST.equals(productVO.getBaseVO().getSelPrdClfCd())){
            return true;
        }

        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelMthdCd(), SelMthdCd.USED)
            && ProductConstDef.PRD_SEL_PRD_CLF_CD_CUST.equals(productVO.getBaseVO().getSelPrdClfCd())){
            return true;
        }
        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelMthdCd(), SelMthdCd.TOWN)
            && GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelMthdCd(), SelMthdCd.PLN)
            || ProductConstDef.PRD_SEL_PRD_CLF_CD_CUST_PLN.equals(productVO.getBaseVO().getSelPrdClfCd())){
            return true;
        }

        return false;
    }

    private void checkAbrdBrandYn(ProductVO productVO) throws ProductException{

        // 해외구입처 선택
        if ("Y".equals(productVO.getBaseVO().getAbrdBrandYn())) {
            if (StringUtil.isEmpty(productVO.getBaseVO().getAbrdBuyPlace())){
                throw new ProductException("구입처는 반드시 선택하셔야 합니다.");
            }
            if (StringUtil.isEmpty(productVO.getBaseVO().getAbrdSizetableDispYn())) productVO.getBaseVO().setAbrdSizetableDispYn("Y");
            if(StringUtil.isContains(ProductConstDef.DEFAULT_VALUE_Y_N, productVO.getBaseVO().getAbrdSizetableDispYn())){
                throw new ProductException("유효하지 않은 해외 사이즈 조건표 노출  코드 입니다.");
            }
        } else {
            if (StringUtil.isNotEmpty(productVO.getBaseVO().getAbrdBuyPlace())){
                productVO.getBaseVO().setAbrdBuyPlace("");
            }
            if (StringUtil.isNotEmpty(productVO.getBaseVO().getAbrdSizetableDispYn())){
                productVO.getBaseVO().setAbrdSizetableDispYn("");
            }

        }
    }

    private void checkMnfcEftvDy(ProductVO productVO) throws ProductException{

        // 제조일자/유효일자 & 출판일시 체크
        if ("Y".equals(productVO.getCategoryVO().getMnfcEftvDyNessYn())) {
            if (StringUtil.isEmpty(productVO.getBaseVO().getMnfcDy()))	throw new ProductException( (productVO.isBookCtgr() ? "출판/출시일자" : "제조일자") + "는 반드시 입력하셔야 합니다.");

            // 도서/음반/DVD 카테고리가 아닌경우, 유효일자 체크
            if (! productVO.isBookCtgr()) {
                if (StringUtil.isEmpty(productVO.getBaseVO().getEftvDy()))	throw new ProductException("유효일자는 반드시 입력하셔야 합니다.");

                if (StringUtil.getLong(DateUtil.getFormatString("yyyyMMdd")) > StringUtil.getLong(productVO.getBaseVO().getEftvDy()))
                    throw new ProductException("유효일자는 오늘보다 이전으로 입력하실 수 없습니다.");

                if (StringUtil.getLong(productVO.getBaseVO().getMnfcDy()) > StringUtil.getLong(productVO.getBaseVO().getEftvDy()))
                    throw new ProductException("제효일자는 유효일자보다 이후로 입력하실 수 없습니다.");
            }
        }
    }

    private void checkReviewDispYn(ProductVO productVO) throws ProductException{

        if(StringUtil.isNotEmpty(productVO.getBaseVO().getReviewDispYn())){
            if(!StringUtil.isContains(ProductConstDef.DEFAULT_VALUE_Y_N, productVO.getBaseVO().getReviewDispYn())){
                throw new ProductException("유효하지 않은 상품리뷰/구매후기 노출  코드 입니다.");
            }
        }else{
            productVO.getBaseVO().setReviewDispYn("Y");
        }
        if(StringUtil.isNotEmpty(productVO.getBaseVO().getReviewOptDispYn())){
            if(!StringUtil.isContains(ProductConstDef.DEFAULT_VALUE_Y_N, productVO.getBaseVO().getReviewOptDispYn())){
                throw new ProductException("유효하지 않은 상품리뷰/구매후기 옵션비노출 코드 입니다.");
            }
        }else{
            productVO.getBaseVO().setReviewOptDispYn("Y");
        }

        if("N".equals(productVO.getBaseVO().getReviewDispYn())
            && "N".equals(productVO.getBaseVO().getReviewOptDispYn())){
            throw new ProductException("상품리뷰/구매후기 노출안함일 경우 옵션 비노출을 선택할 수 없습니다.");
        }
    }

    private void checkSuplDtyfrPrdClfCd(ProductVO productVO) throws ProductException{
        // 부가세/면세
        // 누락시 기본값 처리 : 카테고리 기본 설정값
        if (StringUtil.isEmpty(productVO.getBaseVO().getSuplDtyfrPrdClfCd()))  {
            String dfltSuplDtyfrPrdClfCd = productVO.getCategoryVO().getDfltchkTaxableYn();
            if ("Y".equals(dfltSuplDtyfrPrdClfCd) || "N".equals(dfltSuplDtyfrPrdClfCd)) dfltSuplDtyfrPrdClfCd = "1";
            dfltSuplDtyfrPrdClfCd = "0" + dfltSuplDtyfrPrdClfCd;

            productVO.getBaseVO().setSuplDtyfrPrdClfCd(dfltSuplDtyfrPrdClfCd);
        }
        if (!commonService.isCodeDetail(ProductConstDef.PRD_SUPL_DTYFR_PRD_CLF_CD, productVO.getBaseVO().getSuplDtyfrPrdClfCd())){
            throw new ProductException("유효하지 않은 부가세/면세상품 코드 입니다.");
        }else{
            if(ProductConstDef.PRD_SUPL_DTYFR_PRD_CLF_CD_NON_TAX.equals(productVO.getBaseVO().getSuplDtyfrPrdClfCd())
                && !ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(productVO.getBaseVO().getBsnDealClf())){
                throw new ProductException("유효하지 않은 부가세/면세상품 코드 입니다.");
            }
        }
    }

    private void checkForAbrdBuyClf(ProductVO productVO) throws ProductException{
        //해외판매대행  값 디폴트 설정 01:일반상품 ,02:해외판매대행2011.09.27
        if ( StringUtil.isEmpty(productVO.getBaseVO().getForAbrdBuyClf()) ) {
            productVO.getBaseVO().setForAbrdBuyClf(ProductConstDef.PRD_GLOBAL_PROXY_CD_LOCAL);
        } else {
            // 해외판매대행일 경우 셀러 타입 체크
            if( ProductConstDef.PRD_GLOBAL_PROXY_CD_GLB.equals(productVO.getBaseVO().getForAbrdBuyClf()) ) {
                if( !ProductConstDef.PRD_MEM_TYPE_GLOBAL.equals(productVO.getMemberVO().getMemTypCD())
                    || !("01".equals(productVO.getMemberVO().getPrivateFrgnClf()) || "01".equals(productVO.getMemberVO().getEtprsFrgnClf()) ) ) {
                    throw new ProductException("해외구매대행상품 설정 불가한 판매자입니다.");
                }
            }
        }
    }

    private void checkPrdStatCd(ProductVO productVO) throws ProductException{

        // 상품상태
        // 누락시 기본값 처리 : 새상품
        if (StringUtil.isEmpty(productVO.getBaseVO().getPrdStatCd())) {
            productVO.getBaseVO().setPrdStatCd(ProductConstDef.PRD_STAT_CD_NEW);
        }
        if (!commonService.isCodeDetail(ProductConstDef.PRD_STAT_CD, productVO.getBaseVO().getPrdStatCd()))
            throw new ProductException("유효하지 않은 상품상태 코드 입니다.");

        // 중고상품일 경우에만  중고상품 선택가능 추가  2011.12.23
        // 고정가판매일 경우에만 주문제작상품 선택가능 추가 2013.06.19
        if(!ProductConstDef.PRD_SEL_MTHD_CD_USED.equals(productVO.getBaseVO().getSelMthdCd())) {
            if(ProductConstDef.PRD_STAT_CD_USED.equals(productVO.getBaseVO().getPrdStatCd())
                || ProductConstDef.PRD_STAT_CD_REFUR.equals(productVO.getBaseVO().getPrdStatCd())
                || ProductConstDef.PRD_STAT_CD_EXHIBIT.equals(productVO.getBaseVO().getPrdStatCd())
                || ProductConstDef.PRD_STAT_CD_RARITY.equals(productVO.getBaseVO().getPrdStatCd())
                || ProductConstDef.PRD_STAT_CD_RETURN.equals(productVO.getBaseVO().getPrdStatCd())
                || ProductConstDef.PRD_STAT_CD_SCRATCH.equals(productVO.getBaseVO().getPrdStatCd()))
                throw new ProductException("고정가,공동구매,경매,예약판매는 " + PrdStatCd.valueOf(productVO.getBaseVO().getPrdStatCd()).getDtlsComNm() +"을 등록할 수 없습니다.");

            if(!ProductConstDef.PRD_SEL_MTHD_CD_FIXED.equals(productVO.getBaseVO().getSelMthdCd())
                && ProductConstDef.PRD_STAT_CD_ORDER.equals(productVO.getBaseVO().getPrdStatCd())){
                throw new ProductException("공동구매,경매,예약판매는 " + PrdStatCd.valueOf(productVO.getBaseVO().getPrdStatCd()).getDtlsComNm() +"을 등록할 수 없습니다.");
            }
        }else if(ProductConstDef.PRD_SEL_MTHD_CD_USED.equals(productVO.getBaseVO().getSelMthdCd())) {
            if(ProductConstDef.PRD_STAT_CD_NEW.equals(productVO.getBaseVO().getPrdStatCd())
                || ProductConstDef.PRD_STAT_CD_IMPROVE.equals(productVO.getBaseVO().getPrdStatCd())
                || ProductConstDef.PRD_STAT_CD_ORDER.equals(productVO.getBaseVO().getPrdStatCd()))
                throw new ProductException("중고판매는 " + PrdStatCd.valueOf(productVO.getBaseVO().getPrdStatCd()).getDtlsComNm() +"을 등록할 수 없습니다.");
        }
    }

    private void checkMinorSelCnYn(ProductVO productVO) throws ProductException{
        // 미성년자 구매가능
        // 누락시 기본값 처리 : 카테고리 기본 설정값
        if (StringUtil.isEmpty(productVO.getBaseVO().getMinorSelCnYn()))  {
            productVO.getBaseVO().setMinorSelCnYn(productVO.getCategoryVO().getAdltCrtfYn());
        }
        if (!StringUtil.isContains(ProductConstDef.DEFAULT_VALUE_Y_N, productVO.getBaseVO().getMinorSelCnYn())){
            throw new ProductException("유효하지 않은 미성년자 구매가능 코드 입니다.");
        }
    }

    public void checkOrgnTypCd(ProductVO productVO) throws ProductException{
        if(StringUtil.isEmpty(productVO.getBaseVO().getOrgnTypCd())){
            throw new ProductException(OrgnExceptionMessage.ORGN_01);
        }
        if(ProductConstDef.PRD_ORGN_TYP_CD_EXT.equals(productVO.getBaseVO().getOrgnTypCd())){
            if(StringUtil.isEmpty(productVO.getBaseVO().getOrgnNm())){
                throw new ProductException(OrgnExceptionMessage.ORGN_02);
            }
        }
        else if (ProductConstDef.PRD_ORGN_TYP_CD_ABRD.equals(productVO.getBaseVO().getOrgnTypCd())){
            if (StringUtil.isEmpty(productVO.getBaseVO().getOrgnTypDtlsCd())){
                throw new ProductException(OrgnExceptionMessage.ORGN_03);
            }
        }
        else if (!ProductConstDef.PRD_ORGN_TYP_CD_LOCAL.equals(productVO.getBaseVO().getOrgnTypCd())){
            throw new ProductException(OrgnExceptionMessage.ORGN_04);
        }
    }

    public String checkProductMnbdInfoByPrdNo(Auth auth, long prdNo) throws ProductException {
        return this.checkProductMnbdInfo(auth, productMapper.getProductSelMnbdNo(prdNo));
    }

    /**
     * 자신이 등록한 상품이 맞는지 체크
     * @param auth
     * @param selMnbdNo
     * @return
     */
    public String checkProductMnbdInfo(Auth auth, long selMnbdNo) {
        String isErrorMsg = "";
        try {
            if(ProductConstDef.PRD_MEM_TYPE_INTEGRATION.equals(auth.getMemberType())) {
                //통합ID 일 경우
                boolean isPartnerSearch = false;

                List<MemberVO> suplMemList = customerServiceImpl.getSellersByItgID(auth.getUserNumber());
                if(suplMemList != null && suplMemList.size() > 0) {
                    for(MemberVO memberBO : suplMemList) {
                        if(memberBO != null && memberBO.getMemNO() == selMnbdNo) {
                            isPartnerSearch = true;
                        }
                    }
                }

                if(!isPartnerSearch) {
                    //통합ID로 조회할수 없는 회원의 상품정보를 호출하였기에 에러페이지로 이동처리
                    isErrorMsg = "다른셀러가 등록한 상품을 조회요청 하셨습니다.";
                }
            } else {
                //통합ID가 아닐 경우 (SO, TO)
                if(selMnbdNo > 0 && auth.getMemberNumber() != selMnbdNo) {
                    isErrorMsg = "다른셀러가 등록한 상품을 조회요청 하셨습니다.";
                }
            }
        } catch(Exception e) {
        }
        return isErrorMsg;
    }


    public void checkSelMthdCobuy(ProductVO productVO) throws ProductException{
        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelMthdCd(), SelMthdCd.COBUY)){
            throw new ProductException(ProductExceptionEnumTypes.NOT_USED_COBUY);
        }
    }

    public void checkGlobalDlvCost(ProductVO productVO) throws ProductException{
        if(!ProductConstDef.DLV_CLF_GLOBAL_DELIVERY.equals(productVO.getBaseVO().getDlvClf())){
            return;
        }

        List<SyCoDetailVO> syCoDetailVOList = commonService.getCodeDetail("MB100");
        boolean bFind = false;
        for(SyCoDetailVO syCoDetailVO : syCoDetailVOList){
            if(syCoDetailVO.getDtlsCd().equals(String.valueOf(productVO.getSelMnbdNo()))){
                bFind = true;

                long jeju = StringUtil.getLong(syCoDetailVO.getCdVal1());
                long island = StringUtil.getLong(syCoDetailVO.getCdVal2());

                if(jeju != productVO.getBaseVO().getJejuDlvCst()
                    || island != productVO.getBaseVO().getIslandDlvCst())
                {
                    String msg[] = {String.valueOf(productVO.getBaseVO().getJejuDlvCst()),String.valueOf(productVO.getBaseVO().getIslandDlvCst())};
                    throw new ProductException(ProductExceptionEnumTypes.DIFF_DLV_CST, msg);
                }

                break;
            }
        }
        if(!bFind) throw new ProductException(ProductExceptionEnumTypes.HAVE_NOT_ITG_ID);
    }


    public void checkRfndTypCd(ProductVO productVO) throws ProductException {
        if(!GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getPrdTypCd(),PrdTypCd.PIN_11ST_SEND)
            && StringUtil.isNotEmpty(productVO.getBaseVO().getRfndTypCd())){
            throw new ProductException("환불타입은 PIN 번호(11번가 발송) 서비스 상품에만 설정 가능합니다.");
        }
    }

    public void checkUsedProduct(ProductVO productVO) throws ProductException{
        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelMthdCd(), SelMthdCd.USED)){
            if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getPrdStatCd(), PrdStatCd.NEW)){
                throw new ProductException("중고판매는 상품상태를 새상품으로 선택 불가합니다.");
            }
        }else{
            if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getPrdStatCd(), PrdStatCd.USED)){
                throw new ProductException("상품상태를 확인 하세요. 중고상품을 등록 할 수 없습니다.");
            }
            return;
        }
        long paidSelPrc = productVO.getPriceVO().getPaidSelPrc();
        long chkUseMon = productVO.getBaseVO().getUseMon();
        String usedMnfcDyYn = productVO.getBaseVO().getUsedMnfcDyYn();//체크박스(제조일자&유효일자) 체크
        boolean isBookCtgr = productVO.isBookCtgr();

        if ((chkUseMon < 1) || (chkUseMon > 99999) ) {
            throw new ProductException("사용개월수는 반드시 0보다 크고 100000보다 작게 입력하셔야 합니다.");
        }

        // 구입당시 판매가 체크
        if (paidSelPrc == 0) {
            throw new ProductException("구입당시 판매가를 반드시 입력하셔야 합니다.");
        }
        else if (paidSelPrc % 10 != 0){
            throw new ProductException("구입당시 판매가는 10원 단위로 입력하셔야 합니다.");
        }
        else if (paidSelPrc < 0) {
            throw new ProductException("구입당시 판매가는 반드시 0보다 크게 입력하셔야 합니다.");
        }



        if(usedMnfcDyYn.equals("N")){
            if(!isBookCtgr){
                if (StringUtil.isEmpty(productVO.getBaseVO().getMnfcDy()))	throw new ProductException("제조일자는 반드시 입력하셔야 합니다.");
                if (StringUtil.isEmpty(productVO.getBaseVO().getEftvDy()))	throw new ProductException("유효일자는 반드시 입력하셔야 합니다.");

                checkMnfcDy(productVO.getBaseVO().getMnfcDy());
                checkEftvDy(productVO.getBaseVO().getEftvDy());

                if (StringUtil.getLong(productVO.getBaseVO().getMnfcDy()) > StringUtil.getLong(productVO.getBaseVO().getEftvDy()))
                    throw new ProductException("제조일자는 유효일자보다 이후로 입력하실 수 없습니다.");
            }else{
                if (StringUtil.isEmpty(productVO.getBaseVO().getMnfcDy()))	throw new ProductException("제조일자는 반드시 입력하셔야 합니다.");
                checkMnfcDy(productVO.getBaseVO().getMnfcDy());

                if(!StringUtil.isEmpty(productVO.getBaseVO().getEftvDy()))
                    checkEftvDy(productVO.getBaseVO().getEftvDy());

                if(StringUtil.isNotEmpty(productVO.getBaseVO().getMnfcDy()) && StringUtil.isNotEmpty(productVO.getBaseVO().getEftvDy())){
                    if (StringUtil.getLong(productVO.getBaseVO().getMnfcDy()) > StringUtil.getLong(productVO.getBaseVO().getEftvDy()))
                        throw new ProductException("제조일자는 유효일자보다 이후로 입력하실 수 없습니다.");
                }
            }
        }else {
            if(isBookCtgr)
                throw new ProductException("제조일자는 반드시 입력하셔야 합니다.");
            productVO.getBaseVO().setMnfcDy("");
            productVO.getBaseVO().setEftvDy("");
        }


        // 제조기간 체크
        if( StringUtil.isNotEmpty(StringUtil.nvl(productVO.getBaseVO().getMnfcDy()).replaceAll("/", "")) ) {
            checkDateType(StringUtil.nvl(productVO.getBaseVO().getMnfcDy()).replaceAll("/", ""), "제조/출시일자");
        }
        // 유효기간 체크
        if( StringUtil.isNotEmpty(StringUtil.nvl(productVO.getBaseVO().getEftvDy()).replaceAll("/", "")) ) {
            checkDateType(StringUtil.nvl(productVO.getBaseVO().getEftvDy()).replaceAll("/", ""), "유효일자");
        }
    }


    private void checkMnfcDy(String mnfcDy) throws ProductException{
        checkDateType(mnfcDy, "제조일자");
        if (StringUtil.getLong(mnfcDy) > StringUtil.getLong(DateUtil.getFormatString("yyyyMMdd")))
            throw new ProductException("제조일자는 오늘 이후로 입력하실 수 없습니다.");
    }

    private void checkEftvDy(String eftvDy) throws ProductException{
        checkDateType(eftvDy, "유효일자");
        if (StringUtil.getLong(DateUtil.getFormatString("yyyyMMdd")) > StringUtil.getLong(eftvDy))
            throw new ProductException("유효일자는 오늘보다 이전으로 입력하실 수 없습니다.");
    }

    public void checkDateType(String date, String prefixMsg) throws ProductException {
        if (StringUtil.getLong(date) <= 0 || !DateUtil.isValid(date, "yyyyMMdd")){
            throw new ProductException(prefixMsg + " 형식은 YYYY/MM/DD 로 입력하셔야 합니다.");
        }
    }

    /*
    public String checkAdultYn(MemberVO memInfo ,long memNo){
        try{
            if(memInfo != null){
                if(memInfo.isPrivateSeller()){//개인셀러
                    if("Y".equals(memInfo.getPrivateMemberVO().getAdltCrtfYN())) return "Y";
                    else return "N"; //미성년
                }else return "Y";//개인셀러가 아닐경우 성년/미성년 구분 불필요
            }else return "00";
        }catch(Exception e){
            return "00";
        }
    }
    */


    public void checkPrdNm(ProductVO productVO) throws ProductException{

        if("Y".equals(productVO.getBaseVO().getStdPrdYn()) && StringUtil.isEmpty(productVO.getBaseVO().getStdPrdNm())){
            throw new ProductException("검색상품명은 반드시 입력하셔야 합니다.");
        }
        if(StringUtil.isEmpty(productVO.getPrdNm())){
            throw new ProductException("상품명은 반드시 입력하셔야 합니다.");
        }


        ProductOptLimitVO productOptLimitVO = new ProductOptLimitVO();
        productOptLimitVO.setOptLmtObjNo(productVO.getDispCtgrNo());
        productOptLimitVO = productMapper.getProductLimitInfo(productOptLimitVO);

        boolean isPrdNmLmtSwitch = commonService.isUseValidApp(ProductConstDef.SWITCH_PRD_NM_LMT);
        long prdNmLenLmt = productOptLimitVO.getPrdNmLenLmt();
        long prdNmLenLmtDefault = productOptLimitVO.getPrdNmLenLmtDefault();
        boolean isExptPrdNm = productOptLimitVO.getPrdNmLenLmt() > 0;

        if( prdNmLenLmt <= 0 ) {
            prdNmLenLmt = prdNmLenLmtDefault;
        }

        if( isPrdNmLmtSwitch && productVO.isUpdate() && !isExptPrdNm ) {
            prdNmLenLmt = 100;
        }

        if( ProductConstDef.API_TYPE.equals(productVO.getChannel()) && !isExptPrdNm ) {
            prdNmLenLmt = ProductConstDef.PRD_NM_LMT_API;
        }

        // Max Length 체크
        if ("Y".equals(productVO.getBaseVO().getStdPrdYn())) {
            int prdNmBytes = 0;
            try {
                prdNmBytes = new String(productVO.getBaseVO().getStdPrdNm() + " " + productVO.getBaseVO().getMktPrdNm()).trim().getBytes("EUC-KR").length;
            } catch (UnsupportedEncodingException e) {
                prdNmLenLmt++;
            }
            if (prdNmBytes > prdNmLenLmt) {
                throw new ProductException(ProductExceptionEnumTypes.MAX_LEN_PRD_NM, String.valueOf(prdNmLenLmt));
            }
        } else {
            int prdNmBytes = 0;
            try {
                prdNmBytes = productVO.getPrdNm().getBytes("EUC-KR").length;
            } catch (UnsupportedEncodingException e) {
                prdNmLenLmt++;
            }
            if (prdNmBytes > prdNmLenLmt) {
                throw new ProductException(ProductExceptionEnumTypes.MAX_LEN_PRD_NM, String.valueOf(prdNmLenLmt));
            }
        }

        this.checkPrdContentInfo(productVO, productVO.getPrdNm());
    }

    private void checkPrdContentInfo(ProductVO productVO, String content) throws ProductException{


        // 금칙어 체크
        String tnsKeyword = productServiceImpl.getKeywordPrdFilter(productVO, productVO.getPrdNm());
        if (StringUtil.isNotEmpty(tnsKeyword)){
            throw new ProductException(ProductExceptionEnumTypes.TNS_PRD_NM, tnsKeyword);
        }
        tnsKeyword = "";
        // 셀러별 금칙어 체크
        String checkKeywd = commonService.getPropertyKeyValue("SELLER_TNS_PRD_KEYWD_" + productVO.getSelMnbdNo());
        if(!StringUtil.isEmpty(checkKeywd)){
            List<String> keywdList = Arrays.asList(checkKeywd.split("\\|"));
            for(String keywd : keywdList){
                if(productVO.getPrdNm().indexOf(keywd) > -1){
                    tnsKeyword = tnsKeyword +","+ keywd;
                }
            }
            if(tnsKeyword.length() > 1){
                tnsKeyword = tnsKeyword.substring(1);
            }
            if(StringUtil.isNotEmpty(tnsKeyword)) throw new ProductException(ProductExceptionEnumTypes.TNS_PRD_NM, tnsKeyword);
        }
    }
    public void checkPrdAdvrtStmt(ProductVO productVO) throws ProductException{
        // Max Length 체크
        int maxLen = 40;
        if (productVO.getBaseVO().getAdvrtStmt().getBytes().length > maxLen){
            throw new ProductException("상품홍보문구는 "+maxLen+"바이트 이상 입력하실 수 없습니다.");
        }
    }

    public void checkPrdNmEng(ProductVO productVO) throws ProductException{

        ArrayList<String> PRD_NM_EXTEND_CTGR_LIST = new ArrayList<String>();
        PRD_NM_EXTEND_CTGR_LIST.add("1");
        PRD_NM_EXTEND_CTGR_LIST.add("502");
        PRD_NM_EXTEND_CTGR_LIST.add("806");
        PRD_NM_EXTEND_CTGR_LIST.add("1033");
        PRD_NM_EXTEND_CTGR_LIST.add("748");
        PRD_NM_EXTEND_CTGR_LIST.add("281");
        PRD_NM_EXTEND_CTGR_LIST.add("2514");
        PRD_NM_EXTEND_CTGR_LIST.add("14620");
        PRD_NM_EXTEND_CTGR_LIST.add("2967");
        PRD_NM_EXTEND_CTGR_LIST.add("10040");
        PRD_NM_EXTEND_CTGR_LIST.add("8286");
        PRD_NM_EXTEND_CTGR_LIST.add("14613");
        PRD_NM_EXTEND_CTGR_LIST.add("14614");
        PRD_NM_EXTEND_CTGR_LIST.add("127658");
        PRD_NM_EXTEND_CTGR_LIST.add("128635");

        // Max Length 체크
        int prdNmBytes = productVO.getBaseVO().getPrdNmEng().getBytes().length;
        if (PRD_NM_EXTEND_CTGR_LIST.indexOf(String.valueOf(productVO.getCategoryVO().getLctgrNo())) > -1) {
            if (prdNmBytes > 200){
                throw new ProductException(ProductExceptionEnumTypes.MAX_LEN_PRD_NM_ENG, "200");
            }
        } else {
            if (prdNmBytes > 100){
                throw new ProductException(ProductExceptionEnumTypes.MAX_LEN_PRD_NM_ENG, "100");
            }
        }
        this.checkPrdContentInfo(productVO, productVO.getBaseVO().getPrdNmEng());
    }


    public boolean checkSelPrdClfCd(ProductVO productVO) throws ProductException{

        String selMthdCd = productVO.getBaseVO().getSelMthdCd();
        String selPrdClfCd = productVO.getBaseVO().getSelPrdClfCd();
        String selTermDy = productVO.getBaseVO().getSelTermDy();

        String filter = null;
        switch(SelMthdCd.fromString(selMthdCd)) {
            case FIXED:
            case USED:
                filter = "1";
                break;
            case COBUY:
                filter = "3";
                break;
            case PLN:
                filter = "4";
                break;
            case TOWN:
                filter = "5";
                break;
        }
        if (StringUtil.isEmpty(filter)) {
            return false;
        }

        for(SelPrdClfCd elem : SelPrdClfCd.values()) {
            if (StringUtil.equals(filter, elem.getDtlsCd().substring(0,1)) &&
                StringUtil.equals(selPrdClfCd, elem.getDtlsCd()) &&
                StringUtil.equals(selTermDy, elem.getCdVal1())) {
                return true;
            }
        }

        return false;
    }

    public boolean isUsePrdGrpTypCd(String stdPrdYn, long selMnbdNo) throws ProductException {
        boolean result = false;
        if("Y".equals(stdPrdYn)){
            if (ProductConstDef.PRD_GRP_USE_PRD_GRP_TYP_CD_SELMNBDNO.contains(selMnbdNo)) {
                return true;
            }

            try{
                //MbRoadShopVO mbRoadShopVO = sellerService.getMbRoadShopApplyInfo(selMnbdNo);
                //result = mbRoadShopVO != null && ((EnbrRqstStatCds.STANDBY.getDtlsCd().equals(mbRoadShopVO.getEnbrRqstStatCd()) || EnbrRqstStatCds.APPROVAL.getDtlsCd().equals(mbRoadShopVO.getEnbrRqstStatCd())));
                result = customerServiceImpl.isRoadShoSeller(selMnbdNo);
            }catch(Exception e){
                throw new ProductException(e);
            }
        }
        return result;
    }

    public void checkDispPlayYn(ProductVO productVO) throws ProductException{
        if(!StringUtil.isContains(ProductConstDef.DEFAULT_VALUE_Y_N, productVO.getBaseVO().getDisplayYn())){
            throw new ProductException("전시여부가 유효하지 않는 값 입니다. 'Y' 또는 'N'으로 입력해 주시기 바랍니다.");
        }
    }

    public boolean isUsePrdGrpTypCd(ProductVO productVO) throws ProductException{
        //if("Y".equals(productVO.getBaseVO().getStdPrdYn()) && sellerValidate.isUsePrdGrpTypSeller(productVO.getSelMnbdNo())){
        if("Y".equals(productVO.getBaseVO().getStdPrdYn()) && customerServiceImpl.isRoadShoSeller(productVO.getSelMnbdNo())){
            return true;
        }else{
            return false;
        }
    }

    public void checkProductTagInfo(ProductVO productVO) throws ProductException{
        if("Y".equals(productVO.getBaseVO().getProductTagUseYn())){
            if(this.isUsePrdGrpTypCd(productVO)){

                if(productVO.getProductTagVOList() != null && productVO.getProductTagVOList().size() > 0){
                    if(productVO.getProductTagVOList().size() > 10) throw new ProductException("태그는 10개를 초과 할 수 없습니다.");
                    int tagLength = 0;
                    for(ProductTagVO productTagVO : productVO.getProductTagVOList()){
                        try {
                            tagLength = productTagVO.getTagNm().getBytes("EUC-KR").length;
                        } catch (UnsupportedEncodingException e) {
                            tagLength = 21;
                        }
                        if(tagLength > 20){
                            throw new ProductException("태그는 20Byte 이하로 등록이 가능합니다.");
                        }
                    }
                }
            }
        }
    }
}