package com.example.matchastock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.matchastock.databinding.FragmentEliminadoProdBinding


class EliminadoProdFragment : Fragment() {

    private lateinit var binding: FragmentEliminadoProdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEliminadoProdBinding.inflate(inflater, container, false)

        return binding.root
    }

}
