package com.example.lessnon3_igor.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lessnon3_igor.presentation.ui.catalog.CatalogViewModel
import com.example.lessnon3_igor.presentation.ui.order.OrderViewModel
import com.example.lessnon3_igor.presentation.ui.product.ProductViewModel
import com.example.lessnon3_igor.presentation.ui.profile.ProfileViewModel
import com.example.lessnon3_igor.presentation.ui.signin.ExampleViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
    @Binds
    @IntoMap
    @ViewModelKey(ExampleViewModel::class)
    abstract fun exampleViewModel(exampleViewModel: ExampleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CatalogViewModel::class)
    abstract fun catalogViewModel(exampleViewModel: CatalogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    abstract fun productViewModel(exampleViewModel: ProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderViewModel::class)
    abstract fun orderViewModel(exampleViewModel: OrderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun profileViewModel(exampleViewModel: ProfileViewModel): ViewModel
}