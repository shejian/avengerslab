package com.avengers.appwakanda.ui.indexmain

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import com.avengers.appwakanda.databinding.ActivityWakandaMainBinding
import com.avengers.appwakanda.ui.indexmain.vm.IndexListViewModel

import com.avengers.zombiebase.aacbase.paging.PageListRepository

abstract class IBindingActivityJava<B : ViewDataBinding, V : ViewModel, P : PageListRepository<*, *>>
    : AppCompatActivity() {


    var mViewModel: V? = null

    var mDataBinding: B? = null

    abstract val layout: Int

    abstract fun createRepository(): P
    /*
    {
        return new IndexRepository2(
                Api.getSmartApi(),
                new IndexDataCache(RoomHelper.indexDataDao(), WakandaModule.appExecutors.diskIO()),
                WakandaModule.appExecutors);
    }
*/

    fun getDataBinding(activity: FragmentActivity): B {
        return DataBindingUtil.setContentView(activity, layout)
    }

    fun createViewModel(activity: FragmentActivity, modelClass: Class<V>): V {
        return ViewModelProviders
                .of(activity, createModelFactory(createRepository()))
                .get(modelClass)
    }

    abstract fun createModelFactory(repository: P): ViewModelProvider.Factory
    //  new IndexViewModelFactory(repository);


    fun init(activity: FragmentActivity, modelClass: Class<V>) {
        mViewModel = createViewModel(activity, modelClass)
        mDataBinding = getDataBinding(activity)
    }


}
