package com.thesua.notex

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.thesua.notex.databinding.FragmentSigninBinding
import com.thesua.notex.model.auth.Result
import com.thesua.notex.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class signinFragment : Fragment() {

    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSigninBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener{
            if (binding.txtEmail.text.toString().isNotEmpty() && binding.txtPassword.text.toString().isNotEmpty()){
                lifecycleScope.launch {
                    handleLogin(binding.txtEmail.text.toString(),binding.txtPassword.text.toString())
                }
            }
            else{
                binding.txtError.text = "Fill all the fields"
            }
        }

    }

    private suspend fun handleLogin(email: String, password: String) {
        viewModel.signInWithEmailAndPassword(email,password).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    Log.d("asd", it.data.uid)
                    findNavController().navigate(R.id.action_signinFragment_to_mainFragment)
                }

                is Result.Error -> {
                    binding.txtError.text = it.exception.message
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}