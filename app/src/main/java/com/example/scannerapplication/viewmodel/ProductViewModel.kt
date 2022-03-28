package com.example.scannerapplication.viewmodel

import androidx.lifecycle.*
import com.example.scannerapplication.models.Product
import com.example.scannerapplication.repository.ProductRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    private val searchString = MutableLiveData<String>("")

    val searchedProducts = Transformations.switchMap(searchString) { string ->
        if (string.isEmpty()) {
            repository.allProducts.asLiveData()
        } else {
            repository.findByGivenData(string).asLiveData()
        }
    }

    fun findLikeGivenData(data: String) {
        searchString.value = data
    }

    // launching new coroutine to insert data in a non-blocking way
    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }

    fun delete(uid: Int) = viewModelScope.launch {
        repository.delete(uid)
    }
}

class ProductViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}