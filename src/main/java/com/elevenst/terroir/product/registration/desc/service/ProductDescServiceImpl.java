package com.elevenst.terroir.product.registration.desc.service;

import com.elevenst.common.process.RegistrationInfoConverter;
import com.elevenst.common.util.CodeEnumUtil;
import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.SellerProductDetailUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.desc.code.PrdDescTypCd;
import com.elevenst.terroir.product.registration.desc.code.PrdDtlTypCd;
import com.elevenst.terroir.product.registration.desc.entity.PdPrdDesc;
import com.elevenst.terroir.product.registration.desc.exception.ProductDescServiceException;
import com.elevenst.terroir.product.registration.desc.mapper.ProductDescMapper;
import com.elevenst.terroir.product.registration.desc.message.ProductDescServiceExceptionMessage;
import com.elevenst.terroir.product.registration.desc.validate.ProductDescValidate;
import com.elevenst.terroir.product.registration.desc.vo.ProductDescVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.entity.PdPrd;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.service.ProductServiceImpl;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class ProductDescServiceImpl implements RegistrationInfoConverter<ProductVO> {

    @Autowired
    private ProductDescMapper productDescMapper;

    @Autowired
    private ProductDescValidate productDescValidate;

    @Autowired
    ProductServiceImpl productService;






    public void registerNonServiceVoucherTypeProductDesc(ProductVO productVO) throws ProductDescServiceException {

        try {

            productDescValidate.isPrdTypCdEmpty(productVO.getBaseVO().getPrdTypCd());
            productDescValidate.isPrdTypeCdNonServiceVoucher(productVO.getBaseVO().getPrdTypCd());
            productDescValidate.isPrdCopyYnEmpty(productVO.getPrdCopyYn());

            if(productVO.getReRegPrdNo() > 0 && !productVO.getPrdCopyYn().equals("Y")) { //재등록

                this.insertProductDescReReg(productVO.getReRegPrdNo(), productVO.getPrdNo());

            } else {
                ProductDescVO productDescVO = null;
                if (productVO.getProductDescVOList() != null) {
                    for (int i=0;i<productVO.getProductDescVOList().size();i++) {
                        productDescVO = productVO.getProductDescVOList().get(i);

                        // 데이터가 없을 경우 Insert 처리 안하기 위함.
                        if( "Y".equals(productDescVO.getClobTypYn()) && StringUtil.isEmpty(productDescVO.getPrdDescContClob()) ) {
                            continue;
                        } else if( "N".equals(productDescVO.getClobTypYn()) && StringUtil.isEmpty(productDescVO.getPrdDescContVc()) ) {
                            continue;
                        }

                        productDescVO.setPrdNo(productVO.getPrdNo());
                        this.insertProductDesc(productDescVO);
                    }
                }
            }

        } catch (Exception ex) {
            ProductDescServiceException threx = new ProductDescServiceException(ProductDescServiceExceptionMessage.NON_SERVICE_VOUCHER_TYPE_PRODUCT_DESC_REGISTER_EROOR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

    }


    public void registerServiceVoucherTypeProductDesc(String prdTypCd, long prdNo, ProductDescVO productDescVO) throws ProductDescServiceException{

//        int insertCnt = 0;

        try {
            productDescValidate.isPrdTypCdEmpty(prdTypCd);
            productDescValidate.isPrdTypeCdServiceVoucher(prdTypCd);
            productDescValidate.isProductDescVOEmpty(productDescVO);

            productDescVO.setPrdNo(prdNo);

//            insertCnt = this.insertProductDesc(productDescVO);
            this.insertProductDesc(productDescVO);


        } catch (Exception ex) {
            ProductDescServiceException threx = new ProductDescServiceException(ProductDescServiceExceptionMessage.SERVICE_VOUCHER_TYPE_PRODUCT_DESC_REGISTER_EROOR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

//        return insertCnt;
    }



    public int copy11PayTypeProductDesc(String prdCopyYn, String prdTypCd, long prdNo, ProductDescVO productDescVO) throws ProductDescServiceException{

        int insertCnt = 0;

        try {
        productDescValidate.isPrdCopyYnEmpty(prdCopyYn);
        productDescValidate.isPrdCopyYnY(prdCopyYn);
        productDescValidate.isPrdTypCdEmpty(prdTypCd);
        productDescValidate.isPrdTypeCd11Pay(prdTypCd);
        productDescValidate.isProductDescVOEmpty(productDescVO);

        productDescVO.setPrdNo(prdNo);

        insertCnt = productDescMapper.updateProductDetail(productDescVO);


        } catch (Exception ex) {
            ProductDescServiceException threx = new ProductDescServiceException(ProductDescServiceExceptionMessage.ELEVEN_PAY_TYPE_PRODUCT_DESC_COPY_EROOR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return insertCnt;
    }



    /**
     * 상품정보 수정 프로세스에서 상품설명 정보를 수정 등록하는 서브 프로세스.
     * 수정상품의 기 등록되어 있는 상품설명 정보들과 새로 수정된 상품설명 정보들의 내용을 비교하여 변경유무를 확인하고 수정 등록 처리한다.
     *
     * @param productVO 수정된 상품설명 정보를 담고있는 수정상품정보
     * @return 상품설명 정보 수정 등록이 완료된 후의 수정상품정보
     * @throws ProductDescServiceException
     * @author wcchoi
     */
    public ProductVO updateProductDetailInRegistration(ProductVO productVO) throws ProductDescServiceException {

        try {

            // 수정등록 상품번호 확인
            long productNo = productVO.getPrdNo();

            // 수정된 경우만 처리하기 위한 로직 추가
            // 기 DB등록된 상품설명의 상품설명유형을 key로 내용을 value로 담을 Map을 생성
            HashMap preDetailMap = new HashMap();
            boolean isPreDesignEditor = false; // 기 DB 등록된 상품설명의 상품상세구분 코드가 디자인편집기연동 유형(09~12)일 경우 true
            List<PdPrdDesc> preDetailList = this.getProductDescList(productNo);

            if (preDetailList != null && preDetailList.size() > 0) { // 저장되는 데이터와 비교하기 위해 기존데이터를 DB에서 가져옴

                for (int i = 0; i < preDetailList.size(); i++) {

                    if ("Y".equals((preDetailList.get(i)).getClobTypYn())) { // Clob타입인경우

                        preDetailMap.put(
                                (preDetailList.get(i)).getPrdDescTypCd() //key : 상품설명유형코드(PD022)
                                , (preDetailList.get(i)).getPrdDescContClob() //Value : 상품설명내용(Clob)
                        );

                        if (!isPreDesignEditor && PrdDescTypCd.DETAIL.code.equals((preDetailList.get(i)).getPrdDescTypCd())) { //상품설명유형 == "상세설명"

                            isPreDesignEditor = (CodeEnumUtil.isTypeOf(PrdDtlTypCd.class, preDetailList.get(i).getPrdDtlTypCd(), "design")) //기 등록 상품상세구분코드(PD160)이 "디자인편집기연동 + 옵션세로$단" 일경우 혹은
                                    || PrdDtlTypCd.DESIGN_EDITOR_APPLY_PRODUCT_DESC.code.equals(preDetailList.get(i).getPrdDtlTypCd()); // "디자인편집기연동 + 상품상세" 일 경우 true

                        }
                    } else {
                        preDetailMap.put(
                                (preDetailList.get(i)).getPrdDescTypCd() //key : 상품설명유형코드(PD022)
                                , (preDetailList.get(i)).getPrdDescContVc()); //Value : 상품설명내용(VARCHAR2)
                    }

                }
            }

            // 셀러 morning01의 API요청의 경우에만 셀러의 경우 등록된 상품의 판매방식을 변경할 수 있다.(셀러는 상품의 판매방식을 수정할수없다. 그 예외임).
            //morning01 없어짐
//            if ("Y".equals(productVO.getSelMthdCdChangeYn())) {
//                if ("".equals(StringUtil.nvl(preDetailMap.get("09")))) // // 기 등록 상품설명 Map에 상품설명유형 "외관,기능상 특이사항(09)"이 없을 경우
//                    preDetailMap.put("09", "특이사항없음"); // 기 등록 상품설명 Map에 상품설명유형을 "외관,기능상 특이사항(09)"로 "특이사항없음" 이란 내용의 상품설명을 하나 추가한다.
//            }

            // 상품설명 비교하여 같지 않은경우만 처리
            ProductDescVO productDescVO = null;

            if (productVO.getProductDescVOList() != null) {

                boolean isDesignEditor = false; // 수정상품 상품설명의 상품상세구분 코드가 디자인편집기연동 유형(09~12)일 경우 true

                for (int i = 0; i < productVO.getProductDescVOList().size(); i++) {

                    productDescVO = productVO.getProductDescVOList().get(i);
                    String detailCont = "";

                    if(GroupCodeUtil.isContainsDtlsCd(productDescVO.getPrdDescTypCd(), PrdDescTypCd.DETAIL,
                                                                                       PrdDescTypCd.SMART_OPTION_INTRO_DESC,
                                                                                       PrdDescTypCd.SMART_OPTION_OUTRO_DESC,
                                                                                       PrdDescTypCd.E_COUPON_PRODUCT_USING_INFO)){
                        productDescVO.setClobTypYn("Y");
                    }else{
                        productDescVO.setClobTypYn("N");
                    }

                    // 기등록 상품설명들 중 상품설명 유형이 현재 수정상품 상품설명의 상품설명유형과 같은것이 존재한다면 기등록 상품설명내용을 detailCont에 할당
                    if (preDetailMap.get(StringUtil.nvl(productDescVO.getPrdDescTypCd(), "")) != null)
                        detailCont = StringUtil.nvl(preDetailMap.get(StringUtil.nvl(productDescVO.getPrdDescTypCd(), "")).toString(), "");

                    // 수정상품 현재 상품설명의 내용이 Clob 타입일 경우
                    if ("Y".equals(productDescVO.getClobTypYn()) && productDescVO.getPrdDescTypCd() != null && !"".equals(productDescVO.getPrdDescTypCd())) {

                        // 수정상품과 기등록상품의 현재 상품설명내용이 다른경우 - 삭제 후 등록
                        if (!detailCont.equals(productDescVO.getPrdDescContClob())) {
                            this.deleteProductDesc(productNo, productDescVO.getPrdDescTypCd());
                            productDescVO.setPrdNo(productNo);
                            this.insertProductDesc(productDescVO);

                            if (PrdDescTypCd.DETAIL.code.equals(productDescVO.getPrdDescTypCd()) && !isDesignEditor) {
                                isDesignEditor = Arrays.asList(PrdDtlTypCd.DESIGN_EDITOR_APPLY_PRODUCT_DESC.code, PrdDtlTypCd.DESIGN_EDITOR_APPLY_OPTION_VERTICAL_1DAN.code
                                        , PrdDtlTypCd.DESIGN_EDITOR_APPLY_OPTION_VERTICAL_2DAN.code, PrdDtlTypCd.DESIGN_EDITOR_APPLY_OPTION_VERTICAL_3DAN.code).contains(productDescVO.getPrdDtlTypCd());
                            }
                        } else if (StringUtil.isNotEmpty(productDescVO.getPrdDtlTypCd())) { // 같을 경우에도 처리????? FixMe
                            this.deleteProductDesc(productNo, productDescVO.getPrdDescTypCd());
                            productDescVO.setPrdNo(productNo);
                            this.insertProductDesc(productDescVO);

                            // 상품설명유형이 "상세설명[02]"인데 수정상품 디자인편집기 확인변수가 아직 true가 아닐경우 디자인편집기 사용여부를 판별한다
                            if (ProductConstDef.PRD_DESC_TYP_CD_PRD_DTL.equals(productDescVO.getPrdDescTypCd()) && !isDesignEditor) {
                                // 수정상품 상품설명의 상품상세구분 코드가 디자인편집기연동 유형(09~12)일 경우 true
                                isDesignEditor = Arrays.asList(PrdDtlTypCd.DESIGN_EDITOR_APPLY_PRODUCT_DESC.code, PrdDtlTypCd.DESIGN_EDITOR_APPLY_OPTION_VERTICAL_1DAN.code
                                        , PrdDtlTypCd.DESIGN_EDITOR_APPLY_OPTION_VERTICAL_2DAN.code, PrdDtlTypCd.DESIGN_EDITOR_APPLY_OPTION_VERTICAL_3DAN.code).contains(productDescVO.getPrdDtlTypCd());
                            }
                        }

                        //QC검증 프로세스 필요여부 설정 - 기등록 상품설명과 수정 상품설명이 다를 경우 필요(true)로 설정
                        if (!productVO.isQcVerifiedStat() && PrdDescTypCd.DETAIL.code.equals(productDescVO.getPrdDescTypCd())) {
                            productVO.setQcVerifiedStat(!detailCont.equals(productDescVO.getPrdDescContClob()) || StringUtil.isNotEmpty(productDescVO.getPrdDtlTypCd()));
                        }
                    } // 수정상품 현재 상품설명의 내용이 VARCHAR2 타입일 경우
                    else if ("N".equals(productDescVO.getClobTypYn()) && productDescVO.getPrdDescTypCd() != null && !"".equals(productDescVO.getPrdDescTypCd())) {

                        // 기 등록 상품설명내용과 수정상품의 상품설명내용이 다르다면
                        if (!detailCont.equals(productDescVO.getPrdDescContVc())) {
                            // 해당 상품설명 삭제
                            this.deleteProductDesc(productNo, productDescVO.getPrdDescTypCd());
                            productDescVO.setPrdNo(productNo); //수정상품 해당 상품설명에 수정상품번호 설정

                            // 수정상품의 현재 상품설명의 상품설명유형이 "설치비안내" 가 아닌경우
                            if (!PrdDescTypCd.INSTALLATION_CHARGE.code.equals(productDescVO.getPrdDescTypCd()))
                                this.insertProductDesc(productDescVO); //상품설명 등록
                            else {
                                if (StringUtil.isNotEmpty(productDescVO.getPrdDescContVc()))
                                    this.insertProductDesc(productDescVO); //설치비 안내의 경우 상품설명내용의 유무체크를 하고 등록
                            }
                        }
                    }
                }

                // 기등록된 상품설명 상세설명[02]은 디자인편집기 연동이나 수정상품 상세설명은 디자인편집기 연동이 아닐경우
                // 기등록된 상품설명중 유형이 모바일유형(14~16)을 삭제한다.
                if (isPreDesignEditor && !isDesignEditor) {
                    this.deleteProductDesc(productNo, PrdDescTypCd.MOBILE_DESC.code);
                    this.deleteProductDesc(productNo, PrdDescTypCd.MOBILE_SMART_OPTION_INTRO_DESC.code);
                    this.deleteProductDesc(productNo, PrdDescTypCd.MOBILE_SMART_OPTION_OUTRO_DESC.code);
                }
            }
        } catch (Exception ex) {
            ProductDescServiceException threx = new ProductDescServiceException(ProductDescServiceExceptionMessage.DESC_UPDATE_IN_REGISTRATION_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;

        }

        return productVO;
    }


    /**
     * 신규 상품설명 번호 채번 조회
     * 오라클 시퀀스 SEQ_PD_PRD_DESC.nextVal 실행해서 반환한다.
     * 구) getProductDetailNo
     * @return 신규 채번된 상품설명번호
     * @throws ProductDescServiceException
     * @author wcchoi
     */
    private long getProductDescNo() throws ProductDescServiceException {

        long prdDescNo = 0;

        try {
            prdDescNo = productDescMapper.getProductDescNo();
        } catch (Exception ex) {
            ProductDescServiceException threx = new ProductDescServiceException(ProductDescServiceExceptionMessage.PRD_DESC_NO_SEQ_CREATE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
        return prdDescNo;
    }

    /**
     * 상품설명 조회.
     * 상품번호 및 상품설명유형코드로 해당상품의 상품설명을 조회한다.
     *
     * @param prdNo 상품번호
     * @param prdDescTypCd 상품설명유형코드
     * @return 상품의 상품설명
     * @throws ProductDescServiceException
     * @author wcchoi
     */
    public PdPrdDesc getProductDesc(long prdNo, String prdDescTypCd) throws ProductDescServiceException {
        PdPrdDesc pdPrdDesc = null;
        try {
            pdPrdDesc = productDescMapper.getProductDesc(prdNo, prdDescTypCd);
        } catch (Exception ex) {
            ProductDescServiceException threx = new ProductDescServiceException(ProductDescServiceExceptionMessage.DESC_SELECT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
        return pdPrdDesc;
    }

    /**
     * 상품설명 목록 조회.
     * 상품번호로 해당상품의 상품설명 목록을 조회한다.
     *
     * @param prdNo 상품번호
     * @return 상품의 상품설명 목록
     * @throws ProductDescServiceException
     * @author wcchoi
     */
    public List<PdPrdDesc> getProductDescList(long prdNo) throws ProductDescServiceException {
        List<PdPrdDesc> pdPrdDescList = null;
        try {
            pdPrdDescList = productDescMapper.getProductDescList(prdNo);
        } catch (Exception ex) {
            ProductDescServiceException threx = new ProductDescServiceException(ProductDescServiceExceptionMessage.DESC_LIST_SELECT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
        return pdPrdDescList;
    }


    /**
     * 상품의 상품설명중 상품설명유형 "상세설명[02]" 조회해 온다.
     * 1건의 상품설명 반환. (상품별로 동일한 상품설명유형을 갖는 상품설명은 존재할수 없다. (상품번호, 상품설명유형 쌍으로 고유함))
     *
     * @param prdNo 상세설명을 조회해올 상품의 상품번호
     * @return PdPrdDesc 상품의 상품설명(상세설명[02])
     * @throws ProductDescServiceException
     */
    public PdPrdDesc getProductDetailCont(long prdNo) throws ProductDescServiceException {
        PdPrdDesc prdDescContClob  = null;
        try {
            prdDescContClob = productDescMapper.getProductDetailCont(prdNo);
        } catch (Exception ex) {
            ProductDescServiceException threx =  new ProductDescServiceException(ProductDescServiceExceptionMessage.DETAIL_DESC_SELECT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
        return prdDescContClob;
    }


    /**
     * 상품의 상품설명중 상품설명유형 "상세설명[02], 스마트옵션인트로설명[12], 스마트옵션아웃트로설명[13]"을 조회해 온다.
     *
     * @param prdNo 상품설명 목록을 조회해올 상품의 상품번호
     * @return 상품의 상품설명(상세설명[02], 스마트옵션인트로설명[12], 스마트옵션아웃트로설명[13]) 목록
     * @throws ProductDescServiceException
     */
    public List<ProductDescVO> getProductDescContForSmtOption(long prdNo) throws ProductDescServiceException {
        List<ProductDescVO> prdDescVOList  = null;
        try {
            prdDescVOList = productDescMapper.getProductDescContForSmtOption(prdNo);
        } catch (Exception ex) {
            ProductDescServiceException threx = new ProductDescServiceException(ProductDescServiceExceptionMessage.SMART_OPTION_DESC_SELECT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
        return prdDescVOList;
    }

    /**
     * 상품의 상품설명중 상품설명유형 "모바일상세설명[14], 모바일스마트옵션인트로설명[15], 모바일스마트옵션아웃트로설명[16]"을 조회해 온다.
     *
     * @param prdNo 상품설명 목록을 조회해올 상품의 상품번호
     * @return 상품의 상품설명(모바일상세설명[14], 모바일스마트옵션인트로설명[15], 모바일스마트옵션아웃트로설명[16]) 목록
     * @throws ProductDescServiceException
     */
    public List<ProductDescVO> getProductDescContForSmtOptionMW(long prdNo) throws ProductDescServiceException {
        List<ProductDescVO> prdDescVOList  = null;
        try {
            prdDescVOList = productDescMapper.getProductDescContForSmtOptionMW(prdNo);
        } catch (Exception ex) {
            ProductDescServiceException threx = new ProductDescServiceException(ProductDescServiceExceptionMessage.MW_SMART_OPTION_DESC_SELECT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
        return prdDescVOList;
    }


    /**
     * 상품설명 등록.
     * PD_PRD_DESC insert 한다.
     *
     * @param pdPrdDesc DB 등록할 상품설명
     * @throws ProductDescServiceException
     * @author wcchoi
     */
    public void insertProductDesc(PdPrdDesc pdPrdDesc) throws ProductDescServiceException {

        try {
            long prdDescNo = this.getProductDescNo();

            // 상세설명 수정일 경우
            if( pdPrdDesc != null && !"99".equals(pdPrdDesc.getPrdDtlTypCd()) && ProductConstDef.PRD_DESC_TYP_CD_PRD_DTL.equals(pdPrdDesc.getPrdDescTypCd()) ) {
                pdPrdDesc.setPrdDtlTypCd(SellerProductDetailUtil.getPrdDetailOptionMatching(pdPrdDesc.getPrdDescContClob(), pdPrdDesc.getPrdDtlTypCd()));
            } else if(pdPrdDesc != null && "99".equals(pdPrdDesc.getPrdDtlTypCd())) {
                pdPrdDesc.setPrdDtlTypCd("");
            }

            pdPrdDesc.setPrdDescNo(prdDescNo);
            productDescMapper.insertProductDesc(pdPrdDesc);
        } catch (Exception ex) {
            ProductDescServiceException threx = new ProductDescServiceException(ProductDescServiceExceptionMessage.DESC_INSERT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
    }

    /**
     * 상품설명 등록.
     * PD_PRD_DESC insert 한다.
     *
     * @param productDescVO DB 등록할 상품설명
     * @throws ProductDescServiceException
     * @author wcchoi
     */
    public void insertProductDesc(ProductDescVO productDescVO) throws ProductDescServiceException {
        try {
            PdPrdDesc pdPrdDesc = new PdPrdDesc(
                    productDescVO.getPrdDescNo(),
                    productDescVO.getPrdNo(),
                    productDescVO.getPrdDescTypCd(),
                    productDescVO.getPrdDescContVc(),
                    productDescVO.getPrdDescContClob(),
                    productDescVO.getClobTypYn(),
                    productDescVO.getCreateDt(),
                    productDescVO.getCreateNo(),
                    productDescVO.getUpdateDt(),
                    productDescVO.getUpdateNo(),
                    productDescVO.getSplitedCnt(),
                    productDescVO.getSplitedUrl(),
                    productDescVO.getPrdDtlTypCd(),
                    productDescVO.getMode()
            );

            this.insertProductDesc(pdPrdDesc);

        } catch (Exception ex) {
            ProductDescServiceException threx = new ProductDescServiceException(ProductDescServiceExceptionMessage.DESC_INSERT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
    }

    /**
     * 상품 재등록시 기존 상품설명 복사.
     * 특정 상품의 상품설명을 다른 상품의 상품설명으로 그대로 복사한다.
     * 상품 번호(reRegPrdNo)를 전달하여 해당 상품번호를 가지는 상품설명들과 상품설명번호(PRD_DESC_NO : PK)와 상품번호, 수정일시, 생성일시만 제외한 동일한 데이터를 INSERT SELECT로 PD_PRD_DESC에 insert한다.
     * 상품설명을 복사받는 상품의 상품번호는 newPrdNo로 전달.
     * 이때 PK인 PD_DESC_NO는 오라클 시퀀스 SEQ_PD_PRD_DESC.nextVal 로 새로 채번한다.
     *
     * @param reRegPrdNo 기존 상품설명을 복사해 가져올 출처(source) 상품번호
     * @param newPrdNo 복사한 상품설명을 달아줄 새로운 상품의 상품번호(newPrdNo)
     * @throws ProductDescServiceException
     * @author wcchoi
     */
    public void insertProductDescReReg(long reRegPrdNo, long newPrdNo) throws ProductDescServiceException {

        try {
            ProductDescVO productDescVO = new ProductDescVO(reRegPrdNo, newPrdNo);

            productDescMapper.insertProductDescReReg(productDescVO);

        } catch (Exception ex) {
            ProductDescServiceException threx = new ProductDescServiceException(ProductDescServiceExceptionMessage.DESC_COPY_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
    }


    /**
     * 상품설명 삭제 후 신규등록. (DELETE & INSERT)
     * 특정 상품의 동일 상품설명유형의 상품설명 삭제 후 신규등록한다.
     *
     * 파라메터로 넘긴 ProductDescVO에 설정된 상품번호, 상품설명유형코드로 상품설명을 삭제한 뒤,
     * 오라클 시퀀스 SEQ_PD_PRD_DESC.nextVal 로 PK인 상품설명번호 들을 신규 채번하고 역시 ProductDescVO에 설정된 정보로 신규 등록한다.
     *
     * 즉, 상품번호와 상품설명유형코드는 동일하나 상품설명번호(PRD_DESC_NO)는 변경된다.
     * 특정 상품의 특정유형의 상품설명을 수정하고 싶을때 활용한다.
     *
     * 작업 완료후 상품의 수정일시(PD_PRD.UPDATE_DT)를 수정하고 상품이력(PD_PRD_HIST)을 등록한다.
     *
     * @param productDescVO 삭제 후 신규등록할 특정 상품, 상품설명유형의 상품설명 정보
     * @throws ProductDescServiceException
     * @author wcchoi
     */
    public void updateProductDescCont(ProductDescVO productDescVO) throws ProductDescServiceException {
        this.updateProductDescCont(Arrays.asList(productDescVO));
    }

    /**
     * 상품설명 삭제 후 신규등록. (DELETE & INSERT)
     * 특정 상품의 동일 상품설명유형의 상품설명 삭제 후 신규등록한다.
     *
     * 파라메터로 넘긴 ProductDescVO 목록에 설정된 상품번호, 상품설명유형코드 들로 상품설명들을 삭제한 뒤,
     * 오라클 시퀀스 SEQ_PD_PRD_DESC.nextVal 로 PK인 상품설명번호 들을 신규 채번하고 역시 ProductDescVO 목록에 설정된 정보 들로 신규 등록한다.
     *
     * 즉, 상품번호와 상품설명유형코드는 동일하나 상품설명번호(PRD_DESC_NO)는 변경된다.
     * 특정 상품의 특정유형의 상품설명 들을 수정하고 싶을때 활용한다.
     *
     * 주의할점은 한 상품의 특정유형의 상품설명들을 삭제 후 등록하는 것이므로 상품설명들에 설정된 상품번호는 동일(단일)해야 한다.
     *
     * 작업 완료후 상품의 수정일시(PD_PRD.UPDATE_DT)를 수정하고 상품이력(PD_PRD_HIST)을 등록한다.
     *
     * @param productDescVOList 동일상품의 상품설명 목록들. 상품설명들에 설정된 상품번호가 상이하면 안된다.
     * @throws ProductDescServiceException
     * @author wcchoi
     */
    public void updateProductDescCont(List<ProductDescVO> productDescVOList) throws ProductDescServiceException{
        try {

            //검증
            productDescValidate.isUpdateDescDataEmpty(productDescVOList);

            ProductDescVO delInfo = productDescVOList.get(0);

            delInfo.setPrdDescTypCdList(new ArrayList(productDescVOList.size()));


            for(ProductDescVO modInfo : productDescVOList){
                // 상세설명 수정일 경우
                if(!"99".equals(modInfo.getPrdDtlTypCd()) && ProductConstDef.PRD_DESC_TYP_CD_PRD_DTL.equals(modInfo.getPrdDescTypCd()) ) {
                    modInfo.setPrdDtlTypCd(SellerProductDetailUtil.getPrdDetailOptionMatching(modInfo.getPrdDescContClob(), modInfo.getPrdDtlTypCd()));
                } else if("99".equals(modInfo.getPrdDtlTypCd())){
                    modInfo.setPrdDtlTypCd("");
                }

                delInfo.getPrdDescTypCdList().add(modInfo.getPrdDescTypCd());
            }
            productDescMapper.deleteProductDescContInfo(delInfo);

            for(ProductDescVO modInfo : productDescVOList){
                modInfo.setPrdDescNo(productDescMapper.getProductDescNo()); //PRD_DESC_NO 값 셋팅
                this.insertProductDesc(modInfo);
            }

            ProductVO productVO = new ProductVO();
            productVO.setPrdNo(delInfo.getPrdNo());
            productVO.setUpdateNo(delInfo.getUpdateNo());

            productService.updateProductUpdateDt(productVO);
        } catch (Exception ex) {
            ProductDescServiceException threx = new ProductDescServiceException(ProductDescServiceExceptionMessage.DESC_UPDATE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
    }

    /**
     * 상품의 상품설명중 상품설명유형 "상세설명[02]"인 상품설명의 내용을 수정 한다. (UPDATE)
     * 수정항목은 CLOB상품설명내용(PRD_DESC_CONT_CLOB), 수정자(UPDATE_NO), 수정일시(UPDATE_DT)이다.
     *
     * @param productDescVO 수정할 상품설명의 상품번호가 담긴 상품설명정보
     * @throws ProductDescServiceException
     * @author wcchoi
     */
    public void updateProductDetail(ProductDescVO productDescVO) throws ProductDescServiceException
    {
        try {
            productDescMapper.updateProductDetail(productDescVO);
        } catch (Exception ex) {
            ProductDescServiceException threx = new ProductDescServiceException(ProductDescServiceExceptionMessage.DETAIL_DESC_UPDATE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
    }

    /**
     * 상품설명 삭제 by 상품번호, [상품설명유형코드(PD022)]
     * 상품번호와 상품설명유형코드에 해당하는 상품설명을 모두 삭제한다.
     * 단, 상품설명유형코드 미 전달시 삭제되는 상품설명은 상품설명유형 상세설명(02), AS 안내정보(06), 반품/교환안내(07)로 한정된다.
     *
     * @param prdNo 삭제할 상품설명의 상품번호
     * @param prdDescTypCd [상품설명유형코드]
     * @return 삭제된 상품설명 row 수
     * @throws ProductDescServiceException
     * @author wcchoi
     */
    public int deleteProductDesc(long prdNo, String prdDescTypCd) throws ProductDescServiceException {
        int deletedRowsCnt = 0;

        try {
            deletedRowsCnt = productDescMapper.deleteProductDesc(prdNo, prdDescTypCd);
        } catch (Exception ex) {
            ProductDescServiceException threx = new ProductDescServiceException(ProductDescServiceExceptionMessage.DESC_DELETE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return deletedRowsCnt;
    }

    /**
     * 상품설명 삭제. (DELETE)
     * 파라메터로 넘긴 ProductDescVO 설정된 상품번호, 상품설명유형코드로 상품설명을 삭제한다.
     * 단일 건의 상품설명을 삭제시엔 ProductDescVO.prdDescTypCd 에 상품설명유형코드를 설정하면 되고,
     * ProductDescVO.prdDescTypCdList 에 상품설명유형코드 목록을 설정하고 넘기면
     * 해당 상품설명유형의 상품설명들을 한꺼번에 삭제 시킬 수 도 있다.
     *
     * @param productDescVO 삭제할 특정 상품, 상품설명유형의 상품설명 정보
     * @return 삭제된 상품설명 row 수
     * @throws ProductDescServiceException
     * @author wcchoi
     */
    public int deleteProductDescContInfo(ProductDescVO productDescVO) throws ProductDescServiceException {
        int deletedRowsCnt = 0;

        try {
            deletedRowsCnt = productDescMapper.deleteProductDescContInfo(productDescVO);
        } catch (Exception ex) {
            ProductDescServiceException threx = new ProductDescServiceException(ProductDescServiceExceptionMessage.DESC_DELETE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return deletedRowsCnt;
    }

    public void insertProductDescInfo(ProductVO productVO) throws ProductException{
        this.registerNonServiceVoucherTypeProductDesc(productVO);
    }

    public void updateProductDescInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        this.updateProductDetailInRegistration(productVO);
    }

    /**
     * 셀러 상품설명 조회.
     *
     * @param productVO
     * @return
     * @throws ProductDescServiceException
     */
    public ProductVO getSellerProductDetailDesc(ProductVO productVO) throws ProductDescServiceException {
        ProductVO result = null;

        try {
            result = productDescMapper.getSellerProductDetailDesc(productVO);

            if(productVO.getProductEvtVO() != null && !(productVO.getProductEvtVO().getChnlBitNo() == ProductConstDef.CHNL_BIT_PC || productVO.getProductEvtVO().getChnlBitNo() == 0)){
                ProductVO paramBO = new ProductVO();
                paramBO.setPrdNo(productVO.getPrdNo());
                paramBO.setProductDescVO(new ProductDescVO());
                paramBO.getProductDescVO().setPrdDescTypCd(ProductConstDef.PRD_DESC_TYP_CD_MW_PRD_DTL);

                paramBO = getSellerProductDetailDesc(paramBO);
                if(paramBO != null && StringUtil.isNotEmpty(paramBO.getProductDescVO().getPrdDescContClob())){
                    result.getProductDescVO().setPrdDescContClob(paramBO.getProductDescVO().getPrdDescContClob());
                }
            }
            return result;
        } catch(Exception ex) {
            throw new ProductDescServiceException("[SellerProductDetailManagerBO] 셀러상품상세설명 조회 ", ex);
        }
    }


    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) {
        productDescValidate.checkReturnExchangInfo(productVO);
    }
}
