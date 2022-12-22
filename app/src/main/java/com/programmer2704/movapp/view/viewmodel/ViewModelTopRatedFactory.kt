package com.programmer2704.movapp.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.programmer2704.movapp.data.local.TopRatedRepository

class ViewModelTopRatedFactory(private val repository: TopRatedRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopRatedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TopRatedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}