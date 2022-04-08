package com.example.scannerapplication.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.scannerapplication.databinding.AddProductDialogBinding
import com.example.scannerapplication.models.Product
import com.example.scannerapplication.viewmodel.ProductViewModel

class AddProductDialog() : DialogFragment() {
    lateinit var barcode: String
    var vm: ProductViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (arguments != null) {
            barcode = arguments!!.getString("barcode", "")
        }
        vm = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        val binding = AddProductDialogBinding.inflate(LayoutInflater.from(context))

        binding.tvHeader.text = "Czy chcesz dodać produkt o numerze\n" + barcode

        binding.btnConfirm.setOnClickListener {
            val productName = binding.etProductName.text.toString()
            if (barcode.isEmpty() || productName.isEmpty()) {
                Toast.makeText(
                    context,
                    "Nie mozna dodac produktu z pustymi polami",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                vm!!.insert(Product(barcode = barcode, productName = productName, count = 1))
                dismiss()
            }
        }

        binding.btnDecline.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    companion object {
        const val TAG = "AddProductDialogTag"
    }
}