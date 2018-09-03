package com.elevenst.terroir.product.registration.fee.mapper;

import com.elevenst.terroir.product.registration.fee.entity.SeFeeAplRng;
import com.elevenst.terroir.product.registration.fee.entity.SeFeeItm;
import com.elevenst.terroir.product.registration.fee.vo.BasketNointerestVO;
import com.elevenst.terroir.product.registration.fee.vo.SaleFeeVO;
import com.elevenst.terroir.product.registration.fee.vo.SeFeeItemVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FeeMapper {
    int setSeFeeEndDt(SeFeeItm bo);
    int getTotalCrdtCrdenFeeCnt(BasketNointerestVO basketNointerestVO);
    List<BasketNointerestVO> getCrdtCrdenFeeList(BasketNointerestVO basketNointerestVO);

    void updateSeFeeItmCtgr(SeFeeItm seFeeItm);

    List<SeFeeItm> getHasSaleFeeMedPeriodOverlap(SeFeeItm seFeeItm);

    void updateSeFeeItm(SeFeeItm seFeeItm);

    void insertSeFeeItmHist(SeFeeItm seFeeItm);

    void deleteSeFeeAplRng(SeFeeItm seFeeItm);

    void deleteSeFeeItm(SeFeeItm seFeeItm);

    List<SeFeeAplRng> getSeFeeAplRngByFeeItmNo(SeFeeItm seFeeItm);

    long getSeFeeItmSeq();
    long getBasicMarginRate(long memNo);

    void insertSeFeeItm(SeFeeItm seFeeItm);

    void insertSeFeeAplRng(SeFeeAplRng seFeeAplRng);

    String getProductSaleFee(SeFeeItemVO seFeeItemVO);
    SaleFeeVO getCategoryDefaultFee(ProductVO productVO);
}
