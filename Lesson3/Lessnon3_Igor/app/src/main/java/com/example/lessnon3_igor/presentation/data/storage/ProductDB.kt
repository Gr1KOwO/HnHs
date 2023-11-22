package com.example.lessnon3_igor.presentation.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lessnon3_igor.presentation.data.dto.Product
import com.example.lessnon3_igor.presentation.data.storage.dao.productDAO

@Database(entities = [Product::class], version = 1)
abstract class ProductDB:RoomDatabase() {
    abstract fun getProductDao():productDAO
}