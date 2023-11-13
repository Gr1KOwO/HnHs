package com.example.lessnon3_igor.presentation.di

import androidx.lifecycle.ViewModelProvider
import com.example.lessnon3_igor.presentation.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun provideMainActivity(): MainActivity
}