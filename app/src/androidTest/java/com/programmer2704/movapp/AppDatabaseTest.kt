package com.programmer2704.movapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.programmer2704.movapp.data.local.MovieDao
import com.programmer2704.movapp.data.local.MyDB
import com.programmer2704.movapp.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AppDatabaseTest {

    private lateinit var database: MyDB
    private lateinit var popularMoviesDao: MovieDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            MyDB::class.java
        ).build()
        popularMoviesDao = database.movieDao()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testPopularEntity() = runTest {
        val popularEntities = listOf(
            testPopularMovieEntity(1242, "Simo", ""),
            testPopularMovieEntity(2963, "", "Original sin")
        )
    }

    private fun testPopularMovieEntity(
        id: Int,
        title: String,
        originalTitle: String
    ) = Movie(
        id,
        title = title,
        vote_average = 0F,
        vote_count = 0,
        original_title = originalTitle,
        overview = "",
        poster_path = "",
        backdrop_path = "",
        release_date = "",
        original_language = "",
        popularity = 225.21F,
        budget = 0,
        revenue = 0,
        runtime = 0
    )
}
