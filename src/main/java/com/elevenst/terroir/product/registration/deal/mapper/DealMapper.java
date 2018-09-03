package com.elevenst.terroir.product.registration.deal.mapper;

import com.elevenst.terroir.product.registration.deal.vo.EventRqstPrdInfoVO;
import com.elevenst.terroir.product.registration.deal.vo.ProductEvtVO;
import com.elevenst.terroir.product.registration.option.entity.PdEventRqstCalc;
import com.elevenst.terroir.product.registration.option.entity.PdOptValue;
import com.elevenst.terroir.product.registration.option.vo.PdOptItemVO;
import com.elevenst.terroir.product.registration.option.vo.PdOptValueVO;
import com.elevenst.terroir.product.registration.option.vo.ProductStockVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface DealMapper {
    ProductVO getShockingDealPrd(long prdNo);
    ProductVO evtGetPrdPrcCupnDlvInfo(ProductVO productVO);
    ProductEvtVO getAplPrdInfo(ProductEvtVO productEvtVO);
    ProductEvtVO getEventPrdInfoWithAprv(EventRqstPrdInfoVO eventRqstPrdInfoVO);
    ProductEvtVO getShockDealStarted(long prdNo);

    List<PdOptValue> getEventProductOptMixValueList(HashMap<String, Object> hmap);
    List<PdOptValue> getEventProductOptMixValueOrgList(HashMap<String, Object> hmap);
    List<PdOptItemVO> getEventProductOptItemLst(PdOptItemVO pdOptItemVO);
    List<PdOptItemVO> getEventProductOptItemOrgLst(PdOptItemVO pdOptItemVO);
    List<PdEventRqstCalc> getEvtOptCalcList(PdEventRqstCalc pdEventRqstCalc);
    List<ProductStockVO> getExistEventProductStockLst(ProductStockVO productStockVO);
    List<PdOptValueVO> getProductEventOptValueLstNew(HashMap hashMap);
    List<ProductStockVO> getOptionValueGroupInfoForDeal(ProductStockVO productStockVO);
    List<ProductStockVO> getProductOptValueSubCntListForDeal(ProductStockVO productStockVO);
    List<ProductStockVO> getEventProductStockLst(ProductStockVO productStockVO);
    List<ProductStockVO> getEventProductStockOrgLst(ProductStockVO productStockVO);

    int getOptionClfTypeCntForDeal(HashMap hashMap);
    int getPdStockExistCntForDeal(HashMap hashMap);
    long getEventProductStckCnt(ProductVO productVO);
    long getRollbackEventProductStckCnt(ProductVO productVO);

    void insertEventProductStocksCopy(ProductVO productVO);
    void insertProductOptCalcForDealCopy(ProductVO productVO);
    void insertProductOptCalcForDeal(PdEventRqstCalc pdEventRqstCalc);
    void insertEventProductStock(ProductStockVO productStockVO);
    void insertProductEvtOptValues(PdOptValueVO pdOptValueVO);
    void insertEventProductOptItem(PdOptItemVO pdOptItemVO);
    int insertEventProductOptValues(PdOptValueVO pdOptValueVO);

    void updateNewEventProductOptItem(PdOptItemVO pdOptItemVO);
    void updateProductOptCalcForDeal(PdEventRqstCalc pdEventRqstCalc);
    void updateEventProductStocks(ProductStockVO productStockVO);

    void deleteEventProductStocks(ProductStockVO productStockVO);

    void deleteNewEventProductOptItem(PdOptItemVO pdOptItemVO);
    void deleteProductEvtOptInfo(PdOptValueVO pdOptValueVO);
    void deleteProductOptCalcForDeal(PdEventRqstCalc pdEventRqstCalc);
    int deleteEventProductOptValues(ProductVO productVO);
}
