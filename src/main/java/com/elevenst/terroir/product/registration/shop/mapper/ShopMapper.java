package com.elevenst.terroir.product.registration.shop.mapper;

import com.elevenst.terroir.product.registration.shop.vo.ShopBranchVO;
import org.apache.ibatis.annotations.Mapper;
import com.elevenst.terroir.product.registration.shop.entity.PdTownShopBranchComp;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Component
public interface ShopMapper {

    int insertProductShopBranch(PdTownShopBranchComp pdTownShopBranchComp);
    List<Long> getShopBranchNoList(long shopNo);
    List<ShopBranchVO> getShopBranchList(long shopNo);
    int getPromotionCheck(ShopBranchVO shopBranchVO);
    ArrayList<ShopBranchVO> getTownShopBranchUsingList(long shopNo);
    ShopBranchVO getShopNo(long shopBranchNo);
    ArrayList<ShopBranchVO> getShopList(long selNo);
    ArrayList<ShopBranchVO> getCheckBranchList(ShopBranchVO shopBranchVO);
}
