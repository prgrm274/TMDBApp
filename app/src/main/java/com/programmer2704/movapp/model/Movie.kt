package com.programmer2704.movapp.model

data class Movie(
    val id: Int?,
    val poster_path: String?,
    val backdrop_path: String?,
    val budget: Int?,
    val original_language: String?,
    val original_title: String?,
    val title: String?,
    val overview: String?,
    val revenue: Int?,
    val runtime: Int?,
    val release_date: String?,
    val popularity: Float?,
    var tagline: String? = null,
    var video: Boolean? = null,
    val vote_average: Float,
    val vote_count: Int
)