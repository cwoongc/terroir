package com.elevenst.terroir.product.registration.shop.mapper;

import com.elevenst.terroir.product.registration.shop.entity.PdTownShopBranchComp;
import com.elevenst.terroir.product.registration.shop.vo.ShopBranchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopServiceTestMapper {

    int insertProductShopBranch(PdTownShopBranchComp pdTownShopBranchComp);
    List<PdTownShopBranchComp> getProductShopBranchListByPrdNo(long prdNo);
    int deleteProductShopBranchByPrdNo(long prdNo);


    int insertShopBranch(@Param("shopNo") long shopNo, @Param("shopBranchNo") long shopBranchNo, @Param("shopBranchTypCd") String shopBranchTypCd, @Param("mailNo") String mailNo);
    List<Long> getShopBranchNoList(long shopNo);
    List<ShopBranchVO> getShopBranchList(long shopNo);


    int deleteShopBranchByShopNo(long shopNo);
}
