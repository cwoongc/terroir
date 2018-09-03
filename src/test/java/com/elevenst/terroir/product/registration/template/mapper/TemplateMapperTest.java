package com.elevenst.terroir.product.registration.template.mapper;

import com.elevenst.terroir.product.registration.template.entity.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TemplateMapperTest {

    long getProductInformationTemplateSeq();

    int insertProductInformationTemplate(PdPrdInfoTmplt seq);

    int insertSellInformation(PdSelInfo pdSelInfo);

    int insertOrdBasicDeliCost(PdPrdLstOrdBasiDlvCst deliBo);

    int insertProductDesc(PdPrdLstDesc descBo);

    int insertInventoryProductAddress(PdPrdLstTgowPlcExchRtngd bo);

    int insertInventoryAddProductComposition(PdPrdLstAddPrdComp pdPrdLstAddPrdComp);


    /*-----------------------테스트용 추가 mapper---------------------------*/

    int deleteProductInformationTemplate(long temp_no);

    int deletePrdSelInfo(long temp_no);

    int deleteBasiDlvCst(long temp_no);

    int deleteLstDesc(long temp_no);

    int deleteExchRtngd(long temp_no);

    int deletePrdComp(long temp_no);
}
