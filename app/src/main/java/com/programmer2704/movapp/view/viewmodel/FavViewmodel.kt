package com.programmer2704.movapp.view.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.programmer2704.movapp.data.local.MovieDao
import com.programmer2704.movapp.data.local.MyDB
import com.programmer2704.movapp.data.local.MovieEntity
//import com.programmer2704.movapp.data.local.MyDBWithoutHiltModule
import com.programmer2704.movapp.data.repositories.MovieDBRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavViewmodel(application: Application) : AndroidViewModel(application) {

    private val repo: MovieDBRepo
    val favoriteMoviesList: LiveData<List<MovieEntity>>

    init {
        val moviesDao = MyDB.getDatabase(application).movieDao()
        repo = MovieDBRepo(moviesDao)
        favoriteMoviesList = repo.favoriteMovies
    }

    fun insert(movie: MovieEntity) = viewModelScope.launch {
        repo.insert(movie)
    }

    fun delete(movie: MovieEntity) = viewModelScope.launch {
        repo.delete(movie)
    }
}