package com.elevenst.terroir.product.registration.coordi_prd.mapper;

import com.elevenst.terroir.product.registration.coordi_prd.entity.PdPrdCoordi;
import com.elevenst.terroir.product.registration.coordi_prd.vo.DisplayCoordiProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CoordiProductServiceTestMapper {

    int insertCoordiProduct(PdPrdCoordi pdPrdCoordi);
    int deleteCoordiProduct(@Param("prdNo") long prdNo);
    List<Long> getCoordiPrdNoList(@Param("prdNo") long prdNo);
    List<DisplayCoordiProductVO> getDisplayCoordiProductList(@Param("prdNo") long prdNo);

    List<PdPrdCoordi> getCoordiProductList(@Param("prdNo") long prdNo);


}
