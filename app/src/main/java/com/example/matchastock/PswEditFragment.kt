package com.example.matchastock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.matchastock.databinding.FragmentPswEditBinding
import com.example.matchastock.databinding.FragmentUserEditBinding

class PswEditFragment : Fragment() {
    private lateinit var binding : FragmentPswEditBinding

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
            findNavController().navigate(R.id.action_pswEditFragment_to_userInfoFragment2)
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

}