package com.elevenst.terroir.product.registration.coordi_prd.mapper;

import com.elevenst.terroir.product.registration.coordi_prd.vo.CoordiProductVO;
import com.elevenst.terroir.product.registration.coordi_prd.vo.DisplayCoordiProductVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import com.elevenst.terroir.product.registration.coordi_prd.entity.PdPrdCoordi;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CoordiProductMapper {

    int insertCoordiProduct(PdPrdCoordi pdPrdCoordi);
    int deleteCoordiProduct(@Param("prdNo") long prdNo);
    List<CoordiProductVO> getCoordiPrdNoList(@Param("prdNo") long prdNo); // 구) getMyCoordiPrdList
    List<DisplayCoordiProductVO> getDisplayCoordiProductList(@Param("prdNo") long prdNo);
    long getMyCoordiPrdCnt(ProductVO productVO);
}
