package com.example.scannerapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.scannerapplication.constants.BARCODE
import com.example.scannerapplication.constants.PRODUCTS_TABLE
import com.example.scannerapplication.constants.PRODUCT_COUNT
import com.example.scannerapplication.constants.PRODUCT_NAME

@Entity(tableName = PRODUCTS_TABLE)
data class Product(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = BARCODE) val barcode: String,
    @ColumnInfo(name = PRODUCT_NAME) val productName: String,
    @ColumnInfo(name = PRODUCT_COUNT) val count: Int,
    )
