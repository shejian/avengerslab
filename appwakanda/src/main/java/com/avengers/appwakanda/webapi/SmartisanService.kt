package com.avengers.appwakanda.webapi

import com.avengers.appwakanda.bean.IndexReaderListBean
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import retrofit2.Call
import retrofit2.Response


open class SmartisanService {

    fun indexMainData(
            service: SmartisanApi,
            query: String,
            page: Int,
            itemsPerPage: Int,
            onSuccess: (repos: List<ContextItemEntity>) -> Unit,
            onError: (error: String) -> Unit
    ) {

        service.getSmtIndex(query, itemsPerPage, page).enqueue(object : retrofit2.Callback<IndexReaderListBean> {

            override fun onResponse(call: Call<IndexReaderListBean>?, response: Response<IndexReaderListBean>) {
                if (response.isSuccessful) {
                    val indexReaderListBean = response.body()!!.data!!.list
                    onSuccess(indexReaderListBean!!)
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            }

            override fun onFailure(call: Call<IndexReaderListBean>?, t: Throwable) {
                onError(t.message ?: "unknown error")
            }
        })


    }
}