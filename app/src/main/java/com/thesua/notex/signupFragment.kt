package com.thesua.notex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.thesua.notex.databinding.FragmentSignupBinding


class signupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignupBinding.inflate(layoutInflater,container,false)

        binding.tv.setOnClickListener{
            findNavController().navigate(R.id.action_signupFragment_to_signinFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()

    }

}