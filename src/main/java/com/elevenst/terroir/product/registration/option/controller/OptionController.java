package com.elevenst.terroir.product.registration.option.controller;

import com.elevenst.terroir.product.registration.option.code.OptLimitClfCds;
import com.elevenst.terroir.product.registration.option.service.OptionServiceImpl;
import com.elevenst.terroir.product.registration.option.validate.OptionValidate;
import com.elevenst.terroir.product.registration.option.vo.ProductOptLimitVO;
import com.elevenst.terroir.product.registration.option.vo.StandardOptionGroupMappingVO;
import com.elevenst.terroir.product.registration.option.vo.StandardOptionInfoVO;
import com.elevenst.terroir.product.registration.seller.validate.SellerValidate;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skt.tmall.auth.Auth;
import skt.tmall.auth.AuthCode;
import skt.tmall.auth.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping(value="/option", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OptionController {

    @Autowired
    OptionServiceImpl optionService;

    @Autowired
    OptionValidate optionValidate;

    @Autowired
    SellerValidate sellerValidate;

    @ApiOperation(value = "카테고리 선택 시 표준옵션 정보 조회",
                  notes = "카테고리 선택 시 표준옵션 정보를 조회한다.",
                  response = JSONObject.class)
    @GetMapping("/list/category/{dispCtgrNo}")
    public ResponseEntity<JSONObject> getOptInfoByCtgr(HttpServletRequest request, HttpServletResponse response, @PathVariable long dispCtgrNo) throws Exception {
        long selMnbdNo = 0;
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if (auth != null)  {
            selMnbdNo = auth.getMemberNumber();
        }

        JSONObject jsonObject = new JSONObject();
        StandardOptionGroupMappingVO standardOptionGroupMappingVO = new StandardOptionGroupMappingVO();
        standardOptionGroupMappingVO.setDispCtgrNo(dispCtgrNo);
        jsonObject.put("optItemList", optionService.getOptInfoListAtStd(standardOptionGroupMappingVO));

        ProductOptLimitVO productOptLimitVO = new ProductOptLimitVO();
        productOptLimitVO.setOptLmtClfCd(OptLimitClfCds.STANDARD.getDtlsCd());
        ProductOptLimitVO retProductOptLimitVO = optionService.getProductOptLimitForBasic(productOptLimitVO);
        jsonObject.put("combOptNmCnt", retProductOptLimitVO.getCombOptNmCnt());
        jsonObject.put("combOptValueCnt", retProductOptLimitVO.getCombOptValueCnt());
        jsonObject.put("writeOptNmCnt", retProductOptLimitVO.getWriteOptNmCnt());
        jsonObject.put("combOptCnt", retProductOptLimitVO.getCombOptCnt());

        // 지정일 배송셀러여부 확인
        boolean isPtnrDlvSeller = false;
        if(sellerValidate.isCertStockCdSeller(selMnbdNo)) {
            isPtnrDlvSeller = true;
        }
        jsonObject.put("isPtnrDlvSeller", isPtnrDlvSeller);
        return new ResponseEntity(jsonObject, HttpStatus.OK);
    }

    @ApiOperation(value = "등록된 옵션정보 정보 조회",
                  notes = "등록된 옵션정보 정보를 조회한다.",
                  response = JSONObject.class)
    @GetMapping("/list/prd/{dispCtgrNo}/{prdNo}")
    public ResponseEntity<JSONObject> getOptInfoByCtgrWithPrd(@PathVariable long dispCtgrNo, @PathVariable long prdNo) throws Exception {
        StandardOptionGroupMappingVO standardOptionGroupMappingVO = new StandardOptionGroupMappingVO();
        standardOptionGroupMappingVO.setDispCtgrNo(dispCtgrNo);
        standardOptionGroupMappingVO.setPrdNo(prdNo);

        StandardOptionInfoVO retStandardOptionInfoVO = optionService.getOptInfoListAtStdWithPrd(standardOptionGroupMappingVO);
        JSONObject jsonObject = new JSONObject();
        if(CollectionUtils.isNotEmpty(retStandardOptionInfoVO.getStdOptItemList())){
            jsonObject.put("optItemList", retStandardOptionInfoVO.getStdOptItemList());
        }
        if(CollectionUtils.isNotEmpty(retStandardOptionInfoVO.getPrdOptItemList())){
            jsonObject.put("prdOptItemList", retStandardOptionInfoVO.getPrdOptItemList());
        }
        if(CollectionUtils.isNotEmpty(retStandardOptionInfoVO.getPrdOptCustList())){
            jsonObject.put("prdOptCustList", retStandardOptionInfoVO.getPrdOptCustList());
        }
        return new ResponseEntity(jsonObject, HttpStatus.OK);
    }

    @ApiOperation(value = "선택형 옵션수 확인 - 선택형옵션 5개 초과 상품 정보 조회",
                  notes = "선택형 옵션수 확인 - 선택형옵션 5개 초과 상품 정보를 조회한다.",
                  response = Integer.class)
    @GetMapping("/count/{prdNo}")
    public ResponseEntity<Integer> getOptionCnt(@PathVariable long prdNo) throws Exception {
        return new ResponseEntity(optionService.getSelectOptionCnt(prdNo), HttpStatus.OK);
    }

    @ApiOperation(value = "옵션 / 추가구성상품 존재 여부 정보 조회",
                  notes = "옵션 / 추가구성상품 존재 여부 정보를 조회한다. gubun // 1:옵션, 2:추가구성상품",
                  response = Boolean.class)
    @GetMapping("/boolean/{prdNo}/{gubun}")
    public ResponseEntity<Boolean> isOptionExistCnt(@PathVariable long prdNo, @PathVariable String gubun) throws Exception {
        return new ResponseEntity(optionService.isExistOptionOrAddPrduct(prdNo, gubun), HttpStatus.OK);
    }

    @ApiOperation(value = "옵션가 예외 % 정보 조회",
                  notes = "옵션가 예외 % 정보를 조회한다.",
                  response = Integer.class)
    @GetMapping("/count/maxprice")
    public ResponseEntity<Integer> getSellerOptAddPrcLimit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long selMnbdNo = 0;
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if (auth != null)  {
            selMnbdNo = auth.getMemberNumber();
        }
        return new ResponseEntity(optionValidate.getSellerOptAddPrcLimit(selMnbdNo), HttpStatus.OK);
    }

    @ApiOperation(value = "옵션최대개수 제한 기본정보 조회",
                  notes = "옵션최대개수 제한 기본정보를 조회한다.",
                  response = ProductOptLimitVO.class)
    @GetMapping("/optlimit")
    public ResponseEntity<ProductOptLimitVO> getProductOptLimitForBasic() throws Exception {
        ProductOptLimitVO productOptLimitVO = new ProductOptLimitVO();
        productOptLimitVO.setOptLmtClfCd(OptLimitClfCds.STANDARD.getDtlsCd());
        return new ResponseEntity(optionService.getProductOptLimitForBasic(productOptLimitVO), HttpStatus.OK);
    }

    @ApiOperation(value = "예상배송비 정보 조회",
                  notes = "예상배송비 정보를 조회한다.",
                  response = Long.class)
    @GetMapping("/opt-global-delivery-cost/{prdNo}/{dispCtgrNo}")
    public ResponseEntity<Long> getOptionGlobalItgDeliveryCost(@PathVariable long prdNo, @PathVariable long dispCtgrNo, @RequestParam("optWght") long optWght, @RequestParam("optAddPrc") double optAddPrc, @RequestParam("prdWght") long prdWght, @RequestParam("prdCstmAplPrc") double prdCstmAplPrc, @RequestParam("itgMemNo") long itgMemNo) throws Exception {
        if (prdNo <= 0) { // 상품번호가 없는경우 옵션무게와 옵션가에 상품의 무게와 상품가를 더해서 계산한다.
            optionValidate.checkOptionGlobalItgDeliveryCost(itgMemNo, prdWght, prdCstmAplPrc, dispCtgrNo);
            optWght = optWght + prdWght;
            optAddPrc = optAddPrc + prdCstmAplPrc;
        }
        return new ResponseEntity(optionService.getOptionGlobalItgDeliveryCostInfo(prdNo, optWght, 0, itgMemNo, dispCtgrNo, optAddPrc+"", 0, "N"), HttpStatus.OK);
    }
}
