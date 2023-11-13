package com.example.lessnon3_igor.presentation.di

import android.app.Application
import android.content.Context
import com.example.lessnon3_igor.presentation.MyApplication
import dagger.Module
import dagger.Provides

@Module
open class ApplicationModule {

    @Provides
    fun provideApplicationContext(app: MyApplication): Context {
        return app.applicationContext
    }
}