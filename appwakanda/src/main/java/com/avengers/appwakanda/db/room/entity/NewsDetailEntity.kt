package com.avengers.appwakanda.db.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class NewsDetailEntity {

    @PrimaryKey
    @ColumnInfo
    lateinit var id: String
    var site_id: String? = null
    var title: String? = null
    var headpic: String? = null
    var author_id: String? = null
    var author_name: String? = null
    var brief: String? = null
    var read_num: String? = null
    var collect_num: String? = null
    var origin_url: String? = null
    var url: String? = null
    var create_time: String? = null
    var update_time: String? = null
    var pub_date: String? = null
    var md5: String? = null
    var prepic1: String? = null
    var prepic2: String? = null
    var prepic3: String? = null
    var recommend_time: String? = null
    var sort_score: String? = null


}