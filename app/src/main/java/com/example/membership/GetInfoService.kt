package com.example.membership

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GetInfoService {

    @FormUrlEncoded
    @POST("/app_get_info/")
    fun getInfo(
        @Field("name") textName:String
    ) : Call<Info>
}