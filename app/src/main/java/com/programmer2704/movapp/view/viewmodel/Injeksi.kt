package com.programmer2704.movapp.view.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.programmer2704.movapp.data.local.TopRatedLocalCache
import com.programmer2704.movapp.data.local.TopRatedRepository
import com.programmer2704.movapp.data.local.Toprateddb
import com.programmer2704.movapp.data.remote.NetworkService
import java.util.concurrent.Executors

object Injeksi {
    private fun provideTopRatedCache(context: Context): TopRatedLocalCache {
        val database = Toprateddb.getInstance(context)
        return TopRatedLocalCache(database.topRatedDao(), Executors.newSingleThreadExecutor())
    }
    private fun provideTopRatedRepository(context: Context): TopRatedRepository {
        return TopRatedRepository(NetworkService.instance, provideTopRatedCache(context))
    }
    fun provideTopRatedViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelTopRatedFactory(provideTopRatedRepository(context))
    }
}