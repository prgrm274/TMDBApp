package com.programmer2704.movapp.tes1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.programmer2704.movapp.data.local.MovieEntity
import com.programmer2704.movapp.data.repositories.MovieRemoteRepo
import com.programmer2704.movapp.view.viewmodel.PopularViewmodel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.mock

class PopularViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var movieRemoteRepo: MovieRemoteRepo
    private lateinit var vm: PopularViewmodel

    @Before
    fun setUp() {
        movieRemoteRepo = mock(MovieRemoteRepo::class.java)
        vm = PopularViewmodel()
    }

    @Test
    fun `view model should return list of movie entity test`() = runBlocking {
        val a = MovieEntity(1234, "/path")
        val b = MovieEntity(4321, "/path/2")
        val expected = mutableListOf(a,b)
        vm.popularMoviesLiveData.postValue(expected)
        val c = MovieEntity(1234, "/path")
        val d = MovieEntity(4321, "/path/2")
        val actual = mutableListOf(c,d)
        assertEquals(expected, actual)
    }

}