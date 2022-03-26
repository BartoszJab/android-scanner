package com.example.scannerapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.scannerapplication.dao.ProductDao
import com.example.scannerapplication.models.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao() : ProductDao
}