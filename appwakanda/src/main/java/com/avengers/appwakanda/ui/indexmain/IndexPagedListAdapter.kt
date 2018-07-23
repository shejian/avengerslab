package com.avengers.appwakanda.ui.indexmain

import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.BR
import com.avengers.appwakanda.R
import com.avengers.zombiebase.adapter.BasePagedListAdapter
import com.avengers.zombiebase.adapter.DataBindingLiteHolder

class IndexPagedListAdapter : BasePagedListAdapter<ContextItemEntity>(
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

