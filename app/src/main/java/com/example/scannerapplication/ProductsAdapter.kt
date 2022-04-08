package com.example.scannerapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scannerapplication.databinding.ProductRowBinding
import com.example.scannerapplication.models.Product

class ProductsAdapter(private val allProducts: List<Product>, private val onDeleteListener: OnDeleteListener, private val onEditListener: OnEditListener) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ProductRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, onDeleteListener, onEditListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = allProducts[position]
        holder.binding.tvBarcode.text = product.barcode
        holder.binding.tvProductName.text = product.productName
        holder.binding.tvCount.text = product.count.toString()
    }

    override fun getItemCount(): Int = allProducts.size

    interface OnDeleteListener {
        fun onDeleteClick(position: Int)
    }

    interface OnEditListener {
        fun onEditListener(position: Int)
    }

    class ViewHolder(val binding: ProductRowBinding, val onDeleteListener: OnDeleteListener, val  onEditListener: OnEditListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.imageButtonDelete.setOnClickListener(this)
            binding.imageButtonEdit.setOnClickListener {
                onEditListener.onEditListener(adapterPosition)
            }
        }

        override fun onClick(p0: View?) {
            onDeleteListener.onDeleteClick(adapterPosition)
        }

    }

}
