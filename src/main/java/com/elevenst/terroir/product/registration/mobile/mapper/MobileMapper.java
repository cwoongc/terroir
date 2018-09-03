package com.elevenst.terroir.product.registration.mobile.mapper;

import com.elevenst.terroir.product.registration.mobile.entity.PdPrdMobileFee;
import com.elevenst.terroir.product.registration.mobile.vo.MobileFeeSearchParamVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface MobileMapper {
    int deleteProductMobileFee(PdPrdMobileFee param);

    int insertProductMobileFee(PdPrdMobileFee productMobileFeeBO);

    int insertProductMobileFeeHistory(PdPrdMobileFee histParm);

    List<PdPrdMobileFee> searchMobileFee(MobileFeeSearchParamVO param);
    List<HashMap> mobileGetGroupList(long dispCtgrNo);
    List<HashMap> getMobileFeeNameList(HashMap hashMap);
}
