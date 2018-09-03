package com.elevenst.terroir.product.registration.benefit.mapper;

import com.elevenst.terroir.product.registration.benefit.entity.PdCustBnftInfo;
import com.elevenst.terroir.product.registration.benefit.entity.PdPrdGift;
import com.elevenst.terroir.product.registration.benefit.vo.CustomerBenefitVO;
import com.elevenst.terroir.product.registration.benefit.vo.ProductGiftVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BenefitMapper {

    ProductGiftVO getProductGift(long prdNo);

    PdPrdGift getProductGiftImage(long prdNo);

    void insertProductGift(PdPrdGift pdPrdGift);

    void insertProductGiftHist(long prdNo);

    void updateProductGiftHist(PdPrdGift pdPrdGift);

    void deleteProductGift(long prdNo);

    // 고객혜택정보
    CustomerBenefitVO getCurrentCustomerBenefitInfo(CustomerBenefitVO customerBenefitVO);
    long insertPdCustBnftInfo(PdCustBnftInfo pdCustBnftInfo);
    long updatePdCustBnftInfo(PdCustBnftInfo pdCustBnftInfo);
    long insertPdCustBnftInfoHist(PdCustBnftInfo pdCustBnftInfo);
    long deletePdCustBnftInfo(PdCustBnftInfo pdCustBnftInfo);
    long getSeqPdCustBnftInfoNEXTVAL();
    // -- 고객혜택정보

    CustomerBenefitVO getCustomerBenefit(long prdNo);
    List<CustomerBenefitVO> getCustomerPrdPluDscList(long prdNo);
}
