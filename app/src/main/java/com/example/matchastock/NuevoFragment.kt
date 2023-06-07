package com.example.matchastock

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.matchastock.Controllers.ProductoController
import com.example.matchastock.Controllers.SessionController
import com.example.matchastock.Controllers.UsuarioController
import com.example.matchastock.Entities.Product
import com.example.matchastock.databinding.FragmentNuevoBinding
import okhttp3.OkHttpClient
import java.io.ByteArrayOutputStream

class NuevoFragment : Fragment(), ProductoController.OnProductoGuardadoListener {

    private lateinit var binding: FragmentNuevoBinding
    private lateinit var productoController: ProductoController
    private var imagenSeleccionada: Bitmap? = null
    private lateinit var session: SharedPreferences
    //private var idUser: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNuevoBinding.inflate(inflater, container, false)
        productoController = ProductoController(OkHttpClient())
        session = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnGuardar.setOnClickListener {
            guardarProducto()

        }


        binding.bottomNav.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.btnHome -> {
                    findNavController().navigate(R.id.action_nuevoFragment_to_homeFragment)
                }

                R.id.btnPerfil -> {
                    findNavController().navigate(R.id.action_nuevoFragment_to_userInfoFragment)
                }

                R.id.btnInventario -> {
                    findNavController().navigate(R.id.action_nuevoFragment_to_inventoryFragment)
                }

            }

        }
    }


    private fun guardarProducto(){
        val sessionController = context?.let { SessionController.getInstance(it) }
        val nombreProd = binding.etNombre.text.toString()
        val descripcionProd = binding.etDescripcion.text.toString()
        val cantidadProd = binding.etCantidad.text.toString().toInt()

        if (nombreProd.isNotEmpty() && descripcionProd.isNotEmpty() && cantidadProd > 0 ){
            val idUser = sessionController?.getId()?.toInt()
            if(idUser != null){
                val producto = Product(null, nombreProd, descripcionProd, cantidadProd, 1, idUser)
                productoController.agregarProducto(producto)
            }else {
                mostrarMensaje("Error al obtener el ID del usuario")
            }

        }
    }



    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
    }
    override fun onProductoGuardadoExitosamente() {
        mostrarMensaje("Producto guardado exitosamente")
        // Restablecer los campos y la imagen seleccionada después de guardar el producto
        binding.etNombre.text = null
        binding.etDescripcion.text = null
        binding.etCantidad.text = null
            }

    override fun onErrorGuardado(mensaje: String) {
        mostrarMensaje("Error al guardar el producto: $mensaje")
    }
}