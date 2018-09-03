package com.elevenst.terroir.product.registration.mobile.mapper;

import com.elevenst.terroir.product.registration.mobile.entity.PdPrdMobileFee;
import com.elevenst.terroir.product.registration.mobile.vo.MobileFeeSearchParamVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MobileMapperTest {

    int deleteProductMobileFee(PdPrdMobileFee param);

    int insertProductMobileFee(PdPrdMobileFee productMobileFeeBO);

    int insertProductMobileFeeHistory(PdPrdMobileFee histParm);

    List<PdPrdMobileFee> searchMobileFee(MobileFeeSearchParamVO param);


    //------------------- TEST용-------------------

    PdPrdMobileFee getPdPrdMobileFeeTest(long prdNo1);

    int deleteProductMobileFeeHistTest(PdPrdMobileFee pdPrdMobileFee);
}
