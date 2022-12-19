package com.programmer2704.movapp.view.viewmodel

import androidx.lifecycle.MutableLiveData
import com.programmer2704.movapp.data.local.MovieEntity
import com.programmer2704.movapp.data.remote.ApiFactory
import com.programmer2704.movapp.data.repositories.MovieRemoteRepo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.*

class UpcomingViewmodel @ViewModelScoped constructor() : BaseViewmodel() {

    private val movieRemoteRepo : MovieRemoteRepo = MovieRemoteRepo(ApiFactory.movieApi)
    val upcomingMoviesLiveData = MutableLiveData<MutableList<MovieEntity>>()

    fun fetchUpcomingMovies(page: Int){
        scope.async {
            val upcomingMovies = movieRemoteRepo.getUpcomingMovies(page)
            upcomingMoviesLiveData.postValue(upcomingMovies!!)
        }
    }
}