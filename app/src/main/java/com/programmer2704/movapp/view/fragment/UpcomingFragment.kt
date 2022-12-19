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
import com.programmer2704.movapp.databinding.FragmentUpcomingBinding
import com.programmer2704.movapp.tools.PaginationListener
import com.programmer2704.movapp.tools.Usage
import com.programmer2704.movapp.view.adapter.UpcomingAdapter
import com.programmer2704.movapp.view.viewmodel.UpcomingViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : Fragment() {

    private val upcomingVM: UpcomingViewmodel by viewModels()
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: UpcomingAdapter
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    var page = 1

    private var _b: FragmentUpcomingBinding? = null
    private val b get() = _b!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _b = FragmentUpcomingBinding.inflate(inflater, container, false)
        val v = b.root

        gridLayoutManager = GridLayoutManager(activity, 2)
        b.recyclerView.layoutManager = gridLayoutManager
        b.recyclerView.itemAnimator = DefaultItemAnimator()

        if(Usage.isOnline(activity)) {
            upcomingVM.fetchUpcomingMovies(page)
            b.textRetry.visibility = View.GONE
        } else {
            Toast.makeText(activity, "No internet Present, Please enable internet connection", Toast.LENGTH_SHORT).show()
            b.progressCircular.visibility = View.GONE
            b.textRetry.visibility = View.VISIBLE
        }

        b.textRetry.setOnClickListener{
            if(Usage.isOnline(activity)) {
                upcomingVM.fetchUpcomingMovies(page)
                b.progressCircular.visibility = View.VISIBLE
                b.textRetry.visibility = View.GONE
            } else
                Toast.makeText(activity, "No internet Present, Please enable internet connection", Toast.LENGTH_SHORT).show()
        }

        upcomingVM.upcomingMoviesLiveData.observe(viewLifecycleOwner/*before: this*/,
            Observer {
            if(page == 1) {
                adapter = UpcomingAdapter(it, activity)
                b.recyclerView.adapter = adapter
            } else if(page > 1) {
                adapter.addData(it)
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
                upcomingVM.fetchUpcomingMovies(++page)
            }
        })

        return v
    }
}