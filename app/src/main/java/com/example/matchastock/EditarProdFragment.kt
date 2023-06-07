package com.example.matchastock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.matchastock.Controllers.ProductoController
import com.example.matchastock.Controllers.SessionController
import com.example.matchastock.Entities.Product
import com.example.matchastock.databinding.FragmentEditarProdBinding
import okhttp3.OkHttpClient


class EditarProdFragment : Fragment() {

    private lateinit var binding: FragmentEditarProdBinding
    var id = ""
    var productoController = ProductoController(OkHttpClient())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = requireArguments().getString("idProducto").toString()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditarProdBinding.inflate(inflater, container, false)
        var lista = ProductoController(OkHttpClient()).mostrarProducto()
        var prodAux = Product(1, "", "", 5, 2, 8)
        lista.forEach {
            if(it.idItem.toString() == id){
                prodAux = it
            }

        }
        binding.etNombre.setText(prodAux.nombreProd)
        binding.etDescripcion.setText(prodAux.descripcionProd)
        binding.etCantidad.setText(prodAux.cantidadProd.toString())

        val sessionController = context?.let { SessionController.getInstance(it) }
        binding.btnEditarProd.setOnClickListener {
            var nombre = binding.etNombre.text.toString()
            var descripcion = binding.etDescripcion.text.toString()
            var cantidad = binding.etCantidad.text.toString().toInt()
            var producto = Product(
                id.toInt(),
                nombre,
                descripcion,
                cantidad,
                2,
                sessionController?.getId()?.toInt()
            )
            productoController.editarProducto(producto)
            findNavController().navigate(R.id.action_editarProdFragment_to_inventoryFragment)
        }


        return binding.root
    }



}