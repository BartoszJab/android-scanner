package com.example.scannerapplication.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.scannerapplication.databinding.AddProductDialogBinding
import com.example.scannerapplication.databinding.EditProductDialogBinding
import com.example.scannerapplication.models.Product
import com.example.scannerapplication.viewmodel.ProductViewModel

class EditProductDialog() : DialogFragment() {
    private var uid: Int? = null
    lateinit var productName: String
    lateinit var barcode: String
    var vm: ProductViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (arguments != null) {
            uid = arguments!!.getInt("uid", 0)
            productName = arguments!!.getString("productName", "")
            barcode = arguments!!.getString("barcode", "")
        }
        vm = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        val binding = EditProductDialogBinding.inflate(LayoutInflater.from(context))
        binding.tvHeader.text = "Edytuj nazwę produktu\n" + barcode
        binding.etProductName.setText(productName)

        binding.btnConfirm.setOnClickListener {
            val productName = binding.etProductName.text.toString()
            if (productName.isEmpty()) {
                Toast.makeText(
                    context,
                    "Nie mozna dodac produktu z pustymi polami",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                vm!!.update(Product(uid = uid!!, barcode = barcode, productName = productName))
                dismiss()
            }
        }

        binding.btnDecline.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    companion object {
        const val TAG = "EditProductDialogTag"
    }
}