package com.example.lessnon3_igor.presentation.di

import com.example.lessnon3_igor.presentation.ui.catalog.CatalogFragment
import com.example.lessnon3_igor.presentation.ui.signin.SignInFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun exampleFragment(): SignInFragment
    @ContributesAndroidInjector
    abstract fun catalogFragment(): CatalogFragment
}