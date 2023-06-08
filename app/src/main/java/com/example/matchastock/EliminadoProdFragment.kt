package com.example.matchastock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matchastock.Adapter.ProdEliminadoAdapter
import com.example.matchastock.Controllers.ProductoController
import com.example.matchastock.Controllers.SessionController
import com.example.matchastock.Entities.Product
import com.example.matchastock.databinding.FragmentEliminadoProdBinding
import okhttp3.OkHttpClient


class EliminadoProdFragment : Fragment() {

    private lateinit var binding: FragmentEliminadoProdBinding
    private lateinit var productoController: ProductoController
    private lateinit var adapter: ProdEliminadoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEliminadoProdBinding.inflate(inflater, container, false)
        productoController = ProductoController(OkHttpClient())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionController = context?.let { SessionController.getInstance(it) }
        val manager = LinearLayoutManager(requireContext())
        var nav = findNavController()
        var lista = ProductoController(OkHttpClient()).mostrarProducto()
        var idU = sessionController?.getId()?.toInt()

        var listaAux = mutableListOf<Product>()
        lista.forEach {
            if (it.estado == 3) {
                if (it.idUser == idU) {
                    listaAux.add(it)

                }

            }
        }


        adapter = ProdEliminadoAdapter(listaAux, nav)
        binding.rvEliminadoProd.layoutManager = manager
        binding.rvEliminadoProd.adapter = adapter


        binding.bottomNav.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.btnHome -> {
                    findNavController().navigate(R.id.action_eliminadoProdFragment_to_homeFragment)
                }

                R.id.btnPerfil -> {
                    findNavController().navigate(R.id.action_eliminadoProdFragment_to_userInfoFragment)
                }

                R.id.btnInventario -> {
                    findNavController().navigate(R.id.action_eliminadoProdFragment_to_inventoryFragment)
                }
            }
            val navController = findNavController()
            NavigationUI.setupWithNavController(binding.bottomNav, navController)

        }

    }

    override fun onResume() {
        super.onResume()
        binding.bottomNav.menu.findItem(R.id.btnHome)?.isChecked = false
        binding.bottomNav.menu.findItem(R.id.btnInventario)?.isChecked = false
        binding.bottomNav.menu.findItem(R.id.btnPerfil)?.isChecked = false

    }

}
