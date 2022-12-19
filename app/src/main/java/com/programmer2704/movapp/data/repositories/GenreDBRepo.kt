package com.programmer2704.movapp.data.repositories

import androidx.lifecycle.LiveData
import com.programmer2704.movapp.data.local.GenreDao
import com.programmer2704.movapp.data.local.GenreEntity
import com.programmer2704.movapp.data.local.MovieDao
import com.programmer2704.movapp.data.local.MovieEntity
import javax.inject.Inject

class GenreDBRepo @Inject constructor(
    private val genreDao: GenreDao
) {
    val genres: LiveData<List<GenreEntity>> = genreDao.getGenresMovies()

    suspend fun insert(genre: GenreEntity) {
        genreDao.insert(genre)
    }
    suspend fun delete(genre: GenreEntity) {
        genreDao.delete(genre)
    }
}