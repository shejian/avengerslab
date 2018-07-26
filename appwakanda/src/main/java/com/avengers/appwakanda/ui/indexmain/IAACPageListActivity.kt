package com.avengers.appwakanda.ui.indexmain

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.avengers.zombiebase.aacbase.paging.PageListRepository
import java.lang.reflect.ParameterizedType

abstract class IAACPageListActivity<B : ViewDataBinding, V : ViewModel, P : PageListRepository<*, *>>
    : AppCompatActivity(), AACPageListHelp.IAACPageListHelp<P> {

    abstract override val layout: Int

    abstract override fun createRepository(): P

    //  abstract override fun createModelFactory(repository: P): ViewModelProvider.Factory

    private lateinit var aacHelp: AACPageListHelp<B, V, P>

    lateinit var mViewModel: V

    lateinit var mDataBinding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aacHelp = AACPageListHelp(this, this)
        mViewModel = aacHelp.mViewModel!!
        mDataBinding = aacHelp.mDataBinding!!
        genericViewModelClassToInit()
    }

    private fun genericViewModelClassToInit() {
        val modelClass: Class<V>
        val genType = javaClass.genericSuperclass
        if (genType is ParameterizedType) {
            val params = genType.actualTypeArguments
            @Suppress("UNCHECKED_CAST")
            modelClass = params[1] as Class<V>

            mViewModel = aacHelp.createViewModel(this, modelClass)
            mDataBinding = aacHelp.getDataBinding(this)

        }
    }


    override fun createModelFactory(repository: P): ViewModelProvider.Factory? {

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
