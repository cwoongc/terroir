package com.elevenst.terroir.product.registration.etc.mapper;

import com.elevenst.terroir.product.registration.etc.entity.PdPrdEtc;
import com.elevenst.terroir.product.registration.etc.entity.PdPrdOthers;
import com.elevenst.terroir.product.registration.etc.vo.PrdEtcVO;
import com.elevenst.terroir.product.registration.etc.vo.PrdOthersVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface EtcMapperTest {
    int insertProductEtc(PdPrdEtc productEtc);

    PrdEtcVO getProductEtc(long prdNo);

    int updateProductEtc(PdPrdEtc productEtc);

    HashMap getPrdRsvSchdlYn(PdPrdOthers pdPrdOthers);

    PrdOthersVO getBeefTraceInfo(long prdNo);

    int updateBeefTraceInfo(PdPrdOthers productVO);

    int insertBeefTraceInfo(PdPrdOthers productVO);

    PrdOthersVO getProdMedicalDeviceInfo(long prdNo);

    int updateProductOthersBuyDsblDyInfo(PdPrdOthers pdPrdOthers);

    int insertProductOthersBuyDsblDyInfo(PdPrdOthers pdPrdOthers);

    int updateProductEtcYes24(PdPrdEtc pdPrdEtc);

    int updateProductOthersPtnrInfo(PdPrdOthers prdOthers);

    int insertProductOthersPtnrInfo(PdPrdOthers prdOthers);

    int updateProdMedicalDeviceInfo(PdPrdOthers prdOthers);

    int insertProdMedicalDeviceInfo(PdPrdOthers prdOthers);

    int insertProductOthersForLife(PdPrdOthers prdOthers);

    //----------------------- For Test -----------------------

    int deleteProductEtcTest(long prdno);

    int deleteBeefTraceInfoTest(long prdNo02);

    int insertPrdRsvSchdlYnTest(PdPrdOthers testData);
}
