package com.elevenst.terroir.product.registration.common.vine.service;

import com.elevenst.terroir.product.registration.common.vine.dto.CashV1;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VineLoyaltyService {

    @GET("/loyalty/v1/cash/{memberNo}/sellercash")
    Call<CashV1> getSellerCash(@Path("memberNo") long memberNo);
}
