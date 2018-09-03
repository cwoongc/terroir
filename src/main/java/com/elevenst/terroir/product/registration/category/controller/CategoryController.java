package com.elevenst.terroir.product.registration.category.controller;

import com.elevenst.common.code.CacheKeyConstDef;
import com.elevenst.common.properties.PropMgr;
import com.elevenst.common.util.DBSwitchUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.common.util.cache.ObjectCacheImpl;
import com.elevenst.terroir.product.registration.category.service.CategoryServiceImpl;
import com.elevenst.terroir.product.registration.category.validate.CategoryValidate;
import com.elevenst.terroir.product.registration.category.vo.CategoryRecommendationVO;
import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import com.elevenst.terroir.product.registration.category.vo.DpDispCtgrMetaVO;
import com.elevenst.terroir.product.registration.category.vo.InfoTypeCategoryVO;
import com.elevenst.terroir.product.registration.customer.service.CustomerServiceImpl;
import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.customer.vo.NickNameVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.message.ProductExceptionMessage;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
@RestController
@RequestMapping(value="/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CategoryController {

    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    DBSwitchUtil dBSwitchUtil;

    @Autowired
    CategoryValidate categoryValidate;

    @Autowired
    ObjectCacheImpl objectCache;

    @Autowired
    PropMgr propMgr;

    @ApiOperation(value = "최근 등록한 카테고리 5개 조회",
                  notes = "상품등록 시 최근 등록한 카테고리 5개를 조회한다.",
                  response = List.class)
    @GetMapping("/recents")
    public ResponseEntity<List<CategoryVO>> getRecentList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long selMnbdNo = 0;
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if(auth != null) {
            selMnbdNo = auth.getMemberNumber();
        }
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setSelMnbdNo(selMnbdNo);
        return new ResponseEntity(categoryService.getRecentProductGeneralDisplay(categoryVO), HttpStatus.OK);
    }

    /**
     * Meta 카테고리 정보를 가져온다
     * @throws Exception
     */
    @ApiOperation(value = "Meta 카테고리 정보 조회",
                  notes = "상품등록/수정 시 Meta 카테고리 정보를 조회한다.",
                  response = List.class)
    @GetMapping("/metas")
    public ResponseEntity<List<DpDispCtgrMetaVO>> getMetaList() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ctgrNo", ProductConstDef.DISP_CTGR_META_NO);
        map.put("levelStart", 0);
        map.put("levelEnd"	, 0);
        return new ResponseEntity(categoryService.getMetaDispCtgrList(map), HttpStatus.OK);
    }

    @ApiOperation(value = "대카테고리 정보 조회",
                  notes = "상품등록/수정 시 대카테고리 정보를 조회한다.",
                  response = List.class)
    @GetMapping("/list/ctgr1")
    public ResponseEntity<List<CategoryVO>> getCtgr1List() throws Exception {
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setUserUniqSohoYn("N");
        categoryVO.setDispCtgrNo(0);
        categoryVO.setLev("0");
        return new ResponseEntity(categoryService.getServiceDisplayCategoryProduct(categoryVO), HttpStatus.OK);
    }

    /**
     * Meta 선택 시 카테고리의 대카테고리 정보를 가져온다
     * @param request
     * @param response
     * @throws Exception
     */
    private List<CategoryVO> getCtgr1ListForMeta(HttpServletRequest request, HttpServletResponse response, @PathVariable long hgCtgrNo) throws Exception {
        List<CategoryVO> ctgrList = new ArrayList<CategoryVO>();
        try {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("hgCtgrNo", hgCtgrNo);
            List<DpDispCtgrMetaVO> metaCtgr1List = categoryService.getDisplayCategoryChildList(map);

            int metaCtgrSize = metaCtgr1List.size();
            if(metaCtgrSize > 0) {

                long selMnbdNo = 0;
                String memType = "";
                Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
                if (auth != null)  {
                    selMnbdNo = auth.getMemberNumber();
                    memType = auth.getMemberType();
                }
                if (selMnbdNo == 0) throw new Exception(ProductExceptionMessage.AUTH_ERROR);

                CategoryVO paramBO = new CategoryVO();
                ProductVO productVO = new ProductVO();
                MemberVO memberVO = new MemberVO();
                memberVO.setMemNO(selMnbdNo);
                productVO.setMemberVO(memberVO);
                productVO.setSelMnbdNo(selMnbdNo);
                productVO.setCreateNo(selMnbdNo);
                customerService.getMemberInfo(productVO);

                // 소호 카테고리 노출구분 - Defalut N 제거 2010.01.30
                paramBO.setUserUniqSohoYn("N");
                // 해외쇼핑 카테고리 등록 가능 판매자 체크 ( auth.getMemberType() = '03')
                if (ProductConstDef.PRD_MEM_TYPE_GLOBAL.equals(memType) || customerService.isFrSeller(selMnbdNo)) {
                    paramBO.setUserAbrdBrandYn("Y");
                } else if(!ProductConstDef.PRD_MEM_TYPE_SUPPLIER.equals(memType)) {
                    paramBO.setUserAbrdBrandYn("N");
                }
                // OCB ZONE 승인 여부
                if (productVO.getMemberVO() != null && "02".equals(productVO.getMemberVO().getOcbZoneAppvCd())) {
                    paramBO.setUserOcbZoneYn("Y");
                } else {
                    paramBO.setUserOcbZoneYn("N");
                }
                paramBO.setOcbZoneAppvMemNo(selMnbdNo);
                paramBO.setLev("0");

                long[] ctgrNoArr = new long[metaCtgrSize];
                for(int i=0; i<metaCtgrSize; i++) {
                    ctgrNoArr[i] = metaCtgr1List.get(i).getRefCtgrNo();
                }
                paramBO.setDispCtgr1NoArr(ctgrNoArr);
                paramBO.setDispCtgrNo(0);

                ctgrList = _setDisplayCategoryBOJSONList(categoryService.getServiceDisplayCategoryProduct(paramBO));
            }
        } catch (Exception e) {
            log.error("상품군 분류에(메타) 따른 대카테고리 조회 (" + hgCtgrNo + ") : " + e.getMessage(), e);
        }
        return ctgrList;
    }

    @ApiOperation(value = "카테고리 정보 조회",
                  notes = "상품등록/수정 시 선택한 카테고리의 다음단계 카테고리 정보를 조회한다.",
                  response = List.class)
    @GetMapping("/list/{dispCtgrNo}")
    public ResponseEntity<List<CategoryVO>> getList(HttpServletRequest request, HttpServletResponse response, @PathVariable long dispCtgrNo, @RequestParam(name="isMeta", defaultValue = "false") boolean isMeta) throws Exception {
        List<CategoryVO> retList = null;
        long selMnbdNo = 0;
        String memType = "";
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if (auth != null)  {
            selMnbdNo = auth.getMemberNumber();
            memType = auth.getMemberType();
        }
        if (selMnbdNo == 0) throw new Exception(ProductExceptionMessage.AUTH_ERROR);

        if(isMeta) {
            //private method (getCtgr1ListForMeta)
            retList = this.getCtgr1ListForMeta(request, response, dispCtgrNo);
        } else {
            CategoryVO categoryVO = new CategoryVO();
            categoryVO = categoryService.getServiceDisplayCategoryInfo(dispCtgrNo);

            MemberVO memberVO = new MemberVO();
            memberVO.setMemNO(selMnbdNo);
            MemberVO memInfo = customerService.getMemberInfo(memberVO);

            categoryVO.setUserUniqSohoYn("N");
            if (ProductConstDef.PRD_MEM_TYPE_GLOBAL.equals(memType) || customerService.isFrSeller(selMnbdNo)) categoryVO.setUserAbrdBrandYn("Y");
            else if(!ProductConstDef.PRD_MEM_TYPE_SUPPLIER.equals(memType)) categoryVO.setUserAbrdBrandYn("N");

            // OCB ZONE 승인 여부
            categoryVO.setUserOcbZoneYn("N");
            if (memInfo != null && "02".equals(memInfo.getOcbZoneAppvCd())) {
                categoryVO.setUserOcbZoneYn("Y");
            }
            categoryVO.setOcbZoneAppvMemNo(selMnbdNo);
            categoryVO.setBookCtgrAuthYn("N");

            //Town회원 일경우 (은 제외)
        /*
        if(TownSOUtil.isTownSODomain(request)){
            paramBO.setOmCtgrYn("N");
            paramBO.setSvcAreaCd(ProductConstDef.TOWN_AREA_CD);//SOM
        }
        */
            categoryVO.setOmCtgrYn("N");
            categoryVO.setSvcAreaCd(ProductConstDef.B2B_AREA_CD);

            retList = _setDisplayCategoryBOJSONList(categoryService.getServiceDisplayCategoryProduct(categoryVO));
        }
        return new ResponseEntity(retList, HttpStatus.OK);
    }

    @ApiOperation(value = "상품정보제공고시 정보 조회",
                  notes = "상품등록 시 선택한 카테고리의 상품정보제공고시 정보를 조회한다.",
                  response = List.class)
    @GetMapping("/info-types")
    public ResponseEntity<List<InfoTypeCategoryVO>> getPrdInfoTypeCategoryList(HttpServletRequest request, HttpServletResponse response){
        return new ResponseEntity(categoryService.getPrdInfoTypeCategoryList(), HttpStatus.OK);
    }

    @ApiOperation(value = "카테고리명으로 검색된 정보 조회",
                  notes = "상품등록/수정 시 카테고리명으로 검색된 정보를 조회한다.",
                  response = JSONArray.class)
    @GetMapping("/search/{dispCtgrNm}")
    public ResponseEntity<JSONArray> search(@PathVariable String dispCtgrNm) throws Exception {
        List<CategoryVO> searchList = (List<CategoryVO>)objectCache.getObject("getProductCategorySearchNm_"+dispCtgrNm);
        if(searchList == null){
            searchList = categoryService.getCategorySearchForDispCtgrNm(dispCtgrNm);
            objectCache.setObject("getProductCategorySearchNm_"+dispCtgrNm, searchList, CacheKeyConstDef.ESO_CACHE_30MINUTE_TIME);
        }
        //List<CategoryVO> searchList = categoryService.getCategorySearchForDispCtgrNm(dispCtgrNm);
        JSONArray jsonArray = new JSONArray();
        for(CategoryVO categoryVO : searchList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dispCtgrNo", categoryVO.getDispCtgrNo());
            jsonObject.put("ctgrNoPath", categoryVO.getCtgrNoPath());
            jsonObject.put("dispCtgrNm", categoryVO.getDispCtgrPath());
            jsonArray.add(jsonObject);
        }
        //rootObject.put("search", jsonArray);
        return new ResponseEntity(jsonArray, HttpStatus.OK);
    }

    @ApiOperation(value = "카테고리 등록 권한 여부 조회",
                  notes = "상품등록/수정 시 카테고리 등록 권한 여부를 조회한다.",
                  response = Boolean.class)
    @GetMapping("/check-auth/{dispCtgrNo}")
    public ResponseEntity<Boolean> getProductRegInfobyCtgrNo(HttpServletRequest request, HttpServletResponse response, @PathVariable long dispCtgrNo) throws Exception {
        long selMnbdNo = 0;
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if (auth != null)  {
            selMnbdNo = auth.getMemberNumber();
        }
        if (selMnbdNo == 0) throw new Exception(ProductExceptionMessage.AUTH_ERROR);

        MemberVO memberVO = new MemberVO();
        memberVO.setMemNO(selMnbdNo);
        MemberVO memInfo = customerService.getMemberInfo(memberVO);

        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setSelMnbdNo(selMnbdNo);
        categoryVO.setDispCtgrNo(dispCtgrNo);
        HashMap<String, Object> resultMap = categoryService.getProductRegPossibleCtgrNo(categoryVO);

        boolean isNotRegCategory = false;
        if(resultMap == null){
            isNotRegCategory = false;
        } else {
            if(memInfo != null && ("01".equals(memInfo.getMemClf()) || "Y".equals(memInfo.getCertCardYn()))) {
                resultMap.put("sellerAuth", "Y");
            }
            if("Y".equals(resultMap.get("sellerAuth"))) isNotRegCategory = true;
        }

        if(!categoryValidate.checkMobileNotRegCategory(dispCtgrNo)) {
            return new ResponseEntity(false, HttpStatus.OK);
        } else {
            // 일치하는 값이 없다면 카테고리 권한체크 값을 전달한다.
            return new ResponseEntity(isNotRegCategory, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "추천 카테고리 정보 조회",
                  notes = "상품등록/수정 시 추천 카테고리 정보를 조회한다.",
                  response = String.class)
    @GetMapping("/recommends/{prdNm}/{metaCtgrNm}")
    public ResponseEntity<String> getRecommendList(HttpServletRequest request, HttpServletResponse response, @PathVariable String prdNm, @PathVariable String metaCtgrNm) throws Exception {
        long selMnbdNo = 0;
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if (auth != null)  {
            selMnbdNo = auth.getMemberNumber();
        }
        if (selMnbdNo == 0) throw new Exception(ProductExceptionMessage.AUTH_ERROR);

        NickNameVO nickNameVO = customerService.getBaseNckNM(selMnbdNo);
        String nckNm = "";
        if(nickNameVO != null && nickNameVO.getNickNM() !=null) {
            nckNm = nickNameVO.getNickNM();
        }

        dBSwitchUtil.addKey(CacheKeyConstDef.SWH_USE_CTGR_RECOM);
        dBSwitchUtil.setUseCache(true);
        dBSwitchUtil.checkKey();

        List<CategoryRecommendationVO> crs = null;
        if(dBSwitchUtil.isExist(CacheKeyConstDef.SWH_USE_CTGR_RECOM)) {
            // 루트-리프가 정리된 추천점수 먹여진 카테고리 데이터.
            crs = categoryService.getCategoryRecommendations(nckNm, metaCtgrNm, prdNm, selMnbdNo);
        }

        if(crs == null) {
            return new ResponseEntity("", HttpStatus.OK);
        } else {
            return new ResponseEntity(getCategoryRecommendationResponseJSON(crs), HttpStatus.OK);
        }
    }

    private List<CategoryVO> _setDisplayCategoryBOJSONList(List<CategoryVO> ctgrList) {
        if (ctgrList != null) {
            String ctgrNm;
            String dispCtgrPath;
            for (int i = 0; i < ctgrList.size(); i++) {
                ctgrNm = ctgrList.get(i).getDispCtgrNm();
                if (StringUtil.isNotEmpty(ctgrNm)) {
                    ctgrList.get(i).setDispCtgrNm(StringUtil.convertJSONFormat(ctgrNm));
                }

                dispCtgrPath = ctgrList.get(i).getDispCtgrPath();
                if (StringUtil.isNotEmpty(ctgrNm)) {
                    ctgrList.get(i).setDispCtgrPath(StringUtil.convertJSONFormat(dispCtgrPath));
                }
            }
        }
        return ctgrList;
    }

    /**
     * 추천 카테고리를 JSON String으로 변환하면서, 그에 앞서 SO,PO 추천 카테고리 검색 정책에 맞게 검색된 추천 카테고리를 필터링.
     * 검색 정책 수정시 수정되야할 메소드
     * @param crs Categroy recommendations
     * @return 정책 적용되 필터링되어  JSON으로 변환된 category recommendations
     */
    private String getCategoryRecommendationResponseJSON(List<CategoryRecommendationVO> crs) {

        //정책상 예외로 추천 카테고리에서 제외해야 할 카테고리들
        long[] ctgrExceptions = {
            2967 // 도서/음반
            ,2878 //여행/숙박/항공
            ,117025 // e쿠폰/상품권
            ,1001122 //홈카서비스
            ,1001448 //렌탈/무료신청
        };

        //정책상 confidence limit
        double scoreLimit = 0.5d;

        //SO,PO 상품등록에 보낼 데이터 필터링 및 가공 시작
        Map<Long, CategoryRecommendationVO> m1 = new HashMap<Long, CategoryRecommendationVO>();
        Map<Long, CategoryRecommendationVO> m2 = new HashMap<Long, CategoryRecommendationVO>();
        Map<Long, CategoryRecommendationVO> m3 = new HashMap<Long, CategoryRecommendationVO>();
        Map<Long, CategoryRecommendationVO> m4 = new HashMap<Long, CategoryRecommendationVO>();

        for (CategoryRecommendationVO cr : crs) {
            switch (cr.getLevel()) {
                case 1:
                    m1.put(cr.getDispCtgrNo(), cr);
                    break;
                case 2:
                    m2.put(cr.getDispCtgrNo(), cr);
                    break;
                case 3:
                    m3.put(cr.getDispCtgrNo(), cr);
                    break;
                case 4:
                    m4.put(cr.getDispCtgrNo(), cr);
                    break;
            }
        }

        TreeMap<Double, String[]> rtnMap = new TreeMap<Double, String[]>();
        StringBuilder dispCtgrNoStr = new StringBuilder();
        StringBuilder ctgrNoStr = new StringBuilder();
        StringBuilder ctgrNmStr = new StringBuilder();

        String json = "";

        for (Map.Entry<Long, CategoryRecommendationVO> entry : m4.entrySet()) {

            CategoryRecommendationVO cr4 = entry.getValue();

            if (cr4.getSellerAuthYn() != null && cr4.getSellerAuthYn().equals("Y") && !cr4.isSellerAuthorized()) continue;

            Long ctgrNo4 = entry.getKey();
            String ctgrNm4 = cr4.getDispCtgrNm();

            ctgrNoStr.insert(0, ctgrNo4);
            ctgrNmStr.insert(0, ctgrNm4);

            CategoryRecommendationVO cr3 = m3.get(cr4.getHgrnkCtgrNo());

            ctgrNoStr.insert(0, cr3.getDispCtgrNo() + ">");
            ctgrNmStr.insert(0, cr3.getDispCtgrNm() + ">");

            CategoryRecommendationVO cr2 = m2.get(cr3.getHgrnkCtgrNo());

            ctgrNoStr.insert(0, cr2.getDispCtgrNo() + ">");
            ctgrNmStr.insert(0, cr2.getDispCtgrNm() + ">");

            CategoryRecommendationVO cr1 = m1.get(cr2.getHgrnkCtgrNo());

            ctgrNoStr.insert(0, cr1.getDispCtgrNo() + ">");
            ctgrNmStr.insert(0, cr1.getDispCtgrNm() + ">");

            dispCtgrNoStr.insert(0, cr4.getDispCtgrNo() + ":");

            String[] noNm = {
                dispCtgrNoStr.toString()
                , ctgrNoStr.toString()
                , ctgrNmStr.toString()
            };

            dispCtgrNoStr = new StringBuilder();
            ctgrNoStr = new StringBuilder();
            ctgrNmStr = new StringBuilder();

            boolean isExceptionCtgr = false;

            for(long exCtgrNo : ctgrExceptions) {

                if(cr1.getDispCtgrNo() == exCtgrNo ||
                    cr2.getDispCtgrNo() == exCtgrNo ||
                    cr3.getDispCtgrNo() == exCtgrNo ||
                    cr4.getDispCtgrNo() == exCtgrNo) {
                    isExceptionCtgr = true;
                    break;
                }
            }

            if(isExceptionCtgr) continue;


            if (rtnMap.containsKey(cr4.getScore())) {
                rtnMap.put(cr4.getScore() + 0.00001d, noNm);
            } else {
                rtnMap.put(cr4.getScore(), noNm);
            }
        }

        for (Map.Entry<Long, CategoryRecommendationVO> entry : m3.entrySet()) {

            CategoryRecommendationVO cr3 = entry.getValue();

            if (cr3.getIsLeaf() == 0) continue;
            if (cr3.getSellerAuthYn() != null && cr3.getSellerAuthYn().equals("Y") && !cr3.isSellerAuthorized()) continue;

            Long ctgrNo3 = entry.getKey();
            String ctgrNm3 = cr3.getDispCtgrNm();

            ctgrNoStr.insert(0, cr3.getDispCtgrNo());
            ctgrNmStr.insert(0, cr3.getDispCtgrNm());

            CategoryRecommendationVO cr2 = m2.get(cr3.getHgrnkCtgrNo());

            ctgrNoStr.insert(0, cr2.getDispCtgrNo() + ">");
            ctgrNmStr.insert(0, cr2.getDispCtgrNm() + ">");

            CategoryRecommendationVO cr1 = m1.get(cr2.getHgrnkCtgrNo());

            ctgrNoStr.insert(0, cr1.getDispCtgrNo() + ">");
            ctgrNmStr.insert(0, cr1.getDispCtgrNm() + ">");

            dispCtgrNoStr.insert(0, cr3.getDispCtgrNo() + ":");

            String[] noNm = {
                dispCtgrNoStr.toString()
                , ctgrNoStr.toString()
                , ctgrNmStr.toString()
            };

            dispCtgrNoStr = new StringBuilder();
            ctgrNoStr = new StringBuilder();
            ctgrNmStr = new StringBuilder();

            boolean isExceptionCtgr = false;

            for(long exCtgrNo : ctgrExceptions) {

                if(cr1.getDispCtgrNo() == exCtgrNo ||
                    cr2.getDispCtgrNo() == exCtgrNo ||
                    cr3.getDispCtgrNo() == exCtgrNo) {
                    isExceptionCtgr = true;
                    break;
                }
            }

            if(isExceptionCtgr) continue;

            if (rtnMap.containsKey(cr3.getScore())) {
                rtnMap.put(cr3.getScore() + 0.00001d, noNm);
            } else {
                rtnMap.put(cr3.getScore(), noNm);
            }
        }

        for (Map.Entry<Long, CategoryRecommendationVO> entry : m2.entrySet()) {

            CategoryRecommendationVO cr2 = entry.getValue();
            if (cr2.getIsLeaf() == 0) continue;
            if (cr2.getSellerAuthYn() != null && cr2.getSellerAuthYn().equals("Y") && !cr2.isSellerAuthorized()) continue;

            Long ctgrNo2 = entry.getKey();
            String ctgrNm2 = cr2.getDispCtgrNm();

            ctgrNoStr.insert(0, cr2.getDispCtgrNo());
            ctgrNmStr.insert(0, cr2.getDispCtgrNm());

            CategoryRecommendationVO cr1 = m1.get(cr2.getHgrnkCtgrNo());

            ctgrNoStr.insert(0, cr1.getDispCtgrNo() + ">");
            ctgrNmStr.insert(0, cr1.getDispCtgrNm() + ">");

            dispCtgrNoStr.insert(0, cr2.getDispCtgrNo() + ":");

            String[] noNm = {
                dispCtgrNoStr.toString()
                , ctgrNoStr.toString()
                , ctgrNmStr.toString()
            };

            dispCtgrNoStr = new StringBuilder();
            ctgrNoStr = new StringBuilder();
            ctgrNmStr = new StringBuilder();

            boolean isExceptionCtgr = false;

            for(long exCtgrNo : ctgrExceptions) {

                if(cr1.getDispCtgrNo() == exCtgrNo ||
                    cr2.getDispCtgrNo() == exCtgrNo) {
                    isExceptionCtgr = true;
                    break;
                }
            }

            if(isExceptionCtgr) continue;

            if (rtnMap.containsKey(cr2.getScore())) {
                rtnMap.put(cr2.getScore() + 0.00001d, noNm);
            } else {
                rtnMap.put(cr2.getScore(), noNm);
            }
        }

        for (Map.Entry<Long, CategoryRecommendationVO> entry : m1.entrySet()) {

            CategoryRecommendationVO cr1 = entry.getValue();
            if (cr1.getIsLeaf() == 0) continue;
            if (cr1.getSellerAuthYn() != null && cr1.getSellerAuthYn().equals("Y") && !cr1.isSellerAuthorized()) continue;

            Long ctgrNo1 = entry.getKey();
            String ctgrNm1 = cr1.getDispCtgrNm();

            ctgrNoStr.insert(0, cr1.getDispCtgrNo());
            ctgrNmStr.insert(0, cr1.getDispCtgrNm());

            String[] noNm = {
                ctgrNoStr.toString()
                , ctgrNmStr.toString()
            };

            ctgrNoStr = new StringBuilder();
            ctgrNmStr = new StringBuilder();

            boolean isExceptionCtgr = false;

            for(long exCtgrNo : ctgrExceptions) {

                if(cr1.getDispCtgrNo() == exCtgrNo)  {
                    isExceptionCtgr = true;
                    break;
                }
            }

            if(isExceptionCtgr) continue;

            if (rtnMap.containsKey(cr1.getScore())) {
                rtnMap.put(cr1.getScore() + 0.00001d, noNm);
            } else {
                rtnMap.put(cr1.getScore(), noNm);
            }
        }

        SortedMap<Double, String[]> sm = rtnMap.descendingMap();

        if(!sm.isEmpty() && Double.compare(sm.firstKey(),scoreLimit) < 0) {
            json = "\"\"";
        } else {
            json = new Gson().toJson(sm);
        }

        if(json.equals("{}"))
            json = "\"\"";
        return json;
    }
}
