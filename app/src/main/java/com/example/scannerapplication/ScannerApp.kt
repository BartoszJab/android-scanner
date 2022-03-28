package com.example.scannerapplication

import android.app.Application
import com.example.scannerapplication.database.AppDatabase
import com.example.scannerapplication.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ScannerApp : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy {
        AppDatabase.getDatabase(this, applicationScope)
    }
    val repository by lazy {
        ProductRepository(database.productDao())
    }
}