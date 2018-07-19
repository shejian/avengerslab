package com.avengers.appwakanda

import android.content.Context

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.avengers.zombiebase.RuntimePermissionUtil
import com.yanzhenjie.permission.Permission

/**
 * 通过注解就能实施Activity的拦截器,拦截器只有一个，通常可以是登陆验证，或者是权限的验证
 */
@Interceptor(priority = 90, name = "test interceptor")
class WakandaActivityInterceptor : IInterceptor {

    private var postcard: Postcard? = null
    private var callback: InterceptorCallback? = null

    var context: Context? = null

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        this.postcard = postcard
        this.callback = callback

        if ("/wakanda/mainactivity" == postcard.path) {
            RuntimePermissionUtil.requestPermission(this.context!!, {
                callback.onContinue(postcard)
            }, Permission.ACCESS_COARSE_LOCATION)
        }else{
            callback.onContinue(postcard)
        }

    }

    override fun init(context: Context) {
        this.context = context
    }
}
