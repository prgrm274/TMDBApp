package com.programmer2704.movapp.view.viewmodel

import androidx.lifecycle.MutableLiveData
import com.programmer2704.movapp.data.local.Video
import com.programmer2704.movapp.data.local.VideoResponse
import com.programmer2704.movapp.model.Movie
import com.programmer2704.movapp.data.remote.ApiFactory
import com.programmer2704.movapp.data.repositories.MovieRemoteRepo
import com.programmer2704.movapp.model.Revieww
import com.programmer2704.movapp.model.VideoData
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel

class DescriptionViewmodel @ViewModelScoped constructor()
    : BaseViewmodel() {

    private val repo : MovieRemoteRepo = MovieRemoteRepo(ApiFactory.movieApi)
    val movieDetails = MutableLiveData<Movie>()

    val videos = MutableLiveData<MutableList<VideoData>>()

    val reviews = MutableLiveData<MutableList<Revieww.Result>>()

    fun fetchDetails(id: Int){
        scope.async {
            val details = repo.getMovieDetails(id)
            movieDetails.postValue(details!!)
        }
    }
    fun fetchVideos(id: Int){
        scope.async {
            val details = repo.getVideos(id)
            videos.postValue(details!!)
        }
    }
    fun fetchReviews(id: Int){
        scope.async {
            val details = repo.getReviews(id)
            reviews.postValue(details!!)
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}