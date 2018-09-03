package com.elevenst.terroir.product.registration.category.validate;

import com.elevenst.common.code.CacheKeyConstDef;
import com.elevenst.common.properties.PropMgr;
import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.terroir.product.registration.category.mapper.CategoryMapper;
import com.elevenst.terroir.product.registration.category.service.CategoryServiceImpl;
import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import com.elevenst.terroir.product.registration.delivery.code.DlvClfCdTypes;
import com.elevenst.terroir.product.registration.product.code.SelMthdCd;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.BaseVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.service.SellerServiceImpl;
import com.elevenst.terroir.product.registration.seller.vo.SellerAuthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CategoryValidate {

    @Autowired
    CategoryServiceImpl categoryServiceImpl;

    @Autowired
    SellerServiceImpl sellerService;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    PropMgr propMgr;

    private enum CategoryExceptionEnumTypes implements ExceptionEnumTypes {
        ADLUT_CTGR("PRD_REG", "CATEGORY001", "미성년 성인카테고리 등록 오류")
        ,SEL_LIMIT_CTGR_MAX("PRD_REG", "CATEGORY002", "최대구매수량이 반드시 선택되어야 합니다.")
        ,SEL_LIMIT_MAX("PRD_REG", "CATEGORY003", "현재 선택하신 카테고리는 최대구매수량은 {$1}개 이하로 입력하셔야 합니다.")
        ,NOT_AUTH("PRD_REG", "CATEGORY004", "카테고리 등록 권한이 없습니다.")
        ,NOT_USE_CTGR("PRD_REG", "CATEGORY005", "해당 카테고리는 현재 사용하지 않는 카테고리입니다.")
        ,NOT_KIND_DISP_CTGR("PRD_REG", "CATEGORY006", "해당 카테고리는 전시카테고리가 아닙니다.")
        ,NOT_STAT_DISP_CTGR("PRD_REG", "CATEGORY007", "해당 카테고리는 '전시' 상태가 아닙니다.")
        ,IS_SVC_CUPN("PRD_REG", "CATEGORY008", "서비스쿠폰 상품은 상품등록/수정이 불가합니다.")
        ,MUST_HAVE_CTGR("PRD_REG", "CATEGORY009", "카테고리 정보가 없습니다.")
        ,HAVE_CHILD_CTGR("PRD_REG", "CATEGORY010", "해당 카테고리는 마지막 카테고리가  아닙니다.")
        ,NOT_USED_PRD_CTGR("PRD_REG", "CATEGORY011", "선택한 카테고리는 중고 상품을 등록 할 수 없는 카테고리 입니다.")
        ,ONLY_USED_PRD_CTGR("PRD_REG", "CATEGORY012", "중고판매만 가능한 카테고리입니다.")
        ,NOT_CHANGE_CTGR("PRD_REG", "CATEGORY013", "상위 카테고리를 수정할 수 있는 권한이 없습니다.")
        ,HAVE_NOT_CTGR("PRD_REG", "CATEGORY014", "해당 상품에 대한 카테고리 정보가 없습니다.")
        ,NOT_OPT_CTGR("PRD_REG", "CATEGORY014", "옵션등록 불가능한 카테고리 입니다.")
        ;

        private final String msgGroupId;
        private final String code;
        private final String message;

        CategoryExceptionEnumTypes(String msgGroupId, String code, String message){
            this.msgGroupId = msgGroupId;
            this.code = code;
            this.message = message;
        }

        @Override
        public String getMsgGroupId() {
            return msgGroupId;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    public void checkAdultCategory(ProductVO productVO) throws ProductException{
        if("N".equals(productVO.getCategoryVO().getAdltCrtfYn())
            && "Y".equals(productVO.getCategoryVO().getAdultCtgr()))
        {
            throw new ProductException(CategoryValidate.CategoryExceptionEnumTypes.ADLUT_CTGR);
        }
    }

    public void checkCategoryLimitQty(ProductVO productVO) throws ProductException{
        if(productVO.getCategoryVO().getMaxLimitQty() > 0){
            if(productVO.getBaseVO().getSelLimitQty() <= 0)
                throw new ProductException(CategoryExceptionEnumTypes.SEL_LIMIT_CTGR_MAX);
            if(productVO.getCategoryVO().getMaxLimitQty() < productVO.getBaseVO().getSelLimitQty())
                throw new ProductException(CategoryExceptionEnumTypes.SEL_LIMIT_MAX, String.valueOf(productVO.getCategoryVO().getMaxLimitQty()));
        }
    }

    /**
     * 신선 카테고리 여부 조회
     * @param prdTypCd
     * @param dispCtgrNo
     * @param selMnbdNo
     * @return String
     * @throws ProductException
     */
    public boolean isFreshCategory(String prdTypCd, long dispCtgrNo, long selMnbdNo) throws ProductException {
        if(GroupCodeUtil.isContainsDtlsCd(prdTypCd, PrdTypCd.BRANCH_DELIVERY_MART, PrdTypCd.MART)) {
            ProductVO productVO = new ProductVO();
            productVO.setBaseVO(new BaseVO());
            productVO.getBaseVO().setPrdTypCd(prdTypCd);
            productVO.setDispCtgrNo(dispCtgrNo);
            productVO.setSelMnbdNo(selMnbdNo);

            return "Y".equals(categoryServiceImpl.getFreshCategoryYn(productVO));
        }
        return false;
    }

    public void checkCategoryStatus(ProductVO productVO) throws ProductException{
        if(productVO.getCategoryVO() == null){
            new ProductException(CategoryExceptionEnumTypes.MUST_HAVE_CTGR);
        }

        if("Y".equals(productVO.getCategoryVO().getUseYn())){
            new ProductException(CategoryExceptionEnumTypes.NOT_USE_CTGR);
        }

        if(!ProductConstDef.DISP_CTGR_KIND_CD_DISP.equals(productVO.getCategoryVO().getDispCtgrKindCd())){
            new ProductException(CategoryExceptionEnumTypes.NOT_KIND_DISP_CTGR);
        }

        if(!ProductConstDef.DISP_CTGR_STAT_CD_DISP.equals(productVO.getCategoryVO().getDispCtgrStatCd())){
            new ProductException(CategoryExceptionEnumTypes.NOT_STAT_DISP_CTGR);
        }

        if(productVO.getCategoryVO().getChildCnt() > 0){
            new ProductException(CategoryExceptionEnumTypes.HAVE_CHILD_CTGR);
        }

        if(GroupCodeUtil.equalsDtlsCd(productVO.getCategoryVO().getPrdTypCd(), PrdTypCd.SERVICE_COUPON)){
            new ProductException(CategoryExceptionEnumTypes.IS_SVC_CUPN);
        }
    }

    public void checkCtgrSellerAuth(ProductVO productVO) throws ProductException{

        this.checkCategoryStatus(productVO);

        if("Y".equals(productVO.getCategoryVO().getSellerAuthYn())){

            int sellerAuthCnt = categoryMapper.getDispCtgrSellerAuthCnt(productVO);

            if(sellerAuthCnt < 1){
                new ProductException(CategoryExceptionEnumTypes.NOT_AUTH);
            }
        }

    }

    public void checkCategoryUsedPrdReg(ProductVO productVO) throws ProductException{
        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelMthdCd(), SelMthdCd.USED)){
            if(ProductConstDef.DISP_CTGR_USED_REG_NOT.equals(productVO.getCategoryVO().getUsedRegCD())){
                new ProductException(CategoryExceptionEnumTypes.NOT_USED_PRD_CTGR);
            }else{

                this.checkProductExptCategory(productVO);
            }

            if(ProductConstDef.GLOBAL_LCTGR_NO.indexOf("|"+productVO.getCategoryVO().getLctgrNo()+"|") >= 0){
                new ProductException(CategoryExceptionEnumTypes.NOT_USED_PRD_CTGR);
            }
        }else{
            if(ProductConstDef.DISP_CTGR_USED_REG_ONLY.equals(productVO.getCategoryVO().getUsedRegCD())){
                new ProductException(CategoryExceptionEnumTypes.ONLY_USED_PRD_CTGR);
            }
        }
    }

    public boolean isProductExptCategory(HashMap map) throws ProductException{
        boolean isExptCtgr = false;
        int cnt = categoryMapper.getProductExptCategory(map);
        if(cnt > 0){
            isExptCtgr = true;
        }
        return isExptCtgr;
    }


    public void checkProductExptCategory(ProductVO productVO) throws ProductException{
        HashMap map = new HashMap();
        map.put("dispCtgrNo", productVO.getDispCtgrNo());
        map.put("hgrnkYn", "Y");

        ArrayList<String> exptTypeList = new ArrayList<String>();
        exptTypeList.add("106");
        map.put("exptTypeList",exptTypeList);

        if(!(this.isProductExptCategory(map) && "01".equals(productVO.getMemberVO().getMemClf()))){
            new ProductException(CategoryExceptionEnumTypes.NOT_USED_PRD_CTGR);
        }
    }


    public void checkChangeCategory(ProductVO preProductVO, ProductVO productVO) throws ProductException{

        CategoryVO asisCtgrVO = preProductVO.getCategoryVO();
        CategoryVO tobeCtgrVO = productVO.getCategoryVO();

        if(asisCtgrVO == null && tobeCtgrVO == null){
            return;
        }

        if(asisCtgrVO.getDispCtgrNo() == tobeCtgrVO.getDispCtgrNo()){
            return;
        }

        if(asisCtgrVO.getDispCtgrNo() == tobeCtgrVO.getHgrnkCtgrNo() || asisCtgrVO.getHgrnkCtgrNo() == tobeCtgrVO.getHgrnkCtgrNo()){
            return;
        }

        throw new ProductException(CategoryExceptionEnumTypes.NOT_CHANGE_CTGR);
    }

    public void hasCategoryInfo(ProductVO productVO) throws ProductException{
        if(productVO.getCategoryVO() == null){
            throw new ProductException(CategoryExceptionEnumTypes.HAVE_NOT_CTGR);
        }
    }

    public void checkCategoryBaseInfo(ProductVO productVO) throws ProductException{
        if(productVO.getDispCtgrNo() < 1){
            throw new ProductException(CategoryExceptionEnumTypes.HAVE_NOT_CTGR);
        }
    }

    public void checkOfferDispLmt(ProductVO productVO) throws ProductException{
        if("Y".equals(productVO.getCategoryVO().getOfferDispLmtYn())){
            if(productVO.getOptionVO().isExistOpt()){
                throw new ProductException(CategoryExceptionEnumTypes.NOT_OPT_CTGR);
            }
            if(productVO.getProductAddCompositionVOList() != null || productVO.getProductAddCompositionVOList().size() > 0){
                throw new ProductException("추가구성상품이 불가능한 카테고리 입니다.");
            }
            if(!GroupCodeUtil.isContainsDtlsCd(productVO.getBaseVO().getDlvClf(),
                                               DlvClfCdTypes.ELEVENST_DELIVERY,
                                               DlvClfCdTypes.ELEVENST_ABROAD_DELIVERY)){
                if(ProductConstDef.BACKOFFICE_TYPE.equals(productVO.getChannel())){
                    if(!ProductConstDef.DLV_CST_INST_BASI_CD_FREE.equals(productVO.getBaseVO().getDlvCstInstBasiCd()))
                        throw new ProductException("무료배송만 설정가능한 카테고리 입니다.");
                }else if(!(!ProductConstDef.DLV_WY_CD_NONE_DLV.equals(productVO.getBaseVO().getDlvWyCd()) && "IT".equals(productVO.getBaseVO().getFrCtrCd()))){
                    if(!ProductConstDef.DLV_CST_INST_BASI_CD_FREE.equals(productVO.getBaseVO().getDlvCstInstBasiCd()))
                        throw new ProductException("무료배송만 설정가능한 카테고리 입니다.");
                }
            }
        }
    }

    public void checkParentCategory(ProductVO productVO) throws ProductException{
        List<CategoryVO> categoryVOList = categoryMapper.getDisplayCategoryParentList(productVO.getDispCtgrNo());
        if(categoryVOList != null && categoryVOList.size() < 1){
            throw new ProductException("카테고리는 최하위 카테고리를 선택하여야 합니다.");
        }
        for(CategoryVO categoryVO : categoryVOList){
            if(!"Y".equals(categoryVO.getUseYn())){
                throw new ProductException("해당 카테고리는 현재 사용하지 않는 카테고리입니다.");
            }
            if(!ProductConstDef.DISP_CTGR_KIND_CD_DISP.equals(categoryVO.getDispCtgrKindCd())){
                throw new ProductException("해당 카테고리는 전시카테고리가 아닙니다.");
            }
            if(!ProductConstDef.DISP_CTGR_STAT_CD_DISP.equals(categoryVO.getDispCtgrStatCd())
                && !ProductConstDef.DISP_CTGR_STAT_CD_BEFORE_DISP.equals(categoryVO.getDispCtgrStatCd())){
                throw new ProductException("해당 카테고리는 '전시' 상태가 아닙니다.");
            }
        }
    }

    public void checkAbrdCategory(ProductVO productVO) throws ProductException{
        if("Y".equals(productVO.getBaseVO().getAbrdBrandYn())){
            if(!"Y".equals(productVO.getCategoryVO().getAbrdBrandYn())){
                throw new ProductException("해외 쇼핑 셀러는 해외 쇼핑 카테고리만 등록 가능합니다.");
            }
            if (!ProductConstDef.PRD_MEM_TYPE_GLOBAL.equals(productVO.getMemberVO().getMemTypCD())) {
                throw new ProductException("일반 셀러는  해외 쇼핑 카테고리 등록이 불가능 합니다.");
            }
        }else{
            if ("Y".equals(productVO.getCategoryVO().getAbrdBrandYn())
                && !ProductConstDef.PRD_MEM_TYPE_SUPPLIER.equals(productVO.getMemberVO().getMemTypCD())
                && !productVO.getMemberVO().isFrSeller()) {
                throw new ProductException("일반 셀러는  해외 쇼핑 카테고리 등록이 불가능 합니다.");
            }
        }

        // 모바일카테고리 여부 체크 //TODO CYB 체크 필요 (왜 하는지 잘 모르겠음.)
        if (!productVO.getCategoryVO().getMobileYn().equals(productVO.getMobileYn())){
            productVO.setMobileYn(productVO.getCategoryVO().getMobileYn());
        }

        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelMthdCd(), SelMthdCd.COBUY)
            && ProductConstDef.PRD_CTGR_NO_BAROMART.equals(productVO.getCategoryVO().getLctgrNo())){
            throw new ProductException("바로마트 카테고리는 공동구매로 등록할 수 없습니다.");
        }

        if(ProductConstDef.PRD_CTGR_NO_BAROMART.equals(productVO.getCategoryVO().getLctgrNo())){
            throw new ProductException("바로마트 카테고리는 등록할 수 없습니다.");
        }

    }

    /**
     * 모바일에서 등록할 수 없는 카테고리인지 체크한다.
     * @param dispCtgrNo
     * @return
     */
    public boolean checkMobileNotRegCategory(long dispCtgrNo) {
        CategoryVO retCategoryVO = categoryServiceImpl.getDpDispCtgrListInfo(dispCtgrNo);

        return this.checkMobileNotRegCategory(retCategoryVO);
    }

    private boolean checkMobileNotRegCategory(CategoryVO categoryVO) throws ProductException{
        boolean retBoolean = true;
        String[] setCategoryArr = new String[5];
        setCategoryArr[0] = String.valueOf(categoryVO.getRootCtgrNo());
        setCategoryArr[1] = String.valueOf(categoryVO.getMctgrNo());
        setCategoryArr[2] = String.valueOf(categoryVO.getSctgrNo());
        setCategoryArr[3] = String.valueOf(categoryVO.getLctgrNo());
        setCategoryArr[4] = String.valueOf(categoryVO.getDispCtgrNo());
        // 넘겨받은 카테고리 대/중/소/세 하나라도 일치하는 값이 있다면 등록할 수 없는 카테고리로 처리한다.
        if(setCategoryArr != null) {
            ProductVO productVO = new ProductVO();
            productVO.setSelMnbdNo(0);
            List<SellerAuthVO> sellerAuthList = sellerService.getPdSellerAuth(productVO, ProductConstDef.AUTH_TYP_CD_NOT_REG_MOBILE_CATEGORY);
            for(String categoryStr : setCategoryArr) {
                if(!retBoolean) break;
                for(SellerAuthVO sellerAuthVO : sellerAuthList) {
                    if(sellerAuthVO.getAuthObjNo().equals(categoryStr)) {
                        retBoolean = false;
                        break;
                    }
                }
            }
        }
        return retBoolean;
    }

    public void checkMobileNotRegCategory(ProductVO productVO) throws ProductException{
        if(!this.checkMobileNotRegCategory(productVO.getCategoryVO())){
            throw new ProductException("해당 카테고리는 현재 모바일 상품등록이 불가능합니다. PC 셀러오피스를 이용해주세요.");
        }
    }
}
