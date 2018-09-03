package com.elevenst.terroir.product.registration.common.vine.service;

import com.elevenst.terroir.product.registration.common.vine.dto.SellerBusinessV1;
import com.elevenst.terroir.product.registration.common.vine.dto.SellerDefaultV1;
import com.elevenst.terroir.product.registration.common.vine.dto.SellerNickNameV1;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface VineSellerService {


    @GET("/seller/v1/sellers/{sellerNo}/default")
    Call<SellerDefaultV1> getSellerDefaultInfo(@Path("sellerNo") long memberNo, @Query("encryption") boolean encryption);

    @GET("/seller/v1/sellers/{sellerNo}/business")
    Call<SellerBusinessV1> getSellerBusinessInfo(@Path("sellerNo") long memberNo, @Query("encryption") boolean encryption);

    @GET("/seller/v1/sellers/{sellerNo}/nicknames/{nicknameSequence}")
    Call<SellerNickNameV1> getSellerNickName(@Path("sellerNo") long memberNo, @Path("nicknameSequence") long nicknameSequence);

    @GET("/seller/v1/sellers/{sellerNo}/nicknames")
    Call<List<SellerNickNameV1>> getSellerNickNameSeq(@Path("sellerNo") long memberNo);
}
