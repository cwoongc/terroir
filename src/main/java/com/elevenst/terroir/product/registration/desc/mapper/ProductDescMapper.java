package com.elevenst.terroir.product.registration.desc.mapper;

import com.elevenst.terroir.product.registration.desc.entity.PdPrdDesc;
import com.elevenst.terroir.product.registration.desc.vo.ProductDescVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductDescMapper {
    PdPrdDesc getProductDetailCont(@Param("prdNo") long prdNo);

    PdPrdDesc getProductDesc(@Param("prdNo") long prdNo, @Param("prdDescTypCd") String prdDescTypCd);

    List<PdPrdDesc> getProductDescList(@Param("prdNo") long prdNo);

    int deleteProductDesc(@Param("prdNo") long prdNo, @Param("prdDescTypCd") String prdDescTypCd);

    long getProductDescNo();

    void insertProductDesc(PdPrdDesc pdPrdDesc);

    void insertProductDescReReg(ProductDescVO productDescVO);

    List<ProductDescVO> getProductDescContForSmtOption(@Param("prdNo") long prdNo);

    List<ProductDescVO> getProductDescContForSmtOptionMW(@Param("prdNo") long prdNo);


    int deleteProductDescContInfo(ProductDescVO productDescVO);

    int updateProductDetail(ProductDescVO productDescVO);

    ProductVO getSellerProductDetailDesc(ProductVO productVO);



}
