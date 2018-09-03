package com.elevenst.terroir.product.registration.common.vine.service;

import com.elevenst.terroir.product.registration.common.vine.dto.CouponInfoV1;
import com.elevenst.terroir.product.registration.common.vine.dto.ProductPriceV1;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VineCouponService {

    @POST("/coupon/v1/sodirectcoupons/create")
    Call<CouponInfoV1> insertSellerDirectCoupon(@Query("prdNo") long prdNo,
                                                @Query("memNo") long memNo,
                                                @Query("prdNm") String prdNm,
                                                @Query("updateYn") String updateYn,
                                                @Body ProductPriceV1 productPriceV1);
}
