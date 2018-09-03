package com.elevenst.terroir.product.registration.wms.mapper;

import com.elevenst.terroir.product.registration.wms.entity.PdPrdWmsInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WmsMapperTest {

    int insertPdPrdWmsInfoHist(PdPrdWmsInfo param);

    int updateEmployeeNo(PdPrdWmsInfo param);

    int insertBasisStockAttribute(PdPrdWmsInfo basisStockVO);


    /*
    ------------------------- 검증을 위한 테스트 MAPPER -------------------------
     */

    PdPrdWmsInfo getPdPrdWmsInfoTEST(long prdNo);
    int deletePdPrdWmsInfoTEST(long prdNo);
    int deletePdPrdWmsInfoHistTEST(long prdNo);
}

