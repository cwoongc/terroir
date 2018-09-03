package com.elevenst.terroir.product.registration.fee.controller;

import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.fee.service.FeeServiceImpl;
import com.elevenst.terroir.product.registration.fee.vo.SaleFeeVO;
import com.elevenst.terroir.product.registration.product.message.ProductExceptionMessage;
import com.elevenst.terroir.product.registration.product.vo.BaseVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import skt.tmall.auth.Auth;
import skt.tmall.auth.AuthCode;
import skt.tmall.auth.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping(value="/fee", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FeeController {

    @Autowired
    FeeServiceImpl feeService;

    /**
     * 수수료 조회
     */
    @GetMapping("/sale-fee-rate/{dispCtgr2No}/{dispCtgr3No}")
    @ResponseBody
    public ResponseEntity<String> getSaleFeeRate(HttpServletRequest request, HttpServletResponse response, @RequestParam(name="selMthdCd", defaultValue = "01") String selMthdCd, @PathVariable long dispCtgr2No, @PathVariable long dispCtgr3No, @RequestParam(name="prdNo", defaultValue = "0") long prdNo, @RequestParam(name="selPrc", defaultValue = "0") long selPrc) {
        JSONObject jsonObject = new JSONObject();
        try {
            long userNo = 0;
            Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
            if (auth != null) userNo = auth.getMemberNumber();
            if (userNo == 0) throw new Exception(ProductExceptionMessage.AUTH_ERROR);
            if (dispCtgr2No <= 0) dispCtgr2No = 0;
            if (dispCtgr3No <= 0) dispCtgr3No = 0;

            String feeInfo = feeService.getSaleFeeRateAmount(dispCtgr2No, dispCtgr3No, selMthdCd, userNo, prdNo, selPrc);
            if (StringUtil.isEmpty(feeInfo)) feeInfo = "";

            // 카테고리 기본 수수료율 조회
            double feeDefault = 0;
            ProductVO productVO = new ProductVO();
            productVO.setBaseVO(new BaseVO());
            productVO.setDispCtgrNo(dispCtgr2No);
            productVO.getBaseVO().setSelMthdCd(selMthdCd);
            SaleFeeVO saleFeeVO = feeService.getCategoryDefaultFee(productVO);

            if(saleFeeVO != null && saleFeeVO.getFee() > 0) {
                feeDefault = saleFeeVO.getFee();
            }

            // JSON 포맷 변환
            jsonObject.put("selFee", feeInfo);
            jsonObject.put("feeDefault", feeDefault);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return new ResponseEntity(jsonObject, HttpStatus.OK);
    }
}
