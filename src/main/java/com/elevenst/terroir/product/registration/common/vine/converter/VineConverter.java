package com.elevenst.terroir.product.registration.common.vine.converter;

import com.elevenst.common.util.StringUtil;
import com.elevenst.exception.TerroirException;
import com.elevenst.terroir.product.registration.common.vine.dto.CashV1;
import com.elevenst.terroir.product.registration.common.vine.dto.CouponInfoV1;
import com.elevenst.terroir.product.registration.common.vine.dto.MemberDefaultV1;
import com.elevenst.terroir.product.registration.common.vine.dto.MemberPrivacyV1;
import com.elevenst.terroir.product.registration.common.vine.dto.SellerBusinessV1;
import com.elevenst.terroir.product.registration.common.vine.dto.SellerDefaultV1;
import com.elevenst.terroir.product.registration.common.vine.dto.SellerNickNameV1;
import com.elevenst.terroir.product.registration.customer.vo.EnterpriseMemberVO;
import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.vo.SellerVO;
import lombok.extern.slf4j.Slf4j;
import skt.tmall.auth.common.security.AASecureManager;

import java.util.List;

@Slf4j
public class VineConverter {

    @Deprecated
    public static MemberVO convertMemberDefault(MemberDefaultV1 src, long memNo, boolean encryption) throws TerroirException{
        MemberVO memberVO = new MemberVO();
        return setMemberVODefault(src, memberVO, encryption);
    }

    @Deprecated
    private static MemberVO setMemberVODefault(MemberDefaultV1 src, boolean encryption) throws TerroirException{
        MemberVO memberVO = new MemberVO();
        try{
            memberVO.setMemNO(src.getMemberNo());
            if(encryption){
                memberVO.setMemID(AASecureManager.decodeAuthValue(src.getMemberId()));
            }else{
                memberVO.setMemID(src.getMemberId());
            }

            memberVO.setMemClf(src.getMemberClassification());
            memberVO.setMemTypCD(src.getMemberType());
            memberVO.setMemStatCD(src.getMemberStatus());
            memberVO.setMemStatDtlsCD(src.getMemberStatusDetail());

        }catch (Exception e){
            log.error("setMemberVODefault 에러", e);
        }

        return memberVO;
    }

    public static long convertLoyaltyCash(CashV1 src, long memNo) throws TerroirException{
        if (src == null) {
            src = new CashV1();
            src.setCash(-1);
        }
        log.debug(src.toString());
        return src.getCash();
    }

    public static MemberVO setMemberVODefault(MemberDefaultV1 src, MemberVO memberVO, boolean encryption) throws TerroirException{
        try{
            memberVO.setPrivateSeller(false);
            memberVO.setEtprsSeller(false);
            if("02".equals(src.getMemberType())){
                if("01".equals(src.getMemberClassification())){
                    memberVO.setPrivateSeller(true);
                }else if("02".equals(src.getMemberClassification())){
                    memberVO.setEtprsSeller(true);
                }
            }
            memberVO.setMemClf(src.getMemberClassification());
            memberVO.setMemTypCD(src.getMemberType());
            memberVO.setMemStatCD(src.getMemberStatus());
            memberVO.setMemStatDtlsCD(src.getMemberStatusDetail());
            log.debug(src.toString());
        }catch (Exception e){
            log.error("convertMemberDefault 에러", e);
        }
        return memberVO;
    }

    public static MemberVO convertMemberDefault(MemberDefaultV1 src, MemberVO memberVO, boolean encryption) throws TerroirException{

        return setMemberVODefault(src, memberVO, encryption);
    }

    public static MemberVO convertMemberPrivacy(MemberPrivacyV1 src, MemberVO memberVO, boolean encryption) throws TerroirException{
        try{
            memberVO.setAdltCrtfYn(src.isAdult() ? "Y" : "N");
            memberVO.setPrivateFrgnClf(src.getOverseaMemberType());
            log.debug(src.toString());
        }catch (Exception e){
            log.error("convertMemberPrivacy 에러", e);
        }
        return memberVO;
    }

    public static MemberVO convertSellerBusinessInfo(SellerBusinessV1 src, MemberVO memberVO, boolean encryption) throws TerroirException{
        try{
            memberVO.setOcbZoneAppvCd(src.getOcbZoneApprovalCode());
            memberVO.setEtprsFrgnClf(src.getOverseaMemberType());
            memberVO.setSuplCmpClfCd(src.getSupplierType());
            log.debug(src.toString());
        }catch (Exception e){
            log.error("convertSellerBusinessInfo 에러", e);
        }
        return memberVO;
    }



    public static MemberVO convertSellerDefaultInfo(SellerDefaultV1 src, MemberVO memberVO, boolean encryption) throws TerroirException{
        try{
            memberVO.setChinaApplyStatCd(src.getChinaApplyStatusCode());
            memberVO.setWmsUseClfCd(src.getWmsUseClassificationCode());
            memberVO.setRepSellerNo(StringUtil.getLong(src.getRepresentativeSellerNo()));
            log.debug(src.toString());
        }catch (Exception e){
            log.error("convertSellerDefaultInfo 에러", e);
        }

        return memberVO;
    }


    public static long convertSellerNickNameSeq(List<SellerNickNameV1> src, ProductVO productVO) throws TerroirException{
        long selMnbdNckNmSeq = -1;
        try{
            for(SellerNickNameV1 sellerNickNameV1 : src){
                if(sellerNickNameV1.isHasBaseNickname()){
                    selMnbdNckNmSeq = sellerNickNameV1.getNicknameSequence();
                }
            }
            if(productVO.getSellerVO() == null){
                productVO.setSellerVO(new SellerVO());
            }
            productVO.getSellerVO().setSelMnbdNckNmSeq(selMnbdNckNmSeq);
            log.debug(src.toString());
        }catch (Exception e){
            log.error("convertSellerNickNameSeq 에러", e);
        }

        return selMnbdNckNmSeq;
    }


    public static long convertSellerNickName(SellerNickNameV1 src, ProductVO productVO) throws TerroirException{
        long selMnbdNckNmSeq = -1;
        try{
            selMnbdNckNmSeq = src.getNicknameSequence();
            log.debug(src.toString());
        }catch (Exception e){
            log.error("convertSellerNickName 에러", e);
        }

        return selMnbdNckNmSeq;
    }

    public static CouponInfoV1 convertSellerDirectCoupon(CouponInfoV1 src, ProductVO productVO) throws TerroirException {
        try {
            if(src.getCouponBO().getCouponNo() < -1){
                throw new TerroirException("즉시할인쿠폰 적용 중 오류");
            }

            log.debug(src.toString());
        } catch (Exception e) {
            log.error("convertSellerDirectCoupon 에러", e);
            throw new TerroirException("즉시할인쿠폰 적용 중 오류");
        }

        return src;
    }

}
