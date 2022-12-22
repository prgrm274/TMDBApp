package com.programmer2704.movapp.data.local

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface TopratedDao {
    @Query("SELECT * FROM toprated ORDER BY voteCount DESC")
    fun loadAllToprated(): DataSource.Factory<Int, TopRatedEntry>

    @Query("SELECT * FROM toprated WHERE movieId = :id ORDER BY timeAdded")
    fun checkIfToprated(id: Int):Boolean

    @Insert
    fun insertToprated(topRatedEntry: TopRatedEntry)

    @Delete
    fun deleteToprated(topRatedEntry: TopRatedEntry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(searches: List<TopRatedEntry>)

    @Query("DELETE FROM toprated")
    fun deleteAll()

    @Query("SELECT COUNT(movieId) FROM toprated")
    fun getNumberOfRows(): Int
}