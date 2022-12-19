package com.programmer2704.movapp.data.repositories

import androidx.lifecycle.LiveData
import com.programmer2704.movapp.data.local.MovieDao
import com.programmer2704.movapp.data.local.MovieEntity
import javax.inject.Inject

class MovieDBRepo @Inject constructor(private val movieDao: MovieDao) {

    val favoriteMovies: LiveData<List<MovieEntity>> = movieDao.getFavoriteMovies()

    suspend fun insert(movie: MovieEntity) {
        movieDao.insert(movie)
    }
    suspend fun delete(movie: MovieEntity) {
        movieDao.delete(movie)
    }
}