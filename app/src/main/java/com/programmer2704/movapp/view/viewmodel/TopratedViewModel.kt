package com.programmer2704.movapp.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.programmer2704.movapp.data.local.TopRatedEntry
import com.programmer2704.movapp.data.local.TopRatedRepository
import com.programmer2704.movapp.data.local.TopRatedResults

class TopRatedViewModel(private val repository: TopRatedRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    private val nowShowingResult: LiveData<TopRatedResults> = Transformations.map(queryLiveData) {
        repository.topRated(it)
    }

    val topRated: LiveData<PagedList<TopRatedEntry>> = Transformations.switchMap(
        nowShowingResult
    ) { it -> it.data }

    val networkErrors: LiveData<String> = Transformations.switchMap(
        nowShowingResult
    ) { it -> it.networkErrors }

    fun getTopRated(region: String) {
        queryLiveData.value = region
    }
}