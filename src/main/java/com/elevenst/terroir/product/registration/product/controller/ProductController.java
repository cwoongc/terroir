package com.elevenst.terroir.product.registration.product.controller;

import com.elevenst.common.code.CacheKeyConstDef;
import com.elevenst.common.properties.PropMgr;
import com.elevenst.common.properties.SOfficeProperties;
import com.elevenst.common.util.DBSwitchUtil;
import com.elevenst.common.util.DateUtil;
import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.common.util.cache.ObjectCacheImpl;
import com.elevenst.terroir.product.registration.benefit.service.ProductBenefitServiceImpl;
import com.elevenst.terroir.product.registration.catalog.service.CatalogServiceImpl;
import com.elevenst.terroir.product.registration.category.service.CategoryServiceImpl;
import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import com.elevenst.terroir.product.registration.cert.ProductCertFactory;
import com.elevenst.terroir.product.registration.cert.service.CertService;
import com.elevenst.terroir.product.registration.cert.thirdparty.ProductCertValidApiService;
import com.elevenst.terroir.product.registration.cert.thirdparty.ProductElectroCertValidApiService;
import com.elevenst.terroir.product.registration.common.service.CommonServiceImpl;
import com.elevenst.terroir.product.registration.common.vo.CodeVO;
import com.elevenst.terroir.product.registration.common.vo.GroupCodeVO;
import com.elevenst.terroir.product.registration.common.vo.SyCoDetailVO;
import com.elevenst.terroir.product.registration.coordi_prd.service.CoordiProductServiceImpl;
import com.elevenst.terroir.product.registration.coordi_prd.vo.CoordiProductVO;
import com.elevenst.terroir.product.registration.ctgrattr.service.CtgrAttrServiceImpl;
import com.elevenst.terroir.product.registration.ctgrattr.vo.CtgrAttrVO;
import com.elevenst.terroir.product.registration.customer.service.CustomerServiceImpl;
import com.elevenst.terroir.product.registration.delivery.service.DeliveryServiceImpl;
import com.elevenst.terroir.product.registration.desc.code.PrdDescTypCd;
import com.elevenst.terroir.product.registration.desc.entity.PdPrdDesc;
import com.elevenst.terroir.product.registration.desc.service.ProductDescServiceImpl;
import com.elevenst.terroir.product.registration.desc.vo.ProductDescVO;
import com.elevenst.terroir.product.registration.etc.service.EtcServiceImpl;
import com.elevenst.terroir.product.registration.etc.vo.PrdOthersVO;
import com.elevenst.terroir.product.registration.image.code.ImageKind;
import com.elevenst.terroir.product.registration.image.entity.PdPrdImage;
import com.elevenst.terroir.product.registration.image.service.ProductImageServiceImpl;
import com.elevenst.terroir.product.registration.image.vo.ProductImageVO;
import com.elevenst.terroir.product.registration.option.service.OptionServiceImpl;
import com.elevenst.terroir.product.registration.option.vo.PdOptItemVO;
import com.elevenst.terroir.product.registration.option.vo.ProductOptLimitVO;
import com.elevenst.terroir.product.registration.option.vo.ProductStockVO;
import com.elevenst.terroir.product.registration.option.vo.StandardOptionGroupMappingVO;
import com.elevenst.terroir.product.registration.option.vo.StandardOptionInfoVO;
import com.elevenst.terroir.product.registration.option.vo.StandardOptionItemVO;
import com.elevenst.terroir.product.registration.price.service.PriceServiceImpl;
import com.elevenst.terroir.product.registration.product.code.PrdStatCd;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.code.RmaterialTypCd;
import com.elevenst.terroir.product.registration.product.code.SelPrdClfCd;
import com.elevenst.terroir.product.registration.product.entity.PdPrdTempSave;
import com.elevenst.terroir.product.registration.product.exception.ProductControllerException;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.message.ProductControllerExceptionMessage;
import com.elevenst.terroir.product.registration.product.message.ProductExceptionMessage;
import com.elevenst.terroir.product.registration.product.service.ProductServiceImpl;
import com.elevenst.terroir.product.registration.product.validate.ProductValidate;
import com.elevenst.terroir.product.registration.product.vo.*;
import com.elevenst.terroir.product.registration.seller.validate.SellerValidate;
import com.elevenst.terroir.product.registration.template.service.TemplateServiceImpl;
import com.elevenst.terroir.product.registration.template.vo.PrdInfoTmplVO;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skt.tmall.auth.Auth;
import skt.tmall.auth.AuthCode;
import skt.tmall.auth.AuthService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value="/product", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProductController {

    public static final String SOFFICE_DELIVERY_API_URL = "/api/11ed/product/registration/delivery";

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    ProductImageServiceImpl productImageService;

    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    ProductBenefitServiceImpl productBenefitService;

    @Autowired
    OptionServiceImpl optionService;

    @Autowired
    ProductDescServiceImpl productDescService;

    @Autowired
    CommonServiceImpl commonService;

    @Autowired
    EtcServiceImpl etcService;

    @Autowired
    CoordiProductServiceImpl coordiProductService;

    @Autowired
    CatalogServiceImpl catalogService;

    @Autowired
    PriceServiceImpl priceService;

    @Autowired
    CtgrAttrServiceImpl ctgrAttrService;

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    PropMgr propMgr;

    @Autowired
    ProductCertFactory productCertFactory;

    @Autowired
    CertService certService;

    @Autowired
    SellerValidate sellerValidate;

    @Autowired
    ProductValidate productValidate;

    @Autowired
    SOfficeProperties sOfficeProperties;

    @Autowired
    Gson gson;

    @Autowired
    TemplateServiceImpl templateService;

    @Autowired
    ObjectCacheImpl objectCache;

    @Autowired
    DeliveryServiceImpl deliveryService;

    @Autowired
    DBSwitchUtil dBSwitchUtil;

    @GetMapping("/base-prd-wght/{dispCtgr3No}")
    public ResponseEntity<Long> getBasePrdWght(@PathVariable long dispCtgr3No) {
        CategoryVO categoryVO = new CategoryVO();
        // 카테고리 정보 조회
        long dispCtgrNo = (dispCtgr3No <= 0 ? 0 : dispCtgr3No);  //소카테고리값
        long basePrdWght = 0;

        // 소카테고리 무게 조회
        if (dispCtgrNo > 0) {
            categoryVO = categoryService.getServiceDisplayCategoryInfo(dispCtgrNo);
            if(categoryVO != null) {
                basePrdWght = Long.parseLong(StringUtil.nvl(categoryVO.getGblDvlPrdWght(), "0"));
            }
        }
        return new ResponseEntity(basePrdWght, HttpStatus.OK);
    }

    @GetMapping("/groupcode/prd-stat-cd")
    public String getPrdStatCd() {

        return GroupCodeUtil.toJsonTypeString(PrdStatCd.class);

    }

    /**
     *  원산지 리스트 전달을 위한 controller
     *  입력 파라미터 :
     *      parentCd - SY_CO_DETAIL 테이블의 PARENT_CD 값. 비어있을 경우 PARENT_CD는 아무 값이나 상관없음.
     *
     *  출력 포멧 :
     *      [
     *           {"dtlsCd":"1001", "value":{"dtlsComNm":"강원", "parentCd":"01"}}
     *          ,{"dtlsCd":"1002", "value":{"dtlsComNm":"경기", "parentCd":"01"}}
     *          ,{"dtlsCd":"1003", "value":{"dtlsComNm":"경남", "parentCd":"01"}}
     *          ,{"dtlsCd":"1004", "value":{"dtlsComNm":"경북", "parentCd":"01"}}
     *          ,{"dtlsCd":"1005", "value":{"dtlsComNm":"광주", "parentCd":"01"}}
     *          ,{"dtlsCd":"1006", "value":{"dtlsComNm":"대구", "parentCd":"01"}}
     *          ,{"dtlsCd":"1007", "value":{"dtlsComNm":"대전", "parentCd":"01"}}
     *          ,{"dtlsCd":"1008", "value":{"dtlsComNm":"부산", "parentCd":"01"}}
     *          ,{"dtlsCd":"1009", "value":{"dtlsComNm":"서울", "parentCd":"01"}}
     *          ,{"dtlsCd":"1010", "value":{"dtlsComNm":"울산", "parentCd":"01"}}
     *      ]
     * */

    /**
     * 원산지 리스트 조회 API
     * @return 원산지 리스트, http status code 200
     * @author cwoongc
     */
    @ApiOperation(
        value = "원산지 리스트 코드(PD054) 목록 조회",
        notes = "원산지 리스트 코드(PD054) 목록 조회"
    )
    @ApiResponses(value = {
        @ApiResponse(code=200, message = "원산지 리스트 조회 성공")
    })
    @GetMapping("/orgn-typ-dtls-cd")
    public ResponseEntity<JSONArray> getOrgnTypDtlsCdList(@RequestParam(value="parentCd",required=false) String parentCd) {

        SyCoDetailVO syCoDetailVO = new SyCoDetailVO();
        syCoDetailVO.setGrpCd(ProductConstDef.PRD_ORGN_DTL_TYP_CD);
        syCoDetailVO.setParentCd(parentCd);

        List<SyCoDetailVO> codeList = commonService.getCodeDetail(syCoDetailVO);

        JSONArray result = new JSONArray();
        Map obj;
        Map value;

        for(SyCoDetailVO syCoDetail : codeList){
            obj = new HashMap();
            value = new HashMap();
            obj.put("dtlsCd", syCoDetail.getDtlsCd());
            value.put("dtlsComNm", syCoDetail.getDtlsComNm());
            value.put("parentCd", syCoDetail.getParentCd());
            obj.put("value", value);
            result.add(obj);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);

    }


    @GetMapping("/tagname/{searchTag}")
    public JSONObject getSearchTagName(@PathVariable("searchTag") String searchTag) {

        JSONObject result = new JSONObject();
        result.put("items", productService.getSearchTagName(searchTag));
        result.put("success", true);

        return result;
    }

    @GetMapping("/coordi-product/search-list/{start}/{limit}/{searchName}")
    public ResponseEntity <JSONObject> getCoordiProductSearchList(HttpServletRequest request, HttpServletResponse response,
                                                 @PathVariable("searchName") String searchName,
                                                 @PathVariable("start") long start,
                                                 @PathVariable("limit") long limit) {
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        List<ProductSearchVO> productVOList = new ArrayList<ProductSearchVO>();

        try {
            if (auth == null || !auth.isAuth()) throw new ProductException("로그인 후 사용하세요.");

            ProductSearchVO paramVO = new ProductSearchVO();

            paramVO.setStart(start);
            paramVO.setLimit(limit);

            paramVO.setSelMnbdNo(auth.getMemberNumber());
            paramVO.setBsnDealClf(auth.getBsnDealClf());
            paramVO.setStart(paramVO.getStart() + 1);
            paramVO.setEnd(paramVO.getStart() + paramVO.getLimit());
            paramVO.setStdPrdYn("Y");

            if(StringUtil.isNotEmpty(searchName)) {
                paramVO.setSearchType("PRDNM");
                searchName = URLDecoder.decode(searchName, "UTF-8");
                paramVO.setSearchName(searchName);
            }

            paramVO.setPrdNm(searchName);
            productVOList = productService.getSearchProductList(paramVO);

            if(productVOList.size() < 1) {
                return new ResponseEntity(null, HttpStatus.NO_CONTENT);
            }
        } catch (ProductException pex) {
            if (log.isDebugEnabled()) {
                log.debug("상품 조회 오류 :" + pex.getMessage());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(toJSONProductlist(productVOList), HttpStatus.OK);
    }

    private JSONObject toJSONProductlist(List<ProductSearchVO> productVOList) {
        JSONArray json = new JSONArray();
        JSONObject jsonObj = new JSONObject();

        if(!StringUtil.isEmpty(productVOList)) {

            for(ProductSearchVO product : productVOList){
                SyCoDetailVO codeDetailVO = new SyCoDetailVO();
                JSONObject subObj = new JSONObject();
                JSONObject obj = new JSONObject();

                codeDetailVO.setDtlsCd(product.getSelStatCd());
                codeDetailVO.setGrpCd(ProductConstDef.PRD_SEL_STAT_CD);

                codeDetailVO = commonService.getCodeDetailName(codeDetailVO);

                PdPrdImage pdPrdImage = productImageService.getProductImage(product.getPrdNo());
                String prdImg = "";
                if(null != pdPrdImage) {
                    prdImg = pdPrdImage.getBasicExtNm();
                }
                obj.put("prdNo"  , product.getPrdNo());
                subObj.put("prdNm", product.getPrdNm());
                subObj.put("prdImg", prdImg ); //path확인 필요
                subObj.put("selStatNm", codeDetailVO.getDtlsComNm());
                subObj.put("selStatCd", product.getSelStatCd());
                subObj.put("createDt"  , DateUtil.formatDate(product.getCreateDt(),"yyyy-MM-dd HH:mm:ss"));
                subObj.put("updateDt"  , DateUtil.formatDate(product.getUpdateDt(),"yyyy-MM-dd HH:mm:ss"));
                obj.put("desc",subObj);
                json.add(obj);
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("JSON : " + json);
        }

        jsonObj.put("result", json);
        return jsonObj;

    }

    /**
     * 원재료 유형 코드 목록 조회 API
     * @return 원재료 유형 코드 목록, http status code 200
     * @author cwoongc
     */
    @ApiOperation(
            value = "원재료 유형 코드(PD098) 목록 조회",
            notes = "원재료 유형 코드(PD098) 목록 조회",
            response = GroupCodeVO.class
    )
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "원재료 유형 코드 목록 조회 성공")
    })
    @GetMapping("/rmaterial-typ-cd")
    public ResponseEntity<GroupCodeVO> getRmaterialTypCd() {

        GroupCodeVO groupCodeVO = new GroupCodeVO();

        groupCodeVO.setGroupCode(RmaterialTypCd.FARM_PRODUCT.groupCode);
        groupCodeVO.setGroupCodeName(RmaterialTypCd.FARM_PRODUCT.groupCodeName);
        groupCodeVO.setCodes(new LinkedList<CodeVO>());

        for(RmaterialTypCd cd : RmaterialTypCd.values()) {
            CodeVO codeVO = new CodeVO();
            codeVO.setCode(cd.code);
            codeVO.setCodeName(cd.codeName);
            groupCodeVO.getCodes().add(codeVO);
        }

        return new ResponseEntity<>(groupCodeVO, HttpStatus.OK);
    }

    @ApiOperation(value = "상품정보 정책 (카테고리별 상품명 길이) 정보 조회",
                  notes = "상품정보 정책 (카테고리별 상품명 길이) 정보를 조회한다.",
                  response = JSONObject.class)
    @GetMapping("/limit-info/{dispCtgrNo}")
    public ResponseEntity<JSONObject> getProductLimitInfo(@PathVariable("dispCtgrNo") long dispCtgrNo) {
        if(dispCtgrNo == 0) return null;

        ProductOptLimitVO paramBO = new ProductOptLimitVO();
        paramBO.setOptLmtObjNo(dispCtgrNo);

        ProductOptLimitVO limitBO = productService.getProductLimitInfo(paramBO);

        JSONObject result = new JSONObject();
        long prdNmLenLmt = limitBO.getPrdNmLenLmtDefault();
        /*
        result.put("prdNmLenLmt", limitBO.getPrdNmLenLmt());
        result.put("prdNmLenLmtDefault", limitBO.getPrdNmLenLmtDefault());
        */
        if(limitBO != null && limitBO.getPrdNmLenLmt() > 0) {
            prdNmLenLmt = limitBO.getPrdNmLenLmt();
        }
        result.put("prdNmLenLmt", prdNmLenLmt);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @ApiOperation(value = "서비스상품 콤보 아이템 목록 정보 조회",
                  notes = "서비스상품 콤보 아이템 목록 정보를 조회한다.",
                  response = List.class)
    @GetMapping("/service-list/{dispCtgrNo}")
    public ResponseEntity<List<Map>> getServiceProductComboDataList(HttpServletRequest request, HttpServletResponse response, @PathVariable("dispCtgrNo") long dispCtgrNo, @PathVariable("urlType") String urlType) {
        long selMnbdNo = 0;
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if (auth != null)  {
            selMnbdNo = auth.getMemberNumber();
        }
        HashMap pMap = new HashMap();
        pMap.put("dispCtgrNo", dispCtgrNo);
        pMap.put("memNo", selMnbdNo);
        pMap.put("urlType", urlType);

        return new ResponseEntity(productService.getProductTypeInfoList(pMap), HttpStatus.OK);
    }

    @ApiOperation(value = "글로벌 상품명 정보 조회",
                  notes = "글로벌 상품명 정보를 조회한다.",
                  response = ProductNameMultiLangVO.class)
    @GetMapping("/global-prdnm/{prdNo}")
    public ResponseEntity<ProductNameMultiLangVO> getProductNameMultiLang(@PathVariable("prdNo") long prdNo) {
        ProductNameMultiLangVO productNameMultiLangVO = new ProductNameMultiLangVO();
        productNameMultiLangVO.setPrdNo(prdNo);
        return new ResponseEntity(productService.getProductNameMultiLang(productNameMultiLangVO), HttpStatus.OK);
    }

    @ApiOperation(value = "특수문자 정보 조회",
                  notes = "특수문자 정보를 조회한다.",
                  response = String.class)
    @GetMapping("/special-char")
    public ResponseEntity<String> getInputLimitedSpecialCharacters() {
        return new ResponseEntity(propMgr.get1hourTimeProperty(CacheKeyConstDef.PD_INPUT_LIMITED_SPECIAL_CHARACTERS), HttpStatus.OK);
    }

    @ApiOperation(value = "임시저장 정보 등록/수정",
                  notes = "임시저장 정보를 등록/수정한다.",
                  response = String.class)
    @PostMapping("/merge-tempsave/{tempSaveSeq}")
    public ResponseEntity<String> mergePdPrdTempSave(HttpServletRequest request, HttpServletResponse response, @PathVariable("tempSaveSeq") long tempSaveSeq, @RequestBody String tempSaveJson) {
        long selMnbdNo = 0;
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if (auth != null)  {
            selMnbdNo = auth.getMemberNumber();
        }

        PdPrdTempSave pdPrdTempSave = new PdPrdTempSave();
        pdPrdTempSave.setTempSaveJson(tempSaveJson);
        pdPrdTempSave.setSellerNo(selMnbdNo);
        pdPrdTempSave.setUpdateNo(selMnbdNo);

        if(tempSaveSeq > 0) {
            pdPrdTempSave.setTempSaveSeq(tempSaveSeq);
            //update
            productService.updatePdPrdTempSave(pdPrdTempSave);
        } else {
            //insert
            pdPrdTempSave.setCreateNo(selMnbdNo);
            productService.insertPdPrdTempSave(pdPrdTempSave);
        }
        JSONObject result = new JSONObject();
        result.put("success", "success");
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @ApiOperation(value = "임시저장 정보 삭제",
                  notes = "임시저장 정보를 삭제한다.",
                  response = String.class)
    @DeleteMapping("/delete-tempsave/{tempSaveSeq}")
    public ResponseEntity<String> deletePdPrdTempSave(HttpServletRequest request, HttpServletResponse response, @PathVariable("tempSaveSeq") long tempSaveSeq) {
        long selMnbdNo = 0;
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if (auth != null)  {
            selMnbdNo = auth.getMemberNumber();
        }
        PdPrdTempSave pdPrdTempSave = new PdPrdTempSave();
        pdPrdTempSave.setTempSaveSeq(tempSaveSeq);
        pdPrdTempSave.setSellerNo(selMnbdNo);
        productService.deletePdPrdTempSave(pdPrdTempSave);

        JSONObject result = new JSONObject();
        result.put("success", "success");
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @ApiOperation(value = "임시저장 정보 조회",
                  notes = "임시저장 정보를 조회한다.",
                  response = List.class)
    @GetMapping("/select-tempsave")
    public ResponseEntity<List> selectPdPrdTempSave(HttpServletRequest request, HttpServletResponse response) {
        long selMnbdNo = 0;
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if (auth != null)  {
            selMnbdNo = auth.getMemberNumber();
        }
        PdPrdTempSave pdPrdTempSave = new PdPrdTempSave();
        pdPrdTempSave.setSellerNo(selMnbdNo);
        return new ResponseEntity(productService.selectPdPrdTempSave(pdPrdTempSave), HttpStatus.OK);
    }

    /**
     * 상품등록/수정 페이지 및 Action 처리 완료 시 저장되는 시간 값
     * @param productLdngTmGson
     * @throws Exception
     */
    @PostMapping("/insert-loadtime")
    public void insertLoadTimeData(@RequestBody ProductLdngTmGson productLdngTmGson, Auth auth) throws Exception {
        dBSwitchUtil.addKey(CacheKeyConstDef.SWITCH_PRD_LDNG_TM);
        dBSwitchUtil.setUseCache(true);
        dBSwitchUtil.checkKey();
        if(dBSwitchUtil.isExist(CacheKeyConstDef.SWITCH_PRD_LDNG_TM)) {
            long selMnbdNo = 0;
            if (auth != null)  {
                selMnbdNo = auth.getMemberNumber();
            }
            if (selMnbdNo == 0) throw new Exception(ProductExceptionMessage.AUTH_ERROR);

            ProductLdngTmVO productLdngTmBO = new ProductLdngTmVO();
            //요일 구분
            productLdngTmBO.setPcId(auth.getPCID());
            productLdngTmBO.setMemNo(selMnbdNo);
            productLdngTmBO.setProcTyp(productLdngTmGson.getProcTyp());
            productLdngTmBO.setBrowTyp(productLdngTmGson.getBrowTyp());
            productLdngTmBO.setLdngTm(productLdngTmGson.getLdngTm());
            productLdngTmBO.setCreateDt(new Date());

            productService.insertProductLdngTmInfo(productLdngTmBO);
        }
    }


    /**
     *  카테고리 선택 시, 상품인증 종류를 가져오는 API
     *  AS-IS I/F : http://soffice.11st.co.kr/product/ProductRegAjax.tmall?method=getProductCertInfo&level=3&dispCtgrNo=1006658&useCache=true
     *      실제 AS-IS I/F에서는 카테고리번호(캐쉬유무)를 이용하고 level파라미터는 사용하지 않으므로 TO-BE에서는 이를 생략함.
     *
     *  응답 포멧은 AS-IS와 되도록이면 비슷한 형태.
     *      AS-IS I/F의 응답 root key인 CERT_INFO 만 'result'로 변경
     *      나머지 값은 모두 대문자가 아닌 camel case 표기법 이용.
     *  응답 포멧 예시 :
     *
     *   {
     *      "result": [
     *          {
     *              "certNm": "[생활용품] 안전인증",
     *              "dtlsCd": "101",
     *              "kcMarkYn": "Y",
     *              "dispCtgrNo": 1001311,
     *              "certGroup": "2",
     *              "kcMarkColor": "G"
     *          },
     *          {
     *              "certNm": "[생활용품] 안전확인",
     *              "dtlsCd": "103",
     *              "kcMarkYn": "Y",
     *              "dispCtgrNo": 1001311,
     *              "certGroup": "2",
     *              "kcMarkColor": "N"
     *          },
     *          {
     *              "certNm": "[생활용품] 공급자적합성확인",
     *              "dtlsCd": "132",
     *              "kcMarkYn": "N",
     *              "dispCtgrNo": 1001311,
     *              "certGroup": "2",
     *              "kcMarkColor": null
     *          }
     *      ],
     *      "certInfoRequiredYn": "N"
     *   }
     *
     *
     * */
    @ApiOperation(
            value = "상품 전시 카테고리별 상품인증 유형 목록 조회",
            notes = "상품 전시 카테고리별 상품인증 유형 목록 조회",
            response = Map.class
    )
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "상품인증 유형 목록 조회 성공")
    })
    @GetMapping("/cert-select-item/{dispCtgrNo}")
    public ResponseEntity<Map<String, Object>> getCertSelectItems(@ApiParam("상품 전시 카테고리 번호") @PathVariable("dispCtgrNo") long dispCtgrNo) {

//        String mockupResult = "{result: [{certNm: \"[생활용품] 안전인증\",dtlsCd: \"101\",kcMarkYn: \"Y\",dispCtgrNo: 1001311,certGroup: \"2\"," +
//            "kcMarkColor: \"G\"},{certNm: \"[생활용품] 안전확인\",dtlsCd: \"103\",kcMarkYn: \"Y\",dispCtgrNo: 1001311,certGroup: \"2\"," +
//            "kcMarkColor: \"N\"},{certNm: \"[생활용품] 공급자적합성확인\",dtlsCd: \"124\",kcMarkYn: \"Y\",dispCtgrNo: 1001311,certGroup: \"2\"," +
//            "kcMarkColor: \"N\"},{certNm: \"[생활용품] 어린이보호포장\",dtlsCd: \"123\",kcMarkYn: \"Y\",dispCtgrNo: 1001311,certGroup: \"2\"," +
//            "kcMarkColor: \"N\"},{certNm: \"[전기용품] 안전인증\",dtlsCd: \"102\",kcMarkYn: \"Y\",dispCtgrNo: 1001311,certGroup: \"2\"," +
//            "kcMarkColor: \"B\"},{certNm: \"[전기용품] 안전확인\",dtlsCd: \"104\",kcMarkYn: \"Y\",dispCtgrNo: 1001311,certGroup: \"2\"," +
//            "kcMarkColor: \"B\"},{certNm: \"[전기용품] 공급자적합성확인\",dtlsCd: \"127\",kcMarkYn: \"Y\",dispCtgrNo: 1001311,certGroup: \"2\"," +
//            "kcMarkColor: \"B\"},{certNm: \"[방송통신기자재] 적합성평가 (적합인증 적합등록)\",dtlsCd: \"105\",kcMarkYn: \"Y\",dispCtgrNo: 1001311,certGroup: " +
//            "\"2\",kcMarkColor: \"N\"},{certNm: \"[어린이제품] 안전인증\",dtlsCd: \"128\",kcMarkYn: \"Y\",dispCtgrNo: 1001311,certGroup: \"2\"," +
//            "kcMarkColor: \"G\"},{certNm: \"[어린이제품] 안전확인\",dtlsCd: \"129\",kcMarkYn: \"Y\",dispCtgrNo: 1001311,certGroup: \"2\"," +
//            "kcMarkColor: \"N\"},{certNm: \"[어린이제품] 공급자적합성확인\",dtlsCd: \"130\",kcMarkYn: \"Y\",dispCtgrNo: 1001311,certGroup: \"2\"," +
//            "kcMarkColor: \"N\"},{certNm: \"[위해우려제품]자가검사번호\",dtlsCd: \"133\",kcMarkYn: \"N\",dispCtgrNo: 1001311,certGroup: \"2\"," +
//            "kcMarkColor: \"\"},{certNm: \"해당없음(대상이 아닌 경우)\",dtlsCd: \"131\",kcMarkYn: \"N\",dispCtgrNo: 1001311,certGroup: \"2\"," +
//            "kcMarkColor: \"\"},{certNm: \"상품상세설명 참조\",dtlsCd: \"132\",kcMarkYn: \"N\",dispCtgrNo: 1001311,certGroup: \"2\",kcMarkColor: " +
//            "\"\"}],\"CERT_INFO_REQUIRED_YN\": \"N\"}";
//
//        String test = "{result:\"hello\"}";
//        JSONObject result = JSONObject.fromObject(mockupResult);

        Map<String, Object> model = null;

        try {

            model = certService.getCertSelectItems(dispCtgrNo);

        } catch (Exception ex) {
        ProductControllerException threx = new ProductControllerException(ProductControllerExceptionMessage.PRODUCT_CERT_TYPE_LIST_GET_ERROR, ex);
        if (log.isErrorEnabled()) {
            log.error(threx.getMessage(), threx);
        }
        throw threx;
    }

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     *  상품 인증영역 boolean API
     *  인증유형과 인증값을 수신받아, 인증조건에 맞는지 아닌지 결과를 return하여 주는 동작을 함.
     *  request type
     *      certType : 인증유형(PD065)
     *      certKey : 페이지에서 입력한 인증키값.
     *
     *  response type은 다음과 같이 전달 부탁드립니다(간단한 API라 AS-IS SO spec과 동일하게 하려 합니다)
     *      { "certInfoAuthYn": true/false }
     * */
    @ApiOperation(
            value = "상품인증정보 유효성 확인",
            notes = "외부 상품인증정보 서비스를 이용하여 상품인증정보 유효성 확인",
            response = Map.class
    )
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "상품인증정보 유효"),
            @ApiResponse(code=404, message = "찾을 수 없는 상품인증정보")
    })
    @GetMapping("/cert-auth-yn/{certType}/{certKey}")
    public ResponseEntity<Map<String,Boolean>> getCertInfoAuthYn(
        @ApiParam("상품인증유형코드") @PathVariable("certType") String certType
        ,@ApiParam("상품인증키") @PathVariable("certKey") String certKey) {

        ProductCertValidApiService service = productCertFactory.createService(certType, certKey);

        boolean certInfoAuthYn = service.doRequestApi(certType, certKey);

//        boolean resultRandom = Math.random() < 0.5;
//        JSONObject result = JSONObject.fromObject("{certInfoAuthYn : " + resultRandom + "}");
//        return result;

        Map<String, Boolean> map = new HashMap<>();
        map.put("certInfoAuthYn", certInfoAuthYn);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     *  인증 상세내용 호출 API
     *      AS-IS URL 샘플 : http://soffice.11st.co.kr/product/ProductReg.tmall?method=regFormCertInfoRRAResult&certType=105&certKey=NXT-NEXT-4PHUB
     *              AS-IS URL이 실제로 호출하는 3rd party URL : http://emsip.go.kr/openapi/service/AuthenticationInfoService/getAuthInfo.do?mtlCefNo=NXT-NEXT-4PHUB
     *              front단에서 직접 호출하고 싶으나, cross domain이슈 및, XML로 응답이 와서 backend영역에서 forwarding이 필요합니다.
     *
     *
     * API Spec
     *      AS-IS는 jsp페이지 (03.tMallSellerToolProject/webapps/jsp/product/mgr/productRegCertInfoPopRRAResult.jsp)
     *      를 보여주었으나, TO-BE는 Ajax API로 페이지 데이터가 아닌 메타정보를 받아 front단에서 값 노출.
     *
     *      request type
     *          certType : 인증유형
     *          certKey : 인증키
     *
     *      response type
     *          AS-IS의 jsp에 존재하는 HTML 내 tbody 안의 스크립틀릿 값만 JSON 형식으로 주시면 됩니다.
     *              스크립틀릿 변수명 : JSON Object key
     *              스크립틀릿 실제 값 : JSON Object value
     *          예) productRegCertInfoPopRRAResult.jsp 내의
     *             <tr> <th scope="row">상호</th> <td><%=bsmNm %></td> </tr>
     *             값은
     *             {"bsmNm" : "(주) 이지넷유비쿼터스"}
     *             와 같은 형식으로...
     *
     *
     *      response body 예제
     *          {
     *               bsmNm : "(주) 이지넷유비쿼터스"
     *              ,mtlNm : "USB HUB"
     *              ,matlBscMdlNm : "NEXT-4PHUB,NEXT-304UHP,NEXT-304UH"
     *              ,matlDerivMdlNm : ""
     *              ,mtlCefNo : "NXT-NEXT-4PHUB"
     *              ,matlMfrNm : "SHENZHEN YICHEN TECHNOLOGY DEVELOPMENT CO.,LTD."
     *              ,dtlInfCdNm : "중국"
     *              ,cvaPcsYmd : "2006-01-26"
     *              ,matlEtcMtr : ""
     *           }
     *
     * */
    @ApiOperation(
            value = "상품인증정보 조회",
            notes = "외부 상품인증정보 서비스를 이용하여 상품인증 정보를 조회" ,
            response = Map.class
    )
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "상품인증정보 조회 성공"),
            @ApiResponse(code=404, message = "찾을 수 없는 상품인증정보"),
            @ApiResponse(code=503, message = "상품인증정보 조회 서비스를 이용할 수 없습니다. 제공할수 없는 인증유형이거나, 외부 상품인증정보 서버가 응답하지 않습니다.")
    })
    @GetMapping("/cert-detail-info/{certType}/{certKey}")
    public ResponseEntity<Map<String,Object>> getCertDetailInfo(
            @ApiParam("상품인증유형코드")  @PathVariable("certType") String certType
            ,@ApiParam("상품인증키") @PathVariable("certKey") String certKey) {

        ProductCertValidApiService service = productCertFactory.createService(certType, certKey);

        Map<String, Object> certInfo = service.getCertInfo(certType, certKey);

//        String resultMockUpData = "{bsmNm : \"(주) 이지넷유비쿼터스\",mtlNm : \"USB HUB\",matlBscMdlNm : \"NEXT-4PHUB,NEXT-304UHP,NEXT-304UH\"," +
//            "matlDerivMdlNm : \"\",mtlCefNo : \"NXT-NEXT-4PHUB\",matlMfrNm : \"SHENZHEN YICHEN TECHNOLOGY DEVELOPMENT CO.,LTD.\"," +
//            "dtlInfCdNm : \"중국\",cvaPcsYmd : \"2006-01-26\",matlEtcMtr : \"\"}";
//        return JSONObject.fromObject(resultMockUpData);

        ResponseEntity<Map<String,Object>> re = null;
        if(certInfo == null) {
            re = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        } else if(service instanceof ProductElectroCertValidApiService && certInfo.containsKey("resultCode")) {
            if(certInfo.get("resultCode").equals("0000")) re = new ResponseEntity<>(certInfo, HttpStatus.OK);

            if(certInfo.get("resultCode").equals("0001")) re = new ResponseEntity<>(certInfo, HttpStatus.NOT_FOUND);


            certInfo.remove("resultCode");
            certInfo.remove("resultMsg");
        }

        return re;

    }

    @ApiOperation(value = "상품명/카테고리 영역 정보 조회",
                  notes = "상품수정 시 상품명/카테고리 영역 정보를 조회한다.",
                  response = JSONObject.class)
    @GetMapping("/prd-category/{prdNo}")
    public ResponseEntity<JSONObject> getPrdCategoryArea(HttpServletRequest request, HttpServletResponse response, @PathVariable("prdNo") long prdNo) {
        long selMnbdNo = 0;
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if(auth != null) {
            selMnbdNo = auth.getMemberNumber();
        }
        if (selMnbdNo == 0) throw new ProductException(ProductExceptionMessage.AUTH_ERROR);

        JSONObject jsonObject = new JSONObject();

        ProductVO productVO = productService.getProduct(prdNo);
        String sellerPrdMsg = productValidate.checkProductMnbdInfo(auth, productVO.getSelMnbdNo());
        if(StringUtil.isNotEmpty(sellerPrdMsg)) {
            throw new ProductException(sellerPrdMsg);
        }

        // 모델명 (김영석M)
        // /product/prd-model-info/{prdNo}에서 정보 가져올수 있음. (this.getPrdModelInfo())

        //브랜드 (정승식M)
        jsonObject.put("ctgrAttrVO", ctgrAttrService.getPrdBrandInfo(prdNo));

        //상품명/홍보문구/카테고리번호/서비스유형/판매방식/상품상태/판매기간/판매가/상품리뷰 (김영석M)
        // selTermDy : selPrdClfCd의 value
        // selTemUseYn : selprdClfCd is not null일 경우에만 Y
        productVO.getBaseVO().setSelTermUseYn("N");
        SyCoDetailVO syCoDetailVO = commonService.getCodeDetail(SelPrdClfCd.FIXED_MANUAL_INPUT.getGrpCd() , productVO.getBaseVO().getSelPrdClfCd());
        if(syCoDetailVO != null) {
            productVO.getBaseVO().setSelTermDy(syCoDetailVO.getCdVal1());
        }
        if(!ProductConstDef.PRD_SEL_PRD_CLF_CD_NOT_USE_END_DT.equals(productVO.getBaseVO().getSelEndDy()) && StringUtil.isNotEmpty(productVO.getBaseVO().getSelPrdClfCd())) {
            productVO.getBaseVO().setSelTermUseYn("Y");
        }

        String useOrgnDtlYn = "N";
        if (StringUtil.isNotEmpty(productVO.getBaseVO().getOrgnTypCd())) {
            SyCoDetailVO orgnTypDtlsData = commonService.getCodeDetail(ProductConstDef.PRD_ORGN_DTL_TYP_CD, productVO.getBaseVO().getOrgnTypDtlsCd());
            productVO.getBaseVO().setOrgnTypDtlsCd(productVO.getBaseVO().getOrgnTypDtlsCd());
            if(orgnTypDtlsData != null) {
                productVO.getBaseVO().setOrgnTypDtlsCd1(orgnTypDtlsData.getParentCd());
                useOrgnDtlYn = "Y";
            }
        }
        productVO.getBaseVO().setUseOrgnDtlYn(useOrgnDtlYn);

        // 축산물 이력번호 (김영석M)
        PrdOthersVO prdOthersVO = etcService.getBeefTraceInfo(prdNo);
        String beefTraceNo = "";
        String beefTraceStat = "";
        if(prdOthersVO != null && !"".equals(prdOthersVO.getBeefTraceNo())) {
            beefTraceNo = prdOthersVO.getBeefTraceNo();
            beefTraceStat = "01";
            if("이력번호표시대상아님".equals(beefTraceNo)) {
                beefTraceStat = "02";
            } else if("상세설명 참조".equals(beefTraceNo)) {
                beefTraceStat = "03";
            }
        }
        productVO.getPrdOthersVO().setBeefTraceNo(beefTraceNo);
        productVO.getPrdOthersVO().setBeefTraceStat(beefTraceStat);
        
        // 제주/도서산간 정보 세팅
        String islandUseYn = "N";
        if(productVO.getBaseVO().getJejuDlvCst() > 0 || productVO.getBaseVO().getIslandDlvCst() > 0) {
            islandUseYn = "Y";
        }
        productVO.getBaseVO().setIslandUseYn(islandUseYn);

        jsonObject.put("productVO", productVO);

        //최근등록한 카테고리 (김영석M)
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setSelMnbdNo(selMnbdNo);
        jsonObject.put("recentCategoryList", categoryService.getRecentProductGeneralDisplay(categoryVO));

        //상품속성정보 (정승식M) - 디지털 > 카메라/주변기기 > 미러리스카메라 > 미러리스카메라 선택 시 출력되는 영역
        // CatalogAttrController.getPrdCtlgAttrVal() - /catalog-attr/getPrdCtlgAttrVal/{prdNo} 참고

        return new ResponseEntity(jsonObject, HttpStatus.OK);
    }

    @ApiOperation(value = "모델명/모델코드 정보 조회",
                  notes = "상품수정 시 모델명/모델코드 정보를 조회한다.",
                  response = JSONObject.class)
    @GetMapping("/prd-model-info/{prdNo}")
    public ResponseEntity<JSONObject> getPrdModelInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable("prdNo") long prdNo) {
        String sellerPrdMsg = productValidate.checkProductMnbdInfoByPrdNo(AuthService.getAuth(request, response, AuthCode.TMALL_AUTH), prdNo);
        if(StringUtil.isNotEmpty(sellerPrdMsg)) {
            throw new ProductException(sellerPrdMsg);
        }

        JSONObject jsonObject = new JSONObject();

        //모델명/모델코드 (김영석M)
        JSONObject modelObject = new JSONObject();
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("prdNo", prdNo);
        List modelList = catalogService.getProductCtlgModelInfo(paramMap);
        if(modelList != null && modelList.size() > 0) {
            HashMap modelMap = (HashMap)modelList.get(0);
            modelObject.put("modelNo", modelMap.get("MODEL_NO"));
            modelObject.put("ctlgNo", Integer.parseInt(StringUtil.nvl(modelMap.get("CTLG_NO"), "0")));
            modelObject.put("ctlgGrpNo", Integer.parseInt(StringUtil.nvl(modelMap.get("CTLG_GRP_NO"), "0")));
            modelObject.put("ctlgStatCd", StringUtil.nvl(modelMap.get("CTLG_STAT_CD")));
            modelObject.put("ctlgTypCd", StringUtil.nvl(modelMap.get("CTLG_TYP_CD")));
            modelObject.put("modelCdVal", StringUtil.nvl(modelMap.get("MODEL_CD_VAL")));
            modelObject.put("modelNm", StringUtil.nvl(modelMap.get("MODEL_NM")));
            modelObject.put("minPrice", Integer.parseInt(StringUtil.nvl(modelMap.get("MIN_PRICE"), "0")));
            //modelObject.put("brandInfo", StringUtil.nvl(modelMap.get("BRAND_NAME")));

            //브랜드 정보는 상품의 속성에서 조회함
            CtgrAttrVO brandCtgrAttrVO = ctgrAttrService.getPrdBrandInfo(prdNo);
            if(brandCtgrAttrVO!=null && brandCtgrAttrVO.getPrdAttrValueCompValueNm() !=null && !brandCtgrAttrVO.getPrdAttrValueCompValueNm().equals("")) {
                modelObject.put("brandInfo", ctgrAttrService.getPrdBrandInfo(prdNo).getPrdAttrValueCompValueNm());
            }else {
                //상품 속성정보에 브랜드가 없을 경우
                modelObject.put("brandInfo", StringUtil.nvl(modelMap.get("BRAND_NAME")));
            }

            modelObject.put("makerInfo", StringUtil.nvl(modelMap.get("MAKER_NAME")));
            modelObject.put("ctlgGrpNm", StringUtil.nvl(modelMap.get("CTLG_GRP_NM")));
            modelObject.put("modelSpecSummury",	StringUtil.nvl(modelMap.get("MODEL_SPEC_SUMMURY")));
            modelObject.put("imgUrl", StringUtil.nvl(modelMap.get("IMG_URL")));
            modelObject.put("brandCd", StringUtil.nvl(modelMap.get("BRAND_CD")));
            modelObject.put("mnbdClfCd", StringUtil.nvl(modelMap.get("MNBD_CLF_CD")));
            modelObject.put("prdMatchCnt", Integer.parseInt(StringUtil.nvl(modelMap.get("PRD_MATCH_CNT"), "0")));
        }
        jsonObject.put("prdModelVO", modelObject);

        //브랜드 정보가 없으면 /ctgr-attr/info/{dispCtgrNo}/{infoTypeCtgrNo} 결과의 prdAttrValueCompValueNm 값 참조
        return new ResponseEntity(jsonObject, HttpStatus.OK);
    }

    @ApiOperation(value = "판매방식 영역 정보 조회",
                  notes = "상품수정 시 판매방식 영역 정보를 조회한다.",
                  response = JSONObject.class)
    //@GetMapping("/prd-type/{prdNo}")
    public ResponseEntity<JSONObject> getPrdTypArea(@PathVariable("prdNo") long prdNo) {
        JSONObject jsonObject = new JSONObject();

        // 서비스유형 (김영석M)
        // this.getPrdCategoryArea()에 있는 productVO 내 prdTypCd 참조

        // 판매방식 (김영석M)
        // this.getPrdCategoryArea()에 있는 productVO 내 selMthdCd 참조

        // 상품상태 (김영석M)
        // this.getPrdCategoryArea()에 있는 productVO 내 prdStatCd 참조

        // 판매기간 (김영석M)
        // this.getPrdCategoryArea()에 있는 productVO 내 selPrdClfCd 참조

        return new ResponseEntity(jsonObject, HttpStatus.OK);
    }

    @ApiOperation(value = "판매/옵션 영역 정보 조회",
                  notes = "상품수정 시 판매/옵션 영역 정보를 조회한다.",
                  response = JSONObject.class)
    @GetMapping("/sell-option/{prdNo}/{dispCtgrNo}")
    public ResponseEntity<JSONObject> getSellOptionArea(HttpServletRequest request, HttpServletResponse response, @PathVariable("prdNo") long prdNo, @PathVariable("dispCtgrNo") long dispCtgrNo) {
        long selMnbdNo = 0;
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if(auth != null) {
            selMnbdNo = auth.getMemberNumber();
        }
        if (selMnbdNo == 0) throw new ProductException(ProductExceptionMessage.AUTH_ERROR);

        String sellerPrdMsg = productValidate.checkProductMnbdInfoByPrdNo(auth, prdNo);
        if(StringUtil.isNotEmpty(sellerPrdMsg)) {
            throw new ProductException(sellerPrdMsg);
        }

        JSONObject jsonObject = new JSONObject();

        // 판매가 (김영석M)
        // this.getPrdCategoryArea()에 있는 productVO 내 selPrc 참조

        //기본즉시할인 (김영석M)
        jsonObject.put("priceVO", priceService.getCurrentDirectPrdCoupon(prdNo));

        //판매옵션 (김영석M)
        StandardOptionGroupMappingVO standardOptionGroupMappingVO = new StandardOptionGroupMappingVO();
        standardOptionGroupMappingVO.setDispCtgrNo(dispCtgrNo);
        standardOptionGroupMappingVO.setPrdNo(prdNo);

        StandardOptionInfoVO retStandardOptionInfoVO = optionService.getOptInfoListAtStdWithPrd(standardOptionGroupMappingVO);
        if(CollectionUtils.isNotEmpty(retStandardOptionInfoVO.getStdOptItemList())){
            jsonObject.put("optItemList", retStandardOptionInfoVO.getStdOptItemList());
        } else {
            jsonObject.put("optItemList", new ArrayList<StandardOptionItemVO>());
        }
        if(CollectionUtils.isNotEmpty(retStandardOptionInfoVO.getPrdOptItemList())){
            jsonObject.put("prdOptItemList", retStandardOptionInfoVO.getPrdOptItemList());
        } else {
            jsonObject.put("prdOptItemList", new ArrayList<PdOptItemVO>());
        }
        if(CollectionUtils.isNotEmpty(retStandardOptionInfoVO.getPrdOptCustList())){
            jsonObject.put("prdOptCustList", retStandardOptionInfoVO.getPrdOptCustList());
        } else {
            jsonObject.put("prdOptCustList", new ArrayList<PdOptItemVO>());
        }

        // 옵션 조합정보 리스트 (김영석M) - 재고수량은 해당 list의 재고수량을 합한 값 사용
        ProductStockVO productStockVO = new ProductStockVO();
        productStockVO.setPrdNo(prdNo);
        if(sellerValidate.isCertStockCdSeller(selMnbdNo)) {
            productStockVO.setPtnrPrdSellerYN("Y");
        }
        jsonObject.put("prdStockList", optionService.getProductStockLst(productStockVO));

        // 선택형 옵션수 (김영석M)
        HashMap param = new HashMap();
        param.put("prdNo", prdNo);
        jsonObject.put("selOptCnt", optionService.getPdStockExistCnt(param));

        // 옵션 존재여부 확인 (김영석M)
        jsonObject.put("optExist", optionService.isExistOptionOrAddPrduct(prdNo, "1"));		// 옵션 아이템 존재 여부 조회

        return new ResponseEntity(jsonObject, HttpStatus.OK);
    }

    @ApiOperation(value = "상품이미지/상세설명 영역 정보 조회",
                  notes = "상품수정 시 상품이미지/상세설명 영역 정보를 조회한다.",
                  response = JSONObject.class)
    @GetMapping("/prd-detail-image/{prdNo}")
    public ResponseEntity<JSONObject> getPrdDetailAndImageArea(HttpServletRequest request, HttpServletResponse response, @PathVariable("prdNo") long prdNo) {
        String sellerPrdMsg = productValidate.checkProductMnbdInfoByPrdNo(AuthService.getAuth(request, response, AuthCode.TMALL_AUTH), prdNo);
        if(StringUtil.isNotEmpty(sellerPrdMsg)) {
            throw new ProductException(sellerPrdMsg);
        }

        JSONObject jsonObject = new JSONObject();

        // 상품이미지정보 (김영석M)
        PdPrdImage pdPrdImage = productImageService.getProductImage(prdNo);

        ProductImageVO productImageVO = new ProductImageVO();
        if(pdPrdImage != null) {
            List<ProductImageVO.ImageFile> setImgFiles = new ArrayList<ProductImageVO.ImageFile>();

            for(ImageKind imageKind : ImageKind.values()) {
                ProductImageVO.ImageFile setImageFile = new ProductImageVO.ImageFile();
                setImageFile.setImageKind(imageKind);
                if(imageKind.name().equals(imageKind.B.name()) && StringUtil.isNotEmpty(pdPrdImage.getDtlBasicExtNm())) {
                    setImageFile.setTempPath(pdPrdImage.getDtlBasicExtNm());
                } else if(imageKind.name().equals(imageKind.A1.name()) && StringUtil.isNotEmpty(pdPrdImage.getAdd1ExtNm())) {
                    setImageFile.setTempPath(pdPrdImage.getAdd1ExtNm());
                } else if(imageKind.name().equals(imageKind.A2.name()) && StringUtil.isNotEmpty(pdPrdImage.getAdd2ExtNm())) {
                    setImageFile.setTempPath(pdPrdImage.getAdd2ExtNm());
                } else if(imageKind.name().equals(imageKind.A3.name()) && StringUtil.isNotEmpty(pdPrdImage.getAdd3ExtNm())) {
                    setImageFile.setTempPath(pdPrdImage.getAdd3ExtNm());
                } else if(imageKind.name().equals(imageKind.L300.name()) && StringUtil.isNotEmpty(pdPrdImage.getPngExtNm())) {
                    setImageFile.setTempPath(pdPrdImage.getPngExtNm());
                }

                if(StringUtil.isNotEmpty(setImageFile.getTempPath())) {
                    setImgFiles.add(setImageFile);
                }
            }
            productImageVO.setImgFiles(setImgFiles);
        }
        JSONObject imageSubJsonObject = new JSONObject();
        imageSubJsonObject.put("imgFiles", productImageVO.getImgFiles());
        jsonObject.put("productImageVO", imageSubJsonObject);

        JSONArray productDescArrayObject = new JSONArray();
        // 상품설명 (최웅철M)
        productDescArrayObject.add(setDescInfo(PrdDescTypCd.DETAIL.code, ProductDescVO.convertFromPdPrdDesc(productDescService.getProductDetailCont(prdNo))));

        // A/S안내 (김영석M)
        productDescArrayObject.add(setDescInfo(PrdDescTypCd.AS_INFO.code, productDescService.getProductDesc(prdNo, PrdDescTypCd.AS_INFO.code)));

        // 반품/교환 안내 (김영석M)
        productDescArrayObject.add(setDescInfo(PrdDescTypCd.RETURN_EXCHANGE_INFO.code, productDescService.getProductDesc(prdNo, PrdDescTypCd.RETURN_EXCHANGE_INFO.code)));

        jsonObject.put("productDescVOList", productDescArrayObject);

        // 상품리뷰 (김영석M)
        // this.getPrdCategoryArea()에 있는 productVO 내 reviewDispYn, reviewOptDispYn 참조

        return new ResponseEntity(jsonObject, HttpStatus.OK);
    }

    private Object setDescInfo(String descTypCd, Object object) {
        JSONObject productDescObject = new JSONObject();
        if(PrdDescTypCd.DETAIL.code.equals(descTypCd) && object instanceof ProductDescVO) {
            productDescObject.put("prdDescTypCd", PrdDescTypCd.DETAIL.code);
            productDescObject.put("prdDescContClob", ((ProductDescVO) object).getPrdDescContClob());
        }
        if(PrdDescTypCd.AS_INFO.code.equals(descTypCd) && object instanceof PdPrdDesc) {
            productDescObject.put("prdDescTypCd", PrdDescTypCd.AS_INFO.code);
            productDescObject.put("prdDescContVc", ((PdPrdDesc) object).getPrdDescContVc());
        }
        if(PrdDescTypCd.RETURN_EXCHANGE_INFO.code.equals(descTypCd) && object instanceof PdPrdDesc) {
            productDescObject.put("prdDescTypCd", PrdDescTypCd.RETURN_EXCHANGE_INFO.code);
            productDescObject.put("prdDescContVc", ((PdPrdDesc) object).getPrdDescContVc());
        }
        return productDescObject;
    }

    @ApiOperation(value = "기본정보 영역 정보 조회",
                  notes = "상품수정 시 기본정보 영역 정보를 조회한다.",
                  response = JSONObject.class)
    @GetMapping("/basic-info/{prdNo}")
    public ResponseEntity<JSONObject> getBasicInfoArea(HttpServletRequest request, HttpServletResponse response, @PathVariable("prdNo") long prdNo, @RequestParam(name="stdPrdYn", defaultValue = "N") String stdPrdYn) {
        long selMnbdNo = 0;
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if(auth != null) {
            selMnbdNo = auth.getMemberNumber();
        }
        if (selMnbdNo == 0) throw new ProductException(ProductExceptionMessage.AUTH_ERROR);

        String sellerPrdMsg = productValidate.checkProductMnbdInfoByPrdNo(auth, prdNo);
        if(StringUtil.isNotEmpty(sellerPrdMsg)) {
            throw new ProductException(sellerPrdMsg);
        }

        JSONObject jsonObject = new JSONObject();

        // 미성년자 구매가능 (김영석M)
        //this.getPrdCategoryArea()에 있는 productVO 내 minorSelCnYn 참조

        // 부가세/면세상품 (김영석M)
        //this.getPrdCategoryArea()에 있는 productVO 내 suplDtyfrPrdClfCd 참조

        // 모델코드 (김영석M)
        // /product/prd-model-info/{prdNo}에서 정보 가져올수 있음. (this.getPrdModelInfo())

        // 판매자 상품코드 (김영석M)
        if(sellerValidate.isCertStockCdSeller(selMnbdNo)) {
            ProductStockVO productStockVO = new ProductStockVO();
            productStockVO.setOptItemNo1(0);
            productStockVO.setPrdNo(prdNo);
            int retVal = optionService.getSingleOptionCnt(productStockVO);
            if(retVal > 0) {
                List<ProductStockVO> retProductStockList = optionService.getPdStockPtnrInfoByPrdNo(productStockVO);
                if(retProductStockList != null && retProductStockList.size() > 0) {
                    jsonObject.put("sellerStockCd", retProductStockList.get(0).getSellerStockCd());        // 노옵션 지정일상품코드 (상품에서 직접설정)
                    //jsonObject.put("singleOptionYN", "Y");
                }
            }
            jsonObject.put("ptnrPrdSellerYN", "Y");
        }

        // 인증정보 (심유정M)
        jsonObject.put("productCertInfoVOList", certService.getProductCertList(prdNo));

        List<ProductTagVO> productTagVOList = new ArrayList<ProductTagVO>();
        List<CoordiProductVO> coordiPrdList = new ArrayList<CoordiProductVO>();
        JSONArray jsonArray = new JSONArray();

        //if(productValidate.isUsePrdGrpTypCd(stdPrdYn, selMnbdNo)){
        if("Y".equals(stdPrdYn) && customerService.isRoadShoSeller(selMnbdNo)) {
            // 상품태그 (김영석M)
            productTagVOList = productService.getProductTagList(prdNo);

            // 연관상품 (김영석M)
            coordiPrdList = coordiProductService.getCoordiPrdNoList(prdNo);
        }
        jsonObject.put("productTagVOList", productTagVOList);
        jsonObject.put("coordiProductVOList", coordiPrdList);


        jsonObject.put("productRmaterialVOList", productService.getProductRmaterialList(prdNo));

        return new ResponseEntity(jsonObject, HttpStatus.OK);
    }

    @ApiOperation(value = "추가정보 영역 정보 조회",
                  notes = "상품수정 시 추가정보 영역 정보를 조회한다.",
                  response = JSONObject.class)
    @GetMapping("/prd-add-info/{prdNo}")
    public ResponseEntity<JSONObject> getPrdAddInfoArea(@PathVariable("prdNo") long prdNo) {
        JSONObject jsonObject = new JSONObject();

        // 제조일자/유효일자 (김영석M)
        // 제조일자 : this.getPrdCategoryArea()에 있는 productVO 내 mnfcDy 참조
        // 유효일자 : this.getPrdCategoryArea()에 있는 productVO 내 eftvDy 참조

        // 상품정보제공고시 (정승식M)
        // CtgrAttrController.getCtgrAttributeInfo (/ctgr-attr/info/{dispCtgrNo}/{infoTypeCtgrNo}) 참조

        return new ResponseEntity(jsonObject, HttpStatus.OK);
    }

    @ApiOperation(value = "구매/혜택조건 영역 정보 조회",
                  notes = "상품수정 시 구매/혜택조건 영역 정보를 조회한다.",
                  response = JSONObject.class)
    @GetMapping("/prd-benefit/{prdNo}")
    public ResponseEntity<JSONObject> getPrdBenefitArea(HttpServletRequest request, HttpServletResponse response, @PathVariable("prdNo") long prdNo) {
        String sellerPrdMsg = productValidate.checkProductMnbdInfoByPrdNo(AuthService.getAuth(request, response, AuthCode.TMALL_AUTH), prdNo);
        if(StringUtil.isNotEmpty(sellerPrdMsg)) {
            throw new ProductException(sellerPrdMsg);
        }

        JSONObject jsonObject = new JSONObject();

        // 최소구매수량 (김영석M)
        // this.getPrdCategoryArea()에 있는 productVO 내 selMinLimitTypCd, selMinLimitQty 참조

        // 최대구매수량 (김영석M)
        // this.getPrdCategoryArea()에 있는 productVO 내 selLimitTypCd, selLimitQty, selLimitTerm 참조

        // OK캐시백 지급 (김영석M)
        // 상품고객혜택정보 (ocbPnt, ocbPntRt, ocbWyCd 참조)
        jsonObject.put("customerBenefitVO", productBenefitService.getCustomerBenefit(prdNo));

        // 사은품 (김영석M)
        jsonObject.put("productGiftVO", productBenefitService.getProductGift(prdNo));

        // 가격비교 사이트 등록 (김영석M)
        // this.getPrdCategoryArea()에 있는 productVO 내 prcCmpExpYn 참조

        return new ResponseEntity(jsonObject, HttpStatus.OK);
    }

    @ApiOperation(value = "배송정보 영역 정보 조회",
                  notes = "상품수정 시 배송정보 영역 정보를 조회한다.",
                  response = JSONObject.class)
    @GetMapping("/prd-delivery/{prdNo}")
    public ResponseEntity<JSONObject> getPrdDeliveryArea(HttpServletRequest request, HttpServletResponse response, @PathVariable("prdNo") long prdNo, @RequestParam(name="dlvCnAreaCd", defaultValue = "01") String dlvCnAreaCd, @RequestParam(name="dlvWyCd", defaultValue = "01") String dlvWyCd, @RequestParam(name="dlvCstInstBasiCd", defaultValue = "01") String dlvCstInstBasiCd) {
        long memNo = 0;
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if(auth != null) {
            memNo = auth.getMemberNumber();
        }
        if (memNo == 0) throw new ProductException(ProductExceptionMessage.AUTH_ERROR);

        String sellerPrdMsg = productValidate.checkProductMnbdInfoByPrdNo(auth, prdNo);
        if(StringUtil.isNotEmpty(sellerPrdMsg)) {
            throw new ProductException(sellerPrdMsg);
        }

        JSONObject jsonObject = new JSONObject();

        // 배송정보 템플릿 목록 (심유정M)
        PrdInfoTmplVO prdInfoTmplVO = new PrdInfoTmplVO();

        prdInfoTmplVO.setMemNo(memNo);
        prdInfoTmplVO.setPrdInfoTmpltClfCd(ProductConstDef.PRD_INFO_TMPLT_CLF_CD_DLV);
        jsonObject.put("dlvTemplateList",templateService.getProductInformationTemplateAllList(prdInfoTmplVO));

        // 배송가능지역 (심유정M)
        SyCoDetailVO syCoDetailVO = new SyCoDetailVO();
        syCoDetailVO.setGrpCd(ProductConstDef.DLV_CN_AREA_CD);

        List<SyCoDetailVO> dlvPossAreaCdLst = commonService.getCodeDetail(syCoDetailVO);

        JSONArray result = new JSONArray();
        for(SyCoDetailVO syCoDetail : dlvPossAreaCdLst){
            Map obj = new HashMap();
            obj.put("dtlsCd", syCoDetail.getDtlsCd());
            obj.put("dtlsComNm", syCoDetail.getDtlsComNm());
            if(syCoDetail.getDtlsCd().equals(dlvCnAreaCd)) {
                obj.put("selected", "Y");
            }
            result.add(obj);
        }
        jsonObject.put("dlvPossibleAreaCdList",result);

        // 배송방법 (심유정M)
        syCoDetailVO = new SyCoDetailVO();
        syCoDetailVO.setGrpCd(ProductConstDef.DLV_WY_CD);

        List<SyCoDetailVO> dlvWyCdLst = commonService.getCodeDetail(syCoDetailVO);

        JSONArray resultDlvWy = new JSONArray();
        for(SyCoDetailVO syCoDetail : dlvWyCdLst){
            Map objDlv = new HashMap();
            objDlv.put("dtlsCd", syCoDetail.getDtlsCd());
            objDlv.put("dtlsComNm", syCoDetail.getDtlsComNm());
            if(syCoDetail.getDtlsCd().equals(dlvWyCd)) {
                objDlv.put("selected", "Y");
            }
            resultDlvWy.add(objDlv);
        }
        jsonObject.put("dlvWyCd",resultDlvWy);

        // 주소지 (최웅철M)
        jsonObject.put("claimAddressInfoVOList",deliveryService.getProductAddressList(prdNo));

        // 배송비 설정 (심유정M)
        //판매자 조건부 기준 배송비
        //HashMap hashMap = new HashMap();
        //hashMap.put("sellerBasiDlvCstTxt",deliveryService.getSellerBasiDlvCstTxt(memNo));
        //출고지 조건부 기준 배송비부분 최웅철M 작업과 중복으로 skip처리

        //제주 도서산간
        // this.getPrdCategoryArea()에 있는 productVO 내 jejuDlvCst 참조
        // this.getPrdCategoryArea()에 있는 productVO 내 islandDlvCst 참조

        // 반품/교환 배송비 (+ 초기 배송비 무료시 부과방법) (심유정M)
        // this.getPrdCategoryArea()에 있는 productVO 내 rtngdDlvCst 참조
        // this.getPrdCategoryArea()에 있는 productVO 내 exchDlvCst 참조
        // this.getPrdCategoryArea()에 있는 productVO 내 rtngdDlvCd 참조

        jsonObject.put("sellerBasiDlvCstTxt", deliveryService.getSellerBasiDlvCstTxt(memNo));

        // A/S안내 (김영석M) - prdDescContVc
        // getSellOptionArea()에 통합

        // 반품/교환 안내 (김영석M) - prdDescContVc
        // getSellOptionArea()에 통합

        // 수량별 차등
        if (ProductConstDef.DLV_CST_INST_BASI_CD_QTY_GRADE.equals(dlvCstInstBasiCd)) {
            jsonObject.put("prdOrderCountBaseDeliveryVOList", deliveryService.getOrdQtyBasDlvcstList(prdNo));
        } else {
            jsonObject.put("prdOrderCountBaseDeliveryVOList", new ArrayList());
        }

        // 새로운 배송정보 템플릿으로 저장하기 (체크 + 입력 시 상품/등록 수정에서 처리필요함) (심유정M)

        return new ResponseEntity(jsonObject, HttpStatus.OK);
    }

    @ApiOperation(value = "기본 주소지 셋 조회",
            notes = "상품등록시 접속 셀러기준 기본 주소지 셋 조회.",
            response = DefaultAddressSetVO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "기본 주소지 셋 존재함. 반환 실행."),
            @ApiResponse(code = 404, message = "기본 주소지 셋 존재하지 않음.")
    })
    @GetMapping("/default-address")
    public ResponseEntity<DefaultAddressSetVO> getDefaultAddressSet(HttpServletRequest request) throws ProductControllerException {

        DefaultAddressSetVO defaultAddressSetVO = null;

        //soffice.11st.co.kr API I/F
        try {

            defaultAddressSetVO = new DefaultAddressSetVO(
                    this.getDefaultAddress(DefaultOutAddressVO.class, request),
                    this.getDefaultAddress(DefaultInAddressVO.class, request),
                    this.getDefaultAddress(DefaultVisitAddressVO.class, request),
                    this.getDefaultAddress(DefaultConsignmentOutAddressVO.class, request),
                    this.getDefaultAddress(DefaultConsignmentInAddressVO.class, request),
                    this.getDefaultAddress(DefaultGlobalOutAddressVO.class, request)
            );

        } catch (Exception ex) {
            ex.printStackTrace();
            ProductControllerException threx = new ProductControllerException(ProductControllerExceptionMessage.DEFAULT_ADDRESS_SET_GET_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return new ResponseEntity<>(defaultAddressSetVO, defaultAddressSetVO == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }


    @ApiOperation(value = "기본 주소지 셋 전부 조회",
            notes = "상품등록시 접속 셀러기준 기본 주소지 셋 전부 조회.",
            response = DefaultAddressSetVO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "기본 주소지 셋 존재함. 반환 실행.")
    })
    @GetMapping("/default-all-address")
    public ResponseEntity<DefaultAddressSetVO> getDefaultAddressAllSet(HttpServletRequest request) throws ProductControllerException {
        DefaultAddressSetVO defaultAddressSetVO = null;

        try {
            Cookie[] cks = request.getCookies();

            StringBuilder strb = new StringBuilder();

            for(Cookie ck : cks) {
                strb.append(String.format("%s=%s; ",ck.getName(), ck.getValue()));
            }

            try(CloseableHttpClient client = HttpClients.createDefault()) {

                HttpGet getRequest = new HttpGet(sOfficeProperties.getUrl() + SOFFICE_DELIVERY_API_URL + "/default-all-address");
                getRequest.setHeader("Accept", MediaType.APPLICATION_JSON_VALUE);
                getRequest.setHeader("Origin", request.getHeader("Host"));
                getRequest.setHeader("Cookie", strb.toString());

                try(CloseableHttpResponse response = client.execute(getRequest)) {
                    HttpEntity entity = response.getEntity();

                    StatusLine statusLine = response.getStatusLine();

                    if (statusLine.getStatusCode() == 200) {

                        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));

                        StringBuilder sb = new StringBuilder();

                        String line = null;

                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }

                        String responseJsonString = sb.toString();

                        defaultAddressSetVO = gson.fromJson(responseJsonString, DefaultAddressSetVO.class);

                    } else if (statusLine.getStatusCode() == 404) return null;
                    else
                        throw new ProductControllerException(String.format(ProductControllerExceptionMessage.SOFFICE_INTERFACE_FAILED, statusLine.getStatusCode(), statusLine.getReasonPhrase()));
                }

            }

        } catch (Exception ex) {

            ProductControllerException threx = new ProductControllerException("기본 배송지 전체 조회 error ", ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }




        return new ResponseEntity<>(defaultAddressSetVO, defaultAddressSetVO == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

/* // 기본 주소지 I/F 테스트 용 API
    @GetMapping("/default-out-address")
    public ResponseEntity<DefaultOutAddressVO> getDefaultOutAddress(
            HttpServletRequest request
    ) {

        DefaultOutAddressVO vo = this.getDefaultAddress(DefaultOutAddressVO.class, request);

        return new ResponseEntity<>(vo, vo == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/default-in-address")
    public ResponseEntity<DefaultInAddressVO> getDefaultInAddress(
            HttpServletRequest request
    ) {

        DefaultInAddressVO vo = this.getDefaultAddress(DefaultInAddressVO.class, request);

        return new ResponseEntity<>(vo, vo == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/default-visit-address")
    public ResponseEntity<DefaultVisitAddressVO> getDefaultVisitAddress(
            HttpServletRequest request
    ) {

        DefaultVisitAddressVO vo = this.getDefaultAddress(DefaultVisitAddressVO.class, request);

        return new ResponseEntity<>(vo, vo == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/default-consignment-out-address")
    public ResponseEntity<DefaultConsignmentOutAddressVO> getDefaultConsignmentOutAddress(
            HttpServletRequest request
    ) {

        DefaultConsignmentOutAddressVO vo = this.getDefaultAddress(DefaultConsignmentOutAddressVO.class, request);

        return new ResponseEntity<>(vo, vo == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/default-consignment-in-address")
    public ResponseEntity<DefaultConsignmentInAddressVO> getDefaultConsignmentInAddress(
            HttpServletRequest request
    ) {

        DefaultConsignmentInAddressVO vo = this.getDefaultAddress(DefaultConsignmentInAddressVO.class, request);

        return new ResponseEntity<>(vo, vo == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/default-global-out-address")
    public ResponseEntity<DefaultGlobalOutAddressVO> getDefaultGlobalOutAddress(
            HttpServletRequest request
    ) {

        DefaultGlobalOutAddressVO vo = this.getDefaultAddress(DefaultGlobalOutAddressVO.class, request);

        return new ResponseEntity<>(vo, vo == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
*/

    /**
     * 기본 주소지 조회 soffice API I/F 메소드
     * http://soffice.11st.co.kr의 API를 사용하여 기본 주소지(출고지, 반품/교환지, 방문수령지, 위탁 출고지, 위탁 반품/교환지, 해외 출고지)를 조회해 온다.
     *
     * @param clazz 조회 해올 주소지 유형을 상징하는 VO의 class 객체
     * @param request ServletRequest
     * @return 조회된 주소지를 담은 VO. 해당 유형의 기본 주소지가 존재하지 않을 경우 null 반환
     */
    private <T> T getDefaultAddress(Class<T> clazz, HttpServletRequest request) {

        if(clazz == null) throw new NullPointerException("clazz 인자는 null이 허용되지 않습니다.");

        T defaultAddressVO = null;

        String apiLeafUrlPath = null;

        String className = clazz.getName();
        className = className.substring(className.lastIndexOf(".")+1);

        switch (className) {
            case "DefaultOutAddressVO": apiLeafUrlPath = "/default-out-address";
            break;
            case "DefaultInAddressVO": apiLeafUrlPath = "/default-in-address";
            break;
            case "DefaultVisitAddressVO": apiLeafUrlPath = "/default-visit-address";
            break;
            case "DefaultConsignmentOutAddressVO": apiLeafUrlPath = "/default-consignment-out-address";
            break;
            case "DefaultConsignmentInAddressVO": apiLeafUrlPath = "/default-consignment-in-address";
            break;
            case "DefaultGlobalOutAddressVO": apiLeafUrlPath = "/default-global-out-address";
            break;
            default:
                throw new ProductControllerException(ProductControllerExceptionMessage.INVALID_DEFAULT_ADDRESS_VO_CLASS_ARGUMENT);
        }


        try {
            Cookie[] cks = request.getCookies();

            StringBuilder strb = new StringBuilder();

            for(Cookie ck : cks) {
                strb.append(String.format("%s=%s; ",ck.getName(), ck.getValue()));
            }

            try(CloseableHttpClient client = HttpClients.createDefault()) {

                HttpGet getRequest = new HttpGet(sOfficeProperties.getUrl() + SOFFICE_DELIVERY_API_URL + apiLeafUrlPath);
                getRequest.setHeader("Accept", MediaType.APPLICATION_JSON_VALUE);
                getRequest.setHeader("Origin", request.getHeader("Host"));
                getRequest.setHeader("Cookie", strb.toString());

                try(CloseableHttpResponse response = client.execute(getRequest)) {

                    HttpEntity entity = response.getEntity();

                    StatusLine statusLine = response.getStatusLine();

                    if (statusLine.getStatusCode() == 200) {

                        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));

                        StringBuilder sb = new StringBuilder();

                        String line = null;

                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }

                        String responseJsonString = sb.toString();

                        defaultAddressVO = gson.fromJson(responseJsonString, clazz);

                    } else if (statusLine.getStatusCode() == 404) return null;
                    else
                        throw new ProductControllerException(String.format(ProductControllerExceptionMessage.SOFFICE_INTERFACE_FAILED, statusLine.getStatusCode(), statusLine.getReasonPhrase()));
                }

            }

        } catch (Exception ex) {

            String exMsg = null;
            switch (className) {
                case "DefaultOutAddressVO": exMsg = ProductControllerExceptionMessage.DEFAULT_OUT_ADDRESS_GET_ERROR;
                    break;
                case "DefaultInAddressVO": exMsg = ProductControllerExceptionMessage.DEFAULT_IN_ADDRESS_GET_ERROR;
                    break;
                case "DefaultVisitAddressVO": exMsg = ProductControllerExceptionMessage.DEFAULT_VISIT_ADDRESS_GET_ERROR;
                    break;
                case "DefaultConsignmentOutAddressVO": exMsg = ProductControllerExceptionMessage.DEFAULT_CONSIGNMENT_OUT_ADDRESS_GET_ERROR;
                    break;
                case "DefaultConsignmentInAddressVO": exMsg = ProductControllerExceptionMessage.DEFAULT_CONSIGNMENT_IN_ADDRESS_GET_ERROR;
                    break;
                case "DefaultGlobalOutAddressVO": exMsg = ProductControllerExceptionMessage.DEFAULT_GLOBAL_OUT_ADDRESS_GET_ERROR;
                    break;
                default:
                    throw new ProductControllerException(ProductControllerExceptionMessage.INVALID_DEFAULT_ADDRESS_VO_CLASS_ARGUMENT);
            }

            ProductControllerException threx = new ProductControllerException(exMsg, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
        return defaultAddressVO;
    }



    /**
     * 판매기간구분코드(PD029) 조회 API
     */
    @ApiOperation(
            value = "판매기간구분코드(PD029) 목록 조회",
            notes = "판매기간구분코드(PD029) 목록 조회",
            response = GroupCodeVO.class
    )
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "판매기간구분코드 조회 성공")
    })
    @GetMapping("/sel-prd-clf-cd")
    public ResponseEntity<GroupCodeVO> getSelPrdClfCd() {

        GroupCodeVO groupCodeVO = new GroupCodeVO();

        groupCodeVO.setGroupCode(SelPrdClfCd.FIXED_MANUAL_INPUT.getGrpCd());
        groupCodeVO.setGroupCodeName(SelPrdClfCd.FIXED_MANUAL_INPUT.getGrpCdNm());
        groupCodeVO.setCodes(new LinkedList<CodeVO>());

        for(SelPrdClfCd cd : SelPrdClfCd.values()) {
            CodeVO codeVO = new CodeVO();
            codeVO.setCode(cd.getDtlsCd());
            codeVO.setCodeName(cd.getDtlsComNm());
            codeVO.setCdVal1(cd.getCdVal1());
            groupCodeVO.getCodes().add(codeVO);
        }

        return new ResponseEntity<>(groupCodeVO, HttpStatus.OK);
    }


    /**
     * 출고지 주소목록 조회 API
     * - 참고 URL : http://soffice.11st.co.kr/register/addrSearch.tmall?method=addrSearch&seq=outAddrSeq&addr=outAddrNM&addrCD=02&memTypCD=02&popupYN=Y
     *
     *      - 응답 데이터 설명
     *          03.tMallSellerToolProject/webapps/jsp/register/personalinformation/addrSearch.jsp
     *          해당코드 내 chkInfoValue() 메소드 참고함.
     *
     *
     *      - 응답 예시
     *        [{
     *        	"baseAddrYn": "Y",
     *        	"addrSeq": 284,
     *        	"baseAddr": "서울특별시 영등포구 영등포동4가   타임스퀘어",
     *        	"dtlsAddr": "상세주소.",
     *        	"addrCd": "02",
     *        	"addrNm": "1003811_테스트주소",
     *        	"mailNo": "150798",
     *        	"mailNoSeq": "004",
     *        	"rcvrNm": "주소이름입니다.",
     *        	"gnrlTlphnNo": "02-1234-4321",
     *        	"prtblTlphnNo": "02-555-5555"
     *        },
     *        {
     *        	"baseAddrYn": "N",
     *        	"addrSeq": 888,
     *        	"baseAddr": "서울특별시 영등포구 영등포동4가   타임스퀘어",
     *        	"dtlsAddr": "상세주소.",
     *        	"addrCd": "02",
     *        	"addrNm": "1003811_테스트주소",
     *        	"mailNo": "150798",
     *        	"mailNoSeq": "004",
     *        	"rcvrNm": "주소이름입니다.",
     *        	"gnrlTlphnNo": "02-1234-4321",
     *        	"prtblTlphnNo": "02-555-5555"
     *        },
     *        {
     *        	"baseAddrYn": "N",
     *        	"addrSeq": 999,
     *        	"baseAddr": "서울특별시 영등포구 영등포동4가   타임스퀘어",
     *        	"dtlsAddr": "상세주소.",
     *        	"addrCd": "02",
     *        	"addrNm": "1003811_테스트주소",
     *        	"mailNo": "150798",
     *        	"mailNoSeq": "004",
     *        	"rcvrNm": "주소이름입니다.",
     *        	"gnrlTlphnNo": "02-1234-4321",
     *        	"prtblTlphnNo": "02-555-5555"
     *        }]
     * * */
    @GetMapping("/export-addr-list")
    public net.sf.json.JSONArray getOutAddressList() {

        net.sf.json.JSONArray obj = net.sf.json.JSONArray.fromObject("[{\n" +
                "\t'baseAddrYn': 'Y',\n" +
                "\t'addrSeq': 284,\n" +
                "\t'baseAddr': '서울특별시 영등포구 영등포동4가   타임스퀘어',\n" +
                "\t'dtlsAddr': '상세주소.',\n" +
                "\t'addrCd': '02',\n" +
                "\t'addrNm': '1003811_테스트주소',\n" +
                "\t'mailNo': '150798',\n" +
                "\t'mailNoSeq': '004',\n" +
                "\t'rcvrNm': '주소이름입니다.',\n" +
                "\t'gnrlTlphnNo': '02-1234-4321',\n" +
                "\t'prtblTlphnNo': '02-555-5555'\n" +
                "},\n" +
                "{\n" +
                "\t'baseAddrYn': 'Y',\n" +
                "\t'addrSeq': 888,\n" +
                "\t'baseAddr': '서울특별시 영등포구 영등포동4가   타임스퀘어',\n" +
                "\t'dtlsAddr': '상세주소.',\n" +
                "\t'addrCd': '02',\n" +
                "\t'addrNm': '1003811_테스트주소',\n" +
                "\t'mailNo': '150798',\n" +
                "\t'mailNoSeq': '004',\n" +
                "\t'rcvrNm': '주소이름입니다.',\n" +
                "\t'gnrlTlphnNo': '02-1234-4321',\n" +
                "\t'prtblTlphnNo': '02-555-5555'\n" +
                "},\n" +
                "{\n" +
                "\t'baseAddrYn': 'Y',\n" +
                "\t'addrSeq': 999,\n" +
                "\t'baseAddr': '서울특별시 영등포구 영등포동4가   타임스퀘어',\n" +
                "\t'dtlsAddr': '상세주소.',\n" +
                "\t'addrCd': '02',\n" +
                "\t'addrNm': '1003811_테스트주소',\n" +
                "\t'mailNo': '150798',\n" +
                "\t'mailNoSeq': '004',\n" +
                "\t'rcvrNm': '주소이름입니다.',\n" +
                "\t'gnrlTlphnNo': '02-1234-4321',\n" +
                "\t'prtblTlphnNo': '02-555-5555'\n" +
                "}]");


        return obj;
    }

    /**
     * 반품지 주소목록 조회 API
     * - 참고 URL : http://soffice.11st.co.kr/register/addrSearch.tmall?method=addrSearch&seq=inAddrSeq&addr=inAddrNM&addrCD=03&memTypCD=02&popupYN=Y
     *
     *  응답 예시는 출고지 주소목록과 동일하게 작업이 되면 될 것 같습니다.(addrCd는 다르게 될 것 같네요.)
     *
     *  아니면.....front에서 addrCd 로 구분 할까요??
     *
     * */
    @GetMapping("/return-addr-list")
    public JSONObject getInAddressList() {
        return null;
    }

//    public ResponseEntity<JSONArray> getOrgnTypDtlsCdList(@RequestParam(value="parentCd",required=false) String parentCd) {
    @GetMapping("/search-addr")
    public net.sf.json.JSONObject getAddressListForward(@RequestParam(value="method", required=true) String method,
                                                        @RequestParam(value="langType", required=false) String langType,
                                                        @RequestParam(value="selectBoxIdx", required=false) String selectBoxIdx,
                                                        @RequestParam(value="sidoNm", required=false) String sidoNm,
                                                        @RequestParam(value="sido", required=false) String sido,
                                                        @RequestParam(value="sigungu", required=false) String sigungu,
                                                        @RequestParam(value="searchAddrKwd", required=false) String searchAddrKwd,
                                                        @RequestParam(value="pageNum", required=false) String pageNum,
                                                        @RequestParam(value="fetchSize", required=false) String fetchSize,
                                                        HttpServletRequest req) {

        final String FW_URL_PREFIX = "http://www.11st.co.kr/addr/searchAddr.tmall";

        //get client cookie.
        Cookie[] cookies = req.getCookies();
        StringBuilder sb = new StringBuilder();
        for (Cookie ck : cookies) {
            sb.append(String.format("%s=%s; ", ck.getName(), ck.getValue()));
        }

        try {

            //create client
            org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();

            String queryString = "?method=" + method + "&langType=" + langType +
                    (StringUtils.isEmpty(selectBoxIdx) ? "" : "&selectBoxIdx=" + selectBoxIdx) +
                    (StringUtils.isEmpty(sidoNm) ? "" : "&sidoNm=" + URLEncoder.encode(URLEncoder.encode(sidoNm,"UTF-8"),"UTF-8")) +
                    (StringUtils.isEmpty(sido) ? "" : "&sido=" + URLEncoder.encode(URLEncoder.encode(sido,"UTF-8"),"UTF-8")) +
                    (StringUtils.isEmpty(sigungu) ? "" : "&sigungu=" + URLEncoder.encode(URLEncoder.encode(sigungu,"UTF-8"),"UTF-8")) +
                    (StringUtils.isEmpty(searchAddrKwd) ? "" : "&searchAddrKwd=" + URLEncoder.encode(URLEncoder.encode(searchAddrKwd,"UTF-8"),"UTF-8")) +
                    (StringUtils.isEmpty(pageNum) ? "" : "&pageNum=" + pageNum) +
                    (StringUtils.isEmpty(fetchSize) ? "" : "&fetchSize=" + fetchSize);
//                    (StringUtils.isEmpty() ? "" : "") +


            HttpGet forward = new HttpGet(FW_URL_PREFIX + queryString);
            forward.setHeader("Cookie", sb.toString());
            forward.setHeader("Accept", "*/*");
            forward.setHeader("Origin", req.getHeader("Host"));

            HttpResponse response = client.execute(forward);

            log.error(response.getStatusLine().getStatusCode() + "");

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            log.error(result.toString());
            //result에서 끝에 붙은 두개의 괄호 없앰
            String resObj = result.toString();
            resObj = resObj.substring(1, resObj.length()-1);

            return net.sf.json.JSONObject.fromObject(resObj);



        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }



    @GetMapping("/eso-cache-data/{key}")
    public ResponseEntity<Object> getEsoCacheData(@PathVariable String key){
        Object val = objectCache.getObject(key);
        if(val == null){
            val = "Test Value";
            objectCache.setObject(key, val);
        }
        return new ResponseEntity(val.toString(), HttpStatus.OK);
    }


    @GetMapping("/test-eso-cache-data/{key}")
    public ResponseEntity<List> getTestEsoCacheData(@PathVariable String key) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException{

        List<KeywdVO> data = (List<KeywdVO>)objectCache.getObject(key);
        if(data == null){

            data = new ArrayList<KeywdVO>();
            KeywdVO keywdVO = new KeywdVO();
            keywdVO.setDispCtgrNo(1);
            keywdVO.setKeywdNm("1");
            data.add(keywdVO);
            keywdVO = new KeywdVO();
            keywdVO.setDispCtgrNo(2);
            keywdVO.setKeywdNm("2");
            data.add(keywdVO);
            keywdVO = new KeywdVO();
            keywdVO.setDispCtgrNo(3);
            keywdVO.setKeywdNm("3");
            data.add(keywdVO);

            objectCache.setObject(key, data);
        }else{

            if(data.size() > 0) {

                for(KeywdVO keywdVO : data){
                    log.debug(keywdVO.getKeywdNm());
                }
            }
        }

        return new ResponseEntity(data, HttpStatus.OK);
    }

    @GetMapping("/eso-cache-data/{key}/{data}")
    public ResponseEntity<Object> setEsoCacheData(@PathVariable String key, @PathVariable String data){
        objectCache.setObject(key, data);

        return new ResponseEntity(data.toString(), HttpStatus.OK);
    }


    /**
     * 모바일 수정 가능 상품 조회 API
     * @param prdNo 상품번호
     * @return true or false
     * @throws ProductException
     */
    @ApiOperation(
            value = "모바일 수정 가능 상품 조회 API",
            notes = "상품번호(PK)로 모바일 수정 가능 상품 조회",
            response = Boolean.class
    )
    @ApiResponses(value = {
            @ApiResponse(code=404, message = "해당 상품을 찾을 수 없습니다."),
            @ApiResponse(code=200, message = "모바일 soffice 상품 수정 가능한 상품입니다.")
    })
    @GetMapping("/support-mobile-edit/{prdNo}")
    public ResponseEntity<Boolean> checkSupportMobileEdit(@PathVariable("prdNo") long prdNo, Auth auth) {

        boolean isSupport = false;
        isSupport = productService.checkSupportMobileEdit(prdNo);

        /*
        if(isSupport){
            isSupport = sellerValidate.isSupportMobileEditSeller(auth);
        }
        */
        return new ResponseEntity<>(isSupport, HttpStatus.OK);
    }

    /**
     * 모바일 등록/수정 가능 셀러 조회 API
     * @param selMnbdNo 셀러번호
     * @return true or false
     * @throws ProductException
     */
    @ApiOperation(
            value = "모바일 등록/수정 가능 셀러 조회 API",
            notes = "셀러번호로 모바일 상품 등록/수정 가능 조회",
            response = Boolean.class
    )
    @ApiResponses(value = {
            @ApiResponse(code=404, message = "해당 셀러를 찾을 수 없습니다."),
            @ApiResponse(code=200, message = "모바일 soffice 상품 등록이 불가능한 셀러입니다.")
    })
    @GetMapping("/support-mobile-seller/{selMnbdNo}")
    public ResponseEntity<Boolean> checkSupportMobileSeller(@PathVariable("selMnbdNo") long selMnbdNo, Auth auth) {
        boolean isSupport = false;
        if(selMnbdNo != auth.getMemberNumber()){
            return new ResponseEntity<>(isSupport, HttpStatus.OK);
        }
        isSupport = sellerValidate.isSupportMobileEditSeller(auth);
        return new ResponseEntity<>(isSupport, HttpStatus.OK);
    }

}
