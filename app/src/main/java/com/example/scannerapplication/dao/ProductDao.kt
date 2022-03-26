package com.example.scannerapplication.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.scannerapplication.constants.BARCODE
import com.example.scannerapplication.constants.PRODUCTS_TABLE
import com.example.scannerapplication.models.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM $PRODUCTS_TABLE")
    fun getAllProducts(): List<Product>

    @Query("SELECT * FROM $PRODUCTS_TABLE WHERE $BARCODE = :barcode")
    fun findByBarcode(barcode: String): Product

    @Query("SELECT * FROM $PRODUCTS_TABLE WHERE $BARCODE = :productName")
    fun findByProductName(productName: String): Product

    @Delete
    fun delete(product: Product)

    @Insert
    fun insertProduct(product: Product)
}