package com.example.matchastock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.matchastock.Controllers.UsuarioController
import com.example.matchastock.Entities.User
import com.example.matchastock.databinding.FragmentSignUpBinding
import okhttp3.OkHttpClient


class SignUpFragment : Fragment(), UsuarioController.OnUsuarioRegistradoListener {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var usuarioController: UsuarioController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        usuarioController = UsuarioController(OkHttpClient())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
        binding.btnRegister.setOnClickListener {
            val nombre = binding.etName.text.toString()
            val apellido = binding.etApellido.text.toString()
            val username = binding.etUsername.text.toString()
            val passwordUser = binding.etPsw.text.toString()
            val email = binding.etEmail.text.toString()

            if (nombre.isEmpty() || apellido.isEmpty() || username.isEmpty() || passwordUser.isEmpty() || email.isEmpty()) {
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
            val user = User(0, nombre, apellido, username, passwordUser, email)
            register(user)
        }
    }

    private fun register(usuario: User) {
        usuarioController.agregarUsuario(usuario, this)
        findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)

    }

    override fun onUsuarioRegistradoExitosamente() {
        val mensaje = "El usuario se ha registrado correctamente."

        requireActivity().runOnUiThread {
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onErrorRegistro(mensaje: String) {
        val mensaje = "Ocurrio un problema al registrar el usuario."

        requireActivity().runOnUiThread {
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
        }
    }
}



