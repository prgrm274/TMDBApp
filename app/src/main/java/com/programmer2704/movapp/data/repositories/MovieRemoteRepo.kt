package com.programmer2704.movapp.data.repositories

import com.programmer2704.movapp.data.local.*
import com.programmer2704.movapp.model.Movie
import com.programmer2704.movapp.data.remote.MovieApi
import com.programmer2704.movapp.model.Revieww
import com.programmer2704.movapp.model.VideoData
import javax.inject.Inject
import javax.inject.Named

class MovieRemoteRepo @Inject constructor(
    private val api: MovieApi
) : BaseRepo() {

    private var popularEntityResponse: MovieEntityResponse? = null
    private var upcomingEntityResponse: MovieEntityResponse? = null
    private var topRatedResponse: TopratedEntityResponse? = null
    private var genreEntityResponse: GenreEntityResponse? = null
    private var movieResponse: Movie? = null

    private var videoResponse: VideoResponse? = null

    private var reviewResponse: Revieww? = null

    suspend fun getPopularMovies(page: Int): MutableList<MovieEntity>? {
        popularEntityResponse = safeApiCall(
            call = { api.getPopularMovieAsync(page).await() },
            errorMessage = "Error Fetching Popular Movies"
        )
        return popularEntityResponse?.results?.toMutableList();
    }

    suspend fun getUpcomingMovies(page: Int): MutableList<MovieEntity>? {
        upcomingEntityResponse = safeApiCall(
            call = { api.getUpcomingMovieAsync(page).await() },
            errorMessage = "Error Fetching Upcoming Movies"
        )
        return upcomingEntityResponse?.results?.toMutableList();
    }

    suspend fun getTopRatedMovies(page: Int): MutableList<TopratedEntity>? {
        topRatedResponse = safeApiCall(
            call = { api.getTopRatedMovieAsync(page).await() },
            errorMessage = "Error Fetching Top Rated Movies"
        )
        return topRatedResponse?.results?.toMutableList();
    }

    suspend fun getGenres(language: String): MutableList<GenreEntity>? {
        genreEntityResponse = safeApiCall(
            call = { api.getGenreAsync(language).await() },
            errorMessage = "Error Fetching Genres"
        )
        return genreEntityResponse?.results?.toMutableList();
    }

    suspend fun getMovieDetails(id: Int): Movie? {
        movieResponse = safeApiCall(
            call = { api.getMovieDetailsAsync(id).await() },
            errorMessage = "Error Fetching Movie Details"
        )
        return movieResponse;
    }

    suspend fun getVideos(id: Int): MutableList<VideoData>? {
        videoResponse = safeApiCall(
            call = { api.getVideosAsync(id).await() },
            errorMessage = "Error Fetching Videos"
        )
        return videoResponse?.results?.toMutableList();
    }

    suspend fun getReviews(id: Int): MutableList<Revieww.Result>? {
        reviewResponse = safeApiCall(
            call = { api.getReviewsAsync(id).await() },
            errorMessage = "Error Fetching Videos"
        )
        return reviewResponse?.results?.toMutableList();
    }
}