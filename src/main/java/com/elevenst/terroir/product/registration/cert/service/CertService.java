package com.elevenst.terroir.product.registration.cert.service;

import com.elevenst.common.process.RegistrationInfoConverter;
import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.category.code.PD247_CertInfoRequireCtgr;
import com.elevenst.terroir.product.registration.cert.exception.CertException;
import com.elevenst.terroir.product.registration.cert.validate.CertValidate;
import com.elevenst.terroir.product.registration.common.service.CommonServiceImpl;
import com.elevenst.terroir.product.registration.common.vo.SyCoDetailVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.cert.ProductCertFactory;
import com.elevenst.terroir.product.registration.cert.thirdparty.ProductCertValidApiService;
import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import com.elevenst.terroir.product.registration.cert.vo.ProductCertInfoVO;
import com.elevenst.terroir.product.registration.cert.vo.ProductCertParamVO;
import com.elevenst.terroir.product.registration.cert.code.PD065_PrdCertTypeCd;
import com.elevenst.terroir.product.registration.category.mapper.CategoryMapper;
import com.elevenst.terroir.product.registration.cert.mapper.CertMapper;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 *  상품인증 서비스 영역
 * */
@Slf4j
@Service
public class CertService implements RegistrationInfoConverter<ProductVO>{

    private final int MAX_CERT_CNT_PER_REGISTED = 100;

    @Autowired
    private CommonServiceImpl commonService;
    @Autowired
    private CertMapper certMapper;
    @Autowired
    private CertValidate certValidate;
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductCertFactory productCertFactory;


    public Map<String, Object> getCertSelectItems(long dispCtgrNo) {

        List<Map<String, Object>> certInfo = this.getProductCertInfoCamelCase(dispCtgrNo);

        String certinfoCtgrNo = "";
        if (certInfo != null && certInfo.size() > 0) {
            certinfoCtgrNo = String.valueOf(certInfo.get(0).get("dispCtgrNo"));
        }

        String requiredYn = (commonService.getCodeDetail("PD247", certinfoCtgrNo) != null) ? "Y" : "N";

        Map<String, Object> ret = new HashMap<>();

        ret.put("result", certInfo);
        ret.put("CERT_INFO_REQUIRED_YN", requiredYn);


        return ret;
    }



    public List<Map<String,Object>> getProductCertInfoCamelCase(long dispCtgrNo) {
        List<Map<String, Object>> certInfo = new ArrayList();

        List<Long> ctgrNoList = new ArrayList();
        ctgrNoList.add(dispCtgrNo);

        List<CategoryVO> parentCtgrBOList = getDisplayCategoryParentList(null, dispCtgrNo);

        for(CategoryVO elem : parentCtgrBOList) {
            ctgrNoList.add(elem.getDispCtgrNo());
        }

        for(Long ctgrNo : ctgrNoList) {
            certInfo = certMapper.getProductCertInfoCamelCase(ctgrNo);

            if (CollectionUtils.isNotEmpty(certInfo)) {
                break;
            }
        }

        return certInfo;
    }

    public List<Map<String,Object>> getProductCertInfo(long dispCtgrNo) {
        List<Map<String, Object>> certInfo = new ArrayList();

        List<Long> ctgrNoList = new ArrayList();
        ctgrNoList.add(dispCtgrNo);

        List<CategoryVO> parentCtgrBOList = getDisplayCategoryParentList(null, dispCtgrNo);

        for(CategoryVO elem : parentCtgrBOList) {
            ctgrNoList.add(elem.getDispCtgrNo());
        }

        for(Long ctgrNo : ctgrNoList) {
            certInfo = certMapper.getProductCertInfo(ctgrNo);

            if (CollectionUtils.isNotEmpty(certInfo)) {
                break;
            }
        }

        return certInfo;
    }

    private List<CategoryVO> getDisplayCategoryParentList(Object obj, long dispCtgrNo) {

        List resultList = new ArrayList();
        String dataKey = "displayCategoryParentList_" + dispCtgrNo;

        if(obj == null) {
            resultList = categoryMapper.getDisplayCategoryParentList(dispCtgrNo);
        }
        else if (obj instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest)obj;

            if(request.getAttribute(dataKey) != null) {
                resultList = (List)request.getAttribute(dataKey);
            }
            else {
                resultList = categoryMapper.getDisplayCategoryParentList(dispCtgrNo);

                if(resultList != null && resultList.size() > 0) {
                    request.setAttribute(dataKey, resultList);
                }
            }

        }
        return resultList;
    }


    /**
     * 안전인증정보 생성 & 유효성 체크
     * @param @{@link ProductCertParamVO}
     *      dispCtgrNo : leaf 카테고리 번호
     *      certInfoCnt : 인증정보 갯수
     *      certType(list) : 인증유형 코드(PD065)
     *      certKey(list) : 인증번호
     *
     * certInfoCnt 파라미터가 필요한지......?? certType과 certKey의 카운트만 보아도..
     * */
    public List<ProductCertInfoVO> setProductCertVOList(ProductCertParamVO paramVO) {

        final int MAX_PRODUCT_CERTINFO_CNT = 100;

        List<ProductCertInfoVO> resultList = new ArrayList<ProductCertInfoVO>();

        if (paramVO.getDispCtgrNo() <= 0 ) {
            throw new ProductException("카테고리번호 미입력.");
        }
        if (paramVO.getCertInfoCnt() > MAX_PRODUCT_CERTINFO_CNT) {
            throw new ProductException("인증정보는 최대 " + MAX_PRODUCT_CERTINFO_CNT + "개 까지 입력 가능합니다.");

        }

        List<Map<String, Object>> certInfoList = getProductCertInfo(paramVO.getDispCtgrNo());
        if (CollectionUtils.isEmpty(certInfoList) && paramVO.getCertInfoCnt() > 0 ) {
            throw new ProductException("인증정보를 등록할 수 없는 카테고리입니다. 카테고리 번호 : " + paramVO.getDispCtgrNo());

        }
        else if (CollectionUtils.isNotEmpty(certInfoList)) {

            List<String> certTypedList = paramVO.getCertTypeCd();
            List<String> certKeyList = paramVO.getCertKey();
            int existCnt = 0;

            if (certTypedList.size() != certKeyList.size()) {
                throw new ProductException("인증유형과 인증번호의 개수가 다릅니다. 올바른 인증유형과 인증번호를 입력해 주세요.");
            }

            for(int i = 0; i < certTypedList.size(); i++) {

                String certType = certTypedList.get(i);
                String certKey = certKeyList.get(i);

                if ((StringUtils.isEmpty(certType) || StringUtils.equals("인증유형 선택", certType)) &&
                    (StringUtils.isEmpty(certKey) || StringUtils.equals("인증번호 입력", certKey))) {
                    continue;
                }
                existCnt++;

                boolean notExistCertType = true;
                for (int j = 0 ; j < certInfoList.size() ; j++) {
                    if (StringUtils.equals(certType, (String)certInfoList.get(j).get("DTLSCD"))) {
                        notExistCertType = false;
                        break;
                    }
                }
                if(notExistCertType) {
                    throw new ProductException("유효하지 않은 인증유형 코드 입니다.");
                }

                if (!GroupCodeUtil.isContainsDtlsCd(certType,
                                                   PD065_PrdCertTypeCd.PD065_124_LIFE_PRODUCER_SUITABLE,
                                                   PD065_PrdCertTypeCd.PD065_127_ELECTRONIC_PRODUCER_SUITABLE,
                                                   PD065_PrdCertTypeCd.PD065_130_CHILD_PRODUCER_SUITABLE)
                    && StringUtils.isEmpty(certKey)) {
                    throw new ProductException("인증번호는 반드시 입력하셔야 합니다.");
                }

                if (GroupCodeUtil.equalsDtlsCd(certType, PD065_PrdCertTypeCd.PD065_131_NOT_APPLICABLE)) {
                    certKey = "해당없음";
                }
                if (GroupCodeUtil.equalsDtlsCd(certType, PD065_PrdCertTypeCd.PD065_132_SEE_DETAILS)) {
                    certKey = "상품상세설명참조";
                }
                if(GroupCodeUtil.isContainsDtlsCd(certType,
                                                   PD065_PrdCertTypeCd.PD065_124_LIFE_PRODUCER_SUITABLE,
                                                   PD065_PrdCertTypeCd.PD065_127_ELECTRONIC_PRODUCER_SUITABLE,
                                                   PD065_PrdCertTypeCd.PD065_130_CHILD_PRODUCER_SUITABLE)) {
                    certKey = "인증번호 없음";
                }

                if (certType.getBytes().length > 10) {
                    throw new ProductException("인증유형은 10byte(한글5자,영문/숫자10자) 이하로 입력해야 합니다.");

                }
                if (certKey.getBytes().length > 100) {
                    throw new ProductException("인증번호는 100byte(한글50자,영문/숫자100자) 이하로 입력해야 합니다.");
                }

                //인증데이터 검사
                ProductCertValidApiService certSvc = productCertFactory.createService(certType, certKey);
                boolean isCertAuth = certSvc.doRequestApi(certType, certKey);
                if (!isCertAuth) {
                    throw new ProductException("인증번호가 유효하지 않습니다. 인증번호를 다시 확인해주세요.");
                }

                ProductCertInfoVO vo = new ProductCertInfoVO();
                vo.setPrdNo(paramVO.getPrdNo());
                vo.setCertType(certType);
                vo.setCertKey(certKey);
                resultList.add(vo);
            }

            String certInfoCategory = String.valueOf(certInfoList.get(0).get("DISPCTGRNO"));

            if(GroupCodeUtil.isContainsDtlsCd(certInfoCategory, PD247_CertInfoRequireCtgr.class) && existCnt == 0) {
                throw new ProductException("인증유형 및 인증번호를 입력해주세요");

            }
        }
        return resultList;
    }

    public void mergeProductCertInfoWithExistedInfo(ProductVO productVO) {


        List<ProductCertInfoVO> newProductCertInfoVOList1 = productVO.getProductCertInfoVOList();
        if(newProductCertInfoVOList1 != null){

            if(newProductCertInfoVOList1.size() > MAX_CERT_CNT_PER_REGISTED) {
                throw new CertException("상품 인증정보 갯수 초과 : " + newProductCertInfoVOList1.size());
            }
        }else{
            ProductCertInfoVO elem = new ProductCertInfoVO();
            elem.setPrdNo(productVO.getPrdNo());
            elem.setCertNo("0");
            certMapper.updateCertInfoUseToN(elem);
            return;
        }
        List<ProductCertInfoVO> oldProductCertInfoVOList1 = certMapper.getProductCertList(productVO.getPrdNo());
        List<ProductCertInfoVO> newProductCertInfoVOList2 = new ArrayList<ProductCertInfoVO>(newProductCertInfoVOList1);
        List<ProductCertInfoVO> oldProductCertInfoVOList2 = new ArrayList<ProductCertInfoVO>(oldProductCertInfoVOList1);

        // @ProductCertInfoVO.equals Override함. 참고.
        newProductCertInfoVOList1.removeAll(oldProductCertInfoVOList1); //인증정보 추가용 리스트.
        oldProductCertInfoVOList2.removeAll(newProductCertInfoVOList2); //인증정보 삭제용 리스트.

        for(ProductCertInfoVO elem : oldProductCertInfoVOList2) { //삭제 먼저 진행
            elem.setPrdNo(productVO.getPrdNo());
            elem.setCreateNo(productVO.getCreateNo());
            elem.setUpdateNo(productVO.getUpdateNo());
            certMapper.updateCertInfoUseToN(elem);
        }

        boolean isQcVerifiedStat = true;
        for(ProductCertInfoVO elem : newProductCertInfoVOList1) { //추가
            isQcVerifiedStat = false;
            if(StringUtil.isNotEmpty(elem.getCertKey()) && StringUtil.isNotEmpty(elem.getCertType())){
                elem.setPrdNo(productVO.getPrdNo());
                elem.setCreateNo(productVO.getCreateNo());
                elem.setUpdateNo(productVO.getUpdateNo());
                certMapper.insertNewCertInfo(elem);
            }
        }

        if(productVO != null && !isQcVerifiedStat) {
            productVO.getBaseVO().setQCVerifiedStat(true);
        }
    }

    public void setCertInfoProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        this.setVODefaultValue(productVO);
    }

    private void setVODefaultValue(ProductVO productVO) {

        //certDownYn 기본 N으로 세팅
        String certDownYn = StringUtils.isEmpty(productVO.getBaseVO().getCertDownYn()) ? "N" : productVO.getBaseVO().getCertDownYn();
        productVO.getBaseVO().setCertDownYn(certDownYn);

    }

    public void insertCertInfoProcess(ProductVO productVO) throws ProductException{
        this.mergeProductCertInfoWithExistedInfo(productVO);
    }

    public void updateCertInfoProcess(ProductVO productVO) throws ProductException{
        this.mergeProductCertInfoWithExistedInfo(productVO);
    }

    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) {
        this.convertCertInfo(productVO);
        this.convertPinProductCertInfo(productVO);
        certValidate.checkCertTypeInfo(productVO);
    }


    private boolean isCertTypeAndCertKeyNotModified(String certType, String certKey) {

        if (StringUtil.isEmpty(certType) || StringUtil.equals(certType, "인증유형 선택")
            && StringUtil.isEmpty(certKey) || StringUtil.equals(certType, "인증번호 입력")) {
            return true;
        }

        return false;
    }


    private void convertPinProductCertInfo(ProductVO productVO) throws ProductException{
        if(productVO.isTown()){
            // 인증 타입부터 먼저 세팅
            productVO.getBaseVO().setCertTypCd(this.getMemberCertType(productVO.getSelMnbdNo()));
            if(!ProductConstDef.CERT_TYPE_102.equals(productVO.getBaseVO().getCertTypCd())){
                if("Y".equals(productVO.getBaseVO().getCertDownYn())){
                    productVO.getBaseVO().setCertTypCd(ProductConstDef.CERT_TYPE_101);
                }else{
                    productVO.getBaseVO().setCertTypCd(ProductConstDef.CERT_TYPE_100);
                }
            }
        }
    }

    private void convertCertInfo(ProductVO productVO) throws ProductException{


        String certType = "";
        String certKey = "";

        // 인증정보 관련 정보 조회
        List<Map<String,Object>> certInfoList = this.getProductCertInfo(productVO.getDispCtgrNo());
        if(productVO.getProductCertInfoVOList() != null
            && productVO.getProductCertInfoVOList().size() > 0
            && CollectionUtils.isEmpty(certInfoList)) { // 해당 카테고리번호를 기준으로 직접 DB확인을 해 보니 조회되지 않을 경우
            throw new ProductException("인증정보를 등록할 수 없는 카테고리입니다. 카테고리 번호 : " + productVO.getDispCtgrNo());
        }

        if (CollectionUtils.isNotEmpty(certInfoList)) {
            String certInfoCtgr = String.valueOf(certInfoList.get(0).get("DISPCTGRNO"));
            SyCoDetailVO syCoDetailVO = commonService.getCodeDetail("PD247", certInfoCtgr);
            if(syCoDetailVO != null){
                if(productVO.getProductCertInfoVOList() == null
                    || productVO.getProductCertInfoVOList().size() < 1)
                {
                    throw new ProductException("인증유형 및 인증번호를 입력해주세요");
                }
            }else{
                return;
            }
        }

        //최대 갯수 제한
        if(productVO.getProductCertInfoVOList().size() > MAX_CERT_CNT_PER_REGISTED ) {
            throw new ProductException("인증정보는  최대" + MAX_CERT_CNT_PER_REGISTED + "개까지 입력 가능합니다.");
        }


        for(ProductCertInfoVO productCertInfoVO : productVO.getProductCertInfoVOList()){
            certType = productCertInfoVO.getCertType();
            certKey = productCertInfoVO.getCertKey();

            if(this.isCertTypeAndCertKeyNotModified(productCertInfoVO.getCertType(), productCertInfoVO.getCertKey())){
                continue;
            }

            if(certValidate.checkCertKeyNoNeed(productCertInfoVO.getCertType())){
                certKey = setDefaultCertKey(productCertInfoVO.getCertType());
            }

            certValidate.checkCertInfo(certType, certKey, certInfoList);


            //인증데이터 검사
            ProductCertValidApiService certSvc = productCertFactory.createService(certType, certKey);
            boolean isCertAuth = certSvc.doRequestApi(certType, certKey);
            if (!isCertAuth) {
                throw new ProductException("인증번호가 유효하지 않습니다. 인증번호를 다시 확인해주세요.");
            }

        }
    }


    private String setDefaultCertKey(String certType) throws ProductException{

        String result = "";

        if (GroupCodeUtil.equalsDtlsCd(certType, PD065_PrdCertTypeCd.PD065_131_NOT_APPLICABLE)) {
            result = "해당없음";
        }
        if (GroupCodeUtil.equalsDtlsCd(certType, PD065_PrdCertTypeCd.PD065_132_SEE_DETAILS)) {
            result = "상품상세설명참조";
        }
        if (GroupCodeUtil.isContainsDtlsCd(certType,
                                           PD065_PrdCertTypeCd.PD065_124_LIFE_PRODUCER_SUITABLE,
                                           PD065_PrdCertTypeCd.PD065_127_ELECTRONIC_PRODUCER_SUITABLE,
                                           PD065_PrdCertTypeCd.PD065_130_CHILD_PRODUCER_SUITABLE)) {
            result = "인증번호 없음";
        }

        return result;
    }

    public String getMemberCertType(long memNo) throws ProductException{
        String certType = certMapper.getMemberCertType(memNo);
        if(certType == null){
            certType = "";
        }
        return certType;
    }

    public List getProductCertList(long prdNo) throws CertException {
        return certMapper.getProductCertList(prdNo);
    }
}
