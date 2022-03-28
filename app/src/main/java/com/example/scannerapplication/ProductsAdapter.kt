package com.example.scannerapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scannerapplication.databinding.ProductRowBinding
import com.example.scannerapplication.models.Product

class ProductsAdapter(private val allProducts: List<Product>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ProductRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = allProducts[position]
        holder.binding.tvBarcode.text = product.barcode
        holder.binding.tvProductName.text = product.productName

        holder.binding.imageButtonDelete.setOnClickListener {

        }
    }

    override fun getItemCount(): Int = allProducts.size

    class ViewHolder(val binding: ProductRowBinding) : RecyclerView.ViewHolder(binding.root)

}
