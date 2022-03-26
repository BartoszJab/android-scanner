package com.example.scannerapplication

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import com.example.scannerapplication.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Register the launcher and result handler
        val barcodeLauncher: ActivityResultLauncher<ScanOptions> = registerForActivityResult(
            ScanContract()) {
            result ->
            if(result.contents == null) {
                Toast.makeText(applicationContext, "Anulowano", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(applicationContext, "Zeskanowano: " + result.contents, Toast.LENGTH_LONG).show();
                binding.etContent.setText(result.contents.toString())
                // wyswietl dialog z dodaniem produktu do bazy
                showAddDialog(barcode = result.contents.toString())
            }
        }

        binding.btnScan.setOnClickListener {
            barcodeLauncher.launch(ScanOptions())
        }

    }

    private fun showAddDialog(barcode: String) {
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        val barcodeInput = EditText(this)
        barcodeInput.setHint("Kod produktu")
        barcodeInput.setText(barcode)
        barcodeInput.inputType = InputType.TYPE_CLASS_TEXT

        val productNameInput = EditText(this)
        productNameInput.setHint("Nazwa produktu")
        productNameInput.inputType = InputType.TYPE_CLASS_TEXT

        linearLayout.addView(barcodeInput)
        linearLayout.addView(productNameInput)

        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Dodaj produkt")
            .setMessage("Czy chcesz dodac produkt?")
            .setCancelable(false)
            .setNegativeButton("Anuluj") { _, _ -> cancel()}
            .setView(linearLayout)
            .setPositiveButton("Dodaj", null)
            .show()



        val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            val currentBarcode = barcodeInput.text.toString()
            val currentProductName = productNameInput.text.toString()
            if (currentBarcode.trim().isEmpty() || currentProductName.trim().isEmpty())
            {
                Toast.makeText(applicationContext, "Nie mozna dodac produktu z pustymi polami", Toast.LENGTH_LONG).show();
            } else {
                // sprawdz czy produkt (jego kod kreskowy) znajduje sie w bazie
                    // jesli nie ma to dodaj, jesli jest to anuluj
//                addToDatabase()
                dialog?.dismiss()
            }
        }
    }

    private fun cancel() {

    }

    private fun addToDatabase(barcode: String, productName: String) {
        // dodac room do zapytan
    }
}