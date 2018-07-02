package com.avengers.appgalaxy.db.room.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.databinding.BaseObservable

@Entity
data class ContextItemEntity(@PrimaryKey(autoGenerate = true) val id: Long, val title: String)


