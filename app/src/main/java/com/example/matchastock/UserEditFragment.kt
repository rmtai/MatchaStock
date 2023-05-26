package com.example.matchastock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.matchastock.databinding.FragmentUserEditBinding
import com.example.matchastock.databinding.FragmentUserInfoBinding

class UserEditFragment : Fragment() {
    private lateinit var binding : FragmentUserEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserEditBinding.inflate(inflater, container, false)


        return binding.root

}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnActUser.setOnClickListener{
            findNavController().navigate(R.id.action_userEditFragment_to_userInfoFragment2)
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

}