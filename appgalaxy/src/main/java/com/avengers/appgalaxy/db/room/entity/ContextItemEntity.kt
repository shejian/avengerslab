package com.avengers.appgalaxy.db.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.support.annotation.NonNull
import java.util.*

@Entity
class ContextItemEntity : BaseObservable() {

    @PrimaryKey
    @NonNull
    lateinit var id: String
    @ColumnInfo
    var site_id: String? = null
    @ColumnInfo
    private var title: String? = null
    @ColumnInfo
    var headpic: String? = null
    @ColumnInfo
    var author_id: String? = null
    @ColumnInfo
    var author_name: String? = null
    @ColumnInfo
    var brief: String? = null
    @ColumnInfo
    var read_num: String? = null

    @ColumnInfo
    var collect_num: String? = null

    @ColumnInfo
    var origin_url: String? = null

    @ColumnInfo
    var url: String? = null

    @ColumnInfo
    var status: String? = null

    @ColumnInfo
    var create_time: String? = null

    @ColumnInfo
    var update_time: String? = null

    @ColumnInfo
    var pub_date: String? = null

    @ColumnInfo
    var md5: String? = null

    @ColumnInfo
    var _is_recommend: String? = null

    @ColumnInfo
    var prepic1: String? = null

    @ColumnInfo
    var prepic2: String? = null

    @ColumnInfo
    var prepic3: String? = null

    @ColumnInfo
    var recommend_time: String? = null

    @ColumnInfo
    var sort_score: String? = null

    //private var site_info: SiteInfo? = null


    @Bindable
    fun getTitle(): String? {
        return title
    }

    fun setTitle(mtitle: String) {
        this.title = mtitle
        //notifyPropertyChanged(BR.title)
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