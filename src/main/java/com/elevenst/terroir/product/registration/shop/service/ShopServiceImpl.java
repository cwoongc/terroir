package com.elevenst.terroir.product.registration.shop.service;

import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.shop.exception.ShopServiceException;
import com.elevenst.terroir.product.registration.shop.mapper.ShopMapper;
import com.elevenst.terroir.product.registration.shop.message.ShopServiceExceptionMessage;
import com.elevenst.terroir.product.registration.shop.validate.ShopValidate;
import com.elevenst.terroir.product.registration.shop.entity.PdTownShopBranchComp;
import com.elevenst.terroir.product.registration.shop.vo.ShopBranchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 상품등록에서 활용하는 Shop, ShopBranch, ShopBranchComp 와 관련된 서비스를 제공한다
 */
@Slf4j
@Service
public class ShopServiceImpl {


    @Autowired
    ShopValidate shopValidate;

    @Autowired
    ShopMapper shopMapper;


    /**
     * 상품 지점 연결 등록.
     * 상품 - 본점 - 지점 번호 맵핑 정보를 저장한다 (PD_TOWN_SHOP_BRANCH_COMP)
     *
     * @param pdTownShopBranchComp 등록할 상품 지점 연결(PdTownShopBranchComp)
     * @return 등록된 상품 지점 연결(pdTownShopBranchComp)
     * @throws ShopServiceException
     * @author wcchoi
     */
    public int insertProductShopBranch(PdTownShopBranchComp pdTownShopBranchComp) throws ShopServiceException {

        int insertCnt = 0;

        try {

            insertCnt += shopMapper.insertProductShopBranch(pdTownShopBranchComp);

        } catch (Exception ex) {
            ShopServiceException threx = new ShopServiceException(ShopServiceExceptionMessage.PRODUCT_SHOP_BRANCH_INSERT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return insertCnt;
    }


    /**
     * 지점번호 목록 조회.
     * 상점번호로 지점번호 목록을 조회한다.
     * @param shopNo 상점번호
     * @return 지점번호 목록
     * @throws ShopServiceException
     * @author wcchoi
     */
    public List<Long> getShopBranchNoList(long shopNo) throws ShopServiceException {
        List<Long> shopBranchNoList = null;
        try {
            shopBranchNoList = shopMapper.getShopBranchNoList(shopNo);
        } catch (Exception ex) {
            ShopServiceException threx = new ShopServiceException(ShopServiceExceptionMessage.SHOP_BRANCH_NO_LIST_SELECT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
        return  shopBranchNoList;
    }


    /**
     * 지점 목록 조회.
     * 상점번호로 지점 목록을 조회한다.
     *
     * 구) ShopServiceImpl.getTownShopBranchNMList(long shopNo)
     * @param shopNo
     * @return
     * @throws ShopServiceException
     */
    public List<ShopBranchVO> getShopBranchList(long shopNo) throws ShopServiceException {
        List<ShopBranchVO> shopBranchVOList = null;
        try {
            shopBranchVOList = shopMapper.getShopBranchList(shopNo);
        } catch (Exception ex) {
            ShopServiceException threx = new ShopServiceException(ShopServiceExceptionMessage.SHOP_BRANCH_LIST_SELECT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
        return shopBranchVOList;
    }

    public int getPromotionCheck(ShopBranchVO shopBranchVO) throws ShopServiceException {
        int retCnt = 0;
        try {
            retCnt = shopMapper.getPromotionCheck(shopBranchVO);
        } catch(Exception ex) {
            throw new ShopServiceException(ex);
        }
        return retCnt;
    }

    public ArrayList<ShopBranchVO> getTownShopBranchUsingList(long shopNo) throws ShopServiceException {
        ArrayList<ShopBranchVO> townShopBranch = null;

        try {
            townShopBranch = shopMapper.getTownShopBranchUsingList(shopNo);

            if(StringUtil.isNotEmpty(townShopBranch)){
                for (ShopBranchVO townShopBranchBO : townShopBranch) {
                    townShopBranchBO.setRequiredLifeplusColumn(false);

                    if (StringUtil.isNotEmpty(townShopBranchBO.getStrIntrImgUrl()) &&
                        StringUtil.isNotEmpty(townShopBranchBO.getStrImgUrl()) &&
                        StringUtil.isNotEmpty(townShopBranchBO.getAdvrtStmtCont()) &&
                        townShopBranchBO.getSaleTimeCnt() > 0) {
                        townShopBranchBO.setRequiredLifeplusColumn(true);
                    }
                }
            }
        } catch(Exception ex) {
            throw new ShopServiceException(ex);
        }
        return townShopBranch;
    }

    public ShopBranchVO getShopNo(long shopBranchNo) throws ShopServiceException {
        try {
            return shopMapper.getShopNo(shopBranchNo);
        } catch(Exception ex) {
            throw new ShopServiceException(ex);
        }
    }

    public ArrayList<ShopBranchVO> getShopList(long selNo) throws ShopServiceException {
        ArrayList<ShopBranchVO> shopBOList = null;
        try {
            shopBOList = shopMapper.getShopList(selNo);
        } catch(Exception ex) {
            throw new ShopServiceException(ex);
        }
        return shopBOList;
    }

    /**
     * 상품 지점 정보 조회
     *
     * @param shopBranchVO
     * @exception ShopServiceException
     */
    public ArrayList<ShopBranchVO> getCheckBranchList(ShopBranchVO shopBranchVO) throws ShopServiceException {
        ArrayList<ShopBranchVO> list = null;
        try {
            list = shopMapper.getCheckBranchList(shopBranchVO);
        } catch (Exception ex) {
            throw new ShopServiceException("상품 SEQ 조회 오류", ex);
        }
        return list;
    }
}
