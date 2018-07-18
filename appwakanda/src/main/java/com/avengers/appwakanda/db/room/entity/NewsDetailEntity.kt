package com.avengers.appwakanda.db.room.entity

import android.arch.persistence.room.*
import android.databinding.BaseObservable
import com.avengers.zombiebase.accbase.IBeanResponse

@Entity
class NewsDetailEntity : BaseObservable(), IBeanResponse {

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
    var status: String? = null
    var create_time: String? = null
    var update_time: String? = null
    var pub_date: String? = null
    var md5: String? = null
    //  var _is_recommend: String? = null
    var prepic1: String? = null
    var prepic2: String? = null
    var prepic3: String? = null
    var recommend_time: String? = null
    var sort_score: String? = null
    override fun toString(): String {
        return "NewsDetailEntity(id='$id', site_id=$site_id, title=$title, headpic=$headpic, author_id=$author_id, author_name=$author_name, brief=$brief, read_num=$read_num, collect_num=$collect_num, origin_url=$origin_url, url=$url, status=$status, create_time=$create_time, update_time=$update_time, pub_date=$pub_date, md5=$md5, prepic1=$prepic1, prepic2=$prepic2, prepic3=$prepic3, recommend_time=$recommend_time, sort_score=$sort_score)"
    }

    /*  @Embedded
      var site_info: SiteInfo? = null
      // to be consistent w/ changing backend order, we need to keep a data like this

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
          //   var is_multi_weixin: String? = null
          //  var is_only_from_weixin: String? = null
          var sort: String? = null
          //  var is_recommend: String? = null
          var admin_uid: String? = null
          //  var is_grab: String? = null
          // var is_only_original: String? = null
          var rss_type: String? = null
          var online_time: String? = null
          // var is_findrecom: String? = null
          var cid: String? = null

      }
  */


}