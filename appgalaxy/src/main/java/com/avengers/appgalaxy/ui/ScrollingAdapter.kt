package com.avengers.appgalaxy.ui

import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.util.Log
import com.avengers.appgalaxy.db.room.entity.ContextItemEntity
import com.avengers.zombiebase.adapter.DataBindingPagingLiteAdapter

class ScrollingAdapter<E : ViewDataBinding>(
        var layout: Int,
        var variableId: Int
) : DataBindingPagingLiteAdapter<ContextItemEntity, E>(layout, variableId, POST_COMPARATOR) {

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ContextItemEntity>() {
            override fun areContentsTheSame(oldItem: ContextItemEntity, newItem: ContextItemEntity): Boolean {
                var asd = oldItem == newItem
                Log.d("shejian", "areContentsTheSame$asd")
                return asd
            }

            override fun areItemsTheSame(oldItem: ContextItemEntity, newItem: ContextItemEntity): Boolean {
                var asd = oldItem.title == newItem.title
                Log.d("shejian", "areItemsTheSame$asd")
                return asd
            }


        }


    }
}