package com.elevenst.terroir.product.registration.template.mapper;

import com.elevenst.terroir.product.registration.delivery.vo.ProductSellerBasiDeliveryCostVO;
import com.elevenst.terroir.product.registration.template.entity.*;
import com.elevenst.terroir.product.registration.template.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TemplateMapper {

    long getProductInformationTemplateSeq();

    int insertProductInformationTemplate(PdPrdInfoTmplt seq);

    int insertSellInformation(PdSelInfo pdSelInfo);

    int insertOrdBasicDeliCost(PdPrdLstOrdBasiDlvCst deliBo);

    int insertProductDesc(PdPrdLstDesc descBo);

    int insertInventoryProductAddress(PdPrdLstTgowPlcExchRtngd bo);

    int insertInventoryAddProductComposition(PdPrdLstAddPrdComp pdPrdLstAddPrdComp);

    List getProductInformationTemplateAllList(PrdInfoTmplVO paramVO);

    PrdInfoTmplVO getProductInformationTemplate(ProductInformationTemplateVO templateVO);

    SellInformationVO getSellInformationTemplateDetail(ProductInformationTemplateVO templateVO);

    List getOrderBasiDeliverCostVOList(InventoryOrderBasicDeliverCostVO inventoryOrderBasicDeliverCostVO);

    List getProductDescVOList(InventoryProductDescVO inventoryProductDescVO);

    String getAddrLocation(InventoryProductAddressVO paramVO);

    InventoryProductAddressVO getInventoryProductAddress01List(InventoryProductAddressVO paramVO);

    InventoryProductAddressVO getInventoryProductAddress02List(InventoryProductAddressVO paramVO);

    String getAddrBasiDlvCstTxt(ProductSellerBasiDeliveryCostVO addrBasiDlvCstVO);
}
