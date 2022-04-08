package com.example.scannerapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.scannerapplication.constants.DATABASE_NAME
import com.example.scannerapplication.dao.ProductDao
import com.example.scannerapplication.models.Product
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Product::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            if (INSTANCE != null) {
                return INSTANCE!!
            } else {
                synchronized(this) {
                    return Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
        }
    }
}