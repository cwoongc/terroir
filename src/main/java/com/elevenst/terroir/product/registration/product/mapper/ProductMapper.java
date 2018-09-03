package com.elevenst.terroir.product.registration.product.mapper;

import com.elevenst.terroir.product.registration.etc.entity.PdPrdOthers;
import com.elevenst.terroir.product.registration.option.entity.PdStock;
import com.elevenst.terroir.product.registration.option.vo.ProductOptLimitVO;
import com.elevenst.terroir.product.registration.product.entity.*;
import com.elevenst.terroir.product.registration.product.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface ProductMapper {

    long getProductNo();

    long getProductReRegDtlsNo();

    ProductVO getProduct(long prdNo);

    void insertPdPrd(PdPrd pdPrd);

    void insertPdPrdHist(PdPrd pdPrd);

    void insertPdPrdReRegDtls(PdPrdReRegDtls pdPrdReRegDtls);

    int getSeriesProductYn(ProductSeriesVO productSeriesVO);
    long isExistFrPrd(long prdNo);
    ProductVO getOnlySearchPdPrd(long prdNo);

    List<ProductVO> getChangedStckQtyBundleProduct(long prdNo);
    List<ProductVO> getChangedStckQtyBundleProductByUnitStock(long stockNo);

    void updateProductOptionModify(PdPrd pdPrd);
    void insertProductHist(PdPrdHist pdPrdHist);
    void updateProductUpdateDt(ProductVO productVO);

    void insertPdPrdStatHist(PdPrdStatHist pdPrdStatHist);

    void updateProductStatus(ProductVO productVO);

    void updateAddProductStatus(ProductVO productVO);

    List<ProductVO> getProductList();

    void updateProduct(PdPrd pdPrd);

    void insertPdSvcPassPrd(PdSvcPassPrd pdSvcPassPrd);

    void insertPdSvcPassPrdHist(PdSvcPassPrd pdSvcPassPrd);

    /* 원재료 PD_PRD_RMATERIAL */
    List<ProductRmaterialVO> getProductRmaterialList(long prdNo);

    List<ProductRmaterialIngredVO> getProductRmaterialIngredList(PdPrdRmaterial pdPrdRmaterial);

    void insertProductRmaterial(PdPrdRmaterial pdPrdRmaterial);

    void insertProductRmaterialIngred(PdPrdRmaterialIngred pdPrdRmaterialIngred);

    void deleteAllProductRmaterial(long prdNo);

    void deleteAllProductRmaterialIngred(long prdNo);
    void deleteProductModel(HashMap hashMap);

    HashMap getPrdRsvSchdlYn(PdPrdOthers pdPrdOthers);

    long getProductSelMnbdNo(long prdNo);
    long isExistAdditionalProduct(long mainPrdNo);
    List<Map> getProductTypeInfoList(HashMap hashMap);
    ProductOptLimitVO getProductLimitInfo(ProductOptLimitVO productOptLimitVO);
    List<ProductNameMultiLangVO> getProductNameMultiLang(ProductNameMultiLangVO productNameMultiLangVO);
    List<HashMap> checkSohoPrd(HashMap hashMap);
    String getPrdOptTypCd(long prdNo);
    List getProductWghtHist(long prdNo);
    List<Map> getProductTypeInfoList2(HashMap hashMap);

    void insertPdPrdTempSave(PdPrdTempSave pdPrdTempSave);
    void updatePdPrdTempSave(PdPrdTempSave pdPrdTempSave);
    void deletePdPrdTempSave(PdPrdTempSave pdPrdTempSave);
    List<PdPrdTempSave> selectPdPrdTempSave(PdPrdTempSave pdPrdTempSave);

    //추가구성상품
    long insertPdAddPrdGrp(PdAddPrdGrp pdAddPrdGrp);
    long updatePdPrdAddCompositionProductSellStatus(ProductVO productVO);
    void insertPdAddPrdCompMapGtFROMPdAddPrdComp(ProductVO productVO);
    void insertPdAddPrdGrpUseSelectFrom(PdAddPrdGrp pdAddPrdGrp);
    void insertPdAddPrdCompFROMJoinTables(ProductVO productVO);
    void insertPdStockFROMJoinTables(ProductVO productVO);
    void insertPdPrdFROMJoinTables(ProductVO productVO);
    void insertPdPrdAddHistFROMJoinTabled(ProductVO productVO);

    List<ProductAddCompositionVO> getProductAddCompositionList(long prdNo);
    long deletePdAddPrdGrp(long mainPrdNo);
    long updateSetDelStatePdAddPrdComp(long mainPrdNo);


    long updateProductStockQty(PdStock pdStock);
    long updateAddPrdComp(PdAddPrdComp pdAddPrdComp);

    long insertPdPrdAdditionHist(PdPrdAdditionHist pdPrdAdditionHist);

//    long getBundleProductStckCnt(long prdNo); optionMapper에도 존재 해서 주석처리 곧 삭제 할 예정임
//    long getProductStckCnt(long prdNo);       optionMapper에도 존재 해서 주석처리 곧 삭제 할 예정임
    long updatePdPrdSelStatCd(PdPrd pdPrd);

    List<ProductAddCompositionVO> getProductAddCompositionListSortByDispPrrtRnk(ProductVO productVO);
    long updateProductComposition(ProductAddCompositionVO elem);

    long insertProductSummary(DpPrdContSummary dpPrdContSummary);

    long insertProductCoordi(ProductCoordiVO elem);
    long deleteProductCoordi(long prdNo);

    long getProductStockNo();
    void insertPdStock(PdStock insertPdStock);

    long getProductCompositionNo();
    void insertProductComposition(ProductAddCompositionVO productAddCompositionVO);

    long getAddCompPrdCount(long prdNo);

    void updateDpPrdContSummary(DpPrdContSummary dpPrdContSummary);

    void updateDpPrdSummary(DpPrdContSummary dpPrdContSummary);

    void updateTrDealEvl(DpPrdContSummary dpPrdContSummary);

    void updateDpContAplCtgr(DpPrdContSummary dpPrdContSummary);

    void updateDpSemiExprt(DpPrdContSummary dpPrdContSummary);

    void updatePdPrdSvcArea(PdPrdSvcArea pdPrdSvcArea);

    void insertPdPrdSvcArea(PdPrdSvcArea pdPrdSvcArea);

    void insertPdPrdAgency(PdPrdAgency pdPrdAgency);

    AgencyVO getProductAgency(long prdNo);

    long checkProductAgency(long prdNo);

    void disableProductAgency(long prdNo);

    void updatePdPrdAgency(PdPrdAgency pdPrdAgency);

    void insertPdPrdItm(PdPrdItm pdPrdItm);

    void insertPdStdPrdAttrComp(PdStdPrdAttrComp pdStdPrdAttrComp);

    void deletePdStdPrdAttrComp(long prdNo);

    void deleteProductDispArea(long prdNo);

    List<HashMap<String, String>> getSearchTagName(String searchTag);

    List<ProductSearchVO> getSearchProductList(ProductSearchVO paramVO);

    long getAddPrdNoInputTypeCount(ProductAddCompositionVO productAddCompositionVO);
    List<ProductItemVO> getProductItemList(long prdNo);
    List<ProductAddCompositionVO> getAddProductInformationList(ProductAddCompositionVO productAddCompositionVO);
    int getEventProductYn(long prdNo);
    RentalVO getRntlInfo(long prdNo);
    ProductVO getPdPartnerPrdRt(ProductVO paramBO);
    List<HashMap<String, Object>> getNationList(HashMap<String, Object> hashMap);
    List<ProductTagVO> getProductTagList(long prdNo);
    String getApproveDt(long prdNo);
    List<ProductMobileFeeVO> getProductMobileFee(long prdNo);

    List<KeywdVO> getKeywdPrdExtFilterList(HashMap<String, Object> hashMap);

    List<KeywdVO> getKeywdPrdFilterList(long dispCtgrNo);
    ProductVO getProductMember(long prdNo);

    void insertProductTag(ProductTagVO productTagVO);

    void deleteProductTag(long prdNo);

    long checkSupportMobileEdit(long prdNo);
}
