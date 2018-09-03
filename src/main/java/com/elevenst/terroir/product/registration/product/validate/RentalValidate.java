package com.elevenst.terroir.product.registration.product.validate;


import com.elevenst.common.properties.PropMgr;
import com.elevenst.exception.ExceptionEnumTypes;
import com.elevenst.terroir.product.registration.category.mapper.CategoryMapper;
import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import com.elevenst.terroir.product.registration.common.vine.service.VineCallServiceImpl;
import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.option.code.OptTypCdTypes;
import com.elevenst.terroir.product.registration.product.code.CreateCdTypes;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.service.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RentalValidate {

    public enum ProductTypeExceptionEnumTypes implements ExceptionEnumTypes {

        E001("PRODUCT_TYPE","PT001","렌탈상품 등록을 위해서는 셀러캐시가 30만원 이상 충전되어 있어야 합니다."),
        E002("PRODUCT_TYPE","PT002","렌탈옵션 상품은 API 등록을 지원하지 않습니다."),
        E003("PRODUCT_TYPE","PT003","렌탈옵션 서비스를 이용 할 수 없는 판매자 입니다."),
        E004("PRODUCT_TYPE","PT004","렌탈샵 셀러분들은 렌탈전용 옵션을 사용해 상품을 등록해주세요!"),
        E005("PRODUCT_TYPE","PT005","렌탈옵션의 옵션정보가 없습니다")
        ;

        private final String msgGroupId;
        private final String code;
        private final String message;


        ProductTypeExceptionEnumTypes(String msgGroupId, String code, String message){
            this.msgGroupId = msgGroupId;
            this.code = code;
            this.message = message;
        }


        @Override
        public String getMsgGroupId() {
            return msgGroupId;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    SellerServiceImpl sellerService;

    @Autowired
    PropMgr progMgr;

    @Autowired
    VineCallServiceImpl vineCallService;




    /**
     * 렌탈상품의 경우 특정금액(ProductValidate.RENTAL_SELLER_CASH_MIN 30만원) 이상 보유 체크
     *
     */
    public void checkRentalSellerCash(MemberVO memberVO) throws ProductException {
        //렌탈캐쉬 예외 셀러
        if(progMgr.get1hourTimeProperty("RENTAL_SELL_STOP_EX_SELLER").indexOf(("|" + memberVO.getMemID() + "|")) > -1){
            return;
        }

        if(ProductConstDef.RENTAL_SELLER_CASH_MIN > vineCallService.getSellerCash(memberVO.getMemNO())){
            throw new ProductException(ProductTypeExceptionEnumTypes.E001);
        }
    }

    public static void checkRentalOption(ProductVO productVO, boolean isSpecialRentalSeller) throws ProductException{

        if(OptTypCdTypes.RENTAL.getDtlsCd().equals(productVO.getOptionVO().getOptTypCd())){

            if(isSpecialRentalSeller){
                if(CreateCdTypes.SELLER_API.equals(productVO.getBaseVO().getCreateCd())){
                    throw new ProductException(ProductTypeExceptionEnumTypes.E002);
                }
            }else{
                throw new ProductException(ProductTypeExceptionEnumTypes.E003);
            }
        }else{
            if(isSpecialRentalSeller){
                throw new ProductException(ProductTypeExceptionEnumTypes.E004);
            }
        }
    }
}
