package com.example.membership

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PostInfoService {
/*branch*/
    @FormUrlEncoded
    @POST("/app_post_info/")
    fun postInfo(
        @Field("name") textName:String,
        @Field("gender") textGender:String,
        @Field("height") textHeight:String,
        @Field("weight") textWeight:String,
        @Field("locationid") location:String
    ) : Call<Info>
}