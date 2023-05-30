package com.example.matchastock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.matchastock.Controllers.UsuarioController
import com.example.matchastock.databinding.FragmentUserEditBinding
import com.example.matchastock.databinding.FragmentUserInfoBinding
import okhttp3.OkHttpClient

class UserEditFragment : Fragment(), UsuarioController.OnUsuarioEditListener {
    private lateinit var binding : FragmentUserEditBinding
    private lateinit var usuarioController: UsuarioController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserEditBinding.inflate(inflater, container, false)
        usuarioController = UsuarioController(OkHttpClient())

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnActUser.setOnClickListener{
            findNavController().navigate(R.id.action_userEditFragment_to_userInfoFragment)
        }
        binding.btnActUser.setOnClickListener {
            val nombre = binding.etName.text.toString()
            val apellido = binding.etApell.text.toString()
            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val passwordUser = binding.etPassword.text.toString()

            if (nombre.isEmpty() || apellido.isEmpty() || username.isEmpty() || email.isEmpty() || passwordUser.isEmpty()) {
                // Mostrar mensaje de error indicando que todos los campos deben estar completos.
                Toast.makeText(
                    requireContext(),
                    "Todos los campos son requeridos",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                // Mostrar mensaje de error indicando que el formato del correo electrónico es inválido.
                Toast.makeText(
                    requireContext(),
                    "Formato de correo electrónico inválido",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            usuarioController.editarUsuario(nombre, apellido, username, email, passwordUser, this)
        }


        binding.btnCancelUser.setOnClickListener{
            findNavController().navigate(R.id.action_userEditFragment_to_userInfoFragment2)
        }


        binding.bottomNav.setOnItemReselectedListener { item ->
            when (item.itemId){
                R.id.btnHome -> {
                    Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show()
                }
                R.id.btnPerfil -> {
                    findNavController().navigate(R.id.action_homeFragment_to_userInfoFragment)
                }
                R.id.btnInventario -> {
                    findNavController().navigate(R.id.action_homeFragment_to_inventoryFragment)
                }

            }
        }
    }

    override fun onUsuarioEditadoExitoso() {
        val mensaje = "Datos editados correctamente"
        requireActivity().runOnUiThread{
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onErrorEditar(mensaje: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
        }
    }

}