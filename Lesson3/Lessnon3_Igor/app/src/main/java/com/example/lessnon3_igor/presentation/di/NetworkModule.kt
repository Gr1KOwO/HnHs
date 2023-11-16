package com.example.lessnon3_igor.presentation.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.lessnon3_igor.presentation.MyApplication
import com.example.lessnon3_igor.presentation.data.ApiLesson
import com.example.lessnon3_igor.presentation.data.interceptors.HeaderInterceptor
import com.example.lessnon3_igor.presentation.data.repository.PreferenceStorage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
@Module
class NetworkModule {

    companion object {
        private const val BASE_ENDPOINT = "http://45.144.64.179/cowboys/api/"
    }
    @Provides
    fun provideOkHttp(
        preferenceStorage: PreferenceStorage,
        context: Context,
    ) = OkHttpClient.Builder().apply {
        addInterceptor(HeaderInterceptor(preferenceStorage))
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        addInterceptor(loggingInterceptor)

        addInterceptor(ChuckerInterceptor.Builder(context)
            .alwaysReadResponseBody(true)
            .build())
    }
        .connectTimeout(20000L, TimeUnit.MILLISECONDS)
        .readTimeout(20000L, TimeUnit.MILLISECONDS)
        .writeTimeout(20000L, TimeUnit.MILLISECONDS)
        .build()

    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient)
        .build()
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }
    @Provides
    fun provideApiService(
        retrofit: Retrofit,
    ): ApiLesson {
        return retrofit.create(ApiLesson::class.java)
    }
}