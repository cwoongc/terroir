package com.elevenst.terroir.product.registration.delivery.service;

import com.elevenst.terroir.product.registration.delivery.entity.PdOrdQtyBasiDlvCst;
import com.elevenst.terroir.product.registration.delivery.exception.DeliveryException;
import com.elevenst.terroir.product.registration.delivery.mapper.DeliveryMapper;
import com.elevenst.terroir.product.registration.delivery.vo.PrdOrderCountBaseDeliveryVO;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 판매자 조건부 배송비
 * 주문수량 기준 배송비
 * 기타 조건부 혹은 특정컬럼 기준으로 하는 비즈니스 로직은 이 클래스에서 다룬다
 *
 * */
@Service
public class SpecificBaseDeliveryServiceImpl {


    @Autowired
    DeliveryMapper deliveryMapper;


    /**
     * 상품등록시 주문수량 기준의 배송비 등록 메소드
     *
     * */
    private void insertProductOrdDlvCst(ProductVO productVO) {

        if(productVO.getPrdOrderCountBaseDeliveryVOList() == null) {
            return;
        }

        List<PrdOrderCountBaseDeliveryVO> prdOrderCountBaseDeliveryVOList = productVO.getPrdOrderCountBaseDeliveryVOList();

        long maxOrderCount = 0;
        for(PrdOrderCountBaseDeliveryVO elem : prdOrderCountBaseDeliveryVOList) {

            PdOrdQtyBasiDlvCst pdOrdQtyBasiDlvCst = new PdOrdQtyBasiDlvCst();
            BeanUtils.copyProperties(elem, pdOrdQtyBasiDlvCst);

            pdOrdQtyBasiDlvCst.setPrdNo(productVO.getPrdNo());
            pdOrdQtyBasiDlvCst.setCreateNo(productVO.getUpdateNo());
            pdOrdQtyBasiDlvCst.setUpdateNo(productVO.getUpdateNo());

            if(maxOrderCount >= pdOrdQtyBasiDlvCst.getOrdBgnQty()) {
                throw new DeliveryException("주문수량기준배송비 수정 실패");
            }
            maxOrderCount = pdOrdQtyBasiDlvCst.getOrdEndQty();
            long dlvCstInstNo = deliveryMapper.getPrdOrdQtyBasiDlvCstPrimaryKey();
            pdOrdQtyBasiDlvCst.setDlvCstInstNo(dlvCstInstNo); //set primarykey

            deliveryMapper.insertPrdOrdQtyBasiDlvCst(pdOrdQtyBasiDlvCst);
            deliveryMapper.insertPrdOrdQtyBasiDlvCstHist(dlvCstInstNo);

        }

    }

    /**
     * 상품수정시 주문수량 기준의 배송비 등록 메소드
     * */
    private void updateProductOrdDlvCst(ProductVO productVO) {

        if(productVO.getPrdOrderCountBaseDeliveryVOList() == null) {
            return;
//            throw new DeliveryException("주문수량기준 배송비 데이터 미존재.");
        }

        /*
         * 주문 수량 기준 배송비 입력
         * 1. 기존의 주문수량기준배송비 정보를 가져온다.
         * 2. 히스토리를 업데이트 한다.
         * 3. Insert 한다
         * 4. 기존의 수량기준 배송비 정보를 삭제한다.
         * 5. 배송비 정보를 입력한다.
         */
        // 주문수량기준배송비 삭제
        deliveryMapper.deletePrdOrdQtyBasiDlvCst(productVO.getPrdNo());
        //주문수량기준 배송비 최종 이력 종료일자 수정
        deliveryMapper.updateOrdQtyBasiDlvCstPrdNoHist(productVO.getPrdNo());

        insertProductOrdDlvCst(productVO);


    }


    public void insertSpecificInfoProcess(ProductVO productVO) throws ProductException{
        this.insertProductOrdDlvCst(productVO);
    }


    public void updateSpecificInfoProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        this.updateProductOrdDlvCst(productVO);
    }


}
