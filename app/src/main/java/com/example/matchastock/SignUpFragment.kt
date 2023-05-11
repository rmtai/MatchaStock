package com.example.matchastock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.matchastock.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {

    private lateinit var  binding : FragmentSignUpBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.tvLogin.setOnClickListener{
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        return binding.root
    }
}

