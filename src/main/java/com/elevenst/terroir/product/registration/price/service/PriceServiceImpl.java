package com.elevenst.terroir.product.registration.price.service;

import com.elevenst.common.process.RegistrationInfoConverter;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.common.vine.dto.ProductPriceV1;
import com.elevenst.terroir.product.registration.common.vine.service.VineCallServiceImpl;
import com.elevenst.terroir.product.registration.option.entity.PdStock;
import com.elevenst.terroir.product.registration.price.entity.PdPrdPluDsc;
import com.elevenst.terroir.product.registration.price.entity.PdPrdPrc;
import com.elevenst.terroir.product.registration.price.entity.PdPrdPrcAprv;
import com.elevenst.terroir.product.registration.price.mapper.PriceMapper;
import com.elevenst.terroir.product.registration.price.validate.PriceValidate;
import com.elevenst.terroir.product.registration.price.vo.PlusDscVO;
import com.elevenst.terroir.product.registration.price.vo.PriceVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.validate.ProductValidate;
import com.elevenst.terroir.product.registration.product.vo.EnuriProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Slf4j
@Service
public class PriceServiceImpl implements RegistrationInfoConverter<ProductVO>{

    @Autowired
    PriceValidate priceValidate;

    @Autowired
    PriceMapper priceMapper;

    @Autowired
    ProductValidate productValidate;

    @Autowired
    VineCallServiceImpl vineCallService;


    public void setPriceInfoProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        this.checkPriceInfo(preProductVO, productVO);
    }

    private void checkPriceInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        priceValidate.checkSelPrc(preProductVO, productVO);
        priceValidate.checkOcb(preProductVO, productVO);
        priceValidate.checkMileage(preProductVO, productVO);
        priceValidate.checkHopeShp(preProductVO, productVO);
    }

    public PdPrdPrc setPdPrdPrc(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        PdPrdPrc pdPrdPrc = new PdPrdPrc();

        copyProperties(productVO.getPriceVO(), pdPrdPrc);

        pdPrdPrc.setPrdNo(productVO.getPrdNo());
        pdPrdPrc.setCreateNo(productVO.getCreateNo());
        pdPrdPrc.setUpdateNo(productVO.getUpdateNo());


        if(productVO.isUpdate()){
            pdPrdPrc.setPrdPrcNo(preProductVO.getPriceVO().getPrdPrcNo());
        }else{
            pdPrdPrc.setPrdPrcNo(priceMapper.getPdPrdPrcNo());
        }

        pdPrdPrc.setAplBgnDy(productVO.getBaseVO().getSelBgnDy());
        pdPrdPrc.setAplEndDy(productVO.getBaseVO().getSelEndDy());

        if("Y".equals(productVO.getCategoryVO().getMobileYn())){
            // 휴대폰 가격표시제 시행을 위한 가격 표기 추가
            pdPrdPrc.setMaktPrc(productVO.getPriceVO().getMobilePhoneInstallment());
            pdPrdPrc.setPrmtDscAmt(productVO.getPriceVO().getPrmtDscAmt());
            pdPrdPrc.setMpContractTypCd(productVO.getPriceVO().getMpContractTypCd());
        }

        if(priceValidate.isCanChangePrice(productVO)){
            pdPrdPrc.setPrmtDscAmt(productVO.getPriceVO().getPrmtDscAmt());
            pdPrdPrc.setMpContractTypCd(productVO.getPriceVO().getMpContractTypCd());
        }

        pdPrdPrc.setUseYn("Y");
        pdPrdPrc.setHistAplBgnDt(productVO.getHistAplBgnDt());
        return pdPrdPrc;
    }

    public void insertPriceInfoProcess(ProductVO productVO) throws ProductException{
        this.insertPdPrdPrcNHist(productVO);
        this.insertPdPrdPrcAprv(productVO);

        this.insertPrdPluDscUsingCustomerBenefitList(productVO);
    }

    private void insertPdPrdPrcNHist(ProductVO productVO) throws ProductException{
        PdPrdPrc pdPrdPrc = this.setPdPrdPrc(null, productVO);
        priceMapper.insertPdPrdPrc(pdPrdPrc);
        priceMapper.insertPdPrdPrcHist(pdPrdPrc);
    }

    private void updatePdPrdPrcNHist(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        PdPrdPrc pdPrdPrc = this.setPdPrdPrc(preProductVO, productVO);
        if("Y".equals(productVO.getMobile1WonYn())){
            pdPrdPrc.setMaktPrc(productVO.getPriceVO().getMobilePhoneInstallment());
        }
        priceMapper.updatePdPrdPrc(pdPrdPrc);
        priceMapper.insertPdPrdPrcHist(pdPrdPrc);


    }

    private void insertPdPrdPrcAprv(ProductVO productVO) throws ProductException{

        if(productVO.isPurchaseType()
            && ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(productVO.getBaseVO().getSelStatCd())){
            priceMapper.insertPdPrdPrcAprv(this.setPdPrdPrcAprv(productVO));
        }
    }

    private PdPrdPrcAprv setPdPrdPrcAprv(ProductVO productVO) throws ProductException{
        PdPrdPrcAprv pdPrdPrcAprv = new PdPrdPrcAprv();
        pdPrdPrcAprv.setPrdNo(productVO.getPrdNo());

        pdPrdPrcAprv.setAplyBgnDy(productVO.getPriceVO().getAplBgnDy());
        pdPrdPrcAprv.setAprvStatCd("01");
        pdPrdPrcAprv.setSelPrc(productVO.getPriceVO().getSelPrc());
        pdPrdPrcAprv.setMaktPrc(productVO.getPriceVO().getMaktPrc());
        pdPrdPrcAprv.setPuchPrc(productVO.getPriceVO().getPuchPrc());
        pdPrdPrcAprv.setMrgnAmt(productVO.getPriceVO().getMrgnAmt());
        pdPrdPrcAprv.setMrgnRt(productVO.getPriceVO().getMrgnRt());
        pdPrdPrcAprv.setRequestNo(productVO.getUpdateNo());
        pdPrdPrcAprv.setUpdateNo(productVO.getUpdateNo());
        if(ProductConstDef.PARTNEROFFICE_TYPE.equals(productVO.getChannel())){
            pdPrdPrcAprv.setRequestMnbdClfCd("01");
        }else if(ProductConstDef.BACKOFFICE_TYPE.equals(productVO.getChannel())){
            pdPrdPrcAprv.setRequestMnbdClfCd("02");
        }
        pdPrdPrcAprv.setSelMnbdNo(productVO.getSelMnbdNo());

        return pdPrdPrcAprv;
    }

    /**
     * 상품의 상품 할인 정보 조회 - 상품번호(와 판매가)로 할인정보 조회
     * RETURN : (판매가|SO쿠폰할인표시|MO쿠폰할인표시|최종쿠폰적용가(선할인포함)|서비스이용료|순매출액|순매출률|선할인율|OK캐쉬백적립율|포인트적립율
     *           |SO할인금액|SO지급포인트금액|SO지급칩금액|모바일카테고리여부|셀러수수료체계동의여부(MO쿠폰에대해1:9비용부담동의여부)
     *
     * @param prdNo
     *      , selPrc(상품가격이 0 인경우는 상품의 판매가를 조회해서 데이터 리턴함)
     * @return String
     * @throws ProductException
     */
    public String getProductDiscountDataByPrdNo(long prdNo, long selPrc) throws ProductException{

        try {
            HashMap hashMap = new HashMap();
            hashMap.put("prdNo", prdNo);
            hashMap.put("selPrc", selPrc);
            return priceMapper.getProductDiscountDataByPrdNo(hashMap);
        } catch (ProductException ex) {
            throw new ProductException("상품번호로 상품의 상품 할인 정보 조회 오류", ex);
        } catch (Exception ex) {
            throw new ProductException("상품번호로 상품의 상품 할인 정보 조회 오류", ex);
        }
    }

    /**
     * 상품가격정보 조회
     */
    public PriceVO getProductPrice(long prdNo) throws ProductException {
        try {
            return priceMapper.getProductPrice(prdNo);
        } catch (Exception ex) {
            // Exception 발생
            throw new ProductException(ex);
        }
    }


    public void deleteCustomerPrdPluDsc(PdPrdPluDsc pdPrdPluDsc) {
        priceMapper.deletePdPrdPluDscUsingPrdNo(pdPrdPluDsc);
    }


    public void updatePriceInfoProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if(priceValidate.isCanChangePrice(productVO)){
            this.updateProductPrice(preProductVO, productVO);
            this.updateProductPriceAprv(productVO);
        }else{
            this.updateProductPriceDate(preProductVO, productVO);
        }
        this.insertPrdPluDscUsingCustomerBenefitList(productVO);
    }

    private void updateProductPrice(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        String selStatCd = productVO.getBaseVO().getSelStatCd();
        if (ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(selStatCd)
            || ProductConstDef.PRD_SEL_STAT_CD_BEFORE_DISPLAY.equals(selStatCd)
            || ProductConstDef.PRD_SEL_STAT_CD_SALE.equals(selStatCd)
            || ProductConstDef.PRD_SEL_STAT_CD_SOLDOUT.equals(selStatCd)
            || ProductConstDef.PRD_SEL_STAT_CD_STOP_DISPLAY.equals(selStatCd)
            || ProductConstDef.PRD_AUCT_SEL_STAT_CD_SALE.equals(selStatCd)
            || "Y".equals(productVO.getBaseVO().getTemplateYn()))
        {
            this.updatePdPrdPrcNHist(preProductVO, productVO);
        }

        if("Y".equals(productVO.getReNewYn())){
            if ( ProductConstDef.PRD_SEL_STAT_CD_CLOSE_SALE.equals(selStatCd)
                || ProductConstDef.PRD_SEL_STAT_CD_STOP_SALE.equals(selStatCd)
                || ProductConstDef.PRD_AUCT_SEL_STAT_CD_SOLD.equals(selStatCd)
                || ProductConstDef.PRD_AUCT_SEL_STAT_CD_DONT_SELL.equals(selStatCd))
            {
                this.updatePdPrdPrcNHist(preProductVO, productVO);
            }
        }
    }

    private void updateProductPriceDate(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        PdPrdPrc pdPrdPrc = this.setPdPrdPrc(preProductVO, productVO);

        priceMapper.updatePdPrdPrcAprvDate(pdPrdPrc);
    }

    private void updateProductPriceAprv(ProductVO productVO) throws ProductException{
        if(productVO.isPurchaseType()){
            PdPrdPrcAprv pdPrdPrcAprv = this.setPdPrdPrcAprv(productVO);
            pdPrdPrcAprv.setAprvObjCd("01");
            priceMapper.updatePdPrdPrcAprv(pdPrdPrcAprv);

            pdPrdPrcAprv.setAprvObjCd("02");
            pdPrdPrcAprv.setSvcUsePolicy(productVO.getPriceVO().getMrgnPolicyCd());
            priceMapper.updatePdPrdPrcAprv(pdPrdPrcAprv);

            PdStock pdStock = new PdStock();
            copyProperties(pdPrdPrcAprv, pdStock);
            priceMapper.updateProductOptionPurchaseInfo(pdStock);
        }
    }

    private void insertPrdPluDscUsingCustomerBenefitList(ProductVO productVO) {

        List<PlusDscVO> plusDscVOList = productVO.getPriceVO().getPlusDscVOList();

        if(productVO.isUpdate()){
            PdPrdPluDsc deletePdPrdPluDsc = new PdPrdPluDsc();
            deletePdPrdPluDsc.setPrdNo(productVO.getPrdNo());
            deleteCustomerPrdPluDsc(deletePdPrdPluDsc);
        }

        if(plusDscVOList != null && plusDscVOList.size() > 0){

            for(PlusDscVO plusDscVO : plusDscVOList) {
                PdPrdPluDsc insertPdPrdPluDsc = new PdPrdPluDsc();
                BeanUtils.copyProperties(plusDscVO, insertPdPrdPluDsc);

                insertPdPrdPluDsc.setPrdNo(productVO.getPrdNo());
                insertPdPrdPluDsc.setPluTypCd("01");
                if("Y".equals(plusDscVO.getPluDscAplYn())){
                    insertPdPrdPluDsc.setPluAplBgnDy(StringUtil.setFullDateString(plusDscVO.getPluAplBgnDy(),true));
                    insertPdPrdPluDsc.setPluAplEndDy(StringUtil.setFullDateString(plusDscVO.getPluAplEndDy(),false));
                }else{
                    insertPdPrdPluDsc.setPluDscAplYn("N");
                    insertPdPrdPluDsc.setPluAplBgnDy(productVO.getHistAplBgnDt());
                    insertPdPrdPluDsc.setPluAplEndDy("99991231235959");
                }
                insertPdPrdPluDsc.setPluDscCd(plusDscVO.getPluDscCd());
                insertPdPrdPluDsc.setPluDscBasis(plusDscVO.getPluDscBasis());
                insertPdPrdPluDsc.setPluDscWyCd(plusDscVO.getPluDscWyCd());
                insertPdPrdPluDsc.setPluDscRt(plusDscVO.getPluDscRt());
                insertPdPrdPluDsc.setPluDscAmt(plusDscVO.getPluDscAmt());
                insertPdPrdPluDsc.setUseYn("Y");
                insertPdPrdPluDsc.setSelMnbdNo(productVO.getSelMnbdNo());
                insertPdPrdPluDsc.setCreateNo(productVO.getUpdateNo());
                insertPdPrdPluDsc.setUpdateNo(productVO.getUpdateNo());

                insertPdPrdPluDsc.setPluDscNo(priceMapper.getPdPrdPluDscNo());
                priceMapper.insertPdPrdPluDsc(insertPdPrdPluDsc);
                priceMapper.insertPdPrdPluDscHist(insertPdPrdPluDsc);
            }
        }
    }

    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) {
        this.convertPriceInfo(preProductVO, productVO);
        this.convertCouponInfo(preProductVO, productVO);
        this.convertPlusDscInfo(preProductVO, productVO);
    }

    private void convertPriceInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if(productVO.isUpdate()){
            preProductVO.setPriceVO(this.getProductPrice(productVO.getPrdNo()));
        }
    }

    private void setSoCupnAmt(ProductVO productVO) throws ProductException{
        long soCupnAmt = 0;

        if("Y".equals(productVO.getPriceVO().getCuponCheckYn())){
            if(ProductConstDef.CUPN_DSC_MTHD_CD_WON.equals(productVO.getPriceVO().getCupnDscMthdCd())){
                soCupnAmt = productVO.getPriceVO().getDscAmt();
            }else if(ProductConstDef.CUPN_DSC_MTHD_CD_PERCNT.equals(productVO.getPriceVO().getCupnDscMthdCd())){
                soCupnAmt = productVO.getPriceVO().getSelPrc() * Math.round(productVO.getPriceVO().getDscRt() * 100) / 10000;
                soCupnAmt = ((long)(soCupnAmt/10))*10; //원단위 절사(=>10원 단위)
            }
        }
        productVO.getPriceVO().setSoCupnAmt(soCupnAmt);
    }

    private void convertPlusDscInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if(productVO.getPriceVO().isUsePlusDsc()){
            priceValidate.checkPlusDsc(productVO);
        }
    }

    private void convertCouponDtlInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if (productVO.isUpdate()){

            PriceVO priceVO = priceMapper.getCurrentDirectPrdCoupon(productVO.getPrdNo());

            if(priceVO != null){
                if(preProductVO.getPriceVO() == null) preProductVO.setPriceVO(new PriceVO());
                preProductVO.getPriceVO().setCupnDscMthdCd(priceVO.getCupnDscMthdCd());
                preProductVO.getPriceVO().setDscRt(priceVO.getDscRt());
                preProductVO.getPriceVO().setDscAmt(priceVO.getDscAmt());
                preProductVO.getPriceVO().setIssCnBgnDt(priceVO.getIssCnBgnDt());
                preProductVO.getPriceVO().setIssCnEndDt(priceVO.getIssCnEndDt());
            }
        }
    }

    private void convertCouponInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if(productVO.isUpdate()){

            this.convertCouponDtlInfo(preProductVO, productVO);


        }
        long selPrc = productVO.getPriceVO().getSelPrc();
        long dscAmt = productVO.getPriceVO().getDscAmt();
        double dscRt = productVO.getPriceVO().getDscRt();

        if("Y".equals(productVO.getPriceVO().getCuponCheckYn())){

            if(ProductConstDef.CUPN_DSC_MTHD_CD_WON.equals(productVO.getPriceVO().getCupnDscMthdCd())){
                dscAmt = productVO.getPriceVO().getDscAmt();
                dscRt = (long) Math.floor(dscAmt / selPrc);
            }else{
                dscRt = productVO.getPriceVO().getDscRt();
                dscAmt = (long) (Math.floor(selPrc * dscRt * 0.001) * 10);
            }

            productVO.getPriceVO().setIssCnBgnDt(productVO.getPriceVO().getIssCnBgnDt());
            productVO.getPriceVO().setIssCnEndDt(productVO.getPriceVO().getIssCnEndDt());
            productVO.getPriceVO().setDscAmt(dscAmt);
            productVO.getPriceVO().setDscRt(dscRt);
            productVO.getPriceVO().setSoCupnAmt(dscAmt);

            priceValidate.checkDirectCoupon(productVO);

            if("Y".equals(productVO.getPriceVO().getCupnUseLmtDyYn())){
                if(productVO.getPriceVO().getIssCnBgnDt().length() >= 8 && productVO.getPriceVO().getIssCnEndDt().length() >= 8){
                    productVO.getPriceVO().setIssCnBgnDt(StringUtil.setFullDateString(productVO.getPriceVO().getIssCnBgnDt(), true));
                    productVO.getPriceVO().setIssCnEndDt(StringUtil.setFullDateString(productVO.getPriceVO().getIssCnEndDt(), false));
                }else{
                    throw new ProductException("즉시할인쿠폰 지급기간을 입력해주십시오.");
                }
            }
        }

        if("N".equals(productVO.getPriceVO().getCuponCheckYn())){
            productVO.getPriceVO().setCupnDscMthdCd("");
            productVO.getPriceVO().setIssCnBgnDt("");
            productVO.getPriceVO().setIssCnEndDt("");
            productVO.getPriceVO().setDscAmt(0);
            productVO.getPriceVO().setDscRt(0);
            productVO.getPriceVO().setSoCupnAmt(0);
        }

    }

    /**
     * 즉시할인 쿠폰 정보를 가져온다.
     *
     * @param prdNo 상품번호
     * @return EnuriProductVO
     */
    public PriceVO getCurrentDirectPrdCoupon(long prdNo) throws ProductException {
        try {
            PriceVO priceVO = priceMapper.getCurrentDirectPrdCoupon(prdNo);
            if(priceVO != null) {
                priceVO.setCuponCheckYn("N");
                if(StringUtil.isNotEmpty(priceVO.getCupnDscMthdCd())) {
                    priceVO.setCuponCheckYn("Y");
                }
                priceVO.setCupnUseLmtDyYn("N");
                if(!priceVO.getIssCnEndDt().startsWith("9999")) {
                    priceVO.setCupnUseLmtDyYn("Y");
                }
            }
            return priceVO;
        } catch(Exception e) {
            throw new ProductException("즉시할인 쿠폰 정보 조회 오류", e);
        }
    }

    /**
     * 에누리 최저가 정보 조회
     *
     * @param prdNo 상품번호
     * @return EnuriProductVO
     */
    public EnuriProductVO getEnuriProductInfo(long prdNo) throws ProductException {
        EnuriProductVO enuriProductVO = null;
        try {
            enuriProductVO = priceMapper.getEnuriProductInfo(prdNo);
        } catch(Exception e) {
            throw new ProductException("에누리 최저가 정보 조회 오류", e);
        }

        return enuriProductVO;
    }

    public void insertSellerDirectCoupon(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if(productVO.getPriceVO().getDscAmt() > 0 || productVO.getPriceVO().getDscRt() > 0){
            this.callVineSellerDirectCoupon(productVO);
        }else{
            if(productVO.isUpdate()){
                if(!preProductVO.getPriceVO().getCupnDscMthdCd().equals(productVO.getPriceVO().getCupnDscMthdCd())){
                    this.callVineSellerDirectCoupon(productVO);
                }
            }
        }
    }

    private void callVineSellerDirectCoupon(ProductVO productVO) throws ProductException{
        ProductPriceV1 productPriceV1 = new ProductPriceV1();
        productPriceV1.setCupnDscMthdCd(productVO.getPriceVO().getCupnDscMthdCd());

        productPriceV1.setDscAmt(productVO.getPriceVO().getDscAmt());
        productPriceV1.setDscRt(productVO.getPriceVO().getDscRt());
        productPriceV1.setCupnIssStartDy(productVO.getPriceVO().getIssCnBgnDt());
        productPriceV1.setCupnIssEndDy(productVO.getPriceVO().getIssCnEndDt());
        vineCallService.insertSellerDirectCoupon(productVO, productPriceV1);
    }

    public void getPdPrdPluDscInfoList(ProductVO productVO) throws ProductException{
        List<PlusDscVO> plusDscVOList = priceMapper.getPdPrdPluDscInfoList(productVO.getPrdNo());
        if(plusDscVOList != null || plusDscVOList.size() > 0){
            productVO.getPriceVO().setUsePlusDsc(true);
            productVO.getPriceVO().setPlusDscVOList(plusDscVOList);
        }
    }
}
