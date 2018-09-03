package com.elevenst.terroir.product.registration.customer.service;

import com.elevenst.terroir.product.registration.category.service.CategoryServiceImpl;
import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import com.elevenst.terroir.product.registration.common.vine.service.VineCallServiceImpl;
import com.elevenst.terroir.product.registration.customer.mapper.CustomerMapper;
import com.elevenst.terroir.product.registration.customer.vo.ItgMemVO;
import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.customer.vo.NickNameVO;
import com.elevenst.terroir.product.registration.customer.vo.PdFrExchangeVO;
import com.elevenst.terroir.product.registration.fee.service.FeeServiceImpl;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl {

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    CategoryServiceImpl categoryServiceImpl;

    @Autowired
    VineCallServiceImpl vineCallService;

    @Autowired
    FeeServiceImpl feeServiceImpl;

    /**
     * 셀러의 해외 통합아이디 존재여부 조회
     *
     * @param memNo
     * @return
     * @throws Exception
     */
    public long getGlbItgIdsBySeller(long memNo) throws ProductException {
        long cnt = 0;
        try {
            cnt = customerMapper.getGlbItgIdsBySeller(memNo);
        } catch (Exception ex) {
            throw new ProductException("셀러의 해외 통합아이디 존재여부 조회 오류", ex);
        }
        return cnt;
    }

    public boolean isGlbItgIdsBySeller(long userNo) throws ProductException {
        if( userNo <= 0 ) {
            return false;
        }
        try {
            if(customerMapper.getGlbItgIdsBySeller(userNo) > 0){
                return true;
            }
        } catch(Exception e) {
            throw new ProductException("해외 셀러의 해외 통합아이디 여부 재사용하기 위해 체크 후 Return 오류", e);
        }

        return false;
    }

    /**
     * 상품번호로 셀러의 해외 통합아이디 존재여부 조회
     *
     * @param prdNo
     * @return
     * @throws Exception
     */
    public long getGlbItgIdsByPrdNo(long prdNo) throws ProductException {
        long cnt = 0;
        try {
            cnt = customerMapper.getGlbItgIdsByPrdNo(prdNo);
        } catch (Exception ex) {
            throw new ProductException("상품에 해당하는 셀러의 해외 통합아이디 존재여부 조회 오류", ex);
        }
        return cnt;
    }

    /**
     * 해외셀러인지 여부
     * @param prdNo
     * @return
     * @throws ProductException
     */
    public boolean isFrSellerByPrdNo(long prdNo) throws ProductException{
        try {
            boolean isFrSeller = false;
            long retCnt = customerMapper.isFrSellerByPrdNo(prdNo);
            if(retCnt > 0) isFrSeller = true;
            return isFrSeller;
        } catch (ProductException ex) {
            throw new ProductException(ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ProductException("해외 셀러여부  가져오는중 에러가 발생했습니다.", ex);
        }
    }

    /**
     * 회원 정보 조회
     *
     * @param memberVO
     * @return 회원 정보
     * @exception SQLException, ProductException
     */
    public MemberVO getMemberInfo(MemberVO memberVO) throws ProductException {

        ProductVO productVO = new ProductVO();
        productVO.setMemberVO(memberVO);

        this.getMemberInfo(productVO);

        return productVO.getMemberVO();
    }

    public List<MemberVO> getSellersByItgID(long userNo) {
        List<MemberVO> list = new ArrayList<MemberVO>();
        ItgMemVO itgMemVO = new ItgMemVO() ;
        itgMemVO.setItgMemNO(userNo);
        list = customerMapper.getSellersByItgID(itgMemVO);
        return list;
    }

    public void getMemberInfo(ProductVO productVO) throws ProductException{
        vineCallService.getMemberDefaultInfo(productVO.getMemberVO());
        vineCallService.getSellerDefaultInfo(productVO.getMemberVO());
        if(productVO.getMemberVO().isPrivateSeller()){
            vineCallService.getMemberPrivacyInfo(productVO.getMemberVO());
        }else if(productVO.getMemberVO().isEtprsSeller()){
            vineCallService.getSellerBusinessInfo(productVO.getMemberVO());
        }
        vineCallService.getSellerNickNameSeq(productVO);
    }


    @Deprecated
    public void getMemberInfo(ProductVO productVO, boolean useDB) throws ProductException{
        MemberVO memberVO = new MemberVO();
//        memberVO.setPrivateMemberVO(new PrivateMemberVO());
        memberVO.setMemNO(productVO.getCreateNo());

        try {
            memberVO = this.getMemberInfo(memberVO);
            if(memberVO == null) {
                memberVO = new MemberVO();
//                memberVO.setPrivateMemberVO(new PrivateMemberVO());
                memberVO.setMemNO(productVO.getCreateNo());
            }
            memberVO.setPrivateFrgnClf(customerMapper.getMemberPrivateVO(memberVO.getMemNO()).getFrgnclf());
            memberVO = vineCallService.getSellerBusinessInfo(memberVO);
            productVO.setMemberVO(memberVO);
        }catch (Exception e){
            throw new ProductException("회원정보 조회 오류");
        }
    }

    /**
     * 대표업체번호 조회
     *
     * @param memNo
     * @return
     * @throws Exception
     */
    public long getRepSellerNo(long memNo) throws ProductException {
        long repSellerNo = 0;
        try {
            MemberVO memberVO = customerMapper.getRepSellerNo(memNo);
            if(memberVO != null) repSellerNo = memberVO.getRepSellerNo();
        } catch (ProductException ex) {
            throw ex;
        }
        return repSellerNo;
    }

    public NickNameVO getBaseNckNM(long memNO) throws ProductException {
        NickNameVO nickNameVO = new NickNameVO();
        try {
            nickNameVO = customerMapper.getBaseNckNM(memNO);
        } catch (ProductException ex) {
            throw ex;
        }
        return nickNameVO;
    }

    public List<NickNameVO> getNckNMListOrg(long memNO) throws ProductException {
        List<NickNameVO> nckNMList = null;
        try {
            nckNMList = customerMapper.getNckNMListOrg(memNO);
        } catch (ProductException ex) {
            throw ex;
        }
        return nckNMList;
    }

    /**
     * 공급업체에 대한 상품 정보 조회
     */
    public HashMap<String, Object> getProductRegInfobySuplMemNo(long memNo, long repSellerNo) throws ProductException {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        try {
            rtnMap.put("nckNmList", this.getNckNMListOrg(repSellerNo));
            rtnMap.put("basicMrgnRt", feeServiceImpl.getBasicMarginRate(memNo));

            CategoryVO categoryVO = new CategoryVO();
            categoryVO.setSelMnbdNo(memNo);
            rtnMap.put("recentCtgrList", categoryServiceImpl.getRecentProductGeneralDisplay(categoryVO));
        } catch (Exception e) {
            throw new ProductException("공급업체에 대한 상품 정보 조회 오류", e);
        }

        return rtnMap;
    }

    /**
     * 해외쇼핑 셀러인지 여부
     * @param userNo
     * @return
     * @throws Exception
     */
    public boolean isFrSeller(long userNo) throws ProductException {
        boolean isValid = false;
        try {
            long retMemNo = customerMapper.isFrSeller(userNo);
            if(retMemNo > 0) isValid = true;
        } catch (Exception e) {
            return false;
        }
        return isValid;
    }

    /**
     * B2B 셀러여부
     *
     * @param memNO
     * @return
     * @throws Exception
     */
    public boolean isB2bSeller(long memNO) throws ProductException {
        boolean result = false;
        try {
            result = true;
        } catch (Exception ex) {
            throw ex;
        }
        return result;
    }

    /**
     * 셀러에 해당하는 환율정보 가져오기
     * @param memNo
     * @return
     * @throws ProductException
     */
    public PdFrExchangeVO getExAmt(long memNo) throws ProductException{
        try {
            return customerMapper.getExAmt(memNo);
        } catch (Exception ex) {
            throw new ProductException("환율정보  가져오는중 에러가 발생했습니다.", ex);
        }
    }

    public boolean isRoadShoSeller(long memNo) throws ProductException {
        try {
            long cnt = customerMapper.isRoadShopSeller(memNo);
            if(cnt > 0){
                return true;
            }else{
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ProductException("로드샵 셀러 확인 오류.", ex);
        }
    }
}
