package com.example.scannerapplication.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.scannerapplication.constants.BARCODE
import com.example.scannerapplication.constants.PRODUCTS_TABLE
import com.example.scannerapplication.constants.PRODUCT_NAME
import com.example.scannerapplication.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM $PRODUCTS_TABLE")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM $PRODUCTS_TABLE WHERE $BARCODE LIKE '%' || :data || '%' OR $PRODUCT_NAME LIKE '%' || :data || '%'")
    fun findByGivenData(data: String): Flow<List<Product>>

    @Delete
    suspend fun delete(product: Product)

    @Insert
    suspend fun insertProduct(product: Product)
}