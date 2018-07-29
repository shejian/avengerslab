package com.avengers.appwakanda.bean

import com.avengers.appwakanda.db.room.entity.QdailyArticlesDetailEntity
import com.avengers.zombiebase.aacbase.IBeanResponse

class QdailyDetailBean : IBeanResponse() {

    var meta: QMate? = null
    var response: QResponse? = null
    var errors: String? = null

}

class QMate {

    var status: Int? = null

    var msg: String? = null

}

class QResponse {

    var article: QdailyArticlesDetailEntity? = null

}


/*
class QArticle {

    var id: Int? = null

    var body: String? = null

    var js: Array<String>? = null

    val css: Array<String>? = null

    val image: Array<String>? = null

}*/
