package com.example.matchastock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matchastock.Controllers.SessionController
import com.example.matchastock.Controllers.UsuarioController
import com.example.matchastock.databinding.FragmentLoginBinding
import okhttp3.OkHttpClient

class LoginFragment : Fragment(), UsuarioController.OnUsuarioLoginListener {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var usuarioController: UsuarioController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sessionController = context?.let { SessionController.getInstance(it) }
        if(sessionController?.isLoggedIn() == true){
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        usuarioController = UsuarioController(OkHttpClient())


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.btnLogin.setOnClickListener {

            val username = binding.etUsername.text.toString().trim()
            val passwordUser = binding.etPassword.text.toString().trim()
            login(username, passwordUser)
        }
    }

    fun login(username: String, passwordUser: String) {

        val user = usuarioController.validarUsuario(username, passwordUser)
        if (user != null) {
            if(user.nombre.isNotEmpty()){
                val sessionController = context?.let { SessionController.getInstance(it) }
                if (sessionController != null) {
                    sessionController.saveUserInfo(user.idUser.toString())
                    onUsuarioLoginExitoso()
                }

            }
        }
    }

    override fun onUsuarioLoginExitoso() {
        val mensaje = "Inicio exitoso."
        activity?.runOnUiThread {
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)
        }
    }

    override fun onErrorLogin(mensaje: String) {
        // Hubo un error en el inicio de sesión

        val mensaje = "Usuario o contraseña incorrectos. Pruebe nuevamente."
        requireActivity().runOnUiThread {
            Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
        }
    }
}
