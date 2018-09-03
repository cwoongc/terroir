package com.elevenst.terroir.product.registration.template.controller;

import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.delivery.vo.ProductSellerBasiDeliveryCostVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.template.exception.TemplateServiceExcetpion;
import com.elevenst.terroir.product.registration.template.service.TemplateServiceImpl;
import com.elevenst.terroir.product.registration.template.vo.InventoryProductAddressVO;
import com.elevenst.terroir.product.registration.template.vo.PrdInfoTmplVO;
import com.elevenst.terroir.product.registration.template.vo.ProductInformationTemplateVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skt.tmall.auth.Auth;
import skt.tmall.auth.AuthCode;
import skt.tmall.auth.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value="/template", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TemplateController {

    @Autowired
    TemplateServiceImpl templateService;

    @ApiOperation(value = "배송 템플릿 정보 조회",
            notes = "배송정보 템플릿 목록 정보를 조회한다.",
            response = JSONObject.class)
    @GetMapping("/delivery")
    @ResponseBody
    public ResponseEntity<JSONArray> getProductInformationTemplateAllList(HttpServletRequest request, HttpServletResponse response) {

        long selMnbdNo = 0;

        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if(auth != null) {
            selMnbdNo = auth.getMemberNumber();
        }

        PrdInfoTmplVO prdInfoTmplVO = new PrdInfoTmplVO();

        prdInfoTmplVO.setMemNo(selMnbdNo);
        prdInfoTmplVO.setPrdInfoTmpltClfCd(ProductConstDef.PRD_INFO_TMPLT_CLF_CD_DLV);

        List templateList =templateService.getProductInformationTemplateAllList(prdInfoTmplVO);
        JSONArray result = JSONArray.fromObject(templateList);
        if(templateList.size() < 1) {
            return new ResponseEntity(result, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @ApiOperation(value = "배송 템플릿 정보 조회",
            notes = "배송정보 템플릿 목록 상세 정보를 조회한다.",
            response = ProductInformationTemplateVO.class)
    @GetMapping("/delivery/{tmpltNo}")
    @ResponseBody
    public ResponseEntity getProductTemplateJson(HttpServletRequest request, HttpServletResponse response,
                                                 @PathVariable long tmpltNo) {

        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        long userNo = auth.getMemberNumber();
        ProductInformationTemplateVO resultVO = null;
        try {
            ProductInformationTemplateVO templateVO = new ProductInformationTemplateVO();
            templateVO.setMemNo(userNo);
            templateVO.setPrdInfoTmpltNo(tmpltNo);

            resultVO = templateService.getProductInformationTemplate(templateVO);

            if(null != templateVO.getInventoryVO()) {
                List addressVOList = templateVO.getInventoryVO().getInventoryProductAddressVOList();
                if (StringUtil.isNotEmpty(addressVOList)) {
                    for (int n = 0; n < addressVOList.size(); n++) {
                        InventoryProductAddressVO inventoryProductAddressVO = (InventoryProductAddressVO) addressVOList.get(n);
                        if (inventoryProductAddressVO != null && ProductConstDef.PRD_ADDR_CLF_CD_OUT.equals(inventoryProductAddressVO.getPrdAddrClfCd())) {
                            // 출고지 조건부 기준 배송비 (구매금액별)조회 ---------------------------------
                            ProductSellerBasiDeliveryCostVO addrBasiDlvCstVO = new ProductSellerBasiDeliveryCostVO();
                            addrBasiDlvCstVO.setSelMnbdNo(inventoryProductAddressVO.getMemNo());
                            addrBasiDlvCstVO.setAddrSeq(inventoryProductAddressVO.getAddrSeq());
                            addrBasiDlvCstVO.setMbAddrLocation(inventoryProductAddressVO.getMbAddrLocation());
                            resultVO.setAddrBasiDlvCstTxt(templateService.getAddrBasiDlvCstTxt(addrBasiDlvCstVO));
                        }
                    }
                }
            }

            resultVO.setPrdInfoTmpltNo(tmpltNo);

            if(resultVO==null){
                return new ResponseEntity(null, HttpStatus.NO_CONTENT);
            }

        } catch (TemplateServiceExcetpion e) {
            log.error(e.getMessage(), e);
        }
        return new ResponseEntity(resultVO, HttpStatus.OK);
    }
}