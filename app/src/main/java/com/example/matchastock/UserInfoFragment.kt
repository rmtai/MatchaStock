package com.example.matchastock

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.matchastock.Controllers.SessionController
import com.example.matchastock.Controllers.UsuarioController
import com.example.matchastock.Entities.Product
import com.example.matchastock.Entities.User
import com.example.matchastock.databinding.FragmentSignUpBinding
import com.example.matchastock.databinding.FragmentUserInfoBinding
import okhttp3.OkHttpClient

class UserInfoFragment : Fragment() {

    private lateinit var userList: List<User>
    private lateinit var binding : FragmentUserInfoBinding
    var usuarioController = UsuarioController(OkHttpClient())
    var usuario = User(0, "", " ", "", "", "")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionController = context?.let { SessionController.getInstance(it) }
        //val usuarioController = UsuarioController(OkHttpClient())
        val lista = usuarioController.mostrarUsuario()
        var usuarioAux = User(0, "", " ", "", "", "")
        lista.forEach {
            if(sessionController?.getId()?.toInt() == it.idUser){
                usuarioAux = it
            }
        }
        binding.tvNombre.setText("${usuarioAux.nombre} ${usuarioAux.apellido}")
        binding.tvCorreo.setText("${usuarioAux.email}")

        binding.cvEditarPerfil.setOnClickListener{
            findNavController().navigate(R.id.action_userInfoFragment_to_userEditFragment)
        }

        binding.cvCambiarPsw.setOnClickListener{
            findNavController().navigate(R.id.action_userInfoFragment_to_pswEditFragment)
        }

        binding.cvCerrarSesion.setOnClickListener{
            val sessionController = context?.let { it1 -> SessionController.getInstance(it1) }
            sessionController?.clearSession()
            findNavController().navigate(R.id.action_userInfoFragment_to_loginFragment2)

        }

        binding.bottomNav.setOnItemReselectedListener { item ->
            when (item.itemId){
                R.id.btnHome -> {
                    findNavController().navigate(R.id.action_userInfoFragment_to_homeFragment)

                }
                R.id.btnPerfil -> {
                    Toast.makeText(context, "Perfil", Toast.LENGTH_SHORT).show()
                    binding.bottomNav.menu.findItem(R.id.btnPerfil)?.isChecked = true
                }
                R.id.btnInventario -> {
                    findNavController().navigate(R.id.action_userInfoFragment_to_inventoryFragment)
                }

            }
        }
        binding.cvEliminarCuenta.setOnClickListener{
            val lista = usuarioController.mostrarUsuario()
            val usuarioActual = lista.find { it.idUser == sessionController?.getId()?.toInt() }

            if (usuarioActual != null) {
                // Eliminar el usuario
                usuarioController.eliminarUsuario(usuarioActual)
                // Realizar las acciones necesarias después de eliminar la cuenta, como navegar a la pantalla de inicio de sesión
                sessionController?.clearSession()
                findNavController().navigate(R.id.action_userInfoFragment_to_loginFragment)
            } else {
                Toast.makeText(context, "Error: Usuario no encontrado", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.bottomNav.menu.findItem(R.id.btnPerfil)?.isChecked = true
    }

}