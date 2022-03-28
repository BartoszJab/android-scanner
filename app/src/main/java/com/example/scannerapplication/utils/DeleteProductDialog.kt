package com.example.scannerapplication.utils

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.scannerapplication.viewmodel.ProductViewModel

class DeleteProductDialog : DialogFragment() {
    private var vm: ProductViewModel? = null
    private var uid: Int? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        vm = ViewModelProvider(requireActivity())[ProductViewModel::class.java]

        if (arguments != null) {
            uid = arguments!!.getInt("uid", 0)
        }

        return AlertDialog.Builder(requireContext())
            .setMessage("Czy chcesz usunac produkt?")
            .setPositiveButton("Tak") { _, _ ->
                vm!!.delete(uid!!)
            }
            .setNegativeButton("Nie") { _, _ ->
                dismiss()
            }
            .create()
    }

    companion object {
        const val TAG = "DeleteDialogTag"
    }
}