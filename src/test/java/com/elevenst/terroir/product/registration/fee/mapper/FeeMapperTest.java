package com.elevenst.terroir.product.registration.fee.mapper;

import com.elevenst.terroir.product.registration.fee.entity.SeFeeItm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface FeeMapperTest {
    int setSeFeeEndDt(SeFeeItm bo);


    //---------------- 테스트용----------------
    int insertSeeFeeItm(SeFeeItm value);

    int deleteSetFeeTest(long feeitmno);
}
