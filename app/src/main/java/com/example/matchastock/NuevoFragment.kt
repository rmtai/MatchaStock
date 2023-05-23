package com.example.matchastock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.matchastock.databinding.FragmentNuevoBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class NuevoFragment : Fragment() {

    private lateinit var binding : FragmentNuevoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNuevoBinding.inflate(inflater, container, false)

        return binding.root
    }

}