package com.example.matchastock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.matchastock.databinding.FragmentInventoryBinding
import com.example.matchastock.databinding.FragmentLoginBinding

class InventoryFragment : Fragment() {

    private lateinit var binding: FragmentInventoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInventoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.bottomNav.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.btnHome -> {
                    findNavController().navigate(R.id.action_inventoryFragment_to_homeFragment)

                }

                R.id.btnPerfil -> {
                    findNavController().navigate(R.id.action_inventoryFragment_to_userInfoFragment)
                }

                R.id.btnInventario -> {
                    Toast.makeText(context, "Inventario", Toast.LENGTH_SHORT).show()
                    binding.bottomNav.menu.findItem(R.id.btnInventario)?.isChecked = true

                }

            }
            val navController = findNavController()
            NavigationUI.setupWithNavController(binding.bottomNav, navController)
        }


    }

    override fun onResume() {
        super.onResume()
        binding.bottomNav.menu.findItem(R.id.btnInventario)?.isChecked = true
    }

}
