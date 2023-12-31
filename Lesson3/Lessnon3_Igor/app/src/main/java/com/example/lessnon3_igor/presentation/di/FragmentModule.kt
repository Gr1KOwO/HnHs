package com.example.lessnon3_igor.presentation.di

import com.example.lessnon3_igor.presentation.ui.catalog.CatalogFragment
import com.example.lessnon3_igor.presentation.ui.order.OrderFragment
import com.example.lessnon3_igor.presentation.ui.product.ProductFragment
import com.example.lessnon3_igor.presentation.ui.product.size.SizeBottomSheetFragment
import com.example.lessnon3_igor.presentation.ui.profile.ProfileSettingsFragment
import com.example.lessnon3_igor.presentation.ui.profile.VariableBottomSheetFragment
import com.example.lessnon3_igor.presentation.ui.signin.SignInFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun exampleFragment(): SignInFragment
    @ContributesAndroidInjector
    abstract fun catalogFragment(): CatalogFragment
    @ContributesAndroidInjector
    abstract  fun productFragment(): ProductFragment
    @ContributesAndroidInjector
    abstract fun sizeBottomSheetFragment():SizeBottomSheetFragment
    @ContributesAndroidInjector
    abstract fun orderFragment():OrderFragment
    @ContributesAndroidInjector
    abstract fun profileSettingsFragment():ProfileSettingsFragment

    @ContributesAndroidInjector
    abstract fun variableBottomSheetFragment():VariableBottomSheetFragment
}