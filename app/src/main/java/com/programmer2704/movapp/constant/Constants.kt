package com.programmer2704.movapp.constant

import com.programmer2704.movapp.BuildConfig

object Constants {
    const val MOVIE_PHOTO_URL: String = "https://image.tmdb.org/t/p/w300"
    const val MOVIEDB_BASE_URL: String = "https://api.themoviedb.org/3/"
    const val API_KEY: String = BuildConfig.tmdbkey
    const val YOUTUBE_API_KEY: String = BuildConfig.youtubekey

    val TOP_RATED: Int = 1232
    val CONTENT_SIMILAR = 3
}