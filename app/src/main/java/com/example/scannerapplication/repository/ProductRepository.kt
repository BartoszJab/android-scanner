package com.example.scannerapplication.repository

import com.example.scannerapplication.dao.ProductDao
import com.example.scannerapplication.models.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {
    val allProducts: Flow<List<Product>> = productDao.getAllProducts()

    fun findByGivenData(data: String): Flow<List<Product>> {
        return productDao.findByGivenData(data)
    }

    suspend fun insert(product: Product) {
        productDao.insertProduct(product)
    }

    suspend fun delete(uid: Int) {
        productDao.delete(uid)
    }
}