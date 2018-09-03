package com.elevenst.terroir.product.registration.product.vo;

import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.terroir.product.registration.addtinfo.vo.ProductAddtInfoVO;
import com.elevenst.terroir.product.registration.benefit.vo.CustomerBenefitVO;
import com.elevenst.terroir.product.registration.benefit.vo.ProductGiftVO;
import com.elevenst.terroir.product.registration.catalog.vo.CtlgVO;
import com.elevenst.terroir.product.registration.catalog.vo.PrdModelVO;
import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import com.elevenst.terroir.product.registration.category.vo.DpGnrlDispVO;
import com.elevenst.terroir.product.registration.cert.vo.ProductCertInfoVO;
import com.elevenst.terroir.product.registration.common.code.BsnDealClf;
import com.elevenst.terroir.product.registration.coordi_prd.vo.CoordiProductVO;
import com.elevenst.terroir.product.registration.ctgrattr.vo.CtgrAttrCompVO;
import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.deal.vo.ProductEvtVO;
import com.elevenst.terroir.product.registration.delivery.vo.ClaimAddressInfoVO;
import com.elevenst.terroir.product.registration.delivery.vo.PrdOrderCountBaseDeliveryVO;
import com.elevenst.terroir.product.registration.desc.vo.ProductDescVO;
import com.elevenst.terroir.product.registration.etc.vo.PrdEtcVO;
import com.elevenst.terroir.product.registration.etc.vo.PrdOthersVO;
import com.elevenst.terroir.product.registration.fee.vo.FeeVO;
import com.elevenst.terroir.product.registration.image.vo.ProductImageVO;
import com.elevenst.terroir.product.registration.mobile.vo.PdPrdMobileFeeVO;
import com.elevenst.terroir.product.registration.option.vo.OptionVO;
import com.elevenst.terroir.product.registration.option.vo.ProductStockVO;
import com.elevenst.terroir.product.registration.pln_prd.vo.PlanProductVO;
import com.elevenst.terroir.product.registration.price.vo.PriceVO;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.seller.vo.SellerVO;
import com.elevenst.terroir.product.registration.template.vo.TemplateVO;
import com.elevenst.terroir.product.registration.wms.vo.WmsVO;
import lombok.Data;
import skt.tmall.auth.Auth;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductVO implements Serializable{

    private static final long serialVersionUID = 1485985614433879179L;
    private long prdNo;
    private String prdNm;
    private long selMnbdNo;
    private long dispCtgrNo;
    private String dispCtgrCd;
    private long createNo;
    private String createDt;
    private long updateNo;
    private String updateDt;
    private String histAplBgnDt;
    private String updateYn;
    private String channel = "";

    /**
     * 재등록 상품번호
     */
    private long reRegPrdNo;

    private BaseVO baseVO = null;
    private CategoryVO categoryVO = null;
    private SellerVO sellerVO = null;
    private CtlgVO ctlgVO = null;
    private OptionVO optionVO;
    private MemberVO memberVO;
    private PriceVO priceVO;
    private PrdOthersVO prdOthersVO;
    private PrdEtcVO prdEtcVO;
    private DpGnrlDispVO dpGnrlDispVO;
    private WmsVO wmsVO;
    private ProductStockVO productStockVO;
    private ProductEvtVO productEvtVO;
    private PrdModelVO prdModelVO;
    private ProductGiftVO productGiftVO;
    private ProductImageVO productImageVO;
    private Auth auth;

    //얘약상품//
    private PlanProductVO planProductVO;
    //

    //코디상품//
    private List<CoordiProductVO> coordiProductVOList;
    //

    // addtinfo 상품부가정보
    private ProductAddtInfoVO productAddtInfoVO;
    private List<ProductAddtInfoVO> productAddtInfoVOList;
    // -- addtinfo

    // 상품설명
    private ProductDescVO productDescVO;
    private List<ProductDescVO> productDescVOList;
    //

    // InOutAddressVO 출고지 반품교환지
//    private ClaimAddressInfoVO claimAddressInfoVO;
    private List<ClaimAddressInfoVO> claimAddressInfoVOList;
    // -- InOutAddressVO

    // delivery VO 배송
//    private PrdOrderCountBaseDeliveryVO prdOrderCountBaseDeliveryVO;
    private List<PrdOrderCountBaseDeliveryVO> prdOrderCountBaseDeliveryVOList;
//    private TrGlobalProductAverageDeliveryVO trGlobalProductAverageDeliveryVO;
//    private List<TrGlobalProductAverageDeliveryVO> trGlobalProductAverageDeliveryVOList;
    //--delivery VO

    // addCompositionProductVO 추가구성상품
    private List<ProductAddCompositionVO> productAddCompositionVOList;
    private List<ProductCoordiVO> productCoordiVOList;
    // -- addCompositionProductVO

    // productCertInfo 인증정보
//    private ProductCertInfoVO productCertInfoVO;
    private List<ProductCertInfoVO> productCertInfoVOList;
    // -- productCertInfo

    // 혜택
    private CustomerBenefitVO customerBenefitVO;
//    private List<CustomerBenefitVO> customerBenefitVOList;
    // -- 혜택

    //전시 아이템
    private List<ProductItemVO> productItemVOList;

    private List<CtgrAttrCompVO> ctgrAttrCompVOList;

    //원재료 정보
    @Deprecated
    private ProductRmaterialVO productRmaterialVO;

    private List<ProductRmaterialVO> productRmaterialVOList;

    //표준상품(단일상품) 속성 등록
    private List<StdPrdAttrCompVO> stdPrdAttrCompVOList;

    private List<ProductRsvSchdlVO> productRsvSchdlVOList;      //예약스케쥴 목록
    private List<ProductRsvSchdlVO> productRsvLmtQtyVOList;     //예약스케쥴 템플릿

    //상품태그
    private List<ProductTagVO> productTagVOList;

    //배송 템플릿 정보
    private TemplateVO templateVO;

    //수수료 아이템 정보
    private FeeVO feeVO;

    private String bulkProductYn = "N";
    private boolean isUpdate = false;
    private boolean hmallVod = false;
    private String useGiftYn;
    private String isOpenApi = "N";
    private boolean idepOpt;
    private boolean custOpt;
    private boolean purchaseType;
    private String optionSaveYn;
    private String minusOptLimitYn;
    private String prdCopyYn;
    private String optChangeYn;
    private String offerDispLmtYn;
    private String brodOnlyProductYn;
    private String reNewYn;
    private boolean isTown;
    private String mobile1WonYn;            //10원미만 상품등록 승인요청 여부
    private String mobile1WonApprvAsk;      //모바일1원 상품 승인요청 여부;
    private String mobileYn;
    private String selInfoTempleteYn;					// 배송정보 템플릿 저장여부
    private String selInfoTempleteNm;					// 배송정보 템플릿명
    private boolean bookCtgr = false;

    //bundle
    private boolean bundleProduct;
    private String martPrdYn;
    private boolean seriesPrdYn = false;
    private boolean isTestProduct = false;
    private String useYn;
    private String keywdNm;
    private long start;
    private long end;


    private RentalVO rentalVO;

    private List<PdPrdMobileFeeVO> productMobileFeeVOList;    //상품 휴대폰 요금제 정보 리스트.
    private String histAplEndDt;
    private String ptnrPrdClfCd;
    private String officeGubun;
    private String strSetTypCd;
    private String skMemberPartnerCd;
    private String skMemberDscRt;

    // 판매방식 전환 여부
    private String selMthdCdChangeYn;

    private boolean qcVerifiedStat;
    private String  dispAreaDmlCd;      // 전시영역코드 DML 코드
    private String[]  recomCtgrNos;
    private long rtnType;
    private String useLimitClfCd;  //사용제한구분
    private String usePrdClfCd;	   //사용기간코드

    public boolean isPurchaseType(){
        return GroupCodeUtil.isContainsDtlsCd(this.getBaseVO().getBsnDealClf()
            ,BsnDealClf.DIRECT_PURCHASE
            ,BsnDealClf.SPECIFIC_PURCHASE
            ,BsnDealClf.TRUST_PURCHASE);

    }

    public boolean isTown(){
        return GroupCodeUtil.isContainsDtlsCd(this.getBaseVO().getPrdTypCd()
            , PrdTypCd.PIN_11ST_SEND
            , PrdTypCd.PIN_ZERO_COST);
    }

    public boolean isEngDispYnByGlobal(){
        //카테고리
        //영문사이트 노출 여부 : Y
        //성인인증 카테고리 여부 : N
        //미성년자 주문제한여부 : N 또는 빈값 (Y가 아니면)

        //상품
        //상품타입(PD018) : 01-일반배송상품, 24-소호, 28-마트11번가
        //해외구매대행상품구분(PD099) : 01-일반
        //19금 상품 여부 : N -> 미성년자 구매가능 : Y
        return ("Y".equals(this.getCategoryVO().getEngDispYn())
            && "N".equals(this.getCategoryVO().getAdltCrtfYn())
            && !"Y".equals(this.getCategoryVO().getMinorOrdLmtYn())
            && GroupCodeUtil.isContainsDtlsCd(this.getBaseVO().getPrdTypCd(), PrdTypCd.NORMAL,PrdTypCd.SOHO,PrdTypCd.MART)
            && ProductConstDef.PRD_GLOBAL_PROXY_CD_LOCAL.equals(this.getBaseVO().getForAbrdBuyClf())
            && "Y".equals(this.getBaseVO().getMinorSelCnYn()) );
    }

    public boolean isLifeSvcTyp() {
        boolean isRetTyp = false;
        if(GroupCodeUtil.isContainsDtlsCd(
            this.getBaseVO().getPrdTypCd()
            ,PrdTypCd.STORE_VISIT
            ,PrdTypCd.VISITING_SERVICE
            ,PrdTypCd.VISITING_PICKUP_DELIVERY)){
            isRetTyp = true;
        }
        return isRetTyp;
    }

    public boolean isBookCtgr() {
        return (this.getBaseVO().getDispCtgr1NoDe() == ProductConstDef.BOOK_DVD_CTGR_NO
            || this.getBaseVO().getDispCtgr1NoDe() == ProductConstDef.BOOK_DVD_CTGR_NO_GLOBAL) ? true : false;
    }

}
