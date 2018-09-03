package com.elevenst.terroir.product.registration.catalog.mapper;

import com.elevenst.terroir.product.registration.catalog.entity.PdPrdModel;
import com.elevenst.terroir.product.registration.catalog.vo.CatalogRecmVO;
import com.elevenst.terroir.product.registration.catalog.vo.ItgModelInfoVO;
import com.elevenst.terroir.product.registration.catalog.vo.PrdModelVO;
import com.elevenst.terroir.product.registration.option.vo.ProductStockVO;
import com.elevenst.terroir.product.registration.product.entity.PdPrd;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface CatalogMapper {

    int getCtlgModelNoCheck(HashMap hashMap);
    void deleteProductModel(ItgModelInfoVO itgModelInfoVO);
    void mergeProductCatalogMatch(ProductStockVO productStockVO);
    void mergeProductModelInfo(CatalogRecmVO catalogRecmVO);
    List getProductCtlgModelInfo(HashMap hashMap);
    void insertProductCatalogMatchInfo(PdPrd pdPrd);
    PrdModelVO getCatalogDetailInfo(PrdModelVO prdModelVO);
    PrdModelVO getProductModel(PrdModelVO prdModelVO);
    void deletePdPrdModel(PrdModelVO prdModelVO);
    CatalogRecmVO getProductStockCheckInfo(CatalogRecmVO recmVO);
    void mergePdPrdModel(PdPrdModel pdPrdModel);
}
