package com.programmer2704.movapp.tools

import com.programmer2704.movapp.data.local.MovieEntity
import com.programmer2704.movapp.model.Movie
fun Movie.toMovieEntity() : MovieEntity {
    return MovieEntity(
        id = this.id!!,
        poster_path = this.poster_path,
    )
}