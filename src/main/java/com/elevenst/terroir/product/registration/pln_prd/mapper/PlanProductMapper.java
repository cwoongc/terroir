package com.elevenst.terroir.product.registration.pln_prd.mapper;

import com.elevenst.terroir.product.registration.pln_prd.entity.PdPlnPrd;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlanProductMapper {
    int insertPlanProduct(PdPlnPrd pdPlnPrd);

    int updatePlanProduct(PdPlnPrd pdPlnPrd);


    int deletePlanProduct(@Param("prdNo") long prdNo);

    PdPlnPrd getPlanProduct(@Param("prdNo") long prdNo);
}
