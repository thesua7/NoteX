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
import com.thesua.notex.databinding.FragmentSignupBinding
import com.thesua.notex.model.auth.Result
import com.thesua.notex.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class signupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignupBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            if (binding.txtEmail.text.isNotEmpty() && binding.txtPassword.text.isNotEmpty()) {
                lifecycleScope.launch {
                    Log.d("Threads",Thread.currentThread().name)
                    handleRegister(binding.txtEmail.text.toString(),binding.txtPassword.text.toString())
                }

            } else {
                binding.txtError.text = "Fill all the fields"
                return@setOnClickListener
            }

        }

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_signinFragment)
        }
    }

    private suspend fun handleRegister(email:String,password:String) {
        viewModel.signUpWithEmailAndPassword(email,password).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    Log.d("heda", it.data.uid + " " + it.data.email)
                    findNavController().navigate(R.id.action_signupFragment_to_mainFragment)


                }

                is Result.Error -> {
                    binding.txtError.text = it.exception.message
                }
            }
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}