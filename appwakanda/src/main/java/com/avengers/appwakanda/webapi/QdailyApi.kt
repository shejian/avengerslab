package com.avengers.appwakanda.webapi

import com.avengers.appwakanda.bean.QdailyDetailBean
import com.bty.annotation.Service
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


@Service(alias = "QdailyApi", baseUrl = "http://app3.qdaily.com/app3/")
interface QdailyApi {

    @GET("articles/detail/{url}")
    fun getQdailyDetail(@Path("url") url: String): Call<QdailyDetailBean>


}