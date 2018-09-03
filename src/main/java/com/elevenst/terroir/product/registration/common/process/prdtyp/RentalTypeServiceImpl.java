package com.elevenst.terroir.product.registration.common.process.prdtyp;

import com.elevenst.common.properties.PropMgr;
import com.elevenst.terroir.product.registration.category.mapper.CategoryMapper;
import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import com.elevenst.terroir.product.registration.common.vine.service.VineCallServiceImpl;
import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.option.code.OptionConstDef;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.entity.PdRntlPrd;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.mapper.RentalMapper;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.product.vo.RentalVO;
import com.elevenst.terroir.product.registration.seller.service.SellerServiceImpl;
import com.elevenst.terroir.product.registration.seller.vo.SellerAuthVO;
import org.springframework.beans.factory.annotation.Autowired;

public class RentalTypeServiceImpl implements ProductTypeService{

    @Autowired
    PropMgr propMgr;

    @Autowired
    SellerServiceImpl sellerService;

    @Autowired
    RentalMapper rentalMapper;

    @Autowired
    VineCallServiceImpl vineCallService;

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException {
        this.checkRentalCtgrChange(preProductVO, productVO);
    }

    @Override
    public void setProductTypeInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException {
        this.setRentalTypeInfo(productVO);
        this.checkRentalType(productVO);
        this.checkRentalSellerCash(productVO.getMemberVO());
        this.checkRentalCtgrChange(preProductVO, productVO);
        this.setRentalVO(productVO);
    }

    @Override
    public void insertProductTypeProcess(ProductVO productVO) throws ProductException {
        if(OptionConstDef.OPT_TYP_CD_RENT.equals(productVO.getOptionVO().getOptTypCd())){
            this.insertRntlPrd(productVO);
        }
    }

    @Override
    public void updateProductTypeProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException {
        if(OptionConstDef.OPT_TYP_CD_RENT.equals(productVO.getOptionVO().getOptTypCd())){
            this.updateRntlPrd(productVO);
        }
    }

    private void setRentalTypeInfo(ProductVO productVO) throws ProductException{
        productVO.getBaseVO().setBcktExYn("Y");
    }

    private void checkRentalType(ProductVO productVO) throws ProductException{
        this.checkOnlyFreeDlv(productVO);
    }

    private void checkOnlyFreeDlv(ProductVO productVO) throws ProductException{
        if("01".equals(productVO.getBaseVO().getDlvCstInstBasiCd())){
            throw new ProductException(TypeExceptionEnumTypes.RENTAL_ONLY_FREE_DLV);
        }
    }

    /**
     * 렌탈상품의 경우 특정금액(ProductValidate.RENTAL_SELLER_CASH_MIN 30만원) 이상 보유 체크
     *
     */
    private void checkRentalSellerCash(MemberVO memberVO) throws ProductException {
        //렌탈캐쉬 예외 셀러
        if(propMgr.get1hourTimeProperty("RENTAL_SELL_STOP_EX_SELLER").indexOf(("|" + memberVO.getMemID() + "|")) > -1){
            return;
        }

        if(ProductConstDef.RENTAL_SELLER_CASH_MIN > vineCallService.getSellerCash(memberVO.getMemNO())){
            throw new ProductException(TypeExceptionEnumTypes.RENTAL_NOT_ENOUGH_CASH);
        }
    }


    private void setRentalVO(ProductVO productVO) {
        /*렌탈카테고리 대상 셀러 조회*/
        boolean isAuthTypeSpecialRentalSeller = this.isAuthTypeSpecialRentalSeller(productVO.getSelMnbdNo());

        if (isAuthTypeSpecialRentalSeller && OptionConstDef.OPT_TYP_CD_RENT.equals(productVO.getOptionVO().getOptTypCd())) {

            //스페셜 렌탈 상품 정보 세팅
            if (productVO.getRentalVO().getFreeInstYn()!= null) {
                productVO.getRentalVO().setFreeInstYn("Y");
            } else {
                productVO.getRentalVO().setFreeInstYn("N");
            }

            if (productVO.getRentalVO().getUseSpecialRentalYn() != null) {
                productVO.getRentalVO().setUseSpecialRentalYn("Y");
            } else {
                productVO.getRentalVO().setUseSpecialRentalYn("N");
            }

        }
    }


    private boolean isAuthTypeSpecialRentalSeller (long selMnbdNo) throws ProductException{
        if(selMnbdNo == 0) return false;
        SellerAuthVO sellerAuthVO = new SellerAuthVO();


        sellerAuthVO.setSelMnbdNo(selMnbdNo);
        sellerAuthVO.setAuthObjNo(String.valueOf(selMnbdNo));
        sellerAuthVO.setAuthTypCd(ProductConstDef.AUTH_TYP_CD_SPECIAL_RENTAL);
        sellerAuthVO.setObjClfCd("10");
        sellerAuthVO.setUseYn("Y");
        sellerAuthVO = sellerService.getSellerAuthInfo(sellerAuthVO);

        if (sellerAuthVO == null) {
            return false;
        }else{
            return true;
        }

    }


    private void insertRntlPrd(ProductVO productVO) throws ProductException {
        try {
            PdRntlPrd pdRntlPrd = this.setPdRntlPrd(productVO);
            rentalMapper.insertRntlPrd(pdRntlPrd);
            rentalMapper.insertRntlPrdHist(pdRntlPrd.getPrdNo());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ProductException("스페셜 렌탈 상품정보 입력 오류"+ex.getMessage(), ex);
        }
    }



    private PdRntlPrd setPdRntlPrd (ProductVO productVO){
        PdRntlPrd pdRntlPrd = new PdRntlPrd();
        RentalVO rentalVO = productVO.getRentalVO();
        pdRntlPrd.setFreeInstYn(rentalVO.getFreeInstYn());
        pdRntlPrd.setCstTermCd(rentalVO.getCstTermCd());
        pdRntlPrd.setCoCardBnft(rentalVO.getCoCardBnft());
        pdRntlPrd.setInstCstDesc(rentalVO.getInstCstDesc());
        pdRntlPrd.setFrgftDesc(rentalVO.getFrgftDesc());
        pdRntlPrd.setEtcInfo(rentalVO.getEtcInfo());
        pdRntlPrd.setPrdNo(productVO.getPrdNo());
        pdRntlPrd.setUseYn(rentalVO.getUseSpecialRentalYn());
        pdRntlPrd.setCreateNo(productVO.getCreateNo());
        pdRntlPrd.setUpdateNo(productVO.getUpdateNo());

        return pdRntlPrd;
    }

    private void updateRntlPrd(ProductVO productVO) throws ProductException {
        try {
            PdRntlPrd pdRntlPrd = this.setPdRntlPrd(productVO);
            rentalMapper.updateRntlPrd(pdRntlPrd);
            rentalMapper.insertRntlPrdHist(pdRntlPrd.getPrdNo());
        } catch (Exception ex) {

            throw new ProductException("스페셜 렌탈 상품정보 수정 오류", ex);
        }
    }

    private void checkRentalCtgrChange(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if(productVO.isUpdate()){
            this.validateRentalServiceCategoryPolicy(preProductVO.getCategoryVO(), productVO.getCategoryVO());
        }else{
            this.validateRentalServiceCategoryPolicy(productVO.getCategoryVO(), productVO.getCategoryVO());
        }
    }

    private void validateRentalServiceCategoryPolicy(CategoryVO oldCategoryVO, CategoryVO newCateoryVO) throws ProductException {
        if (isRentalServiceCategory(oldCategoryVO) && !isRentalServiceCategory(newCateoryVO)) {
            throw new ProductException("렌탈상품 카테고리는 일반 카테고리로 변경할 수 없습니다.");
        }

        if (!isRentalServiceCategory(oldCategoryVO) && isRentalServiceCategory(newCateoryVO)) {
            boolean existProduct = categoryMapper.existProductInCategory(oldCategoryVO.getDispCtgrNo());
            if(existProduct) {
                throw new ProductException("렌탈상품 카테고리로 변경이 불가합니다.\n"
                                               + "등록된 상품이 없는 경우만 렌탈상품 카테고리로 변경 가능하며,\n"
                                               + "변경 후에는 1원 렌탈상품만 등록 가능합니다.\n"
                                               + "다시 한번 확인해주세요.");
            }
        }
    }

    private boolean isRentalServiceCategory(CategoryVO categoryVO) {
        if(categoryVO == null) return false;

        return ProductConstDef.PRD_TYP_CD_SVC_CTGR.equals(categoryVO.getPrdTypCd())
            && ProductConstDef.SVC_CTGR_TYP_CD_RENTAL.equals(categoryVO.getSvcCtgrTypCd());
    }

    @Deprecated
    private void updateRntlCstUnt(ProductVO productVO) throws ProductException {
        try {
            PdRntlPrd pdRntlPrd = this.setPdRntlPrd(productVO);
            rentalMapper.updateRntlCstUnt(pdRntlPrd);
            rentalMapper.insertRntlPrdHist(pdRntlPrd.getPrdNo());
        } catch(Exception ex) {
            throw new ProductException("렌탈 요금 단위 업데이트 오류", ex);
        }
    }
}
