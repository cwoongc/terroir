package com.elevenst.terroir.product.registration.product.service;

import com.elevenst.common.process.RegistrationInfoConverter;
import com.elevenst.common.properties.PropMgr;
import com.elevenst.common.util.DateUtil;
import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.common.util.StripHTMLTag;
import com.elevenst.common.util.cache.ObjectCacheImpl;
import com.elevenst.terroir.product.registration.benefit.vo.ProductGiftVO;
import com.elevenst.terroir.product.registration.category.service.CategoryServiceImpl;
import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import com.elevenst.terroir.product.registration.category.vo.DpGnrlDispVO;
import com.elevenst.terroir.product.registration.common.code.BsnDealClf;
import com.elevenst.terroir.product.registration.common.service.CommonServiceImpl;
import com.elevenst.terroir.product.registration.customer.service.CustomerServiceImpl;
import com.elevenst.terroir.product.registration.delivery.code.DlvClfCdTypes;
import com.elevenst.terroir.product.registration.delivery.service.DeliveryServiceImpl;
import com.elevenst.terroir.product.registration.option.mapper.OptionMapper;
import com.elevenst.terroir.product.registration.option.vo.ProductOptLimitVO;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.code.RmaterialTypCd;
import com.elevenst.terroir.product.registration.product.code.SelMthdCd;
import com.elevenst.terroir.product.registration.product.code.SetTypCd;
import com.elevenst.terroir.product.registration.product.entity.*;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.mapper.ProductMapper;
import com.elevenst.terroir.product.registration.product.mapper.ProductVerticalMapper;
import com.elevenst.terroir.product.registration.product.validate.ProductValidate;
import com.elevenst.terroir.product.registration.product.vo.*;
import com.elevenst.terroir.product.registration.seller.service.SellerServiceImpl;
import com.elevenst.terroir.product.registration.seller.validate.SellerValidate;
import com.elevenst.terroir.product.registration.product.code.SelStatCd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skt.tmall.common.util.asyncdb.AsyncDB;
import skt.tmall.common.util.asyncdb.AsyncDBImpl;

import java.util.*;

import static org.springframework.beans.BeanUtils.copyProperties;

@Slf4j
@Service
public class ProductServiceImpl implements RegistrationInfoConverter<ProductVO>{

    @Autowired
    CategoryServiceImpl categoryServiceImpl;

    @Autowired
    SellerServiceImpl sellerService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    OptionMapper optionMapper;

    @Autowired
    ProductVerticalMapper productVerticalMapper;

    @Autowired
    ProductValidate productValidate;

    @Autowired
    SellerValidate sellerValidate;

    @Autowired
    DeliveryServiceImpl deliveryService;

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    CommonServiceImpl commonService;

    @Autowired
    ObjectCacheImpl objectCache;

    @Autowired
    PropMgr propMgr;

    public void setProductInfoProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        this.setProductVoInfo(preProductVO, productVO);
        this.checkProductValidate(preProductVO, productVO);
    }

    public void insertProductProcess(ProductVO productVO) throws ProductException{
        this.insertPdPrdStatHist(productVO);
        this.insertPdPrdNHist(productVO);
        this.insertDpPrdContSummary(productVO);
        this.checkProductDispArea(productVO);
        this.insertPdPrdItm(productVO);
        this.insertProductRmaterial(productVO);
        this.insertPdStdPrdAttrComp(productVO);
        this.mergePdPrdTourInfo(productVO,true);
        this.mergeProductTagInfo(productVO);
    }

    public void updateProductProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException{

        if(productValidate.isCtgrMoveProduct(preProductVO, productVO)){
            this.updateDpPrdContSummary(preProductVO, productVO);
            this.updateDpPrdSummary(preProductVO, productVO);
            this.updateTrDealEvl(preProductVO, productVO);
            this.updateDpContAplCtgr(preProductVO, productVO);
            this.updateDpSemiExprt(preProductVO, productVO);
        }
        this.updatePdStdPrdAttrComp(productVO);
        this.updatePdPrdNHist(preProductVO, productVO);
        this.checkProductDispArea(productVO);
        this.insertPdPrdStatHist(productVO);
        this.updateProductRmaterial(productVO);
        this.mergePdPrdTourInfo(productVO,false);
        this.mergeProductTagInfo(productVO);
    }

    public void updatePdPrdNHist(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        PdPrd pdPrd = this.setPdPrdInfo(productVO);

        productMapper.updateProduct(pdPrd);
        productMapper.insertPdPrdHist(pdPrd);
    }

    private void updateDpPrdContSummary(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if(productValidate.isCtgrMoveProduct(preProductVO, productVO)){
            productMapper.updateDpPrdContSummary(this.setDpPrdContSummary(productVO));
        }
    }

    private void updateDpPrdSummary(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if(productValidate.isCtgrMoveProduct(preProductVO, productVO)){
            productMapper.updateDpPrdSummary(this.setDpPrdContSummary(productVO));
        }
    }

    private void updateTrDealEvl(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if(productValidate.isCtgrMoveProduct(preProductVO, productVO)){
            productMapper.updateTrDealEvl(this.setDpPrdContSummary(productVO));
        }
    }

    private void updateDpContAplCtgr(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if(productValidate.isCtgrMoveProduct(preProductVO, productVO)){
            productMapper.updateDpContAplCtgr(this.setDpPrdContSummary(productVO));
        }
    }

    private void updateDpSemiExprt(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if(productValidate.isCtgrMoveProduct(preProductVO, productVO)){
            productMapper.updateDpSemiExprt(this.setDpPrdContSummary(productVO));
        }
    }

    private DpPrdContSummary setDpPrdContSummary(ProductVO productVO) throws ProductException{
        DpPrdContSummary dpPrdContSummary = new DpPrdContSummary();
        dpPrdContSummary.setPrdNo(productVO.getPrdNo());
        dpPrdContSummary.setAbrdBrandYn(productVO.getBaseVO().getAbrdBrandYn());
        dpPrdContSummary.setDispCtgrNo(productVO.getDispCtgrNo());
        dpPrdContSummary.setCreateNo(productVO.getCreateNo());
        dpPrdContSummary.setUpdateNo(productVO.getUpdateNo());

        return dpPrdContSummary;
    }

    private void insertPdPrdStatHist(ProductVO productVO) throws ProductException{
        this.setBundleProductSelStat(productVO);
        this.insertSellStatusHistory(this.setPdPrdStatHist(productVO));
    }

    private void setBundleProductSelStat(ProductVO productVO) throws ProductException{
        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getBsnDealClf(), BsnDealClf.COMMISSION)
            && GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSetTypCd(), SetTypCd.BUNDLE)
            && GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelStatCd(), SelStatCd.PRODUCT_SOLD_OUT))
        {
            productVO.getBaseVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_SALE);
        }
    }

    private void insertDpPrdContSummary(ProductVO productVO) throws ProductException{

        productMapper.insertProductSummary(this.setDpPrdContSummary(productVO));
    }


    private void setProductVoInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{

        this.setDefaultInfo(productVO);
        this.setSelTermDy(productVO);
        this.setWorkInfo(productVO);
        this.setEngDispYnByGlobal(productVO);
        this.setAdltCrtfToMinorSelCnYn(productVO);//TODO CYB 체크 필요
        this.setLowPrcCompExYn(productVO);
        this.setYes24AddInfo(productVO);
        if(productVO.isUpdate()){
            this.setPreProductVOToProdutVO(preProductVO, productVO);
        }else{

        }
        this.setExCupnInfo(productVO);
        this.replaceSpecialChar(productVO);
        this.removeInvalidTag(productVO);

    }

    private void removeInvalidTag(ProductVO productVO) throws ProductException{
        productVO.setPrdNm(StripHTMLTag.removeInvalidTag(StringUtil.nvl(productVO.getPrdNm(), "")));
        productVO.getBaseVO().setPrdNmEng(StripHTMLTag.removeInvalidTag(StringUtil.nvl(productVO.getBaseVO().getPrdNmEng(), "")));
        productVO.getBaseVO().setAdvrtStmt(StripHTMLTag.removeInvalidTag(productVO.getBaseVO().getAdvrtStmt()));
    }

    private void replaceSpecialChar(ProductVO productVO) throws ProductException{
        productVO.setPrdNm(commonService.replaceSpecialChar(productVO.getPrdNm()));
        productVO.getBaseVO().setPrdNmEng(commonService.replaceSpecialChar(productVO.getBaseVO().getPrdNmEng()));
        productVO.getBaseVO().setAdvrtStmt(commonService.replaceSpecialChar(productVO.getBaseVO().getAdvrtStmt()));
        if("Y".equals(productVO.getBaseVO().getStdPrdYn())){
            productVO.getBaseVO().setStdPrdNm(commonService.replaceSpecialChar(productVO.getBaseVO().getStdPrdNm()));
            productVO.getBaseVO().setMktPrdNm(commonService.replaceSpecialChar(productVO.getBaseVO().getMktPrdNm()));
        }
    }

    private void setExCupnInfo(ProductVO productVO) throws ProductException{
        if(sellerService.isCupnExcldSeller(productVO.getSelMnbdNo())){

            productVO.getBaseVO().setCupnExCd("01");
            productVO.getBaseVO().setLpCupnExYn("Y");
            productVO.getBaseVO().setPartnerCupnExYn("Y");
        }
    }

    private void setLowPrcCompExYn(ProductVO productVO) throws ProductException{
        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getPrdTypCd(), PrdTypCd.BRANCH_DELIVERY_MART)){
            productVO.getBaseVO().setLowPrcCompExYn("N");
        }
    }

    private void setSelTermDy(ProductVO productVO) throws ProductException{
        if(ProductConstDef.PRD_SEL_PRD_CLF_CD_CUST.equals(productVO.getBaseVO().getSelPrdClfCd())){
            productVO.getBaseVO().setSelBgnDy(StringUtil.setFullDateString(productVO.getBaseVO().getSelBgnDy(),true));
            productVO.getBaseVO().setSelEndDy(StringUtil.setFullDateString(productVO.getBaseVO().getSelEndDy(),false));
        }
    }

    private void setWorkInfo(ProductVO productVO) throws ProductException{
        //TODO CYB auth 체크 해서 하기
    }

    private void setDefaultInfo(ProductVO productVO) throws ProductException{

        if(StringUtil.isEmpty(productVO.getBaseVO().getTodayDlvCnYn())) productVO.getBaseVO().setTodayDlvCnYn("N");
        if(StringUtil.isEmpty(productVO.getBaseVO().getAppmtDyDlvCnYn())) productVO.getBaseVO().setAppmtDyDlvCnYn("N");
        if(StringUtil.isEmpty(productVO.getBaseVO().getUnDlvCnYn())) productVO.getBaseVO().setUnDlvCnYn("N");

        if(StringUtil.isEmpty(productVO.getBaseVO().getSelMnbdClfCd())) productVO.getBaseVO().setSelMnbdClfCd("02");
        if(StringUtil.isEmpty(productVO.getBaseVO().getPrdCompTypCd())) productVO.getBaseVO().setPrdCompTypCd("02");
        if(StringUtil.isEmpty(productVO.getBaseVO().getBagrnSelPrdYn())) productVO.getBaseVO().setBagrnSelPrdYn("N");
        if(StringUtil.isEmpty(productVO.getBaseVO().getAprvNessYn())) productVO.getBaseVO().setAprvNessYn("N");

        if(StringUtil.isEmpty(productVO.getBaseVO().getBndlDlvCnYn())) productVO.getBaseVO().setBndlDlvCnYn("N");
        if(StringUtil.isEmpty(productVO.getBaseVO().getRcptIsuCnYn())) productVO.getBaseVO().setRcptIsuCnYn("Y");
        if(StringUtil.isEmpty(productVO.getBaseVO().getPrcCmpExpYn())) productVO.getBaseVO().setPrcCmpExpYn("N");
        if(StringUtil.isEmpty(productVO.getBaseVO().getAbrdBrandYn())) productVO.getBaseVO().setAbrdBrandYn("N");
        if(StringUtil.isEmpty(productVO.getBaseVO().getAbrdBuyPlace())) productVO.getBaseVO().setAbrdBuyPlace("");

        if(productVO.isTown()){
            productVO.getBaseVO().setOmPrdYn("");
        }
    }

    private void checkProductValidate(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        productValidate.checkGlbItgIdsBySeller(productVO.getSelMnbdNo());

        if(productVO.isUpdate()){
            productValidate.checkSellerPrd(preProductVO, productVO);
            productValidate.checkStdPrdInfo(preProductVO, productVO);
            productValidate.checkUpdatePrdStat(preProductVO, productVO);
            this.setUpdateProductSelStatCd(preProductVO, productVO);
        }else{
            this.setInsertProductSelStatCd(productVO);
            if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelStatCd(), SelStatCd.PRODUCT_COMFIRM_WAIT)){
                productVO.getBaseVO().setAprvNessYn("Y");
            }
        }
        productValidate.checkPrdNm(productVO);
        productValidate.checkPrdNmEng(productVO);
        productValidate.checkPrdAdvrtStmt(productVO);
        productValidate.checkOrgnTypCd(productVO);
        productValidate.checkDispPlayYn(productVO);
        productValidate.checkProductTagInfo(productVO);
    }

    private void insertPdPrdNHist(ProductVO productVO) throws ProductException{
        PdPrd pdPrd = this.setPdPrdInfo(productVO);
        productMapper.insertPdPrd(pdPrd);
        productMapper.insertPdPrdHist(pdPrd);
    }

    private PdPrd setPdPrdInfo(ProductVO productVO) throws ProductException{
        PdPrd pdPrd = new PdPrd();

        copyProperties(productVO.getBaseVO(), pdPrd);

        pdPrd.setPrdNo(productVO.getPrdNo());
        pdPrd.setSelMnbdNo(productVO.getSelMnbdNo());
        pdPrd.setDispCtgrNoDe(productVO.getDispCtgrNo());
        pdPrd.setSelMnbdNckNmSeq(productVO.getSellerVO().getSelMnbdNckNmSeq());
        pdPrd.setPrdNm(productVO.getPrdNm());
        pdPrd.setCreateNo(productVO.getCreateNo());
        pdPrd.setUpdateNo(productVO.getUpdateNo());
        pdPrd.setHistAplBgnDt(productVO.getHistAplBgnDt());

        pdPrd.setCreateCd(ProductConstDef.PRD_CREATE_CD_SO_FROMMSO);

        return pdPrd;
    }


    /**
     * 상품 정보 조회 PD_PRD
     *
     * @param prdNo
     * @return ProductBO
     * @throws ProductException
     */
    public ProductVO getProduct(long prdNo) throws ProductException {
        if(prdNo == 0){
            return null;
        }
        ProductVO productVO = null;
        try {
            productVO = productMapper.getProduct(prdNo);
        } catch (ProductException ex) {
            throw new ProductException("상품 정보 조회 오류", ex);
        } catch (Exception ex) {
            throw new ProductException("상품 정보 조회 오류", ex);
        }

        return productVO;
    }

    /**
     * 원재료 조회
     * @param prdNo
     * @return ProductRmaterialVO
     * @throws ProductException
     */
    public List<ProductRmaterialVO> getProductRmaterialList(long prdNo) throws ProductException {
        List<ProductRmaterialVO> resultList = new ArrayList<ProductRmaterialVO>();
        List<ProductRmaterialVO> productRmatialVOList = productMapper.getProductRmaterialList(prdNo);
        if(StringUtil.isNotEmpty(productRmatialVOList)) {
            for(ProductRmaterialVO getProductRmaterialVO : productRmatialVOList) {
                ProductRmaterialVO setProductRmaterialVO = new ProductRmaterialVO();
                PdPrdRmaterial pdPrdRmaterial = new PdPrdRmaterial();
                pdPrdRmaterial.setPrdNo(prdNo);
                pdPrdRmaterial.setRmaterialSeqNo(getProductRmaterialVO.getRmaterialSeqNo());

                setProductRmaterialVO.setRmaterialNm(getProductRmaterialVO.getRmaterialNm());
                setProductRmaterialVO.setRmaterialIngredList(productMapper.getProductRmaterialIngredList(pdPrdRmaterial));

                resultList.add(setProductRmaterialVO);
            }
        }
        return resultList;
    }

    /**
     * 원재료 등록
     * @param pdPrdRmaterialList
     * @param pdPrdRmaterialIngredList
     * @throws ProductException
     */
    public void insertProductRmaterial(List<PdPrdRmaterial> pdPrdRmaterialList, List<PdPrdRmaterialIngred> pdPrdRmaterialIngredList) throws ProductException {
        for(int i=0, len=pdPrdRmaterialList.size(); i<len; i++) {
            productMapper.insertProductRmaterial(pdPrdRmaterialList.get(i));
        }
        for(int i=0, len=pdPrdRmaterialIngredList.size(); i<len; i++) {
            productMapper.insertProductRmaterialIngred(pdPrdRmaterialIngredList.get(i));
        }
    }

    private void insertProductRmaterial(ProductVO productVO) throws ProductException{

        List<ProductRmaterialVO> productRmaterialVOList = productVO.getProductRmaterialVOList();

        ProductRmaterialVO productRmaterialVO = null;
        ProductRmaterialIngredVO productRmaterialIngredVO = null;

        PdPrdRmaterial pdPrdRmaterial = null;
        PdPrdRmaterialIngred pdPrdRmaterialIngred = null;

        List<PdPrdRmaterial> pdPrdRmaterialList = null;
        List<PdPrdRmaterialIngred> pdPrdRmaterialIngredList = null;


        if(productRmaterialVOList != null && productRmaterialVOList.size() > 0){

            pdPrdRmaterialList = new ArrayList<PdPrdRmaterial>();
            pdPrdRmaterialIngredList = new ArrayList<PdPrdRmaterialIngred>();

            for(int i=1,size=productRmaterialVOList.size();i<=size;i++){
                productRmaterialVO = productRmaterialVOList.get(i-1);
                if(StringUtil.isNotEmpty(productRmaterialVO.getRmaterialNm())){
                    pdPrdRmaterial = new PdPrdRmaterial();
                    pdPrdRmaterial.setPrdNo(productVO.getPrdNo());
                    pdPrdRmaterial.setRmaterialSeqNo(i);
                    pdPrdRmaterial.setRmaterialNm(productRmaterialVO.getRmaterialNm());
                    pdPrdRmaterial.setCreateNo(productVO.getUpdateNo());
                    pdPrdRmaterial.setUpdateNo(productVO.getUpdateNo());

                    pdPrdRmaterialList.add(pdPrdRmaterial);

                    if(productRmaterialVO.getRmaterialIngredList() != null && productRmaterialVO.getRmaterialIngredList().size() > 0){

                        for(int j=1,ingredSize=productRmaterialVO.getRmaterialIngredList().size();j<=ingredSize;j++){
                            productRmaterialIngredVO = productRmaterialVO.getRmaterialIngredList().get(j-1);
                            pdPrdRmaterialIngred = new PdPrdRmaterialIngred();
                            pdPrdRmaterialIngred.setPrdNo(productVO.getPrdNo());
                            pdPrdRmaterialIngred.setRmaterialSeqNo(i);
                            pdPrdRmaterialIngred.setIngredSeqNo(j);
                            pdPrdRmaterialIngred.setIngredNm(productRmaterialIngredVO.getIngredNm());
                            pdPrdRmaterialIngred.setOrgnCountry(productRmaterialIngredVO.getOrgnCountry());
                            pdPrdRmaterialIngred.setContent(productRmaterialIngredVO.getContent());
                            pdPrdRmaterialIngredList.add(pdPrdRmaterialIngred);
                        }
                    }
                }
            }

            if(pdPrdRmaterialList.size() > 0 && pdPrdRmaterialIngredList.size() > 0){
                try{
                    this.insertProductRmaterial(pdPrdRmaterialList, pdPrdRmaterialIngredList);
                }catch (Exception e){
                    throw new ProductException("원재료 등록시 오류");
                }
            }
        }
    }

    private void updateProductRmaterial(ProductVO productVO) throws ProductException{
        this.deleteProductRmaterial(productVO.getPrdNo());
        this.insertProductRmaterial(productVO);
    }

    /**
     * 원재료 삭제
     * @param prdNo
     * @throws ProductException
     */
    public void deleteProductRmaterial(long prdNo) throws ProductException {
        productMapper.deleteAllProductRmaterial(prdNo);
        productMapper.deleteAllProductRmaterialIngred(prdNo);
    }


    /**
     * 상품의 UPDATE_DT를 SYSDATE로  수정한다. 독립적으로 운영되는 상품에 대한 정보 수정시 사용(검색에서 필요시)
     * @param productVO
     * @return
     * @throws ProductException
     */
    public void updateProductUpdateDt(ProductVO productVO) throws ProductException {
        try {
            String nowDateStr = DateUtil.getFormatString("yyyyMMddHHmmss");
            productVO.setHistAplBgnDt(nowDateStr);
            productVO.setUpdateDt(nowDateStr);

            /*
             * 상품정보 수정
             */
            productMapper.updateProductUpdateDt(productVO);
            /*
             * 상품History정보 저장
             */
            productMapper.insertPdPrdStatHist(this.setPdPrdStatHist(productVO));

        } catch (ProductException ex) {
            throw new ProductException("상품의 UPDATE_DT 수정 오류", ex);
        } catch (Exception ex) {
            throw new ProductException("상품의 UPDATE_DT 수정 오류", ex);
        }
    }

    /**
     * 상품 판매상태정보 업데이트
     *
     * @param productVO
     * @param prdStatCd  - 상태코드
     * @param statChgRsn
     * @throws ProductException
     */
    public void updateServicePrdStat(ProductVO productVO, String prdStatCd, String statChgRsn) throws ProductException {
        String curDate = DateUtil.getFormatString("yyyyMMddHHmmss");

        try {
            ProductVO paramPrdVO = new ProductVO();
            PdPrdStatHist pdPrdStatHist = new PdPrdStatHist();
            pdPrdStatHist.setAplBgnDt(curDate);
            pdPrdStatHist.setPrdStatCd(prdStatCd); //품절
            pdPrdStatHist.setPrdNo(productVO.getPrdNo());
            pdPrdStatHist.setStatChgRsn(statChgRsn);
            pdPrdStatHist.setSelMthdCd(productVO.getBaseVO().getSelMthdCd());
            pdPrdStatHist.setUpdateNo(Long.valueOf(productVO.getCreateNo()));
            pdPrdStatHist.setCreateNo(Long.valueOf(productVO.getCreateNo()));

            PdPrd pdPrd = new PdPrd();
            pdPrd.setPrdNo(productVO.getPrdNo());
            pdPrd.setHistAplBgnDt(curDate);

            //상품 히스토리 저장
            paramPrdVO.setHistAplBgnDt(curDate);
            productMapper.insertPdPrdHist(pdPrd);

            //상품상태 히스토리 등록
            this.insertSellStatusHistory(pdPrdStatHist);
        } catch (ProductException ex) {
            throw new ProductException("상품 판매상태정보 수정 오류", ex);
        } catch (Exception ex) {
            throw new ProductException("상품 판매상태정보 수정 오류", ex);
        }
    }

    /**
     * 판매상태수정 (수정항목 : 상품상태) 메서드 설명 (첫라인의 설명은 brief 설명) 보다 자세한 설명 (두번째 라인부터)
     *
     * @return
     */
    public void updateProductStatus(ProductVO productVO) throws ProductException {
        try {
            // 상품 판매상태 수정
            productMapper.updateProductStatus(productVO);

            // 추가 상품이 있는 경우 추가상품도 업데이트
            productMapper.updateAddProductStatus(productVO);
        } catch (Exception ex) {
            // Exception 발생
            throw new ProductException(ex);
        }
    }

    /**
     * 상품 판매상태이력 정보 입력  PD_PRD_STAT_HIST
     *
     * @param pdPrdStatHist
     * @return
     * @throws ProductException
     */
    public int insertSellStatusHistory(PdPrdStatHist pdPrdStatHist) throws ProductException {
        int rtncode = 0;
        try {
            productMapper.insertPdPrdStatHist(pdPrdStatHist);
        } catch (ProductException ex) {
            throw new ProductException("상품 판매상태이력 정보 등록 오류", ex);
        } catch (Exception ex) {
            throw new ProductException("상품 판매상태이력 정보 등록 오류", ex);
        }
        return rtncode;
    }

    public void mergePdPrdTourInfo(ProductVO productVO, boolean isReg) throws ProductException {
        try {
            CategoryVO categoryBO = productValidate.getDisplayCategoryBO(productVO, productVO.getDispCtgrNo());

            if(productVO != null && !ProductConstDef.PRD_TYP_CD_TOUR_INTER.equals(productVO.getBaseVO().getPrdTypCd()) && !ProductConstDef.PRD_TYP_CD_TOUR_HOTEL_INTER.equals(productVO.getBaseVO().getPrdTypCd()) &&
                (ProductConstDef.TOUR_DISP_CTGR2_NO_HOTEL.equals(categoryBO.getMctgrNo())
                    || ProductConstDef.TOUR_DISP_CTGR2_NO_RESORT.equals(categoryBO.getMctgrNo())
                    || ProductConstDef.TOUR_DISP_CTGR2_NO_PENSION.equals(categoryBO.getMctgrNo())
                    || ProductConstDef.TOUR_DISP_CTGR2_NO_TRIP_ABROAD.equals(categoryBO.getMctgrNo())
                    || ProductConstDef.TOUR_DISP_CTGR2_NO_DOMESTIC_LODGE.equals(categoryBO.getMctgrNo())
                    || ProductConstDef.TOUR_DISP_CTGR2_NO_HONEYMOON.equals(categoryBO.getMctgrNo())
                    || ProductConstDef.TOUR_DISP_CTGR2_NO_CRUISE.equals(categoryBO.getMctgrNo())
                    || ProductConstDef.TOUR_DISP_CTGR2_NO_ABROAD_GOLF.equals(categoryBO.getMctgrNo())
                    || ProductConstDef.TOUR_DISP_CTGR2_NO_ABROAD_PILGRIMAGE.equals(categoryBO.getMctgrNo())
                    || ProductConstDef.TOUR_DISP_CTGR3_NO_HOTEL.equals(categoryBO.getSctgrNo())
                    || ProductConstDef.TOUR_DISP_CTGR3_NO_RESORT.equals(categoryBO.getSctgrNo())
                    || ProductConstDef.TOUR_DISP_CTGR3_NO_PENSION.equals(categoryBO.getSctgrNo()))) {

                //String tourSidoSelectInfo = dataBox.getNotNullString("tourSidoSelectInfo", "");
                //String sigunguSelectInfoID = dataBox.getNotNullString("sigunguSelectInfoID", "");
                PdPrdTour pdPrdTour = new PdPrdTour();
                pdPrdTour.setPrdNo(productVO.getPrdNo());
                pdPrdTour.setDrcStlYn("Y");
                pdPrdTour.setIframeAcssYn("N");
                pdPrdTour.setSvcAreaCd("00");
                pdPrdTour.setCnFeeAplYn("N");
                pdPrdTour.setDirectStlYn("N");
                pdPrdTour.setSidoNm(productVO.getBaseVO().getTourSidoSelectInfo());
                pdPrdTour.setSigunguNm(productVO.getBaseVO().getSigunguSelectInfoID());
                pdPrdTour.setCreateDt(new Date());
                pdPrdTour.setCreateNo(Long.valueOf(productVO.getCreateNo()));
                pdPrdTour.setUpdateDt(new Date());
                pdPrdTour.setUpdateNo(Long.valueOf(productVO.getCreateNo()));
                //pdPrdTour.setSelMnbdNo(productVO.getCreateNo());

                if(isReg) {
                    //등록
                    productVerticalMapper.insertTour(pdPrdTour);
                } else {
                    PdPrdTour resTourProductBO = productVerticalMapper.getProductTourDetail(productVO.getPrdNo());
                    if(resTourProductBO != null) {
                        productVerticalMapper.updateTourProductForSO(pdPrdTour);
                    } else {
                        productVerticalMapper.insertTour(pdPrdTour);
                    }
                }
                productVerticalMapper.insertTourHist(productVO.getPrdNo());
            }
        } catch (Exception e) {
            throw new ProductException("PD_PRD_TOUR 핸들링 오류", e);
        }
    }

    /**
     * 서비스 이용권상품 등록  PD_SVC_PASS_PRD
     *
     * @param pdSvcPassPrd
     * @return
     * @throws ProductException
     */
    public int insertProductServicePass(PdSvcPassPrd pdSvcPassPrd) throws ProductException {
        int rtncode = 0;
        try {
            productMapper.insertPdSvcPassPrd(pdSvcPassPrd);
            //  서비스 이용권상품 이력정보 입력
            productMapper.insertPdSvcPassPrdHist(pdSvcPassPrd);
        } catch (ProductException ex) {
            throw new ProductException("서비스 이용권상품 등록", ex);
        } catch (Exception ex) {
            throw new ProductException("서비스 이용권상품 등록", ex);
        }
        return rtncode;
    }


    private PdPrdStatHist setPdPrdStatHist(ProductVO productVO) throws ProductException{
        PdPrdStatHist pdPrdStatHist = new PdPrdStatHist();
        pdPrdStatHist.setPrdNo(productVO.getPrdNo());
        pdPrdStatHist.setAplBgnDt(productVO.getHistAplBgnDt());
        pdPrdStatHist.setPrdStatCd(productVO.getBaseVO().getSelStatCd());
        pdPrdStatHist.setSelMthdCd(productVO.getBaseVO().getSelMthdCd());
        pdPrdStatHist.setCreateNo(productVO.getCreateNo());
        pdPrdStatHist.setUpdateNo(productVO.getUpdateNo());
        pdPrdStatHist.setSvcAreaCd(productVO.getBaseVO().getSvcAreaCd());

        return pdPrdStatHist;
    }



    /**
     *  사은품 행사정보 유효성 체크(ProductAbstractInfoConvert.setProductGiftBO())
     *  사실상 databox의 데이터를 VO로 전환시켜주는 메소드라, frontier와 합을 맞추기 전까지는 구현이 힘들다.
     * */
    public ProductGiftVO setProductGiftVO(ProductGiftVO productGiftVO, long prdNo, long userNo) {
        /* domain version databox.
        String giftNm = dataBox.getNotNullString("giftNm").trim();
        String aplBgnDt = dataBox.getNotNullString("giftAplBgnDt").trim().replace("/","");
        String aplEndDt = dataBox.getNotNullString("giftAplEndDt").trim().replace("/","");
        String giftInfo = dataBox.getNotNullString("giftInfo").trim();
        String imgNm = dataBox.getNotNullString("giftImageUrl_FileNm").trim();
        String imgUrlPath = dataBox.getNotNullString("giftImageUrl_Path").trim();
        */

        return productGiftVO;
    }


    /**
     * 재고에 따른 상품 판매상태 수정 오류
     *
     * @param prdNo
     * @throws ProductException
     */
    @Deprecated
    public String updateProductStockSelStatCd(long prdNo) throws ProductException {
        try {
            ProductVO productVO = productMapper.getProduct(prdNo); //상품정보 조회
            if (productVO == null) {
                throw new ProductException("[" + prdNo + "] 상품정보 존재 하지 않음");
            }

            return updateProductStockSelStatCd(optionMapper.getProductStckCnt(prdNo), productVO);
        } catch (ProductException ex) {
            throw new ProductException("재고에 따른 상품 판매상태 수정 오류", ex);
        } catch (Exception ex) {
            throw new ProductException("재고에 따른 상품 판매상태 수정 오류", ex);
        }
    }

    public String updateProductStockSelStatCd(long stckCnt, ProductVO productVO) throws ProductException {
        boolean isChangeStat = false;
        String selStatCd = productVO.getBaseVO().getSelStatCd();
        String statChgRsn = "";

        if (stckCnt	> 0) {
            // 경매상품이 아니고, 기존 상품이 품절 중이였다면 판매시작일에 따라 전시전/판매중 처리 함.
            if (!ProductConstDef.PRD_SEL_MTHD_CD_AUCT.equals(productVO.getBaseVO().getSelMthdCd())
                && ProductConstDef.PRD_SEL_STAT_CD_SOLDOUT.equals(productVO.getBaseVO().getSelStatCd())) {
                String currDay		= DateUtil.getFormatString("yyyyMMdd");
                String selBgnDay	= StringUtil.left(productVO.getBaseVO().getSelBgnDy(), 8);
                String selEndDay	= StringUtil.left(productVO.getBaseVO().getSelEndDy(),8);

                // 판매시작일이 현재일 이후면 '전시전'처리
                if (Long.parseLong(selBgnDay) > Long.parseLong(currDay)) {
                    isChangeStat = true;
                    selStatCd = ProductConstDef.PRD_SEL_STAT_CD_BEFORE_DISPLAY;
                    statChgRsn = ProductConstDef.PRD_SEL_STAT_NM_BEFORE_DISPLAY;
                } else if (Long.parseLong(selEndDay) < Long.parseLong(currDay)) {
                    isChangeStat = true;
                    selStatCd = ProductConstDef.PRD_SEL_STAT_CD_CLOSE_SALE;
                    statChgRsn = ProductConstDef.PRD_SEL_STAT_NM_CLOSE_SALE;
                } else {
                    isChangeStat = true;
                    selStatCd = ProductConstDef.PRD_SEL_STAT_CD_SALE;
                    statChgRsn = ProductConstDef.PRD_SEL_STAT_NM_SALE;
                }
            } else {
                updateProductUpdateDt(productVO);
            }
        } else {
            // 경매상품이 아니고, 판매중일 경우 품절 처리함
            if (!ProductConstDef.PRD_SEL_MTHD_CD_AUCT.equals(productVO.getBaseVO().getSelMthdCd())) {
                //Yes24 직매입 일 경우는 상태값을 재고가 없어도 판매상태로 처리
                if(sellerValidate.chkYes24DirectAuth(productVO.getBaseVO().getBsnDealClf(), ""+productVO.getSelMnbdNo())){
                    selStatCd = ProductConstDef.PRD_SEL_STAT_CD_SALE;
                    isChangeStat = false;
                } else if(ProductConstDef.PRD_SEL_STAT_CD_SALE.equals(productVO.getBaseVO().getSelStatCd())) {
                    isChangeStat = true;
                    selStatCd = ProductConstDef.PRD_SEL_STAT_CD_SOLDOUT;
                    statChgRsn = ProductConstDef.PRD_SEL_STAT_NM_SOLDOUT;
                }
            } else if (!ProductConstDef.PRD_AUCT_SEL_STAT_CD_SOLD.equals(productVO.getBaseVO().getSelStatCd())
                && !ProductConstDef.PRD_AUCT_SEL_STAT_CD_DONT_SELL.equals(productVO.getBaseVO().getSelStatCd())) {
                // 경매상품일 경우, 판매상태가  낙찰정상종료, 유찰정상종료 가 아닐경우 낙찰종료 처리함/
                isChangeStat = true;
                selStatCd = ProductConstDef.PRD_AUCT_SEL_STAT_CD_SOLD;
                statChgRsn = "낙찰종료";
            }
        }

        if(isChangeStat) this.updateServicePrdStat(productVO, selStatCd, statChgRsn);

        return selStatCd;
    }

    /**
     * 재고에 따른 상품 판매상태 수정
     *  - 판매상태코드 반환
     */
    public String updateProductStockSelStatCd(long prdNo, long stckCnt, ProductVO curSetProductVO) throws ProductException {
        String selStatCd = null;

        try	{
            ProductVO productVO	= productMapper.getProduct(prdNo); //상품정보 조회

            if (productVO == null)  throw new ProductException("[" + prdNo + "] 상품정보가 존재 하지 않습니다.");
            productVO.setUpdateNo(curSetProductVO.getUpdateNo());

            selStatCd =	this.updateProductStockSelStatCd(stckCnt, productVO);
        } catch	(ProductException ex) {
            throw new ProductException("재고에 따른 상품 판매상태	수정 오류",	ex);
        } catch	(Exception ex) {
            throw new ProductException("재고에 따른 상품 판매상태	수정 오류",	ex);
        }

        return selStatCd;
    }

    /**
     * 상품 판매상태정보 업데이트
     *
     */
    /*
    public void updateServicePrdStat(ProductVO productVO, String prdStatCd, String statChgRsn) throws ProductException {
        String curDate = DateUtil.getFormatString("yyyyMMddHHmmss");

        try {
            ProductVO paramPrdBO = new ProductVO();
            paramPrdBO.setBaseVO(new BaseVO());
            PdPrdStatHist pdPrdStatHist = new PdPrdStatHist();
            pdPrdStatHist.setAplBgnDt(curDate);
            pdPrdStatHist.setAplEndDt(productVO.getBaseVO().getSelEndDy());
            pdPrdStatHist.setPrdStatCd(prdStatCd); //품절
            pdPrdStatHist.setPrdNo(productVO.getPrdNo());
            pdPrdStatHist.setStatChgRsn(statChgRsn);
            pdPrdStatHist.setSelMthdCd(productVO.getBaseVO().getSelMthdCd());
            pdPrdStatHist.setUpdateNo(productVO.getCreateNo());
            pdPrdStatHist.setCreateNo(productVO.getCreateNo());

            //상품기본정보 업데이트
            paramPrdBO.setPrdNo(productVO.getPrdNo());
            paramPrdBO.getBaseVO().setSelStatCd(prdStatCd);
            paramPrdBO.getBaseVO().setSelBgnDy(productVO.getBaseVO().getSelBgnDy());
            paramPrdBO.getBaseVO().setSelEndDy(productVO.getBaseVO().getSelEndDy());
            paramPrdBO.setUpdateNo(productVO.getCreateNo());
            paramPrdBO.getBaseVO().setSetTypCd(productVO.getBaseVO().getSetTypCd());
            productMapper.updateProductStatus(paramPrdBO);

            //상품 히스토리 저장
            PdPrdHist pdPrdHist = new PdPrdHist();
            pdPrdHist.setPrdNo(paramPrdBO.getPrdNo());
            pdPrdHist.setHistAplEndDt(curDate);
            productMapper.insertProductHist(pdPrdHist);

            //상품상태 히스토리 등록
            this.insertSellStatusHistory(pdPrdStatHist);
        } catch (ProductException ex) {
            throw new ProductException("상품 판매상태정보 수정 오류", ex);
        } catch (Exception ex) {
            throw new ProductException("상품 판매상태정보 수정 오류", ex);
        }
    }
    */

    public void synchBundleProduct(ProductVO productBO) throws ProductException{
        Map paramMap = new HashMap();
        paramMap.put("prdNo", productBO.getPrdNo());
        paramMap.put("createNo", productBO.getCreateNo() <= 0 ? productBO.getUpdateNo() : productBO.getCreateNo());

        this.synchBundleProduct(paramMap);
    }

    public void synchBundleProduct(Map paramMap) throws ProductException{
        try{
            Long prdNo = (Long) paramMap.get("prdNo");
            Long stockNo = (Long) paramMap.get("stockNo");
            Long createNo = (Long) paramMap.get("createNo");

            List<ProductVO> objList = null;
            if(stockNo == null || stockNo <= 0){
                objList = productMapper.getChangedStckQtyBundleProduct(prdNo);
            }else{
                objList = productMapper.getChangedStckQtyBundleProductByUnitStock(stockNo);
            }

            // 해당 상품이 묶음 상품의 옵션 구성상품으로 있는 경우
            if(!StringUtil.isEmpty(objList)){
                DpGnrlDispVO bundlePrdGnrlDispBO = new DpGnrlDispVO();

                for(ProductVO bundlePrd : objList){
                    bundlePrd.setCreateNo(createNo);
                    if(StringUtil.isNotEmpty(bundlePrd.getBaseVO().getSelStatCd())){
                        this.updateServicePrdStat(bundlePrd, bundlePrd.getBaseVO().getSelStatCd(), "묶음 상품의 구성상품 변경에 의한 처리");
                    }

                    bundlePrdGnrlDispBO.setPrdNo(bundlePrd.getPrdNo());
                    bundlePrdGnrlDispBO.setStckQty(bundlePrd.getOptionVO().getPrdStckQty());
                    bundlePrdGnrlDispBO.setOptCd(bundlePrd.getOptionVO().getOptTypCd());
                    if("".equals(bundlePrd.getBaseVO().getSelStatCd())) { bundlePrdGnrlDispBO.setDisableUpdateYn("Y"); }
                    else { bundlePrdGnrlDispBO.setSelStatCd(bundlePrd.getBaseVO().getSelStatCd()); }
                    categoryServiceImpl.updateProductGeneralDisplay(bundlePrdGnrlDispBO);
                }
            }
        }catch(ProductException daoE){
            throw new ProductException("묶음 상품 상태 및 재고 동기화 에러");
        }catch(Exception e){
            throw new ProductException("묶음 상품 상태 및 재고 동기화 에러");
        }
    }

    /**
     * 옵션 수정 시 옵션 구분 코드 및 사용기간 수정 (PD_PRD)
     * @param pdPrd
     * @throws ProductException
     */
    public void updateProductOptionModify(PdPrd pdPrd) throws ProductException {
        try {
            productMapper.updateProductOptionModify(pdPrd);
        } catch (ProductException ex) {
            throw new ProductException("옵션 수정 시 옵션 구분 코드 및 사용기간 수정", ex);
        } catch (Exception ex) {
            throw new ProductException("옵션 수정 시 옵션 구분 코드 및 사용기간 수정", ex);
        }
    }

    /**
     * 상품 존재여부확인
     * @param prdNo
     * @return
     * @throws ProductException
     */
    public boolean isExistPrd(long prdNo) throws ProductException {
        boolean isCheck = false;

        try {
            long prdCnt = productMapper.isExistFrPrd(prdNo);

            if (prdCnt > 0) {
                isCheck = true;
            }else{
                isCheck = false;
            }
        } catch (ProductException ex) {
            throw new ProductException("해외명품 간략PO 상품 존재여부확인 오류", ex);
        } catch (Exception ex) {
            throw new ProductException("해외명품 간략PO 상품 존재여부확인 오류", ex);
        }
        return isCheck;
    }

    /**
     * 시리즈 상품 여부
     */
    public boolean getSeriesProductYn(ProductSeriesVO productSeriesVO) throws ProductException {
        boolean result = false;
        int sCnt =  productMapper.getSeriesProductYn(productSeriesVO);
        if(sCnt > 0) {
            result = true;
        }
        return result;
    }

    public void setAdltCrtfToMinorSelCnYn(ProductVO productVO) throws ProductException{
        if("Y".equals(productVO.getCategoryVO().getAdltCrtfYn())){
            productVO.getBaseVO().setMinorSelCnYn("N");
        }
    }

    private void setPreProductVOToProdutVO(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        productVO.getBaseVO().setLowPrcCompExYn(preProductVO.getBaseVO().getLowPrcCompExYn());
        productVO.getBaseVO().setCtgrPntPreExYn(preProductVO.getBaseVO().getCtgrPntPreExYn());
    }

    //morning01 셀러 현재 판매중인것 하나도 없음
    @Deprecated
    public void setChangeSelMthdCd(ProductVO preProductVO, ProductVO productVO) throws ProductException{

        if(!productVO.getBaseVO().getSelMthdCd().equals(preProductVO.getBaseVO().getSelMthdCd())
            && ProductConstDef.PRD_SEL_MTHD_CD_FIXED.equals(preProductVO.getBaseVO().getSelMthdCd())
            && ProductConstDef.PRD_SEL_MTHD_CD_USED.equals(productVO.getBaseVO().getSelMthdCd())
            && propMgr.get1hourTimeProperty("PRODUCT_USED_SELL_ID_API").equals(productVO.getMemberVO().getMemID())){
//            productVO.getBaseVO().setSelMthdCdChangeYn("Y");
        }
    }

    public long getNewPrdNo() throws ProductException{
        return productMapper.getProductNo();
    }



    private void setInsertProductSelStatCd(ProductVO productVO) throws ProductException{

        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getBsnDealClf(),BsnDealClf.COMMISSION)){
            if("Y".equals(productVO.getCategoryVO().getAprvCrtfYn())) {
                // 수수료 상품에 카테고리 승인이 푤하면 승인대기
                productVO.getBaseVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE);
            }else if(ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(productVO.getBaseVO().getDlvClf())){
                // 셀러위탁배송 상품은 품절로 세팅
                productVO.getBaseVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_SOLDOUT);
            }

        }else{
            productVO.getBaseVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE);
        }

        if("Y".equals(productVO.getMobile1WonApprvAsk())
            || "Y".equals(productVO.getMobile1WonYn())){
            productVO.getBaseVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE);
        }

        if(StringUtil.getLong(productVO.getBaseVO().getSelBgnDy()) <= StringUtil.getLong(productVO.getHistAplBgnDt())){
            productVO.getBaseVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_SALE); // 판매상태 : 진행중

            if(ProductConstDef.CERT_TYPE_101.equals(productVO.getBaseVO().getCertTypCd())){
                // 엑셀업로드 외부인증번호일 경우 등록시에는 상품상태를 품절 처리함.
                productVO.getBaseVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_SOLDOUT);
            }
        }else{
            productVO.getBaseVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_BEFORE_DISPLAY); // 판매상태 : 진행예정
        }

        //1원폰 등록시 셀러캐시 체크
        if("Y".equals(productVO.getMobile1WonApprvAsk())){
            productValidate.checkEnoughSellerCash(productVO);
        }
    }

    private void setUpdateProductSelStatCd(ProductVO preProductVO, ProductVO productVO) throws ProductException {

        CategoryVO preCtgrVO = preProductVO.getCategoryVO();
        CategoryVO categoryVO = productVO.getCategoryVO();

        String nowDateStr = DateUtil.getFormatString("yyyyMMdd");

        if (GroupCodeUtil.equalsDtlsCd(preProductVO.getBaseVO().getBsnDealClf(), BsnDealClf.COMMISSION)
            && "Y".equals(categoryVO.getAdltCrtfYn())
            && !"Y".equals(preCtgrVO.getOfferDispLmtYn())
            && preProductVO.getDispCtgrNo() != productVO.getDispCtgrNo()) {
            productVO.getBaseVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE);
            productVO.getBaseVO().setStatChgRsn("상품승인이 필요한 카테고리입니다.");
        } else if (("Y".equals(productVO.getMobile1WonApprvAsk()) || "Y".equals(preCtgrVO.getMobileYn()))
            && sellerService.isMobileSeller(productVO)) {
            // 핸드폰 1원폰 신청시 승인대기로 설정
            productVO.getBaseVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE);
        } else if ("N".equals(productVO.getMobile1WonApprvAsk())
            || ("Y".equals(preCtgrVO.getMobileYn()) && !sellerService.isMobileSeller(productVO))) {
            // 핸드폰 1원폰 신청해제시 판매시작일 기준으로 상태 변경

            if (StringUtil.getLong(StringUtil.left(productVO.getBaseVO().getSelBgnDy(), 8)) <= StringUtil.getLong(nowDateStr)) {
                productVO.getBaseVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_SALE); // 판매상태 : 진행중
            } else {
                productVO.getBaseVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_BEFORE_DISPLAY); // 판매상태 : 진행예정
            }
        } else if (!preProductVO.isTown()
            && GroupCodeUtil.equalsDtlsCd(preProductVO.getBaseVO().getBsnDealClf(), BsnDealClf.COMMISSION)
            && "Y".equals(preCtgrVO.getAprvCrtfYn())
            && GroupCodeUtil.equalsDtlsCd(preProductVO.getBaseVO().getSelStatCd(), SelStatCd.PRODUCT_COMFIRM_WAIT)
            && !"Y".equals(categoryVO.getAprvCrtfYn())) {

            productVO.getBaseVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_STOP_DISPLAY);
            productVO.getBaseVO().setStatChgRsn("승인필요 카테고리에서 일반 카테고리로 변경.");
        } else if (!GroupCodeUtil.equalsDtlsCd(preProductVO.getBaseVO().getBsnDealClf(), BsnDealClf.COMMISSION)
            && ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(preProductVO.getBaseVO().getSelStatCd())
            && !"Y".equals(categoryVO.getAprvCrtfYn())
            && sellerValidate.chkYes24DirectAuth(productVO.getSelMnbdNo())) {

            productVO.getBaseVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_SALE); // 판매상태 : 진행중
        } else {
            productVO.getBaseVO().setSelStatCd(preProductVO.getBaseVO().getSelStatCd());
        }

        if (ProductConstDef.PRD_SEL_STAT_CD_BEFORE_DISPLAY.equals(productVO.getBaseVO().getSelStatCd())) {
            if (StringUtil.getLong(StringUtil.left(productVO.getBaseVO().getSelBgnDy(), 8)) <= StringUtil.getLong(nowDateStr)) {
                productVO.getBaseVO().setSelStatCd(ProductConstDef.PRD_SEL_STAT_CD_SALE);     //판매중
            }
        }

        if(preProductVO.getPriceVO().getSelPrc() > 1
            && productVO.getPriceVO().getSelPrc() == 1
            && !sellerService.isMobileSeller(productVO)){
            productValidate.checkEnoughSellerCash(productVO);
        }
    }

    private void setEngDispYnByGlobal(ProductVO productVO) throws ProductException{

        if(productVO.isEngDispYnByGlobal()){
            productVO.getBaseVO().setEngDispYn("Y");
        } else {
            productVO.getBaseVO().setEngDispYn("N");
        }
    }


    private PdPrdSvcArea setPdPrdSvcArea(ProductVO productVO) throws ProductException{
        PdPrdSvcArea pdPrdSvcArea = new PdPrdSvcArea();
        pdPrdSvcArea.setPrdNo(productVO.getPrdNo());
        pdPrdSvcArea.setSvcAreaCd(productVO.getBaseVO().getSvcAreaCd());
        pdPrdSvcArea.setSelMnbdNo(productVO.getSelMnbdNo());
        pdPrdSvcArea.setPrdTypCd(productVO.getBaseVO().getPrdTypCd());
        pdPrdSvcArea.setShopNo(productVO.getBaseVO().getShopNo());
        pdPrdSvcArea.setCertDownYn(productVO.getBaseVO().getCertDownYn());//TODO CYB 체크 해볼것
        pdPrdSvcArea.setCertType(productVO.getBaseVO().getCertTypCd());
        pdPrdSvcArea.setAllBranchUseYn(productVO.getBaseVO().getAllBranchUseYn());
        pdPrdSvcArea.setTownSelLmtDy(productVO.getBaseVO().getTownSelLmtDy());

        return pdPrdSvcArea;
    }

    /**
     * 카테고리에 등록 가능한 상품 구분 코드 정보 목록 조회.
     *
     * @param pMap
     * @return 상품 구분 코드 목록.
     * @throws ProductException
     */
    public List<Map> getProductTypeInfoList(HashMap pMap) throws ProductException {
        List<Map> results = new ArrayList<Map>();

        List<Map> productTypeInfoList = productMapper.getProductTypeInfoList(pMap);
        for (Map map : productTypeInfoList) {if("Y".equals(map.get("REG_YN"))) results.add(map);}

        return results;
    }

    public ProductOptLimitVO getProductLimitInfo(ProductOptLimitVO paramBO) throws ProductException {
        try {
            return productMapper.getProductLimitInfo(paramBO);
        } catch (Exception ex) {
            throw new ProductException("상품 관련 정책 정보 조회 오류", ex);
        }
    }

    public ProductNameMultiLangVO getProductNameMultiLang(ProductNameMultiLangVO productNameMultiLangVO) throws ProductException {
        ProductNameMultiLangVO resultBo = new ProductNameMultiLangVO();
        try {
            List<ProductNameMultiLangVO> resultList = productMapper.getProductNameMultiLang(productNameMultiLangVO);
            if(resultList != null && resultList.size() > 0){
                for(ProductNameMultiLangVO tempBO:resultList){
                    if(ProductConstDef.TRANS_LANG_TYPE_ENUM.ENGLISH.getCode().equals(tempBO.getMtlglLangClfCd())){
                        resultBo.setSellerPrdNmEn(tempBO.getSellerTrsltPrdNm());
                        resultBo.setMchnTrsltPrdNmEn(tempBO.getMchnTrsltPrdNm());
                    }else{
                        resultBo.setSellerPrdNmCn(tempBO.getSellerTrsltPrdNm());
                        resultBo.setMchnTrsltPrdNmCn(tempBO.getMchnTrsltPrdNm());
                    }
                }
            }
        } catch(Exception ex) {
            throw new ProductException(ex);
        }
        return resultBo;
    }

    /**
     * 소호 상품 가능 유무 체크
     * @param selMnbdNo
     * @param dispCtgrNo
     * @return
     * @throws Exception
     */
    public boolean isSohoPrd(long selMnbdNo, long dispCtgrNo) throws ProductException {
        boolean isSohoPrd = false;

        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("selMnbdNo", selMnbdNo);
        map.put("dispCtgrNo", dispCtgrNo);
        List<HashMap> sohoList = productMapper.checkSohoPrd(map);

        if(StringUtil.isNotEmpty(sohoList)){
            isSohoPrd = true;
        }

        return isSohoPrd;
    }

    public String getProductOptTypCd(long prdNo) throws ProductException {
        return productMapper.getPrdOptTypCd(prdNo);
    }

    public List getProductWghtHist(long prdNo) throws ProductException {
        List dataList = new ArrayList();
        try {
            dataList = productMapper.getProductWghtHist(prdNo);
            if(!dataList.isEmpty()) {
                int index=0;
                for(index=0; index<dataList.size(); index++) {
                    HashMap<String, String> dataBO = (HashMap<String, String>)dataList.get(index);
                    dataBO.put("UPDATE_ID", StringUtil.getMaskingDataExceptSBO(dataBO.get("UPDATE_ID"), "IDPLUSNM"));
                }
            }
        } catch (Exception ex) {
            throw new ProductException("상품 무게 변경 이력 조회 오류", ex);
        }

        return dataList;
    }

    /**
     * 상품번호로 mem_no 조회
     *
     * @param prdNo
     * @return ProductBO
     * @throws ProductException
     */
    public long getProductSelMnbdNo(long prdNo) throws ProductException{
        long selMnbdNo = 0;
        try {
            selMnbdNo =  productMapper.getProductSelMnbdNo(prdNo);
        } catch(Exception ex) {
            throw new ProductException(ex);
        }
        return selMnbdNo;

    }

    private void setYes24AddInfo(ProductVO productVO) throws ProductException{
        if(sellerValidate.checkSoYes24DirectAuth(productVO.getSelMnbdNo())){
            productVO.getBaseVO().setOrdCnStepCd(ProductConstDef.ORD_CN_STEP_CD_08);
        }
    }

    private void insertPdPrdItm(ProductVO productVO) throws ProductException{
        if(productVO.getProductItemVOList() != null
            && productVO.getProductItemVOList().size() > 0)
        {
            PdPrdItm pdPrdItm = null;
            for(ProductItemVO productItemVO : productVO.getProductItemVOList()){
                pdPrdItm = new PdPrdItm();
                copyProperties(productItemVO, pdPrdItm);
                pdPrdItm.setCreateNo(productVO.getCreateNo());
                pdPrdItm.setUpdateNo(productVO.getUpdateNo());
                pdPrdItm.setPrdNo(productVO.getPrdNo());
                pdPrdItm.setItmSelMnbdNo(productVO.getSelMnbdNo());

                productMapper.insertPdPrdItm(pdPrdItm);
            }
        }
    }

    private void insertPdStdPrdAttrComp(ProductVO productVO) throws ProductException{
        if(productVO.getStdPrdAttrCompVOList() != null){
            PdStdPrdAttrComp pdStdPrdAttrComp = null;
            for(StdPrdAttrCompVO stdPrdAttrCompVO : productVO.getStdPrdAttrCompVOList()){
                pdStdPrdAttrComp = new PdStdPrdAttrComp();
                copyProperties(stdPrdAttrCompVO, pdStdPrdAttrComp);
                pdStdPrdAttrComp.setCreateNo(productVO.getCreateNo());
                pdStdPrdAttrComp.setUpdateNo(productVO.getUpdateNo());
                pdStdPrdAttrComp.setPrdNo(productVO.getPrdNo());

                productMapper.insertPdStdPrdAttrComp(pdStdPrdAttrComp);
            }
        }
    }

    private void updatePdStdPrdAttrComp(ProductVO productVO) throws ProductException{
        productMapper.deletePdStdPrdAttrComp(productVO.getPrdNo());
        if(productVO.getStdPrdAttrCompVOList() != null){
            PdStdPrdAttrComp pdStdPrdAttrComp = null;
            for(StdPrdAttrCompVO stdPrdAttrCompVO : productVO.getStdPrdAttrCompVOList()){
                pdStdPrdAttrComp = new PdStdPrdAttrComp();
                copyProperties(stdPrdAttrCompVO, pdStdPrdAttrComp);
                pdStdPrdAttrComp.setCreateNo(productVO.getCreateNo());
                pdStdPrdAttrComp.setUpdateNo(productVO.getUpdateNo());
                pdStdPrdAttrComp.setPrdNo(productVO.getPrdNo());

                productMapper.insertPdStdPrdAttrComp(pdStdPrdAttrComp);
            }
        }
    }

    public void insertPdPrdTempSave(PdPrdTempSave pdPrdTempSave) throws ProductException{
        productMapper.insertPdPrdTempSave(pdPrdTempSave);
    }

    public void updatePdPrdTempSave(PdPrdTempSave pdPrdTempSave) throws ProductException{
        productMapper.updatePdPrdTempSave(pdPrdTempSave);
    }

    public void deletePdPrdTempSave(PdPrdTempSave pdPrdTempSave) throws ProductException{
        productMapper.deletePdPrdTempSave(pdPrdTempSave);
    }

    public List<PdPrdTempSave> selectPdPrdTempSave(PdPrdTempSave pdPrdTempSave) throws ProductException{
        return productMapper.selectPdPrdTempSave(pdPrdTempSave);
    }

    /**
     * 상품전시영역 정보처리
     * @param productVO
     */
    public void checkProductDispArea(ProductVO productVO) {
        if (!"".equals(productVO.getDispAreaDmlCd())){
            if("".equals(productVO.getBaseVO().getCertDownYn())){
                productVO.getBaseVO().setCertDownYn("N");
            }
            this.mergeProductDispArea(productVO);
        }
    }

    private void mergeProductDispArea(ProductVO productVO) {
        try {
            PdPrdSvcArea pdPrdSvcArea = this.setPdPrdSvcArea(productVO);

            // 상품전시영역 정보 등록
            if ("I".equals(productVO.getDispAreaDmlCd())){
                productMapper.insertPdPrdSvcArea(pdPrdSvcArea);
            }
            // 상품전시영역 정보 수정
            else if ("U".equals(productVO.getDispAreaDmlCd())){
                productMapper.updatePdPrdSvcArea(pdPrdSvcArea);
            }
            // 상품전시영역 정보 삭제
            else if("D".equals(productVO.getDispAreaDmlCd())){
                productMapper.deleteProductDispArea(pdPrdSvcArea.getPrdNo());
            }
//            else{
//                productMapper.deleteProductDispArea(pdPrdSvcArea.getPrdNo());
//                productMapper.insertPdPrdSvcArea(pdPrdSvcArea);
//            }

        } catch (ProductException ex) {
            if (log.isErrorEnabled()) {
                log.error("[" + this.getClass().getName() + ".updateProduct] MSG : "+ ex.getMessage(), ex);
            }
            throw new ProductException("상품전시영역 정보 수정 오류", ex);
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("[" + this.getClass().getName() + ".updateProduct] MSG : "+ ex.getMessage(), ex);
            }
            throw new ProductException("상품전시영역 정보 수정 오류", ex);
        }
    }

    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        this.convertBaseInfo(preProductVO, productVO);
        this.convertRmaterialInfo(preProductVO, productVO);
        this.checkConvertProduct(preProductVO, productVO);
    }


    private void checkConvertProduct(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        productValidate.checkSelMthdCobuy(productVO);
        productValidate.checkGlobalDlvCost(productVO);
        productValidate.checkUsedProduct(productVO);
        productValidate.checkSelMthdCd(productVO.getBaseVO().getSelMthdCd());
        productValidate.checkRfndTypCd(productVO);
    }

    private void convertBaseInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{

        this.convertBaseDateInfo(productVO);
        this.convertOrdCnStepCd(productVO);
        this.convertPrdTypeCd(productVO);

        if(productVO.isLifeSvcTyp()){
            productVO.getBaseVO().setSelMthdCd(SelMthdCd.FIXED.getDtlsCd());
        }
        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelMthdCd(), SelMthdCd.USED)){
            productVO.getBaseVO().setCupnExCd("01");
            productVO.getBaseVO().setLpCupnExYn("Y");
            productVO.getBaseVO().setPartnerCupnExYn("Y");
        }
        if(StringUtil.isEmpty(productVO.getBaseVO().getBsnDealClf())) productVO.getBaseVO().setBsnDealClf(BsnDealClf.COMMISSION.getDtlsCd());

        productValidate.checkProductBaseInfo(preProductVO, productVO);
        if(productVO.isUpdate()){
            productValidate.checkProductMnbdInfo(productVO.getAuth(), this.getProductSelMnbdNo(productVO.getPrdNo()));
        }else{
            productValidate.checkProductMnbdInfo(productVO.getAuth(), productVO.getSelMnbdNo());
        }
        this.convertProductSvcAreaCd(preProductVO, productVO);
    }

    private void convertBaseDateInfo(ProductVO productVO) throws ProductException{
        productVO.getBaseVO().setSelEndDy(StringUtil.setFullDateString(productVO.getBaseVO().getSelEndDy(), false));
    }

    private void convertPrdTypeCd(ProductVO productVO) throws ProductException{
        // 소호상품 체크(코디 상품)
        if(this.isSohoPrd(productVO.getSelMnbdNo(), productVO.getDispCtgrNo())){
            productVO.getBaseVO().setPrdTypCd(PrdTypCd.SOHO.getDtlsCd());
        }else{

            // 여행상품 카테고리인 경우 상품의 PRD_TYP_CD 를 무조건 '13'으로 설정
            if(GroupCodeUtil.equalsDtlsCd(productVO.getCategoryVO().getPrdTypCd(), PrdTypCd.BP_TRAVEL)){
                productVO.getBaseVO().setPrdTypCd(ProductConstDef.PRD_TYP_CD_TOUR);
            }
            // e쿠폰
            else if (GroupCodeUtil.equalsDtlsCd(productVO.getCategoryVO().getPrdTypCd(), PrdTypCd.SMS_AND_PRINT_COUPON)
                && ProductConstDef.SVC_CTGR_TYP_CD_SMS_CUPN.equals(productVO.getCategoryVO().getSvcCtgrTypCd())) {
                // 해당 카테고리의 prd_typ_cd 가 아닌 경우 무조건 '10' 으로 셋팅
                if (!categoryServiceImpl.canRegistProductTypeToCategory(productVO)) {
                    productVO.getBaseVO().setPrdTypCd(ProductConstDef.PRD_TYP_CD_SMS);
                }
            }
            //렌탈 상품.
            else if (GroupCodeUtil.equalsDtlsCd(productVO.getCategoryVO().getPrdTypCd(), PrdTypCd.SMS_AND_PRINT_COUPON)
                && ProductConstDef.SVC_CTGR_TYP_CD_RENTAL.equals(productVO.getCategoryVO().getSvcCtgrTypCd())) {
                productVO.getBaseVO().setPrdTypCd(ProductConstDef.PRD_TYP_CD_RENTAL);
            }
            // 디지털컨텐츠 카테고리 인경우 상품의 PRD_TYP_CD 를 무조건 '12'으로 설정
            else if (GroupCodeUtil.equalsDtlsCd(productVO.getCategoryVO().getPrdTypCd(), PrdTypCd.DIGITAL_CATEGORY)) {
                productVO.getBaseVO().setPrdTypCd(ProductConstDef.PRD_TYP_CD_DIGITAL);
            }
            // 소셜네트워크상품 카테고리 인경우 상품의 PRD_TYP_CD 를 무조건 '17'으로 설정
            else if (GroupCodeUtil.equalsDtlsCd(productVO.getCategoryVO().getPrdTypCd(), PrdTypCd.SOCIAL_INTANGIBLE)) {
                productVO.getBaseVO().setPrdTypCd(ProductConstDef.PRD_TYP_CD_SOCIAL_NET);
            }
            //18:소셜유형상품
            else if (GroupCodeUtil.equalsDtlsCd(productVO.getCategoryVO().getPrdTypCd(), PrdTypCd.SOCIAL_TANGIBLE)) {
                productVO.getBaseVO().setPrdTypCd(ProductConstDef.PRD_TYP_CD_SOCIAL_TANGIBLE);
            }
            // 티켓상품 카테고리인 경우 상품의 PRD_TYP_CD 를 무조건 '22'으로 설정
            else if (GroupCodeUtil.equalsDtlsCd(productVO.getCategoryVO().getPrdTypCd(), PrdTypCd.BP_TICKET)) {
                productVO.getBaseVO().setPrdTypCd(ProductConstDef.PRD_TYP_CD_TICKET);
            }
            else {	// API
                if (StringUtil.isEmpty(productVO.getBaseVO().getPrdTypCd())) {
                    productVO.getBaseVO().setPrdTypCd(ProductConstDef.PRD_TYP_CD_NORMAL);
                }
            }
        }

        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getDlvClf(), DlvClfCdTypes.SELLER_CONSIGNMENT_DELIVERY)
            && !GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getPrdTypCd(), PrdTypCd.NORMAL))
        {
            throw new ProductException("셀러위탁배송으로 설정불가능한 카테고리입니다.");
        }

        // 서비스 쿠폰 상품 등록/수정 불가
        if (ProductConstDef.PRD_TYP_CD_SVC_CUPN.equals(productVO.getCategoryVO().getPrdTypCd())
            || ProductConstDef.PRD_TYP_CD_SVC_CUPN.equals(productVO.getBaseVO().getPrdTypCd())){
            throw new ProductException("서비스쿠폰 상품은 상품등록/수정이 불가합니다.");
        }
        if(GroupCodeUtil.isContainsDtlsCd(productVO.getBaseVO().getPrdTypCd(), PrdTypCd.PIN_11ST_SEND, PrdTypCd.PIN_ZERO_COST)){
            productVO.setTown(true);
        }

        if(productVO.isTown()){
            if(!(ProductConstDef.EXT_CTGR_ATTR_CD.contains(productVO.getCategoryVO().getDispCtgrAttrCd()))){
                throw new ProductException("상품의 현재 카테고리에 등록할 수 없는 상품유형입니다.");
            }
            if((GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getBsnDealClf(),BsnDealClf.COMMISSION)
                    && GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getPrdTypCd(), PrdTypCd.PIN_ZERO_COST))
                || GroupCodeUtil.isContainsDtlsCd(productVO.getBaseVO().getBsnDealClf(), BsnDealClf.DIRECT_PURCHASE, BsnDealClf.SPECIFIC_PURCHASE)){
                throw new ProductException("현재 셀러가 선택 할 수 없는 서비스 상품입니다.");
            }
            if(!GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelMthdCd(), SelMthdCd.FIXED)){
                throw new ProductException("현재 셀러가 선택 할 수 없는 서비스 상품입니다.");
            }
        }
    }


    private void convertOrdCnStepCd(ProductVO productVO) throws ProductException{

        if(ProductConstDef.PRD_STAT_CD_ORDER.equals(productVO.getBaseVO().getPrdStatCd())){
            productVO.getBaseVO().setOrdCnStepCd(ProductConstDef.ORD_CN_STEP_CD_11);
        }

        if(!GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getBsnDealClf(), BsnDealClf.COMMISSION)){
            productVO.getBaseVO().setOrdCnStepCd(ProductConstDef.ORD_CN_STEP_CD_04);
        }
        else if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getDlvClf(), DlvClfCdTypes.ELEVENST_ABROAD_DELIVERY)){
            productVO.getBaseVO().setOrdCnStepCd(ProductConstDef.ORD_CN_STEP_CD_05);

            if(ProductConstDef.ABRD_IN_CD_FREE.equals(productVO.getBaseVO().getAbrdInCd())){// 무료픽업
                productVO.getBaseVO().setAvgDeliDays(6);
            }else if(ProductConstDef.ABRD_IN_CD_SELLER.equals(productVO.getBaseVO().getAbrdInCd())){// 판매자발송
                productVO.getBaseVO().setAvgDeliDays(8);
            }else if(ProductConstDef.ABRD_IN_CD_AGENCY.equals(productVO.getBaseVO().getAbrdInCd())){// 구매대행
                productVO.getBaseVO().setAvgDeliDays(15);
            }
        }

        if(customerService.isFrSeller(productVO.getSelMnbdNo())){
            productVO.getBaseVO().setOrdCnStepCd(ProductConstDef.ORD_CN_STEP_CD_07);
            if (productVO.getPriceVO().getFrSelPrc() == 0){
                throw new ProductException("현지판매가는 반드시 입력하셔야 합니다.");
            }
        }

        if(StringUtil.nvl(propMgr.get1hourTimeProperty("PART_CLAIM_NOT_SELLER")).indexOf("|"+productVO.getSelMnbdNo()+"|") > -1){
            productVO.getBaseVO().setOrdCnStepCd(ProductConstDef.ORD_CN_STEP_CD_08);
        }

        if(StringUtil.nvl(propMgr.get1hourTimeProperty("PARTIAL_CANCELLATION_SELLER")).indexOf("|"+productVO.getSelMnbdNo()+"|") > -1){
            productVO.getBaseVO().setOrdCnStepCd(ProductConstDef.ORD_CN_STEP_CD_06);
        }


    }

    public List<HashMap<String, String>> getSearchTagName(String searchTag) throws ProductException{
        List<HashMap<String, String>> result = null;
        if(StringUtil.isNotEmpty(searchTag)){
            result = productMapper.getSearchTagName(searchTag);
        }
        return result;
    }

    public List<ProductSearchVO> getSearchProductList(ProductSearchVO paramVO) {
        return productMapper.getSearchProductList(paramVO);
    }

    public void insertProductLdngTmInfo(ProductLdngTmVO productLdngTmBO) throws Exception {

        final String logStr = "MEM_NO=" + productLdngTmBO.getMemNo() + ", PROC_TYP=" + productLdngTmBO.getProcTyp() + ", BROW_TYP=" + productLdngTmBO.getBrowTyp() + ", LDNG_TM=" + productLdngTmBO.getLdngTm();

        try {
            AsyncDB<ProductLdngTmVO> aDb = new AsyncDBImpl<ProductLdngTmVO>();
            aDb.insert(productLdngTmBO);
        } catch (Exception e) {
            // 상품 품질지표 등록 실패 하더라도 서비스에는 영향 없도록 하기 위함.
            log.error("insertProductLdngTmInfo Error : "+ logStr + " : " + e.getMessage(), e);
        }
    }

    @Deprecated
    public void setReRegistrationInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if("Y".equals(productVO.getReNewYn()) || "Y".equals(productVO.getPrdCopyYn())){
            productVO.setReRegPrdNo(preProductVO.getPrdNo());
        }
    }

    public void convertRmaterialInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getRmaterialTypCd(), RmaterialTypCd.PROCESSED_PRODUCT)){
//            ProductRmaterialVO productRmaterialVO = productVO.getProductRmaterialVO();
            //TODO CYB 원재료 좀더 파볼것
        }else{
            return;
        }
    }

    /**
     * 상품 리스팅광고 List 조회
     *
     * @param prdNo
     * @return List
     * @throws ProductException
     */
    public List<ProductItemVO> getProductItemList(long prdNo) throws ProductException {
        List dataList = null;

        try {
            dataList = productMapper.getProductItemList(prdNo);
        } catch (Exception ex) {
            throw new ProductException("상품 리스팅광고 List 조회 오류", ex);
        }

        return dataList;
    }

    /**
     * 추가구성상품 등록/수정페이지 조회(상품번호로 조회) - SO/BO에서 사용
     *
     * @param prdNo
     * @return List
     * @throws ProductException
     */
    public List<ProductAddCompositionVO> getAddProductInformationListByPrdNo(long prdNo) throws ProductException {
        List<ProductAddCompositionVO> dataList = null;
        try {
            ProductAddCompositionVO productAddCompositionVO = new ProductAddCompositionVO();
            productAddCompositionVO.setMainPrdNo(prdNo);
            dataList = productMapper.getAddProductInformationList(productAddCompositionVO);
        } catch (Exception ex) {
            throw new ProductException("추가구성상품 등록/수정페이지 조회 오류", ex);
        }
        return dataList;
    }

    /**
     * 이벤트 상품 설정여부 조회
     */
    public String getEventProductYn(long prdNo) throws ProductException{
        String evtPrdYn = "N";

        try {
            int retCnt = productMapper.getEventProductYn(prdNo);
            if(retCnt > 0){
                evtPrdYn = "Y";
            }
        } catch (Exception ex) {
            throw new ProductException("이벤트 설정 상품 여부 조회 오류", ex);
        }

        return evtPrdYn;
    }

    public RentalVO getRntlInfo(long prdNo) throws ProductException {
        try {
            return productMapper.getRntlInfo(prdNo);
        } catch (Exception ex) {
            throw new ProductException("스페셜 렌탈 상품정보 조회 오류", ex);
        }
    }

    /**
     * 상품 제휴 할인율 조회 (임직원)
     *
     * @param productVO
     * @return ProductBO
     * @throws ProductException
     */
    public ProductVO getPdPartnerPrdRt(ProductVO productVO) throws ProductException {
        try {
            return productMapper.getPdPartnerPrdRt(productVO);
        } catch (Exception ex) {
            throw new ProductException("상품 제휴 할인율 조회 (임직원) 조회", ex);
        }
    }

    /**
     * 국가 리스트 조회
     * @param hashMap
     * @return
     * @throws ProductException
     */
    public List<HashMap<String, Object>> getNationList(HashMap<String, Object> hashMap) throws ProductException {
        try {
            return productMapper.getNationList(hashMap);
        }catch (Exception ex) {
            throw new ProductException("국가 리스트 조회 오류", ex);
        }
    }

    public List<ProductTagVO> getProductTagList(long prdNo) throws ProductException {
        try {
            return productMapper.getProductTagList(prdNo);
        } catch (Exception ex) {
            throw new ProductException("상품 태그정보 조회 오류", ex);
        }
    }

    /* 매입상품 승인일자 조회 */
    public String getApproveDt(long prdNo) throws ProductException {
        try {
            return StringUtil.nvl(productMapper.getApproveDt(prdNo));
        } catch (Exception ex) {
            throw new ProductException("상품 정보 조회 오류", ex);
        }
    }

    public List<ProductMobileFeeVO> getProductMobileFee(long prdNo) throws ProductException {
        try {
            return productMapper.getProductMobileFee(prdNo);
        } catch (Exception e) {
            throw new ProductException("상품 휴대폰 요금제 정보 조회 오류", e);
        }
    }


    private void convertProductSvcAreaCd(ProductVO preProductVO, ProductVO productVO) throws ProductException {

        // 넘어온 상품의 전시코드 파라미터 값과 카테고리의 전시코드 파라미터 값이 동일 하지않을 경우
        if (ProductConstDef.B2B_AREA_CD.equals(productVO.getBaseVO().getSvcAreaCd()) && !ProductConstDef.B2B_AREA_CD.equals(productVO.getCategoryVO().getSvcAreaCd())){
            throw new ProductException("해당 카테고리는 B2B상품으로 등록할 수 없습니다.");
        }
        // 현재 카테고리가 B2B 카테고리 일 경우 상품 전시코드 데이터 설정
        else if (ProductConstDef.B2B_AREA_CD.equals(productVO.getCategoryVO().getSvcAreaCd())){
            productVO.getBaseVO().setOmPrdYn("N");
            productVO.getBaseVO().setSvcAreaCd(ProductConstDef.B2B_AREA_CD);
            productVO.setDispAreaDmlCd("I");
        }else if (ProductConstDef.MOBILE_AREA_CD.equals(productVO.getBaseVO().getSvcAreaCd())){
            productVO.getBaseVO().setOmPrdYn("N");
            if(ProductConstDef.PRD_TYP_CD_LIFE_TOWN_VISIT.equals(productVO.getBaseVO().getPrdTypCd())
                && "Y".equals(productVO.getBaseVO().getAllBranchUseYn())) {
                productVO.setDispAreaDmlCd("I"); // I로 세팅되야 pd_prd_svc_area 에 insert된다
            }
        }else if(productVO.isTown()){
            productVO.getBaseVO().setOmPrdYn("");
            productVO.getBaseVO().setSvcAreaCd("");
            productVO.setDispAreaDmlCd("I"); // I로 세팅되야 pd_prd_svc_area 에 insert된다
        }else if(ProductConstDef.PRD_TYP_CD_LIFE_TOWN_VISIT.equals(productVO.getBaseVO().getPrdTypCd())) {
            productVO.getBaseVO().setOmPrdYn("N");
            productVO.getBaseVO().setSvcAreaCd("");
            if("Y".equals(productVO.getBaseVO().getAllBranchUseYn())){
                productVO.setDispAreaDmlCd("I"); // I로 세팅되야 pd_prd_svc_area 에 insert된다
            }
        }else{
            productVO.getBaseVO().setOmPrdYn("Y");
            productVO.getBaseVO().setSvcAreaCd("");
            productVO.setDispAreaDmlCd("");
        }

        // 상품 수정
        if (productVO.isUpdate()){
            // 이전 상품이 일반 상품이였다가 전시코드를 가지는 상품이 될 경우
            if ("".equals(preProductVO.getBaseVO().getSvcAreaCd())
                && !"".equals(productVO.getBaseVO().getSvcAreaCd())){
                productVO.setDispAreaDmlCd("I");
            }
            // 이전 상품이 전시코드를 가지는 상품이였다가 일반 상품이 될 경우
            else if (!"".equals(preProductVO.getBaseVO().getSvcAreaCd()) && "".equals(productVO.getBaseVO().getSvcAreaCd())){
                productVO.setDispAreaDmlCd("D");
            }
            // 이전 상품이 전시코드를 가지는 상품이였는데 코드가 변경된 경우
            else if (!productVO.getBaseVO().getSvcAreaCd().equals(preProductVO.getBaseVO().getSvcAreaCd())){
                productVO.setDispAreaDmlCd("U");
            }
            // 재구매 제한 기간 변경된 경우
            else if(productVO.isTown() && productVO.getBaseVO().getTownSelLmtDy() != preProductVO.getBaseVO().getTownSelLmtDy()) {
                productVO.setDispAreaDmlCd("U");
            }
            // 변경이 없을 경우
            else{
                productVO.setDispAreaDmlCd("");
            }
        }
    }

    public String getKeywordPrdFilter(ProductVO productVO, String content) throws ProductException{
        String filterKeyword = "";

        //대카테고리가 해외쇼핑카테고리의 경우-[상품명]일때만 금칙어 별도사용 - 2013.05.27 by sjshim [SR-26927]
        List<KeywdVO> addList = null;
        if(8286 == productVO.getCategoryVO().getLctgrNo()){
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("dispCtgrNo", productVO.getDispCtgrNo());
            param.put("content", content);
            addList = productMapper.getKeywdPrdExtFilterList(param);
            if(false == addList.isEmpty() && addList.size() > 0){
                for(int i = 0 ; i < addList.size() ;i++ ){
                    KeywdVO tns = addList.get(i);
                    filterKeyword = filterKeyword+","+tns.getKeywdNm();
                }
            }
        }

        addList = this.getKeywdPrdFilterList(productVO.getDispCtgrNo());

        if(addList != null && addList.size() > 0){
            for(KeywdVO compareBO : addList){
                if(compareBO.getDispCtgrNo() == 0 ||
                    compareBO.getDispCtgrNo() == productVO.getCategoryVO().getLctgrNo() ||
                    compareBO.getDispCtgrNo() == productVO.getCategoryVO().getMctgrNo() ||
                    compareBO.getDispCtgrNo() == productVO.getCategoryVO().getSctgrNo() ){

                    if(content.contains(compareBO.getKeywdNm())){
                        filterKeyword = filterKeyword+","+compareBO.getKeywdNm();
                    }
                }
            }
        }

        if(filterKeyword.length() > 1){
            filterKeyword = filterKeyword.substring(1);
        }
        return filterKeyword;
    }

    private List<KeywdVO> getKeywdPrdFilterList(long dispCtgrNo){
        List<KeywdVO> list = (List<KeywdVO>)objectCache.getObject("getKeywdPrdFilterList_"+dispCtgrNo);
        if(list == null){
            list = productMapper.getKeywdPrdFilterList(dispCtgrNo);
            objectCache.setObject("getKeywdPrdFilterList_"+dispCtgrNo, list, 86400);
        }
        return list;
    }

    /**
     * 상품번호로 상품정보 및 회원정보 조회
     */
    public ProductVO getProductMember(long prdNo) throws ProductException {
        return productMapper.getProductMember(prdNo);
    }

    private void mergeProductTagInfo(ProductVO productVO) throws ProductException{
        if(productVO.isUpdate()){
            productMapper.deleteProductTag(productVO.getPrdNo());
        }
        if("Y".equals(productVO.getBaseVO().getProductTagUseYn())){
            if(productVO.getProductTagVOList() != null && productVO.getProductTagVOList().size() > 0){
                for(ProductTagVO productTagVO : productVO.getProductTagVOList()){
                    productTagVO.setPrdNo(productVO.getPrdNo());
                    productTagVO.setCreateNo(productVO.getUpdateNo());
                    if(StringUtil.isNotEmpty(productTagVO.getTagNm())) productMapper.insertProductTag(productTagVO);
                }
            }
        }
    }

    public boolean checkSupportMobileEdit(long prdNo) throws ProductException{
        long count = productMapper.checkSupportMobileEdit(prdNo);
        if(count > 0){
            return true;
        }else{
            return false;
        }
    }

}
