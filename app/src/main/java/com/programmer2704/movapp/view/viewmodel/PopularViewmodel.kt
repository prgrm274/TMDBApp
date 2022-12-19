package com.programmer2704.movapp.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.programmer2704.movapp.data.local.MovieEntity
import com.programmer2704.movapp.data.remote.ApiFactory
import com.programmer2704.movapp.data.repositories.MovieRemoteRepo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.*

class PopularViewmodel @ViewModelScoped constructor(): BaseViewmodel() {
    private val movieRemoteRepo : MovieRemoteRepo = MovieRemoteRepo(ApiFactory.movieApi)
    val popularMoviesLiveData = MutableLiveData<MutableList<MovieEntity>>()

    fun fetchMovies(page: Int){
        scope.async(Dispatchers.Default) {
            val popularMovies = movieRemoteRepo.getPopularMovies(page)
            popularMoviesLiveData.postValue(popularMovies!!)
        }
    }
}
