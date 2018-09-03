package com.elevenst.terroir.product.registration.delivery.controller;

import com.elevenst.common.properties.PropMgr;
import com.elevenst.common.util.NumberUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.common.service.CommonServiceImpl;
import com.elevenst.terroir.product.registration.common.vo.SyCoDetailVO;
import com.elevenst.terroir.product.registration.delivery.exception.DeliveryControllerException;
import com.elevenst.terroir.product.registration.delivery.exception.DeliveryException;
import com.elevenst.terroir.product.registration.delivery.message.DeliveryControllerExceptionMessage;
import com.elevenst.terroir.product.registration.delivery.service.DeliveryServiceImpl;
import com.elevenst.terroir.product.registration.delivery.vo.AddressVO;
import com.elevenst.terroir.product.registration.delivery.vo.ProductSellerBasiDeliveryCostVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.message.ProductExceptionMessage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value="/delivery", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DeliveryController {

    @Autowired
    CommonServiceImpl commonService;

    @Autowired
    DeliveryServiceImpl deliveryService;

    @Autowired
    PropMgr propMgr;

    /** 출력 포맷
     *     [{"dtlsCd":"01","dtlsComNm":"전국"},
     *     {"dtlsCd":"02","dtlsComNm":"전국(제주 도서산간지역 제외)"},
     *     {"dtlsCd":"03","dtlsComNm":"서울"},
     *     {"dtlsCd":"04","dtlsComNm":"인천"},
     *     {"dtlsCd":"05","dtlsComNm":"광주"},
     *     {"dtlsCd":"06","dtlsComNm":"대구"},
     *     {"dtlsCd":"07","dtlsComNm":"대전"},
     *     {"dtlsCd":"08","dtlsComNm":"부산"},
     *     {"dtlsCd":"09","dtlsComNm":"울산"},
     *     {"dtlsCd":"10","dtlsComNm":"경기"},
     *     {"dtlsCd":"11","dtlsComNm":"강원"},
     *     {"dtlsCd":"12","dtlsComNm":"충남"},
     *     {"dtlsCd":"13","dtlsComNm":"충북"},
     *     {"dtlsCd":"14","dtlsComNm":"경남"},
     *     {"dtlsCd":"15","dtlsComNm":"경북"},
     *     {"dtlsCd":"16","dtlsComNm":"전남"},
     *     {"dtlsCd":"17","dtlsComNm":"전북"},
     *     {"dtlsCd":"18","dtlsComNm":"제주"},
     *     {"dtlsCd":"19","dtlsComNm":"서울/경기"},
     *     {"dtlsCd":"20","dtlsComNm":"서울/경기/대전"},
     *     {"dtlsCd":"21","dtlsComNm":"충북/충남"},
     *     {"dtlsCd":"22","dtlsComNm":"경북/경남"},
     *     {"dtlsCd":"23","dtlsComNm":"전북/전남"},
     *     {"dtlsCd":"24","dtlsComNm":"부산/울산"},
     *     {"dtlsCd":"25","dtlsComNm":"서울/경기/제주도서산간 제외지역"},
     *     {"dtlsCd":"26","dtlsComNm":"일부지역불가"}]
     * @return
     */
    @ApiOperation(value = "배송 정보 조회",
            notes = "배송가능 지역을 조회한다.",
            response = JSONArray.class)
    @GetMapping("/poissble-area")
    public ResponseEntity<JSONArray> getDlvPossibleAreaCd() {
        SyCoDetailVO syCoDetailVO = new SyCoDetailVO();
        syCoDetailVO.setGrpCd(ProductConstDef.DLV_CN_AREA_CD);

        List<SyCoDetailVO> codeList = commonService.getCodeDetail(syCoDetailVO);

        JSONArray result = new JSONArray();
        Map obj = new HashMap();

        for(SyCoDetailVO syCoDetail : codeList){
            obj.put("dtlsCd", syCoDetail.getDtlsCd());
            obj.put("dtlsComNm", syCoDetail.getDtlsComNm());
            result.add(obj);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }
    @ApiOperation(value = "배송 정보 조회",
            notes = "배송방법을 조회한다.",
            response = JSONArray.class)
    @GetMapping("/way")
    public ResponseEntity<JSONArray> getDlvWyCd() {
        SyCoDetailVO syCoDetailVO = new SyCoDetailVO();
        syCoDetailVO.setGrpCd(ProductConstDef.DLV_WY_CD);

        List<SyCoDetailVO> codeList = commonService.getCodeDetail(syCoDetailVO);

        JSONArray result = new JSONArray();
        Map obj = new HashMap();

        for(SyCoDetailVO syCoDetail : codeList){
            obj.put("dtlsCd", syCoDetail.getDtlsCd());
            obj.put("dtlsComNm", syCoDetail.getDtlsComNm());
            result.add(obj);
        }
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @ApiOperation(value = "배송 정보 조회",
            notes = "판매자 조건부 배송비 기준설정을 조회한다.",
            response = JSONArray.class)
    @GetMapping("/sel-basi-dlvcst")
    public ResponseEntity<JSONArray> sellerBasiDlvCstSet(HttpServletRequest request, HttpServletResponse response) {

        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        long userNo = auth.getMemberNumber();
        List<ProductSellerBasiDeliveryCostVO> sellerBasiDlvCstList = deliveryService.getSellerBasiDlvCstList(userNo);

        JSONArray result = JSONArray.fromObject(sellerBasiDlvCstList);

        return new ResponseEntity(result,HttpStatus.OK);
    }

    @ApiOperation(value = "배송 정보 조회",
            notes = "판매자 조건부 배송비 기준설정 등록한다.",
            response = JSONArray.class)
    @PostMapping("/sel-basi-dlvcst")
    public ResponseEntity<JSONArray> sellerBasiDlvCstUpdate(HttpServletRequest request, HttpServletResponse response
    ,@RequestBody List<ProductSellerBasiDeliveryCostVO> addrBasiDlvCstVOList) {

        JSONObject dlvInfo = new JSONObject();
        try {
            long memNo = 0;
            String memType = "";
            Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
            if (auth != null)  {
                memNo = auth.getMemberNumber();
                memType = auth.getMemberType();
            }
            if (memNo == 0) throw new DeliveryException(ProductExceptionMessage.AUTH_ERROR);

            boolean isEctSeller = StringUtil.nvl(propMgr.get1hourTimeProperty(ProductConstDef.PRD_DLV_CST_ECT_SELLER),"").indexOf("|" + memNo + "|") >= 0;
            String abrdBrandYn = "";
            if ("03".equals(memType)) {
                abrdBrandYn = "Y";
            } else {
                abrdBrandYn = "N";
            }

            List<ProductSellerBasiDeliveryCostVO> sellerBasiDlvCstList = new ArrayList();

            for(int j =0; j < addrBasiDlvCstVOList.size() ; j++) {

                String dlvCst = String.valueOf(addrBasiDlvCstVOList.get(j).getDlvCst());
                String ordBgnAmt = String.valueOf(addrBasiDlvCstVOList.get(j).getOrdBgnAmt());
                String ordEndAmt = String.valueOf(addrBasiDlvCstVOList.get(j).getOrdEndAmt());

                if (dlvCst == null ||  ordBgnAmt == null || ordEndAmt == null ) {
                    throw new DeliveryException("판매자 조건부 배송비 설정값이 유효하지 않습니다.");
                }

                ProductSellerBasiDeliveryCostVO sellerBasiDlvCstVO = new ProductSellerBasiDeliveryCostVO();

                    if (ordBgnAmt != null && !"".equals(ordBgnAmt) && ordEndAmt != null && !"".equals(ordEndAmt)
                            && dlvCst != null && !"".equals(dlvCst)) {

                        if (StringUtil.isEmpty(ordBgnAmt) || StringUtil.isEmpty(ordEndAmt) || "0".equals(ordEndAmt.replaceAll(",", "")))
                            throw new DeliveryException("금액은 반드시 입력하셔야 합니다.");

                        long cust = StringUtil.getLong(dlvCst);
                        if (cust % 10 != 0) throw new DeliveryException("배송비는 10원단위로 입력하셔야 합니다.");
                        else {
                            long chkAmt = isEctSeller ? 700000 : ("Y".equals(abrdBrandYn) ? 200000 : 100000);
                            if (cust > chkAmt)
                                throw new DeliveryException("배송비는 " + NumberUtil.getCommaString(chkAmt) + "원을 초과하여 설정할 수 없습니다.");
                        }

                        if (j < addrBasiDlvCstVOList.size() - 1) {
                            if (StringUtil.getLong(ordBgnAmt) >= 999999999999L || StringUtil.getLong(ordEndAmt) >= 999999999999L) {
                                throw new DeliveryException("금액은 999,999,999,999원보다 작아야 합니다.");
                            }
                            if (StringUtil.getLong(ordEndAmt) != StringUtil.getLong(String.valueOf(addrBasiDlvCstVOList.get(j+1).getOrdBgnAmt()))) {
                                throw new DeliveryException("판매자 조건부 배송비 기준설정이 잘못 입력 되었습니다.");
                            }
                        }

                        if (StringUtil.getLong(ordBgnAmt) > StringUtil.getLong(ordEndAmt))
                            throw new DeliveryException("뒤의 입력 금액이 앞의 입력 금액보다 커야합니다.");

                        sellerBasiDlvCstVO.setSelMnbdNo(memNo);
                        sellerBasiDlvCstVO.setOrdBgnAmt(StringUtil.getLong(ordBgnAmt));
                        sellerBasiDlvCstVO.setOrdEndAmt(StringUtil.getLong(ordEndAmt));
                        sellerBasiDlvCstVO.setDlvCst(StringUtil.getLong(dlvCst));
                        sellerBasiDlvCstVO.setCreateNo(memNo);
                        sellerBasiDlvCstVO.setUpdateNo(memNo);

                        sellerBasiDlvCstList.add(sellerBasiDlvCstVO);
                    }
                }

            deliveryService.sellerBasiDlvCstSetOK(sellerBasiDlvCstList, memNo);
            String sellerDlvInfoTxt = deliveryService.getSellerBasiDlvCstTxt(memNo);

            dlvInfo.put("responseMsg", "success");
            dlvInfo.put("sellerDlvInfoTxt", sellerDlvInfoTxt);
        }
        catch (DeliveryException ex) {
            if (log.isErrorEnabled()) {
                log.error("판매자 조건부 기준 배송비 설정 오류", ex);
            }
        }
        return new ResponseEntity(dlvInfo,HttpStatus.OK);
    }



    @ApiOperation(
            value = "상품 주소지 목록 조회",
            notes = "특정 상품과 연관된 출고지, 반품/교환지 등의 주소지를 검색한다.",
            response = AddressVO.class,
            responseContainer = "List"
    )
    @GetMapping("/product-address/{prdNo}")
    public ResponseEntity<List<AddressVO>> getProductAddress(@ApiParam("상품번호") @PathVariable Long prdNo) {

        List<AddressVO> addressVOList = null;

        try {
            addressVOList = deliveryService.getProductAddressList(prdNo);
        } catch (Exception ex) {
            DeliveryControllerException threx = new DeliveryControllerException(DeliveryControllerExceptionMessage.PRODUCT_ADDRESS_LIST_GET_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return new ResponseEntity<>(addressVOList, addressVOList == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
}