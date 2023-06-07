package com.example.matchastock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matchastock.Adapter.ProductoAdapter
import com.example.matchastock.Controllers.ProductoController
import com.example.matchastock.Controllers.SessionController
import com.example.matchastock.Entities.Product
import com.example.matchastock.databinding.FragmentInventoryBinding
import com.example.matchastock.databinding.FragmentLoginBinding
import okhttp3.OkHttpClient

class InventoryFragment : Fragment(), ProductoController.OnProductoObtenidoListener {

    private lateinit var binding: FragmentInventoryBinding
    private lateinit var productoController: ProductoController
    private lateinit var adapter : ProductoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInventoryBinding.inflate(inflater, container, false)
        productoController = ProductoController(OkHttpClient())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionController = context?.let { SessionController.getInstance(it) }
        val manager = LinearLayoutManager(requireContext())
        var nav = findNavController()
        var lista = ProductoController(OkHttpClient()).mostrarProducto()

        var listaAux = mutableListOf<Product>()
        lista.forEach {
            if(it.idUser == sessionController?.getId()?.toInt()){
                listaAux.add(it)
            }
        }

        adapter = ProductoAdapter(listaAux, nav)
        binding.rvProd.layoutManager = manager
        binding.rvProd.adapter = adapter


        binding.bottomNav.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.btnHome -> {
                    findNavController().navigate(R.id.action_inventoryFragment_to_homeFragment)

                }

                R.id.btnPerfil -> {
                    findNavController().navigate(R.id.action_inventoryFragment_to_userInfoFragment)
                }

                R.id.btnInventario -> {
                    Toast.makeText(context, "Inventario", Toast.LENGTH_SHORT).show()
                    binding.bottomNav.menu.findItem(R.id.btnInventario)?.isChecked = true

                }

            }
            val navController = findNavController()
            NavigationUI.setupWithNavController(binding.bottomNav, navController)
        }


    }

    override fun onResume() {
        super.onResume()
        binding.bottomNav.menu.findItem(R.id.btnInventario)?.isChecked = true
    }

    override fun onProductoObtenidoExitosamente() {
        val mensaje = "Productos obtenidos exitosamente"
        requireActivity().runOnUiThread {
            Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
        }
    }

    override fun onErrorObtenido(mensaje: String) {
        val mensaje = "Hubo un error al obtener los productos."
        requireActivity().runOnUiThread {
            Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
        }
    }

    override fun onProductoObtenido(productos: List<Product>) {
        adapter.updateData(productos)
    }

}
