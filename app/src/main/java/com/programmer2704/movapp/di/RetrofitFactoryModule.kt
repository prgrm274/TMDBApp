package com.programmer2704.movapp.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.programmer2704.movapp.constant.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.programmer2704.movapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object RetrofitFactoryModule {

    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("api_key", Constants.API_KEY)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client =
        if (BuildConfig.DEBUG) {
            OkHttpClient().newBuilder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()
        }

    @Provides
    @Named("retrofit")
    fun retrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}