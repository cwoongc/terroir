package com.elevenst.terroir.product.registration.common.vine.service;

import com.elevenst.exception.TerroirException;
import com.elevenst.terroir.product.registration.common.vine.VineApiConfig;
import com.elevenst.terroir.product.registration.common.vine.converter.VineConverter;
import com.elevenst.terroir.product.registration.common.vine.dto.CashV1;
import com.elevenst.terroir.product.registration.common.vine.dto.CouponInfoV1;
import com.elevenst.terroir.product.registration.common.vine.dto.MemberDefaultV1;
import com.elevenst.terroir.product.registration.common.vine.dto.MemberPrivacyV1;
import com.elevenst.terroir.product.registration.common.vine.dto.ProductPriceV1;
import com.elevenst.terroir.product.registration.common.vine.dto.SellerBusinessV1;
import com.elevenst.terroir.product.registration.common.vine.dto.SellerDefaultV1;
import com.elevenst.terroir.product.registration.common.vine.dto.SellerNickNameV1;
import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import vine.client.Retrofit2Client;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class VineCallServiceImpl{

    @Autowired
    VineApiConfig vineApiConfig;

    private String apiServerUrl;
    private int connectTimeout = 100;
    private int readTimeout = 2000;
    private int maxIdleConnections = 30;
    private int keepAliveDuration = 60;

    private void setVineConfig(){
        Map<String, Integer> map = vineApiConfig.getVineApiClientConfig();
        connectTimeout = map.get("connectTimeout");
        readTimeout = map.get("readTimeout");
        maxIdleConnections = map.get("maxIdleConnections");
        keepAliveDuration = map.get("keepAliveDuration");
        apiServerUrl = vineApiConfig.getVineApiServerUrl();
    }

    private Retrofit2Client getClient(){
        this.setVineConfig();
        Retrofit2Client client = Retrofit2Client.getInstance(apiServerUrl, connectTimeout, readTimeout, maxIdleConnections, keepAliveDuration);
        return client;
    }

    @Deprecated
    public MemberVO getMemberDefaultInfo(long memNo){
        return this.getMemberDefaultInfo(memNo, false);
    }

    @Deprecated
    public MemberVO getMemberDefaultInfo(long memNo, boolean encryption){
        VineMemberService service = this.getClient().create(VineMemberService.class);
        try {
            Response<MemberDefaultV1> result = service.getMemberDefaultInfo(memNo, encryption).execute();
            return VineConverter.convertMemberDefault(result.body(), memNo, encryption);
        }catch (Exception e){
            return null;
        }
    }


    public MemberVO getMemberDefaultInfo(MemberVO memberVO){
        return this.getMemberDefaultInfo(memberVO, false);
    }

    public MemberVO getMemberDefaultInfo(MemberVO memberVO, boolean encryption){
        VineMemberService service = this.getClient().create(VineMemberService.class);
        try {
            Response<MemberDefaultV1> result = service.getMemberDefaultInfo(memberVO.getMemNO(), encryption).execute();
            return VineConverter.convertMemberDefault(result.body(), memberVO, encryption);
        }catch (Exception e){
            return null;
        }
    }

    public MemberVO getMemberPrivacyInfo(MemberVO memberVO){
        return this.getMemberPrivacyInfo(memberVO, false);
    }

    public MemberVO getMemberPrivacyInfo(MemberVO memberVO, boolean encryption){
        VineMemberService service = this.getClient().create(VineMemberService.class);
        try {
            Response<MemberPrivacyV1> result = service.getMemberPrivacyInfo(memberVO.getMemNO(), encryption).execute();
            return VineConverter.convertMemberPrivacy(result.body(), memberVO, encryption);
        }catch (Exception e){
            return null;
        }
    }


    public long getSellerCash(long memNo){

        VineLoyaltyService service = this.getClient().create(VineLoyaltyService.class);

        try {
            Response<CashV1> result = service.getSellerCash(memNo).execute();

            return VineConverter.convertLoyaltyCash(result.body(), memNo);
        }catch (Exception e){
            return -1;
        }
    }

    public MemberVO getSellerBusinessInfo(MemberVO memberVO){
        return this.getSellerBusinessInfo(memberVO, false);
    }

    public MemberVO getSellerBusinessInfo(MemberVO memberVO, boolean encryption){
        VineSellerService service = this.getClient().create(VineSellerService.class);

        try {
            Response<SellerBusinessV1> result = service.getSellerBusinessInfo(memberVO.getMemNO(), encryption).execute();

            return VineConverter.convertSellerBusinessInfo(result.body(), memberVO, encryption);
        }catch (Exception e){
            return memberVO;
        }
    }

    public MemberVO getSellerDefaultInfo(MemberVO memberVO){
        return this.getSellerDefaultInfo(memberVO, false);
    }

    public MemberVO getSellerDefaultInfo(MemberVO memberVO, boolean encryption){
        VineSellerService service = this.getClient().create(VineSellerService.class);

        try {
            Response<SellerDefaultV1> result = service.getSellerDefaultInfo(memberVO.getMemNO(), encryption).execute();

            return VineConverter.convertSellerDefaultInfo(result.body(), memberVO, encryption);
        }catch (Exception e){
            return memberVO;
        }
    }

    public long getSellerNickNameSeq(ProductVO productVO){
        VineSellerService service = this.getClient().create(VineSellerService.class);

        try {
            Response<List<SellerNickNameV1>> result = service.getSellerNickNameSeq(productVO.getMemberVO().getMemNO()).execute();

            return VineConverter.convertSellerNickNameSeq(result.body(), productVO);
        }catch (Exception e){
            return -1;
        }
    }

    public long getSellerNickName(ProductVO productVO){
        VineSellerService service = this.getClient().create(VineSellerService.class);

        try {
            Response<SellerNickNameV1> result = service.getSellerNickName(productVO.getSelMnbdNo(), productVO.getSellerVO().getSelMnbdNckNmSeq()).execute();

            return VineConverter.convertSellerNickName(result.body(), productVO);
        }catch (Exception e){
            return -1;
        }
    }

    public CouponInfoV1 insertSellerDirectCoupon(ProductVO productVO, ProductPriceV1 productPriceV1) throws TerroirException{
        VineCouponService service = this.getClient().create(VineCouponService.class);

        try {
            Response<CouponInfoV1> result = service.insertSellerDirectCoupon(productVO.getPrdNo(),
                                                                                 productVO.getSelMnbdNo(),
                                                                                 productVO.getPrdNm(),
                                                                                 productVO.isUpdate() ? "Y" : "N",
                                                                                 productPriceV1).execute();

            return VineConverter.convertSellerDirectCoupon(result.body(), productVO);
        }catch (Exception e){
            log.error("즉시할인쿠폰 적용 중 오류. MSG : "+e.getMessage(), e);
            throw new TerroirException("즉시할인쿠폰 적용 중 오류.");
        }
    }
}
