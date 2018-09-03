package com.elevenst.terroir.product.registration.catalog.mapper;

import com.elevenst.terroir.product.registration.catalog.vo.CatalogAttrVO;
import com.elevenst.terroir.product.registration.catalog.vo.CatalogAttrValueVO;
import com.elevenst.terroir.product.registration.product.entity.PdStdPrdAttrComp;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface CatalogAttrMapper {

    List<CatalogAttrVO> getCtgrAttrList(HashMap param);

    List<CatalogAttrValueVO> getCtgrAttrValList(HashMap <String, Object> param );

    List<HashMap> getProductCtlgAttrValList(long prdNo);
    List<HashMap> getCtlgAttrValueList(long ctlgNo);

    long deleteProductCatalogAttr(long prdNo);
    long insertCatalogProductAttr(PdStdPrdAttrComp pdStdPrdAttrComp);

}
