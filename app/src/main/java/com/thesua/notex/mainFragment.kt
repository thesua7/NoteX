package com.thesua.notex

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.thesua.notex.databinding.FragmentMainBinding
import com.thesua.notex.model.auth.Result
import com.thesua.notex.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class mainFragment : Fragment() {

    private var _binding:FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel :UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.heda.setOnClickListener{
//            viewModel.signOut().observe(viewLifecycleOwner) {
//                when (it) {
//                    is Result.Success -> {
//                        Log.d("logout", it.data.toString())
//                    }
//
//                    is Result.Error -> {
//                        Log.d("logoutError", it.exception.message.toString())
//                    }
//                }
//            }
//        }
    }


}