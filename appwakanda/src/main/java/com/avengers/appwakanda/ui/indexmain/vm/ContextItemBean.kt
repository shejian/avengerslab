package com.avengers.appwakanda.ui.indexmain.vm

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.avengers.appwakanda.BR
import java.util.*

class ContextItemBean : BaseObservable() {

    private var id: String? = null
    private var site_id: String? = null
    private var title: String? = null
    private var headpic: String? = null
    private var author_id: String? = null
    private var author_name: String? = null
    private var brief: String? = null
    private var read_num: String? = null
    private var collect_num: String? = null
    private var origin_url: String? = null
    private var url: String? = null
    private var status: String? = null
    private var create_time: String? = null
    private var update_time: String? = null
    private var pub_date: String? = null
    private var md5: String? = null
    private var is_recommend: String? = null
    private var prepic1: String? = null
    private var prepic2: String? = null
    private var prepic3: String? = null
    private var recommend_time: String? = null
    private var sort_score: String? = null
    private var site_info: SiteInfo? = null


    @Bindable
    fun getTitle(): String? {
        return title
    }

    fun setTitle(mtitle: String) {
        this.title = mtitle
        notifyPropertyChanged(BR.title)
    }


    class SiteInfo : BaseObservable() {

        var id: String? = null
        var name: String? = null
        var keyword: String? = null
        var url: String? = null
        var pic: String? = null
        var brief: String? = null
        var article_num: String? = null
        var order_num: String? = null
        var source: String? = null
        var status: String? = null
        var create_time: String? = null
        var update_time: String? = null
        var rss_url: String? = null
        var pk: String? = null
        var wx_biz: String? = null
        var rss_update_time: String? = null
        var last_rss_time: String? = null
        var last_pub_time: String? = null
        var is_multi_weixin: String? = null
        var is_only_from_weixin: String? = null
        var sort: String? = null
        var is_recommend: String? = null
        var admin_uid: String? = null
        var is_grab: String? = null
        var is_only_original: String? = null
        var rss_type: String? = null
        var online_time: String? = null
        var is_findrecom: String? = null
        var cid: Date? = null

    }


}