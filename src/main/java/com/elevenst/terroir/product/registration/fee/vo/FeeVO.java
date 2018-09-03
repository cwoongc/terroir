package com.elevenst.terroir.product.registration.fee.vo;

import com.elevenst.terroir.product.registration.product.vo.ListingItemVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FeeVO implements Serializable{

    private static final long serialVersionUID = 3386173389124643806L;

    private String payCoupon;				// 공동구매 쿠폰 발급번호	- 사용안함
    private String sellerItemEventYn;		// 현재 무조건 N
    private String payCommonCoupon;			// 공동구매 쿠폰 사용여부
    private String dispAmount;				// 공동구매전시수수료

    private String payCouponAmount;	        // 셀러쿠폰사용금액
    private String payPoint;				// 셀러포인트 결제 금액
    private String payCyberMoney;		    // 사이버머니 결제 금액
    private String payAmount;				// 결제금액

    private List<ListingItemVO> listingItemVOList;


}
