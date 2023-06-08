package com.example.matchastock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.matchastock.Controllers.SessionController
import com.example.matchastock.Controllers.UsuarioController
import com.example.matchastock.Entities.User
import com.example.matchastock.databinding.FragmentPswEditBinding
import okhttp3.OkHttpClient

class PswEditFragment : Fragment() {
    private lateinit var binding : FragmentPswEditBinding
    var usuarioController = UsuarioController(OkHttpClient())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPswEditBinding.inflate(inflater, container, false)
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnActPsw.setOnClickListener{
            validarPsw()
        }

        binding.btnCancelPsw.setOnClickListener{
            findNavController().navigate(R.id.action_pswEditFragment_to_userInfoFragment2)
        }

        binding.bottomNav.setOnItemReselectedListener { item ->
            when (item.itemId){
                R.id.btnHome -> {
                    findNavController().navigate(R.id.action_pswEditFragment_to_homeFragment)

                }
                R.id.btnPerfil -> {
                    findNavController().navigate(R.id.action_pswEditFragment_to_userInfoFragment2)
                }
                R.id.btnInventario -> {
                    findNavController().navigate(R.id.action_pswEditFragment_to_inventoryFragment)
                }

            }
        }
    }
    fun validarPsw(){

        val currentPassword = binding.etActPsw.text.toString()
        val newPassword = binding.etNuevaPsw.text.toString()
        val confirmPassword = binding.etConfPsw.text.toString()

        // Verificar si la contrase単a actual es correcta
        val lista = usuarioController.mostrarUsuario()

        val sessionController = context?.let { SessionController.getInstance(it) }
        val usuarioActual = lista.find { it.idUser == sessionController?.getId()?.toInt()}

        var usuarioAux = User(0, "", " ", "", "", "")
        lista.forEach {
            if(it.idUser == sessionController?.getId()?.toInt()){
                usuarioAux = it
            }
        }
        if(usuarioAux.passwordUser == currentPassword){
            if(newPassword == confirmPassword){
                sessionController?.getId()?.toInt()?.let {
                    usuarioController.editarUsuario(it, newPassword)
                    Toast.makeText(context, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_pswEditFragment_to_userInfoFragment2)

                }
            }else{
                Toast.makeText(context, "Contraseña nueva no coincide", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(context, "Contraseña actual es incorrecta", Toast.LENGTH_SHORT).show()
        }

    }
}