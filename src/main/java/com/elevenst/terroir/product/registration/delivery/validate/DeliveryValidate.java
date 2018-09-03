package com.elevenst.terroir.product.registration.delivery.validate;

import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.NumberUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.common.service.CommonServiceImpl;
import com.elevenst.terroir.product.registration.customer.service.CustomerServiceImpl;
import com.elevenst.terroir.product.registration.delivery.code.DlvClfCdTypes;
import com.elevenst.terroir.product.registration.delivery.code.PrdAddrClfCd;
import com.elevenst.terroir.product.registration.delivery.exception.DeliveryException;
import com.elevenst.terroir.product.registration.delivery.mapper.DeliveryMapper;
import com.elevenst.terroir.product.registration.delivery.service.DeliveryServiceImpl;
import com.elevenst.terroir.product.registration.delivery.vo.ClaimAddressInfoVO;
import com.elevenst.terroir.product.registration.delivery.vo.MemberGlobalDeliverVO;
import com.elevenst.terroir.product.registration.delivery.vo.PrdOrderCountBaseDeliveryVO;
import com.elevenst.terroir.product.registration.delivery.vo.ProductSellerBasiDeliveryCostVO;
import com.elevenst.terroir.product.registration.option.code.OptionConstDef;
import com.elevenst.terroir.product.registration.product.code.DlvCstInstBasiCd;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.service.ProductServiceImpl;
import com.elevenst.terroir.product.registration.product.vo.BaseVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

//import com.elevenst.terroir.product.registration.product.vo.ProductBO;

@Component
public class DeliveryValidate {

    @Autowired
    DeliveryServiceImpl deliveryService;

    @Autowired
    DeliveryMapper deliveryMapper;

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    CommonServiceImpl commonService;

    @Autowired
    CustomerServiceImpl customerService;

    /**
     *  구  checkInOutAddressBO()
     * */
    public boolean checkClaimAddressVO(ProductVO productVO) throws ProductException{

        List<ClaimAddressInfoVO> claimAddressInfoVOList = productVO.getClaimAddressInfoVOList();

        for(ClaimAddressInfoVO elem : claimAddressInfoVOList) {
            String prdAddrClfCd = elem.getPrdAddrClfCd();
            if(elem.getMemNo() <= 0) elem.setMemNo(productVO.getSelMnbdNo());
            boolean isExistAddressSeq = deliveryService.isExistUserAddressSeq(elem);

            if(!isExistAddressSeq) {
                switch(GroupCodeUtil.fromString(PrdAddrClfCd.class, prdAddrClfCd)) {

                    case EX_WAREHOUSE:
                        throw new ProductException("출고지 주소지가 올바르지 않습니다.");
                    case EXCHANGE_RETURN:
                        throw new ProductException("교환/반품지 주소지가 올바르지 않습니다.");
                    case VISIT_RECEIVE:
                        throw new ProductException("방문수령 주소지가 올바르지 않습니다.");
                    case SELLER_GLOBAL_EX_WAREHOUSE:
                        throw new ProductException("판매자 해외 출고지 주소지가 올바르지 않습니다.");
                    case SELLER_EXCHANGE_RETURN:
                        throw new ProductException("판매자 반품/교환지  주소지가 올바르지 않습니다.");
                    default:
                        break;
                }
            }
        }

        return true;
    }


    @Deprecated
    public void checkRentalProductDeliveryCost(ProductVO productVO) {
        String prdTypCd = productVO.getBaseVO().getPrdTypCd();
        String dlvCstInstBasiCd = productVO.getBaseVO().getDlvCstInstBasiCd();

        checkRentalProductDeliveryCost(prdTypCd,dlvCstInstBasiCd);

    }

    @Deprecated
    public void checkRentalProductDeliveryCost(String prdTypCd, String dlvCstInstBasiCd) {

        if(GroupCodeUtil.equalsDtlsCd(prdTypCd, PrdTypCd.RENTAL) &&
            GroupCodeUtil.notEqualsDtlsCd(dlvCstInstBasiCd, DlvCstInstBasiCd.FREE)) {

            throw new DeliveryException("렌탈상품은 배송비 무료로만 설정이 가능합니다.");

        }

    }

    public void checkDeliveryInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if(productVO.isTown()){ //PIN번호 상품
            productVO.getBaseVO().setGblDlvYn("");
            productVO.getBaseVO().setDlvClf(ProductConstDef.DLV_CLF_OUT_DELIVERY);
            productVO.getBaseVO().setDlvCnAreaCd(ProductConstDef.DLV_CN_AREA_CD_ALL);
            productVO.getBaseVO().setDlvWyCd(ProductConstDef.DLV_WY_CD_DLV);
            productVO.getBaseVO().setDlvCstInstBasiCd(ProductConstDef.DLV_CST_INST_BASI_CD_FREE);
            productVO.getBaseVO().setDlvCst(0);
            productVO.getBaseVO().setJejuDlvCst(0);
            productVO.getBaseVO().setIslandDlvCst(0);
            productVO.getBaseVO().setRtngdDlvCd(ProductConstDef.RTNGD_DLV_CD_OW);
            productVO.getBaseVO().setRtngdDlvCst(0);
            productVO.getBaseVO().setExchDlvCst(0);


            if (productVO.getBaseVO().getInAddrSeq() < 1)
                throw new ProductException("반품/교환지 설정 후 상품을 등록해 주세요. 회원정보 > 판매자정보관리 메뉴에서 확인 가능합니다.");

            if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getPrdTypCd(), PrdTypCd.PIN_11ST_SEND)){
                // 환불 타입 체크
                if(StringUtil.isEmpty(productVO.getBaseVO().getRfndTypCd())){
                    throw new ProductException("환불 타입은 반드시 입력하셔야 합니다.");
                }else if(!ProductConstDef.RFND_TYP_CD.contains(productVO.getBaseVO().getRfndTypCd())){
                    throw new ProductException("유효하지 않은 환불 타입 코드 입니다.");
                }
            }
        }else{
            if(productVO.getBaseVO().getOutMemNo() <= 0) productVO.getBaseVO().setOutMemNo(productVO.getSelMnbdNo());
            if(productVO.getBaseVO().getInMemNo() <= 0) productVO.getBaseVO().setInMemNo(productVO.getSelMnbdNo());

            // 전세계배송 가능여부 체크
            // 회원 정보 :특수한 경우 전세계 배송여부 상관없이 무게를 입력함.
            if(productVO.getBaseVO().getGblDlvYn() != null) {
                if("Y".equals(StringUtil.nvl(productVO.getBaseVO().getGblDlvYn(),""))){  //전세계배송 이용 상품
                    // 해당 셀러가 전세계배송 이용 여부 체크
                    if(!this.isUseGlobalDeliver(productVO.getSelMnbdNo()))	throw new ProductException("해당 셀러는 전세계배송 이용권한이 없습니다.");
                    this.checkValidGblDlvYn(productVO);
                }else{	//N일때
                    if(!ProductConstDef.MEM_SUPL_CMP_CLF_CD.equals(productVO.getMemberVO().getSuplCmpClfCd())){
                        this.setGblDlvInfo(preProductVO, productVO);
                    }
                }
            }else{ //prdRegBO.getGblDlvYn()== null 일때
                if(!ProductConstDef.MEM_SUPL_CMP_CLF_CD.equals(productVO.getMemberVO().getSuplCmpClfCd())){
                    this.setGblDlvInfo(preProductVO, productVO);
                }
            }

            // 배송가능지역 (기본값 : 전국)
            if (StringUtil.isEmpty(productVO.getBaseVO().getDlvCnAreaCd())) productVO.getBaseVO().setDlvCnAreaCd(ProductConstDef.DLV_CN_AREA_CD_ALL);
            this.checkDlvCnAreaCd(productVO);

            // 배송방법 (기본값 : 택배)
            if (StringUtil.isEmpty(productVO.getBaseVO().getDlvWyCd())) productVO.getBaseVO().setDlvWyCd(ProductConstDef.DLV_WY_CD_DLV);
            this.checkDlvWyCd(productVO);

            // 배송필요없음 처리
            if (ProductConstDef.DLV_WY_CD_NONE_DLV.equals(productVO.getBaseVO().getDlvWyCd())) {
                if (!ProductConstDef.DLV_CST_INST_BASI_CD_FREE.equals(productVO.getBaseVO().getDlvCstInstBasiCd())) {
                    throw new ProductException("배송방법이 '배송필요없음'일 경우 배송비는 반드시 무료여야 합니다.");
                }

                if (productVO.getBaseVO().getRtngdDlvCst() > 0) throw new ProductException("배송방법이 '배송필요없음'일 경우, 반품 배송비는 반드시 무료(0원)이여야 합니다.");
                if (productVO.getBaseVO().getExchDlvCst() > 0) throw new ProductException("배송방법이 '배송필요없음'일 경우, 교환 배송비는 반드시 무료(0원)이여야 합니다.");

                // 11번가 자체 배송일 경우 배송필요없음 처리 불가
                if (ProductConstDef.DLV_CLF_GLOBAL_DELIVERY.equals(productVO.getBaseVO().getDlvClf())) throw new ProductException("11번가 해외배송일 경우 배송방법이 '배송필요없음'일 수 없습니다.");
            }

            // 배송주체 체크
            if(!ProductConstDef.BSN_DEAL_CLF_COMMISSION.equals(productVO.getBaseVO().getBsnDealClf())) {
                if (StringUtil.isEmpty(productVO.getBaseVO().getDlvClf())) throw new ProductException("배송주체는 반드시 선택되어야 합니다.");
            }else{
                this.setDeliveryInfo(preProductVO, productVO);
                if(ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(productVO.getBaseVO().getDlvClf())
                    && !ProductConstDef.DLV_WY_CD_DLV.equals(productVO.getBaseVO().getDlvWyCd())){
                    throw new ProductException("셀러위탁배송은 배송방법이 '택배'만 가능합니다.");
                }
            }
            // 출고지 & 반품/교환지 주소
            if(StringUtil.isEmpty(String.valueOf(productVO.getBaseVO().getInAddrSeq()))){
                throw new ProductException("반품/교환지 주소는 반드시 입력하셔야 합니다.");
            }
            if(StringUtil.isEmpty(String.valueOf(productVO.getBaseVO().getOutAddrSeq()))){
                throw new ProductException("출고지 주소는 반드시 입력하셔야 합니다.");
            }

            if(!ProductConstDef.BSN_DEAL_CLF_COMMISSION.equals(productVO.getBaseVO().getBsnDealClf())
                || ProductConstDef.DLV_CLF_GLOBAL_DELIVERY.equals(productVO.getBaseVO().getDlvClf())) {
                this.checkInOutAddr(productVO);
            }

            // 배송비설정 ( 기본값 처리 )
            // 무료 일 경우 무조건 배송비 0원
            boolean isAbrdBrandYn = ("Y".equals(productVO.getBaseVO().getAbrdBrandYn())) ? true : false;
            boolean is1WonAsk = ("Y".equals(productVO.getMobileYn()) && "Y".equals(productVO.getMobile1WonYn())) ? true : false;
            if (ProductConstDef.DLV_CST_INST_BASI_CD_FREE.equals(productVO.getBaseVO().getDlvCstInstBasiCd())) {
                productVO.getBaseVO().setDlvCst(0);
                productVO.getBaseVO().setDlvCstPayTypCd(ProductConstDef.DLV_CST_PAY_TYP_CD_PRE_PAY);
            }
            if (ProductConstDef.DLV_CST_INST_BASI_CD_SELLER.equals(productVO.getBaseVO().getDlvCstInstBasiCd())) {
                List<ProductSellerBasiDeliveryCostVO> sellerBasiDlvCstList = deliveryService.getSellerBasiDlvCstList(productVO.getSelMnbdNo());
                if(sellerBasiDlvCstList == null || sellerBasiDlvCstList.size() <= 0 ) {
                    throw new ProductException("판매자 조건부 배송일 경우 배송비 기준설정을 반드시 하셔야 합니다.");
                }
            }
            if( (productVO.getBaseVO().getPrdTypCd().equals(ProductConstDef.PRD_TYP_CD_RENTAL))
                && (!ProductConstDef.DLV_CST_INST_BASI_CD_FREE.equals(productVO.getBaseVO().getDlvCstInstBasiCd()))){
                throw new ProductException("렌탈상품은 배송비 무료로만 설정이 가능합니다.");
            }
            this.checkDlvCustInfo(productVO);

            // 결제방법
            // 기본값 : 선결제가능
            if (StringUtil.isEmpty(productVO.getBaseVO().getDlvCstPayTypCd())) productVO.getBaseVO().setDlvCstPayTypCd(ProductConstDef.DLV_CST_PAY_TYP_CD_PRE_PAY);
            if (StringUtil.isNotEmpty(productVO.getBaseVO().getDlvCstPayTypCd())
                && !commonService.isCodeDetail(ProductConstDef.DLV_CST_PAY_TYP_CD, productVO.getBaseVO().getDlvCstPayTypCd()))
                throw new ProductException("유효하지 않은 결제방법코드 입니다.");

            // 배송비추가 안내
            if (StringUtil.isNotEmpty(productVO.getBaseVO().getDlvCstInfoCd())) {
                // 고정배송비 이고 선결제불가 선택시에만 코드 유효성 체크
                if (ProductConstDef.DLV_CST_INST_BASI_CD_COLLECT.equals(productVO.getBaseVO().getDlvCstInstBasiCd())
                    && ProductConstDef.DLV_CST_PAY_TYP_CD_NONE_PRE_PAY.equals(productVO.getBaseVO().getDlvCstPayTypCd())
                    )
                {
                    if (StringUtil.isNotEmpty(productVO.getBaseVO().getDlvCstInfoCd())
                        && !commonService.isCodeDetail(ProductConstDef.DLV_CST_INFO_CD, productVO.getBaseVO().getDlvCstInfoCd()))
                        throw new ProductException("유효하지 않은 배송비 추가 안내 코드 입니다.");
                }
                else {
                    productVO.getBaseVO().setDlvCstInfoCd("");
                }
            }

            // 방문 수령 주소
            if ("Y".equals(productVO.getBaseVO().getVisitDlvYn())
                && productVO.getBaseVO().getVisitAddrSeq() == 0){
                throw new ProductException("방문수령지 주소는 반드시 입력하셔야 합니다.");
            }

            boolean isHyundai = (StringUtil.nvl(commonService.get1hourTimeProperty(ProductConstDef.HYUNDAI_SELLER_ID),"").indexOf("|" + productVO.getSelMnbdNo() + "|") >= 0);
            boolean isEctSeller = StringUtil.nvl(commonService.get1hourTimeProperty(ProductConstDef.PRD_DLV_CST_ECT_SELLER),"").indexOf("|" + productVO.getSelMnbdNo() + "|") >= 0;

            // 반품/교환 배송비
            String errMsg = "";
            if(isHyundai || isEctSeller){
                this.checkDlvCustHyundai(productVO.getBaseVO().getRtngdDlvCst(), true, "반품 ");
            }else{
                this.checkDlvCust(productVO.getBaseVO().getRtngdDlvCst(), isAbrdBrandYn, false, true, "반품 ");
            }


            if(isHyundai || isEctSeller){
                this.checkDlvCustHyundai(productVO.getBaseVO().getExchDlvCst(), true, "교환 ");
            }else{
                this.checkDlvCust(productVO.getBaseVO().getExchDlvCst(), isAbrdBrandYn, true, true, "교환 ");
            }

            this.checkRtngdDlvCd(productVO);

//            prdRegBO.setInstallCstDetail(StringUtil.nvl(dataBox.getString("installCstDetail"),"").trim()); //설치비 안내

            // 템플릿 저장
            if ("Y".equals(productVO.getSelInfoTempleteYn())) {
                if (StringUtil.isEmpty(productVO.getSelInfoTempleteNm())) throw new ProductException("새로운 배송정보 저장시, 템플릿명은 반드시 입력하셔야 합니다.");
            }

            // 상품 신고가
            this.checkCstmAplPrc(productVO);

            // 환불 타입
            if(StringUtil.isNotEmpty(productVO.getBaseVO().getRfndTypCd())){
                throw new ProductException("환불 타입을 설정 할 수 없습니다.");
            }
        }
    }

    private void checkCstmAplPrc(ProductVO productVO) throws DeliveryException{
        if(!ProductConstDef.DLV_CLF_GLOBAL_DELIVERY.equals(productVO.getBaseVO().getDlvClf()))
            return;

        if (productVO.getPriceVO().getPrdCstmAplPrc() == 0) throw new DeliveryException("11번가 해외배송인 경우 상품신고가는 반드시 입력하셔야 합니다.");


        String [] arrCstmAplPrc =  StringUtil.split(String.valueOf(productVO.getPriceVO().getPrdCstmAplPrc()),".");
        if(arrCstmAplPrc.length >1 && arrCstmAplPrc[1].length()>2)
            throw new DeliveryException("상품신고가는 소수둘째자리 까지만 입력 가능 합니다.");

        if (productVO.getPriceVO().getPrdCstmAplPrc() == 0) throw new DeliveryException("11번가 해외배송인 경우 상품신고가는 반드시 입력하셔야 합니다.");

        if (productVO.getPriceVO().getPrdCstmAplPrc() > ProductConstDef.PRD_CSTM_APL_PRC_LIMIT ){
            throw new DeliveryException("상품신고가는 "+NumberUtil.getCommaString(ProductConstDef.PRD_SEL_PRC_LIMIT)+"원 보다 크게 입력할 수 없습니다.");
        }

    }

    private void checkRtngdDlvCd(ProductVO productVO) throws DeliveryException{
        if (productVO.getBaseVO().getRtngdDlvCd() == null
            || !("01".equals(productVO.getBaseVO().getRtngdDlvCd())
            || "02".equals(productVO.getBaseVO().getRtngdDlvCd()))) {
            throw new DeliveryException("초기무료배송시 반품배송비 코드가 잘못 입력되었습니다.");
        }
    }

    private void checkDlvCustInfo(ProductVO productVO) throws DeliveryException{

        boolean isAbrdBrandYn = ("Y".equals(productVO.getBaseVO().getAbrdBrandYn())) ? true : false;
        boolean is1WonAsk = ("Y".equals(productVO.getMobileYn()) && "Y".equals(productVO.getMobile1WonYn())) ? true : false;

        String dlvCstInstBasiCd = productVO.getBaseVO().getDlvCstInstBasiCd();
        long dlvBasiAmt = productVO.getBaseVO().getPrdFrDlvBasiAmt();
        String bndlDlvCnYn = productVO.getBaseVO().getBndlDlvCnYn();

        if (StringUtil.isEmpty(dlvCstInstBasiCd)){
            throw new DeliveryException("배송비 설정 코드는 반드시 입력하셔야 합니다.");
        }

        if (StringUtil.isNotEmpty(productVO.getBaseVO().getDlvCstPayTypCd())
            && !commonService.isCodeDetail(ProductConstDef.DLV_CST_PAY_TYP_CD, productVO.getBaseVO().getDlvCstPayTypCd())) {
            throw new DeliveryException("유효하지 않은 결제방법코드 입니다.");
        }

        if (is1WonAsk && !ProductConstDef.DLV_CST_INST_BASI_CD_FREE.equals(dlvCstInstBasiCd)){
            throw new DeliveryException("10원 미만 상품 등록 승인 요청시 배송비 설정은 무료만 가능합니다.");
        }

        boolean isHyundai = (StringUtil.nvl(commonService.get1hourTimeProperty(ProductConstDef.HYUNDAI_SELLER_ID),"").indexOf("|" + productVO.getSelMnbdNo() + "|") >= 0);
        boolean isEctSeller = StringUtil.nvl(commonService.get1hourTimeProperty(ProductConstDef.PRD_DLV_CST_ECT_SELLER),"").indexOf("|" + productVO.getSelMnbdNo() + "|") >= 0;

        if(ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(productVO.getBaseVO().getDlvClf())
            && ProductConstDef.INVALID_CONSIGN_DLV_CD.contains(dlvCstInstBasiCd)){
            throw new DeliveryException("셀러위탁배송 시 입력 불가능한 배송비 설정 코드입니다.");
        }


        // 무료 일 경우 무조건 배송비 0원
        String dlvCusMsg = "";
        if (ProductConstDef.DLV_CST_INST_BASI_CD_FREE.equals(dlvCstInstBasiCd)) {
            if (0 != productVO.getBaseVO().getDlvCst()){
                throw new DeliveryException("무료배송 선택 시, 배송비는  반드시 '0'으로 입력하셔야 합니다.");
            }
        }
        // 고정배송비 / 착불
        else if (ProductConstDef.DLV_CST_INST_BASI_CD_COLLECT.equals(dlvCstInstBasiCd)) {
            if(isHyundai){
                checkDlvCustHyundai(productVO.getBaseVO().getDlvCst(), true, "고정배송비 선택 시, ");
            }else if(isEctSeller){
                checkDlvCustHyundai(productVO.getBaseVO().getDlvCst(), false, "고정배송비 선택 시, ");
            }else{
                checkDlvCust(productVO.getBaseVO().getDlvCst(), isAbrdBrandYn, false, false, "고정배송비 선택 시, ");
            }

        }
        // 상품조건부 무료
        else if (ProductConstDef.DLV_CST_INST_BASI_CD_PRD_COND_FREE.equals(dlvCstInstBasiCd)) {
            // 묶음배송 불가
            if ("Y".equals(bndlDlvCnYn)){
                throw new DeliveryException("상품 조건부 무료 선택 시, 묶음배송 설정이 불가능 합니다.");
            }

            if(isHyundai || isEctSeller){
                checkDlvCustHyundai(productVO.getBaseVO().getDlvCst(), false, "상품 조건부 무료 선택 시, ");
            }else{
                checkDlvCust(productVO.getBaseVO().getDlvCst(), isAbrdBrandYn, false, false, "상품 조건부 무료 선택 시, ");
            }

            // 기준배송비 체크
            if (dlvBasiAmt == 0) throw new DeliveryException("상품 조건부 무료 선택 시, 기준금액은 반드시 입력하셔야 합니다.");

            if (dlvBasiAmt % 10 != 0)
                throw new DeliveryException("상품 조건부 무료 선택 시, 기준금액은 10원단위로 입력하셔야 합니다.");

            if (dlvBasiAmt < productVO.getBaseVO().getDlvCst())
                throw new DeliveryException("상품 조건부 무료 선택 시, 기준금액은 배송비보다 크게 입력하셔야 합니다.");
        }
        // 수량별 차등
        else if (ProductConstDef.DLV_CST_INST_BASI_CD_QTY_GRADE.equals(dlvCstInstBasiCd)) {

            if(productVO.getPrdOrderCountBaseDeliveryVOList() == null
                || productVO.getPrdOrderCountBaseDeliveryVOList().size() == 1){
                throw new DeliveryException("수량별 차등 배송 선택 시, 설정값이 유효하지 않습니다.");
            }

            if(productVO.getPrdOrderCountBaseDeliveryVOList().size() > 10){
                throw new DeliveryException("수량별 차등 배송 선택 시, 설정값이 유효하지 않습니다.");
            }

            boolean is1Exist = false;
            boolean isBiggerThanMaxCount = false;
            boolean isExistMaxCount = false;
            for(PrdOrderCountBaseDeliveryVO deliveryVO : productVO.getPrdOrderCountBaseDeliveryVOList()){
                if(deliveryVO.getOrdBgnQty() == 1){
                    is1Exist = true;
                }
                if(deliveryVO.getOrdEndQty() > ProductConstDef.DLV_QTY_GRADE_LAST_CNT_MAX){
                    isBiggerThanMaxCount = true;
                }
                if(deliveryVO.getOrdEndQty() == ProductConstDef.DLV_QTY_GRADE_LAST_CNT_MAX){
                    isExistMaxCount = true;
                }
                if(isHyundai){
                    checkDlvCustHyundai(deliveryVO.getDlvCst(), false, "수량별 차등 배송  선택 시, ");
                }else if(isEctSeller){
                    checkDlvCustHyundai(deliveryVO.getDlvCst(), true, "수량별 차등 배송  선택 시, ");
                }else{
                    checkDlvCust(deliveryVO.getDlvCst(), isAbrdBrandYn, false, true, "수량별 차등 배송  선택 시, ");
                }

                if(deliveryVO.getOrdBgnQty() <= 0 || deliveryVO.getOrdEndQty() <= 0) throw new DeliveryException("수량별 차등 배송 선택 시, 수량 조건은 반드시 입력하셔야 합니다.");

                if(deliveryVO.getOrdBgnQty() >= deliveryVO.getOrdEndQty()) throw new DeliveryException("수량별 차등 배송 선택 시, 수량 조건이 잘못 입력 되었습니다.");
            }
            if(!is1Exist) throw new DeliveryException("수량별 차등 배송 선택 시, 최소 수량 1은 반드시 포함되어야 합니다.");
            if(isBiggerThanMaxCount) throw new DeliveryException("수량별 차등 배송 선택 시, 최대수량은 "+ProductConstDef.DLV_QTY_GRADE_LAST_CNT_MAX+ " 보다 작아야 합니다.");
            if(!isExistMaxCount) throw new DeliveryException("수량별 차등 배송 선택 시, 최대 수량 "+ProductConstDef.DLV_QTY_GRADE_LAST_CNT_MAX+"은 반드시 포함되어야 합니다.");

        }
        // 1개당 배송비
        else if (ProductConstDef.DLV_CST_INST_BASI_CD_UNIT_PRC.equals(dlvCstInstBasiCd)) {
            // 묶음배송 불가
            if ("Y".equals(bndlDlvCnYn)){
                throw new DeliveryException("1개당 배송비 선택 시, 묶음배송 설정이 불가능 합니다.");
            }

            if(isHyundai || isEctSeller){
                checkDlvCustHyundai(productVO.getBaseVO().getDlvCst(), false, "1개당 배송비 선택 시, ");
            }else{
                checkDlvCust(productVO.getBaseVO().getDlvCst(), isAbrdBrandYn, false, false, "1개당 배송비 선택 시, ");
            }
        }
        // 판매자 조건부 기준(구매금액별)
        else if (ProductConstDef.DLV_CST_INST_BASI_CD_SELLER.equals(dlvCstInstBasiCd)) {
            // 묶음배송 불가
            if ("N".equals(bndlDlvCnYn)) throw new DeliveryException("판매자 조건부 배송 선택 시, 묶음배송만 가능 합니다.");
            // 출고지 조건부 기준(구매금액별)
        } else if (ProductConstDef.DLV_CST_INST_BASI_CD_ADDR.equals(dlvCstInstBasiCd)) {
            // 묶음배송만 가능
            if ("N".equals(bndlDlvCnYn)) throw new DeliveryException("출고지 조건부 배송 선택 시, 묶음배송만 가능 합니다.");
            // 통합 출고지 조건부 기준(구매금액별)
        } else if (ProductConstDef.DLV_CST_INST_BASI_CD_ITG_ADDR.equals(dlvCstInstBasiCd)) {
            // 묶음배송만 가능
            if ("N".equals(bndlDlvCnYn)) throw new DeliveryException("통합출고지 조건부 배송 선택 시, 묶음배송만 가능 합니다.");

            // 해외 통합출고지 조건부 기준(무게별)
        } else if (ProductConstDef.DLV_CST_INST_BASI_CD_GLB_ITG_ADDR.equals(dlvCstInstBasiCd)) {
            long prdWght = productVO.getBaseVO().getPrdWght();

            // 상품무게 입력 여부 체크
            if (prdWght <= 0) throw new DeliveryException("해외 통합출고지 조건부 배송 선택 시, 상품무게를 설정해야 합니다.");
            if( prdWght>=ProductConstDef.PRD_WGHT_LIMIT) throw new DeliveryException("무게는 "+ProductConstDef.PRD_WGHT_LIMIT+"g 미만으로만 입력 가능 합니다.");
            //해외 명품직매입
        } else if (ProductConstDef.DLV_CST_INST_BASI_CD_FR_ITG_ADDR.equals(dlvCstInstBasiCd)) {


            // NOW배송비
        } else if (ProductConstDef.DLV_CST_INST_BASI_CD_NOW.equals(dlvCstInstBasiCd)) {
            if(!(ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(productVO.getBaseVO().getDlvClf()) || productVO.getSellerVO().isSellableBundlePrdSeller()))
                throw new DeliveryException("설정 할 수 없는 배송비설정 코드 입니다.");

            // 묶음배송 불가
            if ("N".equals(bndlDlvCnYn)) throw new DeliveryException("NOW배송 선택 시, 묶음배송만 가능 합니다.");

            if(isHyundai || isEctSeller){
                checkDlvCustHyundai(productVO.getBaseVO().getDlvCst(), false, "NOW배송 선택 시, ");
            }else{
                checkDlvCust(productVO.getBaseVO().getDlvCst(), isAbrdBrandYn, false, false, "NOW배송 선택 시, ");
            }

            // 기준배송비 체크
            if (dlvBasiAmt == 0)
                throw new DeliveryException("NOW배송 선택 시, 기준금액은 반드시 입력하셔야 합니다.");

            if (dlvBasiAmt % 10 != 0)
                throw new DeliveryException("NOW배송 선택 시, 기준금액은 10원단위로 입력하셔야 합니다.");

            if (dlvBasiAmt < productVO.getBaseVO().getDlvCst())
                throw new DeliveryException("NOW배송 선택 시, 기준금액은 배송비보다 크게 입력하셔야 합니다.");
        } else {
            throw new DeliveryException("유효하지 않은 배송비설정 코드 입니다.");
        }
    }

    private void checkDlvCust(long dlvCst, boolean isAbrdBrandYn, boolean isExchYn, boolean isAbleZero, String msg) throws DeliveryException{
        if (!isAbleZero && dlvCst == 0) {
            throw new DeliveryException(msg +"배송비는 반드시 입력하셔야 합니다.");
        }
        if (dlvCst % 10 != 0)	{
            throw new DeliveryException(msg +"배송비는 10원단위로 입력하셔야 합니다.");
        }

        long chkAmt = 0;
        if (isExchYn) {	//교환
            chkAmt = StringUtil.getLong(commonService.get1hourTimeProperty(isAbrdBrandYn ? "PRD_ABRD_EXCH_DLV_CST" : "PRD_EXCH_DLV_CST"));
        } else { //반품
            chkAmt = StringUtil.getLong(commonService.get1hourTimeProperty(isAbrdBrandYn ? "PRD_ABRD_RTNGD_DLV_CST" : "PRD_RTNGD_DLV_CST"));
        }
        if (chkAmt == 0){
            chkAmt = isAbrdBrandYn ? 400000 : 200000;
        }
        if (dlvCst > chkAmt) {
            throw new DeliveryException(msg +"배송비는 " + NumberUtil.getCommaString(chkAmt) + "원을 초과하여 설정할 수 없습니다.");
        }
    }

    private void checkDlvCustHyundai(long dlvCst, boolean isAbleZero, String msg) throws DeliveryException{
        if(!isAbleZero && dlvCst == 0){
            throw new DeliveryException(msg + "배송비는 반드시 입력하셔야 합니다.");
        }
        if (dlvCst % 10 != 0)	{
            throw new DeliveryException(msg + "배송비는 10원단위로 입력하셔야 합니다.");
        }

        long chkAmt = StringUtil.getLong(commonService.get1hourTimeProperty("PRD_ECT_DLV_CST"));
        if(chkAmt == 0){
            chkAmt = 700000;
        }
        if (dlvCst > chkAmt) {
            throw new DeliveryException(msg + "배송비는 " + NumberUtil.getCommaString(chkAmt) + "원을 초과하여 설정할 수 없습니다.");
        }
    }

    private void checkInOutAddr(ProductVO productVO) throws DeliveryException{
        long outAddrSeq = productVO.getBaseVO().getOutAddrSeq();
        String outMbAddrLocation = productVO.getBaseVO().getOutMbAddrLocation();//mbAddrLocation02
        long inAddrSeq = productVO.getBaseVO().getInAddrSeq();
        String inMbAddrLocation = productVO.getBaseVO().getInMbAddrLocation();//mbAddrLocation03
        long visitAddrSeq = productVO.getBaseVO().getVisitAddrSeq();
        String visitMbAddrLocation = productVO.getBaseVO().getVisitMbAddrLocation();//mbAddrLocation04
        String visitDlvYn = productVO.getBaseVO().getVisitDlvYn();
        long memNo = productVO.getSelMnbdNo();
        long outMemNo = productVO.getBaseVO().getOutMemNo();
        long inMemNo = productVO.getBaseVO().getInMemNo();


        if (StringUtil.equals("Y", visitDlvYn)) {
            throw new DeliveryException("매입/자체배송상품은 방문수령이 불가 합니다");
        }

        if(outAddrSeq <= 0) throw new DeliveryException("입력된 출고지 주소 정보가 없습니다.");
        if(inAddrSeq <= 0) throw new DeliveryException("입력된 반품/교환지 주소 정보가 없습니다.");
        if(StringUtil.isEmpty(outMbAddrLocation)) throw new DeliveryException("입력된 출고지 주소의 지역(국내/해외)정보가 없습니다.");
        if(StringUtil.isEmpty(inMbAddrLocation)) throw new DeliveryException("입력된 반품/교환지 주소의 지역(국내/해외)정보가 없습니다.");

        if("Y".equals(visitDlvYn)) {
            if(visitAddrSeq <= 0) throw new DeliveryException("입력된 방문수령지 주소 정보가 없습니다.");
            if(StringUtil.isEmpty(visitMbAddrLocation)) throw new DeliveryException("입력된 방문수령지 주소의 지역(국내/해외)정보가 없습니다.");
        }

        // 출고지 주소 확인
        boolean isExistOutAddr = this.isExistUserAddressSeq(outMemNo,
                                                            outAddrSeq,
                                                            ProductConstDef.MEM_PRD_ADDR_CD_OUT,
                                                            outMbAddrLocation);
        if(!isExistOutAddr) throw new DeliveryException("입력된 출고지 주소는 판매자의 출고지 주소가 아니거나 존재하지 않는 주소입니다.");


        // 반품/교환지 주소 확인
        boolean isExistInAddr = this.isExistUserAddressSeq(inMemNo,
                                                           inAddrSeq,
                                                           ProductConstDef.MEM_PRD_ADDR_CD_IN,
                                                           inMbAddrLocation);
        if(!isExistInAddr) throw new DeliveryException("입력된 반품/교환지 주소는 판매자의 반품/교환지 주소가 아니거나 존재하지 않는 주소입니다.");

        if("Y".equals(visitDlvYn)) {
            // 방문수령지 주소 확인
            boolean isExistVisitAddr = this.isExistUserAddressSeq(memNo,
                                                                  visitAddrSeq,
                                                                  ProductConstDef.MEM_PRD_ADDR_CD_VISIT,
                                                                  visitMbAddrLocation);
            if(!isExistVisitAddr) throw new DeliveryException("입력된 방문수령지 주소는 판매자의 방문수령지 주소가 아니거나 존재하지 않는 주소입니다.");
        }

        // 11번가 해외배송일 경우 해외 판매자 출고지, 반품/교환지 주소 확인
        if (ProductConstDef.DLV_CLF_GLOBAL_DELIVERY.equals(productVO.getBaseVO().getDlvClf())){

            long globalOutAddrSeq = productVO.getBaseVO().getGlobalOutAddrSeq();
            long globalInAddrSeq = productVO.getBaseVO().getGlobalInAddrSeq();
            String globalOutMbAddrLocation = productVO.getBaseVO().getGlobalOutMbAddrLocation();
            String globalInMbAddrLocation = productVO.getBaseVO().getGlobalInMbAddrLocation();

            if(globalOutAddrSeq <= 0) throw new DeliveryException("입력된 판매자 해외 출고지 주소 정보가 없습니다.");
            if(globalInAddrSeq <= 0) throw new DeliveryException("입력된 판매자 반품/교환지 주소 정보가 없습니다.");
            if(StringUtil.isEmpty(globalOutMbAddrLocation)) throw new DeliveryException("입력된 판매자 해외 출고지 주소의 지역 정보가 없습니다.");
            if(StringUtil.isEmpty(globalInMbAddrLocation)) throw new DeliveryException("입력된 판매자 반품/교환지 주소의 지역 정보가 없습니다.");
            if(ProductConstDef.PRD_ORGN_TYP_CD_ABRD.equals(globalOutMbAddrLocation)) throw new DeliveryException("판매자 해외 출고지 주소는 해외주소만 가능합니다.");


            // 판매자 해외 출고지 주소 확인
            boolean isExistGlobalOutAddr = this.isExistUserAddressSeq(memNo,
                                                                      globalOutAddrSeq,
                                                                      ProductConstDef.MEM_PRD_ADDR_CD_OUT,
                                                                      globalOutMbAddrLocation);
            if(!isExistGlobalOutAddr) throw new DeliveryException("입력된 판매자 해외 출고지 주소는 판매자의 출고지 주소가 아니거나 존재하지 않는 주소입니다.");


            // 반품/교환지 주소 확인
            boolean isExistGlobalInAddr = this.isExistUserAddressSeq(memNo,
                                                                     globalInAddrSeq,
                                                                     ProductConstDef.MEM_PRD_ADDR_CD_IN,
                                                                     globalInMbAddrLocation);
            if(!isExistGlobalInAddr) throw new DeliveryException("입력된 판매자 반품/교환지 주소는 판매자의 반품/교환지 주소가 아니거나 존재하지 않는 주소입니다.");
        }
        this.checkGlobalDlv(productVO);
    }

    private void checkGlobalDlv(ProductVO productVO) throws DeliveryException{
        if(!GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getDlvClf(), DlvClfCdTypes.ELEVENST_ABROAD_DELIVERY)){
            return;
        }
        boolean isAbrdBrandYn = ("Y".equals(productVO.getBaseVO().getAbrdBrandYn())) ? true : false;
        String dlvCstInstBasiCd = productVO.getBaseVO().getDlvCstInstBasiCd();	// 배송비설정
        boolean isFreePickup = false;	// 11번가 무료 픽업 여부

        // 배송주체가 11번가 해외배송인지 여부 체크
        if(!ProductConstDef.DLV_CST_INST_BASI_CD_GLB_ITG_ADDR.equals(dlvCstInstBasiCd)){
            throw new DeliveryException("11번가 해외배송시 해외 통합출고지 조건부 배송비를 설정하셔야 합니다.");
        }
            // 통합 출고지/반품교환지 주소 판매자 정보 비교
        else if(productVO.getBaseVO().getOutMemNo() != productVO.getBaseVO().getInMemNo()){
            throw new DeliveryException("11번가 해외배송시 통합출고지와 반품교환지 주소의 판매자가 같아야 합니다.");
        }
            // 해외 입고유형 데이터 존재 여부 체크
        else if(productVO.getBaseVO().getAbrdInCd() == null || "".equals(productVO.getBaseVO().getAbrdInCd())){
            throw new DeliveryException("11번가 해외배송시 11번가 해외 입고유형을 설정하셔야 합니다.");
        }
            // 해외 입고유형 체크 (11번가 무료픽업일 경우)
        else if(ProductConstDef.ABRD_IN_CD_FREE.equals(productVO.getBaseVO().getAbrdInCd())){
            this.checkAbrdInFreePickup(productVO);
            isFreePickup = true;
        }
        // 결제방법 선결제 필수 여부 체크
        else if(!ProductConstDef.DLV_CST_PAY_TYP_CD_ESS_PRE_PAY.equals(productVO.getBaseVO().getDlvCstPayTypCd())){
            throw new DeliveryException("11번가 해외배송시 결제방법은 선결제 필수만 가능합니다.");
        }

        // 11번가 해외배송에 출고지 주소가 해외(02)인 경우 해외취소 배송비 유효성 체크 ===================================
        if (productVO.getBaseVO().getOutMbAddrLocation() == null || "".equals(productVO.getBaseVO().getOutMbAddrLocation().trim())) {
            throw new DeliveryException("입력된 출고지 주소의 지역(국내/해외)정보가 없습니다.");
        }else if (productVO.getBaseVO().getOutMbAddrLocation().equals("02")) {
            // 11번가 해외배송에 11번가 무료픽업이면 해외취소 배송비는 0원 이여야 함.
            if(isFreePickup){
                if(!"0".equals(productVO.getBaseVO().getAbrdCnDlvCst())){
                    throw new DeliveryException("해당 출고지는 11번가 무료픽업대상이므로 해외 취소 배송비를 부과할 수 없습니다.");
                }
            }else{
                if(StringUtil.nvl(commonService.get1hourTimeProperty(ProductConstDef.PRD_DLV_CST_ECT_SELLER),"").indexOf("|" + productVO.getSelMnbdNo() + "|") >= 0){
                    this.checkDlvCustHyundai(productVO.getBaseVO().getAbrdCnDlvCst(), false, "해외취소 ");
                }else{
                    this.checkDlvCust(productVO.getBaseVO().getAbrdCnDlvCst(), isAbrdBrandYn, false, false, "해외취소 ");
                }
            }
        }
    }

    private void checkAbrdInFreePickup(ProductVO productVO) throws DeliveryException{
        // 11번가 무료픽업 해외 입고유형 체크

        String dlvClf = productVO.getBaseVO().getDlvClf();	// 배송주체
        long outAddrSeq = productVO.getBaseVO().getGlobalOutAddrSeq();	// 판매자 해외 출고지 seq
        long memNo = productVO.getSelMnbdNo();	// 판매자 번호

        // 11번가 해외배송일 경우 해외 판매자 출고지, 반품/교환지 주소 확인
        if (dlvClf != null && ProductConstDef.DLV_CLF_GLOBAL_DELIVERY.equals(dlvClf)) {
            if(outAddrSeq <= 0) throw new DeliveryException("입력된 판매자 해외 출고지 주소 정보가 없습니다.");

            ClaimAddressInfoVO claimAddressInfoVO = new ClaimAddressInfoVO();
            claimAddressInfoVO.setAddrCd(ProductConstDef.MEM_PRD_ADDR_CD_OUT); // 판매자 해외 출고지
            claimAddressInfoVO.setAddrSeq(outAddrSeq);
            claimAddressInfoVO.setMemNo(memNo);

            // 11번가 무료픽업 해외 입고유형 확인
            ClaimAddressInfoVO resultVO = deliveryMapper.isFreePickupSupplyTarget(claimAddressInfoVO);

            if (resultVO == null || !"Y".equals(resultVO.getFreePickupYn())) {
                throw new DeliveryException("판매자의 업체출고지는 11번가 무료픽업 제공 대상이 아닙니다.");
            }
        }
    }

    private boolean isExistUserAddressSeq(long memNo, long addrSeq, String addrCd, String mbAddrLocation) throws DeliveryException{
        ClaimAddressInfoVO claimAddressInfoVO = new ClaimAddressInfoVO();
        claimAddressInfoVO.setMemNo(memNo);
        claimAddressInfoVO.setAddrSeq(addrSeq);
        claimAddressInfoVO.setAddrCd(addrCd);
        claimAddressInfoVO.setMbAddrLocation(mbAddrLocation);
        long cnt = deliveryMapper.isExistUserAddressSeq(claimAddressInfoVO);

        if(cnt > 0){
            return true;
        }else{
            return false;
        }
    }

    private void checkDlvWyCd(ProductVO productVO) throws DeliveryException{
        if (StringUtil.isEmpty(productVO.getBaseVO().getDlvWyCd()))	throw new DeliveryException("배송방법 코드는 반드시 입력하셔야 합니다.");
        if (!commonService.isCodeDetail(ProductConstDef.DLV_WY_CD, productVO.getBaseVO().getDlvWyCd())){
            throw new DeliveryException("유효하지 않은 배송방법코드 입니다.");
        }
    }

    private void checkDlvCnAreaCd(ProductVO productVO) throws DeliveryException{
        if (StringUtil.isEmpty(productVO.getBaseVO().getDlvCnAreaCd()))	throw new DeliveryException("배송가능지역 코드는 반드시 입력하셔야 합니다.");
        if (!commonService.isCodeDetail(ProductConstDef.DLV_CN_AREA_CD, productVO.getBaseVO().getDlvCnAreaCd())){
            throw new DeliveryException("유효하지 않은 배송가능지역 코드 입니다.");
        }
    }

    private void setDeliveryInfo(ProductVO preProductVO, ProductVO productVO) throws DeliveryException{

        long userNo 	= productVO.getSelMnbdNo();		// 셀러 번호
        String memType = productVO.getMemberVO().getMemTypCD();		// 셀러 타입
        String dlvClf = productVO.getBaseVO().getDlvClf();				// 배송유형 체크(값이 존재하지 않을 경우 디폴트 배송유형(업체배송)으로 설정)


        // 거래유형이 수수료일때 회원유형코드가 통합로그인일 경우
        if (ProductConstDef.PRD_MEM_TYPE_INTEGRATION.equals(memType)) {
            throw new DeliveryException("거래유형이 수수료인 통합 아이디는 상품등록이 불가 합니다.");
        }
        // 거래유형이 수수료일때 회원유형코드가 글로벌셀러일 경우
        else if(ProductConstDef.PRD_MEM_TYPE_GLOBAL.equals(memType)) {
            // 해당 셀러의 해외 통합아이디가 존재하고 배송유형이 존재하지 않을 경우
            if(customerService.isGlbItgIdsBySeller(userNo) && StringUtil.isEmpty(dlvClf)){
                throw new DeliveryException("배송주체는 반드시 선택되어야 합니다.");
            }
            // 배송주체가 11번가 해외자체배송일 경우 배송주체를 11번가 해외자체배송으로 선택이 가능한 해외셀러인지 여부 체크
            else if(ProductConstDef.DLV_CLF_GLOBAL_DELIVERY.equals(dlvClf)){
                if(customerService.isGlbItgIdsBySeller(userNo)){
                    throw new DeliveryException("해당 해외 셀러의 해외 통합아이디가 존재하지 않습니다.");
                }
            }else{
                productVO.getBaseVO().setDlvClf(ProductConstDef.DLV_CLF_OUT_DELIVERY);
            }
        }
        //11번가 통합배송일 경우
        else if(ProductConstDef.DLV_CLF_IN_DELIVERY.equals(dlvClf)) {

            // 상품 수정일 경우 배송주체 변경 여부 체크
            if (productVO.isUpdate()){
                if (StringUtil.isNotEmpty(dlvClf) && ProductConstDef.DLV_CLF_OUT_DELIVERY.equals(dlvClf)
                    && ProductConstDef.DLV_CLF_IN_DELIVERY.equals(preProductVO.getBaseVO().getDlvClf())){
                    throw new DeliveryException("상품의 배송주체를 11번가 통합배송에서 업체배송으로 변경하실 수 없습니다.");
                }
            }

        }
        //위탁배송일경우
        else if(ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(dlvClf)) {

            // 상품 수정일 경우 배송주체 변경 여부 체크
            if (productVO.isUpdate()){
                if (StringUtil.isNotEmpty(dlvClf) && ProductConstDef.DLV_CLF_OUT_DELIVERY.equals(dlvClf)
                    && ProductConstDef.DLV_CLF_CONSIGN_DELIVERY.equals(preProductVO.getBaseVO().getDlvClf())) {
                    throw new DeliveryException("상품의 배송주체를 셀러위탁배송에서 업체배송으로 변경하실 수 없습니다.");
                }
            }
        }
        else {
            // 배송유형 업체 배송으로 설정
            productVO.getBaseVO().setDlvClf(ProductConstDef.DLV_CLF_OUT_DELIVERY);
        }
        // 업체배송일 경우 해외취소 배송비가 입력 되었을 경우 해외취소 배송비를 0으로 강제 설정
        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getDlvClf(), DlvClfCdTypes.COMPANY_DELIVERY)){
            productVO.getBaseVO().setAbrdCnDlvCst(0);
        }
    }

    private void setGblDlvInfo(ProductVO preProductVO, ProductVO productVO) throws DeliveryException{

        if(productVO.isUpdate()){
            // 11번가 해외배송일경우 제외
            if (!ProductConstDef.DLV_CLF_GLOBAL_DELIVERY.equals(productVO.getBaseVO().getDlvClf())){
                productVO.getBaseVO().setPrdWght(preProductVO.getBaseVO().getPrdWght());	//이전 무게값 셋팅
            }

            if(StringUtil.isNotEmpty(preProductVO.getBaseVO().getGblHsCode())){
                //전세계배송  gblHsCodeNo 체크 및 set
                this.checkGblHsCode(productVO);
            }
        }else{
            productVO.getBaseVO().setGblHsCode("");
            productVO.getBaseVO().setNtNo(0);
            //11번가 해외배송일경우 제외
            if (!ProductConstDef.DLV_CLF_GLOBAL_DELIVERY.equals(productVO.getBaseVO().getDlvClf())) productVO.getBaseVO().setPrdWght(0);
        }
    }


    /**
     * 전세계배송 이용 여부 - (구) ProductValidate.checkUseGlobalDeliver(userNo)
     *
     * @return
     */
    public boolean isUseGlobalDeliver(long memNo) throws DeliveryException {
        boolean result = false;
        MemberGlobalDeliverVO resultVO;
        try {
            resultVO = deliveryMapper.getGlobalDeliverSeller(memNo);
            if (resultVO != null) {
                result = "Y".equals(resultVO.getGblDlvYn()) ? true : false;
            }
        } catch (Exception ex) {
            throw new DeliveryException("전세계배송 이용 여부 조회 오류", ex);
        }
        return result;
    }

    private void checkValidGblDlvYn(ProductVO productVO) throws DeliveryException{
        if(!"Y".equals(productVO.getCategoryVO().getGblDlvYn())){
            throw new DeliveryException("선택한 카테고리는 전세계배송이 불가능한 카테고리 입니다.");
        }

        if(OptionConstDef.OPT_CLF_CD_IDEP.equals(productVO.getOptionVO().getOptClfCd())){
            throw new DeliveryException("전세계배송은 상품옵션을 독립형으로 설정할 수 없습니다.");
        }
        this.checkGblHsCode(productVO);


        // 배송방법
        if(!ProductConstDef.DLV_WY_CD_DLV.equals(productVO.getBaseVO().getDlvWyCd())
            && !ProductConstDef.DLV_WY_CD_DLV_POST.equals(productVO.getBaseVO().getDlvWyCd())){
            throw new DeliveryException("전세계배송은 배송방법이  \"택배 이거나, 우편(소포/등기)\" 인경우만 가능합니다.");
        }
        //방문수령 추가
        if("Y".equals(productVO.getBaseVO().getVisitDlvYn())) {
            throw new DeliveryException("전세계배송 이용여부가 \"이용\" 인경우 \"방문수령 추가\" 는 불가능합니다.");
        }

        if(productVO.getBaseVO().getPrdWght() > 0){
            if(productVO.getBaseVO().getPrdWght() <= 0) {
                throw new DeliveryException("전세계배송 이용여부가 \"이용\" 인경우 상품무게를  반드시 입력하셔야 합니다.");
            }
            else if(productVO.getBaseVO().getPrdWght() >= ProductConstDef.PRD_WGHT_LIMIT){
                throw new DeliveryException("전세계배송 이용여부가 \"이용\" 인경우 상품무게는 "+ProductConstDef.PRD_WGHT_LIMIT+"g 미만으로만 입력 가능 합니다");
            }
        }else{
            //상품무게 미입력시 소카테고리 기본 상품무게값 가져오기...없을경우 alert
            if(StringUtil.getLong(productVO.getCategoryVO().getGblDvlPrdWght()) > 0){
                productVO.getBaseVO().setPrdWght(StringUtil.getLong(productVO.getCategoryVO().getGblDvlPrdWght()));
            }else{
                throw new DeliveryException("전세계배송 이용여부가 \"이용\" 인경우 상품무게를  반드시 입력하셔야 합니다.");
            }
        }

        if(productVO.getBaseVO().getOutAddrSeq() <= 0) throw new DeliveryException("입력된 출고지 주소 정보가 없습니다.");
        if(StringUtil.isEmpty(productVO.getBaseVO().getOutMbAddrLocation())) throw new DeliveryException( "입력된 출고지 주소의 지역(국내/해외)정보가 없습니다.");

        // 배송비 무료 및 결제방법 체크
        if(!GroupCodeUtil.isContainsDtlsCd(productVO.getBaseVO().getDlvCstInstBasiCd(), DlvCstInstBasiCd.FREE)
            && ProductConstDef.DLV_CST_PAY_TYP_CD_NONE_PRE_PAY.equals(productVO.getBaseVO().getDlvCstPayTypCd())){
            throw new DeliveryException("전세계배송은  배송비가 \"무료\" 이거나, 배송비 결제방법이 \"선결제가능 또는 선결제 필수\" 일때만 가능합니다.");
        }


        // 생산지국가(통관용)코드  체크
        List<HashMap<String, Object>> getNationList = null;
        HashMap<String, Object> map = new HashMap<String, Object>();
        if(productVO.getBaseVO().getNtNo() <= 0){
            productVO.getBaseVO().setNtNo(304);
        }else{
            map.put("ntNo", productVO.getBaseVO().getNtNo());
            map.put("CLFCD", "CREATE");
            getNationList = productService.getNationList(map);

            if(getNationList == null) {
                throw new DeliveryException("존재하지 않는 전세계배송 생산지코드입니다." );
            }
        }
        // 출고지주소
        if(!"01".equals(productVO.getBaseVO().getOutMbAddrLocation())){
            throw new DeliveryException("전세계배송은 출고지가  \"국내\" 인경우만 가능합니다.");
        }
    }

    private void checkGblHsCode(ProductVO productVO) throws DeliveryException{

        if(StringUtil.isNotEmpty(productVO.getBaseVO().getGblHsCode())){

            try {
                Long.parseLong(productVO.getBaseVO().getGblHsCode());
            }catch (Exception e) {
                throw new DeliveryException("전세계배송HSCode는 숫자만 입력가능합니다.");
            }

            BaseVO baseVO = deliveryMapper.getGlobalHsCode(productVO.getBaseVO());
            if(baseVO == null){
                throw new DeliveryException("전세계배송HSCode가 잘 못 입력되었습니다.");
            }else{
                productVO.getBaseVO().setGblHsCodeNo(baseVO.getGblHsCodeNo());
            }
        }else{
            if(productVO.getCategoryVO().getGblHsCodeNo() < 1){
                throw new DeliveryException("전세계배송 이용여부가 \"이용\" 인경우  전세계배송 HSCode는 반드시 입력하셔야 합니다.");
            }
            productVO.getBaseVO().setGblHsCodeNo(productVO.getCategoryVO().getGblHsCodeNo());
        }
    }
}
