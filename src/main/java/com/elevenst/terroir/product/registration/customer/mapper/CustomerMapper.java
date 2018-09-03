package com.elevenst.terroir.product.registration.customer.mapper;

import com.elevenst.terroir.product.registration.customer.vo.ItgMemVO;
import com.elevenst.terroir.product.registration.customer.vo.MemberSprtVO;
import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.customer.vo.NickNameVO;
import com.elevenst.terroir.product.registration.customer.vo.PdFrExchangeVO;
import com.elevenst.terroir.product.registration.customer.vo.PrivateMemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CustomerMapper {

    long getGlbItgIdsBySeller(long memNo);
    long getGlbItgIdsByPrdNo(long prdNo);
    long isFrSellerByPrdNo(long prdNo);

    long isFrSeller(long memNo);

    MemberVO getMemberInfo(MemberVO memberVO);
    MemberVO getMemberInfoSprt(MemberVO memberVO);
    MemberSprtVO getMemberSprtInfo(MemberVO memberVO);
    MemberVO getRepSellerNo(long memNo);

    List<MemberVO> getSellersByItgID(ItgMemVO itgMem);
    List<NickNameVO> getNckNMListOrg(long memNo);
    NickNameVO getBaseNckNM(long memNo);
    PdFrExchangeVO getExAmt(long memNo);
    PrivateMemberVO getMemberPrivateVO(long memNo);

    long isRoadShopSeller (long memNO);
}
