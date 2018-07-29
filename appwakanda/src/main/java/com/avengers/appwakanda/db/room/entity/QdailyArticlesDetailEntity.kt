package com.avengers.appwakanda.db.room.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.avengers.zombiebase.aacbase.IBeanResponse

@Entity
class QdailyArticlesDetailEntity : IBeanResponse() {

    @PrimaryKey
    lateinit var id:String
    var body: String? = null
    @Ignore
    var js: Array<String>? = null
    @Ignore
    val css: Array<String>? = null
    @Ignore
    val image: Array<String>? = null


}