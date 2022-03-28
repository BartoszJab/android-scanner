package com.example.scannerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scannerapplication.databinding.ActivityMainBinding
import com.example.scannerapplication.models.Product
import com.example.scannerapplication.viewmodel.ProductViewModel
import com.example.scannerapplication.viewmodel.ProductViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var listOfProducts: List<Product> = listOf()
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
                Toast.makeText(applicationContext, "Anulowano", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(
                    applicationContext,
                    "Zeskanowano: " + result.contents,
                    Toast.LENGTH_LONG
                ).show();
                // wyswietl dialog z dodaniem produktu do bazy
                showAddDialog(barcode = result.contents.toString())
            }
        }

        val recyclerView = binding.rvProducts
        val adapter = ProductsAdapter(listOfProducts)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        binding.editTextSearch.addTextChangedListener { text ->
            productViewModel.findLikeGivenData(text.toString())
        }

        productViewModel.searchedProducts.observe(this, { searchedProducts ->
            listOfProducts = searchedProducts
            recyclerView.adapter = ProductsAdapter(listOfProducts)
        })

        binding.btnScan.setOnClickListener {
            barcodeLauncher.launch(ScanOptions())
        }

    }

    private fun showAddDialog(barcode: String) {
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        val barcodeInput = TextView(this)
        barcodeInput.text = barcode
        barcodeInput.inputType = InputType.TYPE_CLASS_TEXT

        val productNameInput = EditText(this)
        productNameInput.hint = "Nazwa produktu"
        productNameInput.inputType = InputType.TYPE_CLASS_TEXT

        linearLayout.addView(barcodeInput)
        linearLayout.addView(productNameInput)

        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Dodaj produkt")
            .setMessage("Czy chcesz dodac produkt?")
            .setCancelable(false)
            .setNegativeButton("Anuluj") { _, _ -> cancel() }
            .setView(linearLayout)
            .setPositiveButton("Dodaj", null)
            .show()


        val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            val currentBarcode = barcodeInput.text.toString()
            val currentProductName = productNameInput.text.toString()
            if (currentBarcode.trim().isEmpty() || currentProductName.trim().isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Nie mozna dodac produktu z pustymi polami",
                    Toast.LENGTH_LONG
                ).show();
            } else {
                // sprawdz czy produkt (jego kod kreskowy) znajduje sie w bazie
                // jesli nie ma to dodaj, jesli jest to anuluj
                Log.d("xd", "$currentBarcode  $currentProductName")
                productViewModel.insert(
                    Product(barcode = currentBarcode, productName = currentProductName)
                )
                dialog?.dismiss()
            }
        }
    }

    private fun cancel() {

    }

}