package com.elevenst.terroir.product.registration.deal.service;

import com.elevenst.common.process.RegistrationInfoConverter;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.deal.exception.DealException;
import com.elevenst.terroir.product.registration.deal.mapper.DealMapper;
import com.elevenst.terroir.product.registration.deal.message.DealServiceExceptionMessage;
import com.elevenst.terroir.product.registration.deal.validate.DealValidate;
import com.elevenst.terroir.product.registration.deal.vo.EventRqstPrdInfoVO;
import com.elevenst.terroir.product.registration.deal.vo.ProductEvtVO;
import com.elevenst.terroir.product.registration.deal.vo.ShockingDealVO;
import com.elevenst.terroir.product.registration.option.entity.PdEventRqstCalc;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DealServiceImpl implements RegistrationInfoConverter<ProductVO>{

    @Autowired
    private DealMapper dealMapper;

    @Autowired
    private DealValidate dealValidate;

    public ProductEvtVO getAplPrdInfo(ProductEvtVO productEvtVO) throws ProductException{
        try {
            ProductEvtVO result = dealMapper.getAplPrdInfo(productEvtVO);
            if(result == null) throw new ProductException("유효한 신청 상품 정보가 아닙니다.");

            result.getProductVO().setPrdNo(StringUtil.getLong(result.getEventPricePegVO().getPrdNo()));
            result.getEventPricePegVO().setEventNo(Long.toString(result.getEventNo()));

            return result;
        } catch (Exception ex) {
            throw new ProductException("쇼킹딜 신청 상품 조회 오류: "+ex.getMessage());
        }
    }

/*
    /**
     * 쇼킹딜 정보 객체 재사용하기 위해 체크 후 Return
     * @param dataBox
     * @param prdNo
     * @return ProductBO
     * @throws TmallException
     */
//    public ProductVO getShockingDealPrd(ShockingDealVO shockingDealVO, long prdNo) throws ProductException {
//        ProductVO shockVO = null;
//
//        if( prdNo <= 0 ) {
//            return null;
//        }
//
//        try {
//            String dataKey = "shockingDealPrdObj";
//            String checkKey = "shockingDealPrdCheckYn";
//
//            if( shockingDealVO != null && "Y".equals(shockingDealVO.getShockingDealPrdCheckYn()) ) {
//                shockVO = shockingDealVO.getShockingDealPrdObj();
//            } else {
//                shockVO = dealMapper.getShockingDealPrd(prdNo);
//
//                if( dataBox != null ) {
//                    dataBox.put(dataKey, shockBO);
//                    dataBox.put(checkKey, "Y");
//                }
//            }
//        } catch(Exception e) {
//            throw new ProductException("쇼킹딜 정보 객체 재사용하기 위해 체크 후 Return 오류", e);
//        }
//
//        return shockVO;
//    }

    public ProductVO getShockingDealPrd(long prdNo) throws ProductException {
        try {
             return dealMapper.getShockingDealPrd(prdNo);
        }catch (ProductException e) {
            throw new ProductException("[product.getDealPrdList] 딜 상품목록 조회중 오류", e);
        }
    }

    public ProductVO getPrdPrcCupnDlvInfo(ProductVO productVO) {
        ProductVO resultVO = null;
        try{
            resultVO = dealMapper.evtGetPrdPrcCupnDlvInfo(productVO);
        }catch(ProductException pex){
            throw new ProductException("쇼킹딜 상품의 가격정보 수정가능여부 체크시 오류",pex);
        }catch (Exception ex) {
            throw new ProductException(ex.getMessage());
        }
        return resultVO;
    }

    public ProductEvtVO getEventPrdInfoWithAprv(EventRqstPrdInfoVO eventRqstPrdInfoVO) throws DealException {
        try{
            return dealMapper.getEventPrdInfoWithAprv(eventRqstPrdInfoVO);
        }catch (Exception e) {
            throw new DealException(e.getMessage());
        }
    }

    /**
     * 계산형 옵션 정보를 조회한다.
     * @param pdEventRqstCalc
     * @return
     * @throws DealException
     */
    public List<PdEventRqstCalc> getEvtOptCalcList(PdEventRqstCalc pdEventRqstCalc) throws DealException {
        List<PdEventRqstCalc> productOptCalcList = new ArrayList<PdEventRqstCalc>();
        try {
            productOptCalcList = dealMapper.getEvtOptCalcList(pdEventRqstCalc);
        } catch (Exception ex) {
            throw new DealException(DealServiceExceptionMessage.GET_SHOCKING_DEAL_CALC_OPTION_ERROR, ex);
        }
        return productOptCalcList;
    }

    public ProductEvtVO getShockDealStarted(long prdNo) throws DealException {
        try {
            return dealMapper.getShockDealStarted(prdNo);
        } catch (Exception e) {
            throw new DealException("쇼킹딜 진행중 정보 조회중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) {
        dealValidate.checkDealInfo(preProductVO, productVO);
    }
}
