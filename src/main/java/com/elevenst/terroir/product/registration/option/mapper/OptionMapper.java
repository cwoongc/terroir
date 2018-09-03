package com.elevenst.terroir.product.registration.option.mapper;

import com.elevenst.terroir.product.registration.option.entity.PdOptDtlImage;
import com.elevenst.terroir.product.registration.option.entity.PdOptItem;
import com.elevenst.terroir.product.registration.option.entity.PdOptItemHist;
import com.elevenst.terroir.product.registration.option.entity.PdOptValue;
import com.elevenst.terroir.product.registration.option.entity.PdPrdOptCalc;
import com.elevenst.terroir.product.registration.option.entity.PdPrdOptCalcHist;
import com.elevenst.terroir.product.registration.option.entity.PdStock;
import com.elevenst.terroir.product.registration.option.entity.PdStockHist;
import com.elevenst.terroir.product.registration.option.entity.PdStockPtnrInfo;
import com.elevenst.terroir.product.registration.option.entity.PdStockSetInfo;
import com.elevenst.terroir.product.registration.option.entity.PdStockSetInfoHist;
import com.elevenst.terroir.product.registration.option.vo.ProductOptMartComboVO;
import com.elevenst.terroir.product.registration.option.vo.StandardOptionGroupMappingVO;
import com.elevenst.terroir.product.registration.option.vo.StandardOptionItemVO;
import com.elevenst.terroir.product.registration.option.vo.StandardOptionValueVO;
import com.elevenst.terroir.product.registration.wms.entity.WmDstStck;
import com.elevenst.terroir.product.registration.wms.entity.WmDstStckHist;
import com.elevenst.terroir.product.registration.option.vo.BasisStockVO;
import com.elevenst.terroir.product.registration.option.vo.OptionVO;
import com.elevenst.terroir.product.registration.option.vo.PdOptItemVO;
import com.elevenst.terroir.product.registration.option.vo.PdOptValueVO;
import com.elevenst.terroir.product.registration.option.vo.ProductOptLimitVO;
import com.elevenst.terroir.product.registration.option.vo.ProductStockSetInfoVO;
import com.elevenst.terroir.product.registration.option.vo.ProductStockVO;
import com.elevenst.terroir.product.registration.option.vo.PurchasePrcAprvVO;
import com.elevenst.terroir.product.registration.price.entity.PdPrdPrcAprv;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.wms.entity.PdPrdWmsInfo;
import com.elevenst.terroir.product.registration.wms.entity.PdPrdWmsInfoHist;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface OptionMapper {
    long getProductStockSeq();
    long getProductStckCnt(long prdNo);
    long getBundleProductStckCnt(long prdNo);
    long isExistOption(long prdNo);
    long isExistAddPrduct(long prdNo);
    long getProductOptCnt(ProductVO productVO);
    long optStockValidCheck(long prdNo);
    int getPrdStockImageCount(long prdNo);
    int getSelectOptionCnt(long prdNo);
    int getOptClfCdCnt(HashMap<String, String> map);
    int getOptionClfTypeCnt(HashMap<String, String> map);
    int getPartnerStockInfoExistCnt(ProductStockVO productStockVO);
    int getPdStockExistCnt(HashMap hashMap);
    int getPdStockAddPrcCnt(ProductVO productVO);
    int getPdEventStockAddPrcCnt(ProductVO productVO);
    int getPdStockAddPrcCnt1Over(long prdNo);
    int getPdEventStockAddPrcCnt1Over(ProductVO productVO);

    List<PdOptItemVO> getProductOptItemLst(PdOptItemVO pdOptItemVO);
    List<PdOptItem> getProductOptItemLstAddOptClfCd(PdOptItem pdOptItem);
    List<PdOptValue> getProductOptMartValueList(HashMap<String, Object> hmap);
    List<PdOptValue> getProductOptMixValueList(HashMap<String, Object> hmap);
    List<ProductStockVO> getPdStockPtnrInfoByPrdNo(ProductStockVO productStockVO);
    List<PdPrdOptCalc> getOptCalcList(PdPrdOptCalc pdPrdOptCalc);
    List<PdOptDtlImage> getProductOptImageList(PdOptDtlImage pdOptDtlImage);
    List<OptionVO> getOptValueList(PdOptItemVO pdOptItemVO);
    List<ProductStockVO> getBarcodeDuplicateList(ProductStockVO productStockVO);
    List<ProductStockVO> getOptionValueGroupInfo(ProductStockVO productStockVO);
    List<ProductStockVO> getExistProductStockLst(ProductStockVO productStockVO);
    List<ProductStockVO> getProductOptValueSubCntList(ProductStockVO productStockVO);
    List<Long> getSameDatePriceAprv(PurchasePrcAprvVO purchasePrcAprvVO);
    List<PdOptValueVO> getProductOptValueLstNew(HashMap hashMap);
    List<OptionVO> getOptItemValueNmByteList(PdOptItemVO pdOptItemVO);
    List getOptionWghtHist(HashMap hashMap);
    List<PdOptItemVO> getProductFrOptItemLst(PdOptItemVO pdOptItemVO);
    List<PdOptValueVO> getProductFrOptValueLst(HashMap hashMap);
    List<ProductOptMartComboVO> getProductOptMartComboList(HashMap hashMap);

    ProductVO getProduct4OptionPop(ProductVO productVO);
    ProductOptLimitVO getProductOptLimitForSellerSet(ProductOptLimitVO productOptLimitVO);
    ProductOptLimitVO getProductOptLimitForBasic(ProductOptLimitVO productOptLimitVO);
    ProductStockSetInfoVO getPrdStockSetInfo(ProductStockVO productStockVO);
    List<ProductStockVO> getFrProductStockList(ProductStockVO productStockVO);
    List<ProductStockVO> getBundleProductStockLst(ProductStockVO productStockVO);
    List<ProductStockVO> getOrignalPrdStockSetInfoList(ProductStockVO productStockVO);
    List<ProductStockVO> getProductStockLst(ProductStockVO productStockVO);
    List<StandardOptionItemVO> getOptItemListAtStd(StandardOptionGroupMappingVO standardOptionGroupMappingVO);
    List<StandardOptionValueVO> getOptValueListAtStd(StandardOptionGroupMappingVO standardOptionGroupMappingVO);

    String getBundleProductOptCd(long prdNo);
    String getProductOptCd(long prdNo);
    String getProductSelStatChangeStatReturn(long prdNo, String selBgnDy, String townSellerYN);
    int getPdStockSetInfoCnt(long prdNo);

    void insertProductStock(PdStock pdStock);
    void insertPartnerStockInfo(PdStockPtnrInfo pdStockPtnrInfo);
    void insertHistDeleteStock(PdStockHist pdStockHist);
    void insertHistDeleteAllStockSetInfo(PdStockSetInfoHist pdStockSetInfoHist);
    void insertProductOptCalc(PdPrdOptCalc pdPrdOptCalc);
    void insertProductOptCalcHist(PdPrdOptCalcHist pdPrdOptCalcHist);
    void insertStockSetInfoHist(PdStockSetInfoHist pdStockSetInfoHist);
    void insertProductOptDtlImage(PdOptDtlImage pdOptDtlImage);
    void insertPreOptionPurchaseInfo(Map<String, Object> hashMap);
    void insertProductStockHist(ProductStockVO productStockVO);
    void insertProductOptCalcHistCopy(PdPrdOptCalcHist pdPrdOptCalcHist);
    void insertPurchasePriceAprv(PdPrdPrcAprv pdPrdPrcAprv);
    void insertProductOptItem(PdOptItem pdOptItem);
    void insertProductOptValueInfoExist(PdOptValue pdOptValue);
    void insertWmDstStckHist(WmDstStckHist wmDstStckHist);
    void insertBasisStockAttribute(PdPrdWmsInfo pdPrdWmsInfo);
    void insertPdPrdWmsInfoHist(PdPrdWmsInfoHist pdPrdWmsInfoHist);
    void insertFrProductOptItem(PdOptItem pdOptItem);
    void insertProductOptItemHist(PdOptItemHist pdOptItemHist);
    void insertFrProductStock(PdStock pdStock);
    void insertProductPdStockHist(PdStockHist pdStockHist);
    int insertProductOptValues(PdOptValue pdOptValue);
    int insertNewProductOptItemHist(PdOptItemHist pdOptItemHist);

    void deleteAllStockSetInfo(PdStockSetInfo pdStockSetInfo);
    void deleteProductStocks(PdStock pdStock);
    void deletePartnerStockInfo(PdStockPtnrInfo pdStockPtnrInfo);
    void deleteProductOptInfo(PdOptValue pdOptValue);
    void deleteProductOptCalc(PdPrdOptCalc pdPrdOptCalc);
    void deleteProductOptDtlImage(PdOptDtlImage pdOptDtlImage);
    void deleteNewProductOptItem(PdOptItem pdOptItem);
    void deleteWmDstStck(WmDstStck wmDstStck);
    int deleteProductOptValues(PdOptValue pdOptValue);
    int deleteOptionPurchaseInfo(PdPrdPrcAprv pdPrdPrcAprv);

    void updateProductOptCalc(PdPrdOptCalc pdPrdOptCalc);
    void updateOptionPurchaseInfo(PdPrdPrcAprv pdPrdPrcAprv);
    void updateProductStocks(ProductStockVO productStockVO);
    void updateNewProductOptItem(PdOptItem pdOptItem);
    void updateProductDgstExtNm(PdOptDtlImage pdOptDtlImage);
    void updateProductStockPurchaseInfo(PdStock pdStock);
    int updateProductOptionPurchaseInfo(PdStock pdStock);

    void processStockSetInfo_I(PdStockSetInfo pdStockSetInfo);
    void processStockSetInfo_D(PdStockSetInfo PdStockSetInfo);
    void processStockSetInfo_U(PdStockSetInfo pdStockSetInfo);

    void mergeWmDstStck(BasisStockVO basisStockVO);
    void mergePartnerStockInfo(ProductStockVO productStockVO);

    // COPY
    void insertProductOptItemCopy(ProductVO productVO);
    void insertProductOptItemHistCopy(ProductVO productVO);
    void insertProductOptValueCopy(ProductVO productVO);
    void insertProductStocksCopy(ProductVO productVO);
    void insertProductStocksHistCopy(ProductVO productVO);
    void insertProductStocksPtnrInfoCopy(ProductVO productVO);
    void productStocksPtnrInfoCopy(ProductVO productVO);
    void insertProductOptCalcCopy(ProductVO productVO);
    //void insertProductCalcHistCopy(ProductVO productVO);
    void insertOptionPurchaseInfoCopy(ProductVO productVO);

    int getSingleOptionCnt(ProductStockVO setProductStockVO);
    long getOptStockValidCheck(long prdNo);
    void insertServiceStckQtyHist(long stockNo);
    void updateCutCatalogMatch(PdStock pdStock);

    List<ProductStockVO> getPrdStckOptNmList(ProductStockVO productStockVO);
}
