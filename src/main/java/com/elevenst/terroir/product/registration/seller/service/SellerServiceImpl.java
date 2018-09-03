package com.elevenst.terroir.product.registration.seller.service;

import com.elevenst.common.process.RegistrationInfoConverter;
import com.elevenst.common.properties.PropMgr;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.common.vine.service.VineCallServiceImpl;
import com.elevenst.terroir.product.registration.customer.mapper.CustomerMapper;
import com.elevenst.terroir.product.registration.delivery.vo.MbMemVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.entity.PdSellerServiceLmtLog;
import com.elevenst.terroir.product.registration.seller.mapper.SellerMapper;
import com.elevenst.terroir.product.registration.seller.validate.SellerValidate;
import com.elevenst.terroir.product.registration.seller.vo.MbCnsgnDstSellerVO;
import com.elevenst.terroir.product.registration.seller.vo.MbRoadShopVO;
import com.elevenst.terroir.product.registration.seller.vo.MbSellerAddSetVO;
import com.elevenst.terroir.product.registration.seller.vo.MbSellerOptSetDetailVO;
import com.elevenst.terroir.product.registration.seller.vo.SellerAuthVO;
import com.elevenst.terroir.product.registration.seller.vo.SellerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class SellerServiceImpl implements RegistrationInfoConverter<ProductVO>{

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    SellerMapper sellerMapper;

    @Autowired
    SellerValidate sellerValidate;

    @Autowired
    VineCallServiceImpl vineCallService;

    @Autowired
    private PropMgr propMgr;

    public void setSellerInfoProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        this.checkSellerInfo(productVO);
    }

    public void insertSellerInfo(ProductVO productVO) throws ProductException{
        sellerMapper.mergePdSellerServiceLmtLog(this.setPdSellerServiceLmtLog(productVO));
    }


    private void checkSellerInfo(ProductVO productVO) throws ProductException{
        sellerValidate.checkProductRegLimit(productVO);
        sellerValidate.checkGlobalProxySeller(productVO);
    }

    private PdSellerServiceLmtLog setPdSellerServiceLmtLog(ProductVO productVO) throws ProductException{
        PdSellerServiceLmtLog pdSellerServiceLmtLog = new PdSellerServiceLmtLog();
        pdSellerServiceLmtLog.setMemNo(productVO.getSelMnbdNo());
        //TODO  CYB 데이터 추가 셋팅 할것
        return pdSellerServiceLmtLog;
    }

    /**
     * 해외 셀러의 해외 통합아이디 여부 재사용하기 위해 체크 후 Return
     * @param userNo
     * @return long
     * @throws ProductException
     */
    public long getGlbItgIdsBySeller(long userNo) throws ProductException {
        long cnt = 0;
        if( userNo <= 0 ) {
            return 0;
        }

        try {
            cnt = customerMapper.getGlbItgIdsBySeller(userNo);
        } catch(Exception e) {
            throw new ProductException("해외 셀러의 해외 통합아이디 여부 재사용하기 위해 체크 후 Return 오류", e);
        }

        return cnt;
    }

    public SellerAuthVO getSellerAuthInfo(SellerAuthVO sellerAuthVO) throws ProductException {
        try {
            return sellerMapper.getSellerAuthInfo(sellerAuthVO);
        } catch (Exception ex) {
            throw new ProductException("판매자 권한 정보 조회 오류", ex);
        }
    }

    public List<SellerAuthVO> getSellerAuthList(SellerAuthVO sellerAuthVO) throws ProductException {
        try {
            return sellerMapper.getSellerAuthList(sellerAuthVO);
        } catch (Exception ex) {
            throw new ProductException("판매자 권한 정보 조회 오류", ex);
        }
    }

    public boolean isBarcodeDuplicateCheckSeller(ProductVO productVO) {
        boolean isCheckObj = false;

        try {
            isCheckObj = ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(productVO.getBaseVO().getDlvClf()) || productVO.getSellerVO().isSellableBundlePrdSeller();

            if(!isCheckObj){
                SellerAuthVO sellerAuthVO = new SellerAuthVO();
                sellerAuthVO.setSelMnbdNo(Long.valueOf(productVO.getSelMnbdNo()));
                sellerAuthVO.setAuthTypCd(ProductConstDef.AUTH_TYP_CD_BARCODE_DUP_CHK);
                sellerAuthVO.setAuthObjNo(String.valueOf(productVO.getSelMnbdNo()));
                sellerAuthVO.setObjClfCd(ProductConstDef.OBJ_CLF_CD_BARCODE_DUP_CHK_SELLER);
                sellerAuthVO.setUseYn("Y");
                sellerAuthVO = getSellerAuthInfo(sellerAuthVO);

                isCheckObj = (sellerAuthVO == null ? false : true);
            }
        } catch (Exception ex) {
            log.error("[" + this.getClass().getName() + ".isDuplicateBarcode] MSG : "+ ex.getMessage(), ex);
        }

        return isCheckObj;
    }

    public boolean isPdSellerAuth(SellerAuthVO param) {
        SellerAuthVO sellerAuthVO = new SellerAuthVO();
        sellerAuthVO.setSelMnbdNo(Long.valueOf(param.getSelMnbdNo()));
        sellerAuthVO.setAuthTypCd(param.getAuthTypCd());
        sellerAuthVO.setAuthObjNo(String.valueOf(param.getSelMnbdNo()));
        sellerAuthVO.setObjClfCd(param.getObjClfCd());
        sellerAuthVO.setUseYn("Y");
        sellerAuthVO = getSellerAuthInfo(sellerAuthVO);
        return sellerAuthVO != null && StringUtil.isNotEmpty(sellerAuthVO.getAuthObjNo()) ? true : false;
    }

    public boolean getPdSellerAuth(ProductVO productVO, String authTypCd, String objClfCd) {
        SellerAuthVO sellerAuthVO = new SellerAuthVO();
        sellerAuthVO.setSelMnbdNo(Long.valueOf(productVO.getSelMnbdNo()));
        sellerAuthVO.setAuthTypCd(authTypCd);
        sellerAuthVO.setAuthObjNo(String.valueOf(productVO.getSelMnbdNo()));
        sellerAuthVO.setObjClfCd(objClfCd);
        sellerAuthVO.setUseYn("Y");
        return this.isPdSellerAuth(sellerAuthVO);
    }

    public List<SellerAuthVO> getPdSellerAuth(ProductVO productVO, String authTypCd) {
        SellerAuthVO sellerAuthVO = new SellerAuthVO();
        sellerAuthVO.setSelMnbdNo(Long.valueOf(productVO.getSelMnbdNo()));
        sellerAuthVO.setAuthTypCd(authTypCd);
        sellerAuthVO.setUseYn("Y");
        return getSellerAuthList(sellerAuthVO);
    }

    /**
     * 상품 판매자 정보 조회.
     * @author leegt80
     * @param prdNo
     * @return ProductBO
     * @throws ProductException
     */
    public ProductVO getProductSellerInfo(long prdNo) throws ProductException {
        ProductVO productVO = null;
        try {
            productVO = sellerMapper.getProductSellerInfo(prdNo);
        } catch (ProductException ex) {
            throw new ProductException("상품 판매자 정보 조회 오류", ex);
        } catch (Exception ex) {
            throw new ProductException("상품 판매자 정보 조회 오류", ex);
        }

        return productVO;
    }

    /**
     *  셀러 기본닉네임 조회
     * */
    public boolean isSellerBaseNickname(SellerAuthVO sellerAuthVO) {

        if (sellerAuthVO.getSelMnbdNo() <= 0 || sellerAuthVO.getSelMnbdNckNmSeq() <= 0) {
            return false;
        }
        int resultCount = sellerMapper.getBaseSelMnbdNck(sellerAuthVO);

        return resultCount > 0 ? true : false;
    }

    /**
     * MO쿠폰제외셀러 여부 확인
     * @param sellerNo
     * @return
     * @throws ProductException
     */
    public boolean isCupnExcldSeller(long sellerNo){
        boolean isExcldSeller  = false;

         // MO쿠폰 제외 설정 정보가 있을 경우 true
         if (sellerMapper.isCupnExcldSeller(sellerNo) > 0){
             isExcldSeller = true;
         }

        return isExcldSeller;
    }

    public String getSellerId(long memNo) {
        return sellerMapper.getSellerId(memNo);
    }

    public long getSellerCash(ProductVO productVO){
        //TODO CYB 나중에 재호출 안하게 셋팅할것 (그래서 ProductVO 를 인자로 받기.)
        return vineCallService.getSellerCash(productVO.getSelMnbdNo());
    }

    public boolean isMobileSeller(ProductVO productVO) throws ProductException {
        String retVal = sellerValidate.getMobileSellers();
        String memId = productVO.getMemberVO().getMemID();

        if ("".equals(memId)) {
            memId = vineCallService.getMemberDefaultInfo(productVO.getSelMnbdNo()).getMemID();
            productVO.getMemberVO().setMemID(memId);
        }
        memId = "|" + memId + "|";
        return retVal.indexOf(memId) >= 0;
    }

    // 이미지 예외 셀러인지
    public boolean isImgExSeller(long userNo){
        /*
        List<String> imgExSellers = Arrays.asList(StringUtil.nvl(propMgr.get1hourTimeProperty("PRODUCT_IMG_WHITEBG_EX_SELLER"), "").split(","));
        return imgExSellers.contains(String.valueOf(userNo));
        */
        return propMgr.get1hourTimeProperty("PRODUCT_IMG_WHITEBG_EX_SELLER").indexOf(String.valueOf(userNo)) > -1;
    }

    // 이미지 사이즈 자유로운 셀러인지
    public boolean isFreeImgSeller(long userNo){
        /*
        List<String> freeImgSellers = Arrays.asList(StringUtil.nvl(propMgr.get1hourTimeProperty("PRD_DTL_IMG_SKIP_SELLER"), "").split(","));
        return freeImgSellers.contains(String.valueOf(userNo));
        */

        return propMgr.get1hourTimeProperty("PRD_DTL_IMG_SKIP_SELLER").indexOf(String.valueOf(userNo)) > -1;
    }

    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) {
        this.convertSellerInfo(preProductVO, productVO);
    }

    private void convertSellerInfo(ProductVO preProductVO, ProductVO productVO) {
        if(productVO.getSellerVO() == null) productVO.setSellerVO(new SellerVO());
        productVO.getSellerVO().setSellableBundlePrdSeller(sellerValidate.isDirectBuyingMark(productVO.getMemberVO()));
        productVO.getSellerVO().setOnlyUsableSetTypCd(sellerValidate.isOnlyUsableSetTypCd(productVO.getMemberVO()));
        sellerValidate.checkSelMnbdNo(productVO);
        sellerValidate.checkSelMnbdNckNmSeq(vineCallService.getSellerNickName(productVO), productVO.getSellerVO().getSelMnbdNckNmSeq());
        sellerValidate.checkSelMthdCd(productVO);
    }

    /**
     * 셀러타입 확인
     * @param memNo
     * @return
     * @throws ProductException
     */
    public String getSellerTypCd(long memNo) throws ProductException {
        try {
            return sellerMapper.getSellerTypCd(memNo);
        } catch(Exception e) {
            throw new ProductException("셀러타입 확인 오류", e);
        }
    }

    /**
     * 셀러위탁 물류 정보 조회
     * @param memNo
     * @return
     * @throws ProductException
     */
    public MbCnsgnDstSellerVO getConsignmentDstSellerInfo(long memNo) throws ProductException {
        return sellerMapper.getConsignmentDstSellerInfo(memNo);
    }

    /**
     * 카테고리 수정 권한을 가지고 있는지 여부 값 반환.
     *
     * @param memNo 판매자 번호.
     * @return 수정 권한을 가지고 있으면 true.
     */
    public boolean hasCategoryModificationAuth(long memNo) throws ProductException {
        boolean isMbSellerAddSet = false;
        MbSellerAddSetVO resultVO = sellerMapper.getMbSellerAddSetInfo(memNo);
        if(resultVO != null && resultVO.getCtgrChngAuthYn() != null && "Y".equals(resultVO.getCtgrChngAuthYn())) {
            isMbSellerAddSet = true;
        }
        return isMbSellerAddSet;
    }

    public MbSellerOptSetDetailVO getMbSellerOptSetDetail(MbSellerOptSetDetailVO mbSellerOptSetDetailVO) throws ProductException {
        return sellerMapper.getMbSellerOptSetDetail(mbSellerOptSetDetailVO);
    }

    /**
     * 로드샵 입점신청서 조회
     */
    public MbRoadShopVO getMbRoadShopApplyInfo(long memNo) throws ProductException {
        return sellerMapper.getMbRoadShopApplyInfo(memNo);
    }

    /**
     * 회원 교환/반품 배송비 및 초기배송비 부과방법 정보를 가져온다. 추후 VINE으로 전환필요
     * @param memNo
     * @return
     * @throws ProductException
     */
    public MbMemVO getSellerRtnChngDlvInfo(long memNo) throws ProductException {
        return sellerMapper.getSellerRtnChngDlvInfo(memNo);
    }

}
