package com.avengers.appwakanda.bean

import com.avengers.appwakanda.db.room.entity.NewsDetailEntity
import com.bty.retrofit.net.bean.JsonBeanResponse

class NewsDetailBean : JsonBeanResponse() {


    var data: ContextData? = null

    var error: String? = null

    var code: Int = 0


    class ContextData {
        var list: List<NewsDetailEntity>? = null
        var count = 0
        var page_count = 0
    }


}