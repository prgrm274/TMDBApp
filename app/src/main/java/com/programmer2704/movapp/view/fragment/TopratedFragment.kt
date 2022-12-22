package com.programmer2704.movapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.programmer2704.movapp.data.local.Toprateddb
import com.programmer2704.movapp.data.remote.NetworkService
import com.programmer2704.movapp.databinding.FragmentTopratedBinding
import com.programmer2704.movapp.databinding.FragmentUpcomingBinding
import com.programmer2704.movapp.model.Movie
import com.programmer2704.movapp.tools.PaginationListener
import com.programmer2704.movapp.tools.Usage
import com.programmer2704.movapp.view.adapter.TopRatedAdapter
import com.programmer2704.movapp.view.adapter.TopratedListAdapter
import com.programmer2704.movapp.view.adapter.TopratedLoadStateAdapter
import com.programmer2704.movapp.view.adapter.TopratedPagingDataAdapter
import com.programmer2704.movapp.view.viewmodel.Injeksi
import com.programmer2704.movapp.view.viewmodel.TopRatedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class TopratedFragment : Fragment() {
    private var _b: FragmentTopratedBinding? = null
    private val b get() = _b!!

    private lateinit var gridLayoutManager: GridLayoutManager
    private  lateinit var vm: TopRatedViewModel
    private lateinit var networkService: NetworkService
    private lateinit var mDatabase: Toprateddb
    lateinit var mMovieAdapter: TopRatedAdapter
    lateinit var data:MutableList<Movie>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _b = FragmentTopratedBinding.inflate(inflater, container, false)

        data = mutableListOf()
        networkService = NetworkService.instance
        mDatabase = Toprateddb.getInstance(requireContext())

        setRVGrid()
        setListAdapter()
        getTopRatedData()
        setSwipeRefreshLayoutListener()

        return b.root
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            b.textRetry.visibility = View.VISIBLE
            b.recyclerView.visibility = View.GONE
        } else {
            b.textRetry.visibility = View.GONE
            b.recyclerView.visibility = View.VISIBLE
        }
    }

    private fun getTopRatedData(){
        vm.getTopRated("US")
        mMovieAdapter.submitList(null)
        b.mSwipeRefreshLayout.isRefreshing = false
    }

    private fun setSwipeRefreshLayoutListener() {
        b.mSwipeRefreshLayout.setOnRefreshListener {
            refreshTable()
            b.mSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun refreshTable(){
        b.mSwipeRefreshLayout.isEnabled = false
        runBlocking {
            async(Dispatchers.Default) {
                mDatabase.topRatedDao().deleteAll()
            }.await()
        }
        b.mSwipeRefreshLayout.isEnabled = true
        b.recyclerView.scrollToPosition(0)
        vm.getTopRated("US")
        mMovieAdapter.submitList(null)
    }

    private fun setRVGrid() {
        gridLayoutManager = GridLayoutManager(activity, 2)
        b.recyclerView.layoutManager = gridLayoutManager
        b.recyclerView.itemAnimator = null
        b.recyclerView.setHasFixedSize(true)
    }

    private fun setListAdapter() {
        vm = ViewModelProvider(this, Injeksi.provideTopRatedViewModelFactory(requireContext()))[TopRatedViewModel::class.java]

        mMovieAdapter = TopRatedAdapter(requireActivity())
        b.recyclerView.adapter = mMovieAdapter

        vm.topRated.observe(viewLifecycleOwner) {
            showEmptyList(it?.size == 0)
            mMovieAdapter.submitList(it!!)
        }
        vm.networkErrors.observe(viewLifecycleOwner) {
            Toast.makeText(context, "\uD83D\uDE28 Wooops ${it}", Toast.LENGTH_LONG).show()
        }
    }
}