package com.example.scannerapplication.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.scannerapplication.ScannerApp
import com.example.scannerapplication.databinding.AddProductDialogBinding
import com.example.scannerapplication.databinding.EditProductDialogBinding
import com.example.scannerapplication.models.Product
import com.example.scannerapplication.viewmodel.EditDialogViewModel
import com.example.scannerapplication.viewmodel.ProductViewModel
import com.example.scannerapplication.viewmodel.ProductViewModelFactory

class EditProductDialog() : DialogFragment() {
    private var uid: Int? = null
    lateinit var productName: String
    lateinit var barcode: String
    var count: Int? = null
    var vm: ProductViewModel? = null
    private val editDialogViewModel: EditDialogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (arguments != null) {
            uid = arguments!!.getInt("uid", 0)
            productName = arguments!!.getString("productName", "")
            barcode = arguments!!.getString("barcode", "")
            count = arguments!!.getInt("count", 0)
            editDialogViewModel.setCount(count!!)
        }
        vm = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        val binding = EditProductDialogBinding.inflate(LayoutInflater.from(context))
        binding.tvHeader.text = "Edytuj produkt o numberze\n" + barcode
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
                vm!!.update(Product(uid = uid!!, barcode = barcode, productName = productName, count = count!!))
                dismiss()
            }
        }

        binding.btnDecline.setOnClickListener {
            dismiss()
        }

        editDialogViewModel.productCount.observe(this, { productCount ->
            count = productCount
            binding.btnMinus.isVisible = productCount > 1

            binding.tvCount.text = productCount.toString()
        })

        binding.btnPlus.setOnClickListener {
            editDialogViewModel.increaseCount()
        }

        binding.btnMinus.setOnClickListener {
            editDialogViewModel.decreaseCount()
        }

        return binding.root
    }

    companion object {
        const val TAG = "EditProductDialogTag"
    }
}