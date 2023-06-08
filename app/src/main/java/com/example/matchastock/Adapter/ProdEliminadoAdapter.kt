package com.example.matchastock.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.matchastock.Entities.Product
import com.example.matchastock.R
import com.example.matchastock.databinding.ItemEliminadoProdBinding

class ProdEliminadoAdapter(private var prodList: List<Product>,private var controller: NavController) :
    RecyclerView.Adapter<ProdEliminadoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_eliminado_prod, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = prodList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = prodList.size

    fun updateData(newProdList: List<Product>) {
        prodList = newProdList
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = ItemEliminadoProdBinding.bind(view)

        fun render(prod: Product) {
            binding.tvProdName.text = prod.nombreProd
            binding.tvDescProd.text = prod.descripcionProd

        }

    }
}