package com.elevenst.terroir.product.registration.category.mapper;

import com.elevenst.terroir.product.registration.category.entity.DpGnrlDisp;
import com.elevenst.terroir.product.registration.category.entity.PdObjTypProp;
import com.elevenst.terroir.product.registration.category.vo.*;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface CategoryMapper {

    void insertDpGnrlDisp(DpGnrlDisp dpGnrlDisp);

    public List<CategoryVO> getDisplayCategoryParentList(long dispCtgrNo);

    CategoryVO getServiceDisplayCategoryInfo(long dispCtgrNo);

    public CategoryVO getSimpleCategoryInfo(long dispCtgrNo);

    public String canRegistProductTypeToCategory(ProductVO productVO);

    String getFreshCategoryYn(ProductVO productVO);

    boolean existProductInCategory(long dispCtgrNo);

    void updateProductGeneralDisplay(DpGnrlDispVO dpGnrlDispVO);

    int getDispCtgrSellerAuthCnt(ProductVO productVO);
    ProductVO getCategoryOtherFixing(long prdNo);

    int getProductExptCategory(HashMap map);

    void updateDpGnrlDisp(DpGnrlDisp dpGnrlDisp);

    List<DpDispCtgrMetaVO> getDisplayCategoryChildList(HashMap<String, Object> map);
    List getServiceDisplayCategoryProduct(CategoryVO categoryVO);
    List<DpDispObjVO> getHelpDpDispObjList(DpDispObjVO dpDispObjVO);
    CategoryVO getDisplayCategoryInfoNoneUseCheck(long dispCtgrNo);
    String getDispCtgrSellerAuthYn(CategoryVO categoryVO);
    int getDispCtgrSellerAuth(CategoryVO categoryVO);
    List getDpDispCtgrList(CategoryVO categoryVO);
    List<CategoryVO> getRecentProductGeneralDisplay(CategoryVO categoryVO);
    String getProductMedicalPermissionYn(long dispCtgrNo);
    List<DpDispCtgrMetaVO> getMetaDispCtgrList(HashMap hashMap);
    List<CategoryRecommendationVO> getCategoryRecommendations(HashMap hashMap);
    List<Long> getSellerAuthorizedCtgrNos(HashMap hashMap);
    List<CategoryVO> getServiceDisplayCategoryLeafList(CategoryVO categoryVO);
    List<CategoryVO> getCategorySearchForDispCtgrNm(String dispCtgrNm);

    int insertCategoryRecommendations(PdObjTypProp pdObjTypProp);

    List<InfoTypeCategoryVO> getPrdInfoTypeCategoryList();

    String isPrdInfoTypeCategory(long infoTypeCtgrNo);

    CategoryVO getDpDispCtgrListInfo(long dispCtgrNo);
}
