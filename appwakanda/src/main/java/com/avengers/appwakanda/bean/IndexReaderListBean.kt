package com.avengers.appwakanda.bean

import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.bty.retrofit.net.bean.JsonBeanResponse

class IndexReaderListBean : JsonBeanResponse() {


    var data: ContextData? = null

    var error: String? = null

    var code: Int = 0


    class ContextData {
        var list: List<ContextItemEntity>? = null
        var count = 0
        var page_count = 0
    }


}