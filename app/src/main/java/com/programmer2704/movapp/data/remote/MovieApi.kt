package com.programmer2704.movapp.data.remote

import com.programmer2704.movapp.data.local.GenreEntityResponse
import com.programmer2704.movapp.data.local.MovieEntityResponse
import com.programmer2704.movapp.data.local.TopratedEntityResponse
import com.programmer2704.movapp.data.local.VideoResponse
import com.programmer2704.movapp.model.Movie
import com.programmer2704.movapp.model.Revieww
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("genre/movie/list")
    fun getGenreAsync(@Query("language") language: String): Deferred<Response<GenreEntityResponse>>

    @GET("movie/popular")
    fun getPopularMovieAsync(@Query("page") page: Int): Deferred<Response<MovieEntityResponse>>
    @GET("movie/upcoming")
    fun getUpcomingMovieAsync(@Query("page") page: Int): Deferred<Response<MovieEntityResponse>>
    @GET("movie/top_rated")
    fun getTopRatedMovieAsync(@Query("page") page: Int): Deferred<Response<TopratedEntityResponse>>

    @GET("movie/{movie_id}")
    fun getMovieDetailsAsync(@Path("movie_id") id: Int): Deferred<Response<Movie>>

    @GET("movie/{movie_id}/videos")
    fun getVideosAsync(@Path("movie_id") id: Int): Deferred<Response<VideoResponse>>

    @GET("movie/{movie_id}/reviews")
    fun getReviewsAsync(@Path("movie_id") id: Int): Deferred<Response<Revieww>>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovieTry2(@Query("page") page: Int,
    ): TopratedEntityResponse

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String,
                          @Query("language") language: String,
                          @Query("page") pageNumber: Int,
                          @Query("region") region:String,
                          @Query("with_release_type") releaseType: String): Call<MovieRequest>
}