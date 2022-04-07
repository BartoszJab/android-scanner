package com.example.scannerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scannerapplication.databinding.ActivityMainBinding
import com.example.scannerapplication.models.Product
import com.example.scannerapplication.utils.AddProductDialog
import com.example.scannerapplication.utils.DeleteProductDialog
import com.example.scannerapplication.utils.EditProductDialog
import com.example.scannerapplication.viewmodel.ProductViewModel
import com.example.scannerapplication.viewmodel.ProductViewModelFactory
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions


class MainActivity : AppCompatActivity(), ProductsAdapter.OnDeleteListener, ProductsAdapter.OnEditListener {
    private lateinit var binding: ActivityMainBinding
    private var listOfProducts: List<Product> = listOf()
    lateinit var dialog: AddProductDialog
    private val productViewModel: ProductViewModel by viewModels {
        ProductViewModelFactory((application as ScannerApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Register the launcher and result handler
        val barcodeLauncher: ActivityResultLauncher<ScanOptions> = registerForActivityResult(
            ScanContract()
        ) { result ->
            if (result.contents == null) {
                Toast.makeText(applicationContext, "Anulowano", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Zeskanowano: " + result.contents,
                    Toast.LENGTH_LONG
                ).show()
                var numberOfProducts: Int = 0
//                runOnUiThread {
//                    numberOfProducts = productViewModel.numberOfProductsOfBarcode(result.contents.toString())
//                }

                if (numberOfProducts > 0) {
                    Toast.makeText(
                        applicationContext,
                        "Zeskanowany podukt istnieje w bazie: " + result.contents,
                        Toast.LENGTH_LONG
                    ).show()
                } else {

                    // wyswietl dialog z dodaniem produktu do bazy
                    val addProductDialog = AddProductDialog()
                    val bundle = Bundle().apply {
                        putString("barcode", result.contents.toString())
                    }
                    addProductDialog.arguments = bundle
                    addProductDialog.show(supportFragmentManager, AddProductDialog.TAG)
                }

            }
        }

        val recyclerView = binding.rvProducts
        val adapter = ProductsAdapter(listOfProducts, this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        binding.editTextSearch.addTextChangedListener { text ->
            productViewModel.findLikeGivenData(text.toString())
        }

        productViewModel.searchedProducts.observe(this, { searchedProducts ->
            listOfProducts = searchedProducts
            recyclerView.adapter = ProductsAdapter(listOfProducts, this, this)
        })

        binding.btnScan.setOnClickListener {
            barcodeLauncher.launch(ScanOptions())
        }
    }


    override fun onDeleteClick(position: Int) {
        val deleteProductDialog = DeleteProductDialog()
        val bundle = Bundle().apply {
            putInt("uid", listOfProducts[position].uid)
        }
        deleteProductDialog.arguments = bundle
        deleteProductDialog.show(supportFragmentManager, DeleteProductDialog.TAG)
    }

    override fun onEditListener(position: Int) {
        val editProductDialog = EditProductDialog()
        val bundle = Bundle().apply {
            putInt("uid", listOfProducts[position].uid)
            putString("productName", listOfProducts[position].productName)
            putString("barcode", listOfProducts[position].barcode)
        }
        editProductDialog.arguments = bundle
        editProductDialog.show(supportFragmentManager, EditProductDialog.TAG)
    }

}
