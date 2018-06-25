package com.avengers.appwakanda.webapi

import com.avengers.appwakanda.bean.IndexReaderListBean
import com.bty.annotation.Service
import retrofit2.Call
import retrofit2.http.*


@Service("SmartApi")
interface SmartisanApi {

    @GET("/index.php")
    fun getSmtIndex(@Query("r") r: String,
                    @Query("offset") offset: Int,
                    @Query("page_size") page_size: Int)
            : Call<IndexReaderListBean>

}