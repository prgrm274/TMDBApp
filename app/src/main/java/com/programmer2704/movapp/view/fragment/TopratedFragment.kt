package com.programmer2704.movapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.programmer2704.movapp.databinding.FragmentUpcomingBinding
import com.programmer2704.movapp.tools.PaginationListener
import com.programmer2704.movapp.tools.Usage
import com.programmer2704.movapp.view.adapter.TopratedListAdapter
import com.programmer2704.movapp.view.adapter.TopratedLoadStateAdapter
import com.programmer2704.movapp.view.adapter.TopratedPagingDataAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopratedFragment : Fragment() {

    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: TopratedListAdapter
    private lateinit var pagingDataAdapter: TopratedPagingDataAdapter
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

        setRVGrid()
        setRVPagingDataAdapter()

        if(Usage.isOnline(activity)) {
            b.textRetry.visibility = View.GONE
        } else {
            Toast.makeText(activity, "No internet Present, Please enable internet connection", Toast.LENGTH_SHORT).show()
            b.progressCircular.visibility = View.GONE
            b.textRetry.visibility = View.VISIBLE
        }
        b.textRetry.setOnClickListener{
            if(Usage.isOnline(activity)) {
//                vm.fetchTopRatedMovies(page)
                b.progressCircular.visibility = View.VISIBLE
                b.textRetry.visibility = View.GONE
            } else
                Toast.makeText(activity, "No internet Present, Please enable internet connection", Toast.LENGTH_SHORT).show()
        }

        b.recyclerView.addOnScrollListener(object : PaginationListener(gridLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
//                vm.fetchTopRatedMovies(++page)
            }
        })

        return b.root
    }

    private fun setRVGrid() {
        gridLayoutManager = GridLayoutManager(activity, 2)
        b.recyclerView.layoutManager = gridLayoutManager
//        b.recyclerView.itemAnimator = DefaultItemAnimator()
        b.recyclerView.itemAnimator = null
        b.recyclerView.setHasFixedSize(true)
    }

    private fun setRVPagingDataAdapter() {
        pagingDataAdapter = TopratedPagingDataAdapter(activity)
        b.recyclerView.adapter = pagingDataAdapter.withLoadStateHeaderAndFooter(
            header = TopratedLoadStateAdapter { pagingDataAdapter.retry() },
            footer = TopratedLoadStateAdapter { pagingDataAdapter.retry() }
        )
    }
}