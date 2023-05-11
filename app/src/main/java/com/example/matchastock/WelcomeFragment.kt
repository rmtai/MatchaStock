package com.example.matchastock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.matchastock.databinding.FragmentLoginBinding
import com.example.matchastock.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {

    private lateinit var binding : FragmentWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        binding.btnEnter.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
        }

        return binding.root
    }


}