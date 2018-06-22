package com.avengers.appwakanda.ui.indexmain.vm

import com.bty.retrofit.net.bean.JsonBeanResponse

class IndexReaderListBean : JsonBeanResponse() {


    var data: ContextData? = null

    var error: String? = null

    var code: Int = 0


    class ContextData {
        var list: List<ContextItemBean>? = null
        var count = 0
        var page_count = 0
    }


}