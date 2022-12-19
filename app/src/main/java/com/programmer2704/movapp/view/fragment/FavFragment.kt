package com.programmer2704.movapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programmer2704.movapp.R
import com.programmer2704.movapp.view.adapter.FavAdapter
import com.programmer2704.movapp.view.viewmodel.FavViewmodel

class FavFragment : Fragment() {
    private lateinit var favoritesViewModel: FavViewmodel
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoritesViewModel = ViewModelProviders.of(this).get(FavViewmodel::class.java)
        val root = inflater.inflate(R.layout.fragment_popular, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerView)
        val progressBar: ProgressBar = root.findViewById(R.id.progress_circular)
        progressBar.visibility= View.GONE

        gridLayoutManager = GridLayoutManager(activity, 2)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()

        val adapter = FavAdapter(activity)
        recyclerView.adapter = adapter

        favoritesViewModel.favoriteMoviesList.observe(viewLifecycleOwner/*before: this*/, Observer {
            it?.let { adapter.setMovieEntityList(it) }
        })
        return root
    }
}