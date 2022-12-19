package com.programmer2704.movapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GenreDao {

    @Query("SELECT * from genres")
    fun getGenresMovies(): LiveData<List<GenreEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(genre: GenreEntity)

    @Delete
    suspend fun delete(genre: GenreEntity)

    @Query("DELETE FROM genres")
    suspend fun deleteGenres()
}