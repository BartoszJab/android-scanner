package com.example.scannerapplication.dao

import androidx.room.*
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

    @Query("SELECT COUNT(*) FROM $PRODUCTS_TABLE WHERE $BARCODE = :barcode")
    fun numberOfProductsOfBarcode(barcode: String): Int

    @Query("DELETE FROM $PRODUCTS_TABLE WHERE uid = :uid")
    suspend fun delete(uid: Int)

    @Insert
    suspend fun insertProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)
}