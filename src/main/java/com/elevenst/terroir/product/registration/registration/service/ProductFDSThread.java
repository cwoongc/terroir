package com.elevenst.terroir.product.registration.registration.service;

import com.elevenst.common.util.DateUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.common.vo.SyAppMngVO;
import com.elevenst.terroir.product.registration.benefit.vo.CustomerBenefitVO;
import com.elevenst.terroir.product.registration.cert.vo.ProductCertInfoVO;
import com.elevenst.terroir.product.registration.common.service.CommonServiceImpl;
import com.elevenst.terroir.product.registration.ctgrattr.vo.CtgrAttrCompVO;
import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.option.code.OptionConstDef;
import com.elevenst.terroir.product.registration.option.mapper.OptionMapper;
import com.elevenst.terroir.product.registration.option.vo.PdOptItemVO;
import com.elevenst.terroir.product.registration.option.vo.PdOptValueVO;
import com.elevenst.terroir.product.registration.option.vo.ProductStockVO;
import com.elevenst.terroir.product.registration.price.vo.PriceVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ProductFDSThread extends Thread {

    private ProductVO productVO;
    private ProductVO preProductVO;

    @Autowired
    OptionMapper optionMapper;


    public ProductFDSThread(ProductVO preProductVO, ProductVO productVO) {
        this.preProductVO = preProductVO;
        this.productVO = productVO;
    }


    public void run() {
        try {
            this.sendProductFDSData(preProductVO, productVO);
        } catch(Exception e) {
            log.error(e.getMessage(), e);
        }
    }



    public void sendProductFDSData(ProductVO preProductVO, ProductVO productVO) {
        try {
            String isOpenApi = productVO.getIsOpenApi();
            String updateYn = productVO.isUpdate() ? "Y" : "N";
            String bulkProductYn = productVO.getBulkProductYn();

            // API 에서는 전송 제외 (엑셀 등록 시 isOpenAPI 값이 Y로 넘어옴)
            if( "Y".equals(isOpenApi) && "N".equals(bulkProductYn) ) {
                return;
            }
            // 암호화 전문
            String ktbAgent = StringUtil.nvl("");//request.getParameter("ktb_agent"));

            CustomerBenefitVO benefitVO = productVO.getCustomerBenefitVO();
            PriceVO priceVO = productVO.getPriceVO();
            List<CtgrAttrCompVO> prdAttrList = productVO.getCtgrAttrCompVOList();
            List<ProductCertInfoVO> prdCertList = productVO.getProductCertInfoVOList();
            int i = 0;
            int listSize = 0;


            Map<String, Object> fdsDataMap = new HashMap<String, Object>();

            // 상품 기본 정보
            fdsDataMap.put("prdNo", productVO.getPrdNo());
            fdsDataMap.put("prdNm", productVO.getPrdNm());
            fdsDataMap.put("mnfcDy", productVO.getBaseVO().getMnfcDy());
            fdsDataMap.put("dispCtgrNo", productVO.getDispCtgrNo());
            fdsDataMap.put("orgnTypCd", productVO.getBaseVO().getOrgnTypCd());
            fdsDataMap.put("orgnTypDtlsCd", productVO.getBaseVO().getOrgnTypDtlsCd());
            fdsDataMap.put("orgnNm", productVO.getBaseVO().getOrgnNm());
            fdsDataMap.put("forAbrdBuyClf", productVO.getBaseVO().getForAbrdBuyClf());
            fdsDataMap.put("minorSelCnYn", productVO.getBaseVO().getMinorSelCnYn());
            fdsDataMap.put("selPrdClfCd", productVO.getBaseVO().getSelPrdClfCd());
            fdsDataMap.put("selStatCd", productVO.getBaseVO().getSelStatCd());
            fdsDataMap.put("updateYn", updateYn);
            fdsDataMap.put("regType", productVO.getChannel());
            fdsDataMap.put("advrtStmt", productVO.getBaseVO().getAdvrtStmt());

            // 가격 관련 설정 [s]
            if( priceVO != null ) {
                fdsDataMap.put("selPrc", priceVO.getSelPrc());
                fdsDataMap.put("maktPrc", priceVO.getMaktPrc());
            } else {
                fdsDataMap.put("selPrc", 0);
                fdsDataMap.put("maktPrc", 0);
            }
            // 가격 관련 설정 [e]


            // 혜택 관련 설정 [s]
            if( benefitVO != null ) {
                if( ProductConstDef.POINT_SPPL_WY_CD_WON.equals(benefitVO.getSpplWyCd()) && benefitVO.getMallPnt() > 0 ) {
                    fdsDataMap.put("pntInfo", benefitVO.getMallPnt() + "-" + benefitVO.getSpplWyCd());
                } else if( ProductConstDef.POINT_SPPL_WY_CD_PERCNT.equals(benefitVO.getSpplWyCd()) && benefitVO.getMallPntRt() > 0 ) {
                    fdsDataMap.put("pntInfo", benefitVO.getMallPntRt() + "-" + benefitVO.getSpplWyCd());
                } else {
                    fdsDataMap.put("pntInfo", "");
                }

                if( ProductConstDef.OCB_SPPL_WY_CD_WON.equals(benefitVO.getOcbWyCd()) && benefitVO.getOcbPnt() > 0 ) {
                    fdsDataMap.put("ocbInfo", benefitVO.getOcbPnt() + "-" + benefitVO.getOcbWyCd());
                } else if( ProductConstDef.OCB_SPPL_WY_CD_PERCNT.equals(benefitVO.getOcbWyCd()) && benefitVO.getOcbPntRt() > 0 ) {
                    fdsDataMap.put("ocbInfo", benefitVO.getOcbPntRt() + "-" + benefitVO.getOcbWyCd());
                } else {
                    fdsDataMap.put("ocbInfo", "");
                }

                if( ProductConstDef.MILEAGE_SPPL_WY_CD_WON.equals(benefitVO.getMileageWyCd()) && benefitVO.getMileagePnt() > 0 ) {
                    fdsDataMap.put("mileageInfo", benefitVO.getMileagePnt() + "-" + benefitVO.getMileageWyCd());
                } else if( ProductConstDef.MILEAGE_SPPL_WY_CD_PERCNT.equals(benefitVO.getMileageWyCd()) && benefitVO.getMileagePntRt() > 0 ) {
                    fdsDataMap.put("mileageInfo", benefitVO.getMileagePntRt() + "-" + benefitVO.getMileageWyCd());
                } else {
                    fdsDataMap.put("mileageInfo", "");
                }
            } else {
                fdsDataMap.put("pntInfo", "");
                fdsDataMap.put("ocbInfo", "");
                fdsDataMap.put("mileageInfo", "");
            }
            // 혜택 관련 설정 [e]


            // 속성 관련 설정 [s]
            fdsDataMap.put("brandNm", "");
            fdsDataMap.put("mnfcNm", "");
            fdsDataMap.put("modelNm", "");
            fdsDataMap.put("infoTypeCtgrNo", 0);
            fdsDataMap.put("attrList", null);
            if(prdAttrList != null && prdAttrList.size() > 0){
                List<Map<String, String>> attrList = new ArrayList<Map<String, String>>();
                Map<String, String> attrMap = null;

                CtgrAttrCompVO attrBO = null;
                listSize = prdAttrList.size();
                for(i = 0 ; i < listSize ; i++) {
                    attrBO = prdAttrList.get(i);

                    // 브랜드
                    if( ProductConstDef.ATTR_CD_BRAND.equals(attrBO.getPrdAttrCd()) ) {
                        fdsDataMap.put("brandNm", attrBO.getPrdAttrValueNm());
                    }
                    // 제조사
                    else if( ProductConstDef.ATTR_CD_COMPANY.equals(attrBO.getPrdAttrCd()) ) {
                        fdsDataMap.put("mnfcNm", attrBO.getPrdAttrValueNm());
                    }
                    // 모델명
                    else if( ProductConstDef.ATTR_CD_MODEL.equals(attrBO.getPrdAttrCd()) ) {
                        fdsDataMap.put("modelNm", attrBO.getPrdAttrValueNm());
                    }
                    // 그 외
                    else {
                        attrMap = new HashMap<String, String>();
                        attrMap.put("prdAttrCd", attrBO.getPrdAttrCd());
                        attrMap.put("prdAttrNm", attrBO.getPrdAttrNm());
                        attrMap.put("prdAttrValueNm", attrBO.getPrdAttrValueNm());
                        attrList.add(attrMap);
                    }
                }

                fdsDataMap.put("infoTypeCtgrNo", productVO.getBaseVO().getInfoTypeCtgrNo());
                fdsDataMap.put("attrList", attrList);
            }
            // 속성 관련 설정 [e]

            // 인증 정보 설정 [s]
            fdsDataMap.put("certList", null);
            if(prdCertList != null && prdCertList.size() > 0){
                List<Map<String, String>> certList = new ArrayList<Map<String, String>>();
                Map<String, String> certMap = null;

                ProductCertInfoVO certBO = null;
                listSize = prdCertList.size();
                for(i = 0 ; i < listSize ; i++) {
                    certBO = prdCertList.get(i);

                    certMap = new HashMap<String, String>();
                    certMap.put("certType", certBO.getCertType());
                    certMap.put("certKey", certBO.getCertKey());
                    certList.add(certMap);
                }

                fdsDataMap.put("certList", certList);
            }
            // 인증 정보 설정 [e]

            // 옵션 정보 설정 [s]
            fdsDataMap.put("optWriteList", null);
            fdsDataMap.put("optItemList", null);
            fdsDataMap.put("optValueList", null);
            fdsDataMap.put("optList", null);

            String optClfCd = null;

            PdOptItemVO optItemBO = new PdOptItemVO();
            optItemBO.setPrdNo(productVO.getPrdNo());
            List<PdOptItemVO> optItemList = optionMapper.getProductOptItemLst(optItemBO);
            if(optItemList != null && optItemList.size() > 0) {
                optClfCd = optItemList.get(0).getOptClfCd();

                // 첫번째 항목이 조합형, 독립형이 아닐 경우 옵션이 없는 상품임.
                if(!OptionConstDef.OPT_CLF_CD_MIXED.equals(optClfCd) && OptionConstDef.OPT_CLF_CD_IDEP.equals(optClfCd)) {
                    optClfCd = null;
                }

                List<Map<String, Object>> optWriteList = new ArrayList<Map<String, Object>>();
                Map<String, Object> tmpMap = null;

                List<Map<String, Object>> optItemMapList = new ArrayList<Map<String, Object>>();
                Map<String, Object> optItemMap = new HashMap<String, Object>();

                // 작성형 옵션
                for(i = 0, listSize = optItemList.size() ; i < listSize ; i++) {
                    optItemBO = optItemList.get(i);

                    if(OptionConstDef.OPT_CLF_CD_CUST.equals(optItemBO.getOptClfCd())) {
                        tmpMap = new HashMap<String, Object>();
                        tmpMap.put("optItemNo", optItemBO.getOptItemNo());
                        tmpMap.put("optItemNm", optItemBO.getOptItemNm());
                        tmpMap.put("statCd", optItemBO.getStatCd());
                        optWriteList.add(tmpMap);
                    } else {
                        if(OptionConstDef.OPT_CLF_CD_IDEP.equals(optItemBO.getOptClfCd())) {
                            tmpMap = new HashMap<String, Object>();

                            tmpMap.put("optItemNo", optItemBO.getOptItemNo());
                            tmpMap.put("optItemNm", optItemBO.getOptItemNm());

                            optItemMapList.add(tmpMap);
                        }

                        optItemMap.put(StringUtil.nvl(optItemBO.getOptItemNo()), optItemBO.getOptItemNm());
                    }
                }

                // 조합형, 독립형 옵션
                if(OptionConstDef.OPT_CLF_CD_MIXED.equals(optClfCd)) {
                    List<Map<String, Object>> optList = new ArrayList<Map<String, Object>>();

                    ProductStockVO stckBO = new ProductStockVO();
                    stckBO.setPrdNo(productVO.getPrdNo());

                    List<ProductStockVO> stckList = optionMapper.getPrdStckOptNmList(stckBO);

                    if(stckList != null && stckList.size() > 0) {
                        for(i = 0, listSize = stckList.size() ; i < listSize ; i++) {
                            stckBO = stckList.get(i);
                            tmpMap = new HashMap<String, Object>();

                            tmpMap.put("optItemNo1", stckBO.getOptItemNo1());
                            tmpMap.put("optItemNm1", optItemMap.get(StringUtil.nvl(stckBO.getOptItemNo1())));
                            tmpMap.put("optValueNo1", stckBO.getOptValueNo1());
                            tmpMap.put("optValueNm1", stckBO.getOptValueNm1());

                            tmpMap.put("optItemNo2", stckBO.getOptItemNo2());
                            tmpMap.put("optItemNm2", optItemMap.get(StringUtil.nvl(stckBO.getOptItemNo2())));
                            tmpMap.put("optValueNo2", stckBO.getOptValueNo2());
                            tmpMap.put("optValueNm2", stckBO.getOptValueNm2());

                            tmpMap.put("optItemNo3", stckBO.getOptItemNo3());
                            tmpMap.put("optItemNm3", optItemMap.get(StringUtil.nvl(stckBO.getOptItemNo3())));
                            tmpMap.put("optValueNo3", stckBO.getOptValueNo3());
                            tmpMap.put("optValueNm3", stckBO.getOptValueNm3());

                            tmpMap.put("optItemNo4", stckBO.getOptItemNo4());
                            tmpMap.put("optItemNm4", optItemMap.get(StringUtil.nvl(stckBO.getOptItemNo4())));
                            tmpMap.put("optValueNo4", stckBO.getOptValueNo4());
                            tmpMap.put("optValueNm4", stckBO.getOptValueNm4());

                            tmpMap.put("optItemNo5", stckBO.getOptItemNo5());
                            tmpMap.put("optItemNm5", optItemMap.get(StringUtil.nvl(stckBO.getOptItemNo5())));
                            tmpMap.put("optValueNo5", stckBO.getOptValueNo5());
                            tmpMap.put("optValueNm5", stckBO.getOptValueNm5());

                            tmpMap.put("stckQty", stckBO.getStckQty());
                            tmpMap.put("addPrc", stckBO.getAddPrc());
                            tmpMap.put("optWght", stckBO.getOptWght());
                            tmpMap.put("prdStckStatCd", stckBO.getPrdStckStatCd());

                            optList.add(tmpMap);
                        }
                    }

                    fdsDataMap.put("optList", optList);
                } else if(OptionConstDef.OPT_CLF_CD_IDEP.equals(optClfCd)) {
                    HashMap<String, String> paramMap = new HashMap<String, String>();
                    paramMap.put("prdNo", StringUtil.nvl(productVO.getPrdNo()));

                    List<PdOptValueVO> optValueList = optionMapper.getProductOptValueLstNew(paramMap);

                    List<Map<String, Object>> optValueMapList = new ArrayList<Map<String, Object>>();

                    if(optValueList != null && optValueList.size() > 0) {
                        PdOptValueVO optValueBO = null;
                        for(i = 0, listSize = optValueList.size() ; i < listSize ; i++) {
                            optValueBO = optValueList.get(i);
                            tmpMap = new HashMap<String, Object>();

                            tmpMap.put("optItemNo", optValueBO.getOptItemNo());
                            tmpMap.put("optValueNo", optValueBO.getOptValueNo());
                            tmpMap.put("optValueNm", optValueBO.getOptValueNm());
                            tmpMap.put("statCd", optValueBO.getPrdStckStatCd());

                            optValueMapList.add(tmpMap);
                        }
                    }

                    fdsDataMap.put("optItemList", optItemMapList);
                    fdsDataMap.put("optValueList", optValueMapList);
                }

                fdsDataMap.put("optWriteList", optWriteList);
            }

            fdsDataMap.put("optClfCd", optClfCd);
            // 옵션 정보 설정 [e]

            String curDate = DateUtil.formatDate("yyyy-MM-dd HH:mm:ss");

            if( "Y".equals(updateYn) ) {
                String createDt = StringUtil.nvl(preProductVO.getCreateDt());

                if( createDt.indexOf(".") > -1 ) {
                    fdsDataMap.put("createDt", createDt.substring(0, createDt.indexOf(".")));
                } else {
                    fdsDataMap.put("createDt", createDt);
                }
                fdsDataMap.put("updateDt", curDate);
            } else {
                fdsDataMap.put("createDt", curDate);
                fdsDataMap.put("updateDt", curDate);
            }

            // 셀러 정보 설정 [s]
            MemberVO memberVO = productVO.getMemberVO();

            fdsDataMap.put("memNo", memberVO.getMemNO());
            fdsDataMap.put("memId", StringUtil.nvl(memberVO.getMemID()));
//            fdsDataMap.put("memId_hs", HashUtil.getHashFor11stFDS(StringUtil.nvl(memberVO.getMemID())));
            fdsDataMap.put("memNm", StringUtil.nvl(memberVO.getMemNm()));
//            fdsDataMap.put("memNm_hs", HashUtil.getHashFor11stFDS(StringUtil.nvl(memberVO.getMemNm())));
//            fdsDataMap.put("memTelNo", memberVO.getGnrlTlphnNO());
//            fdsDataMap.put("memTelNo_hs", HashUtil.getHashFor11stFDS(StringUtil.nvl(memBO.getGnrlTlphnNO())));
//            fdsDataMap.put("memPhoneNo", StringUtil.nvl(memBO.getPrtblTlphnNO()));
//            fdsDataMap.put("memPhoneNo_hs", HashUtil.getHashFor11stFDS(StringUtil.nvl(memBO.getPrtblTlphnNO())));
//            fdsDataMap.put("memEmail", StringUtil.nvl(memBO.getConcatEmail()));
//            fdsDataMap.put("memEmail_hs", HashUtil.getHashFor11stFDS(StringUtil.nvl(memBO.getConcatEmail())));
            fdsDataMap.put("memTypCd", memberVO.getMemTypCD());
            fdsDataMap.put("memClf", memberVO.getMemClf());
            fdsDataMap.put("memEnpAddr", null);
            fdsDataMap.put("memEnpTelNo", null);
            fdsDataMap.put("memEnpTelNo_hs", null);
            fdsDataMap.put("memBnkCd", null);
            fdsDataMap.put("memAddr", null);
            fdsDataMap.put("frgnClf", null);
            fdsDataMap.put("psnCobsEnprNo", null);
            if( memberVO.isEtprsSeller()) {
//                fdsDataMap.put("memEnpBsnsNm", enpBO.getBsnsNM());
//                fdsDataMap.put("memEnpCobsRptvNm", enpBO.getCobsRPTV());
//                fdsDataMap.put("memEnpAddr", enpBO.getEnpAddr());
//                fdsDataMap.put("memEnpTelNo", StringUtil.nvl(enpBO.getRptvTlphnNO()));
//                fdsDataMap.put("memEnpTelNo_hs", HashUtil.getHashFor11stFDS(StringUtil.nvl(enpBO.getRptvTlphnNO())));
//                fdsDataMap.put("psnCobsEnprNo", memBO.getEnterpriseMemberBO().getPsnCobsEnprNO());
            }
//            if( memBO.getAccountBO() != null ) {
//                fdsDataMap.put("memBnkCd", memBO.getAccountBO().getBnkCD());
//            }
//            if( memBO.getAddressBO() != null ) {
//                fdsDataMap.put("memAddr", memBO.getAddressBO().getBaseAddr() + " " + memBO.getAddressBO().getDtlsAddr());
//            }
//            if( memBO.getPrivateMemberBO() != null ) {
//                fdsDataMap.put("frgnClf", memberVO.getPrivateMemberBO().getFrgnclf());
//            }
            fdsDataMap.put("frgnClf", memberVO.getFrgnClf());

            // 판매자 해외 계좌 정보 조회
//            fdsDataMap.put("memGlobalBnkNm", null);
//            MemberBO accBO = prdBasicInfoService.getMemberGlobalAccountInfo(productBO);
//            if( accBO != null && accBO.getAccountBO() != null ) {
//                fdsDataMap.put("memGlobalBnkNm", accBO.getAccountBO().getBnkNM());
//            }

            // 닉네임 정보 조회
//            ProductBO nckInfo = prdBasicInfoService.getSellerProductNckNm(productBO);
//            fdsDataMap.put("nckNm", null);
//            if( nckInfo != null && StringUtil.isNotEmpty(nckInfo.getSelMnbdNckNmText()) ) {
//                fdsDataMap.put("nckNm", nckInfo.getSelMnbdNckNmText());
//            }

            // 출고지, 반품교환지 정보 조회
//            fdsDataMap.put("outAddr", null);
//            fdsDataMap.put("inAddr", null);
//            List<InOutAddressBO> inOutList = prdDeliveryInfoService.getInOutAddress(SqlMapLoader.getInstance(), productBO.getPrdNo());
//            if( CmnUtil.isNotEmpty(inOutList) ) {
//                InOutAddressBO addrBO = null;
//                for(i = 0, listSize = inOutList.size() ; i < listSize ; i++) {
//                    addrBO = inOutList.get(i);
//
//                    if(addrBO == null) {
//                        continue;
//                    }
//
//                    // 출고지
//                    if( ProductConstDef.PRD_ADDR_CLF_CD_OUT.equals(addrBO.getPrdAddrClfCd())
//                            || ( CmnUtil.isEmpty(fdsDataMap.get("outAddr")) && ProductConstDef.PRD_GLB_ADDR_CLF_CD_OUT.equals(addrBO.getPrdAddrClfCd()) ) ) {
//                        fdsDataMap.put("outAddr", addrBO.getAddrNm());
//                    }
//                    // 반품,교환지
//                    else if( ProductConstDef.PRD_ADDR_CLF_CD_IN.equals(addrBO.getPrdAddrClfCd())
//                            || ( CmnUtil.isEmpty(fdsDataMap.get("inAddr")) && ProductConstDef.PRD_GLB_ADDR_CLF_CD_IN.equals(addrBO.getPrdAddrClfCd())) ) {
//                        fdsDataMap.put("inAddr", addrBO.getAddrNm());
//                    }
//                }
//            }
            // 셀러 정보 설정 [e]

//            FDSService fdsService = new FDSService();
//            fdsService.sendFDSForProduct(request, fdsDataMap, ktbAgent);

        } catch(Exception e) {
            // FDS 전송용으로 Error Pass
            log.error("상품 등록 수정 시 FDS 전송용으로 해당 에러는 무시 됩니다. : " + e.getMessage(), e);
        }
    }

}
