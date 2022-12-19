package com.programmer2704.movapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.programmer2704.movapp.databinding.FragmentPopularBinding
import com.programmer2704.movapp.tools.PaginationListener
import com.programmer2704.movapp.tools.Usage
import com.programmer2704.movapp.view.adapter.PopularAdapter
import com.programmer2704.movapp.view.viewmodel.PopularViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularFragment : Fragment() {

    private val popularVM: PopularViewmodel by viewModels()
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var popularAdapter: PopularAdapter
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    var page = 1

    private var _b: FragmentPopularBinding? = null
    private val b get() = _b!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _b = FragmentPopularBinding.inflate(inflater, container, false)
        val v = b.root

        gridLayoutManager = GridLayoutManager(activity, 2)
        b.recyclerView.layoutManager = gridLayoutManager
        b.recyclerView.itemAnimator = DefaultItemAnimator()

        if(Usage.isOnline(activity)) {
            popularVM.fetchMovies(page)
            b.textRetry.visibility = View.GONE
        } else {
            Toast.makeText(activity, "No internet Present, Please enable internet connection", Toast.LENGTH_SHORT).show()
            b.progressCircular.visibility = View.GONE
            b.textRetry.visibility = View.VISIBLE
        }

        b.textRetry.setOnClickListener{
            if(Usage.isOnline(activity)) {
                popularVM.fetchMovies(page)
                b.progressCircular.visibility = View.VISIBLE
                b.textRetry.visibility = View.GONE
            } else
                Toast.makeText(activity, "No internet Present, Please enable internet connection", Toast.LENGTH_SHORT).show()
        }

        popularVM.popularMoviesLiveData.observe(viewLifecycleOwner/*before: this*/,
            Observer {
            if(page == 1) {
                popularAdapter = PopularAdapter(it, activity)
                b.recyclerView.adapter = popularAdapter
            } else if(page > 1) {
                popularAdapter.addData(it)
            }
                b.progressCircular.visibility = View.GONE
                b.textRetry.visibility = View.GONE
            isLoading = false
        })

        b.recyclerView.addOnScrollListener(object : PaginationListener(gridLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                popularVM.fetchMovies(++page)
            }
        })

        return v
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}