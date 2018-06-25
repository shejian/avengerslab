package com.avengers.appgalaxy

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.avengers.zombiebase.BaseApplication

@Interceptor(priority = 80, name = "test interceptor")
class GalaxyActivityInterceptor : IInterceptor {

    var postcard: Postcard? = null
    var callback: InterceptorCallback? = null


    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        this.postcard = postcard
        this.callback = callback

        if ("/galaxy/scrollactivity" == postcard.path) {
            Log.d("shejian", "galaxy被我拦截到了")
            callback.onContinue(postcard)
            //   Toast.makeText(context, "galaxy被我拦截到了", Toast.LENGTH_SHORT).show()
        } else {
            //必须让拦截器继续，否则其他拦截器将无效
            callback.onContinue(postcard)
        }
    }

    var context: Context? = null

    override fun init(context: Context) {
        this.context = context
    }


}