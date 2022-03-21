package com.example.scannerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.view.isVisible
import com.example.scannerapplication.databinding.ActivityMainBinding
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
                Toast.makeText(applicationContext, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(applicationContext, "Scanned: " + result.contents, Toast.LENGTH_LONG).show();
            }
        }
        binding.spinnerSearch.isVisible = false
        binding.btnScan.setOnClickListener {
            barcodeLauncher.launch(ScanOptions())
        }
    }

}