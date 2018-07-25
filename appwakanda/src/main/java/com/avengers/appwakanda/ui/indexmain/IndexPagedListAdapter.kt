package com.avengers.appwakanda.ui.indexmain

import android.content.Intent
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.view.View
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.BR
import com.avengers.appwakanda.R
import com.avengers.appwakanda.ui.detail.NewsDetailActivity
import com.avengers.zombiebase.adapter.DataBindingPagedListAdapter
import com.avengers.zombiebase.adapter.DataBindingLiteHolder

class IndexPagedListAdapter : DataBindingPagedListAdapter<ContextItemEntity>(
        R.layout.indexlist_dbd_item,
        BR.contextItem,
        POST_COMPARATOR,
        onBindView) {

    override fun onBindView(holder: DataBindingLiteHolder<ViewDataBinding>, position: Int) {
        //LogU.d("子类实现 onBindView")
    }

    companion object {

        val onBindView = { view: ViewDataBinding, sd: Int ->
           // LogU.d("闭包 onBindView")
        }

        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ContextItemEntity>() {
            override fun areContentsTheSame(oldItem: ContextItemEntity, newItem: ContextItemEntity): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: ContextItemEntity, newItem: ContextItemEntity): Boolean {
                return oldItem.title == newItem.title
            }

        }
    }

}

