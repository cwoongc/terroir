package com.elevenst.terroir.product.registration.wms.service;

import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.wms.entity.PdPrdWmsInfo;
import com.elevenst.terroir.product.registration.wms.exception.WmsInfoServiceException;
import com.elevenst.terroir.product.registration.wms.mapper.WmsMapperTest;
import com.elevenst.terroir.product.registration.wms.vo.EmpVO;
import com.elevenst.terroir.product.registration.wms.vo.WmsVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("local")
@SpringBootTest
public class WmsInfoServiceTest {
    @Autowired
    WmsServiceImpl wmsInfoService;

    @Autowired
    WmsMapperTest wmsMapperTest;

    private static final long PRD_NO1 = 88888888881L;
    private static final long SELL_NO1 = 1004;
    private static final long SELL_NO2 = 1005;
    private boolean clearWmsInfo = false;

    @Test
    public void insertBasisStockAttribute() {

        int result = 0;

        WmsVO param = new WmsVO();

        param.setDlvClf("02");
        param.setBsnDealClf("02");
        param.setSetTypCd("02");

        boolean isDirectPurchaseOutDlv = ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(param.getBsnDealClf()) && ProductConstDef.DLV_CLF_OUT_DELIVERY.equals(param.getDlvClf());

        PdPrdWmsInfo basisStockVO = new PdPrdWmsInfo();

        basisStockVO.setPrdNo(PRD_NO1);
        basisStockVO.setEmpNo(SELL_NO1);

        if(ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(param.getDlvClf()) || ProductConstDef.SetTypCd.BUNDLE.equals(param.getSetTypCd())
                || isDirectPurchaseOutDlv || ProductConstDef.BSN_DEAL_CLF_SPECIFIC_PURCHASE.equals(param.getBsnDealClf())){
            basisStockVO.setRepPtnrMemNo(String.valueOf(SELL_NO1));
        }else{
            basisStockVO.setRepPtnrMemNo("0");
        }
        basisStockVO.setCreateNo(SELL_NO1);
        basisStockVO.setUpdateNo(SELL_NO1);


        result += wmsMapperTest.insertBasisStockAttribute(basisStockVO);
        result += wmsMapperTest.insertPdPrdWmsInfoHist(basisStockVO);

        Assert.assertEquals(2,result);

        PdPrdWmsInfo getPdPrdWmsTEST;

        getPdPrdWmsTEST = wmsMapperTest.getPdPrdWmsInfoTEST(PRD_NO1);

        Assert.assertEquals(SELL_NO1,getPdPrdWmsTEST.getEmpNo());
        Assert.assertEquals(SELL_NO1,Long.parseLong(getPdPrdWmsTEST.getRepPtnrMemNo()));
        Assert.assertEquals(SELL_NO1,getPdPrdWmsTEST.getUpdateNo());

    }

    @Test
    public void updateEmployee() {
        PdPrdWmsInfo pdPrdWmsInfo = new PdPrdWmsInfo();
        pdPrdWmsInfo.setPrdNo(PRD_NO1);
        pdPrdWmsInfo.setEmpNo(SELL_NO2);
        pdPrdWmsInfo.setUpdateNo(SELL_NO2);

        List<PdPrdWmsInfo> paramList = new ArrayList<>();
        paramList.add(pdPrdWmsInfo) ;

        if(StringUtil.isEmpty(paramList)) throw new WmsInfoServiceException("wmsInfo 수정될 정보가 없습니다.");

        int result = 0;
        for(PdPrdWmsInfo param : paramList){
            result += wmsMapperTest.updateEmployeeNo(param);
            wmsMapperTest.insertPdPrdWmsInfoHist(param);
        }

        if(result != paramList.size()) throw new WmsInfoServiceException("wmsInfo 수정을 완료하지 못했습니다.");

        PdPrdWmsInfo getPdPrdWmsTEST;
        getPdPrdWmsTEST = wmsMapperTest.getPdPrdWmsInfoTEST(PRD_NO1);

        Assert.assertEquals(SELL_NO2,getPdPrdWmsTEST.getEmpNo());
        Assert.assertEquals(SELL_NO2,getPdPrdWmsTEST.getUpdateNo());
        this.clearWmsInfo = true;
    }

    @Test
    public void after() {

        int delete = 0;

        PdPrdWmsInfo getPdPrdWmsTEST;
        getPdPrdWmsTEST = wmsMapperTest.getPdPrdWmsInfoTEST(PRD_NO1);
        if(getPdPrdWmsTEST.getPrdNo() != 0) {
            delete += wmsMapperTest.deletePdPrdWmsInfoTEST(PRD_NO1);
            wmsMapperTest.deletePdPrdWmsInfoHistTEST(PRD_NO1);
        }
        Assert.assertEquals(1,delete);
    }

    @Test
    public void getMdList() {
        EmpVO empVO = new EmpVO();
        String category1 = "1";
        String findName = "테스트";
        empVO.setSearchText1(category1);
        empVO.setSearchText2(findName);
        empVO.setSearchText3("AA");
        empVO.setSearchType("SHOCKDEAL");
        wmsInfoService.getMdList(empVO);
    }

    @Test
    public void getBasisStockAttribute() {
        long prdNo = 1244823084;
        wmsInfoService.getBasisStockAttribute(prdNo);
    }
}
