package com.elevenst.terroir.product.registration.seller.validate;

import com.elevenst.common.properties.PropMgr;
import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.terroir.product.registration.common.code.BsnDealClf;
import com.elevenst.terroir.product.registration.customer.service.CustomerServiceImpl;
import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.delivery.code.DlvClfCdTypes;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.code.SelMthdCd;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.code.PtnrPrdClfCdSeller;
import com.elevenst.terroir.product.registration.seller.mapper.SellerMapper;
import com.elevenst.terroir.product.registration.seller.service.SellerServiceImpl;
import com.elevenst.terroir.product.registration.seller.vo.MbRoadShopVO;
import com.elevenst.terroir.product.registration.seller.vo.SellerAuthVO;
import com.elevenst.terroir.product.registration.seller.vo.SellerServiceLmtVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import skt.tmall.auth.Auth;

import java.sql.SQLException;

@Component
public class SellerValidate {

    private enum SellerExceptionEnumTypes implements ExceptionEnumTypes {
        NICKNAME_NULL("SELLER", "SELLER001", "닉네임은 반드시 선택하셔야 합니다.")
        ,NICKNAME_REFUSED("SELLER", "SELLER002", "셀러의 닉네임 선택이 올바르지 않습니다.")
        ,OTHER_SELLER("PRD_REG", "SELLER003", "다른셀러가 등록한 상품을 수정요청 하셨습니다.")
        ,LIMIT_REG("PRD_REG", "SELLER004", "상품등록실패 : 상품 등록은 1일에 {$1}개 까지만 가능합니다.")
        ,GLOBAL_SELLER("PRD_REG", "SELLER005", "해외구매대행상품이 올바르지 않습니다. 글로벌 국내 개인 셀러 또는 글로벌 국내 사업자 셀러만 등록가능합니다.")
        ,HAS_NOT_SELLER("PRD_REG", "SELLER006", "공급업체는 반드시 선택하셔야 합니다.")
        ,ONLY_SEL_USED("PRD_REG", "SELLER007", "신용인증셀러는 중고상품 외 등록 불가합니다.")
        ,ONLY_SEL_FIXED("PRD_REG", "SELLER008", "셀러위탁배송 상품은 고정가판매 외 등록 불가합니다.")
        ;

        private final String msgGroupId;
        private final String code;
        private final String message;

        SellerExceptionEnumTypes(String msgGroupId, String code, String message){
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
    PropMgr propMgr;

    @Autowired
    private SellerMapper mapper;

    @Autowired
    SellerServiceImpl sellerService;

    @Autowired
    CustomerServiceImpl customerService;

    public void checkSelMnbdNo(ProductVO productVO) throws ProductException{
        if(productVO.getSelMnbdNo() < 1){
            throw new ProductException(SellerExceptionEnumTypes.HAS_NOT_SELLER);
        }
        if(productVO.getSellerVO().getSelMnbdNckNmSeq() < 1){
            throw new ProductException(SellerExceptionEnumTypes.NICKNAME_NULL);
        }
    }

    public void checkSelMnbdNckNmSeq(long checkSeq, long requestSeq){
        if(checkSeq != requestSeq){
            throw new ProductException(SellerExceptionEnumTypes.NICKNAME_REFUSED);
        }
    }

    public void checkSellerProductInfo(ProductVO productVO) throws ProductException{
        //TODO  CYB auth 체크 후 자기 상품이 맞는지 체크
        if(false){
            throw new ProductException(SellerExceptionEnumTypes.OTHER_SELLER);
        }
    }

    public void checkProductRegLimit(ProductVO productVO) throws ProductException{
        if(this.isProductRegLimitCheckTarget(productVO)){
            SellerServiceLmtVO sellerServiceLmtVO = new SellerServiceLmtVO();
            sellerServiceLmtVO.setMemNo(productVO.getSelMnbdNo());
            sellerServiceLmtVO.setService("01");
            sellerServiceLmtVO = mapper.getSellerServiceLmtInfo(sellerServiceLmtVO);
            if(sellerServiceLmtVO != null
            && sellerServiceLmtVO.getLmtCnt() <= sellerServiceLmtVO.getAccessCnt()){
                throw new ProductException(SellerExceptionEnumTypes.LIMIT_REG, String.valueOf(sellerServiceLmtVO.getLmtCnt()));
            }
        }
    }

    private boolean isProductRegLimitCheckTarget(ProductVO productVO) throws ProductException{
        if(("N".equals(productVO.getIsOpenApi()) || "Y".equals(productVO.getBulkProductYn()))
        && !productVO.isUpdate()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Yes24 직매입 권한 체크
     * @param bsnDealClf
     * @param selMnbdNo
     * @return boolean
     */
    public boolean chkYes24DirectAuth(String bsnDealClf, String selMnbdNo){
        return (ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(bsnDealClf) && (propMgr.get1hourTimeProperty("Yes24BookAuth").indexOf(("|"+selMnbdNo+"|")) > -1));
    }

    /**
     * Yes24 직매입 권한 체크
     * @param selMnbdNo
     * @return boolean
     */
    public boolean chkYes24DirectAuth(long selMnbdNo){
        return propMgr.get1hourTimeProperty("Yes24BookAuth").indexOf(("|" + selMnbdNo + "|")) > -1;
    }

    /**
     * So용 Yes24 권한체크
     * @param selMnbdNo
     * @return boolean
     */
    public boolean checkSoYes24DirectAuth(long selMnbdNo ){
        return propMgr.get1hourTimeProperty("SoYes24BookAuth").indexOf(("|"+selMnbdNo+"|")) > -1;

    }

    public boolean checkSelMnbdNckNmSeq(SellerAuthVO sellerAuthVO) {

        long selMnbdNo = sellerAuthVO.getSelMnbdNo();
        String bsnDealClf = sellerAuthVO.getBsnDealClf();
        long selMnbdNckNmSeq = sellerAuthVO.getSelMnbdNckNmSeq();

        boolean isSellerBaseNickName = sellerService.isSellerBaseNickname(sellerAuthVO);

        if (GroupCodeUtil.equalsDtlsCd(bsnDealClf, BsnDealClf.COMMISSION) && !isSellerBaseNickName) {
               throw new ProductException(SellerExceptionEnumTypes.NICKNAME_REFUSED);
        }

        return true;

    }

    /**
     * 상품구성(PD_PRD.SET_TYP_CD) 설정 가능한 셀러
     * @param memberVO
     * @return
     * @throws ProductException
     */
    public boolean isOnlyUsableSetTypCd(MemberVO memberVO) throws ProductException{
        return ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(memberVO.getBsnDealClf()) && "03".equals(memberVO.getWmsUseClfCd());
    }

    /**
     * 자사ID 체크
     * return true시 자사ID / return false시 자사ID 아님
     * input : 회원 번호
     */
    public boolean checkMobileSeller(long memNo) throws ProductException {
        String retVal = getMobileSellers();

        String memId = "|"+ sellerService.getSellerId(memNo)+"|";
        try {
            return retVal.indexOf(memId) >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 자사ID 체크
     *input : 회원 ID
     */
    public boolean checkMobileSeller(String memId) throws ProductException {
        String retVal = getMobileSellers();
        memId = "|"+memId+"|";
        try {
            return retVal.indexOf(memId) >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    //DB 값 불러오기
    public String getMobileSellers() throws ProductException {
        return propMgr.get1hourTimeProperty("SKT_MOBILE_ID");
    }


    /**
     * 묶음상품 등록 가능 셀러
     * @param memNo
     * @return
     * @throws ProductException
     */
    @Deprecated
    public boolean isDirectBuyingMark(long memNo) throws ProductException {
        try {
            MemberVO memberVO = new MemberVO();
            memberVO.setMemNO(memNo);

            return isDirectBuyingMark(customerService.getMemberInfo(memberVO));
        } catch (Exception sqle) {
            throw new ProductException("회원 정보조회 오류");

        }
    }

    /**
     * 묶음상품 등록 가능 셀러
     * @param memberVO
     * @return
     */
    public boolean isDirectBuyingMark(MemberVO memberVO){
        return ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(memberVO.getBsnDealClf())
                && (memberVO.isEtprsSeller())
                && "02".equals(memberVO.getSuplCmpClfCd());
    }

    /**
     * 지정배송일 인증셀러 여부 확인
     * @param selMnbdNo
     * @return
     */
    public boolean isCertStockCdSeller(long selMnbdNo) {
        boolean isCertSeller = false;
        if(selMnbdNo <= 0) return isCertSeller;

        for(PtnrPrdClfCdSeller elem : PtnrPrdClfCdSeller.values()) {
            if(StringUtil.equals(String.valueOf(selMnbdNo) , elem.getDtlsComNm())) {
                return true;
            }
        }
        return isCertSeller;
    }


    public void checkGlobalProxySeller(ProductVO productVO) throws ProductException{
        boolean isNotGlobalSeller = true; // 일반판매자

        isNotGlobalSeller = ("01".equals(productVO.getMemberVO().getFrgnClf())) ? false : true;

        if(productVO.getBaseVO().getForAbrdBuyClf() == null || "".equals(productVO.getBaseVO().getForAbrdBuyClf())) {
            productVO.getBaseVO().setForAbrdBuyClf(ProductConstDef.PRD_GLOBAL_PROXY_CD_LOCAL); //일반상품 (01)
        }

        if(isNotGlobalSeller && ProductConstDef.PRD_GLOBAL_PROXY_CD_GLB.equals(productVO.getBaseVO().getForAbrdBuyClf())){
            throw new ProductException(SellerExceptionEnumTypes.GLOBAL_SELLER);
        }
    }

    public void checkSelMthdCd(ProductVO productVO) throws ProductException{
        //신용인증셀러여부 (중고상품판매)
        if("Y".equals(productVO.getMemberVO().getCertCardYn())
            && !GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelMthdCd(), SelMthdCd.USED)) {
            throw new ProductException(SellerExceptionEnumTypes.ONLY_SEL_USED);
        }
        //셀러위탁은 고정가만 가능
        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getDlvClf(), DlvClfCdTypes.SELLER_CONSIGNMENT_DELIVERY)
            && !GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelMthdCd(), SelMthdCd.FIXED)) {
            throw new ProductException(SellerExceptionEnumTypes.ONLY_SEL_FIXED);
        }
    }

    @Deprecated
    public boolean isUsePrdGrpTypSeller(long selMnbdNo) throws ProductException{
        boolean result = false;

        if (ProductConstDef.PRD_GRP_USE_PRD_GRP_TYP_CD_SELMNBDNO.contains(selMnbdNo)) {
            return true;
        }

        try{
            MbRoadShopVO mbRoadShopVO = mapper.getMbRoadShopApplyInfo(selMnbdNo);

            result = mbRoadShopVO != null && mbRoadShopVO.isApproval();

        }catch(Exception e){
            throw new ProductException("로드샵 셀러 조회시 오류",e);
        }

        return result;
    }

    public boolean isSupportMobileEditSeller(Auth auth) throws ProductException{

        if("Y".equals(auth.getCertCardYN())){
            return false;
        }
        MemberVO memberVO = new MemberVO();
        memberVO.setMemNO(auth.getMemberNumber());
        memberVO = customerService.getMemberInfo(memberVO);
        if("03".equals(memberVO.getMemTypCD())){
            return false;
        }

        return true;
    }
}
