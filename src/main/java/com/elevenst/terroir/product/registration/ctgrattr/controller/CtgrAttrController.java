package com.elevenst.terroir.product.registration.ctgrattr.controller;


import com.elevenst.common.properties.PropMgr;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.brand.vo.BrandVO;
import com.elevenst.terroir.product.registration.category.code.DispCtgrKindCd;
import com.elevenst.terroir.product.registration.ctgrattr.mapper.CtgrAttrMapper;
import com.elevenst.terroir.product.registration.ctgrattr.service.CtgrAttrServiceImpl;
import com.elevenst.terroir.product.registration.ctgrattr.vo.CtgrAttrVO;
import com.elevenst.terroir.product.registration.ctgrattr.vo.CtgrAttrValueVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.validate.ProductValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value="/ctgr-attr", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CtgrAttrController {
    @Autowired
    CtgrAttrServiceImpl ctgrAttrService;

    @Autowired
    ProductValidate productValidate;

    @Autowired
    PropMgr propMgr;



    @GetMapping("/info/{dispCtgrNo}/{infoTypeCtgrNo}")
    public List getCtgrAttributeInfo(@PathVariable long dispCtgrNo,
                                     @PathVariable long infoTypeCtgrNo,
                                     @RequestParam(name="modifyCtgrYn", defaultValue = "N") String modifyCtgrYn,
                                     @RequestParam(name="tourAttrDisableYn", defaultValue = "N") String tourAttrDisableYn,
                                     @RequestParam(name="prdNo", defaultValue = "0") long prdNo
    ){
        if ("Y".equals(modifyCtgrYn)) {
            prdNo = 0;
        }

        List<CtgrAttrVO> ctgrAttrList = ctgrAttrService.getCtgrAttributeList(prdNo,  dispCtgrNo,  modifyCtgrYn, infoTypeCtgrNo,  null);


        // 여행 상품 유형 카테고리일 때 일반배송상품일 경우에는 카테고리 속성 제외
        if( "Y".equals(tourAttrDisableYn) && ctgrAttrList != null && !ctgrAttrList.isEmpty() ) {
            CtgrAttrVO tmpBO = null;
            for(int i = 0 ; i < ctgrAttrList.size() ; i++) {
                tmpBO = ctgrAttrList.get(i);
                if( DispCtgrKindCd.DISP_CTGR_KIND_CD_01.equals(tmpBO.getDispCtgrKindCd()) ) {
                    ctgrAttrList.remove(i);
                    i--;
                }
            }
        }

        // 브랜드, 모델명 속성은 제외(카테고리 선택 시 별도 노출됨)
        if(ctgrAttrList != null && !ctgrAttrList.isEmpty() ){
            for(int i = 0 ; i < ctgrAttrList.size() ; i++) {
                CtgrAttrVO tmpBO = ctgrAttrList.get(i);
                if( ProductConstDef.ATTR_CD_BRAND.equals(tmpBO.getPrdAttrValueCd()) || (ProductConstDef.ATTR_CD_MODEL.equals(tmpBO.getPrdAttrValueCd()) && "01".equals(tmpBO.getDispCtgrKindCd()))) {
                    ctgrAttrList.remove(i);
                    i--;
                }
            }
        }

        return ctgrAttrList;


    }

    @GetMapping("/search/ctgrAttrInfoForLife/{dispCtgr3No}/{infoTypeCtgrNo}/{modifyCtgrYn}/{prdNo}")
    @ResponseBody
    public List getCtgrAttributeInfoForLife(HttpServletRequest request, HttpServletResponse response,
                                     @PathVariable long dispCtgr3No,
                                     @PathVariable long infoTypeCtgrNo,
                                     @PathVariable String modifyCtgrYn,
                                     @PathVariable long prdNo
    ){
        if ("Y".equals(modifyCtgrYn)) {
            prdNo = 0;
        }

        List<CtgrAttrVO> ctgrAttrList = ctgrAttrService.getCtgrAttributeListForTour(prdNo, dispCtgr3No, modifyCtgrYn, infoTypeCtgrNo, null, "N", ProductConstDef.CATE_LC_ATTR_SVC_TYP_CD);
        return ctgrAttrList;


    }


    @GetMapping("/search/ctgrAttrInfoForTour/{dispCtgr2No}/{infoTypeCtgrNo}/{modifyCtgrYn}/{prdNo}")
    @ResponseBody
    public List getCtgrAttributeInfoForTour(HttpServletRequest request, HttpServletResponse response,
                                            @PathVariable long dispCtgr2No,
                                            @PathVariable long infoTypeCtgrNo,
                                            @PathVariable String modifyCtgrYn,
                                            @PathVariable long prdNo
    ){
        if ("Y".equals(modifyCtgrYn)) {
            prdNo = 0;
        }

        System.out.println("----------------> prdNo :"+prdNo);



        List<CtgrAttrVO> ctgrAttrList = ctgrAttrService.getCtgrAttributeListForTour(prdNo, dispCtgr2No, modifyCtgrYn, infoTypeCtgrNo, null, "N", ProductConstDef.CATE_ATTR_SVC_TYP_CD);


        return ctgrAttrList;


    }
}
