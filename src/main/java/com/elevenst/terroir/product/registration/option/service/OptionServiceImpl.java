package com.elevenst.terroir.product.registration.option.service;

import com.elevenst.common.code.CacheKeyConstDef;
import com.elevenst.common.process.RegistrationInfoConverter;
import com.elevenst.common.properties.PropMgr;
import com.elevenst.common.util.DateUtil;
import com.elevenst.common.util.FileUtil;
import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.common.util.VOUtil;
import com.elevenst.terroir.product.registration.catalog.service.CatalogServiceImpl;
import com.elevenst.terroir.product.registration.catalog.vo.CatalogRecmVO;
import com.elevenst.terroir.product.registration.catalog.vo.ItgModelInfoVO;
import com.elevenst.terroir.product.registration.category.service.CategoryServiceImpl;
import com.elevenst.terroir.product.registration.category.validate.CategoryValidate;
import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import com.elevenst.terroir.product.registration.category.vo.DpGnrlDispVO;
import com.elevenst.terroir.product.registration.common.property.FileProperty;
import com.elevenst.terroir.product.registration.common.service.CommonServiceImpl;
import com.elevenst.terroir.product.registration.common.vo.SyCoDetailVO;
import com.elevenst.terroir.product.registration.customer.service.CustomerServiceImpl;
import com.elevenst.terroir.product.registration.deal.mapper.DealMapper;
import com.elevenst.terroir.product.registration.deal.service.DealServiceImpl;
import com.elevenst.terroir.product.registration.deal.validate.DealValidate;
import com.elevenst.terroir.product.registration.deal.vo.ProductEvtVO;
import com.elevenst.terroir.product.registration.delivery.service.DeliveryServiceImpl;
import com.elevenst.terroir.product.registration.etc.service.EtcServiceImpl;
import com.elevenst.terroir.product.registration.etc.vo.PrdOthersVO;
import com.elevenst.terroir.product.registration.image.code.ImageDir;
import com.elevenst.terroir.product.registration.image.service.ProductImageServiceImpl;
import com.elevenst.terroir.product.registration.option.AbstractOption;
import com.elevenst.terroir.product.registration.option.CombinationOption;
import com.elevenst.terroir.product.registration.option.InputOption;
import com.elevenst.terroir.product.registration.option.code.OptLimitClfCds;
import com.elevenst.terroir.product.registration.option.code.OptionConstDef;
import com.elevenst.terroir.product.registration.option.entity.PdEventRqstCalc;
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
import com.elevenst.terroir.product.registration.option.exception.OptionException;
import com.elevenst.terroir.product.registration.option.mapper.OptionMapper;
import com.elevenst.terroir.product.registration.option.message.OptionServiceExceptionMessage;
import com.elevenst.terroir.product.registration.option.validate.OptionValidate;
import com.elevenst.terroir.product.registration.option.vo.BasisStockVO;
import com.elevenst.terroir.product.registration.option.vo.OptionVO;
import com.elevenst.terroir.product.registration.option.vo.PdOptItemVO;
import com.elevenst.terroir.product.registration.option.vo.PdOptValueVO;
import com.elevenst.terroir.product.registration.option.vo.ProductOptCalcVO;
import com.elevenst.terroir.product.registration.option.vo.ProductOptLimitVO;
import com.elevenst.terroir.product.registration.option.vo.ProductOptMartComboVO;
import com.elevenst.terroir.product.registration.option.vo.ProductStockSetInfoVO;
import com.elevenst.terroir.product.registration.option.vo.ProductStockVO;
import com.elevenst.terroir.product.registration.option.vo.PurchasePrcAprvVO;
import com.elevenst.terroir.product.registration.option.vo.StandardOptionGroupMappingVO;
import com.elevenst.terroir.product.registration.option.vo.StandardOptionInfoVO;
import com.elevenst.terroir.product.registration.option.vo.StandardOptionItemVO;
import com.elevenst.terroir.product.registration.price.entity.PdPrdPrcAprv;
import com.elevenst.terroir.product.registration.price.service.PriceServiceImpl;
import com.elevenst.terroir.product.registration.price.vo.PriceVO;
import com.elevenst.terroir.product.registration.product.code.CreateCdTypes;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.entity.PdPrd;
import com.elevenst.terroir.product.registration.product.entity.PdPrdHist;
import com.elevenst.terroir.product.registration.product.mapper.ProductMapper;
import com.elevenst.terroir.product.registration.product.message.ProductExceptionMessage;
import com.elevenst.terroir.product.registration.product.service.AdditionalProductServiceImpl;
import com.elevenst.terroir.product.registration.product.service.ProductCombServiceImpl;
import com.elevenst.terroir.product.registration.product.service.ProductServiceImpl;
import com.elevenst.terroir.product.registration.product.validate.ProductValidate;
import com.elevenst.terroir.product.registration.product.vo.BaseVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.service.SellerServiceImpl;
import com.elevenst.terroir.product.registration.seller.validate.SellerValidate;
import com.elevenst.terroir.product.registration.seller.vo.SellerAuthVO;
import com.elevenst.terroir.product.registration.wms.entity.PdPrdWmsInfo;
import com.elevenst.terroir.product.registration.wms.entity.PdPrdWmsInfoHist;
import com.elevenst.terroir.product.registration.wms.entity.WmDstStck;
import com.elevenst.terroir.product.registration.wms.entity.WmDstStckHist;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skt.tmall.common.properties.PropertiesManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class OptionServiceImpl implements RegistrationInfoConverter<ProductVO>{

    @Autowired
    SellerServiceImpl sellerServiceImpl;

    @Autowired
    CategoryServiceImpl categoryServiceImpl;

    @Autowired
    CommonServiceImpl commonServiceImpl;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OptionMapper optionMapper;

    @Autowired
    DealMapper dealMapper;

    @Autowired
    AdditionalProductServiceImpl additionalProductService;

    @Autowired
    CustomerServiceImpl customerServiceImpl;

    @Autowired
    CatalogServiceImpl catalogServiceImpl;

    @Autowired
    ProductServiceImpl productServiceImpl;

    @Autowired
    ProductImageServiceImpl productImageServiceImpl;

    @Autowired
    ProductCombServiceImpl productCombServiceImpl;

    @Autowired
    DealServiceImpl dealServiceImpl;

    @Autowired
    PriceServiceImpl priceServiceImpl;

    @Autowired
    DeliveryServiceImpl deliveryServiceImpl;

    @Autowired
    EtcServiceImpl etcServiceImpl;

    @Autowired
    CombinationOption optionCombination;

    @Autowired
    InputOption optionInput;

    @Autowired
    ProductValidate productValidate;

    @Autowired
    OptionValidate optionValidate;

    @Autowired
    SellerValidate sellerValidate;

    @Autowired
    CategoryValidate categoryValidate;

    @Autowired
    DealValidate dealValidate;

    @Autowired
    PropMgr propMgr;

    @Autowired
    VOUtil voUtil;

    @Autowired
    FileProperty fileProperty;

    public void setTestOptiontVO(ProductVO productVO) {
        /*
        productVO.setDispCtgrNo(1008870);
        productVO.setSelMnbdNo(selMnbdNo);
        productVO.setUpdateNo(selMnbdNo);

        BaseVO baseVO = new BaseVO();
        baseVO.setPrdTypCd(ProductConstDef.PRD_TYP_CD_NORMAL);
        baseVO.setPrdStatCd(ProductConstDef.PRD_SEL_STAT_CD_SALE);
        baseVO.setBsnDealClf(bsnDealClf);
        baseVO.setDispCtgr1NoDe(1001373);
        baseVO.setDispCtgr2NoDe(1002289);
        baseVO.setEngDispYn("N");
        baseVO.setSelMthdCd("01");
        baseVO.setDlvClf("01");
        baseVO.setSelStatCd("01");
        baseVO.setCreateCd(OptionConstDef.CODE_PD052_0200);
        */

        OptionVO optionVO = new OptionVO();
        optionVO.setOptClfCd(OptionConstDef.OPT_CLF_CD_MIXED);
        optionVO.setOptSelectYn("Y");
        String[] colTitle = new String[1];
        //옵션명
        colTitle[0] = "색상†ㅁㅁㅁ";
        optionVO.setColTitle(colTitle);

        //재고수량
        String[] colCount = new String[1];
        colCount[0] = "11†12†13†14";

        //옵션가격
        String[] colOptPrice = new String[1];
        colOptPrice[0] = "100†200†300†0";

        //옵션상태
        String[] prdStckStatCd = new String[1];
        prdStckStatCd[0] = "01†01†02†01";

        //옵션무게
        String[] colOptWght = new String[1];
        colOptWght[0] = "10†20†30†40";

        //지정일배송코드
        String[] colSellerStockCd = new String[1];
        colSellerStockCd[0] = "A†B†C†D";

        //조합된 옵션값 (2x2 일 경우 1단 :빨간색,노란색 이라면 총4개 출력 빨간색†빨간색†노란색†노란색
        String[] colValue1 = new String[1];
        colValue1[0] = "빨간색†빨간색†노란색†노란색";

        //조합된 옵션값 (2x2 일 경우 2단 :a,b 이라면 총4개 출력 a†b†a†b
        String[] colValue2 = new String[1];
        colValue2[0] = "a†b†a†b";

        //작성협옵션 사용여부
        optionVO.setOptInputYn("Y");
        //작성형옵션 명
        String[] colOptName = new String[1];
        colOptName[0] = "작성형1†작성형2";

        //작성형옵션 상태값
        String[] colOptStatCd = new String[1];
        colOptStatCd[0] = "01†01";

        //barcode
        String[] colBarCode = new String[1];
        colBarCode[0] = "1†2†3†4";

        /*
        if("U".equals(tranType)) {
            productVO.setPrdNo(1);
            colCount[0] = "111†121†131†141";
            colOptPrice[0] = "100†2000†300†0";
            prdStckStatCd[0] = "01†02†02†01";
            colOptWght[0] = "100†20†30†400";
            colSellerStockCd[0] = "A†B1†C1†D";
            colValue1[0] = "빨간색†빨간색†노란색1†노란색1";
            colValue2[0] = "a†b1†a1†b";
            colOptName[0] = "작성형11†작성형22";
            colOptStatCd[0] = "01†02";
            colBarCode[0] = "1†2a†3†4b";
        }
        */
        optionVO.setColCount(colCount);
        optionVO.setColOptPrice(colOptPrice);
        optionVO.setPrdStckStatCds(prdStckStatCd);
        optionVO.setColOptWght(colOptWght);
        optionVO.setColSellerStockCd(colSellerStockCd);
        optionVO.setColValue1(colValue1);
        optionVO.setColValue2(colValue2);
        optionVO.setColOptName(colOptName);
        optionVO.setColOptStatCd(colOptStatCd);
        optionVO.setColBarCode(colBarCode);

        //계산형, 날짜형 옵션 (단일상품에는 포함안됨)


        /*
        MemberVO memberVO = new MemberVO();
        memberVO.setGlobalSellerYn("N");
        */

        productVO.setOptionVO(optionVO);
        //productVO.setMemberVO(memberVO);

        //return productVO;
    }

    /**
     * 상품옵션 아이템 리스트를 가져온다.
     * - 쇼킹딜 또는 실제 상품정보
     */
    public List<PdOptItemVO> getProductOptItemLst(PdOptItemVO pdOptItemVO) throws OptionException {
        try {
            if (ProductConstDef.PrdInfoType.EVENT.equals(pdOptItemVO.getPrdInfoType())) {
                return dealMapper.getEventProductOptItemLst(pdOptItemVO);
            } else if (ProductConstDef.PrdInfoType.ROLLBACK.equals(pdOptItemVO.getPrdInfoType())) {
                return dealMapper.getEventProductOptItemOrgLst(pdOptItemVO);
            } else {
                return optionMapper.getProductOptItemLst(pdOptItemVO);
            }
        } catch (OptionException ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_OPTION_ITEM_LIST_ERROR, ex);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_OPTION_ITEM_LIST_ERROR, ex);
        }
    }

    /**
     * 상품옵션 아이템 리스트를 가져온다.
     * - 쇼킹딜 또는 실제 상품정보
     */
    @SuppressWarnings("unchecked")
    public List<PdOptItem> getProductOptItemLstAddOptClfCd(PdOptItem pdOptItem) throws OptionException {
        try {
            return optionMapper.getProductOptItemLstAddOptClfCd(pdOptItem);
        } catch (OptionException ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_OPTION_ITEM_LIST_ERROR, ex);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_OPTION_ITEM_LIST_ERROR, ex);
        }
    }

    /**
     * 상품상세 스마트옵션으로 등록되어있는 요약이미지의 개수를 출력한다.
     *
     * @param prdNo
     * @return
     * @throws OptionException
     */
    public int getPrdStockImageCount(long prdNo) throws OptionException {
        try {
            return optionMapper.getPrdStockImageCount(prdNo);
        } catch (OptionException ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_SMART_OPTION_REG_CNT_ERROR, ex);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_SMART_OPTION_REG_CNT_ERROR, ex);
        }
    }

    /**
     * 조합형의 옵션 아이템 상품옵션값 리스트를 가져온다.
     */
    @SuppressWarnings("unchecked")
    public List<PdOptValue> getProductOptMixValueList(HashMap<String, Object> hmap) throws OptionException {
        try {
            //if(ProductConstDef.PrdInfoType.EVENT.equals((ProductConstDef.PrdInfoType)hmap.get("prdInfoType"))){
            if (ProductConstDef.PrdInfoType.EVENT.equals(hmap.get("prdInfoType"))) {
                return dealMapper.getEventProductOptMixValueList(hmap);
                //}else if(ProductConstDef.PrdInfoType.ROLLBACK.equals((ProductConstDef.PrdInfoType)hmap.get("prdInfoType"))){
            } else if (ProductConstDef.PrdInfoType.ROLLBACK.equals(hmap.get("prdInfoType"))) {
                return dealMapper.getEventProductOptMixValueOrgList(hmap);
            } else {
                if (OptionConstDef.USE_Y.equals(hmap.get("martOptionYn"))) {
                    return optionMapper.getProductOptMartValueList(hmap);
                } else {
                    return optionMapper.getProductOptMixValueList(hmap);
                }
            }
        } catch (OptionException ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_MIX_OPTION_VALUE_ERROR, ex);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_MIX_OPTION_VALUE_ERROR, ex);
        }
    }

    /**
     * 선택형 옵션 수 조회
     *
     * @param prdNo
     * @throws OptionException
     */
    public int getSelectOptionCnt(long prdNo) throws OptionException {
        int selOptCnt = OptionConstDef.INT_ZERO;
        try {
            selOptCnt = optionMapper.getSelectOptionCnt(prdNo);
        } catch (OptionException ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_STOCK_TOTAL_CNT_ERROR, ex);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_STOCK_TOTAL_CNT_ERROR, ex);
        }
        return selOptCnt;
    }

    /**
     * 독립형 옵션 체크(전세계배송)
     */
    public int getOptClfCdCnt(List prdNoList) throws OptionException {
        try {
            HashMap map = new HashMap();
            map.put("prdNoList", prdNoList);
            return (Integer) optionMapper.getOptClfCdCnt(map);
        } catch (OptionException ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_IDEP_STOCK_CNT_ERROR, ex);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_IDEP_STOCK_CNT_ERROR, ex);
        }
    }

    /**
     * 추가구성 상품의 주상품번호
     *
     * @param prdNo
     * @throws OptionException
     */
    public long getProductStckCnt(long prdNo) throws OptionException {
        long stckCnt = OptionConstDef.LONG_ZERO;
        try {
            ProductVO productVO = productMapper.getOnlySearchPdPrd(prdNo);
            stckCnt = this.getProductStckCnt(productVO);
        } catch (OptionException ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_LIVE_STOCK_CNT_ERROR, ex);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_LIVE_STOCK_CNT_ERROR, ex);
        }
        return stckCnt;
    }

    public long getProductStckCnt(ProductVO productVO) throws OptionException {
        long stckCnt = OptionConstDef.LONG_ZERO;
        try {
            if (productVO != null && ProductConstDef.SetTypCd.BUNDLE.equals(productVO.getBaseVO().getSetTypCd())) {
                stckCnt = optionMapper.getBundleProductStckCnt(productVO.getPrdNo());
            } else {
                stckCnt = optionMapper.getProductStckCnt(productVO.getPrdNo());
            }
        } catch (OptionException ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_LIVE_STOCK_CNT_ERROR, ex);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_LIVE_STOCK_CNT_ERROR, ex);
        }
        return stckCnt;
    }

    /**
     * 상품의 옵션코드
     */
    public String getProductOptCd(long prdNo) throws OptionException {
        try {
            ProductVO productVO = productMapper.getOnlySearchPdPrd(prdNo);
            return this.getProductOptCd(productVO);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_OPTION_CLF_CD_ERROR, ex);
        }
    }

    public String getProductOptCd(ProductVO productVO) throws OptionException {
        try {
            if (productVO != null && ProductConstDef.SetTypCd.BUNDLE.equals(productVO.getBaseVO().getSetTypCd())) {
                return optionMapper.getBundleProductOptCd(productVO.getPrdNo());
            } else {
                return optionMapper.getProductOptCd(productVO.getPrdNo());
            }
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_OPTION_CLF_CD_ERROR, ex);
        }
    }

    /**
     * 상품의 옵션항목구분(조합/독립/입력)에 등록된 개수 조회
     *
     * @param prdNo
     * @param optClfCd
     * @return
     * @throws OptionException
     */
    public int getOptionClfTypeCnt(long prdNo, String optClfCd) throws OptionException {
        int selOptCnt = 0;
        try {
            HashMap map = new HashMap();
            map.put("prdNo", prdNo);
            map.put("optClfCd", optClfCd);
            selOptCnt = optionMapper.getOptionClfTypeCnt(map);
        } catch (OptionException ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_OPTION_CLF_TYP_CD_CNT_ERROR, ex);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_OPTION_CLF_TYP_CD_CNT_ERROR, ex);
        }
        return selOptCnt;
    }

    /**
     * 재고수량에 따른 판매상태 코드 반환
     *
     * @param object
     * @param selBgnDy
     * @param townExcelUser
     * @return String -판매상태 코드
     * @throws OptionException
     */
    public String getProductSelStatChangeStatReturn(Object object, String selBgnDy, String townExcelUser) throws OptionException {
        String rtnSelStat = OptionConstDef.NULL_STRING;
        try {
            String curDate = DateUtil.getFormatString("yyyyMMddHHmmss");
            ProductVO chkPrdVO = null;
            long prdNo = OptionConstDef.LONG_ZERO;
            long stckCnt = OptionConstDef.LONG_ZERO;
            if (object != null && object.getClass().equals(Long.class)) {
                prdNo = (Long) object;
                chkPrdVO = productMapper.getProduct(prdNo);
            } else if (object != null && object.getClass().equals(ProductVO.class)) {
                chkPrdVO = (ProductVO) object;
                prdNo = chkPrdVO.getPrdNo();
            }

            if (chkPrdVO == null) {
                throw new OptionException(ProductExceptionMessage.NOT_EXIST_PRD_INFO_ERROR);
            }

            /*
             * 재고 수량을 검색해서 0  품절상태 변경을 처리한다.
             */
            stckCnt = optionMapper.getProductStckCnt(prdNo);

            /*
             * 판매상태가 품절이고 0보다 크다면
             * 판매 시작일을 체크한다.
             */
            if (stckCnt > OptionConstDef.LONG_ZERO) {

            	/*
            	 * 1.경매가 아닌경우
            	 * 	 1-1.승인대기 라면
            	 *   1-2.품절이 라면
            	 *   1-3.품절이 아닌경우
            	 * 2.경매인경우
            	 */
                if (!"03".equals(chkPrdVO.getBaseVO().getSelMthdCd())) {

            		/*
            		 * 승인전
            		 */
                    if ("101".equals(chkPrdVO.getBaseVO().getSelStatCd())) {
                        return "101";
                    }
                    if (Long.parseLong(StringUtil.left(selBgnDy, 8)) > Long.parseLong(StringUtil.left(curDate, 8))) {
                        //전시전으로 처리한다.
                        rtnSelStat = "102";
                    } else {
                        //판매중으로 처리한다.
                        rtnSelStat = "103";
                    }

                    if (chkPrdVO.getBaseVO().getSelStatCd().equals("105")) { //판매상태가 전시중지 인 경우는 원래상태로....
                        rtnSelStat = chkPrdVO.getBaseVO().getSelStatCd();
                    }
                } else {
            		/*
            		 * 승인전
            		 */
                    if ("201".equals(chkPrdVO.getBaseVO().getSelStatCd())) {
                        return "201";
                    }
                    rtnSelStat = productSelStatChangeStatReturn(selBgnDy, curDate);
                }
            } else {
                if (OptionConstDef.USE_Y.equals(townExcelUser)
                    && (Long.parseLong(StringUtil.left(selBgnDy, 8)) > Long.parseLong(StringUtil.left(curDate, 8)))) {
                    rtnSelStat = "102"; // 재고 0이어도 전시전으로 처리
                } else {
                    rtnSelStat = productSelStatChangeStatReturn(chkPrdVO);
                }
            }
            return rtnSelStat;
        } catch (OptionException ex) {
            throw new OptionException(ProductExceptionMessage.CHECK_PRD_SEL_STAT_CD_ERROR, ex);
        } catch (Exception ex) {
            throw new OptionException(ProductExceptionMessage.CHECK_PRD_SEL_STAT_CD_ERROR, ex);
        }
    }

    protected String productSelStatChangeStatReturn(String selBgnDy, String curDate) {
        String rtnSelStat = OptionConstDef.NULL_STRING;
        if (Long.parseLong(StringUtil.left(selBgnDy, 8)) > Long.parseLong(StringUtil.left(curDate, 8))) {
            //전시전으로 처리한다.
            rtnSelStat = "202";
        } else {
            //판매중으로 처리한다.
            rtnSelStat = "203";
        }
        return rtnSelStat;
    }

    protected String productSelStatChangeStatReturn(ProductVO chkPrdVO) {
        String rtnSelStat = OptionConstDef.NULL_STRING;
        if (!"03".equals(chkPrdVO.getBaseVO().getSelMthdCd())) {
        	/*
    		 * 승인전
    		 */
            if ("101".equals(chkPrdVO.getBaseVO().getSelStatCd())) {
                return "101";
            } else {
                //품절처리한다.
                rtnSelStat = "104";
            }
        } else {
        	/*
    		 * 승인전
    		 */
            if ("201".equals(chkPrdVO.getBaseVO().getSelStatCd())) {
                return "201";
            } else {
                //종료로 처리한다.
                rtnSelStat = "204";
            }
        }
        return rtnSelStat;
    }

    /**
     * 지정배송일 정보 조회
     *
     * @param productStockVO
     * @throws OptionException
     */
    public List<ProductStockVO> getPdStockPtnrInfoByPrdNo(ProductStockVO productStockVO) throws OptionException {
        List<ProductStockVO> retProductStockList = new ArrayList<ProductStockVO>();
        try {
            retProductStockList = optionMapper.getPdStockPtnrInfoByPrdNo(productStockVO);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_PTNR_INFO_ERROR, ex);
        }
        return retProductStockList;
    }

    /**
     * 계산형 옵션 정보를 등록한다. (HIST 포함)
     *
     * @param PdPrdOptCalcList
     * @throws OptionException
     */
    public void insertProductOptCalcAll(List<PdPrdOptCalc> PdPrdOptCalcList) throws OptionException {
        try {
            if (PdPrdOptCalcList != null && PdPrdOptCalcList.size() > OptionConstDef.INT_ZERO) {
                int index = OptionConstDef.INT_ZERO;
                for (index = OptionConstDef.INT_ZERO; index < PdPrdOptCalcList.size(); index++) {
                    PdPrdOptCalc pdPrdOptCalc = PdPrdOptCalcList.get(index);
                    optionMapper.insertProductOptCalc(pdPrdOptCalc);
                    optionMapper.insertProductOptCalcHist(this.setProductOptCalcHistBO(pdPrdOptCalc));
                }
            }
        } catch (OptionException ex) {
            throw new OptionException(OptionServiceExceptionMessage.INSERT_CALC_OPTION_ERROR, ex);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.INSERT_CALC_OPTION_ERROR, ex);
        }
    }

    public PdPrdOptCalcHist setProductOptCalcHistBO(PdPrdOptCalc pdPrdOptCalc) throws OptionException {
        PdPrdOptCalcHist pdPrdOptCalcHist = new PdPrdOptCalcHist();
        pdPrdOptCalcHist.setPrdNo(pdPrdOptCalc.getPrdNo());
        pdPrdOptCalcHist.setOptItemNo(pdPrdOptCalc.getOptItemNo());
        pdPrdOptCalcHist.setOptItemNm(pdPrdOptCalc.getOptItemNm());
        pdPrdOptCalcHist.setOptItemMinValue(pdPrdOptCalc.getOptItemMinValue());
        pdPrdOptCalcHist.setOptItemMaxValue(pdPrdOptCalc.getOptItemMaxValue());
        pdPrdOptCalcHist.setOptUnitPrc(pdPrdOptCalc.getOptUnitPrc());
        pdPrdOptCalcHist.setOptUnitCd(pdPrdOptCalc.getOptUnitCd());
        pdPrdOptCalcHist.setOptSelUnit(pdPrdOptCalc.getOptSelUnit());
        pdPrdOptCalcHist.setCreateDt(pdPrdOptCalc.getCreateDt());
        pdPrdOptCalcHist.setCreateNo(pdPrdOptCalc.getCreateNo());
        pdPrdOptCalcHist.setUpdateDt(pdPrdOptCalc.getUpdateDt());
        pdPrdOptCalcHist.setUpdateNo(pdPrdOptCalc.getUpdateNo());
        return pdPrdOptCalcHist;
    }

    public void setProductOptCalcBO(Object object, long eventNo, long prdNo, long optUnitPrc, String optUnitCd, long optSelUnit, long userNo) throws OptionException {
        if (object != null && object instanceof PdPrdOptCalc) {
            PdPrdOptCalc pdPrdOptCalc = (PdPrdOptCalc) object;
            pdPrdOptCalc.setPrdNo(prdNo);
            pdPrdOptCalc.setOptUnitPrc(optUnitPrc);
            pdPrdOptCalc.setOptUnitCd(optUnitCd);
            pdPrdOptCalc.setOptSelUnit(optSelUnit);
            pdPrdOptCalc.setCreateNo(userNo);
            pdPrdOptCalc.setUpdateNo(userNo);
        } else if (object != null && object instanceof PdEventRqstCalc) {
            PdEventRqstCalc pdEventRqstCalc = (PdEventRqstCalc) object;
            pdEventRqstCalc.setEventNo(eventNo);
            pdEventRqstCalc.setPrdNo(prdNo);
            pdEventRqstCalc.setOptUnitPrc(optUnitPrc);
            pdEventRqstCalc.setOptUnitCd(optUnitCd);
            pdEventRqstCalc.setOptSelUnit(optSelUnit);
            pdEventRqstCalc.setCreateNo(userNo);
            pdEventRqstCalc.setUpdateNo(userNo);
        }

    }

    /**
     * 계산형 옵션 적용
     *
     * @param userNo
     * @param productOptCalcVO
     * @return
     * @throws OptionException
     */
    public List<PdPrdOptCalc> applyProductOptionCalc(long userNo, ProductOptCalcVO productOptCalcVO) throws OptionException {
        if(productOptCalcVO == null){
            return null;
        }
        List<PdPrdOptCalc> productOptCalcList = new ArrayList<PdPrdOptCalc>();
        List<PdEventRqstCalc> productEventRqstCalcList = new ArrayList<PdEventRqstCalc>();
        String useOptCalc = productOptCalcVO.getUseOptCalc();
        long eventNo = productOptCalcVO.getEventNo();
        //처리해야 할 내용만큼의 정보를 List로 생성
        PdPrdOptCalc pdPrdOptCalc = new PdPrdOptCalc();
        PdEventRqstCalc pdEventRqstCalc = new PdEventRqstCalc();

        long prdNo = productOptCalcVO.getPrdNo();

        long optUnitPrc = productOptCalcVO.getOptUnitPrc();
        long optSelUnit = productOptCalcVO.getOptSelUnit(); //판매단위-숫자
        String optUnitCd = productOptCalcVO.getOptUnitCd();

        String optCalcItem1Nm = productOptCalcVO.getOptItem1Nm();
        long optCalcItem1MinValue = productOptCalcVO.getOptItem1MinValue();
        long optCalcItem1MaxValue = productOptCalcVO.getOptItem1MaxValue();

        String optCalcItem2Nm = productOptCalcVO.getOptItem2Nm();
        long optCalcItem2MinValue = productOptCalcVO.getOptItem2MinValue();
        long optCalcItem2MaxValue = productOptCalcVO.getOptItem2MaxValue();

        if (eventNo > OptionConstDef.LONG_ZERO) {
            pdEventRqstCalc.setOptItemNo(1);
            pdEventRqstCalc.setOptItemNm(StringUtil.cutInStringByBytesNoneSpace(optCalcItem1Nm, 20));
            pdEventRqstCalc.setOptItemMinValue(optCalcItem1MinValue);
            pdEventRqstCalc.setOptItemMaxValue(optCalcItem1MaxValue);
            setProductOptCalcBO(pdEventRqstCalc, eventNo, prdNo, optUnitPrc, optUnitCd, optSelUnit, userNo);
            productEventRqstCalcList.add(pdEventRqstCalc);

            pdEventRqstCalc = new PdEventRqstCalc();
            pdEventRqstCalc.setOptItemNo(2);
            pdEventRqstCalc.setOptItemNm(StringUtil.cutInStringByBytesNoneSpace(optCalcItem2Nm, 20));
            pdEventRqstCalc.setOptItemMinValue(optCalcItem2MinValue);
            pdEventRqstCalc.setOptItemMaxValue(optCalcItem2MaxValue);
            setProductOptCalcBO(pdEventRqstCalc, eventNo, prdNo, optUnitPrc, optUnitCd, optSelUnit, userNo);
            productEventRqstCalcList.add(pdEventRqstCalc);
        } else {
            pdPrdOptCalc.setOptItemNo(1);
            pdPrdOptCalc.setOptItemNm(StringUtil.cutInStringByBytesNoneSpace(optCalcItem1Nm, 20));
            pdPrdOptCalc.setOptItemMinValue(optCalcItem1MinValue);
            pdPrdOptCalc.setOptItemMaxValue(optCalcItem1MaxValue);
            setProductOptCalcBO(pdPrdOptCalc, eventNo, prdNo, optUnitPrc, optUnitCd, optSelUnit, userNo);
            productOptCalcList.add(pdPrdOptCalc);

            pdPrdOptCalc = new PdPrdOptCalc();
            pdPrdOptCalc.setOptItemNo(2);
            pdPrdOptCalc.setOptItemNm(StringUtil.cutInStringByBytesNoneSpace(optCalcItem2Nm, 20));
            pdPrdOptCalc.setOptItemMinValue(optCalcItem2MinValue);
            pdPrdOptCalc.setOptItemMaxValue(optCalcItem2MaxValue);
            setProductOptCalcBO(pdPrdOptCalc, eventNo, prdNo, optUnitPrc, optUnitCd, optSelUnit, userNo);
            productOptCalcList.add(pdPrdOptCalc);
        }

        if (!OptionConstDef.NULL_STRING.equals(useOptCalc) && OptionConstDef.USE_N.equals(useOptCalc)) {
            //삭제처리
            if (eventNo > OptionConstDef.LONG_ZERO) {
                this.deleteProductOptCalcForDeal(productEventRqstCalcList);
            } else {
                List<PdPrdOptCalc> resList = this.getOptCalcList(pdPrdOptCalc);
                if (resList.isEmpty()) {
                    productOptCalcList = null;
                }
                this.deleteProductOptCalcAll(productOptCalcList);
            }
        } else if (!OptionConstDef.NULL_STRING.equals(useOptCalc) && OptionConstDef.USE_Y.equals(useOptCalc)) {
            optionValidate.checkCalcOption(optCalcItem1Nm,
                                           optCalcItem2Nm,
                                           optCalcItem1MinValue,
                                           optCalcItem1MaxValue,
                                           optCalcItem2MinValue,
                                           optCalcItem2MaxValue,
                                           optUnitPrc,
                                           optSelUnit,
                                           optUnitCd);

            //등록, 수정 처리 진행
            if ("reg".equals(productOptCalcVO.getOptCalcTranType())) {
                if (eventNo > OptionConstDef.LONG_ZERO) {
                    this.insertProductOptCalcForDeal(productEventRqstCalcList);
                } else {
                    this.insertProductOptCalcAll(productOptCalcList);
                }
            } else if ("upd".equals(productOptCalcVO.getOptCalcTranType())) {
                if (eventNo > OptionConstDef.LONG_ZERO) {
                    List<PdEventRqstCalc> resList = dealServiceImpl.getEvtOptCalcList(pdEventRqstCalc);
                    if (resList != null && resList.size() > OptionConstDef.INT_ZERO) {
                        this.updateProductOptCalcForDeal(productEventRqstCalcList);
                    } else {
                        this.insertProductOptCalcForDeal(productEventRqstCalcList);
                    }
                } else {
                    List<PdPrdOptCalc> resList = this.getOptCalcList(pdPrdOptCalc);
                    if (resList != null && resList.size() > OptionConstDef.INT_ZERO) {
                        this.updateProductOptCalcAll(productOptCalcList);
                    } else {
                        this.insertProductOptCalcAll(productOptCalcList);
                    }
                }
            }
        }
        return productOptCalcList;
    }

    public void deleteProductOptCalcForDeal(List<PdEventRqstCalc> productEventRqstCalcList) throws OptionException {
        try {
            if (productEventRqstCalcList != null && productEventRqstCalcList.size() > OptionConstDef.INT_ZERO) {
                int index = OptionConstDef.INT_ZERO;
                for (index = OptionConstDef.INT_ZERO; index < productEventRqstCalcList.size(); index++) {
                    PdEventRqstCalc pdEventRqstCalc = productEventRqstCalcList.get(index);
                    dealMapper.deleteProductOptCalcForDeal(pdEventRqstCalc);
                }
            }
        } catch (OptionException ex) {
            throw new OptionException(OptionServiceExceptionMessage.DELETE_SHOCKING_DEAL_CALC_OPTION_ERROR, ex);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.DELETE_SHOCKING_DEAL_CALC_OPTION_ERROR, ex);
        }
    }

    /**
     * 계산형 옵션 정보를 조회한다.
     *
     * @param pdPrdOptCalc
     * @return
     * @throws OptionException
     */
    public List<PdPrdOptCalc> getOptCalcList(PdPrdOptCalc pdPrdOptCalc) throws OptionException {
        List<PdPrdOptCalc> pdPrdOptCalcList = new ArrayList<PdPrdOptCalc>();
        try {
            pdPrdOptCalcList = optionMapper.getOptCalcList(pdPrdOptCalc);
        } catch (OptionException ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_CALC_OPTION_ERROR, ex);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.GET_CALC_OPTION_ERROR, ex);
        }
        return pdPrdOptCalcList;
    }

    /**
     * 계산형 옵션 정보를 수정한다. (HIST 포함)
     *
     * @param pdPrdOptCalcList
     * @throws OptionException
     */
    public void updateProductOptCalcAll(List<PdPrdOptCalc> pdPrdOptCalcList) throws OptionException {
        try {
            if (pdPrdOptCalcList != null && pdPrdOptCalcList.size() > OptionConstDef.INT_ZERO) {
                int index = OptionConstDef.INT_ZERO;
                for (index = OptionConstDef.INT_ZERO; index < pdPrdOptCalcList.size(); index++) {
                    PdPrdOptCalc pdPrdOptCalc = pdPrdOptCalcList.get(index);
                    optionMapper.updateProductOptCalc(pdPrdOptCalc);
                    optionMapper.insertProductOptCalcHist(this.setProductOptCalcHistBO(pdPrdOptCalc));
                }
            }
        } catch (OptionException ex) {
            throw new OptionException(OptionServiceExceptionMessage.UPDATE_CALC_OPTION_ERROR, ex);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.UPDATE_CALC_OPTION_ERROR, ex);
        }
    }

    /**
     * 계산형 옵션 정보를 삭제한다. (HIST 포함)
     *
     * @param pdPrdOptCalcList
     * @throws OptionException
     */
    public void deleteProductOptCalcAll(List<PdPrdOptCalc> pdPrdOptCalcList) throws OptionException {
        try {
            if (pdPrdOptCalcList != null && pdPrdOptCalcList.size() > OptionConstDef.INT_ZERO) {
                int index = OptionConstDef.INT_ZERO;
                for (index = OptionConstDef.INT_ZERO; index < pdPrdOptCalcList.size(); index++) {
                    PdPrdOptCalc pdPrdOptCalc = pdPrdOptCalcList.get(index);
                    optionMapper.insertProductOptCalcHist(this.setProductOptCalcHistBO(pdPrdOptCalc));
                    optionMapper.deleteProductOptCalc(pdPrdOptCalc);
                }
            }
        } catch (OptionException ex) {
            throw new OptionException(OptionServiceExceptionMessage.DELETE_CALC_OPTION_ERROR, ex);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.DELETE_CALC_OPTION_ERROR, ex);
        }
    }

    /**
     * 계산형 옵션 정보를 삭제한다. (HIST 포함)
     *
     * @param pdPrdOptCalcList
     * @throws OptionException
     */
    public void deleteProductOptCalc(List<PdPrdOptCalc> pdPrdOptCalcList) throws OptionException {
        try {
            if (pdPrdOptCalcList != null && pdPrdOptCalcList.size() > OptionConstDef.INT_ZERO) {
                int index = OptionConstDef.INT_ZERO;
                for (index = OptionConstDef.INT_ZERO; index < pdPrdOptCalcList.size(); index++) {
                    PdPrdOptCalc pdPrdOptCalc = pdPrdOptCalcList.get(index);
                    optionMapper.deleteProductOptCalc(pdPrdOptCalc);
                }
            }
        } catch (OptionException ex) {
            throw new OptionException(OptionServiceExceptionMessage.DELETE_CALC_OPTION_ERROR, ex);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.DELETE_CALC_OPTION_ERROR, ex);
        }
    }

    /**
     * 계산형 옵션 정보를 등록한다.
     *
     * @param productEventRqstCalcList
     * @throws OptionException
     */
    public void insertProductOptCalcForDeal(List<PdEventRqstCalc> productEventRqstCalcList) throws OptionException {
        try {
            if (productEventRqstCalcList != null && productEventRqstCalcList.size() > OptionConstDef.INT_ZERO) {
                int index = OptionConstDef.INT_ZERO;
                for (index = OptionConstDef.INT_ZERO; index < productEventRqstCalcList.size(); index++) {
                    PdEventRqstCalc pdEventRqstCalc = productEventRqstCalcList.get(index);
                    dealMapper.insertProductOptCalcForDeal(pdEventRqstCalc);
                }
            }
        } catch (OptionException ex) {
            throw new OptionException(OptionServiceExceptionMessage.INSERT_SHOCKING_DEAL_CALC_OPTION_ERROR, ex);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.INSERT_SHOCKING_DEAL_CALC_OPTION_ERROR, ex);
        }
    }

    /**
     * 계산형 옵션 정보를 수정한다.
     *
     * @param pdEventRqstCalcList
     * @throws OptionException
     */
    public void updateProductOptCalcForDeal(List<PdEventRqstCalc> pdEventRqstCalcList) throws OptionException {
        try {
            if (pdEventRqstCalcList != null && pdEventRqstCalcList.size() > OptionConstDef.INT_ZERO) {
                int index = OptionConstDef.INT_ZERO;
                for (index = OptionConstDef.INT_ZERO; index < pdEventRqstCalcList.size(); index++) {
                    PdEventRqstCalc pdEventRqstCalc = pdEventRqstCalcList.get(index);
                    dealMapper.updateProductOptCalcForDeal(pdEventRqstCalc);
                }
            }
        } catch (OptionException ex) {
            throw new OptionException(OptionServiceExceptionMessage.UPDATE_SHOCKING_DEAL_CALC_OPTION_ERROR, ex);
        } catch (Exception ex) {
            throw new OptionException(OptionServiceExceptionMessage.UPDATE_SHOCKING_DEAL_CALC_OPTION_ERROR, ex);
        }
    }

    public int updateProductOptionPurchaseInfo(PdStock pdStock) throws OptionException {
        try {
            return optionMapper.updateProductOptionPurchaseInfo(pdStock);
        } catch (OptionException ex) {
            throw new OptionException(OptionServiceExceptionMessage.UPDATE_PURCHASE_INFO_ERROR, ex);
        }
    }

    public ProductVO getProduct4OptionPop(ProductVO productVO) throws OptionException {
        ProductVO rstProductBO = null;
        try {
            // 옵션 등록/수정 용 상품 추가 정보 조회
            rstProductBO = optionMapper.getProduct4OptionPop(productVO);
            // 마이너스 옵션 제한 카테고리 여부 조회
            if (ProductConstDef.BSN_DEAL_CLF_COMMISSION.equals(rstProductBO.getBaseVO().getBsnDealClf())) {
                rstProductBO = optionValidate.isMinusOptLimit(rstProductBO);
            } else {
                rstProductBO.setMinusOptLimitYn("N");
            }
            rstProductBO.getPriceVO().setEventNo(productVO.getPriceVO().getEventNo());
            rstProductBO.getBaseVO().setPrdInfoType(productVO.getBaseVO().getPrdInfoType());

        } catch (OptionException ex) {
            throw new OptionException("옵션등록/수정 상품정보 조회 오류", ex);
        } catch (Exception ex) {
            throw new OptionException("옵션등록/수정 상품정보 조회 오류", ex);
        }

        return rstProductBO;
    }

    /**
     * 옵션 정책 정보 조회
     *
     * @param paramBO
     * @return ProductOptLimitVO
     * @throws OptionException
     */
    public ProductOptLimitVO getProductOptLimitForSellerSet(ProductOptLimitVO paramBO) throws OptionException {
        ProductOptLimitVO resProductOptLimitVO = null;
        try {
            if ("Y".equals(paramBO.getStdPrdYn())) {
                paramBO.setOptLmtClfCd(OptLimitClfCds.STANDARD.getDtlsCd());
                resProductOptLimitVO = optionMapper.getProductOptLimitForBasic(paramBO);
            } else {
                resProductOptLimitVO = optionMapper.getProductOptLimitForSellerSet(paramBO);
                // 예외 처리 된 정책이 없을 경우 기본 정책 정보를 가져온다.
                if (resProductOptLimitVO == null) {
                    paramBO.setOptLmtClfCd(OptLimitClfCds.BASIC.getDtlsCd());
                    resProductOptLimitVO = optionMapper.getProductOptLimitForBasic(paramBO);
                }
            }
        } catch (OptionException ex) {
            throw new OptionException("상품옵션 최대개수 정책관리 정보 출력 오류", ex);
        } catch (Exception ex) {
            throw new OptionException("상품옵션 최대개수 정책관리 정보 출력 오류", ex);
        }

        return resProductOptLimitVO;
    }

    /**
     * 기본 정책 정보를 가져온다.
     * @param paramBO
     * @return
     * @throws OptionException
     */
    public ProductOptLimitVO getProductOptLimitForBasic(ProductOptLimitVO paramBO) throws OptionException {
        ProductOptLimitVO resProductOptLimitVO = null;
        try {
            // 예외 처리 된 정책이 없을 경우 기본 정책 정보를 가져온다.
            resProductOptLimitVO = optionMapper.getProductOptLimitForBasic(paramBO);
        } catch (OptionException ex) {
            throw new OptionException("상품옵션 최대개수 정책관리 정보 출력 오류", ex);
        } catch (Exception ex) {
            throw new OptionException("상품옵션 최대개수 정책관리 정보 출력 오류", ex);
        }
        return resProductOptLimitVO;
    }

    /**
     * 상품재고 리스트 조회
     * - 현재 유효한 재고 리스트 중 item_nm 값을 포함하여 조회함.
     * ** (중요) 옵션적용시 중복 기존 옵션 체크를 위해서 만 사용함..
     */
    @SuppressWarnings("unchecked")
    public List<ProductStockVO> getExistProductStockLst(ProductStockVO param) throws OptionException {
        try {
            if (param.getEventNo() > 0) {
                return dealMapper.getExistEventProductStockLst(param);
            } else {
                List<ProductStockVO> list = optionMapper.getExistProductStockLst(param);
                if (ProductConstDef.SetTypCd.BUNDLE.equals(param.getSetTypCd())) {
                    for (ProductStockVO productStockBO : list) {
                        productStockBO.setStockSetInfoListVO(this.getPrdStockSetInfo(productStockBO));
                    }
                }
                return list;
            }
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    public List<ProductStockSetInfoVO> getPrdStockSetInfo(ProductStockVO paramBO) throws OptionException {
        try {
            return (List<ProductStockSetInfoVO>) optionMapper.getPrdStockSetInfo(paramBO);
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * 상품옵션값 리스트를 가져온다.
     */
    public List<OptionVO> getOptValueList(PdOptItemVO pdOptItemVO) throws OptionException {
        try {
            return optionMapper.getOptValueList(pdOptItemVO);
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    // 독립형 옵션 재고 생성 메소드 (하나의 데이터 row만 생성한다.)
    private void createIdepOptionStock(Map<String, PdOptItemVO> optItemMap, PdOptItemVO optItemBO, List<ProductStockVO> stockBOList, ProductVO productBO) throws OptionException {
        ProductStockVO stock = new ProductStockVO();

        Iterator<Map.Entry<String, PdOptItemVO>> item = optItemMap.entrySet().iterator();

        int index = 1;

        while (item.hasNext()) {
            Map.Entry<String, PdOptItemVO> entry = item.next();
            optItemBO = entry.getValue();
            if (OptionConstDef.OPT_CLF_CD_CUST.equals(optItemBO.getOptClfCd())) continue;

            PdOptValueVO tempPtValueBO = new PdOptValueVO();

            for (PdOptValueVO ptValueBO : optItemBO.getPdOptValueVOList()) {
                tempPtValueBO = ptValueBO;
                if (OptionConstDef.PRD_STCK_STAT_CD_USE.equals(ptValueBO.getPrdStckStatCd())) break;
            }

            Object[] obj = {tempPtValueBO.getOptItemNo(), tempPtValueBO.getOptValueNo(), tempPtValueBO.getOptValueNm()}; // {옵션아이템 NO, 옵션값 NO, 옵션값}

            // ProductStockBO 에 옵션 아이템 순번, 옵션값 순번, 옵션값을 설정함. setOptItemNo, setOptValueNo, setOptValueNm ex)(2, 1, 95사이즈), (1, 1, 빨간색), (2, 2, 100사이즈), (1, 1, 빨간색)
            AbstractOption.setPrdStckBOMethodInvoke(stock, obj, index);

            // 재고 상태값이 설정되어 있지 않을 경우 현재 옵션값의 상태값으로 설정
            if (StringUtil.isEmpty(stock.getPrdStckStatCd()) || OptionConstDef.PRD_STCK_STAT_CD_USE.equals(stock.getPrdStckStatCd())) {
                stock.setPrdStckStatCd(tempPtValueBO.getPrdStckStatCd());
            }
            index++;
        }

        stock.setCreateNo(optItemBO.getCreateNo());
        stock.setUpdateNo(optItemBO.getUpdateNo());
        stock.setStckQty(OptionConstDef.MAX_IDEP_STOCK_CNT);
        stock.setRegRnk(1);
        if (productBO.getCtlgVO().isFreshCategory()) {
            stock.setCtlgNo(-1);
        }

        stockBOList.add(stock);
    }

    public long getDealProductStckCnt(ProductVO productVO) throws OptionException {
        try {
            if (ProductConstDef.PrdInfoType.EVENT.equals(productVO.getBaseVO().getPrdInfoType())) {
                return dealMapper.getEventProductStckCnt(productVO);
            } else if (ProductConstDef.PrdInfoType.ROLLBACK.equals(productVO.getBaseVO().getPrdInfoType())) {
                return dealMapper.getRollbackEventProductStckCnt(productVO);
            } else {
                return this.getProductStckCnt(productVO.getPrdNo());
            }
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    private void checkBarcodeDuplicate(ProductStockVO productStockVO) throws OptionException {
        List<ProductStockVO> dupBarcodeList = optionMapper.getBarcodeDuplicateList(productStockVO);

        if (!dupBarcodeList.isEmpty() && dupBarcodeList.size() > dupBarcodeList.get(0).getBarCodeDupChkCnt()) {
            StringBuilder barCodeMsg = new StringBuilder();
            barCodeMsg.append("중복된 바코드가 존재합니다.");
            barCodeMsg.append(" 바코드 : " + dupBarcodeList.get(0).getBarCode());
            barCodeMsg.append(" 상품번호 : ");

            for (ProductStockVO dupBarcodeBO : dupBarcodeList) {
                barCodeMsg.append(dupBarcodeBO.getPrdNo());
                barCodeMsg.append(",");
            }

            throw new OptionException(barCodeMsg.toString().substring(0, barCodeMsg.length() - 1));
        }
    }

    public void setSmartOption(PdOptValueVO pdOptValueVO,
                               Map<String, String> optItemImageMap,
                               long prdNo,
                               String realHardImagePath,
                               String dbImagePath) throws Exception {
        if (pdOptValueVO.getOptItemNo() == 1 && optItemImageMap != null) {
            String prdNoStr = String.valueOf(prdNo).toString();
            String summaryImageValue = optItemImageMap.get(pdOptValueVO.getOptValueNm());
            if (summaryImageValue != null && !"".equals(summaryImageValue)) {
                pdOptValueVO.setDgstExtNm(summaryImageValue);
                File summaryFile = new File(realHardImagePath + summaryImageValue);
                if (summaryFile.isFile()) {
                    String[] smartImageStrArr = summaryImageValue.split("_");
                    String makeName = prdNoStr + summaryImageValue.substring(summaryImageValue.lastIndexOf("_"), summaryImageValue.length());

                    //length : 4 -> summaryImageValue : eg) summary_image_01_yyyyMMDDHHmmsss.jpg
                    if (smartImageStrArr.length == 4) {
                        makeName = prdNoStr + "_" + smartImageStrArr[smartImageStrArr.length - 2] + summaryImageValue.substring(
                            summaryImageValue.lastIndexOf("."),
                            summaryImageValue.length());
                    }
                    pdOptValueVO.setDgstExtNm(dbImagePath + "/" + makeName);

                    //move file (temp to real)
                    if (!StringUtil.equals(realHardImagePath + summaryImageValue, realHardImagePath + dbImagePath + "/" + makeName)) {
                        FileUtil.copy(realHardImagePath + summaryImageValue, realHardImagePath + dbImagePath + "/" + makeName);
                    }
                }
            }
        }
    }

    public boolean findDupOptionInfo(List<PdOptValueVO> checkOptList, PdOptValueVO productOptValueBO) throws OptionException {
        boolean isNew = true;
        if (checkOptList != null && checkOptList.size() > 0) {
            for (int checkIndex = 0; checkIndex < checkOptList.size(); checkIndex++) {
                PdOptValueVO productOptValueInfo = checkOptList.get(checkIndex);

                if (productOptValueBO.getOptValueNm() != null && checkOptList.get(checkIndex) != null && productOptValueBO.getOptValueNm().equals(checkOptList.get(checkIndex).getOptValueNm())) {
                    //중복된 값만 추출 (update처리) : 교집합
                    productOptValueBO.setOptValueSeq(checkOptList.get(checkIndex).getOptValueSeq());
                    productOptValueBO.setCreateDtDate(productOptValueInfo.getCreateDtDate());
                    isNew = false;
                    break;
                }
            }
        }
        return isNew;
    }

    public List<ProductStockVO> getProductOptValueSubCntList(ProductStockVO paramBO) throws OptionException {
        try {
            if (paramBO != null && paramBO.getEventNo() > 0) {
                return dealMapper.getProductOptValueSubCntListForDeal(paramBO);
            } else {
                return optionMapper.getProductOptValueSubCntList(paramBO);
            }
        } catch (OptionException ex) {
            throw new OptionException("옵션 값별 하위 개수 목록 조회 오류", ex);
        } catch (Exception ex) {
            throw new OptionException("옵션 값별 하위 개수 목록 조회 오류", ex);
        }
    }

    public void checkNewStockReg(ProductVO productBO, ProductStockVO productStockBO, int todayVal) throws OptionException {
        // 외부인증번호 사용 시
        if (OptionConstDef.CERT_TYPE_101.equals(productBO.getBaseVO().getCertTypCd())) {
            //  재고 수량이 0보다 클 경우
            if (productStockBO.getStckQty() > 0) {
                throw new OptionException("외부인증번호 사용 시 재고수량 입력이 불가합니다.");
            }

            // 외부 인증번호 사용 시 신규 추가되는 옵션은 품절로 처리 되어야 함.
            if (!OptionConstDef.PRD_STCK_STAT_CD_SOLDOUT.equals(productStockBO.getPrdStckStatCd())) {
                productStockBO.setPrdStckStatCd(OptionConstDef.PRD_STCK_STAT_CD_SOLDOUT);
            }
        }

        // 날짜형 옵션일 경우 신규 날짜 오늘보다 이전인지 체크
        if (OptionConstDef.OPT_TYP_CD_DAY.equals(productBO.getOptionVO().getOptTypCd()) && Integer.parseInt(productStockBO.getOptValueNm1().replaceAll("-", ""), 10) < todayVal) {
            throw new OptionException("오늘보다 이전 날짜는 등록할 수 없습니다. (" + productStockBO.getOptValueNm1() + ")");
        }

        if (productStockBO.getCtlgNo() > 0) {
            HashMap chkMap = new HashMap();
            chkMap.put("ctlgNo", productStockBO.getCtlgNo());
            if (!catalogServiceImpl.getCtlgModelNoCheck(chkMap)) {
                throw new OptionException("등록하신 상품모델은 삭제된 모델입니다. 다시 등록해 주세요. (" + productStockBO.getOptValueNm1() + ")");
            }
        }
    }

    public void setOptImageInfo(ProductStockVO productStockBO, long prdNo, String dbImagePath) throws OptionException, IOException {
        String realHardImagePath = PropertiesManager.getProperty(fileProperty.getUploadPath()); // 물리적으로 저장될 root Path(고정) /data1/upload
        StringBuilder smartOptionDetailImageStr = new StringBuilder();
        ;
        if (StringUtil.isNotEmpty(productStockBO.getDetailImageStr())) {
            String[] detailImage = productStockBO.getDetailImageStr().split(",", 5);
            for (String detailImageStr : detailImage) {
                if (detailImageStr != null && !"".equals(detailImageStr) && !detailImageStr.contains("undefined")) {
                    String replaceDetailImgStr = detailImageStr.replace(",", "");
                    int endLength = replaceDetailImgStr.length();
                    String[] replaceDetailImgStrArr = replaceDetailImgStr.split("_");
                    String makeName = "";
                    if (replaceDetailImgStrArr.length == 5) {
                        int rowIndex = replaceDetailImgStr.lastIndexOf(".");
                        makeName = String.valueOf(prdNo).toString() + "_" + productStockBO.getGroupNumber() + "_" + replaceDetailImgStrArr[replaceDetailImgStrArr.length - 2] + "." + (replaceDetailImgStr.substring(rowIndex + 1, endLength));
                    } else {
                        int rowIndex = replaceDetailImgStr.lastIndexOf("_");
                        makeName = String.valueOf(prdNo).toString() + "_" + productStockBO.getGroupNumber() + "_" + (replaceDetailImgStr.substring(rowIndex + 1, endLength));
                    }

                    File detailImageFile = new File(realHardImagePath + replaceDetailImgStr);
                    if (detailImageFile.isFile()) {
                        smartOptionDetailImageStr.append(dbImagePath + "/" + makeName).append(",");

                        //move file (temp to real)
                        if (detailImageStr.lastIndexOf(",") > -1) detailImageStr = detailImageStr.substring(0, detailImageStr.length() - 1);
                        if (makeName.lastIndexOf(",") > -1) makeName = makeName.substring(0, makeName.length() - 1);
                        if (!StringUtil.equals(realHardImagePath + detailImageStr, realHardImagePath + dbImagePath + "/" + makeName)) {
                            FileUtil.copy(realHardImagePath + detailImageStr, realHardImagePath + dbImagePath + "/" + makeName);
                        }
                    } else {
                        smartOptionDetailImageStr.append(replaceDetailImgStr).append(",");
                    }
                } else {
                    smartOptionDetailImageStr.append(",");
                }
            }
        }
        if (smartOptionDetailImageStr.length() > 0)
            productStockBO.setDetailImageStr(smartOptionDetailImageStr.toString().substring(0, smartOptionDetailImageStr.length() - 1));
    }

    /**
     * 동일 날짜의 승인대기,승인 상태 데이터 존재 여부 조회
     */
    public List<Long> getSameDatePriceAprv(PurchasePrcAprvVO purPrcAprvVO) throws OptionException {
        List<Long> dataList = new ArrayList<Long>();
        try {
            dataList = optionMapper.getSameDatePriceAprv(purPrcAprvVO);
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
        return dataList;
    }

    /**
     * 매입 상품옵션의 가격변경신청 정보 삭제(상태만 update)
     *
     * @param purchasePrcAprvVO
     * @return
     * @throws OptionException
     */
    public int deleteOptionPurchaseInfo(PurchasePrcAprvVO purchasePrcAprvVO) throws OptionException {
        try {
            PdPrdPrcAprv pdPrdPrcAprv = new PdPrdPrcAprv();
            BeanUtils.copyProperties(purchasePrcAprvVO, pdPrdPrcAprv);
            return optionMapper.deleteOptionPurchaseInfo(pdPrdPrcAprv);
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    public void deleteBasisStockAttr(long stckNo) throws OptionException {
        try {
            WmDstStck wmDstStck = new WmDstStck();
            wmDstStck.setStckNo(stckNo);
            optionMapper.deleteWmDstStck(wmDstStck);
        } catch (Exception ex) {
            throw new OptionException("기준재고속성 등록(WM_DST_STCK) 오류가 발생하였습니다.", ex);
        }
    }

    //기준재고속성 등록(WM_DST_STCK)
    public void insertBasisStockAttr(BasisStockVO basisStockVO) throws OptionException {
        try {
            optionMapper.mergeWmDstStck(basisStockVO);
            //WM_DST_STCK_HIST 저장
            WmDstStckHist wmDstStckHist = new WmDstStckHist();
            BeanUtils.copyProperties(basisStockVO, wmDstStckHist);
            optionMapper.insertWmDstStckHist(wmDstStckHist);
        } catch (Exception ex) {
            throw new OptionException("기준재고속성 등록(WM_DST_STCK) 오류가 발생하였습니다.", ex);
        }
    }

    /**
     * @param productStockVOList
     * @param productVO
     * @throws OptionException 기존재고 정보중 변경이 발생한 데이터 삭제
     *                         - 매입 상품의 경우
     */
    public int deleteProductStocks(List<ProductStockVO> productStockVOList, ProductVO productVO) throws OptionException {
        try {
            boolean isBundlePrd = ProductConstDef.SetTypCd.BUNDLE.equals(productVO.getBaseVO().getSetTypCd());
            ItgModelInfoVO itgModelInfoVO = null;

            //sqlMapClient.startBatch();
            int cursorCount = 0;
            for (ProductStockVO productStockVO : productStockVOList) {
                if (productStockVO.getEventNo() > 0) {
                    dealMapper.deleteEventProductStocks(productStockVO);
                } else {
                    if (cursorCount > 100) {
                        //sqlMapClient.executeBatch();
                        //sqlMapClient.startBatch();
                        cursorCount = 0;
                    }
                    if (isBundlePrd) {
                        productStockVO.setChgType(OptionConstDef.HIST_DELETE);
                        PdStockHist pdStockHist = new PdStockHist();
                        BeanUtils.copyProperties(productStockVO, pdStockHist);
                        optionMapper.insertHistDeleteStock(pdStockHist);
                        cursorCount++;
                        PdStockSetInfoHist pdStockSetInfoHist = new PdStockSetInfoHist();
                        BeanUtils.copyProperties(productStockVO, pdStockSetInfoHist);
                        optionMapper.insertHistDeleteAllStockSetInfo(pdStockSetInfoHist);
                        cursorCount++;
                        PdStockSetInfo pdStockSetInfo = new PdStockSetInfo();
                        BeanUtils.copyProperties(productStockVO, pdStockSetInfo);
                        optionMapper.deleteAllStockSetInfo(pdStockSetInfo);
                        cursorCount++;
                    }
                    PdStock pdStock = new PdStock();
                    BeanUtils.copyProperties(productStockVO, pdStock);
                    optionMapper.deleteProductStocks(pdStock);
                    cursorCount++;
                    if (StringUtil.isNotEmpty(productStockVO.getSellerStockCd())) {
                        PdStockPtnrInfo pdStockPtnrInfo = new PdStockPtnrInfo();
                        BeanUtils.copyProperties(productStockVO, pdStockPtnrInfo);
                        optionMapper.deletePartnerStockInfo(pdStockPtnrInfo);
                        cursorCount++;
                    }
                    if (productValidate.isPurchaseType(productVO.getBaseVO()
                                                                .getBsnDealClf()) && ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(
                        productVO.getBaseVO().getSelStatCd())) {
                        PurchasePrcAprvVO purchasePrcAprvBO = new PurchasePrcAprvVO();
                        purchasePrcAprvBO.setPrdNo(productStockVO.getPrdNo());
                        purchasePrcAprvBO.setUpdateNo(productStockVO.getUpdateNo());    // 수정자
                        purchasePrcAprvBO.setStockNo(productStockVO.getStockNo());
                        purchasePrcAprvBO.setAprvStatRsn("옵션삭제");

                        this.deleteOptionPurchaseInfo(purchasePrcAprvBO);
                        cursorCount++;
                    }

                    // 카탈로그에 매칭 되어 있을 경우 상품 매칭 해제
                    if (productStockVO.getCtlgNo() > 0) {
                        itgModelInfoVO = new ItgModelInfoVO();

                        itgModelInfoVO.setMatchCtlgNo(productStockVO.getCtlgNo());
                        itgModelInfoVO.setMatchClfCd(ProductConstDef.MATCH_CLF_CD_PRD);
                        itgModelInfoVO.setPrdNo(productStockVO.getPrdNo());
                        itgModelInfoVO.setUpdateNo(productVO.getUpdateNo());
                        catalogServiceImpl.deleteProductModel(itgModelInfoVO);
                        cursorCount++;
                    }

                    if (ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(productVO.getBaseVO().getSelStatCd()) && !isBundlePrd
                        && (ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(productVO.getBaseVO()
                                                                                         .getBsnDealClf()) || ProductConstDef.DLV_CLF_CONSIGN_DELIVERY
                        .equals(productVO.getBaseVO().getDlvClf()))) {
                        this.deleteBasisStockAttr(productStockVO.getStockNo());
                        cursorCount++;
                    }
                }
            }

            //return sqlMapClient.executeBatch();
            return cursorCount;
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * 상품옵션항목값 데이타를  수정한다.
     */
    public void updateProductStocks(ProductStockVO productStockVO) throws OptionException {
        try {
            optionMapper.updateProductStocks(productStockVO);
        } catch (OptionException ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * 상품옵션항목값 데이타를 배치 방식으로 수정한다.
     */
    public int updateProductStocks(List<ProductStockVO> list, ProductVO productVO) throws OptionException {
        try {
            boolean isBundlePrd = ProductConstDef.SetTypCd.BUNDLE.equals(productVO.getBaseVO().getSetTypCd());
            //sqlMapClient.startBatch();
            int cursourCount = 0;
            ItgModelInfoVO itgModelInfoVO = null;
            for (ProductStockVO productStockBO : list) {
                if (OptionConstDef.HIST_UPDATE.equals(productStockBO.getChgType())) {
                    if (productStockBO.getEventNo() > 0) {
                        dealMapper.updateEventProductStocks(productStockBO);
                    } else {
                        if (cursourCount > 100) {
                            //sqlMapClient.executeBatch();
                            //sqlMapClient.startBatch();
                            cursourCount = 0;
                        }
                        if ((productValidate.isPurchaseType(productVO.getBaseVO().getBsnDealClf()) || ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(productVO.getBaseVO().getDlvClf())) && productVO.getOptionVO().isExistOpt() && ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(productVO.getBaseVO().getSelStatCd())) {
                            if (productValidate.isPurchaseType(productVO.getBaseVO().getBsnDealClf())) {
                                PdPrdPrcAprv pdPrdPrcAprv = new PdPrdPrcAprv();
                                pdPrdPrcAprv.setPrdNo(productStockBO.getPrdNo());
                                pdPrdPrcAprv.setUpdateNo(productStockBO.getUpdateNo());    // 수정자
                                pdPrdPrcAprv.setStockNo(productStockBO.getStockNo());
                                pdPrdPrcAprv.setSelPrc(productVO.getPriceVO().getSelPrc());
                                pdPrdPrcAprv.setAddPrc(productStockBO.getAddPrc());
                                pdPrdPrcAprv.setPuchPrc(productStockBO.getPuchPrc());
                                pdPrdPrcAprv.setMrgnRt(new Double(productStockBO.getMrgnRt()).longValue());
                                pdPrdPrcAprv.setMrgnAmt(productStockBO.getMrgnAmt());

                                optionMapper.updateOptionPurchaseInfo(pdPrdPrcAprv);
                                cursourCount++;

                                productStockBO.setPuchPrc(productVO.getOptionVO().getPuchPrc());
                                productStockBO.setMrgnRt(productVO.getOptionVO().getMrgnRt());
                                productStockBO.setMrgnAmt(productVO.getOptionVO().getMrgnAmt());
                            }

                            productStockBO.setAddPrc(0);

                            this.updateProductStocks(productStockBO);
                            cursourCount++;
                        } else {
                            // 카탈로그 매칭정보가 변경 되었을 경우
                            if (productStockBO.getPrevCtlgNo() > 0 && productStockBO.getPrevCtlgNo() != productStockBO.getCtlgNo()) {
                                itgModelInfoVO = new ItgModelInfoVO();
                                itgModelInfoVO.setMatchCtlgNo(productStockBO.getPrevCtlgNo());
                                itgModelInfoVO.setMatchClfCd(ProductConstDef.MATCH_CLF_CD_PRD);
                                itgModelInfoVO.setPrdNo(productStockBO.getPrdNo());
                                itgModelInfoVO.setUpdateNo(productStockBO.getUpdateNo());
                                //productStockBO.setUpdateType("CTLG_INIT");

                                catalogServiceImpl.deleteProductModel(itgModelInfoVO);
                                cursourCount++;
                            }

                            this.updateProductStocks(productStockBO);
                            cursourCount++;
                        }
                    }
                }

                if (optionValidate.isExtChgType(productStockBO.getExtChgType())) {
                    PdStockPtnrInfo pdStockPtnrInfo = new PdStockPtnrInfo();
                    BeanUtils.copyProperties(productStockBO, pdStockPtnrInfo);
                    optionMapper.deletePartnerStockInfo(pdStockPtnrInfo);
                    cursourCount++;
                }

                if (isBundlePrd && !StringUtil.isEmpty(productStockBO.getStockSetInfoListVO())) {
                    int setInfoCnt = productStockBO.getStockSetInfoListVO().size();
                    for (int j = 0; j < setInfoCnt; j++) {
                        ProductStockSetInfoVO setInfo = productStockBO.getStockSetInfoListVO().get(j);
                        setInfo.setUpdateNo(productStockBO.getUpdateNo());
                        setInfo.setCreateNo(productStockBO.getUpdateNo());

                        PdStockSetInfoHist pdStockSetInfoHist = new PdStockSetInfoHist();
                        BeanUtils.copyProperties(setInfo, pdStockSetInfoHist);

                        if (OptionConstDef.HIST_DELETE.equals(setInfo.getChgTypeCd())) {
                            optionMapper.insertStockSetInfoHist(pdStockSetInfoHist);
                            cursourCount++;
                        }
                        PdStockSetInfo pdStockSetInfo = new PdStockSetInfo();
                        BeanUtils.copyProperties(setInfo, pdStockSetInfo);
                        if (OptionConstDef.USE_I.equals(setInfo.getChgTypeCd())) {
                            optionMapper.processStockSetInfo_I(pdStockSetInfo);
                        } else if (OptionConstDef.USE_U.equals(setInfo.getChgTypeCd())) {
                            optionMapper.processStockSetInfo_U(pdStockSetInfo);
                        } else if (OptionConstDef.USE_D.equals(setInfo.getChgTypeCd())) {
                            optionMapper.processStockSetInfo_D(pdStockSetInfo);
                        }
                        cursourCount++;
                        if (!OptionConstDef.HIST_DELETE.equals(setInfo.getChgTypeCd())) {
                            optionMapper.insertStockSetInfoHist(pdStockSetInfoHist);
                            cursourCount++;
                        }
                    }
                }
            }

            cursourCount = 0;
            for (ProductStockVO productStockBO : list) {
                if (optionValidate.isExtChgType(productStockBO.getExtChgType()) && !OptionConstDef.HIST_DELETE.equals(productStockBO.getExtChgType())) {
                    if (cursourCount > 100) {
                        //sqlMapClient.executeBatch();
                        //sqlMapClient.startBatch();
                        cursourCount = 0;
                    }

                    productStockBO.setSelMnbdNo(productVO.getSelMnbdNo());
                    if (productVO.getMemberVO().isCertStockSeller()) {
                        productStockBO.setUniqChkCd(productStockBO.getStockNo() + "_" + productStockBO.getSellerStockCd());
                    } else {
                        productStockBO.setUniqChkCd(productStockBO.getSellerStockCd());
                    }

                    if (productVO.getMemberVO().isCertStockSeller() && "".equals(productStockBO.getSellerStockCd()) && productStockBO.getStockNo() > 0) {
                        PdStockPtnrInfo pdStockPtnrInfo = new PdStockPtnrInfo();
                        BeanUtils.copyProperties(productStockBO, pdStockPtnrInfo);
                        optionMapper.deletePartnerStockInfo(pdStockPtnrInfo);
                        cursourCount++;
                    } else {
                        if (StringUtil.isNotEmpty(productStockBO.getSellerStockCd())){
                            optionMapper.mergePartnerStockInfo(productStockBO);
                            cursourCount++;
                        }
                        //optionMapper.mergePartnerStockInfo(productStockBO);
                        //cursourCount++;
                    }
                }
            }

            if (!StringUtil.isEmpty(list) && !productVO.getOptionVO().isQCVerifiedStat()) productVO.getOptionVO().setQCVerifiedStat(true);

            //return sqlMapClient.executeBatch() ;
            return cursourCount;
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * 재고를 배치 방식으로 등록한다.
     */
    public int insertProductStocks(List<ProductStockVO> list, ProductVO productBO) throws OptionException {
        try {
            boolean isBundlePrd = ProductConstDef.SetTypCd.BUNDLE.equals(productBO.getBaseVO().getSetTypCd());
            boolean isDirectPurchaseOutDlv = ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(productBO.getBaseVO().getBsnDealClf()) && ProductConstDef.DLV_CLF_OUT_DELIVERY.equals(productBO.getBaseVO().getDlvClf());

            //sqlMapClient.startBatch();
            int cursorCount = 0;
            for (ProductStockVO productStockBO : list) {
                PdStock pdStock = new PdStock();
                long stockNo = (Long) optionMapper.getProductStockSeq();

                if (stockNo > 0) productStockBO.setStockNo(stockNo);
                else throw new OptionException("상품재고 sequence가 0 입니다. 재 시도 바랍니다.");

                if (productStockBO.getEventNo() > 0) {
                    dealMapper.insertEventProductStock(productStockBO);
                } else {
                    if (cursorCount > 100) {
                        //sqlMapClient.executeBatch();
                        //sqlMapClient.startBatch();
                        cursorCount = 0;
                    }

                    if ((productValidate.isPurchaseType(productBO.getBaseVO().getBsnDealClf()) || ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(
                        productBO.getBaseVO().getDlvClf())) && productBO.getOptionVO().isExistOpt()) {
                        if (productValidate.isPurchaseType(productBO.getBaseVO().getBsnDealClf())) {
                            PdPrdPrcAprv pdPrdPrcAprv = new PdPrdPrcAprv();
                            pdPrdPrcAprv.setPrdNo(productStockBO.getPrdNo());
                            if (ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(productBO.getBaseVO().getSelStatCd())) {
                                pdPrdPrcAprv.setAplyBgnDy(DateUtil.getFormatString("yyyy/MM/dd HH"));
                            } else {
                                pdPrdPrcAprv.setAplyBgnDy(DateUtil.formatDate(DateUtils.addHours(DateUtil.getCurrentDate(), 2), "yyyy/MM/dd HH"));
                            }
                            pdPrdPrcAprv.setAprvStatCd("01"); // 가격승인상태 (승인대기)
                            pdPrdPrcAprv.setCupnAplyYn("N"); // 쿠폰적용여부
                            pdPrdPrcAprv.setRequestMnbdClfCd("01"); // 가격승인요청주체구분코드(PO)
                            pdPrdPrcAprv.setRequestNo(productStockBO.getCreateNo()); // 요청자
                            pdPrdPrcAprv.setUpdateNo(productStockBO.getCreateNo());    // 수정자
                            pdPrdPrcAprv.setSelMnbdNo(productBO.getSelMnbdNo());
                            pdPrdPrcAprv.setAprvObjCd("02");
                            pdPrdPrcAprv.setFirstRqstYn("Y");
                            pdPrdPrcAprv.setStockNo(stockNo);
                            pdPrdPrcAprv.setSelPrc(productBO.getPriceVO().getSelPrc());
                            pdPrdPrcAprv.setAddPrc(productStockBO.getAddPrc());
                            pdPrdPrcAprv.setPuchPrc(productStockBO.getPuchPrc());
                            pdPrdPrcAprv.setMrgnRt(new Double(productStockBO.getMrgnRt()).longValue());
                            pdPrdPrcAprv.setMrgnAmt(productStockBO.getMrgnAmt());

                            optionMapper.insertPurchasePriceAprv(pdPrdPrcAprv);
                            cursorCount++;
                            if (!ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(productBO.getBaseVO().getSelStatCd())) {
                                pdPrdPrcAprv.setAplyBgnDy(DateUtil.getFormatString("yyyy/MM/dd HH"));
                                pdPrdPrcAprv.setAprvStatCd("00"); // 가격승인상태 (판매승인완료)
                                pdPrdPrcAprv.setCupnAplyYn("N"); // 쿠폰적용여부
                                pdPrdPrcAprv.setRequestMnbdClfCd("01"); // 가격승인요청주체구분코드(PO)
                                pdPrdPrcAprv.setRequestNo(productStockBO.getCreateNo()); // 요청자
                                pdPrdPrcAprv.setUpdateNo(productStockBO.getCreateNo());    // 수정자
                                pdPrdPrcAprv.setSelMnbdNo(productBO.getSelMnbdNo());
                                pdPrdPrcAprv.setApproveNo(1001);
                                pdPrdPrcAprv.setAprvObjCd("02");
                                pdPrdPrcAprv.setFirstRqstYn("");
                                pdPrdPrcAprv.setStockNo(stockNo);
                                pdPrdPrcAprv.setSelPrc(productBO.getOptionVO().getSelPrc());
                                pdPrdPrcAprv.setAddPrc(0);
                                pdPrdPrcAprv.setPuchPrc(productBO.getOptionVO().getPuchPrc());
                                pdPrdPrcAprv.setMrgnRt(new Double(productBO.getOptionVO().getMrgnRt()).longValue());
                                pdPrdPrcAprv.setMrgnAmt(productBO.getOptionVO().getMrgnAmt());

                                optionMapper.insertPurchasePriceAprv(pdPrdPrcAprv);
                                cursorCount++;
                            }

                            productStockBO.setAddPrc(0);
                            productStockBO.setPuchPrc(productBO.getOptionVO().getPuchPrc());
                            productStockBO.setMrgnRt(productBO.getOptionVO().getMrgnRt());
                            productStockBO.setMrgnAmt(productBO.getOptionVO().getMrgnAmt());
                        }

                        BeanUtils.copyProperties(productStockBO, pdStock);
                        optionMapper.insertProductStock(pdStock);
                        cursorCount++;

                        if (isBundlePrd && !StringUtil.isEmpty(productStockBO.getStockSetInfoListVO())) {
                            int setInfoCnt = productStockBO.getStockSetInfoListVO().size();
                            for (int j = 0; j < setInfoCnt; j++) {
                                ProductStockSetInfoVO setInfo = productStockBO.getStockSetInfoListVO().get(j);
                                setInfo.setMstStckNo(stockNo);
                                setInfo.setMstPrdNo(productBO.getPrdNo());
                                setInfo.setCreateNo(productStockBO.getCreateNo());
                                setInfo.setUpdateNo(productStockBO.getCreateNo());
                                setInfo.setChgTypeCd(OptionConstDef.HIST_INSERT);

                                PdStockSetInfo pdStockSetInfo = new PdStockSetInfo();
                                BeanUtils.copyProperties(setInfo, pdStockSetInfo);
                                optionMapper.processStockSetInfo_I(pdStockSetInfo);
                                cursorCount++;
                                PdStockSetInfoHist pdStockSetInfoHist = new PdStockSetInfoHist();
                                BeanUtils.copyProperties(setInfo, pdStockSetInfoHist);
                                optionMapper.insertStockSetInfoHist(pdStockSetInfoHist);
                                cursorCount++;
                            }
                        }
                    } else {
                        BeanUtils.copyProperties(productStockBO, pdStock);
                        optionMapper.insertProductStock(pdStock);
                        cursorCount++;

                        // 카탈로그 매칭 되었을 경우
                        if (productStockBO.getCtlgNo() > 0) {
                            productStockBO.setMatchClfCd(ProductConstDef.MATCH_CLF_CD_PRD);
                            catalogServiceImpl.mergeProductCatalogMatch(productStockBO);
                            cursorCount++;
                        }
                    }

                    if (StringUtil.isNotEmpty(productStockBO.getSellerStockCd())) {
                        productStockBO.setSelMnbdNo(productBO.getSelMnbdNo());

                        if (productBO.getMemberVO().isCertStockSeller()) {
                            productStockBO.setUniqChkCd(productStockBO.getStockNo() + "_" + productStockBO.getSellerStockCd());
                        } else {
                            productStockBO.setUniqChkCd(productStockBO.getSellerStockCd());

                            // 중복 체크
                            if (optionMapper.getPartnerStockInfoExistCnt(productStockBO) > 0) {
                                throw new OptionException("중복된 판매자 재고번호 입니다. : " + productStockBO.getSellerStockCd());
                            }
                            cursorCount++;
                        }

                        PdStockPtnrInfo pdStockPtnrInfo = new PdStockPtnrInfo();
                        BeanUtils.copyProperties(productStockBO, pdStockPtnrInfo);
                        optionMapper.insertPartnerStockInfo(pdStockPtnrInfo);
                        cursorCount++;
                    }

                    if (!isBundlePrd && isDirectPurchaseOutDlv) {
                        BasisStockVO attrBO = new BasisStockVO();
                        attrBO.setPrdNo(String.valueOf(productStockBO.getPrdNo()));
                        attrBO.setStockNo(String.valueOf(productStockBO.getStockNo()));
                        attrBO.setBarCode(attrBO.getStockNo());
                        attrBO.setCreateNo(String.valueOf(productStockBO.getCreateNo()));
                        attrBO.setUpdateNo(String.valueOf(productStockBO.getCreateNo()));

                        this.insertBasisStockAttr(attrBO);
                        cursorCount++;
                        cursorCount++;
                    }
                }
            }

            if (!StringUtil.isEmpty(list) && !productBO.getOptionVO().isQCVerifiedStat()) productBO.getOptionVO().setQCVerifiedStat(true);

            //return sqlMapClient.executeBatch() ;
            return cursorCount;
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * 스마트옵션 이미지 정보를 등록한다.
     *
     * @param prdNo
     * @param list
     * @return
     * @throws OptionException
     */
    public int insertProductImageStocks(long prdNo, List<ProductStockVO> list) throws OptionException {
        try {
            int resultCnt = 0;

            if (list != null && list.size() > 0) {
                //sqlMapClient.startBatch();
                int i = 1;
                for (ProductStockVO productStockBO : list) {
                    if (i == 1) {
                        PdOptDtlImage pdOptDtlImage = new PdOptDtlImage();
                        pdOptDtlImage.setPrdNo(prdNo);
                        optionMapper.deleteProductOptDtlImage(pdOptDtlImage);
                    }
                    if (i > 1 && i % 401 == 0) {
                        //sqlMapClient.executeBatch();
                        //sqlMapClient.startBatch();
                    }

                    if (productStockBO.getDetailImage() != null && productStockBO.getDetailImage().indexOf("등록됨") > -1) {
                        PdOptDtlImage pdOptDtlImage = new PdOptDtlImage();
                        pdOptDtlImage.setPrdNo(productStockBO.getPrdNo());
                        pdOptDtlImage.setEventNo(productStockBO.getEventNo());

                        pdOptDtlImage.setOptItemNo(productStockBO.getOptItemNo1());
                        pdOptDtlImage.setOptValueNo(productStockBO.getOptValueNo1());
                        pdOptDtlImage.setOptValueNm(productStockBO.getOptValueNm1());

                        String[] detailImage = productStockBO.getDetailImageStr().split(",", 5);
                        pdOptDtlImage.setDtl1ExtNm(detailImage[0]);
                        pdOptDtlImage.setDtl2ExtNm(detailImage[1]);
                        pdOptDtlImage.setDtl3ExtNm(detailImage[2]);
                        pdOptDtlImage.setDtl4ExtNm(detailImage[3]);
                        pdOptDtlImage.setDtl5ExtNm(detailImage[4]);

                        pdOptDtlImage.setCreateNo(productStockBO.getUpdateNo());
                        pdOptDtlImage.setUpdateNo(productStockBO.getUpdateNo());    // 수정자

                        optionMapper.insertProductOptDtlImage(pdOptDtlImage);
                        productStockBO.setQCVerifiedStat(true);
                    }
                    i++;
                }

                //resultCnt = sqlMapClient.executeBatch() ;
            }

            return resultCnt;

        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    public void insertPreOptionPurchaseInfo(List<ProductStockVO> excludeStockList, ProductVO productVO) throws OptionException {
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("stockList", excludeStockList);
            paramMap.put("productVO", productVO);
            optionMapper.insertPreOptionPurchaseInfo(paramMap);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * @param histStockBOList
     * @throws OptionException 현재 상품재고 데이타 히스토리 등록
     */
    public void insertProductStockHist(List<ProductStockVO> histStockBOList) throws OptionException {
        try {
            List<String> dupChkList = new ArrayList<String>();
            StringBuffer sb = new StringBuffer();

            //sqlMapClient.startBatch ();
            for (ProductStockVO stockBo : histStockBOList) {
                sb.setLength(0);
                sb.append(stockBo.getStockNo()).append("_").append(stockBo.getPrdNo()).append("_").append(stockBo.getChgType());

                if (dupChkList.contains(sb.toString())) {
                    continue;
                }

                optionMapper.insertProductStockHist(stockBo);
                dupChkList.add(sb.toString());
            }
            //return sqlMapClient.executeBatch();
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * 상품 옵션명 각각의 옵션값 리스트를 가져온다.
     */
    public List<PdOptValueVO> getProductOptValueLstNew(HashMap hmap) throws OptionException {
        try {
            if (hmap != null && hmap.get("eventNo") != null && Long.parseLong(hmap.get("eventNo").toString()) > 0) {
                return dealMapper.getProductEventOptValueLstNew(hmap);
            } else {
                return optionMapper.getProductOptValueLstNew(hmap);
            }
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * 옵션값 삭제
     *
     * @param productBO
     * @return int
     * @throws OptionException
     */
    public int deleteProductOptValues(ProductVO productBO) throws OptionException {
        try {
            if (productBO.getPriceVO().getEventNo() > 0) {
                return dealMapper.deleteEventProductOptValues(productBO);
            } else {
                PdOptValue pdOptValue = new PdOptValue();
                pdOptValue.setPrdNo(productBO.getPrdNo());
                return optionMapper.deleteProductOptValues(pdOptValue);
            }
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * 옵션값을 배치 방식으로 등록한다.
     *
     * @param list
     * @return int
     * @throws OptionException
     */
    public void insertProductOptValues(List<PdOptValueVO> list) throws OptionException {
        try {
            //sqlMapClient.startBatch();
            for (PdOptValueVO pdOptValueVO : list) {
                if (pdOptValueVO.getEventNo() > 0) {
                    dealMapper.insertEventProductOptValues(pdOptValueVO);
                } else {
                    PdOptValue pdOptValue = new PdOptValue();
                    BeanUtils.copyProperties(pdOptValueVO, pdOptValue);
                    optionMapper.insertProductOptValues(pdOptValue);
                }
            }
            //return sqlMapClient.executeBatch() ;
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * 옵션값 정보를 삭제한다.
     */
    public void deleteProductOptInfo(PdOptValueVO pdOptValueVO) throws OptionException {
        try {
            if (pdOptValueVO.getEventNo() > 0) {
                dealMapper.deleteProductEvtOptInfo(pdOptValueVO);
            } else {
                PdOptValue pdOptValue = new PdOptValue();
                BeanUtils.copyProperties(pdOptValueVO, pdOptValue);
                optionMapper.deleteProductOptInfo(pdOptValue);
            }
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * 옵션값을 배치 방식으로 등록한다.
     *
     * @param list
     * @throws OptionException
     */
    public void insertProductOptInfoValues(List<PdOptValueVO> list) throws OptionException {
        try {
            //sqlMapClient.startBatch();
            int i = 1;
            for (PdOptValueVO pdOptValueVO : list) {

                if (i > 1 && i % 401 == 0) {
                    //sqlMapClient.executeBatch();
                    //sqlMapClient.startBatch();
                }

                if (pdOptValueVO.getEventNo() > 0) {
                    dealMapper.insertProductEvtOptValues(pdOptValueVO);
                } else {
                    PdOptValue pdOptValue = new PdOptValue();
                    BeanUtils.copyProperties(pdOptValueVO, pdOptValue);
                    optionMapper.insertProductOptValueInfoExist(pdOptValue);
                }
                i++;
            }
            //return sqlMapClient.executeBatch() ;
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * 해외명품 상품 옵션/재고 저장(간략PO상품 정규등록시 수행되는 로직)
     *
     * @param productVO
     * @throws OptionException
     */
    public void insertFrProductOpt(ProductVO productVO) throws OptionException {
        try {
            if (!"Y".equals(productVO.getOfferDispLmtYn())) {
                PdOptItem pdOptItem = new PdOptItem();
                BeanUtils.copyProperties(productVO, pdOptItem);
                optionMapper.insertFrProductOptItem(pdOptItem); // 옵션아이템 등록

                PdOptItemHist pdOptItemHist = new PdOptItemHist();
                BeanUtils.copyProperties(productVO, pdOptItemHist);
                optionMapper.insertProductOptItemHist(pdOptItemHist); //옵션 아이템 hist 등록(옵션 고도화 작업에 따른 히스토리 등록 2012.01.02)
            }

            PdStock pdStock = new PdStock();
            BeanUtils.copyProperties(productVO, pdStock);
            optionMapper.insertFrProductStock(pdStock);   // 재고정보 등록

            PdStockHist pdStockHist = new PdStockHist();
            BeanUtils.copyProperties(productVO, pdStockHist);
            optionMapper.insertProductPdStockHist(pdStockHist); //재고 hist 등록(옵션 고도화 작업에 따른 히스토리 등록 2012.01.02)
        } catch (OptionException ex) {
            throw new OptionException("해외 상품 옵션/재고 정보 저장 오류 : ", ex);
        } catch (Exception ex) {
            throw new OptionException("해외 상품 옵션/재고 정보 저장 오류 : ", ex);
        }
    }

    /**
     * 옵션,추가상품 존재여부확인
     *
     * @param prdNo
     * @param gubun
     * @return true/false
     * @throws OptionException
     */
    public boolean isExistOptionOrAddPrduct(long prdNo, String gubun) throws OptionException {
        boolean isCheck = false;
        long prdCnt = 0;
        try {
            if (gubun.equals("1")) { // 옵션
                prdCnt = optionMapper.isExistOption(prdNo);
            } else { // 추가상품
                prdCnt = optionMapper.isExistAddPrduct(prdNo);
            }
            if (prdCnt > 0) {
                isCheck = true;
            }
        } catch (OptionException ex) {
            throw new OptionException("옵션,추가상품 존재여부확인 오류", ex);
        } catch (Exception ex) {
            throw new OptionException("옵션,추가상품 존재여부확인 오류", ex);
        }
        return isCheck;
    }

    /**
     * @param pdOptItemVO
     * @throws OptionException 현재 상품 옵션 아이템 삭제
     */
    public void deleteNewProductOptItem(PdOptItemVO pdOptItemVO) throws OptionException {
        try {
            if (pdOptItemVO.getEventNo() > 0) {
                dealMapper.deleteNewEventProductOptItem(pdOptItemVO);
            } else {
                PdOptItem pdOptItem = new PdOptItem();
                BeanUtils.copyProperties(pdOptItemVO, pdOptItem);
                optionMapper.deleteNewProductOptItem(pdOptItem);
            }
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * @param pdOptItemVO
     * @throws OptionException 현재 상품 옵션 아이템 수정
     */
    public void updateNewProductOptItem(PdOptItemVO pdOptItemVO) throws OptionException {
        try {
            if (pdOptItemVO.getEventNo() > 0) {
                dealMapper.updateNewEventProductOptItem(pdOptItemVO);
            } else {
                PdOptItem pdOptItem = new PdOptItem();
                BeanUtils.copyProperties(pdOptItemVO, pdOptItem);
                optionMapper.updateNewProductOptItem(pdOptItem);
            }
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * 상품옵션항목을 등록한다.
     */
    public void insertProductOptItem(PdOptItemVO pdOptItemVO) throws OptionException {
        try {
            if (pdOptItemVO.getEventNo() > 0) {
                dealMapper.insertEventProductOptItem(pdOptItemVO);
            } else {
                PdOptItem pdOptItem = new PdOptItem();
                BeanUtils.copyProperties(pdOptItemVO, pdOptItem);
                optionMapper.insertProductOptItem(pdOptItem);
            }
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * @param histOptItemVOList
     * @throws OptionException 현재 상품 옵션 아이템 히스토리 등록
     */
    public int insertNewProductOptItemHist(List<PdOptItemVO> histOptItemVOList) throws OptionException {
        try {
            //sqlMapClient.startBatch();
            int i = 0;
            for (PdOptItemVO pdOptItemVO : histOptItemVOList) {
                PdOptItemHist pdOptItemHist = new PdOptItemHist();
                BeanUtils.copyProperties(pdOptItemVO, pdOptItemHist);
                optionMapper.insertNewProductOptItemHist(pdOptItemHist);
                i++;
            }
            //return sqlMapClient.executeBatch();
            return i;
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * 옵션 아이템수 조회 PD_OPT_ITEM (유효한 옵션 아이템 정보 존재 유무 확인)
     *
     * @param productVO
     * @return long  > 0 보다 클 경우 옵션존재
     * @throws OptionException
     */
    public long getProductOptCnt(ProductVO productVO) throws OptionException {
        long optCnt = 0;
        try {
            optCnt = optionMapper.getProductOptCnt(productVO);
        } catch (OptionException ex) {
            throw new OptionException("옵션 아이템수 조회 오류", ex);
        } catch (Exception ex) {
            throw new OptionException("옵션 아이템수 조회 오류", ex);
        }
        return optCnt;
    }

    /**
     * 상품옵션 재고 데이터 유효성(동일코드 옵션재고항목 존재여부 확인) 체크
     */
    public long getOptStockValidCheck(long prdNo) throws OptionException {
        try {
            return optionMapper.optStockValidCheck(prdNo);
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * 상품의 옵션항목구분(조합/독립/입력)에 등록된 개수 조회
     *
     * @param eventNo
     * @param optClfCd
     * @return
     * @throws OptionException
     */
    public int getOptionClfTypeCntForDeal(long eventNo, String optClfCd) throws OptionException {
        try {
            HashMap map = new HashMap();
            map.put("eventNo", eventNo);
            map.put("optClfCd", optClfCd);
            return dealMapper.getOptionClfTypeCntForDeal(map);
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * 상품번호와 재고번호로 재고가 있는지 조회
     *
     * @param param
     * @return
     * @throws OptionException
     */
    public int getPdStockExistCntForDeal(HashMap param) throws OptionException {
        try {
            return dealMapper.getPdStockExistCntForDeal(param);
        } catch (Exception e) {
            throw new OptionException("상품번호와 재고번호로 재고가 있는지 조회 오류", e);
        }
    }

    /**
     * 상품번호와 재고번호로 재고가 있는지 조회
     *
     * @param param
     * @return
     * @throws OptionException
     */
    public int getPdStockExistCnt(HashMap param) throws OptionException {
        try {
            return optionMapper.getPdStockExistCnt(param);
        } catch (Exception e) {
            throw new OptionException("상품번호와 재고번호로 재고가 있는지 조회 오류", e);
        }
    }

    /**
     * 옵션명별 옵션값 개수를 가져온다.
     *
     * @param productStockVO
     * @return
     * @throws OptionException
     */
    public List<ProductStockVO> getOptionValueGroupInfoForDeal(ProductStockVO productStockVO) throws OptionException {
        try {
            return dealMapper.getOptionValueGroupInfoForDeal(productStockVO);
        } catch (OptionException ex) {
            throw new OptionException("옵션명별 옵션값 개수 조회 오류", ex);
        } catch (Exception ex) {
            throw new OptionException("옵션명별 옵션값 개수 조회 오류", ex);
        }
    }

    /**
     * 옵션명별 옵션값 개수를 가져온다.
     *
     * @param productStockVO
     * @return
     * @throws OptionException
     */
    public List<ProductStockVO> getOptionValueGroupInfo(ProductStockVO productStockVO) throws OptionException {
        String exceptionErrMsg = "옵션명별 옵션값 개수 조회 오류";
        try {
            return optionMapper.getOptionValueGroupInfo(productStockVO);
        } catch (OptionException ex) {
            throw new OptionException(exceptionErrMsg, ex);
        } catch (Exception ex) {
            throw new OptionException(exceptionErrMsg, ex);
        }
    }

    public List<ProductStockVO> getProductOptValueSubCntListForDeal(ProductStockVO paramBO) throws OptionException {
        try {
            return dealMapper.getProductOptValueSubCntListForDeal(paramBO);
        } catch (OptionException ex) {
            throw new OptionException("옵션 값별 하위 개수 목록 조회 (쇼킹딜) 오류", ex);
        } catch (Exception ex) {
            throw new OptionException("옵션 값별 하위 개수 목록 조회 (쇼킹딜) 오류", ex);
        }
    }

    /**
     * 계산형 옵션 데이타를 복사한다.
     */
    public void insertProductCalcCopy(ProductVO productVO) throws OptionException {
        try {
            if (productVO.getPriceVO().getEventNo() > 0) {
                dealMapper.insertProductOptCalcForDealCopy(productVO);
            } else {
                optionMapper.insertProductOptCalcCopy(productVO);
            }
        } catch (OptionException ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * 상품옵션 재고정보 데이타를 복사한다.
     */
    public void insertProductStocksCopy(ProductVO productVO) throws OptionException {
        try {
            if (productVO.getPriceVO().getEventNo() > 0) {
                dealMapper.insertEventProductStocksCopy(productVO);
            } else {
                optionMapper.insertProductStocksCopy(productVO);
                if (productValidate.isPurchaseType(productVO.getBaseVO().getBsnDealClf()) && productVO.getOptionVO().isExistOpt()) {
                    optionMapper.insertOptionPurchaseInfoCopy(productVO);

                    PdStock pdStock = new PdStock();
                    BeanUtils.copyProperties(productVO, pdStock);
                    optionMapper.updateProductStockPurchaseInfo(pdStock);
                }
            }
        } catch (OptionException ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * 상품이미지 옵션 리스트를 가져온다.
     */
    public void processProductOptImage(List<PdOptDtlImage> productOptDtlImageList) throws OptionException {
        try {
            //sqlMapClient.startBatch();
            int i = 1;
            for (PdOptDtlImage productOptDtlImageBO : productOptDtlImageList) {
                if (i > 1 && i % 401 == 0) {
                    //sqlMapClient.executeBatch();
                    //sqlMapClient.startBatch();
                }
                optionMapper.updateProductDgstExtNm(productOptDtlImageBO);
                if (productOptDtlImageBO != null && (productOptDtlImageBO.getDtl1ExtNm() != null && !"".equals(productOptDtlImageBO.getDtl1ExtNm())
                    || productOptDtlImageBO.getDtl2ExtNm() != null && !"".equals(productOptDtlImageBO.getDtl2ExtNm())
                    || productOptDtlImageBO.getDtl3ExtNm() != null && !"".equals(productOptDtlImageBO.getDtl3ExtNm())
                    || productOptDtlImageBO.getDtl4ExtNm() != null && !"".equals(productOptDtlImageBO.getDtl4ExtNm())
                    || productOptDtlImageBO.getDtl5ExtNm() != null && !"".equals(productOptDtlImageBO.getDtl5ExtNm()))) {
                    optionMapper.insertProductOptDtlImage(productOptDtlImageBO);
                }
                i++;
            }
            //return sqlMapClient.executeBatch() ;
        } catch (OptionException ex) {
            throw new OptionException(ex);
        } catch (Exception ex) {
            throw new OptionException(ex);
        }
    }

    /**
     * 상품 옵션/재고 정보 복사
     *
     * @param productBO
     * @throws OptionException
     */
    public void insertProductOptCopy(ProductVO productBO) throws OptionException {
        try {
            //옵션명/값 길이 초과체크 여부
            //기등록된 옵션명 / 옵션값 정보를 가져온다.
            PdOptItemVO productOptItemBO = new PdOptItemVO();
            productOptItemBO.setPrdNo(productBO.getBaseVO().getReRegPrdNo());
            List<OptionVO> checkOptionByteList = optionMapper.getOptItemValueNmByteList(productOptItemBO);
            if (checkOptionByteList != null && checkOptionByteList.size() > 0) {
                //예외셀러가 아닐경우 50byte
                boolean isExSeller = false;
                int checkByte = 50;
                //예외셀러일 경우 80byte
                if (StringUtil.nvl(propMgr.get1hourTimeProperty(CacheKeyConstDef.OPT_ITEM_VALUE_EX_SELLER), "").indexOf("|" + productBO.getSelMnbdNo() + "|") > -1) {
                    checkByte = 80;
                    isExSeller = true;
                }
                for (OptionVO optionBO : checkOptionByteList) {
                    if (optionBO != null) {
                        //옵션명은 최대 40byte
                        if (optionBO.getOptItemNm() != null) {
                            int optItemNmByte = Integer.parseInt(optionBO.getOptItemNm());
                            if ((OptionConstDef.OPT_CLF_CD_MIXED.equals(optionBO.getOptClfCd()) || OptionConstDef.OPT_CLF_CD_IDEP.equals(optionBO.getOptClfCd())) && optItemNmByte > 40) {
                                throw new OptionException("옵션명은 공백포함 한글 최대 20자/영문(숫자) 40자를 넘을 수 없습니다.");
                            } else if (OptionConstDef.OPT_CLF_CD_CUST.equals(optionBO.getOptClfCd()) && optItemNmByte > 20) {
                                throw new OptionException("작성형은 공백포함 한글 최대 10자/영문(숫자) 20자를 넘을 수 없습니다.");
                            }
                        }

                        if (optionBO.getOptValueNm() != null && ((OptionConstDef.OPT_CLF_CD_MIXED.equals(optionBO.getOptClfCd()) || OptionConstDef.OPT_CLF_CD_IDEP.equals(optionBO.getOptClfCd())) && Integer.parseInt(optionBO.getOptValueNm()) > checkByte)) {
                            if (isExSeller) {
                                throw new OptionException("옵션값은 공백포함 한글 최대 40자/영문(숫자) 80자를 넘을 수 없습니다.");
                            } else {
                                throw new OptionException("옵션값은 공백포함 한글 최대 25자/영문(숫자) 50자를 넘을 수 없습니다.");
                            }
                        }
                    }
                }
            }

            // 옵션아이템 등록
            optionMapper.insertProductOptItemCopy(productBO);
            // 옵션아템 히스토리 등록
            optionMapper.insertProductOptItemHistCopy(productBO);
            // 옵션값 등록
            optionMapper.insertProductOptValueCopy(productBO);
            // 재고정보 등록
            this.insertProductStocksCopy(productBO);
            // 재고정보 히스토리 등록
            optionMapper.insertProductStocksHistCopy(productBO);
            // 지정일 상품코드 정보 등록
            optionMapper.productStocksPtnrInfoCopy(productBO);

            CategoryVO categoryVO = categoryServiceImpl.getServiceDisplayCategoryInfo(productBO.getDispCtgrNo());
            if (productBO.getCategoryVO() != null) {
                if (OptionConstDef.OPT_TYP_CD_CALC.equals(productBO.getOptionVO().getOptTypCd()) && StringUtil.nvl(propMgr.get1hourTimeProperty(CacheKeyConstDef.CALC_OPT_USE_CATEGORY), "").indexOf("|" + categoryVO.getRootCtgrNo() + "|") < 0) {
                    throw new OptionException("계산형 옵션으로 등록할 수 없는 카테고리입니다.");
                }

                ProductVO checkProductBO = new ProductVO();
                checkProductBO.setBaseVO(new BaseVO());
                checkProductBO.setPriceVO(new PriceVO());
                checkProductBO.setPrdNo(productBO.getPrdNo());
                checkProductBO.getBaseVO().setReRegPrdNo(productBO.getPrdNo());
                checkProductBO.setDispCtgrNo(productBO.getDispCtgrNo());
                checkProductBO.setSelMnbdNo(productBO.getSelMnbdNo());
                checkProductBO.getBaseVO().setBsnDealClf(productBO.getBaseVO().getBsnDealClf());
                optionValidate.checkOptLimit(checkProductBO, false);

                //계산형 옵션 등록
                this.insertProductCalcCopy(productBO);

                //계산형 옵션 이력 등록
                //optionMapper.insertProductCalcHistCopy(productBO);
                PdPrdOptCalcHist pdPrdOptCalcHist = new PdPrdOptCalcHist();
                BeanUtils.copyProperties(productBO, pdPrdOptCalcHist);
                optionMapper.insertProductOptCalcHistCopy(pdPrdOptCalcHist);
            }

            //스마트옵션이미지상세정보등록
            List<PdOptDtlImage> productOptImageList = productImageServiceImpl.setOptionImage(productBO,
                                                                                             productBO.getBaseVO().getReRegPrdNo(),
                                                                                             productBO.getPrdNo(),
                                                                                             false,
                                                                                             false);

            //정보 update and insert
            this.processProductOptImage(productOptImageList);
        } catch (OptionException ex) {
            throw new OptionException("상품 옵션/재고 정보 복사 오류 : ", ex);
        } catch (Exception ex) {
            throw new OptionException("상품 옵션/재고 정보 복사 오류 : " + ex.getMessage(), ex);
        }
    }

    /**
     * 상품 옵션 아이템 정보 등록/수정
     *
     * @param productVO
     * @throws OptionException
     */
    public void insertPdOptItem(ProductVO productVO) throws OptionException {
        try {
            long prdNo = productVO.getPrdNo();                                // 상품번호
            String curDate = DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyy/MM/dd HH:mm:ss");    // 현재 날짜
            PdOptItemVO optItemBO = null;                                                // 임시 옵션 아이템 BO
            PdOptItemVO preOptItemBO = null;                                            // 임시 옵션 아이템 BO
            List<PdOptItemVO> prevOptItemBOList = null;                                // 이전 옵션 아이템 정보 리스트
            List<PdOptItemVO> histOptItemBOList = new ArrayList<PdOptItemVO>();// 변경 히스토리 옵션 아이템 정보 리스트
            Map<String, PdOptItemVO> optItemMap = productVO.getOptionVO().getOptItemMap();        // 옵션명 (아이템) 맵(옵션명, 옵션 아이템 BO) (ProductOptItemBO)

            // 1-1. 이전 옵션 정보 조회
            preOptItemBO = new PdOptItemVO();
            optItemBO = new PdOptItemVO();
            optItemBO.setPrdNo(prdNo);
            optItemBO.setEventNo(productVO.getPriceVO().getEventNo());
            //optItemBO.setPrdInfoType(productValidate.getStrPrdInfoType(productVO.getBaseVO().getPrdInfoType()));
            optItemBO.setPrdInfoType(productVO.getBaseVO().getPrdInfoType());
            prevOptItemBOList = (ArrayList<PdOptItemVO>) this.getProductOptItemLst(optItemBO);
            productVO.getMemberVO().setFrSeller(productValidate.isPurchaseType(productVO.getBaseVO().getBsnDealClf()) && customerServiceImpl.isFrSellerByPrdNo(prdNo));

            boolean isWriteOptExist = false;
            boolean isOptTypCdExist = false;

            // 1-2. 변경 옵션맵에 기존 옵션명이 존재하는지 여부에 따른 처리
            for (PdOptItemVO prevProductOptItemBO : prevOptItemBOList) {

                // 옵션항목구분코드:옵션명 ex) 01:색상
                String optItemKey = prevProductOptItemBO.getOptClfCd() + OptionConstDef.OPTION_ITEM_VALUE_DELIMITER + prevProductOptItemBO.getOptItemNm();

                // 기존 옵션명이 변경 옵션맵에 존재할 경우 ExistOptItem setter에 설정
                if (optItemMap.containsKey(optItemKey)) {
                    optItemBO = optItemMap.get(optItemKey);
                    optItemBO.setExistOptItem(true);
                    optItemBO.setCreateDt(prevProductOptItemBO.getCreateDt());
                    optItemBO.setUpdateDt(curDate);

                    String asis = prevProductOptItemBO.getOptItemNo() + OptionConstDef.OPTION_ITEM_VALUE_DELIMITER + prevProductOptItemBO.getStockClmnPos() + OptionConstDef.OPTION_ITEM_VALUE_DELIMITER + prevProductOptItemBO.getStatCd() + OptionConstDef.OPTION_ITEM_VALUE_DELIMITER + prevProductOptItemBO.getPrdExposeClfCd();
                    String tobe = optItemBO.getOptItemNo() + OptionConstDef.OPTION_ITEM_VALUE_DELIMITER + optItemBO.getStockClmnPos() + OptionConstDef.OPTION_ITEM_VALUE_DELIMITER + optItemBO.getStatCd() + OptionConstDef.OPTION_ITEM_VALUE_DELIMITER + optItemBO.getPrdExposeClfCd();

                    // 히스토리 수정 타입 설정
                    if (!asis.equals(tobe)) {
                        optItemBO.setChgType(OptionConstDef.HIST_UPDATE);
                        histOptItemBOList.add(optItemBO);
                    }
                }
                // 기존 옵션명이 변경 옵션맵에 존재하지 않을 경우 해당 옵션명 삭제
                else {
                    this.deleteNewProductOptItem(prevProductOptItemBO);    // 옵션명 삭제
                    prevProductOptItemBO.setChgType(OptionConstDef.HIST_DELETE);        // 히스토리 삭제 타입 설정
                    histOptItemBOList.add(prevProductOptItemBO);
                }

                preOptItemBO = prevProductOptItemBO;

            }

            // 1-3. ProductOptItemBO 등록/수정
            // 옵션 아이템이 존재할 경우
            if (optItemMap != null) {
                Iterator<Map.Entry<String, PdOptItemVO>> item = optItemMap.entrySet().iterator();

                while (item.hasNext()) {

                    Map.Entry<String, PdOptItemVO> entry = item.next();

                    optItemBO = entry.getValue();
                    optItemBO.setPrdNo(prdNo);
                    optItemBO.setEventNo(productVO.getPriceVO().getEventNo());

                    // 기존 옵션명이 존재하는지 여부에 따라 처리
                    if (optItemBO.isExistOptItem()) {
                        this.updateNewProductOptItem(optItemBO);    // 존재할 경우 수정
                    } else {
                        optItemBO.setCreateDt(curDate);
                        optItemBO.setUpdateDt(curDate);

                        this.insertProductOptItem(optItemBO);        // 존재하지 않을 경우 등록

                        if (productVO.getPriceVO().getEventNo() <= 0) {
                            // 히스토리 등록 타입 설정
                            optItemBO.setChgType(OptionConstDef.HIST_INSERT);
                            histOptItemBOList.add(optItemBO);
                        }
                    }

                    if (productVO.getPriceVO().getEventNo() <= 0) {
                        if (optionValidate.IMPOSSIBLE_OPT_TYP_CD.contains(optItemBO.getOptTypCd())) {
                            isOptTypCdExist = true;
                        }

                        if (OptionConstDef.OPT_CLF_CD_CUST.equals(optItemBO.getOptClfCd()) && OptionConstDef.OPT_ITEM_STAT_CD_USE.equals(
                            optItemBO.getStatCd())) {
                            isWriteOptExist = true;
                        }
                    }

                    // 옵션 항목구분 수정 여부
                    if (!optItemBO.getOptClfCd().equals(preOptItemBO.getOptClfCd())) productVO.getOptionVO().setChgOptClfCd(true);
                }
            }

            // 1-4. ProductOptItemBO History 등록
            if (productVO.getPriceVO().getEventNo() <= 0) this.insertNewProductOptItemHist(histOptItemBOList);

            // 작성형 옵션 또는 정기결제 설정 불가한 옵션유형 등록되었을 경우
            if (isWriteOptExist || isOptTypCdExist) {
                deliveryServiceImpl.updateProductReglDeliveryInfo(productVO);
            }
        } catch (OptionException ex) {
            throw new OptionException("옵션 아이템 정보 저장에러 : ", ex);
        } catch (Exception ex) {
            throw new OptionException("옵션 아이템 정보 저장에러 : ", ex);
        }
    }

    /**
     * 상품 재고 등록/수정
     * - 여기 호출되기 이전에 묶음 상품의 경우 삭제된 옵션은 별도 선 처리한다.
     *
     * @param productVO
     * @param _type
     * @throws OptionException
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void insertPdStock(ProductVO productVO, String _type) throws OptionException {
        String tmallExceptionErrClsMsg = "";
        String tmallExceptionErrMsg = "";
        try {
            if (_type == null) _type = "";

            long prdNo = productVO.getPrdNo();                // 상품번호
            String curDate = DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyy/MM/dd HH:mm:ss");    // 현재 날짜
            long oriTotOptStckCnt = 0;                              // 수정일 경우 기존에 저장된 옵션 총 재고 수량
            long totOptStckCnt = 0;                                // 적용할 옵션  총 재고 수량
            Integer stckIdx = null;
            PdOptItemVO optItemBO = null;                                // 임시 옵션 아이템 BO
            ProductStockVO stockBO = null;                                // 임시 옵션 재고 BO
            int selOptCnt = (int) productVO.getOptionVO().getOptColCnt();    // 조합형 옵션명 수
            Map<String, PdOptItemVO> optItemMap = productVO.getOptionVO()
                                                           .getOptItemMap();        // 옵션명 (아이템) 맵(옵션명, 옵션 아이템 BO) (ProductOptItemBO)
            Map<String, String> optItemImageMap = productVO.getOptionVO().getOptItemImageMap();        // 스마트옵션 요약이미지 맵
            Map<String, String> optItemDetailImageMap = productVO.getOptionVO().getOptItemDetailImageMap();        // 스마트옵션 자세히보기 요약이미지 맵
            HashMap<String, Integer> stckIndexMap = productVO.getOptionVO()
                                                             .getStckIndexMap();        // 조합형 재고에 중복 데이터 유무 체크 맵 ({색상:빨간색†사이즈:95, 0}, {색상:빨간색†사이즈:100, 1})
            List<ProductStockVO> prevStockBOList = null;                                // 이전 옵션 재고 정보 리스트
            List<ProductStockVO> delPrevStockBoList = new ArrayList<ProductStockVO>();    // 이전 옵션 재고 삭제 리스트
            List<ProductStockVO> histStockBOList = new ArrayList<ProductStockVO>();    // 변경 히스토리 옵션 재고 정보 리스트
            List<ProductStockVO> stockBOList = (ArrayList<ProductStockVO>) productVO.getOptionVO()
                                                                                    .getProductStockVOList();    // 조합형 재고 BO ArrayList (ProductStockBO)
            ProductStockVO singleProductStockBO = new ProductStockVO();            // 옵션이 없는 단일 상품

            String realHardImagePath = PropertiesManager.getProperty(fileProperty.getUploadPath()); // 물리적으로 저장될 root Path(고정) /data1/upload
            String dbImagePath = productImageServiceImpl.getImageStoragePath(prdNo, ImageDir.SMART_OPTION);

            String exceptionStockCntZeroMsg = "상품재고 수량이 0개 입니다. 재 등록 바랍니다.";

            if (prdNo <= 0) throw new OptionException("옵션 등록에 필요한 상품번호가 존재하지 않습니다.");

            boolean isIdepOpt = false;
            boolean isCustOpt = false;
            boolean isOptExist = true;

            ProductOptLimitVO productOptLimitVO = new ProductOptLimitVO();
            productOptLimitVO.setPrdNo(prdNo);
            productOptLimitVO.setDispCtgrNo(productVO.getDispCtgrNo());
            productOptLimitVO.setSellerNo(productVO.getSelMnbdNo());
            productOptLimitVO.setOptLmtObjNo(productVO.getDispCtgrNo());
            productOptLimitVO.setStdPrdYn(productVO.getBaseVO().getStdPrdYn());
            //PO, 쇼킹딜 상품일 경우
            if (!ProductConstDef.BSN_DEAL_CLF_COMMISSION.equals(productVO.getBaseVO().getBsnDealClf())) {
                //PO 일 경우
                productOptLimitVO.setOptLmtType("PO");
                if (!productVO.getSellerVO().isSellableBundlePrdSeller())
                    productVO.getSellerVO().setSellableBundlePrdSeller(sellerValidate.isDirectBuyingMark(productVO.getMemberVO()));
                if (!productVO.getSellerVO().isSellableBundlePrdSeller() && !productVO.getSellerVO().isOnlyUsableSetTypCd())
                    productVO.getSellerVO().setOnlyUsableSetTypCd(sellerValidate.isOnlyUsableSetTypCd(productVO.getMemberVO()));
            } else if (productVO.getPriceVO().getEventNo() > 0) {
                //쇼킹딜 일 경우
                productOptLimitVO.setOptLmtType("SHOCKDEAL");
            }
            ProductOptLimitVO resProductOptLimitBO = this.getProductOptLimitForSellerSet(productOptLimitVO);

            boolean checkIsIdepOpt = false;
            int custOptCnt = 0;
            List<ProductStockVO> optItemList = new ArrayList<ProductStockVO>();

            //옵션 제한정책 체크
            boolean[] retIdepMixData = optionValidate.checkRestrictOption(optItemMap,
                                                                          productVO,
                                                                          optItemBO,
                                                                          resProductOptLimitBO,
                                                                          optItemList,
                                                                          stockBOList,
                                                                          _type,
                                                                          custOptCnt,
                                                                          selOptCnt,
                                                                          isIdepOpt,
                                                                          isCustOpt,
                                                                          checkIsIdepOpt);
            isIdepOpt = retIdepMixData[0];
            checkIsIdepOpt = retIdepMixData[1];
            isCustOpt = retIdepMixData[2];

            boolean barcodeDuplicateCheck = !ProductConstDef.SetTypCd.BUNDLE.equals(productVO.getBaseVO().getSetTypCd()) && sellerServiceImpl.isBarcodeDuplicateCheckSeller(productVO);
            boolean isOnlyOneBarCode = barcodeDuplicateCheck && (ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(productVO.getBaseVO().getDlvClf()) || productVO.getSellerVO().isSellableBundlePrdSeller());

            productVO.setIdepOpt(isIdepOpt);
            productVO.setCustOpt(isCustOpt);

            // 1-1. 이전 재고 정보 조회
            ProductStockVO productStockVO = new ProductStockVO();
            productStockVO.setPrdNo(prdNo);
            productStockVO.setEventNo(productVO.getPriceVO().getEventNo());
            productStockVO.setBsnDealClf(productVO.getBaseVO().getBsnDealClf());
            productStockVO.setSetTypCd(productVO.getBaseVO().getSetTypCd());
            productStockVO.setStdPrdYn(productVO.getBaseVO().getStdPrdYn());
            prevStockBOList = (ArrayList<ProductStockVO>) this.getExistProductStockLst(productStockVO);

            optionValidate.checkConsignNBsnDirectOption(productVO, prevStockBOList, selOptCnt, custOptCnt);

            // 1-2. 이전 재고 정보가 존재할 경우  변경 전 STOCKNO를 변경 후 STOCKNO에 설정할지 여부를 결정하는 PROCESS.
            if (prevStockBOList.size() > 0) {
                ProductStockVO prevStockBO = prevStockBOList.get(0);
                prevStockBO.setBsnDealClf(productVO.getBaseVO().getBsnDealClf());

                int prevSelOptCnt = 0;    // 이전 옵션 아이템 수

                if (prevStockBO.getOptItemNo5() > 0) prevSelOptCnt = 5;
                else if (prevStockBO.getOptItemNo4() > 0) prevSelOptCnt = 4;
                else if (prevStockBO.getOptItemNo3() > 0) prevSelOptCnt = 3;
                else if (prevStockBO.getOptItemNo2() > 0) prevSelOptCnt = 2;
                else if (prevStockBO.getOptItemNo1() > 0) prevSelOptCnt = 1;

                // 변경할 옵션 아이템이 존재하고, 옵션 아이템 수가 일치할 경우에만 기존 재고 리스트에서 재고 항목 변경 체크
                if (selOptCnt > 0 && prevSelOptCnt == selOptCnt && (!"ALL".equals(_type))) {

                    // 옵션 아이템 등록순에 따라 옵션 아이템명 Mapping variable
                    Map<Long, String> optItemNmMap = new HashMap<Long, String>();
                    // 옵션 아이템이 존재할 경우
                    if (optItemMap != null) {
                        Iterator<Map.Entry<String, PdOptItemVO>> item = optItemMap.entrySet().iterator();
                        while (item.hasNext()) {
                            Map.Entry<String, PdOptItemVO> entry = item.next();
                            optItemBO = entry.getValue();
                            optItemNmMap.put(optItemBO.getOptItemNo(), optItemBO.getOptItemNm());    // 옵션 아이템 등록순에 따라 옵션 아이템명 Mapping
                        }
                    }

                    boolean isMatched = false;

                    // 조합형,작성형 옵션일 경우
                    if (!isIdepOpt) {
                        // 우선 변경전 재고 리스트의 항목을 모두 비교하기 전에 옵션 아이템의 변경전,후의 갯수를 비교하여 제일 마지막 아이템명을 서로 비교하여 모두 비교하는 Case를 줄인다.
                        Method method = prevStockBO.getClass().getMethod("getOptItemNm" + (selOptCnt));
                        String prevOptNm = (String) method.invoke(prevStockBO, new Object[0]);    // 이전 옵션 아이템 명
                        String optNm = optItemNmMap.get((long) selOptCnt);                    // 현재 옵션 아이템 명

                        if (optNm.equals(prevOptNm)) isMatched = true;

                        // 위에서 마지막 아이템명이 일치할 경우 변경할 재고 리스트와 기존재고 리스트를 서로 비교하여 기존재고에서 재고번호를 가져옴.(변경사항이 없을 경우에만 STOCKNO를 가져옴.)
                        if (isMatched) {
                            Method tempMethod1;        // 옵션 아이템 reflection nmethod
                            Method tempMethod2;        // 옵션 값 reflection method
                            StringBuilder _stockKey;    // 재고 테이블에 들어있는 옵션명:옵션값 조합 index key ex)색상:빨간색†사이즈:95, 색상:빨간색†사이즈:100

                            // 이전 재고 정보 반복문 수행하면서 재고조합 내용이 변경전,후가 같을 경우 변경 전 STOCKNO를 변경 후 STOCKNO에 설정을 한다.
                            for (int i = 0; i < prevStockBOList.size(); i++) {
                                prevStockBO = prevStockBOList.get(i);
                                _stockKey = new StringBuilder();

                                // 재고정보 중복 체크를 위한 키 생성 ex)색상:빨간색†사이즈:95
                                for (int j = 1; j < selOptCnt + 1; j++) {
                                    tempMethod1 = prevStockBO.getClass().getMethod("getOptItemNm" + (j));
                                    tempMethod2 = prevStockBO.getClass().getMethod("getOptValueNm" + (j));

                                    if (StringUtil.isNotEmpty(_stockKey.toString()))
                                        _stockKey.append(OptionConstDef.OPTION_DELIMITER);    // String Split 옵션 구분자 (†)
                                    _stockKey.append((String) tempMethod1.invoke(prevStockBO, new Object[0]));    // 옵션 아이템명
                                    _stockKey.append(OptionConstDef.OPTION_ITEM_VALUE_DELIMITER);                // Option 옵션명/옵션값 구분자 (:)
                                    _stockKey.append((String) tempMethod2.invoke(prevStockBO, new Object[0]));    // 옵션 값
                                }

                                // 재고의 순서대로 등록되어 있는 Mapper에서 생성키에 해당하는 index를 추출.
                                stckIdx = stckIndexMap.get(_stockKey.toString());
                                // 해당 index 존재 시  변경할 재고정보 리스트에서  변경할 재고정보를 추출하여 해당 BO에 이전 STOCKNO를 설정함.
                                if (stckIdx != null) {
                                    stockBO = stockBOList.get(stckIdx.intValue());    // index에 해당하는 변경할 재고정보를 변경할 재고정보 리스트에서 추출. (ProductStockBO)
                                    if (!"Y".equals(productVO.getIsOpenApi()) && !"Y".equals(productVO.getPrdCopyYn())) {
                                        stockBO.setUniqChkCd(prevStockBO.getUniqChkCd());
                                        if (!productVO.getMemberVO().isCertStockSeller()) {
                                            stockBO.setSellerStockCd(prevStockBO.getSellerStockCd());
                                        }
                                    }

                                    // 컬럼 추가시 비교 StringBuffer에 반드시 추가해 준다.
                                    StringBuilder tobe = optionValidate.setCheckDiffOptionInfo(stockBO, productVO, null);
                                    StringBuilder asis = optionValidate.setCheckDiffOptionInfo(prevStockBO, productVO, stockBO);

                                    // 외부인증번호 사용 시 이전데이타에서 상태값을 제외하고 수정 불가.
                                    optionValidate.checkOptionInfo(productVO, stockBO, prevStockBO, isOnlyOneBarCode, asis, tobe);

                                    tobe.append(stockBO.getPrdStckStatCd());
                                    asis.append(prevStockBO.getPrdStckStatCd());

                                    stockBO.setChgType("");  // 히스토리 수정 타입 설정
                                    // 이전 정보와 현재 정보 비교 후 수정 여부 결정
                                    if (!tobe.toString().equals(asis.toString())) {
                                        stockBO.setStockNo(prevStockBO.getStockNo());    // 변경할 재고정보 BO에 이전 STOCKNO를 설정.
                                        stockBO.setCreateDt(prevStockBO.getCreateDt());    // 이전 생성일 설정
                                        stockBO.setUpdateDt(curDate);                    // 현재 수정일 설정
                                        stockBO.setSelQty(prevStockBO.getSelQty());        // 판매수량
                                        stockBO.setIsOpenApi(productVO.getIsOpenApi()); // OpenAPI 여부
                                        stockBO.setChgType(OptionConstDef.HIST_UPDATE);  // 히스토리 수정 타입 설정
                                    }

                                    // 셀러재고번호 변경 이력 타입 설정
                                    if (prevStockBO.getSellerStockCd() != null && prevStockBO.getSellerStockCd().equals(stockBO.getSellerStockCd())) {
                                        stockBO.setExtChgType("");
                                    } else {
                                        stockBO.setExtChgType(OptionConstDef.HIST_UPDATE);
                                        if (StringUtil.isEmpty(prevStockBO.getSellerStockCd())) {
                                            stockBO.setExtChgType(OptionConstDef.HIST_INSERT);
                                        } else if (StringUtil.isEmpty(stockBO.getSellerStockCd())) {
                                            stockBO.setExtChgType(OptionConstDef.HIST_DELETE);
                                        }
                                    }

                                    // 묶음상품 구성 변경 이력 타입 설정, db데이터와 비교하여 새로 list생성하여 setter한다
                                    if (ProductConstDef.SetTypCd.BUNDLE.equals(productVO.getBaseVO().getSetTypCd())) {
                                        List<ProductStockSetInfoVO> preStockSetInfo = prevStockBO.getStockSetInfoListVO();
                                        List<ProductStockSetInfoVO> stockSetInfo = stockBO.getStockSetInfoListVO();
                                        List<ProductStockSetInfoVO> workedSetInfo = new ArrayList<ProductStockSetInfoVO>();

                                        if (preStockSetInfo.size() > stockSetInfo.size()) {
                                            optionValidate.setStockBundleInfo(preStockSetInfo, stockSetInfo, workedSetInfo);
                                        } else {
                                            optionValidate.setStockBundleInfo(stockSetInfo, preStockSetInfo, workedSetInfo);
                                        }

                                        stockBO.setStockSetInfoListVO(workedSetInfo);

                                        if (!StringUtil.isEmpty(workedSetInfo) && !OptionConstDef.HIST_UPDATE.equals(stockBO.getChgType())) {
                                            stockBO.setChgType(OptionConstDef.HIST_ONLY_MAPPING_UPDATE);
                                        }
                                    }
                                }
                                // 해당 index 미 존재 시 삭제 될 대상 데이터를 리스트에 담는다.
                                else {
                                    delPrevStockBoList.add(prevStockBO);
                                    prevStockBO.setChgType(OptionConstDef.HIST_DELETE);        // 히스토리 삭제 타입 설정
                                    histStockBOList.add(prevStockBO);
                                }
                            }
                        } else {
                            delPrevStockBoList = prevStockBOList;

                            for (ProductStockVO prevProductStockVO : prevStockBOList) {
                                prevProductStockVO.setChgType(OptionConstDef.HIST_DELETE);        // 히스토리 삭제 타입 설정
                                histStockBOList.add(prevProductStockVO);
                            }
                        }
                    }
                    // 독립형 옵션일 경우
                    /* TODO: 독립형 삭제
                    else{
                        // 현재 옵션 아이템과 이전 옵션 아이템의 변경 여부 체크
                        for (int i = 1; i <= selOptCnt; i++) {
                            Method method =  prevStockBO.getClass().getMethod("getOptItemNm"+(i));
                            String prevOptNm = (String) method.invoke(prevStockBO, new Object[0]);	// 이전 옵션 아이템 명
                            String optNm 	 = optItemNmMap.get((long)i);							// 현재 옵션 아이템 명

                            if(optNm.equals(prevOptNm)) {
                                isMatched = true;
                            } else {
                                isMatched = false;
                                break;
                            }
                        }

                        // 독립형에서 옵션 항목구분 수정 발생시
                        if (productVO.getOptionVO().isChgOptClfCd()) isMatched = false;

                        // 위에서 모든 아이템명이 일치할 경우 옵션값의 변경 여부를 판단한다.
                        if (isMatched) {
                            PdOptItemVO pdOptItemVO = new PdOptItemVO();
                            pdOptItemVO.setPrdNo(prdNo);
                            pdOptItemVO.setEventNo(productVO.getPriceVO().getEventNo());
                            // 현재 저장되어 있는 옵션값 데이터 조회
                            List<OptionVO> preOptValueList = this.getOptValueList(pdOptItemVO);

                            // 현재 저장되어 있는 옵션값과 저장하려는 옵션값을 비교 후 수정 여부 결정
                            for (OptionVO optionVO : preOptValueList) {

                                PdOptItemVO itemBO = optItemMap.get(OptionConstDef.OPT_CLF_CD_IDEP+OptionConstDef.OPTION_ITEM_VALUE_DELIMITER+optionVO.getOptItemNm());

                                // 먼저 옵션값의 갯수가 일치하지 않으면 반복문을 빠져 나온다. (변경 발생으로 판단함)
                                if (optionVO.getCnt() != itemBO.getPdOptValueVOList().size()) {
                                    isMatched = false;
                                    break;
                                }

                                StringBuilder asis = new StringBuilder();
                                asis.append(optionVO.getOptItemNo()).append(OptionConstDef.OPTION_DELIMITER).append(optionVO.getOptValueNo()).append(OptionConstDef.OPTION_DELIMITER)
                                    .append(optionVO.getOptValueNm()).append(OptionConstDef.OPTION_DELIMITER).append(optionVO.getPrdStckStatCd());

                                PdOptValueVO pdOptValueVO = itemBO.getPdOptValueVOList().get((int)optionVO.getOptValueNo()-1);

                                StringBuilder tobe = new StringBuilder();
                                tobe.append(pdOptValueVO.getOptItemNo()).append(OptionConstDef.OPTION_DELIMITER).append(pdOptValueVO.getOptValueNo()).append(OptionConstDef.OPTION_DELIMITER)
                                    .append(pdOptValueVO.getOptValueNm()).append(OptionConstDef.OPTION_DELIMITER).append(pdOptValueVO.getPrdStckStatCd());

                                // 이전 정보와 현재 정보 비교 후 수정 여부 결정
                                if (!tobe.toString().equals(asis.toString())) {
                                    isMatched = false;
                                    break;
                                }
                            }
                        }

                        // 옵션에 수정이 발생하였을 경우
                        if (!isMatched) {
                            // 현재 저장되어 있는 옵션 재고 모두 삭제
                            delPrevStockBoList = prevStockBOList;

                            for (ProductStockVO prevProductStockVO : prevStockBOList) {
                                prevProductStockVO.setChgType(OptionConstDef.HIST_DELETE);		// 히스토리 삭제 타입 설정
                                histStockBOList.add(prevProductStockVO);
                            }

                            // 독립형 옵션 재고 하나의 데이터 row만 생성한다. (재고가 없으면 품절로 표시 되므로)
                            this.createIdepOptionStock(optItemMap, optItemBO, stockBOList, productVO);
                            if(!StringUtil.isEmpty(stockBOList)){
                                ProductStockVO productStockBO = stockBOList.get(0);
                                productStockBO.setPuchPrc(productVO.getOptionVO().getPuchPrc());
                                productStockBO.setMrgnRt(productVO.getOptionVO().getMrgnRt());
                                productStockBO.setMrgnAmt(productVO.getOptionVO().getMrgnAmt());
                            }
                        }
                        // 옵션에 수정이 발생하지 않았을 경우
                        else {
                            stockBOList = prevStockBOList;
                        }
                    }
                    */
                }
                //  독립형 옵션이 아니고 옵션이 없을 경우. (독립형 옵션은 앞에서 재고 데이터를 생성하지 않으므로 조건에서 반드시 독립형 여부를 체크해 줘야 함.)
                else if (!isIdepOpt && (stockBOList == null || stockBOList.size() == 0)) {

                    optionValidate.checkIsNotOption(optItemMap, isCustOpt);

                    boolean isMixedOpt = true;
                    singleProductStockBO.setChgType("");    // 기존 상품이 존재할경우 변경 타입 초기화

                    // 기존 재고가 옵션 상품일 경우
                    if (prevStockBO.getOptItemNo1() > 0) isMixedOpt = false;

                    if (!"ALL".equals(_type) || isMixedOpt) {

                        prevStockBO = prevStockBOList.get(0);

                        // 적용할 재고정보가 없을 경우, 기존 상품의 재고수량 조회 후 이를 총 재고수량에 설정.
                        oriTotOptStckCnt = prevStockBO.getStckQty();
                        totOptStckCnt = productVO.getOptionVO().getPrdStckQty();
                        // 현재 재고수가 0일 경우 기존 상품의 재고수량 조회 (옵션상품에서 옵션이 없는 단일상품으로 변경시)
                        if (totOptStckCnt == 0) totOptStckCnt = this.getProductStckCnt(productVO);
                        // 직매입 상품 or 외부인증번호 사용 or 셀러위탁배송 상품은 초기 등록시 재고수량 0
                        if (totOptStckCnt == 0 && !ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(productVO.getBaseVO().getBsnDealClf())
                            && !OptionConstDef.CERT_TYPE_101.equals(productVO.getBaseVO().getCertTypCd())
                            && !ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(productVO.getBaseVO().getDlvClf())
                            ) throw new OptionException(exceptionStockCntZeroMsg);

                        // 기존 상품도 단일옵션일 경우 이면서 재고수량에 변경이 있거나 카탈로그 번호가 변경되었을 경우 수정 실행  (타운 외부엑셀인증 상품, 인증번호 엑셀 다운 상품은 재고수량을 update 하지 않는다.)
                        if (isMixedOpt && prevStockBO != null
                            && (totOptStckCnt != prevStockBO.getStckQty() || !StringUtil.nvl(productVO.getOptionVO().getBarCode(), "").equals(StringUtil.nvl(prevStockBO.getBarCode(), "")) || prevStockBO.getCtlgNo() != productVO.getCtlgVO().getCtlgNo())
                            && !OptionConstDef.CERT_TYPE_101.equals(productVO.getBaseVO().getCertTypCd()) || (productVO.getOptionVO().getSellerStockCd() != null && productVO.getMemberVO().isCertStockSeller())) {
                            singleProductStockBO.setStockNo(prevStockBO.getStockNo());
                            singleProductStockBO.setCreateDt(prevStockBO.getCreateDt());
                            singleProductStockBO.setSelQty(prevStockBO.getSelQty());            // 판매수량
                            singleProductStockBO.setChgType(OptionConstDef.HIST_UPDATE);
                            singleProductStockBO.setIsOpenApi(productVO.getIsOpenApi());
                            singleProductStockBO.setPrevCtlgNo(prevStockBO.getCtlgNo());
                            singleProductStockBO.setCtlgNo(productVO.getCtlgVO().getCtlgNo());
                            if (productVO.getOptionVO().getSellerStockCd() != null && productVO.getMemberVO().isCertStockSeller()) {
                                singleProductStockBO.setSellerStockCd(productVO.getOptionVO().getSellerStockCd());
                                singleProductStockBO.setExtChgType(OptionConstDef.HIST_UPDATE);
                            }
                            isOptExist = false;
                        }
                        // 기존 상품이 옵션상품일 경우
                        else if (!isMixedOpt) {
                            delPrevStockBoList = prevStockBOList;

                            singleProductStockBO.setChgType(OptionConstDef.HIST_INSERT);    // 기존 상품이 옵션일 경우 변경 타입 등록으로 변경

                            for (ProductStockVO productStockBO : prevStockBOList) {
                                productStockBO.setChgType(OptionConstDef.HIST_DELETE);        // 히스토리 삭제 타입 설정
                                histStockBOList.add(productStockBO);
                            }
                        }
                    }
                } else if (!"ALL".equals(_type)) {
                    delPrevStockBoList = prevStockBOList;

                    for (ProductStockVO productStockBO : prevStockBOList) {
                        productStockBO.setChgType(OptionConstDef.HIST_DELETE);        // 히스토리 삭제 타입 설정
                        histStockBOList.add(productStockBO);
                    }
                }

                /*
                if (prevSelOptCnt > 0 && !StringUtil.isEmpty(delPrevStockBoList)
                    && ((productValidate.isPurchaseType(productVO.getBaseVO().getBsnDealClf()) && !ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(
                    productVO.getBaseVO().getSelStatCd())) || ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(productVO.getBaseVO().getDlvClf()))) {
                    if (productValidate.isPurchaseType(productVO.getBaseVO().getBsnDealClf())) {
                        throw new OptionException("매입의 상품 옵션은 삭제가 불가능합니다.");
                    } else {
                        throw new OptionException("셀러위탁배송 상품 옵션은 삭제가 불가능합니다.");
                    }
                }
                */

                if (prevSelOptCnt > 0 && !StringUtil.isEmpty(delPrevStockBoList)) {
                    if (productVO.isPurchaseType() && !ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(productVO.getBaseVO().getSelStatCd())) {
                        throw new OptionException("매입의 상품 옵션은 삭제가 불가능합니다.");
                    } else if (ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(productVO.getBaseVO().getDlvClf())) {
                        throw new OptionException("셀러위탁배송 상품 옵션은 삭제가 불가능합니다.");
                    } else if (!(productVO.isPurchaseType() && ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(productVO.getBaseVO().getSelStatCd()))
                        && "Y".equals(productVO.getBaseVO().getStdPrdYn())) {
                        throw new OptionException("표준 상품 옵션은 삭제가 불가능합니다.");
                    }
                }
            }

            // 독립형 옵션이면서 재고가 하나도 존재하지 않을 경우 하나의 데이터 row만 생성한다. (재고가 없으면 품절로 표시 되므로)
            /* TODO: 독립형 삭제
            if (isIdepOpt && stockBOList.size() <= 0){
                createIdepOptionStock(optItemMap, optItemBO, stockBOList, productVO);
            }
            */

            // 1-3. 변경할 재고 정보 등록/수정
            // 변경할 재고와 관련하여 반복문 수행.
            List<ProductStockVO> updateprdStockBoList = new ArrayList<ProductStockVO>(); // 수정 재고BO 리스트 variable
            List<ProductStockVO> insertprdStockBoList = new ArrayList<ProductStockVO>(); // 등록 재고BO 리스트 variable
            List<ProductStockVO> insertImageStockBoList = new ArrayList<ProductStockVO>(); // 스마트옵션 이미지 등록 BO 리스트 variable
            List<ProductStockVO> checkDuplicateBarcodeList = new ArrayList<ProductStockVO>();

            if (!StringUtil.isEmpty(stockBOList) && (!"ALL".equals(_type))) {

                int todayVal = Integer.parseInt(DateUtil.formatDate("yyyyMMdd"), 10);
                productVO.getOptionVO().setExistOpt(true);

                for (ProductStockVO productStockBO : stockBOList) {

                    productStockBO.setPrdNo(prdNo);
                    productStockBO.setEventNo(productVO.getPriceVO().getEventNo());
                    productStockBO.setBsnDealClf(productVO.getBaseVO().getBsnDealClf());

                    // 재고사용함 상태일 경우에만 재고 수량 수집
                    if (OptionConstDef.PRD_STCK_STAT_CD_USE.equals(productStockBO.getPrdStckStatCd())) {
                        // 직매입 상품 or 외부인증번호 사용 or 셀러위탁배송 상품은 초기 등록시 재고수량 0
                        if (productStockBO.getStckQty() == 0 && !OptionConstDef.CERT_TYPE_101.equals(productVO.getBaseVO().getCertTypCd())
                            && !ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(productVO.getBaseVO().getBsnDealClf())
                            && !ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(productVO.getBaseVO().getDlvClf()))
                            throw new OptionException(exceptionStockCntZeroMsg);

                        totOptStckCnt += productStockBO.getStckQty();
                    }

                    // 변경 재고 수정
                    if (OptionConstDef.HIST_UPDATE.equals(productStockBO.getChgType())) {
                        updateprdStockBoList.add(productStockBO);    // 변경 재고BO 등록
                        histStockBOList.add(productStockBO);        // 변경 히스토리 재고BO 등록
                    } else if (OptionConstDef.HIST_ONLY_MAPPING_UPDATE.equals(productStockBO.getChgType())) {
                        updateprdStockBoList.add(productStockBO);    // 재고매핑 정보하기 위해 상태 추가됨
                    }
                    // 신규 재고 등록
                    else if (OptionConstDef.HIST_INSERT.equals(productStockBO.getChgType())) {

                        checkNewStockReg(productVO, productStockBO, todayVal);

                        productStockBO.setCreateDt(curDate);        // 현재 등록일 설정
                        productStockBO.setUpdateDt(curDate);        // 현재 수정일 설정

                        insertprdStockBoList.add(productStockBO);    // 신규 재고BO 등록
                        histStockBOList.add(productStockBO);        // 신규 히스토리 재고BO 등록
                    }
                    String undefindStr = "undefined";
                    if ((productStockBO.getSummaryImage() != null && !undefindStr.equals(productStockBO.getSummaryImage()) && productStockBO.getSummaryImage().indexOf("등록됨") > -1)
                        || (productStockBO.getDetailImage() != null && !undefindStr.equals(productStockBO.getDetailImage()) && productStockBO.getDetailImage().indexOf("등록됨") > -1)) {
                        setOptImageInfo(productStockBO, prdNo, dbImagePath);
                        insertImageStockBoList.add(productStockBO);
                    }
                }
            }
            //  독립형이 아니고 옵션이 없을 경우. (독립형 옵션은 앞에서 재고 데이터를 생성하지 않으므로 조건에서 반드시 독립형 여부를 체크해 줘야 함.)
            else if (!isIdepOpt && StringUtil.isEmpty(stockBOList)) {
                optionValidate.checkIsNotOption(optItemMap, isCustOpt);

                if (productVO.getOptionVO().getPrdSelQty() < 0) {
                    throw new OptionException("재고수량은 음수로 입력 불가합니다.");
                }

                singleProductStockBO.setPrdNo(prdNo);
                singleProductStockBO.setEventNo(productVO.getPriceVO().getEventNo());
                singleProductStockBO.setBsnDealClf(productVO.getBaseVO().getBsnDealClf());
                singleProductStockBO.setStckQty(totOptStckCnt);
                singleProductStockBO.setPrdStckStatCd(OptionConstDef.PRD_STCK_STAT_CD_USE);
                singleProductStockBO.setCreateNo(productVO.getCreateNo());
                singleProductStockBO.setUpdateNo(productVO.getUpdateNo());
                singleProductStockBO.setUpdateDt(curDate);        // 현재 수정일 설정
                singleProductStockBO.setPuchPrc(productVO.getOptionVO().getPuchPrc());
                singleProductStockBO.setMrgnRt(productVO.getOptionVO().getMrgnRt());
                singleProductStockBO.setMrgnAmt(productVO.getOptionVO().getMrgnAmt());
                productVO.getOptionVO().setExistOpt(false);
                isOptExist = false;

                singleProductStockBO.setBarCode(productVO.getOptionVO().getBarCode());
                singleProductStockBO.setSelMnbdNo(productVO.getSelMnbdNo());

                if (productVO.getMemberVO().isCertStockSeller()) {
                    singleProductStockBO.setSellerStockCd(productVO.getOptionVO().getSellerStockCd());
                }

                // 변경 재고 수정
                if (OptionConstDef.HIST_UPDATE.equals(singleProductStockBO.getChgType())) {
                    //singleProductStockBO.setStckQtyModYn(productVO.getOptionVO().isModifiedPrdSelQty() ? "Y" : "N");
                    singleProductStockBO.setStckQtyModYn(oriTotOptStckCnt != totOptStckCnt ? "Y" : "N");

                    updateprdStockBoList.add(singleProductStockBO);    // 변경 재고BO 등록
                    histStockBOList.add(singleProductStockBO);        // 변경 히스토리 재고BO 등록
                } else if (OptionConstDef.HIST_INSERT.equals(singleProductStockBO.getChgType())) {
                    // 신규 재고 등록
                    totOptStckCnt = productVO.getOptionVO().getPrdStckQty();
                    // 현재 재고수가 0일 경우 기존 상품의 재고수량 조회 (옵션상품에서 옵션이 없는 단일상품으로 변경시)
                    if (totOptStckCnt == 0) totOptStckCnt = this.getProductStckCnt(productVO);
                    if (totOptStckCnt == 0 && !ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(productVO.getBaseVO().getBsnDealClf())
                        && !OptionConstDef.CERT_TYPE_101.equals(productVO.getBaseVO().getCertTypCd())
                        && !ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(productVO.getBaseVO().getDlvClf()))
                        throw new OptionException(exceptionStockCntZeroMsg);

                    if (productVO.getCtlgVO().getCtlgNo() == 0 && productVO.getCtlgVO().isFreshCategory()) {
                        productVO.getCtlgVO().setCtlgNo(-1);
                    }
                    singleProductStockBO.setStckQty(totOptStckCnt); // 신규 재고 수량
                    singleProductStockBO.setSelQty(0);                // 판매 재고 수량
                    singleProductStockBO.setCreateDt(curDate);        // 현재 등록일 설정

                    insertprdStockBoList.add(singleProductStockBO);    // 신규 재고BO 등록
                    histStockBOList.add(singleProductStockBO);        // 변경 히스토리 재고BO 등록
                }
            }

            if (productValidate.isPurchaseType(productVO.getBaseVO().getBsnDealClf()) && productVO.getOptionVO().isExistOpt() && !ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(productVO.getBaseVO().getSelStatCd()) && !insertprdStockBoList.isEmpty()) {
                PurchasePrcAprvVO paramBO = new PurchasePrcAprvVO();
                paramBO.setAprvObjCd("02");
                paramBO.setPrdNoList(Arrays.asList(productVO.getPrdNo()));
                if (!this.getSameDatePriceAprv(paramBO).isEmpty())
                    throw new OptionException("가격승인이 완료되지 않은 데이터가 있어 옵션 추가가 불가합니다. 승인거부 혹은 승인취소 처리 후 재시도해주세요.");
            }

            // 기존재고 정보중 변경이 발생한 데이터는 모두 삭제 Batch
            this.deleteProductStocks(delPrevStockBoList, productVO);
            // 현재 상품재고 데이타 수정 Batch
            this.updateProductStocks(updateprdStockBoList, productVO);
            // 현재 상품재고 데이타 등록 Batch
            this.insertProductStocks(insertprdStockBoList, productVO);

            checkDuplicateBarcodeList.addAll(updateprdStockBoList);
            checkDuplicateBarcodeList.addAll(insertprdStockBoList);

            if (barcodeDuplicateCheck && !checkDuplicateBarcodeList.isEmpty()) {
                Set<String> currentBarcodeSet = new LinkedHashSet<String>();
                for (ProductStockVO productStockBO : checkDuplicateBarcodeList) {
                    productStockBO.setMemId(productVO.getMemberVO().getMemID());
                    productStockBO.setOnlyOneBarCodeYn(isOnlyOneBarCode ? "Y" : "N");
                    String barCode = StringUtils.trim(productStockBO.getBarCode());
                    if (StringUtils.isNotEmpty(barCode)) {
                        this.checkBarcodeDuplicate(productStockBO);
                        if (!currentBarcodeSet.add(barCode)) {
                            throw new OptionException("중복된 바코드가 존재합니다. 바코드 : " + barCode);
                        }
                    } else {
                        if (isOnlyOneBarCode) throw new OptionException("바코드를 확인해 주세요.");
                    }
                }
            }

            // PD_STOCK 처리 후 옵션 값별 제한 개수 체크.
            if (selOptCnt > 1 && !checkIsIdepOpt) {
                ProductStockVO prdStockBO = new ProductStockVO();
                prdStockBO.setPrdNo(productVO.getPrdNo());
                prdStockBO.setOptItemCnt(selOptCnt);

                if (productVO.getPriceVO().getEventNo() > 0) {
                    prdStockBO.setEventNo(productVO.getPriceVO().getEventNo());
                }
                List<ProductStockVO> optValueSubList = this.getProductOptValueSubCntList(prdStockBO);
                optionValidate.checkOptValueSubCnt(optItemList, optValueSubList, resProductOptLimitBO.getCombOptValueCnt(), null);
            }

            if (!StringUtil.isEmpty(insertImageStockBoList)) {
                List<ProductStockVO> dupStockPrdList = new ArrayList<ProductStockVO>();
                HashSet<String> dupOptSet = new HashSet<String>();
                boolean isQcVerifitedStat = false;
                for (ProductStockVO bo : insertImageStockBoList) {
                    if ((!"0".equals(bo.getOptItemNo1()) && !"".equals(bo.getOptItemNo1())) && !"0".equals(bo.getOptValueNo1()) && !"".equals(bo.getOptValueNo1()) && !dupOptSet.contains(bo.getOptItemNo1() + "" + bo.getOptValueNo1())) {
                        dupStockPrdList.add(bo);
                        dupOptSet.add(String.valueOf(bo.getOptItemNo1()) + String.valueOf(bo.getOptValueNo1()));
                        if (!isQcVerifitedStat && bo.isQCVerifiedStat()) {
                            isQcVerifitedStat = true;
                            productVO.getOptionVO().setQCVerifiedStat(true);
                        }
                    }
                }
                this.insertProductImageStocks(prdNo, dupStockPrdList);
            }

            // 기존 옵션의 매입가 승인 정보 등록
            if (productValidate.isPurchaseType(productVO.getBaseVO().getBsnDealClf()) && productVO.getOptionVO().isExistOpt() && !ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(productVO.getBaseVO().getSelStatCd()) && !insertprdStockBoList.isEmpty()) {
                this.insertPreOptionPurchaseInfo(insertprdStockBoList, productVO);
            }

            // 1-4. ProductStockBO History 등록
            if (productVO.getPriceVO().getEventNo() <= 0) this.insertProductStockHist(histStockBOList);

            // 2. 옵션값 처리
            if (optItemMap != null && (!"ALL".equals(_type))) {
                Iterator<Map.Entry<String, PdOptItemVO>> checkItem = optItemMap.entrySet().iterator();
                List<PdOptValueVO> prdInsOptValueBoList = new ArrayList<PdOptValueVO>();

                while (checkItem.hasNext()) {
                    Map.Entry<String, PdOptItemVO> entry = checkItem.next();
                    optItemBO = entry.getValue();

                    HashMap hmap = new HashMap();
                    hmap.put("prdNo", prdNo);
                    hmap.put("optItemNo", optItemBO.getOptItemNo());
                    hmap.put("eventNo", productVO.getPriceVO().getEventNo());
                    List<PdOptValueVO> checkOptList = this.getProductOptValueLstNew(hmap);

                    for (PdOptValueVO productOptValueBO : optItemBO.getPdOptValueVOList()) {
                        productOptValueBO.setPrdNo(prdNo);
                        productOptValueBO.setEventNo(productVO.getPriceVO().getEventNo());

                        //스마트옵션 일 경우, 첫번째옵션일 경우 요약이미지 정보가 있을 경우 set
                        this.setSmartOption(productOptValueBO, optItemImageMap, prdNo, realHardImagePath, dbImagePath);

                        if (this.findDupOptionInfo(checkOptList, productOptValueBO)) {
                            productOptValueBO.setCreateDtDate(new Date());
                        }
                        prdInsOptValueBoList.add(productOptValueBO);
                    }
                }

                // 2-2. 옵션값 등록((독립형일 경우에만 옵션값 데이터를 관리한다)
                if (isIdepOpt) {
                    /* TODO : 독립형 제거
                    List<PdOptValueVO> prdOptValueBoList = new ArrayList<PdOptValueVO>();
                    Iterator<Map.Entry<String, PdOptItemVO>> item = optItemMap.entrySet().iterator();

                    while(item.hasNext()) {
                        Map.Entry<String, PdOptItemVO> entry = item.next();
                        optItemBO = entry.getValue();
                        for (PdOptValueVO productOptValueBO : optItemBO.getPdOptValueVOList()) {
                            productOptValueBO.setPrdNo(prdNo);
                            productOptValueBO.setEventNo(productVO.getPriceVO().getEventNo());
                            prdOptValueBoList.add(productOptValueBO);
                        }
                    }
                    this.deleteProductOptValues(productVO);
                    // 현재 상품 옵션값 데이타 등록 Batch
                    this.insertProductOptValues(prdOptValueBoList);
                    */
                } else {
                    //조합형 일 경우 pd_opt_value테이블에 값을 넣는다.
                    PdOptValueVO deleteProductOptValueBO = new PdOptValueVO();
                    deleteProductOptValueBO.setPrdNo(prdNo);
                    deleteProductOptValueBO.setEventNo(productVO.getPriceVO().getEventNo());
                    this.deleteProductOptInfo(deleteProductOptValueBO);
                    this.insertProductOptInfoValues(prdInsOptValueBoList);

                    //자세히보기 이미지가 하나도 없을 경우에는 전체를 삭제한다.
                    if (optItemDetailImageMap != null && optItemDetailImageMap.size() <= 0) {
                        PdOptDtlImage pdOptDtlImage = new PdOptDtlImage();
                        pdOptDtlImage.setPrdNo(prdNo);
                        optionMapper.deleteProductOptDtlImage(pdOptDtlImage);
                    }

                    if (!StringUtil.isEmpty(prdInsOptValueBoList) && productVO.getOptionVO().isQCVerifiedStat())
                        productVO.getOptionVO().setQCVerifiedStat(true);
                }
            }

            try {
                //스마트옵션일 경우 validate 확인
                optionValidate.checkSmartOption(productVO);
            } catch (Exception ex) {
                throw new OptionException(ex.getMessage(), ex);
            }

            // 변경후 총 재고 수량값 세팅
            productVO.getOptionVO().setPrdStckQty(optionMapper.getProductStckCnt(productVO.getPrdNo()));

            // 옵션 팝업에서 저장할 경우에만 처리
            if (("Y".equals(productVO.getOptionSaveYn()) && productVO.getCtlgVO().getCtlgNo() > 0 && (ProductConstDef.PRD_TYP_CD_MART.equals(productVO.getBaseVO().getPrdTypCd()) || ProductConstDef.PRD_TYP_CD_NEW_MART.equals(productVO.getBaseVO().getPrdTypCd())))
                || "OPT_INIT".equals(productVO.getOptionVO().getOptStr())) {
                HashMap modelMap = new HashMap();
                modelMap.put("prdNo", productVO.getPrdNo());
                modelMap.put("mnbdClfCd", ProductConstDef.MNBD_CLF_CD_OPT);

                // 기존 모델 제거 후 등록
                productMapper.deleteProductModel(modelMap);

                if ("Y".equals(productVO.getOptionSaveYn()) && productVO.getCtlgVO().getCtlgNo() > 0 && (ProductConstDef.PRD_TYP_CD_MART.equals(productVO.getBaseVO().getPrdTypCd()) || ProductConstDef.PRD_TYP_CD_NEW_MART.equals(productVO.getBaseVO().getPrdTypCd()))) {
                    CatalogRecmVO bo = new CatalogRecmVO();
                    bo.setPrdNo(productVO.getPrdNo());
                    bo.setCtlgNo(productVO.getCtlgVO().getCtlgNo());
                    bo.setCreateNo(productVO.getUpdateNo());
                    bo.setMnbdClfCd(ProductConstDef.MNBD_CLF_CD_OPT);
                    catalogServiceImpl.mergeProductModelInfo(bo);
                }
            }
            // 상품 등록/수정 일 경우
            else if (!"Y".equals(productVO.getOptionSaveYn())) {
                // 옵션 존재여부 저장. (PD_PRD_MODEL 등록하는 영역에서 사용. (ProductRegistrationServiceImpl.java setEnuriModel 메소드에서 사용))
                productVO.getBaseVO().setItemInfo(isOptExist ? "Y" : "N");
            }

            // 스마트 매칭에 포함된 상품 일 경우
            if (productVO.getPriceVO().getEventNo() <= 0) productCombServiceImpl.postProcessAddedStock(productVO);
        } catch (OptionException ex) {
            throw new OptionException(tmallExceptionErrMsg + ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new OptionException(tmallExceptionErrMsg + ex.getMessage(), ex);
        }
    }

    /**
     * 옵션 데이터 수집 (ProductOptItemBO, ProductStockBO 생성)
     *
     * @param productVO
     * @return
     * @throws OptionException
     */
    @Deprecated
    //public HashMap<String, Object> setProductInformationOptionVO(long userNo, ProductVO productVO) throws OptionException {
    public ProductVO setProductInformationOptionVO(ProductVO productVO) throws OptionException {
        //optMap 은 return하지 않는다.
        //HashMap<String, Object> optMap = new HashMap<String, Object>();
        try {
            //String optInputYn		= dataBox.getNotNullString("optInputYn");			// 작성형
            //String optClfCd			= dataBox.getString("optClfCd");					// 조합형:01, 독립형:02, 작성형:03
            String optInputYn = productVO.getOptionVO().getOptInputYn();            // 작성형
            String optClfCd = productVO.getOptionVO().getOptClfCd();                    // 조합형:01, 독립형:02, 작성형:03

            //setOptionData(productVO, optClfCd, optMap);
            setOptionData(productVO, optClfCd);

            // 조합형 옵션
            if (OptionConstDef.OPT_CLF_CD_MIXED.equals(optClfCd)) {
                //optMap = (HashMap<String, Object>) optionCombination.processProductOption(productVO);
                optionCombination.processProductOption(productVO);
            }
            // 작성형 옵션
            if ("Y".equals(optInputYn)) {
                // 작성형 옵션에서 사용할 수 있도록 dataBox에 옵션 정보 설정
                //dataBox.put("optMap", optMap);
                //optMap = (HashMap<String, Object>) optionInput.processProductOption(optMap, productVO);
                optionInput.processProductOption(productVO);
            }
        } catch (OptionException e) {
            throw e;
        } catch (Exception e) {
            throw new OptionException(OptionServiceExceptionMessage.SET_OPTION_DATA_ERROR, e);
        }
        //return optMap;
        return productVO;
    }

    //public void setOptionData(ProductVO productVO, String optClfCd, HashMap<String, Object> optMap) throws Exception {
    @Deprecated
    public void setOptionData(ProductVO productVO, String optClfCd) throws Exception {
        if (productVO == null && (productVO.getBaseVO() == null || productVO.getOptionVO() == null || productVO.getMemberVO() == null || productVO.getPriceVO() == null)) throw new OptionException(OptionServiceExceptionMessage.SET_OPTION_DATA_ERROR);

        //상품정보
        long prdNo = productVO.getPrdNo();            // 상품번호
        long selPrc = StringUtil.getLong(StringUtil.nvl(productVO.getPriceVO().getSelPrc(), "-1").replaceAll(",", ""));        // 상품판매가
        String mobile1WonYn = StringUtil.nvl(productVO.getBaseVO().getMobile1wonYn(), "N");    // 1원 상품 여부
        String bsnDealClf = StringUtil.nvl(productVO.getBaseVO().getBsnDealClf(), ProductConstDef.BSN_DEAL_CLF_COMMISSION);    // 거래유형
        long dispCtgr1No = Long.parseLong(StringUtil.nvl(productVO.getBaseVO().getDispCtgr1NoDe(), "0"));                    // 대카
        long dispCtgr2No = Long.parseLong(StringUtil.nvl(productVO.getBaseVO().getDispCtgr2NoDe(), "0"));                        // 중카
        long dispCtgrNo = Long.parseLong(StringUtil.nvl(productVO.getDispCtgrNo(), "0"));
        String engDispYn = productVO.getBaseVO().getEngDispYn();            // 영문11번가 노출 여부
        String selMthdCd = productVO.getBaseVO().getSelMthdCd();            // 판매방식 코드
        String dlvClf = productVO.getBaseVO().getDlvClf();    // 배송주체(셀러위탁배송여부체크)
        long soCupnAmt = Long.parseLong(StringUtil.nvl(productVO.getPriceVO().getSoCupnAmt(), "-1"));                    // 기본즉시할인가
        double prdCstmAplPrc = StringUtil.getDouble(StringUtil.nvl(productVO.getPriceVO().getCstmAplPrc(), "0").replaceAll(",", ""));    // 상품신고가
        String minusOptLimitYn = ProductConstDef.BSN_DEAL_CLF_COMMISSION.equals(bsnDealClf) ? optionValidate.isMinusOptLimit(dispCtgr1No, dispCtgr2No) : "N";    // 마이너스 옵션 제한 카테고리 여부 조회
        String selStatCd = productVO.getBaseVO().getSelStatCd();            // 판매상태
        String prdTypCd = productVO.getBaseVO().getPrdTypCd();                // 상품 구분 코드

        //옵션정보
        // 조합형 옵션 여부
        boolean isSendMixedOpt = OptionConstDef.OPT_CLF_CD_MIXED.equals(optClfCd) && StringUtil.isNotEmpty(productVO.getOptionVO().getColTitle());
        // 독립형 옵션 여부
        //boolean isSendIndepOpt  = OptionConstDef.OPT_CLF_CD_IDEP.equals(optClfCd) && StringUtil.isNotEmpty(dataBox.getNotNullString("cOptValue"));
        //String minusOptLimitYn 	= ProductConstDef.BSN_DEAL_CLF_COMMISSION.equals(bsnDealClf) ? AbstractOption.isMinusOptLimit(dispCtgr1No, dispCtgr2No) : "N";
        String mrgnPolicyCd = StringUtil.nvl(productVO.getOptionVO().getMrgnPolicyCd(), "");    // 마진정책
        String optSelectYn = productVO.getOptionVO().getOptSelectYn();                    // 선택형 (조합형, 독립형)
        String optionSaveYn = productVO.getOptionVO().getOptionSaveYn();                // 옵션팝업에서 저장 여부
        String certDownYn = productVO.getBaseVO().getCertDownYn();                    // 외부인증번호 사용 여부
        String certTypCd = productVO.getBaseVO().getTownSellerCertType(); // 인증 구분 코드
        String optTypCd = productVO.getOptionVO().getOptTypCd();                // 옵션 구분 코드 (01 : 날짜형)

        //회원정보
        boolean globalItgSeller = (prdNo > 0 ? false : productValidate.checkGlbItgIdsBySeller(productVO.getSelMnbdNo()));    // 해외 통합출고지 사용가능 셀러여부
        String memTypCd = productVO.getMemberVO().getMemTypCD();                                        // 회원 유형 코드
        String globalSellerYn = productVO.getMemberVO().getGlobalSellerYn();                                    // 글로벌 셀러 여부

        long chkDispCtgr1No = dispCtgr1No;
        long chkDispCtgr2No = dispCtgr2No;
        String[] ctgrOptPrcArr = null;
        boolean isDealRangeCheck = false;

        if (prdNo > 0) {
            ProductVO setProductVO = new ProductVO();
            setProductVO.setPriceVO(new PriceVO());
            setProductVO.setBaseVO(new BaseVO());
            setProductVO.setPrdNo(prdNo);
            setProductVO.getPriceVO().setEventNo(Long.parseLong(StringUtil.nvl(productVO.getPriceVO().getEventNo(), "-1")));
            setProductVO.getBaseVO().setPrdInfoType(productVO.getBaseVO().getPrdInfoType());
            setProductVO = this.getProduct4OptionPop(setProductVO);

            dispCtgrNo = setProductVO.getDispCtgrNo();
            prdTypCd = setProductVO.getBaseVO().getPrdTypCd();

            selStatCd = setProductVO.getBaseVO().getSelStatCd();

            // 상품의 가격과 옵션가를 비교하기 위해 필요 - 상품 기본즉시할인 적용된 판매가의 100%이상을 옵션가로 셋팅할수 없음.(11/30 봉재원 매니저 요청)
            if (selPrc <= 0) selPrc = setProductVO.getPriceVO().getSelPrc();
            prdCstmAplPrc = setProductVO.getPriceVO().getCstmAplPrc();

            // 1 원 상품여부 승인대기가 아닐경우 설정
            if (!ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(setProductVO.getBaseVO().getSelStatCd()))
                mobile1WonYn = StringUtil.nvl(setProductVO.getBaseVO().getMobile1wonYn(), "N");

            // 승인대기 상태에 특정매입 상품에서 마진정책을 마진율 -> 매입가로 수정시 옵션가 설정 여부 체크
            if (ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(setProductVO.getBaseVO().getSelStatCd()) &&
                (ProductConstDef.BSN_DEAL_CLF_SPECIFIC_PURCHASE.equals(setProductVO.getBaseVO().getBsnDealClf()) || ProductConstDef.BSN_DEAL_CLF_TRUST_PURCHASE.equals(bsnDealClf)) &&
                ("02".equals(setProductVO.getOptionVO().getMrgnPolicyCd()) || "02".equals(mrgnPolicyCd)) && !isSendMixedOpt && "Y".equals(optSelectYn)) {
                // 조합형/독립형 옵션 없음 & 구매자선택형 옵션 사용여부 "Y"일 경우 선택형옵션 사용여부 "N"
                optSelectYn = "N";
            }

            // 해외 통합출고지가 가능한 해외셀러인지 여부
            globalItgSeller = productValidate.checkGlbItgIdsByPrdNo(prdNo);

            // 수정일 경우 외부인증번호 사용 여부, 영문11번가 노출 여부, 상품 구분 코드 DB조회 한 값으로 Set.
            if ("Y".equals(optionSaveYn)) {
                certDownYn = setProductVO.getBaseVO().getCertDownYn();
                engDispYn = setProductVO.getBaseVO().getEngDispYn();
                selMthdCd = setProductVO.getBaseVO().getSelMthdCd();
                dispCtgr1No = Long.parseLong(StringUtil.nvl(setProductVO.getBaseVO().getDispCtgr1NoDe(), "0"));
                if (StringUtil.isNotEmpty(productVO.getBaseVO().getPrdTypCd()) &&
                    (ProductConstDef.PRD_TYP_CD_MART.equals(productVO.getBaseVO().getPrdTypCd()) || ProductConstDef.PRD_TYP_CD_NEW_MART.equals(productVO.getBaseVO().getPrdTypCd()))) {
                    prdTypCd = productVO.getBaseVO().getPrdTypCd();
                }
                certTypCd = setProductVO.getBaseVO().getCertTypCd();
                productVO.getBaseVO().setCertDownYn(certDownYn);
                productVO.getBaseVO().setEngDispYn(engDispYn);
                productVO.getBaseVO().setSelMthdCd(selMthdCd);
                productVO.getBaseVO().setSelBgnDy(StringUtil.left(StringUtil.nvl(setProductVO.getBaseVO().getSelBgnDy()), 8));
                productVO.getBaseVO().setSelEndDy(StringUtil.left(StringUtil.nvl(setProductVO.getBaseVO().getSelEndDy()), 8));
            }

            // 상품번호로 글로벌 셀러 여부 조회
            if (StringUtil.isEmpty(memTypCd)) {
                ProductVO sellerBO = sellerServiceImpl.getProductSellerInfo(prdNo);
                memTypCd = sellerBO.getMemberVO().getMemTypCD();
            }
            if (ProductConstDef.PRD_MEM_TYPE_GLOBAL.equals(memTypCd)) {
                globalSellerYn = "Y";
            }

            productVO.setBundleProduct(ProductConstDef.SetTypCd.BUNDLE.equals(setProductVO.getBaseVO().getSetTypCd()));
            minusOptLimitYn = setProductVO.getMinusOptLimitYn();
            if (!"Y".equals(productVO.getPrdCopyYn())) {
                if (setProductVO.getPriceVO().getEventNo() > 0) {
                    isDealRangeCheck = true;
                } else {
                    ProductVO dealPrdBO = dealServiceImpl.getShockingDealPrd(prdNo);
                    if (dealPrdBO != null && prdNo == dealPrdBO.getPrdNo()) isDealRangeCheck = true;
                }
            }
            chkDispCtgr1No = setProductVO.getBaseVO().getDispCtgr1NoDe();
            chkDispCtgr2No = setProductVO.getBaseVO().getDispCtgr2NoDe();

            // 기본즉시할인이  0보다 작은 경우 DB에서 조회함
            if (soCupnAmt < 0) {
                if (setProductVO.getPriceVO().getEventNo() > 0) {
                    ProductEvtVO productEvtVO = new ProductEvtVO();
                    productEvtVO.setEventNo(setProductVO.getPriceVO().getEventNo());
                    productEvtVO.setPrdNo(prdNo);
                    productEvtVO.setSelMnbdNo(setProductVO.getSelMnbdNo());

                    productEvtVO = dealServiceImpl.getAplPrdInfo(productEvtVO);
                    if (productEvtVO != null && productEvtVO.getEventPricePegVO() != null) {
                        soCupnAmt = dealValidate.getCalculatedDscAmt(productEvtVO.getEventPricePegVO());
                    }
                } else {
                    // 할인정보 조회
                    String descData = priceServiceImpl.getProductDiscountDataByPrdNo(prdNo, selPrc);
                    String[] descDatas = descData.split("\\|");
                    soCupnAmt = Long.parseLong(descDatas[10]); // SO 쿠폰할인금액
                }
            }
        }

        String createCd = StringUtil.nvl(productVO.getBaseVO().getCreateCd(), CreateCdTypes.MO_SIMPLEREG.getDtlsCd());
        createCd = (createCd + "00").substring(0, 4);

        // validateCertTypeAndKey check
        optionValidate.checkSetOptionInfo(createCd,
                                          isSendMixedOpt,
                                          optSelectYn,
                                          bsnDealClf,
                                          dlvClf,
                                          globalItgSeller,
                                          certDownYn,
                                          optTypCd,
                                          globalSellerYn,
                                          dispCtgr1No,
                                          selMthdCd,
                                          engDispYn);
        // 조합형/독립형 옵션 없음& 구매자선택형 옵션 사용여부 "Y"일 경우 선택형옵션 사용여부 "N"
        //if (!isSendMixedOpt && !isSendIndepOpt && "Y".equals(optSelectYn)) optSelectYn = "N";

        if (ProductConstDef.BSN_DEAL_CLF_COMMISSION.equals(bsnDealClf)) {
            ctgrOptPrcArr = optionValidate.checkCtgrOptPrcData(chkDispCtgr1No, chkDispCtgr2No, isDealRangeCheck);
        } else {
            ctgrOptPrcArr = new String[]{OptionConstDef.CTGR_OPT_RESTRICT_VALUE, OptionConstDef.CTGR_OPT_RESTRICT_PERCENT};
        }

        //dataBox.put("userNo", userNo);								// 현재 접속자 NO
        productVO.getPriceVO().setPrdCstmAplPrc(prdCstmAplPrc);          // 상품신고가
        productVO.getBaseVO().setMobile1wonYn(mobile1WonYn);            // 1원 상품 여부
        productVO.setMinusOptLimitYn(minusOptLimitYn);      // 마이너스 옵션 제한 여부
        productVO.getPriceVO().setSoCupnAmt(soCupnAmt);                  // 기본즉시할인가
        productVO.getBaseVO().setSelStatCd(selStatCd);                  // 판매상태
        productVO.getBaseVO().setPrdTypCd(prdTypCd);
        productVO.getMemberVO().setGlobalItgSeller(globalItgSeller);    // 해외 통합출고지가 가능한 해외셀러인지 여부 flag
        productVO.getMemberVO().setFrSeller(customerServiceImpl.isFrSellerByPrdNo(prdNo));    // 이태리 셀러여부
        productVO.getCategoryVO().setFreshCategory(categoryValidate.isFreshCategory(prdTypCd, dispCtgrNo, productVO.getSelMnbdNo()));
        productVO.getOptionVO().setCtgrOptPrcVal(ctgrOptPrcArr[0]);                // 카테고리별 옵션 가격 제한 값
        productVO.getOptionVO().setCtgrOptPrcPercent(ctgrOptPrcArr[1]);            // 카테고리별 옵션 가격 제한 값 (%)
        productVO.getPriceVO().setSelPrc(selPrc);           // 상품판매가

        // 타운홍보용 상품등록 시 옵션이 존재할 경우
        /*
        if( optMap != null && !optMap.isEmpty() ) {
            optionValidate.checkTownInfo(prdTypCd, certTypCd, optTypCd);
            optMap.put("isFreshCategory", productVO.getCategoryVO().isFreshCategory());
        }
        */
        optionValidate.checkTownInfo(prdTypCd, certTypCd, optTypCd);

        /*
        SellerAuthVO sellerAuthVO = new SellerAuthVO();
        sellerAuthVO.setSelMnbdNo(Long.valueOf(productVO.getSelMnbdNo()));
        sellerAuthVO.setAuthTypCd(ProductConstDef.AUTH_TYP_CD_STOCK_SELLER);
        sellerAuthVO.setAuthObjNo(String.valueOf(productVO.getSelMnbdNo()));
        sellerAuthVO.setObjClfCd(ProductConstDef.OBJ_CLF_CD_STOCK_SELLER);
        sellerAuthVO.setUseYn("Y");
        productVO.getMemberVO().setCertStockSeller(sellerServiceImpl.isPdSellerAuth(sellerAuthVO));
        */
        productVO.getMemberVO().setCertStockSeller(sellerServiceImpl.getPdSellerAuth(productVO, ProductConstDef.AUTH_TYP_CD_STOCK_SELLER, ProductConstDef.OBJ_CLF_CD_STOCK_SELLER));
    }

    /**
     * 상품등록시 상품옵션을 생성한다.(신규등록/복사)
     * - 참고 : 카테고리 정보 변경과 동시성의 우려가 생길시 db에서 바로 조회후 체크로 변경(현재 validate에서 세팅된 값으로 체크중)
     * 전시카테고리의 상품입력제한이더라도 재고는 등록하도록 함
     *
     * @param productVO
     * @throws OptionException
     */
    public void insertProductOption(ProductVO productVO) throws OptionException {
        // 2. 옵션정보등록 start
        // 상품등록이 복사가 아닌경우
        if (!"Y".equals(productVO.getPrdCopyYn())) {
            //해외명품상품은 수정불가.
            if (productVO.getBaseVO().getFrPrdNo() > 0) {
                // 해외명품 상품 옵션 재고 정보 저장
                this.insertFrProductOpt(productVO);
            } else {
                Boolean optExist = false;
                if (productVO.getBaseVO().getTemplatePrdNo() > 0) {
                    optExist = this.isExistOptionOrAddPrduct(productVO.getBaseVO().getTemplatePrdNo(), "1");
                }
                // 간편등록 옵션이 존재하는 경우
                if (optExist && !"Y".equals(productVO.getOptionVO().getTmpColMap().get("142"))) {
                    //간편등록 제외
                    //prdUnitInfoService.insertProductOptSimple(productVO);
                } else {
                    // 옵션 아이템 등록/수정 process
                    if (!"Y".equals(productVO.getOfferDispLmtYn()) && productVO.getOptionVO().getOptItemMap() != null) {
                        this.insertPdOptItem(productVO);
                    }
                    // 옵션 재고 등록/수정 process
                    this.insertPdStock(productVO, null);
                }
            }
        }
        // 상품등록이 복사인경우
        else {
            // 복사시 옵션을 팝업에서 수정하여 옵션 정보가 등록 패이지 바닥에 있는경우
            if ("Y".equals(productVO.getOptChangeYn())) {
                // 옵션 아이템 등록/수정 process
                if (!"Y".equals(productVO.getOfferDispLmtYn()) && productVO.getOptionVO().getOptItemMap() != null) {
                    this.insertPdOptItem(productVO);
                }
                // 옵션 재고 등록/수정 process
                this.insertPdStock(productVO, null);
            }
            // 복사시 옵션을 수정하지 않아 옵션관련 정보가 없을 경우 DB 조회를 통해 옵션 정보를 복사함.
            else {
                // 옵션 아이템수 조회 PD_OPT_ITEM (유효한 옵션 아이템 정보 존재 유무 확인)
                long optCnt = this.getProductOptCnt(productVO);

                if (optCnt > 0 && ProductConstDef.SetTypCd.MASTER.equals(productVO.getBaseVO().getSetTypCd())) {
                    if (!productVO.getSellerVO().isSellableBundlePrdSeller())
                        productVO.getSellerVO().setSellableBundlePrdSeller(sellerValidate.isDirectBuyingMark(productVO.getMemberVO()));
                    if (!productVO.getSellerVO().isSellableBundlePrdSeller() && !productVO.getSellerVO().isOnlyUsableSetTypCd())
                        productVO.getSellerVO().setOnlyUsableSetTypCd(sellerValidate.isOnlyUsableSetTypCd(productVO.getMemberVO()));
                    if (sellerServiceImpl.isBarcodeDuplicateCheckSeller(productVO))
                        throw new OptionException("바코드 중복으로 상품옵션 팝업을 이용하시기 바랍니다.");
                }

                // 옵션이 존재하는 경우
                if (!"Y".equals(productVO.getOfferDispLmtYn()) && optCnt > 0) {
                    optionValidate.checkTownInfo(productVO.getBaseVO().getPrdTypCd(), productVO.getBaseVO().getCertTypCd(), productVO.getOptionVO().getOptTypCd());

                    // 선택형 옵션수 조회
                    int selOptCnt = optionValidate.checkMaxOptCnt(productVO.getBaseVO().getReRegPrdNo());
                    productVO.getOptionVO().setExistOpt(selOptCnt > 0);
                    // 상품 옵션 정보 복사 등록
                    this.insertProductOptCopy(productVO);
                }
                // 옵션이 없는 경우
                else {
                    this.insertPdStock(productVO, null);
                }
            }
        }

        // 상품옵션 재고 데이터 유효성(동일코드 옵션재고항목 존재여부 확인) 체크
        if (this.getOptStockValidCheck(productVO.getPrdNo()) > 0) {
            log.error("prdNo: " + productVO.getPrdNo(), new OptionException("옵션등록 오류 : 동일코드 옵션이 2개 이상 존재합니다."));
            throw new OptionException("옵션등록 오류 : [02]동일코드 옵션이 2개 이상 존재합니다.");
        }
        // 2. 옵션정보등록 end
    }

    /**
     * 기준재고속성 최초 등록(최초 담당MD만 등록됨)
     *
     * @param param
     * @return
     * @throws OptionException
     */
    public void insertBasisStockAttribute(ProductVO param) throws OptionException {
        boolean isDirectPurchaseOutDlv = ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(param.getBaseVO().getBsnDealClf()) && ProductConstDef.DLV_CLF_OUT_DELIVERY.equals(param.getBaseVO().getDlvClf());

        BasisStockVO basisStockBO = new BasisStockVO();
        basisStockBO.setPrdNo(String.valueOf(param.getPrdNo()));
        basisStockBO.setEmpNo(String.valueOf(param.getBaseVO().getEmployeeNo()));
        if (ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(param.getBaseVO().getDlvClf()) || ProductConstDef.SetTypCd.BUNDLE.equals(param.getBaseVO().getSetTypCd())
            || isDirectPurchaseOutDlv || ProductConstDef.BSN_DEAL_CLF_SPECIFIC_PURCHASE.equals(param.getBaseVO().getBsnDealClf())) {
            basisStockBO.setRepPtnrMemNo(String.valueOf(param.getSelMnbdNo()));
        } else {
            basisStockBO.setRepPtnrMemNo("0");
        }
        basisStockBO.setCreateNo(String.valueOf(param.getCreateNo()));
        basisStockBO.setUpdateNo(String.valueOf(param.getUpdateNo()));

        try {
            PdPrdWmsInfo pdPrdWmsInfo = new PdPrdWmsInfo();
            BeanUtils.copyProperties(basisStockBO, pdPrdWmsInfo);
            optionMapper.insertBasisStockAttribute(pdPrdWmsInfo);

            PdPrdWmsInfoHist pdPrdWmsInfoHist = new PdPrdWmsInfoHist();
            BeanUtils.copyProperties(basisStockBO, pdPrdWmsInfoHist);
            optionMapper.insertPdPrdWmsInfoHist(pdPrdWmsInfoHist);
        } catch (Exception e) {
            throw new OptionException(e.getMessage(), e);
        }
    }

    public int getSingleOptionCnt(ProductStockVO setProductStockVO) {
        int cnt = 0;
        try {
            cnt = optionMapper.getSingleOptionCnt(setProductStockVO);
        } catch (Exception ex) {
            // Exception 발생
            throw new OptionException(ex);
        }
        return cnt;
    }

    public void updateAddProductOptionApiYn(ProductVO productVO) {

        String createCd = productVO.getBaseVO().getCreateCd();
        boolean isApiCalledProceed = false;
        if (GroupCodeUtil.equalsDtlsCd(createCd, CreateCdTypes.SELLER_API)) {
            isApiCalledProceed = true;
        } else {
            isApiCalledProceed = false;
        }

        if (!isApiCalledProceed) {
            if (StringUtils.equals("Y", productVO.getBaseVO().getStdPrdYn())) {
                //상품 옵션 아이템 정보 등록/수정
                if (!StringUtils.equals("Y", productVO.getOfferDispLmtYn())) {
                    insertPdOptItem(productVO);
                }

                //상품 재고 등록/수정
                insertPdStock(productVO, "");

                //옵션 중복 등록 체크
                optionValidate.checkOptionDuplicate(productVO);
            } else {
                insertPdStock(productVO, "ALL");
            }
        } else {
            if (voUtil.isEmptyVO(productVO.getProductAddCompositionVOList())) {
                if (GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getPrdTypCd(), PrdTypCd.RENTAL)) {
                    throw new OptionException("렌탈상품은 추가구성상품 설정이 불가능합니다.");
                } else {
                    additionalProductService.insertAdditionalProductProcess(productVO);
                }
            }
        }
    }

    /**
     * 상품옵션 적용
     *
     * @param productVO
     * @return
     * @throws OptionException
     */
    public ProductVO updateProductOption(ProductVO productVO) throws OptionException {
        try {
            // 상품 옵션 아이템 정보 등록/수정
            if (!"Y".equals(productVO.getOfferDispLmtYn())) this.insertPdOptItem(productVO);

            // 상품 재고 등록/수정
            this.insertPdStock(productVO, null);

            // 옵션 중복 등록 체크
            optionValidate.checkOptionDuplicate(productVO);
        } catch (OptionException ex) {
            throw new OptionException("상품옵션 적용 오류", ex);
        } catch (Exception ex) {
            throw new OptionException("상품옵션 적용 오류", ex);
        }
        return productVO;
    }

    /**
     * 옵션 수정 적용
     *
     * @param productVO
     * @return
     * @throws OptionException
     */
    public ProductVO applyProductOption(ProductVO productVO) throws OptionException {
        ProductVO rtnPrdVO = null;
        try {
            // 옵션 수정에 따른 관련 데이터 정보 수정 process method
            try {
                if (productVO.getPrdNo() == 0) throw new OptionException("(INNER) 옵션 적용 오류 - 상품번호 누락");

                if (productVO.getPriceVO().getEventNo() > 0) {
                    ProductEvtVO infoParam = new ProductEvtVO();
                    infoParam.setEventNo(productVO.getPriceVO().getEventNo());
                    infoParam.setPrdNo(productVO.getPrdNo());
                    infoParam.setSelMnbdNo(productVO.getUpdateNo()); // API연동업체 예외관련
                    infoParam.setOfficeGubun(productVO.getOfficeGubun());

                    if (!dealValidate.getShockDealState(infoParam).isUpdablePrdInfo())
                        throw new OptionException("현재 쇼킹딜 상품정보를 수정 할 수 없습니다.");
                }

                // 1. 상품 옵션 정보 수정
                this.updateProductOption(productVO);
                long stckQty = productVO.getOptionVO().getPrdStckQty();        // 변경 옵션의 재고수량 총 합.

                DpGnrlDispVO prdGnrlDispBO = new DpGnrlDispVO();
                prdGnrlDispBO.setPrdNo(productVO.getPrdNo());
                prdGnrlDispBO.setUpdateNo(productVO.getUpdateNo());
                prdGnrlDispBO.setStckQty(stckQty);
                prdGnrlDispBO.setOptCd(this.getProductOptCd(productVO.getPrdNo())); // DP_GNRL_DISP 비정규화 컬럼 정보 설정

                if (productVO.getPriceVO().getEventNo() <= 0) {
                    // 2. 상품 옵션 재고에 따른 상품의 상태 변경
                    prdGnrlDispBO.setSelStatCd(productServiceImpl.updateProductStockSelStatCd(stckQty, productVO));
                    // 상품 일반전시 카테고리 수정
                    categoryServiceImpl.updateProductGeneralDisplay(prdGnrlDispBO);

                    // 묶음 상품 처리. 상태가 변경이 되지 않았어도 처리되어야 함
                    if (productVO.isPurchaseType() && !ProductConstDef.SetTypCd.BUNDLE.equals(productVO.getStrSetTypCd())
                        && !ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(productVO.getBaseVO().getSelStatCd())) {
                        productServiceImpl.synchBundleProduct(productVO);
                    }

                    // 옵션 팝업에서 저장시에만 처리
                    if ("Y".equals(productVO.getOptionSaveYn()) && !"0100".equals(productVO.getBaseVO().getCreateCd())) {
                        // 상품 기존 정보 조회
                        ProductVO orgPrdInfo = productMapper.getProduct(productVO.getPrdNo());
                        String orgOptTypCd = StringUtil.nvl(orgPrdInfo.getOptionVO().getOptTypCd());
                        String orgUseBgnDy = StringUtil.nvl(orgPrdInfo.getOptionVO().getUseBgnDy());
                        String orgUseEndDy = StringUtil.nvl(orgPrdInfo.getOptionVO().getUseEndDy());
                        String regUseBgnDy = StringUtil.nvl(productVO.getOptionVO().getUseBgnDy()).replaceAll("/", "");
                        String regUseEndDy = StringUtil.nvl(productVO.getOptionVO().getUseEndDy()).replaceAll("/", "");

                        if (!"".equals(orgUseBgnDy)) {
                            orgUseBgnDy = orgUseBgnDy.substring(0, 8);
                        }
                        if (!"".equals(orgUseEndDy)) {
                            orgUseEndDy = orgUseEndDy.substring(0, 8);
                        }

                        // 옵션 구분 코드가 변경 되었거나
                        if (!orgOptTypCd.equals(productVO.getOptionVO().getOptTypCd()) ||
                            // PIN번호(11번가발송) 날짜형 상품일 때 날짜가 변경 되었을 경우 처리
                            (ProductConstDef.PRD_TYP_CD_TOWN_SALES.equals(productVO.getBaseVO().getPrdTypCd()) && OptionConstDef.OPT_TYP_CD_DAY.equals(
                                productVO.getOptionVO().getOptTypCd())
                                && (!orgUseBgnDy.equals(regUseBgnDy) || !orgUseEndDy.equals(regUseEndDy)))) {

                            // PIN번호(11번가발송) 상품이고 날짜형 상품일 경우 환불타입 자동환불 제외로 설정
                            if (ProductConstDef.PRD_TYP_CD_TOWN_SALES.equals(productVO.getBaseVO().getPrdTypCd()) && OptionConstDef.OPT_TYP_CD_DAY.equals(

                                productVO.getOptionVO().getOptTypCd())) {
                                productVO.getBaseVO().setRfndTypCd(ProductConstDef.RFND_TYP_CD.NOT.getDtlsCd());
                            }

                            // 히스토리 등록을 위해 현재 시간을 조회한다
                            String nowDate = DateUtil.getFormatString("yyyyMMddHHmmss");
                            productVO.setHistAplEndDt(nowDate);

                            // 상품 옵션 구분 코드 및 사용기간 수정
                            PdPrd pdPrd = new PdPrd();
                            BeanUtils.copyProperties(productVO, pdPrd);
                            productMapper.updateProductOptionModify(pdPrd);
                            // 상품 히스토리 등록
                            PdPrdHist pdPrdHist = new PdPrdHist();
                            pdPrdHist.setPrdNo(productVO.getPrdNo());
                            pdPrdHist.setHistAplEndDt(nowDate);
                            productMapper.insertProductHist(pdPrdHist);
                        }

                        // 날짜형 옵션일 경우 구매불가일 정보 수정
                        if (OptionConstDef.OPT_TYP_CD_DAY.equals(productVO.getOptionVO().getOptTypCd())) {
                            PrdOthersVO prdOthersVO = new PrdOthersVO();
                            BeanUtils.copyProperties(productVO, prdOthersVO);
                            etcServiceImpl.mergeProductOthersBuyDsblDyInfo(prdOthersVO);
                        }
                    }
                } else {
                    prdGnrlDispBO.setSelStatCd(productVO.getBaseVO().getSelStatCd());
                }

                rtnPrdVO = new ProductVO();
                rtnPrdVO.setDpGnrlDispVO(prdGnrlDispBO);

            } catch (OptionException e) {
                throw new OptionException("옵션 적용 오류", e);
            } catch (Exception e) {
                throw new OptionException("옵션 적용 오류", e);
            }
            return rtnPrdVO;
        } catch (Exception e) {
            throw new OptionException("옵션 적용 오류", e);
        }
    }

    /**
     * 구성상품(세트,묶음) 재고 정보가 있는지 확인한다.
     *
     * @param prdNo
     * @return
     * @throws OptionException
     */
    public int getPdStockSetInfoCnt(long prdNo) throws OptionException {
        int resCnt = 0;
        try {
            resCnt = optionMapper.getPdStockSetInfoCnt(prdNo);
        } catch (OptionException ex) {
            throw new OptionException("구성상품(세트,묶음) 재고 정보 조회 오류", ex);
        } catch (Exception ex) {
            throw new OptionException("구성상품(세트,묶음) 재고 정보 조회 오류", ex);
        }
        return resCnt;
    }

    /**
     * 옵션 데이터 수집 (ProductOptItemBO, ProductStockBO 생성)
     *
     * @param productVO
     * @return
     * @throws OptionException
     */
    public ProductVO setProductInformationOptionVONew(ProductVO productVO) throws OptionException {
        try {
            String optInputYn = productVO.getOptionVO().getOptInputYn();            // 작성형
            String optClfCd = productVO.getOptionVO().getOptClfCd();                    // 조합형:01, 독립형:02, 작성형:03

            setOptionDataNew(productVO, optClfCd);

            // 조합형 옵션
            if (OptionConstDef.OPT_CLF_CD_MIXED.equals(optClfCd)) {
                optionCombination.processProductOptionNew(productVO);
            }
            // 작성형 옵션
            if ("Y".equals(optInputYn)) {
                // 작성형 옵션에서 사용할 수 있도록 dataBox에 옵션 정보 설정
                optionInput.processProductOptionNew(productVO);
            }
        } catch (OptionException e) {
            throw e;
        } catch (Exception e) {
            throw new OptionException(OptionServiceExceptionMessage.SET_OPTION_DATA_ERROR, e);
        }
        return productVO;
    }

    public void setOptionDataNew(ProductVO productVO, String optClfCd) throws Exception {
        if (productVO == null && (productVO.getBaseVO() == null || productVO.getOptionVO() == null || productVO.getMemberVO() == null || productVO.getPriceVO() == null)) throw new OptionException(OptionServiceExceptionMessage.SET_OPTION_DATA_ERROR);

        //상품정보
        long prdNo = productVO.getPrdNo();            // 상품번호
        long selPrc = StringUtil.getLong(StringUtil.nvl(productVO.getPriceVO().getSelPrc(), "-1").replaceAll(",", ""));        // 상품판매가
        String mobile1WonYn = StringUtil.nvl(productVO.getBaseVO().getMobile1wonYn(), "N");    // 1원 상품 여부
        String bsnDealClf = StringUtil.nvl(productVO.getBaseVO().getBsnDealClf(), ProductConstDef.BSN_DEAL_CLF_COMMISSION);    // 거래유형
        long dispCtgr1No = Long.parseLong(StringUtil.nvl(productVO.getBaseVO().getDispCtgr1NoDe(), "0"));                    // 대카
        long dispCtgr2No = Long.parseLong(StringUtil.nvl(productVO.getBaseVO().getDispCtgr2NoDe(), "0"));                        // 중카
        long dispCtgrNo = Long.parseLong(StringUtil.nvl(productVO.getDispCtgrNo(), "0"));
        String engDispYn = productVO.getBaseVO().getEngDispYn();            // 영문11번가 노출 여부
        String selMthdCd = productVO.getBaseVO().getSelMthdCd();            // 판매방식 코드
        String dlvClf = productVO.getBaseVO().getDlvClf();    // 배송주체(셀러위탁배송여부체크)
        long soCupnAmt = Long.parseLong(StringUtil.nvl(productVO.getPriceVO().getSoCupnAmt(), "-1"));                    // 기본즉시할인가
        double prdCstmAplPrc = StringUtil.getDouble(StringUtil.nvl(productVO.getPriceVO().getCstmAplPrc(), "0").replaceAll(",", ""));    // 상품신고가
        String minusOptLimitYn = ProductConstDef.BSN_DEAL_CLF_COMMISSION.equals(bsnDealClf) ? optionValidate.isMinusOptLimit(dispCtgr1No, dispCtgr2No) : "N";    // 마이너스 옵션 제한 카테고리 여부 조회
        String selStatCd = productVO.getBaseVO().getSelStatCd();            // 판매상태
        String prdTypCd = productVO.getBaseVO().getPrdTypCd();                // 상품 구분 코드

        //옵션정보
        // 조합형 옵션 여부
        boolean isSendMixedOpt = OptionConstDef.OPT_CLF_CD_MIXED.equals(optClfCd) && StringUtil.isNotEmpty(productVO.getOptionVO().getColTitle());
        String mrgnPolicyCd = StringUtil.nvl(productVO.getOptionVO().getMrgnPolicyCd(), "");    // 마진정책
        String optSelectYn = productVO.getOptionVO().getOptSelectYn();                    // 선택형 (조합형, 독립형)
        String optionSaveYn = productVO.getOptionVO().getOptionSaveYn();                // 옵션팝업에서 저장 여부
        String certDownYn = productVO.getBaseVO().getCertDownYn();                    // 외부인증번호 사용 여부
        String certTypCd = productVO.getBaseVO().getTownSellerCertType(); // 인증 구분 코드
        String optTypCd = productVO.getOptionVO().getOptTypCd();                // 옵션 구분 코드 (01 : 날짜형)

        //회원정보
        boolean globalItgSeller = (prdNo > 0 ? false : productValidate.checkGlbItgIdsBySeller(productVO.getSelMnbdNo()));    // 해외 통합출고지 사용가능 셀러여부
        String memTypCd = productVO.getMemberVO().getMemTypCD();                                        // 회원 유형 코드
        String globalSellerYn = productVO.getMemberVO().getGlobalSellerYn();                                    // 글로벌 셀러 여부

        long chkDispCtgr1No = dispCtgr1No;
        long chkDispCtgr2No = dispCtgr2No;
        String[] ctgrOptPrcArr = null;
        boolean isDealRangeCheck = false;

        if (prdNo > 0) {
            ProductVO setProductVO = new ProductVO();
            setProductVO.setPriceVO(new PriceVO());
            setProductVO.setBaseVO(new BaseVO());
            setProductVO.setPrdNo(prdNo);
            setProductVO.getPriceVO().setEventNo(Long.parseLong(StringUtil.nvl(productVO.getPriceVO().getEventNo(), "-1")));
            setProductVO.getBaseVO().setPrdInfoType(productVO.getBaseVO().getPrdInfoType());
            setProductVO = this.getProduct4OptionPop(setProductVO);

            dispCtgrNo = setProductVO.getDispCtgrNo();
            prdTypCd = setProductVO.getBaseVO().getPrdTypCd();
            selStatCd = setProductVO.getBaseVO().getSelStatCd();

            // 상품의 가격과 옵션가를 비교하기 위해 필요 - 상품 기본즉시할인 적용된 판매가의 100%이상을 옵션가로 셋팅할수 없음.(11/30 봉재원 매니저 요청)
            if (selPrc <= 0) selPrc = setProductVO.getPriceVO().getSelPrc();
            prdCstmAplPrc = setProductVO.getPriceVO().getCstmAplPrc();

            // 1 원 상품여부 승인대기가 아닐경우 설정
            if (!ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(setProductVO.getBaseVO().getSelStatCd()))
                mobile1WonYn = StringUtil.nvl(setProductVO.getBaseVO().getMobile1wonYn(), "N");

            // 승인대기 상태에 특정매입 상품에서 마진정책을 마진율 -> 매입가로 수정시 옵션가 설정 여부 체크
            if (ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(setProductVO.getBaseVO().getSelStatCd()) &&
                (ProductConstDef.BSN_DEAL_CLF_SPECIFIC_PURCHASE.equals(setProductVO.getBaseVO().getBsnDealClf()) || ProductConstDef.BSN_DEAL_CLF_TRUST_PURCHASE.equals(bsnDealClf)) &&
                ("02".equals(setProductVO.getOptionVO().getMrgnPolicyCd()) || "02".equals(mrgnPolicyCd)) && !isSendMixedOpt && "Y".equals(
                optSelectYn)) {
                // 조합형/독립형 옵션 없음 & 구매자선택형 옵션 사용여부 "Y"일 경우 선택형옵션 사용여부 "N"
                optSelectYn = "N";
            }

            // 해외 통합출고지가 가능한 해외셀러인지 여부
            globalItgSeller = productValidate.checkGlbItgIdsByPrdNo(prdNo);

            // 수정일 경우 외부인증번호 사용 여부, 영문11번가 노출 여부, 상품 구분 코드 DB조회 한 값으로 Set.
            if ("Y".equals(optionSaveYn)) {
                certDownYn = setProductVO.getBaseVO().getCertDownYn();
                engDispYn = setProductVO.getBaseVO().getEngDispYn();
                selMthdCd = setProductVO.getBaseVO().getSelMthdCd();
                dispCtgr1No = Long.parseLong(StringUtil.nvl(setProductVO.getBaseVO().getDispCtgr1NoDe(), "0"));
                if (StringUtil.isNotEmpty(productVO.getBaseVO().getPrdTypCd()) &&
                    (ProductConstDef.PRD_TYP_CD_MART.equals(productVO.getBaseVO().getPrdTypCd()) || ProductConstDef.PRD_TYP_CD_NEW_MART.equals(productVO.getBaseVO().getPrdTypCd()))) {
                    prdTypCd = productVO.getBaseVO().getPrdTypCd();
                }
                certTypCd = setProductVO.getBaseVO().getCertTypCd();
                productVO.getBaseVO().setCertDownYn(certDownYn);
                productVO.getBaseVO().setEngDispYn(engDispYn);
                productVO.getBaseVO().setSelMthdCd(selMthdCd);
                productVO.getBaseVO().setSelBgnDy(StringUtil.left(StringUtil.nvl(setProductVO.getBaseVO().getSelBgnDy()), 8));
                productVO.getBaseVO().setSelEndDy(StringUtil.left(StringUtil.nvl(setProductVO.getBaseVO().getSelEndDy()), 8));
            }

            // 상품번호로 글로벌 셀러 여부 조회
            if (StringUtil.isEmpty(memTypCd)) {
                ProductVO sellerBO = sellerServiceImpl.getProductSellerInfo(prdNo);
                memTypCd = sellerBO.getMemberVO().getMemTypCD();
            }
            if (ProductConstDef.PRD_MEM_TYPE_GLOBAL.equals(memTypCd)) {
                globalSellerYn = "Y";
            }

            productVO.setBundleProduct(ProductConstDef.SetTypCd.BUNDLE.equals(setProductVO.getBaseVO().getSetTypCd()));
            minusOptLimitYn = setProductVO.getMinusOptLimitYn();
            if (!"Y".equals(productVO.getPrdCopyYn())) {
                if (setProductVO.getPriceVO().getEventNo() > 0) {
                    isDealRangeCheck = true;
                } else {
                    ProductVO dealPrdBO = dealServiceImpl.getShockingDealPrd(prdNo);
                    if (dealPrdBO != null && prdNo == dealPrdBO.getPrdNo()) isDealRangeCheck = true;
                }
            }
            chkDispCtgr1No = setProductVO.getBaseVO().getDispCtgr1NoDe();
            chkDispCtgr2No = setProductVO.getBaseVO().getDispCtgr2NoDe();

            // 기본즉시할인이  0보다 작은 경우 DB에서 조회함
            if (soCupnAmt < 0) {
                if (setProductVO.getPriceVO().getEventNo() > 0) {
                    ProductEvtVO productEvtVO = new ProductEvtVO();
                    productEvtVO.setEventNo(setProductVO.getPriceVO().getEventNo());
                    productEvtVO.setPrdNo(prdNo);
                    productEvtVO.setSelMnbdNo(setProductVO.getSelMnbdNo());

                    productEvtVO = dealServiceImpl.getAplPrdInfo(productEvtVO);
                    if (productEvtVO != null && productEvtVO.getEventPricePegVO() != null) {
                        soCupnAmt = dealValidate.getCalculatedDscAmt(productEvtVO.getEventPricePegVO());
                    }
                } else {
                    // 할인정보 조회
                    String descData = priceServiceImpl.getProductDiscountDataByPrdNo(prdNo, selPrc);
                    String[] descDatas = descData.split("\\|");
                    soCupnAmt = Long.parseLong(descDatas[10]); // SO 쿠폰할인금액
                }
            }
        }

        String createCd = StringUtil.nvl(productVO.getBaseVO().getCreateCd(), CreateCdTypes.MO_SIMPLEREG.getDtlsCd());
        createCd = (createCd + "00").substring(0, 4);

        // validateCertTypeAndKey check
        optionValidate.checkSetOptionInfo(createCd,
                                          isSendMixedOpt,
                                          optSelectYn,
                                          bsnDealClf,
                                          dlvClf,
                                          globalItgSeller,
                                          certDownYn,
                                          optTypCd,
                                          globalSellerYn,
                                          dispCtgr1No,
                                          selMthdCd,
                                          engDispYn);

        if (ProductConstDef.BSN_DEAL_CLF_COMMISSION.equals(bsnDealClf)) {
            ctgrOptPrcArr = optionValidate.checkCtgrOptPrcData(chkDispCtgr1No, chkDispCtgr2No, isDealRangeCheck);
        } else {
            ctgrOptPrcArr = new String[]{OptionConstDef.CTGR_OPT_RESTRICT_VALUE, OptionConstDef.CTGR_OPT_RESTRICT_PERCENT};
        }

        //dataBox.put("userNo", userNo);								// 현재 접속자 NO
        productVO.getPriceVO().setPrdCstmAplPrc(prdCstmAplPrc);          // 상품신고가
        productVO.getBaseVO().setMobile1wonYn(mobile1WonYn);            // 1원 상품 여부
        productVO.setMinusOptLimitYn(minusOptLimitYn);      // 마이너스 옵션 제한 여부
        productVO.getPriceVO().setSoCupnAmt(soCupnAmt);                  // 기본즉시할인가
        productVO.getBaseVO().setSelStatCd(selStatCd);                  // 판매상태
        productVO.getBaseVO().setPrdTypCd(prdTypCd);
        productVO.getMemberVO().setGlobalItgSeller(globalItgSeller);    // 해외 통합출고지가 가능한 해외셀러인지 여부 flag
        productVO.getMemberVO().setFrSeller(customerServiceImpl.isFrSellerByPrdNo(prdNo));    // 이태리 셀러여부
        productVO.getCategoryVO().setFreshCategory(categoryValidate.isFreshCategory(prdTypCd, dispCtgrNo, productVO.getSelMnbdNo()));
        productVO.getOptionVO().setCtgrOptPrcVal(ctgrOptPrcArr[0]);                // 카테고리별 옵션 가격 제한 값
        productVO.getOptionVO().setCtgrOptPrcPercent(ctgrOptPrcArr[1]);            // 카테고리별 옵션 가격 제한 값 (%)
        productVO.getPriceVO().setSelPrc(selPrc);           // 상품판매가

        // 타운홍보용 상품등록 시 옵션이 존재할 경우
        /*
        if( optMap != null && !optMap.isEmpty() ) {
            optionValidate.checkTownInfo(prdTypCd, certTypCd, optTypCd);
            optMap.put("isFreshCategory", productVO.getCategoryVO().isFreshCategory());
        }
        */
        optionValidate.checkTownInfo(prdTypCd, certTypCd, optTypCd);

        SellerAuthVO sellerAuthVO = new SellerAuthVO();
        sellerAuthVO.setSelMnbdNo(Long.valueOf(productVO.getSelMnbdNo()));
        sellerAuthVO.setAuthTypCd(ProductConstDef.AUTH_TYP_CD_STOCK_SELLER);
        sellerAuthVO.setAuthObjNo(String.valueOf(productVO.getSelMnbdNo()));
        sellerAuthVO.setObjClfCd(ProductConstDef.OBJ_CLF_CD_STOCK_SELLER);
        sellerAuthVO.setUseYn("Y");
        productVO.getMemberVO().setCertStockSeller(sellerServiceImpl.isPdSellerAuth(sellerAuthVO));
    }

    /**
     * 상품재고 list 조회
     *
     * @param productStockVO
     * @return List<ProductStockVO>
     * @throws OptionException
     */
    public List<ProductStockVO> getStockList(ProductStockVO productStockVO) throws OptionException {
        List<ProductStockVO> stockList = null;

        try {
            if (customerServiceImpl.isFrSellerByPrdNo(productStockVO.getPrdNo())) {
                stockList = optionMapper.getFrProductStockList(productStockVO);
            } else {
                if (ProductConstDef.PrdInfoType.EVENT.name().equals(productStockVO.getPrdInfoType())) {
                    return dealMapper.getEventProductStockLst(productStockVO);
                } else if (ProductConstDef.PrdInfoType.ROLLBACK.name().equals(productStockVO.getPrdInfoType())) {
                    return dealMapper.getEventProductStockOrgLst(productStockVO);
                } else {
                    if (ProductConstDef.SetTypCd.BUNDLE.value().equals(productStockVO.getSetTypCd())) {
                        List<ProductStockVO> dbList = optionMapper.getBundleProductStockLst(productStockVO);
                        List<ProductStockVO> resultList = new ArrayList<ProductStockVO>();

                        if (productStockVO.isNotNecessaryMasterStock() || !productStockVO.isOptPop()) {
                            resultList = dbList;
                        } else {
                            for (ProductStockVO productStockBO : dbList) {
                                resultList.add(productStockBO);
                                if (ProductConstDef.SetTypCd.SET.value().equals(productStockBO.getSetTypCd())) {
                                    List<ProductStockVO> compList = optionMapper.getOrignalPrdStockSetInfoList(productStockBO);
                                    for (ProductStockVO compInfo : compList) {
                                        compInfo.setStockNo(productStockBO.getStockNo());
                                        compInfo.setOptValueNm1(StringUtil.isEmpty(compInfo.getStockSetInfoVO().getOptionMixNm()) ? compInfo
                                            .getStockSetInfoVO()
                                            .getPrdNm() : compInfo.getStockSetInfoVO().getOptionMixNm());
                                        compInfo.setSetTypCd(productStockBO.getSetTypCd());
                                        compInfo.setAprvStatCd(productStockBO.getAprvStatCd());

                                        resultList.add(compInfo);
                                    }
                                }
                            }
                        }

                        return resultList;
                    } else {
                        return optionMapper.getProductStockLst(productStockVO);
                    }
                }
            }
        } catch (Exception e) {
            throw new OptionException("상품 재고정보  조회 오류 - Other Exception", e);
        }
        return stockList;
    }

    public List<ProductStockVO> getProductStockLst(ProductStockVO productStockVO) throws OptionException {
        if( !(ProductConstDef.PrdInfoType.EVENT.equals(productStockVO.getPrdInfoType()) || ProductConstDef.PrdInfoType.ROLLBACK.equals(productStockVO.getPrdInfoType()))
            && StringUtil.isEmpty(productStockVO.getSetTypCd())){
            ProductVO productVO = productMapper.getOnlySearchPdPrd(productStockVO.getPrdNo());
            if(productVO != null) productStockVO.setSetTypCd(productVO.getStrSetTypCd());
        }

        return optionMapper.getProductStockLst(productStockVO);
    }

    public List<StandardOptionItemVO> lookUpMappedOptItemListAtStd(StandardOptionGroupMappingVO standardOptionGroupMappingBO) throws OptionException {
        CategoryVO displayCategoryBO = categoryServiceImpl.findMappedStdOptGrpCategory(standardOptionGroupMappingBO.getDispCtgrNo());

        if (displayCategoryBO == null) return null;

        final long tempCtgrNo = standardOptionGroupMappingBO.getDispCtgrNo();
        standardOptionGroupMappingBO.setDispCtgrNo(displayCategoryBO.getDispCtgrNo());

        List<StandardOptionItemVO> resultList = optionMapper.getOptItemListAtStd(standardOptionGroupMappingBO);

        standardOptionGroupMappingBO.setDispCtgrNo(tempCtgrNo);

        return resultList;
    }

    public StandardOptionInfoVO getOptItemListAtStdWithPrd(StandardOptionGroupMappingVO standardOptionGroupMappingBO) throws OptionException {
        StandardOptionInfoVO result = new StandardOptionInfoVO();

        result.setStdOptItemList(lookUpMappedOptItemListAtStd(standardOptionGroupMappingBO));
        if (!StringUtil.isEmpty(result.getStdOptItemList())) {
            PdOptItemVO paramBO = new PdOptItemVO();
            paramBO.setPrdNo(standardOptionGroupMappingBO.getPrdNo());

            List<PdOptItemVO> optItemList = this.getProductOptItemLst(paramBO);
            if (!StringUtil.isEmpty(optItemList)) {
                result.setPrdOptItemList(new ArrayList<PdOptItemVO>());
                result.setPrdOptCustList(new ArrayList<PdOptItemVO>());

                for (PdOptItemVO productOptItemBO : optItemList) {
                    if (OptionConstDef.OPT_CLF_CD_CUST.equals(productOptItemBO.getOptClfCd())) {
                        result.getPrdOptCustList().add(productOptItemBO);
                    } else {
                        result.getPrdOptItemList().add(productOptItemBO);
                    }
                }
            }
        }

        return result;
    }

    public StandardOptionInfoVO getOptInfoListAtStdWithPrd(StandardOptionGroupMappingVO standardOptionGroupMappingVO) throws OptionException {
        final long tempNo = standardOptionGroupMappingVO.getStdOptNo();
        final long tempGrpNo = standardOptionGroupMappingVO.getStdOptGrpNo();

        StandardOptionInfoVO result = getOptItemListAtStdWithPrd(standardOptionGroupMappingVO);
        if (!StringUtil.isEmpty(result.getStdOptItemList())) {
            for (StandardOptionItemVO standardOptionItemBO : result.getStdOptItemList()) {
                standardOptionGroupMappingVO.setStdOptNo(standardOptionItemBO.getStdOptNo());
                standardOptionGroupMappingVO.setStdOptGrpNo(standardOptionItemBO.getStdOptGrpMappVO().getStdOptGrpNo());

                standardOptionItemBO.setStdOptValList(optionMapper.getOptValueListAtStd(standardOptionGroupMappingVO));
            }
        }

        if (!StringUtil.isEmpty(result.getPrdOptItemList())) {
            for (PdOptItemVO productOptItemBO : result.getPrdOptItemList()) {
                HashMap hmap = new HashMap();
                hmap.put("prdNo", standardOptionGroupMappingVO.getPrdNo());
                hmap.put("optItemNo", productOptItemBO.getOptItemNo());

                productOptItemBO.setPdOptValueVOList(optionMapper.getProductOptValueLstNew(hmap));
            }
        }

        standardOptionGroupMappingVO.setStdOptNo(tempNo);
        standardOptionGroupMappingVO.setStdOptGrpNo(tempGrpNo);

        return result;
    }

    public List<StandardOptionItemVO> getOptInfoListAtStd(StandardOptionGroupMappingVO standardOptionGroupMappingBO) throws OptionException {
        List<StandardOptionItemVO> stdOptItemList = lookUpMappedOptItemListAtStd(standardOptionGroupMappingBO);
        if (!StringUtil.isEmpty(stdOptItemList)) {
            for (StandardOptionItemVO standardOptionItemBO : stdOptItemList) {
                standardOptionGroupMappingBO.setStdOptNo(standardOptionItemBO.getStdOptNo());
                standardOptionGroupMappingBO.setStdOptGrpNo(standardOptionItemBO.getStdOptGrpMappVO().getStdOptGrpNo());
                standardOptionItemBO.setStdOptValList(optionMapper.getOptValueListAtStd(standardOptionGroupMappingBO));
            }
        }
        standardOptionGroupMappingBO.setStdOptNo(standardOptionGroupMappingBO.getStdOptNo());
        standardOptionGroupMappingBO.setStdOptGrpNo(standardOptionGroupMappingBO.getStdOptGrpNo());
        return stdOptItemList;
    }

    public void setOptionInfoProcess(ProductVO preProductVO, ProductVO productVO) throws OptionException {
        if (productVO.isUpdate()) {
            optionValidate.checkMaxOptCnt(productVO.getPrdNo());
        } else {

        }
        optionValidate.checkGlobalDlvOptClf(productVO);
        this.setProductInformationOptionVONew(productVO);
    }

    public List getOptionWghtHist(HashMap map) throws OptionException {
        List dataList = optionMapper.getOptionWghtHist(map);
        setDataMasking(dataList);
        return dataList;
    }

    private void setDataMasking(List dataList) {
        if (StringUtil.isEmpty(dataList)) {
            return;
        }

        Map<String, Object> dataMap = null;
        for (int i = 0, size = dataList.size(); i < size; i++) {
            dataMap = (Map<String, Object>) dataList.get(i);
            StringUtil.setDataMasking(dataMap, "UPDATE_ID");
        }
    }

    /**
     * 상품 옵션 Item/value리스트 조회
     *
     * @param prdNo
     * @return
     * @throws OptionException
     */
    public List<PdOptItemVO> getFrOptionItemValueList(long prdNo) throws OptionException {
        List<PdOptItemVO> optItemList = null;

        try {
            PdOptItemVO prdOptItmBO = new PdOptItemVO();
            prdOptItmBO.setPrdNo(prdNo);
            prdOptItmBO.setUseYn("Y");    // 현재 유효한 옵션만 조회
            optItemList = optionMapper.getProductFrOptItemLst(prdOptItmBO);

            if (optItemList != null) {
                PdOptItemVO optItemBO;
                HashMap<String, Object> param = new HashMap<String, Object>();
                param.put("prdNo", prdNo);

                for (int i = 0; i < optItemList.size(); i++) {
                    optItemBO = optItemList.get(i);

                    // 작성형 옵션이 아닐 경우 opt_value 정보 조회
                    if (!OptionConstDef.OPT_CLF_CD_CUST.equals(optItemBO.getOptClfCd())) {
                        param.put("optItemNo", optItemBO.getOptItemNo());
                        optItemBO.setPdOptValueVOList(optionMapper.getProductFrOptValueLst(param));
                    }
                }
            }
        } catch (Exception e) {
            throw new OptionException("옵션정보 조회 오류", e);
        }

        return optItemList;
    }

    /**
     * 상품옵션 정보 조회
     *
     * @param prdNo
     * @return
     * @throws OptionException
     */
    public ProductVO getProductFrOptionInfo(long prdNo) throws OptionException {
        ProductVO productBO = new ProductVO();
        productBO.setOptionVO(new OptionVO());
        try {
            productBO.setPrdNo(prdNo);
            //상품 옵션 Item/value리스트 조회
            productBO.getOptionVO().setProductOptItemVOList(this.getFrOptionItemValueList(prdNo));
        } catch (Exception e) {
            throw new OptionException("상품옵션 정보 조회 오류", e);
        }
        return productBO;
    }

    /**
     * 상품 옵션 Item/value리스트 조회
     * - 쇼킹딜 또는 상품 등록/수정용
     *
     * @param productVO
     * @return List<ProductOptItemBO>
     * @throws OptionException
     */
    public List<PdOptItemVO> getOptionItemValueList(ProductVO productVO) throws OptionException {
        List<PdOptItemVO> optItemList = null;

        try {
            PdOptItemVO prdOptItmBO = new PdOptItemVO();
            prdOptItmBO.setPrdNo(productVO.getPrdNo());
            prdOptItmBO.setEventNo(productVO.getPriceVO().getEventNo());
            prdOptItmBO.setPrdInfoType(productVO.getBaseVO().getPrdInfoType());

            // 옵션 아이템 리스트 조회
            optItemList = this.getProductOptItemLst(prdOptItmBO);

            if (optItemList != null) {
                PdOptItemVO optItemBO;
                HashMap param = new HashMap();
                param.put("prdNo", productVO.getPrdNo());
                param.put("eventNo", productVO.getPriceVO().getEventNo());
                param.put("prdInfoType", productVO.getBaseVO().getPrdInfoType());

                if ("Y".equals(productVO.getOptionVO().getMartOptionYn()) && optItemList.size() <= 0) {
                    PdOptItemVO tempBo = new PdOptItemVO();
                    tempBo.setPrdNo(productVO.getPrdNo());
                    tempBo.setOptClfCd(OptionConstDef.OPT_CLF_CD_MIXED);
                    optItemList.add(tempBo);
                }

                for (int i = 0; i < optItemList.size(); i++) {
                    optItemBO = optItemList.get(i);

                    param.put("optItemNo", optItemBO.getOptItemNo());
                    param.put("stockClmnPos", optItemBO.getStockClmnPos());

                    // 조합형 옵션일 경우 옵션명에 노출할 옵션값 정보 조회
                    if (OptionConstDef.OPT_CLF_CD_MIXED.equals(optItemBO.getOptClfCd())) {
                        param.put("martOptionYn", productVO.getOptionVO().getMartOptionYn());
                        param.put("ctlgNo", productVO.getCtlgVO().getCtlgNo());
                        param.put("searchText", productVO.getKeywdNm());
                        param.put("comboBox", productVO.getBaseVO().getModelNm());
                        param.put("start", productVO.getStart());
                        param.put("end", productVO.getEnd());
                        param.put("optTypCd", productVO.getOptionVO().getOptTypCd());
                        if (!sellerServiceImpl.getPdSellerAuth(productVO, ProductConstDef.AUTH_TYP_CD_STOCK_SELLER, ProductConstDef.OBJ_CLF_CD_STOCK_SELLER)) {
                            param.put("ptnrPrdSellerYN", "Y");
                        }

                        optItemBO.setPdOptValueVOList(this.getProductOptMixValueList(param));
                        optItemBO.setMartPrdYn(productVO.getMartPrdYn());
                    }
                    // 독립형은 stock table에서 재고 정보를 가져오지 않는다.
                    /*
                    else if(OptionConstDef.OPT_CLF_CD_IDEP.equals(optItemBO.getOptClfCd())){
                        optItemBO.setProductOptValueVOList(this.getProductOptIdpValueList(param));
                    }
                    */
                }
            }
        } catch (Exception e) {
            throw new OptionException("옵션정보 조회 오류", e);
        }
        return optItemList;
    }

    public ProductVO getProductOptionInfo(ProductVO productVO) throws OptionException {
        ProductVO rstProductVO = new ProductVO();
        rstProductVO.setOptionVO(new OptionVO());
        try {
            // 옵션 등록/수정 용 상품 추가 정보 조회
            rstProductVO = this.getProduct4OptionPop(productVO);
            if (rstProductVO == null) throw new OptionException("해당 상품이 존재하지 않습니다.");

            rstProductVO.setPrdNo(productVO.getPrdNo());
            rstProductVO.getOptionVO().setProductOptItemVOList(getOptionItemValueList(productVO));
        } catch (Exception e) {
            throw new OptionException("상품옵션 정보 조회 오류", e);
        }
        return rstProductVO;
    }

    public void updateOptionInfoProcess(ProductVO preProductVO, ProductVO productVO) throws OptionException {
        this.updateAddProductOptionApiYn(productVO);
        this.applyProductOptionCalc(productVO.getSelMnbdNo(), productVO.getOptionVO().getProductOptCalcVO());
    }

    public void insertOptionInfoProcess(ProductVO productVO) throws OptionException {
        this.insertProductOption(productVO);
    }

    /**
     * 예상배송비 조회
     * @param prdNo
     * @param optWght
     * @param optAddPrc
     * @param itgMemNo
     * @param dispCtgrNo
     * @param cstmAplPrc
     * @param addCstmAplPrc
     * @param type
     * @return
     * @throws OptionException
     */
    public long getOptionGlobalItgDeliveryCostInfo(long prdNo, long optWght, long optAddPrc, long itgMemNo, long dispCtgrNo, String cstmAplPrc, double addCstmAplPrc, String type) throws OptionException {
        long resultDlvCst = 0;
        HashMap resultMap = null;
        try {
            double uFinalDscPrc = 0;
            long finalDscPrc  = 0 ;
            long weight = 0;
            String hsCode = "";

            if (prdNo > 0) {
                // 할인모음가 조회
                if("O".equals(type)) {
                    String descData = priceServiceImpl.getProductDiscountDataByPrdNo(prdNo, 0);
                    String[] descDatas = descData.split("\\|");
                    finalDscPrc = Long.parseLong(descDatas[3]) + optAddPrc; // 할인모음가 + 옵션가
                } else if("N".equals(type)) {
                    PriceVO prcBO = priceServiceImpl.getProductPrice(prdNo);
                    uFinalDscPrc = prcBO.getCstmAplPrc() + Double.parseDouble(cstmAplPrc);
                }

                // 통합ID번호, HS-CODE, 상품무게 조회
                resultMap = deliveryServiceImpl.getGlobalItgInfoForPrdDeliveryCost(prdNo);
                if(resultMap != null){
                    if("O".equals(type) || "N".equals(type)) weight = Long.parseLong(StringUtil.nvl(resultMap.get("PRDWGHT"),"0")) + optWght; // 상품무게 + 옵션무게
                    itgMemNo = Long.parseLong(StringUtil.nvl(resultMap.get("ITGMEMNO"),"0"));
                    hsCode = (String)resultMap.get("HSCODE");
                } else {
                    throw new OptionException("해당상품은 통합출고지에 등록된 상품이 아닙니다.");
                }
            } else{
                if("O".equals(type)) {
                    finalDscPrc = optAddPrc;
                    weight = optWght;
                } else if("N".equals(type)) {
                    uFinalDscPrc = Double.parseDouble(cstmAplPrc);
                    weight = optWght;
                }

                // HS-CODE 조회
                CategoryVO displayCategoryBO = categoryServiceImpl.getServiceDisplayCategoryInfo(dispCtgrNo);
                hsCode = displayCategoryBO.getHsCode();
            }

            if(hsCode == null || "".equals(hsCode.trim())) throw new OptionException("상품에 해당하는 카테고리에 HS-CODE가 존재하지 않습니다.");
            if("O".equals(type)) {
                resultDlvCst = deliveryServiceImpl.getGlobalItgDeliveryCost(itgMemNo, finalDscPrc, weight, hsCode);
            } else if("N".equals(type)) {
                resultDlvCst = deliveryServiceImpl.getGlobalItgDeliveryCost(itgMemNo, (long) uFinalDscPrc, weight, hsCode);
            } else {
                resultDlvCst = deliveryServiceImpl.getGlobalItgDeliveryCost(itgMemNo, (long) addCstmAplPrc, optWght, hsCode);
            }
        } catch (Exception e) {
            throw new OptionException(e.getMessage());
        }
        return resultDlvCst;
    }

    /**
     * 옵션 변경 팝업에서 카테고리별 옵션가 설정 적용하기 위해 코드 조회 후 데이터 가공.
     * - 만원 이상의 경우 기본 -50% ~ 100% 내 옵션가 가능
     * - 매입 상품은 모든 카테고리 기본 -50% ~ 2000%으로 변경되어 해당 되지 않음
     *
     * @param selMnbdNo
     * @throws Exception
     */
    public HashMap makeCtgrOptPrcData(long selMnbdNo) throws Exception {
        HashMap<String, String> retHashMap = new HashMap<String, String>();
        SyCoDetailVO codeDetailVO = new SyCoDetailVO();
        codeDetailVO.setGrpCd(ProductConstDef.CTGR_OPT_PRC_CD);
        List<SyCoDetailVO> list = commonServiceImpl.getCodeDetail(codeDetailVO);
        if( !StringUtil.isEmpty(list) ) {
            StringBuffer ctgrBuff = new StringBuffer();
            StringBuffer prcBuff = new StringBuffer();
            StringBuffer dealPrcBuff = new StringBuffer();	// 쇼킹딜 상품용

            SyCoDetailVO code = new SyCoDetailVO();
            int listSize = list.size();

            for(int i = 0 ; i < listSize ; i++) {
                code = list.get(i);
                ctgrBuff.append("^").append(code.getDtlsCd());
                prcBuff.append("^").append(code.getDtlsComNm());
                dealPrcBuff.append("^").append(StringUtil.isNumber(code.getCdVal1()) ? code.getCdVal1() : "100");
            }

            retHashMap.put("ctgrChkData", ctgrBuff.toString().substring(1));
            retHashMap.put("prcChkData", prcBuff.toString().substring(1));
            retHashMap.put("dealPrcChkData", dealPrcBuff.toString().substring(1));
        } else {
            retHashMap.put("ctgrChkData", "");
            retHashMap.put("prcChkData", "");
            retHashMap.put("dealPrcChkData", "");
        }
        return retHashMap;
    }

    public List<ProductOptMartComboVO> getProductOptMartComboList(HashMap<String, Object> hmap) throws OptionException {
        try {
            return optionMapper.getProductOptMartComboList(hmap);
        } catch (Exception e) {
            throw new OptionException("마트옵션 검색 콤보박스 조회 오류", e);
        }
    }

    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) {

        // 옵션관련 설정값
        /*
        String optSelectYn = StringUtil.nvl(dataBox.getString("optSelectYn"));      //선택형인지 입력형인지
        if ("Y".equals(optSelectYn)) productVO.getOptionVO().setOptColCnt(dataBox.getLong("txtColCnt", 0));
        */

        /*
        productVO.getMemberVO()
                 .setCertStockSeller(sellerServiceImpl.getPdSellerAuth(productVO,
                                                                       ProductConstDef.AUTH_TYP_CD_STOCK_SELLER,
                                                                       ProductConstDef.OBJ_CLF_CD_STOCK_SELLER));
        if(!StringUtil.isEmpty(TmallCommonCode.searchListDetailByDtlsComNm(ProductConstDef.PTNR_PRD_CLF_CD_SELLER, String.valueOf(userNo)))) {
            productBO.setSellerStockCd(dataBox.getNotNullString("prdSellerStockCd", ""));
        }
        */

        // 수정인경우 또는 복사이면서 옵션이 없는경우 체크 함
        // 바닥페이지에 데이터가 있는경우는 이미 위에서 처리했기 때문에 옵션정보가 없는경우 체크한다.
        if(productVO.getOptionVO().getOptItemMap() == null || productVO.getOptionVO().getOptItemMap().isEmpty()) {
            if ("Y".equals(productVO.getPrdCopyYn())) {
                // 복사 인 경우
                if(!"20".equals(productVO.getBaseVO().getPrdTypCd())) {
                    checkOptionChangePrc(productVO);
                }
            } else{
                // 복사가 아닐 경우
                if(!"20".equals(productVO.getBaseVO().getPrdTypCd())) {
                    //optionValidate.checkMsgSoDscSelPrcForReg(productVO.getPrdNo(), productVO.getPriceVO().getSelPrc(), productVO.getPriceVO().getDscAmt(), productVO.getCategoryVO().getRootCtgrNo(), productVO.getCategoryVO().getMctgrNo(), productVO.getBaseVO().getBsnDealClf());
                    optionValidate.checkMsgSoDscSelPrcForReg(productVO);
                }
            }
        }else{
            // api에서 옵션변경없이 수정요청시에 판매가에따른 옵션 범위 체크
            if("0100".equals(StringUtils.defaultIfEmpty(productVO.getBaseVO().getCreateCd(), CreateCdTypes.MO_SIMPLEREG.getDtlsCd())) && !"Y".equals(productVO.getOptChangeYn()) && productVO.getPrdNo() > 0){
                checkOptionChangePrc(productVO);
            }
        }


        // 날짜형 옵션일 경우
        if( OptionConstDef.OPT_TYP_CD_DAY.equals(productVO.getBaseVO().getOptTypCd()) ) {
            // PIN번호 상품이 아닐 경우
            optionValidate.checkPinNumberType(productVO);

            // 판매용 상품일 경우
            if( ProductConstDef.PRD_TYP_CD_TOWN_SALES.equals(productVO.getBaseVO().getPrdTypCd()) ) {
                // 환불타입이 환불 불가가 아닐 경우
                optionValidate.checkRefundType(productVO);

                // 날짜형 옵션에 설정된 값으로 사용기간 설정
                /*
                dataBox.put("useLimitClfCd", ProductConstDef.PRD_TOWN_LMT_CLF_CD_CUST);
                dataBox.put("useBgnDy", dataBox.getString("optUseBgnDy"));
                dataBox.put("useEndDy", dataBox.getString("optUseEndDy"));
                dataBox.put("usePrdClfCd", "0:500");
                prdRegBO.setUseLimitClfCd(ProductConstDef.PRD_TOWN_LMT_CLF_CD_CUST);
                prdRegBO.setUseBgnDy(dataBox.getString("optUseBgnDy"));
                prdRegBO.setUseEndDy(dataBox.getString("optUseEndDy"));
                prdRegBO.setUsePrdClfCd("0:500");
                */

                productVO.setUseLimitClfCd(ProductConstDef.PRD_TOWN_LMT_CLF_CD_CUST);
                productVO.setUsePrdClfCd("0:500");
            }

            /*
            if( StringUtil.isNotEmpty(dataBox.getString("buyDsblDyCd")) ) {
                productVO.getPrdOthersVO().setBuyDsblDyCd(dataBox.getString("buyDsblDyCd"));
            }
            */
        }
        //렌탈상품 옵션체크
    }

    public void checkOptionChangePrc(ProductVO productVO) {
        ProductVO checkParamBO = new ProductVO();
        if("Y".equals(productVO.getPrdCopyYn())) {
            checkParamBO.setPrdNo(productVO.getBaseVO().getReRegPrdNo());
            checkParamBO.setPrdCopyYn("Y");
        } else {
            checkParamBO.setPrdNo(productVO.getPrdNo());
        }
        checkParamBO.getPriceVO().setSelPrc(productVO.getPriceVO().getSelPrc());
        checkParamBO.getPriceVO().setBuylCnPrc(productVO.getPriceVO().getSelPrc() - productVO.getPriceVO().getDscAmt());
        checkParamBO.getCategoryVO().setRootCtgrNo(productVO.getCategoryVO().getRootCtgrNo());
        checkParamBO.getCategoryVO().setMctgrNo(productVO.getCategoryVO().getMctgrNo());
        checkParamBO.getBaseVO().setBsnDealClf(productVO.getBaseVO().getBsnDealClf());
        checkParamBO.setSelMnbdNo(productVO.getSelMnbdNo());
        if("Y".equals(productVO.getIsOpenApi())) {
            String msg = optionValidate.checkMsgSoDscSelPrc(checkParamBO);
            if(StringUtil.isNotEmpty(msg)) throw new OptionException(msg);
        } else {
            if("Y".equals(productVO.getOptionVO().getOptSelectYn())) {
                String msg = optionValidate.checkMsgSoDscSelPrc(checkParamBO);
                if (StringUtil.isNotEmpty(msg)) throw new OptionException(msg);
            }
        }

    }

    /**
     * 상품 판매가의 옵션가 100% -50%를 체크
     * - 매입 상품의 경우 -50 ~ 2000%
     * @param productVO
     * @throws OptionException
     */
    public int getPdStockAddPrcCnt(ProductVO productVO) throws OptionException {
        try {
            if(productVO.getPriceVO().getEventNo() > 0){
                return optionMapper.getPdEventStockAddPrcCnt(productVO);
            }else{
                return optionMapper.getPdStockAddPrcCnt(productVO);
            }
        } catch (Exception ex) {
            throw new OptionException("상품 판매가의 옵션가 확인 오류", ex);
        }
    }

    /**
     * 옵션가가 1원 이상인 것이 있는지 조회
     */
    public int getPdStockAddPrcCnt1Over(ProductVO productVO) throws OptionException{
        try{
            if(productVO.getPriceVO().getEventNo() > 0){
                return optionMapper.getPdEventStockAddPrcCnt1Over(productVO);
            }else{
                return optionMapper.getPdStockAddPrcCnt1Over(productVO.getPrdNo());
            }
        } catch(Exception ex){
            throw new OptionException(ex);
        }
    }
}
