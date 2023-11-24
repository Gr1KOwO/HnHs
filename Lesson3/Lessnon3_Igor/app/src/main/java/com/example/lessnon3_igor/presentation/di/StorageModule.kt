package com.example.lessnon3_igor.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.lessnon3_igor.presentation.data.storage.ProductDB
import com.example.lessnon3_igor.presentation.data.storage.dao.productDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
    @Singleton
    @Provides
    fun provideProductDB(context:Context):ProductDB{
        return Room.databaseBuilder(context,ProductDB::class.java,"ProductDB").build()
    }
    @Provides
    fun provideDao(database: ProductDB): productDAO{
        return database.getProductDao()
    }
}