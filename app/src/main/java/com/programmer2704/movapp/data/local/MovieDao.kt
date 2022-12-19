package com.programmer2704.movapp.data.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

@Dao
interface MovieDao {

    @Query("SELECT * from movies")
    fun getFavoriteMovies(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Delete
    suspend fun delete(movie: MovieEntity)

    @Query("DELETE FROM movies")
    suspend fun deleteMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("delete from movies")
    suspend fun clearMovies()

    @Query("select * from movies order by id asc")
    fun getPagingMovie() : PagingSource<Int, MovieEntity>
    @Query("select * from movies order by id asc limit :limit")
    fun getFlowMovies(limit: Int): Flow<List<MovieEntity>>
}