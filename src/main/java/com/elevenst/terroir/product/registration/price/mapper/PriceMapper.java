package com.elevenst.terroir.product.registration.price.mapper;

import com.elevenst.terroir.product.registration.option.entity.PdStock;
import com.elevenst.terroir.product.registration.price.entity.PdPrdPluDsc;
import com.elevenst.terroir.product.registration.price.entity.PdPrdPrc;
import com.elevenst.terroir.product.registration.price.entity.PdPrdPrcAprv;
import com.elevenst.terroir.product.registration.price.vo.PlusDscVO;
import com.elevenst.terroir.product.registration.price.vo.PriceVO;
import com.elevenst.terroir.product.registration.product.vo.EnuriProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface PriceMapper {
    String getProductDiscountDataByPrdNo(HashMap hashMap);

    PriceVO getProductPrice(long prdNo);

    long getPdPrdPrcNo();

    void insertPdPrdPrc(PdPrdPrc pdPrdPrc);

    void insertPdPrdPrcHist(PdPrdPrc pdPrdPrc);

    void insertPdPrdPrcAprv(PdPrdPrcAprv pdPrdPrcAprv);

    List<PlusDscVO> getPdPrdPluDscInfoList(long prdNo);

    long getPdPrdPluDscNo();

    void insertPdPrdPluDsc(PdPrdPluDsc pdPrdPluDsc);

    void insertPdPrdPluDscHist(PdPrdPluDsc pdPrdPluDsc);

    void updatePdPrdPrc(PdPrdPrc pdPrdPrc);

    void updatePdPrdPrcAprv(PdPrdPrcAprv pdPrdPrcAprv);

    void updatePdPrdPrcAprvDate(PdPrdPrc pdPrdPrc);

//    void updatePdPrdPluDsc(PdPrdPluDsc pdPrdPluDsc);

    long deletePdPrdPluDscUsingPrdNo(PdPrdPluDsc pdPrdPluDsc);

    void updateProductOptionPurchaseInfo(PdStock pdStock);

    PriceVO getCurrentDirectPrdCoupon(long prdNo);
    EnuriProductVO getEnuriProductInfo(long prdNo);
}
