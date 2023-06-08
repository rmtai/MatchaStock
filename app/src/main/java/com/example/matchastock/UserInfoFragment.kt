package com.example.matchastock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.matchastock.databinding.FragmentSignUpBinding
import com.example.matchastock.databinding.FragmentUserInfoBinding

class UserInfoFragment : Fragment() {

    private lateinit var binding : FragmentUserInfoBinding

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

        binding.cvEditarPerfil.setOnClickListener{
            findNavController().navigate(R.id.action_userInfoFragment_to_userEditFragment)
        }

        binding.cvCambiarPsw.setOnClickListener{
            findNavController().navigate(R.id.action_userInfoFragment_to_pswEditFragment)
        }

        binding.cvCerrarSesion.setOnClickListener{
            findNavController().navigate(R.id.action_userInfoFragment_to_loginFragment2)
        }

        binding.cvEliminarCuenta.setOnClickListener{
            findNavController().navigate(R.id.action_userInfoFragment_to_signUpFragment)
        }
        binding.cvEliminarCuenta.setOnClickListener {
            deleteUser()
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

    private fun deleteUser() {
        val idUser = arguments?.getString("idUser")

        val url = "http://192.168.1.26/MatchaStock/Usuario/eliminar.php"
        val queue = Volley.newRequestQueue(activity)

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                // Eliminación exitosa, maneja la respuesta del servidor si es necesario
                Toast.makeText(context, "Usuario eliminado exitosamente", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_userInfoFragment_to_signUpFragment)
            },
            Response.ErrorListener { error ->
                // Error en la eliminación, maneja el error según sea necesario
                Toast.makeText(context, "Error al eliminar usuario: $error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["idUser"] = idUser ?: ""
                return params
            }
        }

        queue.add(stringRequest)
    }

}