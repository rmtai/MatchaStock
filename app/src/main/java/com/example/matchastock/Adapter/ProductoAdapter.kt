package com.example.matchastock.Adapter

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.matchastock.Controllers.ProductoController
import com.example.matchastock.Controllers.SessionController
import com.example.matchastock.Entities.Product
import com.example.matchastock.R
import com.example.matchastock.databinding.ItemProductoBinding
import okhttp3.OkHttpClient


class ProductoAdapter(
    private var productList: List<Product>,
    private var controller: NavController,
    private var context : Context
) :
    RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    var productoController = ProductoController(OkHttpClient())


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_producto, parent, false))
    }

    override fun onBindViewHolder(holder: ProductoAdapter.ViewHolder, position: Int) {
        val item = productList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = productList.size

    fun updateData(newProdList: List<Product>) {
        productList = newProdList
        Handler(Looper.getMainLooper()).post {
            notifyDataSetChanged()
        }

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemProductoBinding.bind(view)

        fun render(productModel: Product) {
            binding.tvProdName.text = productModel.nombreProd
            binding.tvDescProd.text = productModel.descripcionProd
            binding.tvCantProd.text = productModel.cantidadProd.toString()
            binding.btnEditarProd.setOnClickListener {
                var productoId = productModel.idItem.toString()
                var bundle: Bundle? = Bundle()
                bundle!!.putString("idProducto", productoId)
                controller.navigate(R.id.action_inventoryFragment_to_editarProdFragment, bundle)
            }

            binding.btnEliminarProd.setOnClickListener {
                productoController.eliminarProducto(productModel)
                var lista = productoController.mostrarProducto()
                var listaAux = mutableListOf<Product>()
                val sessionController = SessionController.getInstance(context)

                lista.forEach {
                    if (it.estado != 3 && it.idUser == sessionController.getId()?.toInt()) {
                        listaAux.add(it)
                    }
                }
                updateData(listaAux)
            }
        }

    }


}