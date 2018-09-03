package com.elevenst.terroir.product.registration.ctgrattr.validate;

import com.elevenst.common.properties.PropMgr;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.category.service.CategoryServiceImpl;

import com.elevenst.terroir.product.registration.ctgrattr.service.CtgrAttrServiceImpl;
import com.elevenst.terroir.product.registration.ctgrattr.vo.CtgrAttrCompVO;
import com.elevenst.terroir.product.registration.ctgrattr.vo.CtgrAttrVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.code.SyAppConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.mapper.ProductMapper;
import com.elevenst.terroir.product.registration.product.service.ProductServiceImpl;

import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
public class CtgrAttrValidate {
    @Autowired
    PropMgr propMgr;

    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    CtgrAttrServiceImpl ctgrAttrService;

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    ProductMapper productMapper;


    public void checkCtgrAttrCompVO(ProductVO productVO ) throws ProductException {


        List<CtgrAttrCompVO> productAttrCompVOList = productVO.getCtgrAttrCompVOList();

        try {

            String prdTypCd = productVO.getBaseVO().getPrdTypCd(); //
            String createCd = productVO.getBaseVO().getCreateCd(); //StringUtils.defaultIfEmpty(dataBox.getNotNullString("createCd"), "0200");	// OpenAPI : 0100, SellerOffice : 0200
            boolean isPrdInfoTypeCheck = true;                                                // 상품정보고시 필수 CHECK boolean
            boolean isPrdInfoTypeCheck2 = true;                                                // 상품정보고시 필수 CHECK boolean
            boolean isPrdInfoTypeCheck3 = true;



            // 상품정보제공고시 데이터 비필수 셀러 여부 CHECK boolean (true: 필수, false: 비 필수)
            //boolean isSellerAPI = ProductConstDef.PRD_CREATE_CD_SELLER_API.equals(createCd);
            /*
            if(validAppMap != null && validAppMap.containsKey(SyAppConstDef.SWITCH_PRD_INFOTYPE)) {
                isPrdInfoTypeCheck = false;
            }

            if(validAppMap != null && validAppMap.containsKey(SyAppConstDef.SWITCH_PRD_INFOTYPE2)) {
                isPrdInfoTypeCheck2 = false;
            }
            */



            // 상품정보제공고시 데이터 비필수 셀러 여부
            long selMnbdNo = productVO.getSelMnbdNo();//("Y".equals(dataBox.getNotNullString("backOfficeYn", "N"))) ? dataBox.getLong("selMnbdNo", 0) : userNo;

            if (StringUtil.nvl(propMgr.get1hourTimeProperty(SyAppConstDef.SKIP_PRD_INFOTYPE_SELLER), "").indexOf("|" + String.valueOf(selMnbdNo) + "|") >= 0) {
                isPrdInfoTypeCheck3 = false;
            }

            // 공정위 상품정보제공고시 카테고리 여부 확인
            // 수정이면서 DB 적용일에 해당되지 않을 경우 check
            if (isPrdInfoTypeCheck3) {
                if (productVO.getPrdNo() != 0 && (isPrdInfoTypeCheck)) {
                    // API 수정이아니거나 API DB 적용일에 해당되지 않을 경우 check
                    if (!"0100".equals(createCd) || isPrdInfoTypeCheck2) {
                        if ("false".equals(categoryService.isPrdInfoTypeCategory(productVO.getBaseVO().getInfoTypeCtgrNo()))) {
                            throw new ProductException("상품정보제공 유형 정보가 존재하지 않습니다.");
                        }
                    }
                }
                // 상품 등록이면서 DB 적용일에 해당되지 않고 타운 배달용 상품이 아닐 경우
                else if (productVO.getPrdNo() == 0 && isPrdInfoTypeCheck && !ProductConstDef.PRD_TYP_CD_TOWN_DELIVERY.equals(prdTypCd)) {
                    if ("false".equals(categoryService.isPrdInfoTypeCategory(productVO.getBaseVO().getInfoTypeCtgrNo()))) {
                        throw new ProductException("상품정보제공 유형 정보가 존재하지 않습니다.");
                    }
                }
            }


            // 키속성 체크
            List<CtgrAttrVO> ctgrAttrVOList = new ArrayList<CtgrAttrVO>();
            try {
                // 전시카테고리, 상품정보제공 유형 카테고리로 필수 속성 정보 가져오기
                ctgrAttrVOList = ctgrAttrService.getServiceKeyAttributeListByDispCtgNo(productVO.getDispCtgrNo(), false, productVO.getBaseVO().getInfoTypeCtgrNo());
            } catch (Exception ex) {
                throw new ProductException("카테고리별 필수 속성 조회시 오류 발생했습니다.");
            }

            if (ctgrAttrVOList != null && ctgrAttrVOList.size() > 0) { // DB 데이터 기준

                for (CtgrAttrVO ctgrAttributeVO : ctgrAttrVOList) {
                    // 브랜드, 모델 세팅
                    if (ProductConstDef.PRD_ATTR_CD_BRAND == Long.parseLong(ctgrAttributeVO.getPrdAttrValueCd())) {
                        //if(StringUtil.isNotEmpty(dataBox.getString("brand"))) {
                        if (StringUtil.isNotEmpty(productVO.getBaseVO().getBrandNm())) {
                            CtgrAttrCompVO brandComp = new CtgrAttrCompVO();
                            brandComp.setPrdAttrNo(ctgrAttributeVO.getPrdAttrNo());
                            brandComp.setAttrType(ctgrAttributeVO.getAttrType());
                            brandComp.setPrdAttrCd(ctgrAttributeVO.getPrdAttrValueCd());
                            brandComp.setPrdAttrNm(ctgrAttributeVO.getPrdAttrValueNm());
                            brandComp.setPrdAttrEntyCd(ctgrAttributeVO.getAttrEntWyCd());
                            brandComp.setPrdAttrValueCd("");
                            brandComp.setPrdAttrValueNm(productVO.getBaseVO().getBrandNm());
                            productAttrCompVOList.add(brandComp);
                            //dataBox.put(String.valueOf(ctgrAttributeBO.getPrdAttrNo()), dataBox.getString("brand"));
                        }
                        /*
                        prdAttrTypeList.add(ctgrAttributeVO.getAttrType());
                        prdAttrCdList.add(ctgrAttributeVO.getPrdAttrValueCd());
                        prdAttrNmList.add(ctgrAttributeVO.getPrdAttrValueNm());
                        prdAttrNoList.add(String.valueOf(ctgrAttributeVO.getPrdAttrNo()));
                        */
                    } else if (ProductConstDef.ATTR_CD_MODEL.equals(ctgrAttributeVO.getPrdAttrValueCd()) && "01".equals(ctgrAttributeVO.getDispCtgrKindCd())) {
                        //f(StringUtil.isNotEmpty(dataBox.getString("modelNm"))) {
                        if (StringUtil.isNotEmpty(productVO.getPrdModelVO().getModelNm())) {
                            CtgrAttrCompVO modelComp = new CtgrAttrCompVO();
                            modelComp.setPrdAttrNo(ctgrAttributeVO.getPrdAttrNo());
                            modelComp.setAttrType(ctgrAttributeVO.getAttrType());
                            modelComp.setPrdAttrCd(ctgrAttributeVO.getPrdAttrValueCd());
                            modelComp.setPrdAttrNm(ctgrAttributeVO.getPrdAttrValueNm());
                            modelComp.setPrdAttrEntyCd(ctgrAttributeVO.getAttrEntWyCd());
                            modelComp.setPrdAttrValueCd("");
                            modelComp.setPrdAttrValueNm(productVO.getPrdModelVO().getModelNm());
                            productAttrCompVOList.add(modelComp);
                        }
                        /*
                        prdAttrTypeList.add(ctgrAttributeVO.getAttrType());
                        prdAttrCdList.add(ctgrAttributeVO.getPrdAttrValueCd());
                        prdAttrNmList.add(ctgrAttributeVO.getPrdAttrValueNm());
                        prdAttrNoList.add(String.valueOf(ctgrAttributeVO.getPrdAttrNo()));
                        */
                    }
                }



                for (CtgrAttrVO ctgrAttributeVO : ctgrAttrVOList) {
                    if("07".equals(ctgrAttributeVO.getDispCtgrKindCd())) {
                        boolean isAttrCdExist = false;
                        for (CtgrAttrCompVO ctgrAttrCompVO : productAttrCompVOList) {
                            if (ctgrAttrCompVO.getPrdAttrCd().equals(ctgrAttributeVO.getPrdAttrValueCd())) {
                                isAttrCdExist = true;
                                break;
                            }
                        }

                        if (!isAttrCdExist) {
                            throw new ProductException(ctgrAttributeVO.getPrdAttrValueNm() + ": 키속성 정보는 반드시 입력하셔야 합니다.");
                        }
                    }
                }



                // 금칙어 체크
                for (CtgrAttrCompVO compVO : productAttrCompVOList) {
                    String tnsKeyword = productService.getKeywordPrdFilter(productVO, compVO.getPrdAttrValueNm());
                    if (StringUtil.isNotEmpty(tnsKeyword)) {
                        throw new ProductException(compVO.getPrdAttrValueNm() + ": 속성명에 금칙어가 사용 되었습니다.[" + tnsKeyword + "]");
                    }
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ProductException("상품 속성값 체크시 오류");

        }


    }
}
