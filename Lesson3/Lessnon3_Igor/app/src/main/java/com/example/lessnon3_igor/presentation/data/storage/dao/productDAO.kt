package com.example.lessnon3_igor.presentation.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lessnon3_igor.presentation.data.dto.Product

@Dao
interface productDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProducts(products: List<Product>)
    @Query("Select * from Product")
    suspend fun getProducts():List<Product>
}