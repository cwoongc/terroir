package com.elevenst.terroir.product.registration.option.validate;

import com.elevenst.common.code.CacheKeyConstDef;
import com.elevenst.common.properties.PropMgr;
import com.elevenst.common.util.NumberUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.common.service.CommonServiceImpl;
import com.elevenst.terroir.product.registration.common.vo.SyCoDetailVO;
import com.elevenst.terroir.product.registration.deal.service.DealServiceImpl;
import com.elevenst.terroir.product.registration.deal.vo.ProductEvtVO;
import com.elevenst.terroir.product.registration.desc.entity.PdPrdDesc;
import com.elevenst.terroir.product.registration.desc.service.ProductDescServiceImpl;
import com.elevenst.terroir.product.registration.option.code.OptionConstDef;
import com.elevenst.terroir.product.registration.option.entity.PdOptItem;
import com.elevenst.terroir.product.registration.option.entity.PdOptValue;
import com.elevenst.terroir.product.registration.option.exception.OptionException;
import com.elevenst.terroir.product.registration.option.mapper.OptionMapper;
import com.elevenst.terroir.product.registration.option.message.OptionValidateExceptionMessage;
import com.elevenst.terroir.product.registration.option.service.OptionServiceImpl;
import com.elevenst.terroir.product.registration.option.vo.PdOptItemVO;
import com.elevenst.terroir.product.registration.option.vo.PdOptValueVO;
import com.elevenst.terroir.product.registration.option.vo.ProductOptLimitVO;
import com.elevenst.terroir.product.registration.option.vo.ProductStockSetInfoVO;
import com.elevenst.terroir.product.registration.option.vo.ProductStockVO;
import com.elevenst.terroir.product.registration.product.code.CreateCdTypes;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.service.ProductServiceImpl;
import com.elevenst.terroir.product.registration.product.validate.ProductValidate;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.service.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class OptionValidate {

    @Autowired
    OptionServiceImpl optionServiceImpl;

    @Autowired
    ProductDescServiceImpl productDescServiceImpl;

    @Autowired
    SellerServiceImpl sellerServiceImpl;

    @Autowired
    DealServiceImpl dealService;

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    CommonServiceImpl commonService;

    @Autowired
    ProductValidate productValidate;

    @Autowired
    OptionMapper optionMapper;

    @Autowired
    PropMgr propMgr;

    /**
     * 스마트옵션일 경우 이미지가 제대로 등록되어있는지 validateCertTypeAndKey 체크를 한다
     *
     * @param pdPrdDesc
     */
    @Deprecated
    public void checkSmartOption(PdPrdDesc pdPrdDesc) throws OptionException {
        try {
            PdOptItemVO pdOptItemVO = new PdOptItemVO();
            pdOptItemVO.setPrdNo(pdPrdDesc.getPrdNo());
            List<PdOptItemVO> productOptItemList = optionServiceImpl.getProductOptItemLst(pdOptItemVO);

            boolean isIdepOpt = false;
            boolean isCustOpt = false;
            String optTypCd = OptionConstDef.NULL_STRING;

            if (!StringUtil.isEmpty(productOptItemList)) {
                for (PdOptItemVO resProductOptItemBO : productOptItemList) {
                    if (OptionConstDef.OPT_CLF_CD_IDEP.equals(resProductOptItemBO.getOptClfCd())) {
                        isIdepOpt = true;
                    } else if (OptionConstDef.OPT_CLF_CD_CUST.equals(resProductOptItemBO.getOptClfCd())) {
                        isCustOpt = true;
                    }

                    if (!OptionConstDef.NULL_STRING.equals(resProductOptItemBO.getOptTypCd())) {
                        optTypCd = resProductOptItemBO.getOptTypCd();
                    }
                }
            }

            ProductVO productVO = new ProductVO();
            productVO.setIsOpenApi(OptionConstDef.USE_N);
            productVO.getBaseVO().setCreateCd(CreateCdTypes.MO_SIMPLEREG.getDtlsCd());
            productVO.setPrdNo(pdPrdDesc.getPrdNo());
            productVO.setIdepOpt(isIdepOpt);
            productVO.setCustOpt(isCustOpt);
            productVO.getOptionVO().setOptTypCd(optTypCd);
            String prdDtlTypCd = checkSmartOption(productVO);
            if (StringUtil.isEmpty(prdDtlTypCd) && StringUtil.isEmpty(productOptItemList)) {
                //스마트옵션으로 변경상품이지만, 기존에 스마트옵션으로 등록되어있지 않고, 이미지도 등록된게 없다면 오류 처리
                if ("API_DESIGN_EDITOR".equals(pdPrdDesc.getMode())) {
                    throw new OptionException(OptionValidateExceptionMessage.SMART_OPTION_ALL_REG_FOR_SMART_EDITOR_ERROR);
                } else {
                    throw new OptionException(OptionValidateExceptionMessage.SMART_OPTION_ALL_REG_FOR_SMART_EDITOR_ERROR);
                }
            }
        } catch (Exception ex) {
            throw new OptionException(ex.getMessage(), ex);
        }
    }

    public String checkSmartOption(ProductVO productVO) throws OptionException {
        String prdDtlTypCd = OptionConstDef.NULL_STRING;
        if (!OptionConstDef.USE_Y.equals(productVO.getIsOpenApi()) && !"0100".equals(productVO.getBaseVO().getCreateCd())) {
            //현재 상품이 스마트옵션인지 확인한다.
            PdPrdDesc productDetailBO = productDescServiceImpl.getProductDetailCont(productVO.getPrdNo());
            if (productDetailBO != null) prdDtlTypCd = productDetailBO.getPrdDtlTypCd();

            if (ProductConstDef.SMART_OPTION_TYPE.contains(prdDtlTypCd)) {
                if (productVO.isIdepOpt()) {
                    throw new OptionException(OptionValidateExceptionMessage.SMART_OPTION_NOT_ALLOW_IDEP_REG_ERROR);
                } else if (OptionConstDef.OPT_TYP_CD_DAY.equals(productVO.getOptionVO().getOptTypCd())) {
                    throw new OptionException(OptionValidateExceptionMessage.SMART_OPTION_NOT_ALLOW_DAY_REG_ERROR);
                }

                //TODO : 옵션을 수정했는지 하지 않았는지 구별할 수 있는값이 필요함
                PdOptItem pdOptItem = new PdOptItem();
                pdOptItem.setPrdNo(productVO.getPrdNo());
                pdOptItem.setOptClfCd("01");
                List<PdOptItem> options = optionServiceImpl.getProductOptItemLstAddOptClfCd(pdOptItem);

                if (StringUtil.isEmpty(options)) options = new ArrayList<PdOptItem>();

                int optionImageCnt = optionServiceImpl.getPrdStockImageCount(productVO.getPrdNo());  //옵션이미지가 등록되었는지 여부 확인

                //pd_stock의 첫번째 옵션값의 개수를 체크한다.
                HashMap<String, Object> hmap = new HashMap<String, Object>();
                hmap.put("prdNo", productVO.getPrdNo());
                hmap.put("optItemNo", "1");
                hmap.put("stockClmnPos", "1");
                List<PdOptValue> regOptList = optionServiceImpl.getProductOptMixValueList(hmap);
                if (StringUtil.isEmpty(regOptList)) regOptList = new ArrayList<PdOptValue>();

                if (productVO.isCustOpt() && StringUtil.isEmpty(options)) {
                    throw new OptionException(OptionValidateExceptionMessage.SMART_OPTION_ONLY_WRITE_REG_ERROR);
                }

                if (StringUtil.isEmpty(options)) {
                    throw new OptionException(OptionValidateExceptionMessage.SMART_OPTION_MENDATORY_OPTION_REG_ERROR);
                } else if (regOptList.size() != optionImageCnt) {
                    throw new OptionException(OptionValidateExceptionMessage.SMART_OPTION_ALL_REG_FOR_POPUP_ERROR);
                }
            }
        }
        return prdDtlTypCd;
    }

    public void checkRestrictFirstOption(ProductOptLimitVO resPdOptLmt,
                                         PdOptItemVO optItemBO,
                                                List<ProductStockVO> stockBOList,
                                                int selOptCnt) throws OptionException {
        if (resPdOptLmt != null && OptionConstDef.OPT_CLF_CD_MIXED.equals(optItemBO.getOptClfCd()) && stockBOList != null && stockBOList.size() > OptionConstDef.INT_ZERO) {
            if (selOptCnt > resPdOptLmt.getCombOptNmCnt()) {
                throw new OptionException("조합형 옵션명은 " + resPdOptLmt.getCombOptNmCnt() + "개까지 사용 가능합니다. 현재 상태로 옵션 수정이 불가능 하니, 조합형 옵션명을 " + resPdOptLmt.getCombOptNmCnt() + "개 이하로 등록해주세요.");
            }
            if (stockBOList.size() > resPdOptLmt.getCombOptCnt()) {
                throw new OptionException("조합 후 옵션값은 " + resPdOptLmt.getCombOptCnt() + "개까지 사용 가능합니다. 현재 상태로 옵션 수정이 불가능 하니, 조합 후 옵션값을 " + resPdOptLmt.getCombOptCnt() + "개 이하로 등록해주세요.");
            }
        } else if (resPdOptLmt != null && OptionConstDef.OPT_CLF_CD_IDEP.equals(optItemBO.getOptClfCd())) {
            if (selOptCnt > resPdOptLmt.getIdepOptNmCnt()) {
                throw new OptionException("독립형 옵션명은 " + resPdOptLmt.getIdepOptNmCnt() + "개까지 사용 가능합니다. 현재 상태로 옵션 수정이 불가능 하니, 독립형 옵션명을 " + resPdOptLmt.getIdepOptNmCnt() + "개 이하로 등록해주세요.");
            }
        }
    }

    public void checkRestrictLastOption(StringBuilder overOptItemNm,
                                               StringBuilder chkBuf,
                                               long totalRegOptSize,
                                               ProductOptLimitVO resProductOptLimitVO,
                                               boolean checkIsCustOpt,
                                               boolean checkIsIdepOpt,
                                               int custOptCnt,
                                               ProductVO productVO,
                                               String _type,
                                               Map<String, PdOptItemVO> optItemMap) throws OptionException {
        if (!OptionConstDef.NULL_STRING.equals(overOptItemNm.toString())) {
            throw new OptionException("옵션값은 " + totalRegOptSize + "개까지 사용 가능합니다. 현재 상태로 옵션 수정이 불가능 하니, " + overOptItemNm.toString() + " 의 옵션값을 " + totalRegOptSize + "개 이하로 등록해주세요.");
        }
        if (!OptionConstDef.NULL_STRING.equals(chkBuf.toString())) {
            throw new OptionException(chkBuf.toString());
        } else if (resProductOptLimitVO != null && checkIsCustOpt && custOptCnt > resProductOptLimitVO.getWriteOptNmCnt()) {
            throw new OptionException("작성형 옵션명은 " + resProductOptLimitVO.getWriteOptNmCnt() + "개까지 사용 가능합니다. 현재 상태로 옵션 수정이 불가능 하니, 작성형 옵션명을 " + resProductOptLimitVO.getWriteOptNmCnt() + "개 이하로 등록해주세요.");
        }

        if (checkIsIdepOpt && OptionConstDef.OPT_TYP_CD_CALC.equals(productVO.getOptionVO().getOptTypCd())) {
            throw new OptionException(OptionValidateExceptionMessage.NOT_USE_CALC_IDEP_ERROR);
        } else if (checkIsCustOpt && OptionConstDef.OPT_TYP_CD_CALC.equals(productVO.getOptionVO().getOptTypCd())) {
            throw new OptionException(OptionValidateExceptionMessage.NOT_USE_CALC_WRITE_ERROR);
        } else if ((optItemMap == null || optItemMap.size() == 0) && (!"ALL".equals(_type)) && OptionConstDef.OPT_TYP_CD_CALC.equals(productVO.getOptionVO().getOptTypCd())) {
            throw new OptionException(OptionValidateExceptionMessage.MIX_OPT_MIN_ONE_REG_ERROR);
        }
    }

    public void checkOptionInfo(ProductVO productBO,
                                ProductStockVO stockBO,
                                ProductStockVO prevStockBO,
                                       boolean isOnlyOneBarCode,
                                       StringBuilder asis,
                                       StringBuilder tobe) throws OptionException {
        if (ProductConstDef.PRD_TYP_CD_TOWN_SALES.equals(productBO.getBaseVO().getPrdTypCd()) && ProductConstDef.CERT_TYPE_101.equals(productBO.getBaseVO().getCertTypCd()) && !tobe.toString().equals(asis.toString())) {
            throw new OptionException(OptionValidateExceptionMessage.OUTSIDE_CERT_INFO_ONLY_CHANGE_STAT_ERROR);
        }
        if (productValidate.isPurchaseType(productBO.getBaseVO().getBsnDealClf()) && !ProductConstDef.PRD_SEL_STAT_CD_BEFORE_APPROVE.equals(productBO.getBaseVO().getSelStatCd())) {
            if (ProductConstDef.SetTypCd.BUNDLE.equals(productBO.getBaseVO().getSetTypCd())) {
                if (stockBO.getAddPrc() != prevStockBO.getAddPrc()) {
                    throw new OptionException(OptionValidateExceptionMessage.NOT_ALLOW_PURCHASE_OPT_PRC_CHANGE_ERROR);
                }
            } else {
                if (stockBO.getAddPrc() != prevStockBO.getAddPrc() || stockBO.getPuchPrc() != prevStockBO.getPuchPrc() || stockBO.getMrgnRt() != prevStockBO.getMrgnRt() || stockBO.getMrgnAmt() != prevStockBO.getMrgnAmt()) {
                    throw new OptionException(OptionValidateExceptionMessage.NOT_ALLOW_PURCHASE_OPT_PRC_CHANGE_ERROR);
                }
                if (ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(productBO.getBaseVO().getBsnDealClf()) && stockBO.getStckQty() != prevStockBO.getStckQty()) {
                    throw new OptionException(OptionValidateExceptionMessage.NOT_ALLOW_DIRECT_BUYING_STOCK_CHANGE_ERROR);
                }
                if (isOnlyOneBarCode && !prevStockBO.getBarCode().equals(stockBO.getBarCode())) {
                    throw new OptionException(OptionValidateExceptionMessage.AFTER_APPROVE_NO_CHANGE_BARCODE_ERROR);
                }
            }
        }
        if (ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(productBO.getBaseVO().getDlvClf())) {
            if (stockBO.getStckQty() != prevStockBO.getStckQty()) {
                throw new OptionException(OptionValidateExceptionMessage.NOT_ALLOW_SELL_FORMAL_DLV_STOCK_CHANGE_ERROR);
            }
            if (isOnlyOneBarCode && !prevStockBO.getBarCode().equals(stockBO.getBarCode())) {
                throw new OptionException(OptionValidateExceptionMessage.NOT_ALLOW_SELL_FORMAL_DLV_BARCODE_CHANGE_ERROR);
            }
        }
        if (ProductConstDef.SetTypCd.BUNDLE.equals(productBO.getBaseVO().getSetTypCd()) && stockBO.getStckQty() > OptionConstDef.LONG_ZERO) {
            throw new OptionException(OptionValidateExceptionMessage.NOT_INPUT_BUNDLE_PRD_STOCK_CHANGE_ERROR);
        }
    }

    public void checkIsNotOption(Map<String, PdOptItemVO> optItemMap, boolean isCustOpt) throws OptionException {
        if (optItemMap != null && optItemMap.entrySet().iterator().hasNext() && !isCustOpt) {
            throw new OptionException(OptionValidateExceptionMessage.CHECK_OPTION_INFO_ERROR);
        }
    }

    public void checkConsignNBsnDirectOption(ProductVO productVO,
                                                    List<ProductStockVO> prevStockVOList,
                                                    int selOptCnt,
                                                    int custOptCnt) throws OptionException {
        if (ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(productVO.getBaseVO().getDlvClf()) || ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(productVO.getBaseVO().getBsnDealClf())) {
            if (!ProductConstDef.SetTypCd.BUNDLE.equals(productVO.getBaseVO().getSetTypCd()) && OptionConstDef.USE_Y.equals(productVO.getOptionSaveYn()) && StringUtil.isEmpty(prevStockVOList) && selOptCnt > OptionConstDef.INT_ZERO) {
                throw new OptionException(OptionValidateExceptionMessage.CANT_CHANGE_OPTION_NO_ERROR);
            }
            if (custOptCnt > OptionConstDef.INT_ZERO) {
                throw new OptionException((ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(productVO.getBaseVO().getDlvClf()) ? OptionValidateExceptionMessage.SELL_FORMAL_DLV : OptionValidateExceptionMessage.RETAIL) + " " + OptionValidateExceptionMessage.NOT_ALLOW_WRITE_OPTION_ERROR);
            }
        }
    }

    /**
     * 계산형 옵션 정보를 체크한다.
     *
     * @throws OptionException
     */
    public void checkCalcOption(String optCalcItem1Nm,
                                       String optCalcItem2Nm,
                                       long optCalcItem1MinValue,
                                       long optCalcItem1MaxValue,
                                       long optCalcItem2MinValue,
                                       long optCalcItem2MaxValue,
                                       double optUnitPrc,
                                       long optSelUnit,
                                       String optUnitCd) throws OptionException {
        if (optCalcItem1Nm == OptionConstDef.NULL_STRING || optCalcItem2Nm == OptionConstDef.NULL_STRING || optCalcItem1MinValue == OptionConstDef.LONG_ZERO || optCalcItem2MinValue == OptionConstDef.LONG_ZERO || optCalcItem1MaxValue == OptionConstDef.LONG_ZERO || optCalcItem2MaxValue == OptionConstDef.LONG_ZERO || optUnitPrc == OptionConstDef.LONG_ZERO || optSelUnit == OptionConstDef.LONG_ZERO || optUnitCd == OptionConstDef.NULL_STRING) {
            throw new OptionException(OptionValidateExceptionMessage.NOT_ALLOW_CALC_OPTION_EMPTY_ERROR);
        }

        if ((optCalcItem1MinValue <= OptionConstDef.LONG_ZERO || optCalcItem1MinValue > OptionConstDef.MAX_CALC_PRC) || (optCalcItem2MinValue <= OptionConstDef.LONG_ZERO || optCalcItem2MinValue > OptionConstDef.MAX_CALC_PRC)) {
            throw new OptionException(OptionValidateExceptionMessage.ALLOW_CALC_OPTION_MIN_PRC_ERROR);
        }
        if ((optCalcItem1MaxValue <= OptionConstDef.LONG_ZERO || optCalcItem1MaxValue > OptionConstDef.MAX_CALC_PRC) || (optCalcItem2MaxValue <= OptionConstDef.LONG_ZERO || optCalcItem2MaxValue > OptionConstDef.MAX_CALC_PRC)) {
            throw new OptionException(OptionValidateExceptionMessage.ALLOW_CALC_OPTION_MAX_PRC_ERROR);
        }
        if (optUnitPrc <= OptionConstDef.LONG_ZERO || optUnitPrc > OptionConstDef.MAX_CALC_PRC) {
            throw new OptionException(OptionValidateExceptionMessage.ALLOW_CALC_OPTION_UNIT_PRC_ERROR);
        }
        String regex = "^[0-9]{0,7}[\\.]?\\d{0,3}$";
        if (!Pattern.matches(regex, String.valueOf(optUnitPrc))) {
            throw new OptionException(OptionValidateExceptionMessage.ALLOW_CALC_OPTION_UNIT_PRC_MAX_FLOAT_THREE_ERROR);
        }
        String[] floatNum = String.valueOf(optUnitPrc).split("\\.");
        if (floatNum != null && floatNum.length > 2) {
            throw new OptionException(OptionValidateExceptionMessage.ALLOW_CALC_OPTION_UNIT_PRC_ONE_POINT_ERROR);
        }
        if (optSelUnit <= OptionConstDef.LONG_ZERO || optSelUnit > OptionConstDef.MAX_CALC_PRC) {
            throw new OptionException(OptionValidateExceptionMessage.ALLOW_CALC_OPTION_SEL_UINT_ERROR);
        }
        if (optCalcItem1MinValue > optCalcItem1MaxValue || optCalcItem2MinValue > optCalcItem2MaxValue) {
            throw new OptionException(OptionValidateExceptionMessage.CALC_OPTION_MIN_VALUE_SMALLER_THAN_MAX_VALUE_ERROR);
        }
        if (optSelUnit > optCalcItem1MinValue || optSelUnit > optCalcItem2MinValue) {
            throw new OptionException(OptionValidateExceptionMessage.CALC_OPTION_MIN_VALUE_BIGGER_THAN_SEL_UNIT_VALUE_ERROR);
        }
        if (optCalcItem1MinValue % optSelUnit != OptionConstDef.LONG_ZERO || optCalcItem2MinValue % optSelUnit != OptionConstDef.LONG_ZERO || optCalcItem1MaxValue % optSelUnit != OptionConstDef.LONG_ZERO || optCalcItem2MaxValue % optSelUnit != OptionConstDef.LONG_ZERO) {
            throw new OptionException(OptionValidateExceptionMessage.CALC_OPTION_MIN_VALUE_AND_MAX_VALUE_CAN_ONLY_MULTIPLES_SALES_UNIT_ERROR);
        }

        //옵션명 특수문자 등록 불가
        String[] calcOptionStr = new String[9];
        calcOptionStr[0] = optCalcItem1Nm;
        calcOptionStr[1] = optCalcItem2Nm;
        calcOptionStr[2] = String.valueOf(optCalcItem1MinValue);
        calcOptionStr[3] = String.valueOf(optCalcItem2MinValue);
        calcOptionStr[4] = String.valueOf(optCalcItem1MaxValue);
        calcOptionStr[5] = String.valueOf(optCalcItem2MaxValue);
        calcOptionStr[6] = String.valueOf(optUnitPrc);
        calcOptionStr[7] = String.valueOf(optSelUnit);
        calcOptionStr[8] = optUnitCd;
        if (StringUtil.existsCharactersForArr(calcOptionStr, OptionValidateExceptionMessage.OPTION_SPECIAL_CHAR))
            throw new OptionException(OptionValidateExceptionMessage.OPTION_SPECIAL_CHAR_ERROR);
    }

    public void checkTownInfo(String prdTypCd, String certTypCd, String optTypCd) throws OptionException {
        if (ProductConstDef.PRD_TYP_CD_TOWN_PROMOTE.equals(prdTypCd)) {
            throw new OptionException(OptionValidateExceptionMessage.NOT_ALLOW_PIN_PRD_ZERO_OPTION_REG_ERROR);
        } else if (ProductConstDef.CERT_TYPE_102.equals(certTypCd)) {
            throw new OptionException(OptionValidateExceptionMessage.NOT_ALLOW_CERT_PRD_OPTION_REG_ERROR);
        } else if (OptionConstDef.OPT_TYP_CD_DAY.equals(optTypCd)
            && !ProductConstDef.PRD_TYP_CD_TOWN_SALES.equals(prdTypCd) && !ProductConstDef.PRD_TYP_CD_TOWN_PROMOTE.equals(prdTypCd)) {
            throw new OptionException(OptionValidateExceptionMessage.DATE_OPTION_ONLY_PIN_NUMBER_PRD_REG_ERROR);
        }
    }

    public void checkSetOptionInfo(String createCd,
                                   boolean isSendMixedOpt,
                                   String optSelectYn,
                                   String bsnDealClf,
                                   String dlvClf,
                                   boolean globalItgSeller,
                                   String certDownYn,
                                   String optTypCd,
                                   String globalSellerYn,
                                   long dispCtgr1No,
                                   String selMthdCd,
                                   String engDispYn) throws OptionException {
        // OpenAPI 등록이 아니고 조합형/독립형 옵션 존재 & 구매자선택형 옵션 사용여부 "Y"가 아닐경우
        if (!"01".equals(createCd.substring(0, 2)) && isSendMixedOpt && !OptionConstDef.USE_Y.equals(optSelectYn))
            throw new OptionException(OptionValidateExceptionMessage.MIX_OPTION_CHECK_ERROR);

        // 독립형 옵션 존재일 경우
        /*
        if (isSendIndepOpt) {
            if (!ProductConstDef.BSN_DEAL_CLF_COMMISSION.equals(bsnDealClf)) {
                throw new OptionException(OptionValidateExceptionMessage.NOT_ALLOW_PURCHAE_PRD_WRITE_OPTION_REG_ERROR);
            } else if (ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(dlvClf)) {
                throw new OptionException(OptionValidateExceptionMessage.NOT_ALLOW_SELL_FORMAL_DLV_PRD_IDEP_OPTION_REG_ERROR);
            }
        }

        // 해외 통합출고지가 가능한 해외셀러인 경우
        if (isSendIndepOpt && globalItgSeller) {
            throw new OptionException(OptionValidateExceptionMessage.ALLOW_GLOBAL_PRD_SETTING_WGHT_AND_CAN_MIX_OPTION_REG_ERROR);
        }
        */

        // 외부인증번호 사용 시
        if (OptionConstDef.USE_Y.equals(certDownYn)) {
            // 날짜형 옵션일 경우
            if (OptionConstDef.OPT_TYP_CD_DAY.equals(optTypCd)) {
                throw new OptionException(OptionValidateExceptionMessage.NOT_ALLOW_OUTSIDE_CERT_INFO_DATE_OPTION_REG_ERROR);
            }

            // 조합형이 아닐 경우
            /*
            if (isSendIndepOpt) {
                throw new OptionException(OptionValidateExceptionMessage.ALLOW_OUTSIDE_CERT_INFO_CAN_MIX_OPTION_REG_ERROR);
            }
            */
        }

        // 날짜형 옵션일 경우
        if (OptionConstDef.OPT_TYP_CD_DAY.equals(optTypCd)) {
            // 글로벌 셀러일 경우
            if (OptionConstDef.USE_Y.equals(globalSellerYn)) {
                throw new OptionException(OptionValidateExceptionMessage.NOT_ALLOW_GLOBAL_SELLER_DATE_OPTION_REG_ERROR);
            }

            // 대카테고리가 해외 쇼핑일 경우
            String globalDispCtgr1No = propMgr.get1hourTimeProperty(CacheKeyConstDef.GOLBAL_DISP_CTGR1_NO);
            if (globalDispCtgr1No.equals(String.valueOf(dispCtgr1No))) {
                throw new OptionException(OptionValidateExceptionMessage.NOT_ALLOW_GLOBAL_CTGR_DATE_OPTION_REG_ERROR);
            }

            // 고정가 판매가 아닐 경우
            if (!ProductConstDef.PRD_SEL_MTHD_CD_FIXED.equals(selMthdCd)) {
                throw new OptionException(OptionValidateExceptionMessage.ALLOW_DATE_OPTION_ABAILABLE_FIX_SALE_REG_ERROR);
            }

            // 영문 11번가 노출 시 날짜형 옵션 등록할 경우
            if (OptionConstDef.USE_Y.equals(engDispYn)) {
                throw new OptionException(OptionValidateExceptionMessage.NOT_ALLOW_ENG_11ST_DATE_OPTION_REG_ERROR);
            }

            // 날짜형 옵션일 경우 조합형 옵션인지 체크
            /*
            if (isSendIndepOpt) {
                throw new OptionException(OptionValidateExceptionMessage.ALLOW_DATE_OPTION_AVAILABLE_MIX_OPTION_REG_ERROR);
            }
            */
        }
    }

    public int checkMaxOptCnt(long prdNo) throws OptionException {
        int selOptCnt = optionServiceImpl.getSelectOptionCnt(prdNo);
        if (selOptCnt > OptionConstDef.MAX_OPTION_CNT) {
            throw new OptionException(OptionValidateExceptionMessage.MAX_MIX_OPTION_REG_ERROR);
        }
        return selOptCnt;
    }


    public void checkGlobalDlvOptClf(ProductVO productVO) throws OptionException {
        if("Y".equals(productVO.getBaseVO().getGblDlvYn())){
            if(productVO.isUpdate()){

                List prdNoList = new ArrayList();
                prdNoList.add(productVO.getPrdNo());

                if(optionServiceImpl.getOptClfCdCnt(prdNoList) > 0){
                    throw new OptionException(OptionValidateExceptionMessage.GBL_DLV_NOT_OPT);
                }
            }else{
                if("02".equals(productVO.getOptionVO().getOptClfCd())){
                    throw new OptionException(OptionValidateExceptionMessage.GBL_DLV_NOT_OPT);
                }
            }
        }
    }

    /**
     * 옵션명, 옵션값 유효성 체크
     *
     * @param type
     * @param data
     * @return boolean
     */
    public void checkOptNmVal(String type, String data, long userNo) {
        try {
            String msg = checkMsgOptNmVal(type, data, userNo);
            if (StringUtil.isNotEmpty(msg)) {
                throw new OptionException(msg);
            }
        } catch (OptionException e) {
            throw e;
        }
    }

    /**
     * 옵션명, 옵션값 유효성 체크
     *
     * @param type
     * @param data
     * @return String
     */
    public String checkMsgOptNmVal(String type, String data, long userNo) {

        try {
            String msg = OptionConstDef.NULL_STRING;

            if (StringUtil.isEmpty(type) || StringUtil.isEmpty(data)) msg = OptionValidateExceptionMessage.MISSING_PARAMETER_ERROR;

            type = type.toUpperCase();
            data = data.trim();

            String pattern = OptionConstDef.USE_N.equals(type) ? OptionConstDef.OPTION_RESTRICT_SPECIAL_STRING : OptionConstDef.OPTION_RESTRICT_SPECIAL_STRING_ADD_COMMA;
            List<String> equalPattern = Arrays.asList("NULL_STRING");

            Pattern REG_OPT_SEPECIAL = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            Matcher mOpt = REG_OPT_SEPECIAL.matcher(data);

            String trgtNm = OptionConstDef.USE_N.equals(type) ? OptionConstDef.OPTION_NAME : OptionConstDef.OPTION_VALUE;
            trgtNm = OptionConstDef.USE_P.equals(type) ? OptionConstDef.OPTION_PRC : trgtNm;
            int trgtMaxLength = OptionConstDef.USE_N.equals(type) ? OptionConstDef.MAX_OPT_NM_LEN : OptionConstDef.MAX_OPT_VAL_LEN;
            if (!OptionConstDef.USE_N.equals(type)) {
                if (StringUtil.nvl(propMgr.get1hourTimeProperty(CacheKeyConstDef.OPT_ITEM_VALUE_EX_SELLER), OptionConstDef.NULL_STRING).indexOf("|" + userNo + "|") > -1) {
                    trgtMaxLength = OptionConstDef.MAX_OPT_VAL_EX_LEN;
                }
                if (OptionConstDef.USE_W.equals(type)) {
                    trgtMaxLength = OptionConstDef.MAX_OPT_WRITE_VAL_LEN;
                }
            }

            // 필수 입력 체크
            if (StringUtil.isEmpty(data)) {
                msg = trgtNm + "을 입력해주십시오.";
            }
            // 특수문자 입력 체크
            else if (mOpt.find()) {
                if (OptionConstDef.USE_N.equals(type)) {
                    msg = OptionValidateExceptionMessage.NOT_ALLOW_OPTION_SPECIAL_CHAR_ERROR;
                } else {
                    msg = OptionValidateExceptionMessage.NOT_ALLOW_OPTION_SPECIAL_ADD_COMMA_CHAR_ERROR;
                }
            }
            // Max Length 체크 : 옵션명 -한글 20자/영문(숫자)40자, 옵션값-한글 40자/영문(숫자)80자
            else if (data.getBytes("EUC-KR").length > trgtMaxLength) {
                msg = trgtNm + "은 공백포함 한글 " + (trgtMaxLength / 2) + "자/영문(숫자)" + trgtMaxLength + "자를 넘을 수 없습니다.";
            } else if (equalPattern.contains(data.toUpperCase())) {
                msg = trgtNm + "은 [" + data + "]를 입력할 수 없습니다.";
            }
            // 정통망법 개정에 따른 제한어 체크(주민등록번호)
            else if (OptionConstDef.USE_N.equals(type)) {
            /*
            try	{
                List<CodeDetail> detailCodes = TmallCommonCode.getListCodeDetail(CODE_PD129);

                String notSpaceData = data.replaceAll(" ", OptionConstDef.NULL_STRING);
                boolean isExist = false;
                List<String> restDatas = new ArrayList<String>();

                for(CodeDetail code : detailCodes){
                    if(!isExist && notSpaceData.indexOf(code.getCodeValue1()) > -1){
                        isExist = true;
                    }
                    restDatas.add(code.getCodeValue1());
                }

                if(isExist)
                    msg = "정통망법에 따라 "+restDatas.toString()+"는 사용이 불가합니다.";
            } catch(Exception ex) {
                msg = "하위코드 목록 조회 오류[Error Message:"+ex.getMessage();
            }
            */
            }
            return msg;
        } catch(OptionException e) {
            throw new OptionException(e);
        } catch (UnsupportedEncodingException e) {
            throw new OptionException(e);
        }
    }

    public String checkMsgOptWghtVal(String type, String data) {
        if (StringUtil.isEmpty(type)) return OptionValidateExceptionMessage.MISSING_PARAMETER_ERROR;

        data = OptionConstDef.NULL_STRING.equals(data) ? "0" : data.trim();
        long num = OptionConstDef.LONG_ZERO;

        try {
            num = Long.parseLong(data);
        } catch (NumberFormatException e) {
            return OptionValidateExceptionMessage.OPTION_WGHT_ONLY_NUMBERIC_ERROR;
        }
        if (num < OptionConstDef.LONG_ZERO) {
            return OptionValidateExceptionMessage.OPTION_WGHT_BIGGER_THAN_ZERO_ERROR;
        } else if (num > OptionConstDef.MAX_WGHT) {
            return OptionValidateExceptionMessage.OPTION_WGHT_SMALLER_TNAN_3000_ERROR;
        }
        return OptionConstDef.NULL_STRING;
    }

    public String checkOptAddPrc(long selPrc, long dscSelPrc, String optAddPrc, long addPrc, String mobile1WonYn) {
        String msg = OptionConstDef.NULL_STRING;
        if (selPrc != OptionConstDef.LONG_ZERO && dscSelPrc <= OptionConstDef.LONG_ZERO) {
            msg = OptionValidateExceptionMessage.BARGAIN_PRICE_BIGGER_THAN_ZERO_ERROR;
        } else if (StringUtil.isEmpty(optAddPrc)) {
            //옵션가
            msg = OptionValidateExceptionMessage.OPTION_RRICE_EMPTY_ERROR;
        }

        // 옵션가 원단위 입력  불가 체크
        if (addPrc % OptionConstDef.MIM_OPT_PRC != OptionConstDef.LONG_ZERO) {
            msg = OptionValidateExceptionMessage.OPTION_PRICE_CAN_TEN_WON_UNIT_ERROR;
        } else if (addPrc >= OptionConstDef.MAX_OPT_PRC) {
            // 옵션가Max 체크
            msg = OptionValidateExceptionMessage.OPTION_PRICE_SMALLER_THAN_TEN_THOUSAND_ERROR;
        }

        if (OptionConstDef.USE_Y.equals(mobile1WonYn) && addPrc > OptionConstDef.LONG_ZERO) {
            msg = OptionValidateExceptionMessage.PRD_PRICE_IS_ONE_CANT_OPTION_PRICE_ZERO_ERROR;
        }
        return msg;
    }

    /**
     * 옵션가격 유효성 체크 method
     *
     * @param optAddPrc
     * @param selPrc
     * @param mobile1WonYn
     * @param minusOptLimitYn
     * @param ctgrOptPrcVal
     * @param ctgrOptPrcPercent
     * @param soCupnAmt
     * @param mrgnPolicyCd
     * @param rentalPrdYn
     * @param selMnbdNo
     * @return
     */
    public String checkMsgOptPrice(String optAddPrc,
                                      long selPrc,
                                      String mobile1WonYn,
                                      String minusOptLimitYn,
                                      String ctgrOptPrcVal,
                                      String ctgrOptPrcPercent,
                                      long soCupnAmt,
                                      String mrgnPolicyCd,
                                      boolean rentalPrdYn,
                                      long selMnbdNo) {
        long dscSelPrc = selPrc - soCupnAmt; // 상품 판매가에 기본즉시할인 적용가

        long addPrc = StringUtil.getLong(optAddPrc.replaceAll(",", OptionConstDef.NULL_STRING));

        String msg = checkOptAddPrc(selPrc, dscSelPrc, optAddPrc, addPrc, mobile1WonYn);
        if (StringUtil.isNotEmpty(msg)) return msg;

        // 옵션가 예외 금액 조회
        int optAddPrcLimit = this.getSellerOptAddPrcLimit(selMnbdNo);

        // 2012/11/09 이후 판매가 2000원 미만은 마이너스 옵션가 적용불가
        // 2010/03/11 이후 등록된 상품에 대해 판매가의 100%이상 적용 허용하지 않음.
        // 2010/03/11 이후 등록된 상품에 대한로직 제거(2010.12.03 정기) prdCreateDt > ProductConstDef.DATE_CHECK_OPTION_PRICE 조건 제거
        // 플러스 옵션은 판매가에서 100% 또는 200% 까지 설정가능 (2010.12.13 정기)
        if (optAddPrcLimit <= OptionConstDef.INT_ZERO) {
            if (selPrc != OptionConstDef.LONG_ZERO) {
                if (selPrc >= OptionConstDef.CHECK_STANDARD_SEL_PRC_FOR_OPT) {
                    if (addPrc > selPrc * Integer.parseInt(ctgrOptPrcVal, 10)) {
                        return "옵션가는 판매가의 +" + ctgrOptPrcPercent + " 까지 설정하실 수 있습니다.";
                    }
                } else if (selPrc < OptionConstDef.CHECK_STANDARD_SEL_PRC_FOR_OPT && addPrc > OptionConstDef.CHECK_STANDARD_OPT_PRC) {
                    return OptionValidateExceptionMessage.OPTION_PRICE_MAX_10000_ERROR;
                }
            }
            // 마이너스 옵션의 경우 날짜제한없이 판매가의-50%이상 적용 허용하지 않음. (2010.07.09 강민경M 요청)
            // 마이너스 옵션의 경우 판매가에 기본즉시할인가를 적용한 금액에 -50%까지 적용함 (2010.12.13 정기)
            if (selPrc >= OptionConstDef.CHECK_MIN_SEL_PRC_FOR_OPT) {
                if (OptionConstDef.LONG_ZERO >= (dscSelPrc + addPrc)) {
                    return OptionValidateExceptionMessage.MINUS_OPTION_BIGGER_THAN_ZERO_PRICE_ERROR;
                } else if (OptionConstDef.LONG_ZERO > (selPrc + (addPrc * 2))) {
                    return OptionValidateExceptionMessage.NOT_ALLOW_OPTION_PRICE_BIGGER_THAN_MINUS_50_PERCENT_ERROR;
                }
            } else if (addPrc < OptionConstDef.LONG_ZERO) {
                return OptionValidateExceptionMessage.NOT_ALLOW_MINUS_OPTION_PRICE_SMALLER_THAN_PRD_PRICE_2000_ERROR;
            }
        } else {
            if (addPrc > selPrc * (optAddPrcLimit / 100)) {
                return "옵션가는 판매가의 +" + optAddPrcLimit + "% 까지 설정하실 수 있습니다.";
            } else if (addPrc < OptionConstDef.LONG_ZERO && (((addPrc * -1) / selPrc) * 100) > 99) {
                return OptionValidateExceptionMessage.ALLOW_OPTION_PRICE_SET_PRD_PRICE_MINUS_99_PERCENT_ERROR;
            }
        }

        // 마이너스 옵션 불가 카테고리 체크
        if (minusOptLimitYn == OptionConstDef.USE_Y && addPrc < OptionConstDef.LONG_ZERO) {
            return OptionValidateExceptionMessage.NOT_ALLOW_MINUS_OPTION_PRICE_ERROR;
        } else if (rentalPrdYn && addPrc > OptionConstDef.LONG_ZERO) {
            return OptionValidateExceptionMessage.OPTION_PRIEC_CANT_EXCEED_ZERO_IF_PRD_PRICE_IS_ONE_ERROR;
        }
        return OptionConstDef.NULL_STRING;
    }

    // 옵션 신고가 체크
    public String checkMsgOptCstmAplPrc(String cstmAplPrc, boolean globalItgSeller, double prdCstmAplPrc) {
        double lCstmAplPrc = 0;
        if(!globalItgSeller) {
            if(StringUtil.isNotEmpty(cstmAplPrc) && !OptionConstDef.NULL_STRING.equals(StringUtil.nvl(cstmAplPrc, OptionConstDef.NULL_STRING)) && StringUtil.getDouble(cstmAplPrc) != OptionConstDef.LONG_ZERO) {
                return OptionValidateExceptionMessage.NOT_ALLOW_OPTION_PRD_REPORT_PRC_REG_SELLER_ERROR;
            }else
                return OptionConstDef.NULL_STRING;
        }
        cstmAplPrc = StringUtil.nvl(cstmAplPrc, "0");
        // 신고가 체크
        if (StringUtil.isEmpty(cstmAplPrc)) {
            return OptionValidateExceptionMessage.OPTION_PRD_REPORT_PRC_EMPTY_ERROR;
        }

        try {
            lCstmAplPrc = Double.parseDouble(cstmAplPrc.replaceAll(",", OptionConstDef.NULL_STRING));
        } catch (Exception e) {
            return OptionValidateExceptionMessage.OPTION_PRD_REPORT_PRC_NUMBERIC_TYPE_ERROR;
        }
        if(lCstmAplPrc < 0 )
            return OptionValidateExceptionMessage.NOT_ALLOW_OPTION_PRD_REPORT_PRC_MINUS_VALUE_ERROR;

        String [] arrCstmAplPrc =  StringUtil.split(cstmAplPrc,".");
        if(arrCstmAplPrc.length >1 && arrCstmAplPrc[1].length()>2)
            return OptionValidateExceptionMessage.ALLOW_OPTION_PRD_REOPRT_PRC_INPUT_TWO_FLOAT_ERROR;

        if(!globalItgSeller && lCstmAplPrc != OptionConstDef.DOUBLE_ZERO)
            return OptionValidateExceptionMessage.ALLOW_OPTION_PRD_REPORT_PRC_ABORD_RELEASE_SELLER_ERROR;
        else if(!globalItgSeller)
            return OptionConstDef.NULL_STRING;
        return OptionConstDef.NULL_STRING;
    }

    /**
     * 옵션 재고수량 유효성 체크 method
     * @param optQty
     * @param stckStatCd
     * @param bsnDealClf
     * @param dlvClf
     * @return
     */
    public String checkMsgOptStock(String optQty, String stckStatCd, String bsnDealClf, String dlvClf) {
        // 재고상태 체크
        String msg = checkMsgOptStckStatCd(stckStatCd);
        if (StringUtil.isNotEmpty(msg)) return msg;

        // 재고수량 체크
        if (StringUtil.isEmpty(optQty)) {
            return OptionValidateExceptionMessage.INPUT_STOCK_QTY_ERROR;
        }
        long stckQty = OptionConstDef.LONG_ZERO;
        try {
            stckQty = StringUtil.getLong(optQty.replaceAll(",", OptionConstDef.NULL_STRING));
        } catch (Exception e) {
            return OptionValidateExceptionMessage.ALLOW_STOCK_QTY_ONLY_NUMBERIC_TYPE_ERROR;
        }

        // 재고수량 음수 여부
        if( stckQty < OptionConstDef.LONG_ZERO) {
            return OptionValidateExceptionMessage.NOT_ALLOW_STOCK_QTY_MINUS_VALUE_ERROR;
        }

        if(!ProductConstDef.BSN_DEAL_CLF_DIRECT_PURCHASE.equals(bsnDealClf) && !ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(dlvClf) && OptionConstDef.PRD_STCK_STAT_CD_USE.equals(stckStatCd) && stckQty == OptionConstDef.LONG_ZERO){
            // 사용함 상태일 경우 재고수량은 0보다 커야 함
            return OptionValidateExceptionMessage.IF_USE_STOCK_STAT_STOCK_QTY_MORE_THAN_ZERO_VALUE_ERROR;
        }

        return OptionConstDef.NULL_STRING;
    }

    /**
     * 재고상태 유효성 체크 method
     * @param stckStatCd
     * @return
     */
    public String checkMsgOptStckStatCd(String stckStatCd) {
        // 재고상태 체크
        if (StringUtil.isEmpty(stckStatCd)) {
            return OptionValidateExceptionMessage.INPUT_STOCK_STAT_ERROR;
        }
        // 재고상태 유효성 체크
        else if (!OptionConstDef.PRD_STCK_STAT_CD_USE.equals(stckStatCd) && !OptionConstDef.PRD_STCK_STAT_CD_SOLDOUT.equals(stckStatCd) && !OptionConstDef.PRD_STCK_STAT_CD_NOUSE.equals(stckStatCd)) {
            return OptionValidateExceptionMessage.WRONG_STOCK_STAT_ERROR;
        }
        return OptionConstDef.NULL_STRING;
    }

    /**
     * 날짜형 옵션 체크
     * @param data
     * @param optNm
     * @param idx
     * @return String
     */
    public String checkDayOptVal(String data, String optNm, int idx) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
        Pattern pat = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");

        // 날짜 형식 체크
        if( data == null || data.length() != 10 || !pat.matcher(data).find() ) {
            return optNm + "의" + idx + "번째 옵션값 [" + data + "]을 확인 하십시오.\n날짜형식이 올바르지 않습니다.\nYYYY-MM-DD형식으로 입력해 주십시오.";
        }

        // 유효한 날짜인지 체크
        String tempDate = OptionConstDef.NULL_STRING;
        try {
            tempDate = formatter.format(formatter.parse(data));
        } catch(java.text.ParseException pe) {
        }
        if( !tempDate.equals(data) ) {
            return optNm + "의" + idx + "번째 옵션값 [" + data + "]을 확인 하십시오.\n유효하지 않은 날짜입니다.";
        }

        return OptionConstDef.NULL_STRING;
    }

    /**
     * 마이너스 옵션 제한 카테고리 여부 조회
     * @param dispCtgr1No
     * @param dispCtgr2No
     * @return isMinusOptLimit
     * @throws OptionException
     */
    public String isMinusOptLimit(long dispCtgr1No, long dispCtgr2No) throws OptionException{
        String isMinusOptLimit = "N";
/*
        final String cacheKey = "MINUS_OPT_LIMIT_CTGR";
        ProductCacheUtil cache = new ProductCacheUtil();
        String minusOptLimitCtgr = (String)cache.getEsoCacheData(cacheKey);

        if( StringUtil.isEmpty(minusOptLimitCtgr) ) {
            minusOptLimitCtgr = StringUtil.nvl(progMgr.getRealTimeProperty(cacheKey));
            cache.setEsoCacheData(cacheKey, minusOptLimitCtgr, ProductCacheUtil.CACHE_TIME_24H);
        }

        List<String> minusOptLimitCtgrList = Arrays.asList(minusOptLimitCtgr.split(","));
        if( minusOptLimitCtgrList.contains(String.valueOf(dispCtgr1No)) || minusOptLimitCtgrList.contains(String.valueOf(dispCtgr2No)) ) {
            isMinusOptLimit = "Y";
        }
*/
        return isMinusOptLimit;
    }

    public StringBuilder setCheckDiffOptionInfo(ProductStockVO prevStockVO, ProductVO productVO, ProductStockVO stockVO) throws OptionException {
        StringBuilder retVal = new StringBuilder();
        retVal.append(prevStockVO.getOptValueNo1()).append(OptionConstDef.OPTION_DELIMITER).append(prevStockVO.getOptValueNo2()).append(OptionConstDef.OPTION_DELIMITER)
              .append(prevStockVO.getOptValueNo3()).append(OptionConstDef.OPTION_DELIMITER).append(prevStockVO.getOptValueNo4()).append(OptionConstDef.OPTION_DELIMITER)
              .append(prevStockVO.getOptValueNo5()).append(OptionConstDef.OPTION_DELIMITER).append(prevStockVO.getOptValueNo6()).append(OptionConstDef.OPTION_DELIMITER)
              .append(prevStockVO.getOptValueNo7()).append(OptionConstDef.OPTION_DELIMITER).append(String.valueOf(prevStockVO.getAddPrc())).append(OptionConstDef.OPTION_DELIMITER);
        if(ProductConstDef.SetTypCd.BUNDLE.equals(productVO.getBaseVO().getSetTypCd())){
            if(stockVO != null) {
                retVal.append(stockVO.getCstmOptAplPrc()).append(OptionConstDef.OPTION_DELIMITER).append(prevStockVO.getBarCode()).append(OptionConstDef.OPTION_DELIMITER);
            } else {
                retVal.append(prevStockVO.getCstmOptAplPrc()).append(OptionConstDef.OPTION_DELIMITER).append(prevStockVO.getBarCode()).append(OptionConstDef.OPTION_DELIMITER);
            }
        }else{
            retVal.append(prevStockVO.getStckQty()).append(OptionConstDef.OPTION_DELIMITER).append(prevStockVO.getCstmOptAplPrc()).append(OptionConstDef.OPTION_DELIMITER)
                  .append(OptionConstDef.OPTION_DELIMITER).append(prevStockVO.getRealStckQty()).append(OptionConstDef.OPTION_DELIMITER)
                  .append(prevStockVO.getResvStckQty()).append(OptionConstDef.OPTION_DELIMITER).append(prevStockVO.getBarCode()).append(OptionConstDef.OPTION_DELIMITER)
                  .append(prevStockVO.getTempStckQty()).append(OptionConstDef.OPTION_DELIMITER).append(prevStockVO.getDfctStckQty()).append(OptionConstDef.OPTION_DELIMITER);
        }

        retVal.append(prevStockVO.getOptWght()).append(OptionConstDef.OPTION_DELIMITER).append(prevStockVO.getRegRnk()).append(OptionConstDef.OPTION_DELIMITER)
              .append(prevStockVO.getCtlgNo()).append(OptionConstDef.OPTION_DELIMITER)
              .append(prevStockVO.getAddDesc()).append(OptionConstDef.OPTION_DELIMITER)
              .append(prevStockVO.getSpplBnftCd()).append(OptionConstDef.OPTION_DELIMITER)
              .append(prevStockVO.getSellerStockCd()).append(OptionConstDef.OPTION_DELIMITER)
              .append(prevStockVO.getRntlCst()).append(OptionConstDef.OPTION_DELIMITER)
        ;
        if(productValidate.isPurchaseType(productVO.getBaseVO().getBsnDealClf())){
            retVal.append(prevStockVO.getPuchPrc()).append(OptionConstDef.OPTION_DELIMITER)
                  .append(prevStockVO.getMrgnRt()).append(OptionConstDef.OPTION_DELIMITER)
                  .append(prevStockVO.getMrgnAmt()).append(OptionConstDef.OPTION_DELIMITER)
                  .append(prevStockVO.getAprvStatCd()).append(OptionConstDef.OPTION_DELIMITER);
        }
        return retVal;
    }

    public void setStockBundleInfo(List<ProductStockSetInfoVO> preStockSetInfo, List<ProductStockSetInfoVO> stockSetInfo, List<ProductStockSetInfoVO> workedSetInfo) {
        for(ProductStockSetInfoVO preSetInfo : preStockSetInfo){
            preSetInfo.setChgTypeCd(OptionConstDef.HIST_DELETE);
            for(ProductStockSetInfoVO setInfo : stockSetInfo){
                if(setInfo.getMstStckNo() == preSetInfo.getMstStckNo() && setInfo.getCompStckNo() == preSetInfo.getCompStckNo()){
                    preSetInfo.setChgTypeCd("");

                    if(setInfo.getSetUnitQty() != preSetInfo.getSetUnitQty()
                        || setInfo.getSetDscRt() != preSetInfo.getSetDscRt()){
                        preSetInfo.setChgTypeCd(OptionConstDef.HIST_UPDATE);
                    }

                    break;
                }
            }

            if(StringUtil.isNotEmpty(preSetInfo.getChgTypeCd())) workedSetInfo.add(preSetInfo);
        }
    }

    /**
     * 마이너스 옵션 제한 카테고리 여부 조회
     * @param productVO
     * @return ProductBO
     * @throws OptionException
     */
    public ProductVO isMinusOptLimit(ProductVO productVO) throws OptionException {
        long dispCtgr1No = Long.parseLong(StringUtil.nvl(productVO.getBaseVO().getDispCtgr1NoDe(), "0"));	// 대카
        long dispCtgr2No = Long.parseLong(StringUtil.nvl(productVO.getBaseVO().getDispCtgr2NoDe(), "0"));	// 중카

        // 마이너스 옵션 제한 카테고리 여부 조회
        String isMinusOptLimit = isMinusOptLimit(dispCtgr1No, dispCtgr2No);
        productVO.setMinusOptLimitYn(isMinusOptLimit);

        return productVO;
    }

    /**
     * 하위 옵션 개수 체크
     * @param resProductStockList
     * @param optValueSubList
     * @param combOptValueCnt
     * @param resErrList
     * @throws OptionException
     */
    public void checkOptValueSubCnt(List<ProductStockVO> resProductStockList, List<ProductStockVO> optValueSubList, long combOptValueCnt, List<String> resErrList) throws OptionException {
        if( StringUtil.isNotEmpty(optValueSubList) ) {
            List<String> dupChkList = new ArrayList<String>();

            StringBuffer chkBuf = new StringBuffer();
            int listSize = optValueSubList.size();
            ProductStockVO prdStockBO = null;
            for(int i = 0 ; i < listSize ; i++) {
                prdStockBO = optValueSubList.get(i);

                if( prdStockBO.getOptValueCnt1() > combOptValueCnt ) {
                    setOptValueSubCntMsg(prdStockBO, resProductStockList, combOptValueCnt, chkBuf, dupChkList, 1, resErrList);
                }

                if( prdStockBO.getOptValueCnt2() > combOptValueCnt ) {
                    setOptValueSubCntMsg(prdStockBO, resProductStockList, combOptValueCnt, chkBuf, dupChkList, 2, resErrList);
                }

                if( prdStockBO.getOptValueCnt3() > combOptValueCnt ) {
                    setOptValueSubCntMsg(prdStockBO, resProductStockList, combOptValueCnt, chkBuf, dupChkList, 3, resErrList);
                }

                if( prdStockBO.getOptValueCnt4() > combOptValueCnt ) {
                    setOptValueSubCntMsg(prdStockBO, resProductStockList, combOptValueCnt, chkBuf, dupChkList, 4, resErrList);
                }
            }

            String checkMsg = chkBuf.toString();
            if( StringUtil.isNotEmpty(checkMsg) && resErrList == null ) {
                throw new OptionException(checkMsg);
            }
        }
    }

    /**
     * 하위 옵션 개수 초과 시 메세지 설정.
     * @param prdStockBO
     * @param resProductStockList
     * @param combOptValueCnt
     * @param chkBuf
     * @param dupChkList
     * @param idx
     * @param resErrList
     * @throws OptionException
     */
    private static void setOptValueSubCntMsg(ProductStockVO prdStockBO, List<ProductStockVO> resProductStockList, long combOptValueCnt, StringBuffer chkBuf, List<String> dupChkList, int idx, List<String> resErrList) throws OptionException {
        String dupChkKey = "";

        if( idx == 1 ) {
            dupChkKey = "[" + prdStockBO.getOptValueNm1() + "]";
        } else if( idx == 2 ) {
            dupChkKey = "[" + prdStockBO.getOptValueNm1() + "] + [" + prdStockBO.getOptValueNm2() + "]";
        } else if( idx == 3 ) {
            dupChkKey = "[" + prdStockBO.getOptValueNm1() + "] + [" + prdStockBO.getOptValueNm2() + "]" + " + [" + prdStockBO.getOptValueNm3() + "]";
        } else if( idx == 4 ) {
            dupChkKey = "[" + prdStockBO.getOptValueNm1() + "] + [" + prdStockBO.getOptValueNm2() + "]" + " + [" + prdStockBO.getOptValueNm3() + "]" + " + [" + prdStockBO.getOptValueNm4() + "]";
        }

        if( !dupChkList.contains(dupChkKey) ) {
            dupChkList.add(dupChkKey);

            if( StringUtil.isNotEmpty(chkBuf.toString()) && resErrList == null ) {
                chkBuf.append(" ");
            }

            if( resErrList != null ) {
                chkBuf.setLength(0);
            }

            chkBuf.append(dupChkKey).append("에 조합된 ").append(resProductStockList.get(idx).getOptItemNm1()).append("의 개수가 ").append(combOptValueCnt);
            chkBuf.append("개 초과되었습니다. 현재 상태로 옵션 수정이 불가능 하니, 조합되는 옵션값을 ").append(combOptValueCnt).append("개 이하로 등록해주세요.");

            if( resErrList != null ) {
                resErrList.add(chkBuf.toString());
            }
        }
    }

    public boolean[] checkRestrictOption(Map<String, PdOptItemVO> optItemMap, ProductVO productVO, PdOptItemVO pdOptItemVO, ProductOptLimitVO resProductOptLimitVO, List<ProductStockVO> optItemList, List<ProductStockVO> stockVOList, String _type, int custOptCnt, int selOptCnt, boolean isIdepOpt, boolean isCustOpt, boolean checkIsIdepOpt) throws OptionException {
        StringBuilder chkBuf = new StringBuilder();
        StringBuilder overOptItemNm = new StringBuilder();
        int checkFirstCnt = 0;
        long totalRegOptSize = 0;
        int maxOptItemNmByte = 40;
        int maxOptItemValueByte = 50;

        boolean checkIsCustOpt = false;
        boolean isOverOptByte = false;

        //예외셀러 확인 처리
        if(StringUtil.nvl(propMgr.get1hourTimeProperty(CacheKeyConstDef.OPT_ITEM_VALUE_EX_SELLER),"").indexOf("|" + productVO.getSelMnbdNo() + "|") > -1) {
            maxOptItemValueByte = 80;
        }

        //옵션 개수 정책 정보를 체크한다.
        if(optItemMap != null) {
            int optItemCnt = 0;
            int overCnt = 0;
            Iterator<Map.Entry<String, PdOptItemVO>> item = optItemMap.entrySet().iterator();

            while(item.hasNext()) {
                optItemCnt++;
                Map.Entry<String, PdOptItemVO> entry = item.next();
                pdOptItemVO = entry.getValue();

                if(OptionConstDef.OPT_CLF_CD_CUST.equals(pdOptItemVO.getOptClfCd())){
                    custOptCnt++;
                }

                if(checkFirstCnt == 0) {
                    if (OptionConstDef.OPT_CLF_CD_IDEP.equals(pdOptItemVO.getOptClfCd())){
                        isIdepOpt = true;
                        checkIsCustOpt = true;
                    } else if(OptionConstDef.OPT_CLF_CD_CUST.equals(pdOptItemVO.getOptClfCd())){
                        isCustOpt = true;
                        checkIsIdepOpt = true;
                    }

                    //옵션정책 제한 시작
                    checkRestrictFirstOption(resProductOptLimitVO, pdOptItemVO, stockVOList, selOptCnt);
                }

                if(resProductOptLimitVO != null && pdOptItemVO.getPdOptValueVOList() != null && pdOptItemVO.getPdOptValueVOList().size() > 0) {
                    if (OptionConstDef.OPT_CLF_CD_MIXED.equals(pdOptItemVO.getOptClfCd())){
                        if( optItemCnt == 1 ) {
                            totalRegOptSize = resProductOptLimitVO.getCombOptValCntNo1();
                        } else if( optItemCnt == 2 ) {
                            totalRegOptSize = resProductOptLimitVO.getCombOptValCntNo2();
                        } else if( optItemCnt == 3 ) {
                            totalRegOptSize = resProductOptLimitVO.getCombOptValCntNo3();
                        } else if( optItemCnt == 4 ) {
                            totalRegOptSize = resProductOptLimitVO.getCombOptValCntNo4();
                        } else if( optItemCnt == 5 ) {
                            totalRegOptSize = resProductOptLimitVO.getCombOptValCntNo5();
                        }

                        if(pdOptItemVO.getPdOptValueVOList().size() > totalRegOptSize) {
                            if( overCnt > 0 ) {
                                chkBuf.append(" ");
                            }

                            // Ex. [ 옵션명 ] 의 옵션값은 100개까지 사용 가능합니다. 현재 상태로 옵션 수정이 불가능 하니, 옵션값을 100개 이하로 등록해주세요.
                            chkBuf.append("[ ").append(pdOptItemVO.getOptItemNm()).append(" ] 의 옵션값은 ").append(totalRegOptSize).append("개까지 사용 가능합니다. 현재 상태로 옵션 수정이 불가능 하니, 옵션값을 ");
                            chkBuf.append(totalRegOptSize).append("개 이하로 등록해주세요.");
                            overCnt++;
                        }
                    } else if(checkIsIdepOpt && pdOptItemVO.getPdOptValueVOList().size() > resProductOptLimitVO.getIdepOptValueCnt()) {
                        overOptItemNm.append(" [ "+pdOptItemVO.getOptItemNm()+" ] ");
                        totalRegOptSize = resProductOptLimitVO.getIdepOptValueCnt();
                    }

                    //옵션명 // 옵션값 초과값 없는지 확인 (기본 : 40 // 50) : 예외 : properties값 (마트상품은 제외해야 한다.)
                    if(!isOverOptByte && pdOptItemVO.getOptItemNm().getBytes().length > maxOptItemNmByte) {
                        isOverOptByte = true;
                        pdOptItemVO.setOptItemNm(pdOptItemVO.getOptItemNm().substring(0, maxOptItemNmByte));
                    }

                    ProductStockVO optItemStockBO = new ProductStockVO();
                    optItemStockBO.setOptItemNm1(pdOptItemVO.getOptItemNm());
                    optItemList.add(optItemStockBO);

                    for(PdOptValueVO pdOptValueVO : pdOptItemVO.getPdOptValueVOList()) {
                        if(pdOptValueVO.getOptValueNm().getBytes().length > maxOptItemValueByte) {
                            isOverOptByte = true;
                            pdOptValueVO.setOptValueNm(pdOptValueVO.getOptValueNm().substring(0, maxOptItemValueByte));
                        }
                    }
                }
                checkFirstCnt++;
            }
        }
        //옵션정책 제한 시작
        checkRestrictLastOption(overOptItemNm, chkBuf, totalRegOptSize, resProductOptLimitVO, checkIsCustOpt, checkIsIdepOpt, custOptCnt, productVO, _type, optItemMap);

        boolean setIdepMixData[] = new boolean[3];
        setIdepMixData[0] = isIdepOpt;
        setIdepMixData[1] = checkIsIdepOpt;
        setIdepMixData[2] = isCustOpt;
        return setIdepMixData;
    }

    /**
     * 셀러재고번호 변경 여부
     * @return
     */
    public boolean isExtChgType(String extChgType){
        return Arrays.asList(OptionConstDef.HIST_DELETE, OptionConstDef.HIST_UPDATE, OptionConstDef.HIST_INSERT).contains(extChgType);
    }

    /**
     * 옵션개수 제한 정책 체크
     * @param productVO
     */
    public void checkOptLimit(ProductVO productVO, boolean isDeal) throws OptionException {

        String overOptValueErrMsg = "";

        ProductOptLimitVO productOptLimitBO = new ProductOptLimitVO();
        productOptLimitBO.setPrdNo(productVO.getBaseVO().getReRegPrdNo());
        productOptLimitBO.setDispCtgrNo(productVO.getDispCtgrNo());
        productOptLimitBO.setSellerNo(productVO.getSelMnbdNo());
        productOptLimitBO.setOptLmtObjNo(productVO.getDispCtgrNo());
        //PO, 쇼킹딜 상품일 경우
        if(!ProductConstDef.BSN_DEAL_CLF_COMMISSION.equals(productVO.getBaseVO().getBsnDealClf())){
            //PO 일 경우
            productOptLimitBO.setOptLmtType("PO");
        } else if(productVO.getPriceVO().getEventNo() > 0 || isDeal) {
            //쇼킹딜 일 경우
            productOptLimitBO.setPrdNo(productVO.getPrdNo());
            productOptLimitBO.setOptLmtType("SHOCKDEAL");
        }
        ProductOptLimitVO resProductOptLimitBO = optionServiceImpl.getProductOptLimitForSellerSet(productOptLimitBO);

        boolean isOptLimitValueCheck = false;
        boolean isIdepOpt = false;
        int selOptCnt = 0;
        int writeOptCnt = 0;
        if(productVO.getPriceVO().getEventNo() > 0) {
            //조합형 옵션 개수
            selOptCnt = optionServiceImpl.getOptionClfTypeCntForDeal(productVO.getPriceVO().getEventNo(), OptionConstDef.OPT_CLF_CD_MIXED);

            //독립형 옵션 개수
            if(selOptCnt <= 0) {
                selOptCnt = optionServiceImpl.getOptionClfTypeCntForDeal(productVO.getPriceVO().getEventNo(), OptionConstDef.OPT_CLF_CD_IDEP);
                isIdepOpt = true;
            }

            //작성형 옵션 개수
            writeOptCnt = optionServiceImpl.getOptionClfTypeCntForDeal(productVO.getPriceVO().getEventNo(), OptionConstDef.OPT_CLF_CD_CUST);
        } else {
            //조합형 옵션 개수
            selOptCnt = optionServiceImpl.getOptionClfTypeCnt(productVO.getBaseVO().getReRegPrdNo(), OptionConstDef.OPT_CLF_CD_MIXED);

            //독립형 옵션 개수
            if(selOptCnt <= 0) {
                selOptCnt = optionServiceImpl.getOptionClfTypeCnt(productVO.getBaseVO().getReRegPrdNo(), OptionConstDef.OPT_CLF_CD_IDEP);
                isIdepOpt = true;
            }

            //작성형 옵션 개수
            writeOptCnt = optionServiceImpl.getOptionClfTypeCnt(productVO.getBaseVO().getReRegPrdNo(), OptionConstDef.OPT_CLF_CD_CUST);
        }

        //각 옵션명 validate 체크
        if(resProductOptLimitBO != null && !isIdepOpt && selOptCnt > 0) {
            if(selOptCnt > resProductOptLimitBO.getCombOptNmCnt()) {
                //옵션정책 제한 시작
                throw new OptionException("조합형 옵션명은 "+resProductOptLimitBO.getCombOptNmCnt()+"개까지 사용 가능합니다. 현재 상태로 옵션 수정이 불가능 하니, 조합형 옵션명을 "+resProductOptLimitBO.getCombOptNmCnt()+"개 이하로 등록해주세요.");
            }

            //조합형 옵션의 총개수를 가져온다.
            HashMap param = new HashMap();
            param.put("viewRownum", "Y");

            int selOptTotalSize = 0;
            if(productVO.getPriceVO().getEventNo() > 0) {
                param.put("eventNo", productVO.getPriceVO().getEventNo());
                selOptTotalSize = optionServiceImpl.getPdStockExistCntForDeal(param);
            } else {
                param.put("prdNo", productVO.getBaseVO().getReRegPrdNo());
                selOptTotalSize = optionServiceImpl.getPdStockExistCnt(param);
            }

            if(selOptTotalSize > resProductOptLimitBO.getCombOptCnt()) {
                //옵션정책 제한 시작
                overOptValueErrMsg = "조합 후 옵션값은 "+resProductOptLimitBO.getCombOptCnt()+"개까지 사용 가능합니다. 현재 상태로 옵션 수정이 불가능 하니, 조합 후 옵션값을 "+resProductOptLimitBO.getCombOptCnt()+"개 이하로 등록해주세요.";
            }
            isOptLimitValueCheck = true;
        } else if(resProductOptLimitBO != null && isIdepOpt && selOptCnt > 0) {
            if(selOptCnt > resProductOptLimitBO.getIdepOptNmCnt()) {
                //옵션정책 제한 시작
                throw new OptionException("독립형 옵션명은 "+resProductOptLimitBO.getIdepOptNmCnt()+"개까지 사용 가능합니다. 현재 상태로 옵션 수정이 불가능 하니, 독립형 옵션명을 "+resProductOptLimitBO.getIdepOptNmCnt()+"개 이하로 등록해주세요.");
            }
            isOptLimitValueCheck = true;
        }

        if(resProductOptLimitBO != null && writeOptCnt > 0 && writeOptCnt > resProductOptLimitBO.getWriteOptNmCnt()) {
            //옵션정책 제한 시작
            throw new OptionException("작성형 옵션명은 "+resProductOptLimitBO.getWriteOptNmCnt()+"개까지 사용 가능합니다. 현재 상태로 옵션 수정이 불가능 하니, 작성형 옵션명을 "+resProductOptLimitBO.getWriteOptNmCnt()+"개 이하로 등록해주세요.");
        }

        if(!"".equals(overOptValueErrMsg)) {
            //옵션정책 제한 시작
            throw new OptionException(overOptValueErrMsg);
        }

        long maxOptLimitSize = 0;
        // 조합형 옵션명의 초과된 옵션값 개수를 가져온다.
        if(isOptLimitValueCheck) {
            ProductStockVO productStockBO = new ProductStockVO();
            productStockBO.setPrdNo(productVO.getBaseVO().getReRegPrdNo());

            List<ProductStockVO> resProductStockList = new ArrayList<ProductStockVO>();
            if(productVO.getPriceVO().getEventNo() > 0) {
                productStockBO.setEventNo(productVO.getPriceVO().getEventNo());
                resProductStockList = optionServiceImpl.getOptionValueGroupInfoForDeal(productStockBO);
            } else {
                resProductStockList = optionServiceImpl.getOptionValueGroupInfo(productStockBO);
            }

            if(isIdepOpt) {
                maxOptLimitSize = resProductOptLimitBO.getIdepOptValueCnt();
            }

            int optItemSize = 0;
            StringBuffer chkBuf = new StringBuffer();
            int index = 0;
            int overCnt = 0;

            if(StringUtil.isNotEmpty(resProductStockList)) {
                optItemSize = resProductStockList.size();

                ProductStockVO resProductStockBO = null;
                for(index=0; index<optItemSize; index++) {
                    resProductStockBO = resProductStockList.get(index);

                    // 조합형일 경우 옵션 단 별 제한 개수를 체크.
                    if(!isIdepOpt) {
                        if( index == 0 ) {
                            maxOptLimitSize = resProductOptLimitBO.getCombOptValCntNo1();
                        } else if( index == 1 ) {
                            maxOptLimitSize = resProductOptLimitBO.getCombOptValCntNo2();
                        } else if( index == 2 ) {
                            maxOptLimitSize = resProductOptLimitBO.getCombOptValCntNo3();
                        } else if( index == 3 ) {
                            maxOptLimitSize = resProductOptLimitBO.getCombOptValCntNo4();
                        } else if( index == 4 ) {
                            maxOptLimitSize = resProductOptLimitBO.getCombOptValCntNo5();
                        }
                    }

                    if(resProductStockBO.getTotalCount() > maxOptLimitSize) {
                        if( overCnt > 0 ) {
                            chkBuf.append(" ");
                        }

                        // Ex. [ 옵션명 ] 의 옵션값은 100개까지 사용 가능합니다. 현재 상태로 옵션 수정이 불가능 하니, 옵션값을 100개 이하로 등록해주세요.
                        chkBuf.append("[ ").append(resProductStockBO.getOptItemNm1()).append(" ] 의 옵션값은 ").append(maxOptLimitSize).append("개까지 사용 가능합니다. 현재 상태로 옵션 수정이 불가능 하니, 옵션값을 ");
                        chkBuf.append(maxOptLimitSize).append("개 이하로 등록해주세요.");
                        overCnt++;
                    }
                }

                String overOptItemNm = chkBuf.toString();
                if(!"".equals(overOptItemNm)) {
                    //옵션정책 제한 시작
                    throw new OptionException(overOptItemNm);
                }
            }

            // 조합형 옵션이고 2단 이상 옵션등록 시 옵션 값별 제한 체크.
            if( !isIdepOpt && optItemSize > 1 ) {
                List<ProductStockVO> optValueSubList = null;

                ProductStockVO prdStockBO = new ProductStockVO();
                prdStockBO.setPrdNo(productVO.getPrdNo());
                prdStockBO.setOptItemCnt(optItemSize);

                if(productVO.getPriceVO().getEventNo() > 0) {
                    prdStockBO.setEventNo(productVO.getPriceVO().getEventNo());

                    optValueSubList = optionServiceImpl.getProductOptValueSubCntListForDeal(prdStockBO);
                } else {
                    optValueSubList = optionServiceImpl.getProductOptValueSubCntList(prdStockBO);
                }

                checkOptValueSubCnt(resProductStockList, optValueSubList, resProductOptLimitBO.getCombOptValueCnt(), null);
            }
        }
    }

    /** 정기결제 설정 불가능한 옵션 유형 */
    public List<String> IMPOSSIBLE_OPT_TYP_CD = Arrays.asList(OptionConstDef.OPT_TYP_CD_DAY, OptionConstDef.OPT_TYP_CD_CALC);

    /**옵션 중복 등록 체크*/
    public void checkOptionDuplicate(ProductVO productVO) {

        if(productVO == null || productVO.getPrdNo() <= 0) {
            return;
        }

        if(optionMapper.getOptStockValidCheck(productVO.getPrdNo()) > 0) {
            throw new OptionException("옵션등록 오류 : 동일코드 옵션이 2개 이상 존재합니다.");
        }

        return;
    }

    /**
     * 예상배송비 제약조건을 확인한다.
     * @param itgMemNo
     * @param prdWght
     * @param prdCstmAplPrc
     * @param dispCtgrNo
     */
    public void checkOptionGlobalItgDeliveryCost(long itgMemNo, long prdWght, double prdCstmAplPrc, long dispCtgrNo) {
        if(itgMemNo <= 0) throw new OptionException("통합출고지를 선택되지 않았습니다. 통합출고지를 선택 하세요.");
        if(prdWght <= 0) throw new OptionException("상품에 무게가 입력되지 않았습니다. 상품무게를 입력 하세요.");
        if(prdCstmAplPrc <= 0) throw new OptionException("상품에 신고가가 입력되지 않았습니다. 신고가를 입력 하세요.");
        if(dispCtgrNo <= 0) throw new OptionException("상품의 카테고리가 선택되지 않았습니다. 카테고리를 선택 하세요.");
    }

    /**
     * 옵션가 예외 셀러 조회. (등록 가능한 % 리턴)
     * 0일 경우 예외 셀러 아님.
     * @param selMnbdNo
     * @return int
     */
    public int getSellerOptAddPrcLimit(long selMnbdNo) {
        int addPrcLimit = 0;

        //TODO: ESO
        /*
        try {
            String cacheKey = "OPT_ADD_PRC_LIMIT_SELLER_" + selMnbdNo;

            ProductCacheUtil cache = new ProductCacheUtil();
            String limit = (String)cache.getEsoCacheData(cacheKey);

            if( StringUtil.isNotEmpty(limit) ) {
                addPrcLimit = StringUtil.parseInt(limit);
            } else {
                SellerAuthVO sellerAuthVO = new SellerAuthVO();
                sellerAuthVO.setSelMnbdNo(selMnbdNo);
                sellerAuthVO.setAuthTypCd(ProductConstDef.AUTH_TYP_CD_OPT_ADD_PRC);
                sellerAuthVO.setObjClfCd(ProductConstDef.OBJ_CLF_CD_OPT_ADD_PRC);
                sellerAuthVO.setMode("AUTH_OBJ_NO_NOT_CHK");
                sellerAuthVO.setUseYn("Y");

                sellerAuthVO = sellerServiceImpl.getSellerAuthInfo(sellerAuthVO);

                limit = "0";

                if( sellerAuthVO != null && StringUtil.isNotEmpty(sellerAuthVO.getAuthObjNo()) && NumberUtil.isNumber(sellerAuthVO.getAuthObjNo()) ) {
                    limit = sellerAuthVO.getAuthObjNo();
                    addPrcLimit = StringUtil.parseInt(limit);

                    // 100 단위로 입력했는지 체크
                    if( addPrcLimit % 100 != 0 ) {
                        limit = "0";
                        addPrcLimit = 0;
                    }
                }

                cache.setEsoCacheData(cacheKey, limit, ProductCacheUtil.CACHE_TIME_1H);
            }
        } catch(Exception e) {
            // Error Pass
            throw new ProductException(e);
        }
        */

        return addPrcLimit;
    }

    public String checkMsgSoDscSelPrc(ProductVO productVO) throws OptionException {
        String msg = "";
        if(productVO.getPriceVO().getBuylCnPrc() <= 0){
            return "상품["+productVO.getPrdNo()+"]의 기본즉시할인 적용 판매가는 0원보다 커야 합니다.";
        }

        // 카테고리별 판매가 옵션가의 체크
        boolean isDealRangeCheck = false;
        if(!"Y".equals(productVO.getPrdCopyYn())){
            if(dealService.getShockingDealPrd(productVO.getPrdNo()) != null){	// 쇼킹딜로 판매중
                isDealRangeCheck = ProductConstDef.BSN_DEAL_CLF_COMMISSION.equals(productVO.getBaseVO().getBsnDealClf());
            }else if(productVO.getPriceVO().getEventNo() > 0){
                isDealRangeCheck = true;
            }
        }
        //String tmpValue = productVO.getIsShockDealPrdYn();
        if(productVO.getProductEvtVO() == null) productVO.setProductEvtVO(new ProductEvtVO());
        productVO.getProductEvtVO().setShockDealPrdYn(isDealRangeCheck ? "Y" : "");

        /*
        if( dataBox != null && productVO.getSelMnbdNo() <= 0 ) {
            productVO.setSelMnbdNo(dataBox.getLong("selMnbdNo"));
        }
        */

        try{
            checkOptionAddPrcRange(productVO);
        }catch(OptionException e){
            msg = e.getMessage();
        }
        //productVO.setIsShockDealPrdYn(tmpValue);

        // 옵션가가 1원 이상인 것이 있는지 조회
        if(productVO.getPriceVO().getSelPrc() <= 1 && StringUtil.isNotEmpty(msg) && optionServiceImpl.getPdStockAddPrcCnt1Over(productVO) > 0){
            msg = "상품 판매가가 1원인 경우 옵션가격은 0원을 초과할 수 없습니다.";
        }

        return msg;
    }

    /**
     * 해당 카테고리의 옵션 추가금 범위를 체크한다.
     * - prdNo가 0인경우는 스킵(실제 db의 데이터를 조회함으로 의미 없음)
     * - 실제 DB의 옵션 데이터로 체크함
     * - Param의 데이터에 prdNo, dispCtgr1NoDe, dispCtgr2NoDe, selPrc, buylCnPrc는 필수
     * - 쇼킹딜 범위로 체크를 해야 할 경우 isShockDealPrdYn 을 Y로 세팅한다
     * - 쇼킹딜 상품정보의 옵션정보(pd_event_rqst_stock)를 체크해야 할 경우 eventNo를 세팅한다.
     * @param productVO
     * @throws OptionException
     */
    public void checkOptionAddPrcRange(ProductVO productVO) throws OptionException{
        try{
            //if(productVO.getPrdNo() <= 0) return;
            String bsnDealClf = productVO.getBaseVO().getBsnDealClf();
            long selMnbdNo = productVO.getSelMnbdNo();

            // 거래 유형,판매자 번호가 없을 경우에만 DB 조회
            if( StringUtil.isEmpty(bsnDealClf) || selMnbdNo <= 0 ) {
                ProductVO dbProduct = productService.getProductMember(productVO.getPrdNo());
                bsnDealClf = dbProduct.getBaseVO().getBsnDealClf();
                selMnbdNo = dbProduct.getSelMnbdNo();
            }

            String[] ctgrOptPrcArr = null;
            if(ProductConstDef.BSN_DEAL_CLF_COMMISSION.equals(bsnDealClf)){
                ctgrOptPrcArr = this.checkCtgrOptPrcData(productVO.getCategoryVO().getRootCtgrNo(), productVO.getCategoryVO().getMctgrNo(), "Y".equals(productVO.getProductEvtVO().getShockDealPrdYn()));
            }else{
                ctgrOptPrcArr = new String[]{"20", "2000"};
            }

            productVO.setRtnType(Long.parseLong(ctgrOptPrcArr[0]));

            // 옵션가 예외 여부 조회
            int optAddPrcLimit = this.getSellerOptAddPrcLimit(selMnbdNo);
            productVO.getOptionVO().setOptAddPrcLimit(optAddPrcLimit);

            if(optionServiceImpl.getPdStockAddPrcCnt(productVO) > 0){
                if( optAddPrcLimit <= 0 ) {
                    if(productVO.getPriceVO().getSelPrc() < 10000L) {
                        if(productVO.getPriceVO().getSelPrc() < 2000){
                            throw new OptionException("옵션가격이 1만원 보다 크거나 기본즉시할인이 적용된 판매가의 0원 보다 작습니다.");
                        }else{
                            throw new OptionException("기본즉시할인이 적용된 판매가와 옵션가의 합이 0보다 작거나, 옵션가격이 1만원 보다 크거나 판매가의 -50%보다 작습니다.");
                        }
                    } else if(productVO.getPriceVO().getSelPrc() >= 10000) {
                        throw new OptionException("기본즉시할인이 적용된 판매가와 옵션가의 합이 0보다 작거나, 옵션가격이 판매가의 +"+ctgrOptPrcArr[1]+"보다 크거나 -50%보다 작습니다.");
                    }
                } else {
                    throw new OptionException("기본즉시할인이 적용된 판매가와 옵션가의 합이 0보다 작거나, 옵션가격이 판매가의 +"+optAddPrcLimit+"%보다 크거나 -99%보다 작습니다.");
                }
            }
        }catch(OptionException e){
            throw e;
        }catch(Exception e){
            throw new OptionException("카테고리별 옵션 추가금 범위 체크 오류.", e);
        }
    }

    //public String checkMsgSoDscSelPrcForReg(long prdNo, long selPrc, long soDscAmt, long dispCtgr1No, long dispCtgr2No, String bsnDealClf) throws OptionException {
    public String checkMsgSoDscSelPrcForReg(ProductVO productVO) throws OptionException {

        /*
        ProductVO checkBO = new ProductVO();
        checkBO.setBaseVO(new BaseVO());
        checkBO.setPriceVO(new PriceVO());
        checkBO.setCategoryVO(new CategoryVO());
        checkBO.setOptionVO(new OptionVO());
        checkBO.setProductEvtVO(new ProductEvtVO());

        checkBO.setPrdNo(prdNo);
        checkBO.getPriceVO().setSelPrc(selPrc);
        checkBO.getPriceVO().setBuylCnPrc(selPrc - soDscAmt);
        checkBO.getCategoryVO().setRootCtgrNo(dispCtgr1No);
        checkBO.getCategoryVO().setMctgrNo(dispCtgr2No);
        checkBO.getBaseVO().setBsnDealClf(bsnDealClf);
        */

        productVO.getPriceVO().setBuylCnPrc(productVO.getPriceVO().getSelPrc() - productVO.getPriceVO().getDscAmt());
        return this.checkMsgSoDscSelPrc(productVO);
    }

    /**
     * 카테고리 옵션가 조회하여 리턴 - codeValue1로 체크하지 않는 경우
     * @param dispCtgr1No
     * @param dispCtgr2No
     * @return String[]
     */
    public String[] checkCtgrOptPrcData(long dispCtgr1No, long dispCtgr2No) {
        return checkCtgrOptPrcData(dispCtgr1No, dispCtgr2No, false);
    }

    /**
     * 카테고리 옵션가 조회하여 리턴
     * @param dispCtgr1No
     * @param dispCtgr2No
     * @param isDealRangeCheck
     * @return String[]
     */
    public String[] checkCtgrOptPrcData(long dispCtgr1No, long dispCtgr2No, boolean isDealRangeCheck){
        String[] resultArr = {"1", "100%"};

        SyCoDetailVO codeDetailVO = new SyCoDetailVO();
        int overVal = 1;
        boolean isCtgr2Chk = false;

        if( dispCtgr2No != 0 ) {
            codeDetailVO.setDtlsCd(String.valueOf(dispCtgr2No));
            codeDetailVO.setGrpCd(ProductConstDef.CTGR_OPT_PRC_CD);
            codeDetailVO = commonService.getCodeDetailName(codeDetailVO);

            if( codeDetailVO != null && codeDetailVO.getDtlsCd() != null ) {
                if(isDealRangeCheck){
                    String codeValue1 = NumberUtil.isNumber(codeDetailVO.getCdVal1()) ? codeDetailVO.getCdVal1() : "100";
                    overVal = Integer.parseInt(codeValue1, 10);
                    resultArr[0] = String.valueOf(overVal / 100);
                    resultArr[1] = codeValue1 + "%";
                }else{
                    overVal = Integer.parseInt(codeDetailVO.getDtlsComNm(), 10);
                    resultArr[0] = String.valueOf(overVal / 100);
                    resultArr[1] = codeDetailVO.getDtlsComNm() + "%";
                }
                isCtgr2Chk = true;
            }
        }

        if( dispCtgr1No != 0 && !isCtgr2Chk ) {
            codeDetailVO = new SyCoDetailVO();
            codeDetailVO.setDtlsCd(String.valueOf(dispCtgr1No));
            codeDetailVO.setGrpCd(ProductConstDef.CTGR_OPT_PRC_CD);
            codeDetailVO = commonService.getCodeDetailName(codeDetailVO);

            if( codeDetailVO != null && codeDetailVO.getDtlsCd() != null ) {
                if(isDealRangeCheck){
                    String codeValue1 = StringUtil.isNumber(codeDetailVO.getCdVal1()) ? codeDetailVO.getCdVal1() : "100";
                    overVal = Integer.parseInt(codeValue1, 10);
                    resultArr[0] = String.valueOf(overVal / 100);
                    resultArr[1] = codeValue1 + "%";
                }else{
                    overVal = Integer.parseInt(codeDetailVO.getDtlsComNm(), 10);
                    resultArr[0] = String.valueOf(overVal / 100);
                    resultArr[1] = codeDetailVO.getDtlsComNm() + "%";
                }
            }
        }
        return resultArr;
    }

    public void checkPinNumberType(ProductVO productVO) throws OptionException {
        // PIN번호 상품이 아닐 경우
        if( !ProductConstDef.PRD_TYP_CD_TOWN_SALES.equals(productVO.getBaseVO().getPrdTypCd()) && !ProductConstDef.PRD_TYP_CD_TOWN_PROMOTE.equals(productVO.getBaseVO().getPrdTypCd()) ) {
            throw new OptionException("날짜형 옵션은 PIN번호 상품에만 등록 가능합니다.");
        }
    }

    public void checkRefundType(ProductVO productVO) throws OptionException {
        // 환불타입이 환불 불가가 아닐 경우
        if( !ProductConstDef.RFND_TYP_CD.NOT.getDtlsCd().equals(productVO.getBaseVO().getRfndTypCd()) ) {
            throw new OptionException("날짜형 옵션을 설정하시면 환불불가만 설정 가능합니다.");
        }
    }
}
