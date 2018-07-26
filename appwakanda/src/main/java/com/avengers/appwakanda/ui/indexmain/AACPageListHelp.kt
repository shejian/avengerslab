package com.avengers.appwakanda.ui.indexmain

import android.arch.lifecycle.*
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v4.app.FragmentActivity
import com.avengers.zombiebase.aacbase.paging.PageListRepository
import java.lang.reflect.ParameterizedType


class AACPageListHelp<B : ViewDataBinding, V : ViewModel, P : PageListRepository<*, *>>(
        var activity: FragmentActivity,
        var iAAC: IAACPageListHelp<P>) : LifecycleObserver {


    interface IAACPageListHelp<P> {

        val layout: Int

        fun createRepository(): P

        fun createModelFactory(repository: P): ViewModelProvider.Factory?

    }

    var mViewModel: V? = null

    var mDataBinding: B? = null


    fun getDataBinding(activity: FragmentActivity): B {
        return DataBindingUtil.setContentView(activity, iAAC.layout)
    }

    fun createViewModel(activity: FragmentActivity, modelClass: Class<V>): V {
        return ViewModelProviders
                .of(activity, iAAC.createModelFactory(iAAC.createRepository()))
                .get(modelClass)
    }


    fun init(activity: FragmentActivity, modelClass: Class<V>) {
        mViewModel = createViewModel(activity, modelClass)
        mDataBinding = getDataBinding(activity)
    }


    private fun createModelFactoryz(repository: P): ViewModelProvider.Factory? {
        var viewModelClass: Class<V>
        var repositoryClass: Class<P>
        val genType = javaClass.genericSuperclass
        if (genType is ParameterizedType) {
            val params = genType.actualTypeArguments
            @Suppress("UNCHECKED_CAST")
            viewModelClass = params[1] as Class<V>
            @Suppress("UNCHECKED_CAST")
            repositoryClass = params[2] as Class<P>

            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {

                    if (modelClass.isAssignableFrom(viewModelClass)) {
                        val constructor = viewModelClass.constructors
                        @Suppress("UNCHECKED_CAST")
                        return constructor[0].newInstance(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        } else {
            return null
        }
    }

}