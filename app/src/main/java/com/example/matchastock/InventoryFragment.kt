package com.example.matchastock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

}
