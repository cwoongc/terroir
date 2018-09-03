package com.elevenst.terroir.product.registration.common.process.prdtyp;

import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductRsvSchdlVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;

import java.util.List;

public class CheckOutReservationTypeServiceImpl implements ProductTypeService{
    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException {
        this.convertReservationInfo(preProductVO, productVO);
    }

    @Override
    public void setProductTypeInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException {

    }

    @Override
    public void insertProductTypeProcess(ProductVO productVO) throws ProductException {

    }

    @Override
    public void updateProductTypeProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException {

    }

    private void convertReservationInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException {

        List<ProductRsvSchdlVO> productRsvSchdlVOList = productVO.getProductRsvSchdlVOList();
        List<ProductRsvSchdlVO> productRsvLmtQtyVOList = productVO.getProductRsvLmtQtyVOList();

        if(productRsvSchdlVOList != null){
            for(ProductRsvSchdlVO productRsvSchdlVO : productRsvSchdlVOList){
                if(productRsvSchdlVO.getBgnDt().isEmpty())
                    throw new ProductException("상품예약일정정보의 시작일시를 입력하셔야 합니다.");
                if(productRsvSchdlVO.getEndDt().isEmpty())
                    throw new ProductException("상품예약일정정보의 종료일시를 입력하셔야 합니다.");
                if(StringUtil.existsCharacters(productRsvSchdlVO.getDtlDesc(), "['|\"|%|&|<|>|#|†|\\\\|∏|‡|,]"))
                    throw new ProductException("예약스케쥴 상세설명 값에 특수 문자[',\",%,&,<,>,#,†,\\,∏,‡,콤마(,)]는 입력할 수 없습니다.");
            }
        }

        if(productRsvLmtQtyVOList != null){
            for(ProductRsvSchdlVO productRsvSchdlVO : productRsvLmtQtyVOList){
                if(productRsvSchdlVO.getBgnDt().isEmpty())
                    throw new ProductException("상품예약제한수량의 시작일시를 입력하셔야 합니다.");
                if(productRsvSchdlVO.getWkdyCd().isEmpty())
                    throw new ProductException("상품예약제한수량의 요일코드를 입력하셔야 합니다.");
                if(productRsvSchdlVO.getHhmm().isEmpty())
                    throw new ProductException("상품예약제한수량의 일시를 입력하셔야 합니다.");
                if(productRsvSchdlVO.getRsvLmtQty() <= 0)
                    throw new ProductException("상품예약제한수량의 재고수량 반드시 입력하셔야 합니다.");
            }
        }

    }
}
