package com.avengers.appwakanda.webapi

import com.avengers.appwakanda.bean.IndexReaderListBean
import com.avengers.appwakanda.bean.NewsDetailBean
import com.avengers.appwakanda.db.room.entity.NewsDetailEntity
import com.bty.annotation.Service
import retrofit2.Call
import retrofit2.http.*


@Service(alias = "SmartApi")
interface SmartisanApi {

    @GET("/index.php")
    fun getSmtIndex(@Query("r") r: String,
                    @Query("offset") offset: Int,
                    @Query("page_size") page_size: Int)
            : Call<IndexReaderListBean>

    @GET("/index.php")
    fun getDetailInfo(@Query("r") r: String,
                      @Query("offset") offset: Int,
                      @Query("page_size") page_size: Int)
            : Call<NewsDetailBean>

}