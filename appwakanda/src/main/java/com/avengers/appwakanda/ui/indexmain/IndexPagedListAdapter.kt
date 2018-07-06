package com.avengers.appwakanda.ui.indexmain

import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.avengers.appwakanda.BR
import com.avengers.appwakanda.R
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.zombiebase.adapter.DataBindingLiteHolder

class IndexPagedListAdapter<E : ViewDataBinding> :
        PagedListAdapter<ContextItemEntity, DataBindingLiteHolder<E>>(POST_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int)
            : DataBindingLiteHolder<E> {
        //1.拿到itemView的viewDataBinding对象
        val viewDataBinding = DataBindingUtil.inflate<E>(LayoutInflater.from(parent.context), R.layout.indexlist_dbd_item, parent, false)
        return DataBindingLiteHolder(viewDataBinding)
    }

    override fun onBindViewHolder(
            holder: DataBindingLiteHolder<E>,
            position: Int
    ) {
        val binding = holder.binding
        //2.为viewDataBinding对象设置XML中的数据属性
        binding.setVariable(BR.contextItem, getItem(position))
        //3.据说时为了解决使用dataBinding导致RecycleView 的闪烁问题
        binding.executePendingBindings()
    }


    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ContextItemEntity>() {
            override fun areContentsTheSame(oldItem: ContextItemEntity, newItem: ContextItemEntity): Boolean {
                var asd = oldItem.id == newItem.id
                Log.d("shejian", "areContentsTheSame$asd")
                return asd
            }

            override fun areItemsTheSame(oldItem: ContextItemEntity, newItem: ContextItemEntity): Boolean {
                var asd = oldItem.getTitle() == newItem.getTitle()
                Log.d("shejian", "areItemsTheSame$asd")
                return asd
            }


        }


    }
}
