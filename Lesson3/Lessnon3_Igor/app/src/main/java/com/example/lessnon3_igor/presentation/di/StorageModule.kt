package com.example.lessnon3_igor.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.lessnon3_igor.presentation.data.storage.ProductDB
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
}