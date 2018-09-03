package com.elevenst.terroir.product.registration.common.vine.service;

import com.elevenst.terroir.product.registration.common.vine.dto.MemberDefaultV1;
import com.elevenst.terroir.product.registration.common.vine.dto.MemberPrivacyV1;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VineMemberService {


    @GET("/member/v1/members/{memberNo}/default")
    Call<MemberDefaultV1> getMemberDefaultInfo(@Path("memberNo") long memberNo
                                        , @Query("encryption") boolean encryption);

    @GET("/member/v1/members/{memberNo}/detail")
    Call<MemberDefaultV1> getMemberDetailInfo(@Path("memberNo") long memberNo
                                        , @Query("encryption") boolean encryption);

    @GET("/member/v1/members/{memberNo}/privacy")
    Call<MemberPrivacyV1> getMemberPrivacyInfo(@Path("memberNo") long memberNo
        , @Query("encryption") boolean encryption);

}
