package com.example.matchastock.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.matchastock.Entities.Product
import com.example.matchastock.R
import com.example.matchastock.databinding.ItemProductoBinding

class ProductoAdapter(private var productList: List<Product>): RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoAdapter.ViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_producto, parent, false))
    }

    override fun onBindViewHolder(holder: ProductoAdapter.ViewHolder, position: Int) {
        val item = productList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = productList.size

    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view){
        val binding = ItemProductoBinding.bind(view)

        fun render(productModel: Product){
            binding.tvProdName.text = productModel.nombreProd
            binding.tvDescProd.text = productModel.descripcionProd
            binding.tvCantProd.text = productModel.cantidadProd.toString()
        }

    }


}