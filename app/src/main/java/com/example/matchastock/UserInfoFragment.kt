package com.example.matchastock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
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

    }

    override fun onResume() {
        super.onResume()
        binding.bottomNav.menu.findItem(R.id.btnPerfil)?.isChecked = true
    }

}