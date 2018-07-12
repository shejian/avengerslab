package com.avengers.appwakanda.db.room.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
class NewsSiteInfoEntity {

    @PrimaryKey
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