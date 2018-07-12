package com.avengers.appwakanda.ui.detail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avengers.appwakanda.R
import com.avengers.appwakanda.databinding.FragmentNewsDetailBinding
import com.avengers.appwakanda.ui.detail.repository.NewsDetailRepository
import com.avengers.appwakanda.ui.detail.vm.NewsDetailVM
import com.avengers.appwakanda.ui.indexmain.repository.IndexRepository
import com.avengers.appwakanda.ui.indexmain.vm.IndexListViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class NewsDetailActivityFragment : Fragment() {

    var newsDetailVM: NewsDetailVM? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var newsDetail = DataBindingUtil.inflate<FragmentNewsDetailBinding>(inflater, R.layout.fragment_news_detail, container, false)

        var repository = NewsDetailRepository()

        newsDetailVM = ViewModelProviders.of(this, object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(NewsDetailVM::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return NewsDetailVM(repository) as T
                }
                
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }).get(NewsDetailVM::class.java)


        return newsDetail.root
    }
}
