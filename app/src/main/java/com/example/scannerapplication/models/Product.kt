package com.example.scannerapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.scannerapplication.constants.BARCODE
import com.example.scannerapplication.constants.PRODUCT_NAME

@Entity
data class Product(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = BARCODE) val barcode: String,
    @ColumnInfo(name = PRODUCT_NAME) val productName: String
    )
