package com.elevenst.terroir.product.registration.category.service;

import com.elevenst.common.process.RegistrationInfoConverter;
import com.elevenst.common.util.StringUtil;
import com.elevenst.common.util.cache.ObjectCacheImpl;
import com.elevenst.terroir.product.registration.category.entity.DpGnrlDisp;
import com.elevenst.terroir.product.registration.category.entity.PdObjTypProp;
import com.elevenst.terroir.product.registration.category.mapper.CategoryMapper;
import com.elevenst.terroir.product.registration.category.validate.CategoryValidate;
import com.elevenst.terroir.product.registration.category.vo.*;
import com.elevenst.terroir.product.registration.common.property.UrlProperty;
import com.elevenst.terroir.product.registration.option.service.OptionServiceImpl;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.service.AdditionalProductServiceImpl;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skt.tmall.common.properties.PropertiesManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CategoryServiceImpl implements RegistrationInfoConverter<ProductVO>{

    @Autowired
    CategoryValidate categoryValidate;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    OptionServiceImpl optionService;

    @Autowired
    AdditionalProductServiceImpl additionalProductService;

    @Autowired
    ObjectCacheImpl objectCache;

    @Autowired
    UrlProperty urlProperty;

    public void setCategoryInfoProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        this.checkCategoryInfo(productVO);
    }

    public void insertCategoryProcess(ProductVO productVO) throws ProductException{

        categoryMapper.insertDpGnrlDisp(this.setDpGnrlDisp(productVO));
        this.insertCategoryRecommedations(productVO);
    }

    public void updateCategoryProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        categoryMapper.updateDpGnrlDisp(this.setDpGnrlDisp(productVO));
    }

    private DpGnrlDisp setDpGnrlDisp(ProductVO productVO) throws ProductException{
        DpGnrlDisp dpGnrlDisp = new DpGnrlDisp();

        dpGnrlDisp.setDispCtgrNo(productVO.getDispCtgrNo());
        dpGnrlDisp.setPrdNo(productVO.getPrdNo());
        dpGnrlDisp.setDispBgnDy(productVO.getBaseVO().getSelBgnDy());
        dpGnrlDisp.setDispEndDy(productVO.getBaseVO().getSelEndDy());
        dpGnrlDisp.setCreateNo(productVO.getCreateNo());
        dpGnrlDisp.setUpdateNo(productVO.getUpdateNo());
        dpGnrlDisp.setGnrlSelStatCd(productVO.getBaseVO().getSelStatCd());
        dpGnrlDisp.setGnrlSelMthdCd(productVO.getBaseVO().getSelMthdCd());
        dpGnrlDisp.setGnrlSelMnbdNo(productVO.getSelMnbdNo());
        dpGnrlDisp.setGnrlStckQty(optionService.getProductStckCnt(productVO));
        dpGnrlDisp.setGnrlOptCd(optionService.getProductOptCd(productVO));
        dpGnrlDisp.setGnrlAddPrdQty(additionalProductService.getProductCompositionCount(productVO.getPrdNo()));
        dpGnrlDisp.setGnrlDlvCstPayTypCd(productVO.getBaseVO().getDlvCstPayTypCd());
        dpGnrlDisp.setGnrlDlvCstInstBasiCd(productVO.getBaseVO().getDlvCstInstBasiCd());
        dpGnrlDisp.setGnrlMinorSelCnYn(productVO.getBaseVO().getMinorSelCnYn());
        dpGnrlDisp.setGnrlBrandCd(productVO.getBaseVO().getBrandCd());//TODO CYB 수정필요 ==> 수정 했음
        dpGnrlDisp.setGnrlBsnDealClf(productVO.getBaseVO().getBsnDealClf());
        dpGnrlDisp.setGnrlDlvClf(productVO.getBaseVO().getDlvClf());
        dpGnrlDisp.setGnrlOmPrdYn(productVO.getBaseVO().getOmPrdYn());
        dpGnrlDisp.setGnrlSvcAreaCd(productVO.getBaseVO().getSvcAreaCd());
        dpGnrlDisp.setGnrlHideYn(productVO.getBaseVO().getHideYn());
        dpGnrlDisp.setGblDlvYn(productVO.getBaseVO().getGblDlvYn());
        dpGnrlDisp.setGnrlEngDispYn(productVO.getBaseVO().getEngDispYn());
        dpGnrlDisp.setStdPrdYn(productVO.getBaseVO().getStdPrdYn());

        return dpGnrlDisp;

    }


    public CategoryVO getServiceDisplayCategoryInfo(long dispCtgrNo) throws ProductException {
        CategoryVO categoryVO = null;
        try {

            String key = "getServiceDisplayCategoryInfo_"+dispCtgrNo;
            try {
                CategoryVO value = (CategoryVO)objectCache.getObject(key);
                if(value == null){
                    value = categoryMapper.getServiceDisplayCategoryInfo(dispCtgrNo);
                    objectCache.setObject(key, value);
                    return value;
                }
            }catch (Exception e){
                log.error("ESO cache error == "+key);
            }

            categoryVO = categoryMapper.getServiceDisplayCategoryInfo(dispCtgrNo);
        }catch (Exception e){
            throw new ProductException("카테고리 정보 조회 오류");
        }

        return categoryVO;
    }

    public void getCategoryInfo(ProductVO productVO) throws ProductException{
        if(productVO.getCategoryVO() == null){
            productVO.setCategoryVO(this.getServiceDisplayCategoryInfo(productVO.getDispCtgrNo()));
        }
    }

    private void checkCategoryInfo(ProductVO productVO) throws ProductException{
        categoryValidate.checkAdultCategory(productVO);
        categoryValidate.checkCategoryLimitQty(productVO);
        categoryValidate.checkCtgrSellerAuth(productVO);
        categoryValidate.checkCategoryUsedPrdReg(productVO);
        categoryValidate.checkOfferDispLmt(productVO);
        categoryValidate.checkParentCategory(productVO);
        categoryValidate.checkAbrdCategory(productVO);
        //TODO CYB checkTopStyleInfo
//        categoryValidate.checkTopStyleInfo(productVO);
    }

    public CategoryVO getSimpleCategoryInfo(long dispCtgrNo) {
        return categoryMapper.getSimpleCategoryInfo(dispCtgrNo);
    }

    public boolean canRegistProductTypeToCategory(String prdTypCd, long dispCtgrNo) {
        ProductVO paramVO = new ProductVO();
        paramVO.getBaseVO().setPrdTypCd(prdTypCd);
        paramVO.setDispCtgrNo(dispCtgrNo);

        String result = categoryMapper.canRegistProductTypeToCategory(paramVO);

        return StringUtils.equals(result, "Y");
    }

    public boolean canRegistProductTypeToCategory(ProductVO productVO) {

        String result = categoryMapper.canRegistProductTypeToCategory(productVO);

        return StringUtils.equals(result, "Y");
    }

    /**
     * 신선 카테고리 조회
     * @param productVO
     * @return
     * @throws ProductException
     */
    public String getFreshCategoryYn(ProductVO productVO) throws ProductException {
        try {
            return categoryMapper.getFreshCategoryYn(productVO);
        } catch (Exception ex) {
            throw new ProductException("신선 카테고리 조회 오류", ex);
        }
    }

    /**
     * 상품 일반전시 카테고리 수정
     *
     * @param dpGnrlDispVO
     * @return
     * @throws ProductException
     */
    public int updateProductGeneralDisplay(DpGnrlDispVO dpGnrlDispVO) throws ProductException {
        int rtncode = 0;

        try {
            categoryMapper.updateProductGeneralDisplay(dpGnrlDispVO);
            rtncode = 1;
        } catch (ProductException ex) {
            throw new ProductException("상품 일반전시 카테고리 수정 오류", ex);
        } catch (Exception ex) {
            throw new ProductException("상품 일반전시 카테고리 수정 오류", ex);
        }

        return rtncode;
    }

    public ProductVO getCategoryOtherFixing(long prdNo) throws ProductException {
        try {
            return categoryMapper.getCategoryOtherFixing(prdNo);
        } catch (ProductException ex) {
            throw new ProductException(ex);
        } catch (Exception ex) {
            throw new ProductException(ex);
        }
    }

    /**
     * 하위 카테고리 리스트를 조회한다.
     */
    public List<DpDispCtgrMetaVO> getDisplayCategoryChildList(HashMap<String, Object> map) throws ProductException {
        List<DpDispCtgrMetaVO> childList = null;
        try {
            childList = categoryMapper.getDisplayCategoryChildList(map);
        } catch (Exception e) {
            throw new ProductException("하위 카테고리 리스트를 조회 오류", e);
        }
        return childList;
    }

    /**
     * 전시카테고리 Depth 목록(상품사용)
     */
    public List getServiceDisplayCategoryProduct(CategoryVO categoryVO) throws ProductException {
        try {
            return categoryMapper.getServiceDisplayCategoryProduct(categoryVO);
        } catch(Exception e) {
            throw new ProductException("전시카테고리 Depth 목록 조회 오류", e);
        }
    }

    public List<DpDispObjVO> getHelpDpDispObjList(DpDispObjVO dpDispObjVO) throws ProductException {
        try {
            return categoryMapper.getHelpDpDispObjList(dpDispObjVO);
        } catch (Exception e) {
            throw new ProductException("상품등록 도움말 메시지 조회 오류.", e);
        }
    }

    public CategoryVO getProductRegInfobyCtgrNo(long dispCtgrNo) throws ProductException {
        CategoryVO categoryVO = new CategoryVO();
        try{
            categoryVO = categoryMapper.getDisplayCategoryInfoNoneUseCheck(dispCtgrNo);
        } catch(Exception e) {
            throw new ProductException("카테고리 권한 체크 및 기본 정보 조회", e);
        }
        return categoryVO;
    }

    /**
     * 전시카테고리 셀러 권한 체크
     * @param  categoryVO, userNo
     * @throws ProductException
     */
    public String getDispCtgrSellerAuthChk(CategoryVO categoryVO) throws ProductException {
        String returnVal = "N";
        try {
            // 권한 설정 여부
            String sellerAuth = categoryMapper.getDispCtgrSellerAuthYn(categoryVO);
            log.debug("sellerAuth : " + sellerAuth);
            if ("Y".equals(sellerAuth)) {
                // 권한이 있는지 확인
                int count = categoryMapper.getDispCtgrSellerAuth(categoryVO);
                log.debug("count : " + count);
                if (count > 0)
                    returnVal = "Y";
                else
                    returnVal = "N";
            }
            else
                returnVal = "Y";
        } catch(Exception ex) {
            throw new ProductException("전시카테고리 셀러 권한 체크 오류", ex);
        }
        return returnVal;
    }

    //카테고리 정보 가져오기
    public HashMap<String, Object> getProductRegPossibleCtgrNo(CategoryVO categoryVO) throws ProductException {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        try {
            // 카테고리 상품등록 권한 체크 -----------------------------------
            String sellerAuth = this.getDispCtgrSellerAuthChk(categoryVO);

            rtnMap.put("sellerAuth", sellerAuth);
        } catch (Exception e) {
            throw new ProductException("카테고리 권한 체크 및 기본 정보 조회", e);
        }

        return rtnMap;
    }

    public CategoryVO findMappedStdOptGrpCategory(long leafDispCtgrNo) throws ProductException {
        CategoryVO paramBO = new CategoryVO();
        paramBO.setDispCtgrNo(leafDispCtgrNo);
        List<CategoryVO> ctgrList = categoryMapper.getDpDispCtgrList(paramBO);

        List<Long> resultList = new ArrayList<Long>();
        resultList.add(leafDispCtgrNo);
        if(!StringUtil.isEmpty(ctgrList)){
            int level = ctgrList.get(0).getDispCtgrLvl();
            if(level > 2){
                resultList.add(ctgrList.get(0).getSctgrNo());
            }
            if(level > 1){
                resultList.add(ctgrList.get(0).getMctgrNo());
            }
            if(level > 0){
                resultList.add(ctgrList.get(0).getLctgrNo());
            }
        }

        for(long dispCtgrNo : resultList){
            CategoryVO displayCategoryBO = this.getServiceDisplayCategoryInfo(dispCtgrNo);
            if(displayCategoryBO.getStdOptGrpNo() > 0) return displayCategoryBO;
        }

        return null;
    }

    public List<CategoryVO> getRecentProductGeneralDisplay(CategoryVO categoryVO) throws ProductException {
        List<CategoryVO> dataList = new ArrayList<CategoryVO>();
        try {
            dataList = categoryMapper.getRecentProductGeneralDisplay(categoryVO);
        } catch (ProductException ex) {
            throw new ProductException("최근등록한 상품정보 조회 오류", ex);
        }

        return dataList;
    }

    public boolean isProductInfoInputLimit(long prdNo) throws ProductException {
        ProductVO productBO = this.getCategoryOtherFixing(prdNo);
        return productBO != null && "Y".equals(productBO.getOfferDispLmtYn());
    }

    /**
     * 대카테고리 상품 관련 정보 조회
     * @param dispCtgrNo, useCache
     * @return Map
     * @throws ProductException
     */
    public Map<String, Object> getProductLCtgrRefInfo(long dispCtgrNo) throws ProductException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String medicalYn = categoryMapper.getProductMedicalPermissionYn(dispCtgrNo);
            resultMap.put("MEDICAL_YN", medicalYn);
        } catch (Exception e) {
            throw new ProductException("대카테고리 상품 관련 정보 조회", e);
        }
        return resultMap;
    }

    public boolean isProductMedicalPermissionYn(long dispCtgrNo) throws ProductException {

        return ("Y".equals(categoryMapper.getProductMedicalPermissionYn(dispCtgrNo)) ? true : false);
    }

    /**
     * 추천 카테고리 정보 저장
     * @param productVO
     */
    public void insertCategoryRecommedations(ProductVO productVO) {
        String[] recomCtgrNos = productVO.getRecomCtgrNos();
        if(recomCtgrNos != null && recomCtgrNos.length > 0) {
            long prdNo = productVO.getPrdNo();
            long memNo = productVO.getCreateNo();
            this.insertCategoryRecommedations(prdNo, memNo, recomCtgrNos);
        }
    }

    private void insertCategoryRecommedations(long prdNo, long memNo, String[] recomCtgrNos) {

        PdObjTypProp pdObjTypProp = new PdObjTypProp();

        pdObjTypProp.setObjNo(prdNo);
        pdObjTypProp.setObjVal1(recomCtgrNos[0]);

        if(recomCtgrNos.length == 2) {
            pdObjTypProp.setObjVal2(recomCtgrNos[1]);
        } else if (recomCtgrNos.length == 3) {
            pdObjTypProp.setObjVal2(recomCtgrNos[1]);
            pdObjTypProp.setObjVal3( recomCtgrNos[2]);
        }
        pdObjTypProp.setCreateNo(memNo);
        categoryMapper.insertCategoryRecommendations(pdObjTypProp);
    }

    /**
     * 메타카테고리와 추가 전시카테고리 맵핑 조회
     *
     * @param map
     * @return
     * @throws ProductException
     */
    public List<DpDispCtgrMetaVO> getMetaDispCtgrList(HashMap<String, Object> map) throws ProductException {
        try {
            StringBuffer cacheKey = new StringBuffer();
            cacheKey.append("getMetaDispCtgrList_")
                    .append(map.get("ctgrNo"))
                    .append("_")
                    .append(map.get("levelStart"))
                    .append("_")
                    .append(map.get("levelEnd"));

            try {
                List<DpDispCtgrMetaVO> value = (List<DpDispCtgrMetaVO>)objectCache.getObject(cacheKey.toString());
                if(value == null){
                    value = categoryMapper.getMetaDispCtgrList(map);
                    objectCache.setObject(cacheKey.toString(), value);
                    return value;
                }
            }catch (Exception e){
                log.error("ESO cache error == "+cacheKey.toString());
            }
            /*
            CacheOption option 	= CacheOption.createMobileOption(cacheKey.toString(), ListingConstDef.DB_CACHE_EXPIRED_TIME_1_MIN);
            return ESOCacheUtil.<DisplayService>getCGLIBProxy(this, option).getMetaDispCtgrListDB(map);
            */
            return categoryMapper.getMetaDispCtgrList(map);
        } catch (Exception ex) {
            throw new ProductException("메타 카테고리 Cache 조회 오류", ex);
        }
    }

    /**
     * MI Lab과 연동하여 추천 카테고리를 받아오고 해당 추천카테고리의 상위 직계존속 카테고리 까지 검색해서 가져온다.
     * @param nckNm 닉네임
     * @param metaCtgrNm 메타카테고리명
     * @param prdNm 등록상품명
     * @param memNo 사용자(셀러,파트너) 계정 번호
     * @return 추천된 카테고리 목록(계층)
     * @throws Exception
     */
    public List<CategoryRecommendationVO> getCategoryRecommendations(String nckNm, String metaCtgrNm, String prdNm, long memNo) throws Exception {


        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        BufferedReader br = null;
        List<CategoryRecommendationVO> crs = null;

        try {

            //1. 추천 leaf category를 MI LAB의 API로 get
            HashMap<String, Object> rtnMap = new HashMap<String, Object>();
            rtnMap.put("seller_mall_name", nckNm);
            rtnMap.put("meta", metaCtgrNm);
            rtnMap.put("prod_name", prdNm);

            JSONObject mapObj = JSONObject.fromObject(rtnMap);

            conn = (HttpURLConnection) new URL(urlProperty.getUrl() + "/v1/getCategory").openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));

            bw.write(mapObj.toString());
            bw.flush();
            bw.close();


            InputStream is = conn.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            String recommendedCtgrJson = StringEscapeUtils.unescapeJava(sb.toString());

            log.debug(recommendedCtgrJson);

            if(recommendedCtgrJson.trim().startsWith("{"))
                recommendedCtgrJson = "["+recommendedCtgrJson+"]";

            JSONArray jsonArray = JSONArray.fromObject(recommendedCtgrJson);

            List<Long> ctgrNos = new ArrayList<Long>();

            for(int i=0; i<jsonArray.size();i++) {
                JSONObject jo = jsonArray.getJSONObject(i);

                Long ctgrNo = Long.parseLong(jo.getString("leaf_category_no"));
                ctgrNos.add(ctgrNo);
            }

            //2. 추천 카테고리의 직계존속 카테고리를 검색
            HashMap param = new HashMap();
            param.put("ctgrList", ctgrNos);
            crs = categoryMapper.getCategoryRecommendations(param);

            //카테고리 등록권한이 있는 카테고리인지 확인
            List<Long> sellerAuthCheckTargetCtgrNos = new ArrayList<Long>();

            for(CategoryRecommendationVO cr : crs) {

                if(cr.getIsLeaf() == 1) {
                    for(int i=0; i<jsonArray.size();i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);

                        Long ctgrNo = Long.parseLong(jo.getString("leaf_category_no"));

                        if(cr.getDispCtgrNo() == ctgrNo)
                            cr.setScore(Double.parseDouble(jo.getString("confidence")));
                    }
                }

                if(cr.getSellerAuthYn() != null && cr.getSellerAuthYn().equals("Y")) {
                    sellerAuthCheckTargetCtgrNos.add(cr.getDispCtgrNo());
                }
            }


            if(sellerAuthCheckTargetCtgrNos.size() != 0) {
                HashMap sellAuthCtgrParam = new HashMap();
                sellAuthCtgrParam.put("ctgrList", sellerAuthCheckTargetCtgrNos);
                sellAuthCtgrParam.put("memNo", memNo);

                List<Long> sellerAuthorizedCtgrNos = categoryMapper.getSellerAuthorizedCtgrNos(sellAuthCtgrParam);

                for (CategoryRecommendationVO cr : crs) {
                    for (long sellerAuthorizedCtgrNo : sellerAuthorizedCtgrNos) {
                        if (cr.getDispCtgrNo() == sellerAuthorizedCtgrNo) {
                            cr.setSellerAuthorized(true);
                        }
                    }
                }
            }

        } catch (Exception e) {

            if(conn != null) {
                int responseCode = conn.getResponseCode();
                switch (responseCode) {
                    case HttpURLConnection.HTTP_UNAUTHORIZED : //지원되지 않는 메타 카테고리 전달시 발생하기로 함.
                        return crs = new ArrayList<CategoryRecommendationVO>();
                    default:
                        throw e;
                }
            } else {
                throw e;
            }

        } finally {
            try {
                if (br != null) br.close();
                if (dos != null) dos.close();
                if (conn != null) conn.disconnect();
            } catch (Exception e) {
                throw e;
            }
        }
        return crs;
    }

    /**
     * 전시카테고리 최하위 노드 리스트를 조회한다.
     */
    public List<CategoryVO> getServiceDisplayCategoryLeafList(CategoryVO paramBO) throws ProductException {
        try {
            return categoryMapper.getServiceDisplayCategoryLeafList(paramBO);
        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        categoryValidate.checkCategoryBaseInfo(productVO);
        categoryValidate.hasCategoryInfo(productVO);
        categoryValidate.checkMobileNotRegCategory(productVO);
        if(productVO.isUpdate()){
            categoryValidate.checkChangeCategory(preProductVO, productVO);
        }
        this.checkCategoryInfo(productVO);
    }

    public List<InfoTypeCategoryVO> getPrdInfoTypeCategoryList(){
        return categoryMapper.getPrdInfoTypeCategoryList();
    }

    /**
     * 카테고리명으로 검색된 데이터를 출력한다. (대중소세 구별없이 5개 출력됨)
     * @param dispCtgrNm
     * @return
     */
    public List<CategoryVO> getCategorySearchForDispCtgrNm(String dispCtgrNm) {
        try {
            return categoryMapper.getCategorySearchForDispCtgrNm(dispCtgrNm);
        } catch (Exception e) {
            throw e;
        }
    }

    public String isPrdInfoTypeCategory (long infoTypeCtgrNo){
        return categoryMapper.isPrdInfoTypeCategory(infoTypeCtgrNo);
    }

    public CategoryVO getDpDispCtgrListInfo(long dispCtgrNo) throws ProductException {
        CategoryVO categoryVO = new CategoryVO();
        try {
            categoryVO = categoryMapper.getDpDispCtgrListInfo(dispCtgrNo);
        } catch (ProductException ex) {
            throw new ProductException("카테고리 정보 조회 오류", ex);
        }

        return categoryVO;
    }
}