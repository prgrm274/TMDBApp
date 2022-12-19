package com.programmer2704.movapp.data.remote

import com.programmer2704.movapp.constant.Constants
import com.programmer2704.movapp.di.RetrofitFactoryModule
import javax.inject.Named

object ApiFactory {
    val movieApi: MovieApi =
        RetrofitFactoryModule.retrofit(
            Constants.MOVIEDB_BASE_URL
        ).create(MovieApi::class.java)
}