package com.avengers.appwakanda.ui.indexmain

import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.avengers.appwakanda.BR
import com.avengers.appwakanda.R
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.zombiebase.DataBindingLiteAdapter

class IndexPagedListAdapter<E : ViewDataBinding> :
        PagedListAdapter<ContextItemEntity, DataBindingLiteAdapter.DbdLiteHolder<E>>(POST_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int)
            : DataBindingLiteAdapter.DbdLiteHolder<E> {
        //1.拿到itemView的viewDataBinding对象
        val viewDataBinding = DataBindingUtil.inflate<E>(LayoutInflater.from(parent.context), R.layout.indexlist_dbd_item, parent, false)
        return DataBindingLiteAdapter.DbdLiteHolder(viewDataBinding)
    }

    override fun onBindViewHolder(
            holder: DataBindingLiteAdapter.DbdLiteHolder<E>,
            position: Int
    ) {
        val binding = holder.binding
        //2.为viewDataBinding对象设置XML中的数据属性
        binding.setVariable(BR.contextItem, getItem(position))
        //3.据说时为了解决使用dataBinding导致RecycleView 的闪烁问题
        binding.executePendingBindings()
    }


    companion object {
        private val PAYLOAD_SCORE = Any()
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ContextItemEntity>() {
            override fun areContentsTheSame(oldItem: ContextItemEntity, newItem: ContextItemEntity): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: ContextItemEntity, newItem: ContextItemEntity): Boolean =
                    oldItem.getTitle() == newItem.getTitle()

            /*          override fun getChangePayload(oldItem: ContextItemEntity, newItem: ContextItemEntity): Any? {
                          return if (sameExceptScore(oldItem, newItem)) {
                              PAYLOAD_SCORE
                          } else {
                              null
                          }
                      }*/
        }

        /*       private fun sameExceptScore(oldItem: ContextItemEntity, newItem: ContextItemEntity): Boolean {
                   // DON'T do this copy in a real app, it is just convenient here for the demo :)
                   // because reddit randomizes scores, we want to pass it as a payload to minimize
                   // UI updates between refreshes
                   return oldItem.copy(score = newItem.score) == newItem
               }*/
    }
}

