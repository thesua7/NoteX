package com.thesua.notex

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.thesua.notex.databinding.FragmentMainBinding
import com.thesua.notex.model.auth.Result
import com.thesua.notex.model.notes.NoteModel
import com.thesua.notex.viewModel.NoteViewModel
import com.thesua.notex.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID

@AndroidEntryPoint
class mainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()
    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        sharedPreferences = requireActivity().getSharedPreferences("W", Context.MODE_PRIVATE)
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteId = UUID.randomUUID().toString()
        val nodeModel = sharedPreferences.getString("uid", "")
            ?.let { NoteModel(noteId, "Test", "TEstDescc", it) }
        Log.d("notxx", nodeModel.toString())

        lifecycleScope.launch {

            Log.d("IsAuthenticated", viewModel.isAuthenticated().toString())
            viewModel.getCurrentUserUid()?.let { Log.d("CurrentUserx", it.uid) }



            if (nodeModel != null) {
                noteViewModel.insertNote(nodeModel)
                noteViewModel.insertResult.observe(viewLifecycleOwner, Observer {
                    when (it) {
                        is Result.Success -> {
                            Log.d("xxSuccess1",it.data.toString())

                        }

                        is Result.Error -> {
                            Log.d("xxSuccessE",it.exception.message.toString())

                        }

                        is Result.Loading -> {

                            Log.d("xxSuccess","Loading")


                        }
                    }
                })
            }


            viewModel.getCurrentUserUid()?.let { noteViewModel.getNotes(it.uid) }

            noteViewModel.getResults.observe(viewLifecycleOwner, Observer {
                when(it){
                    is Result.Success->{
                        val resultData = it.data.toObject(NoteModel::class.java)
                        Log.d("ySuccess",resultData.toString())
                    }
                    is Result.Error->{
                        Log.d("yError",it.exception.message.toString())
                    }
                    is Result.Loading->{
                        Log.d("yLoading","it.toString()")
                    }
                }
            })

            viewModel.getCurrentUserUid()?.let { noteViewModel.updateNote("heda","heda", it.uid) }
            noteViewModel.getUpdateResponse.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Result.Success -> {
                        Log.d("zzSuccess",it.data.toString())

                    }

                    is Result.Error -> {
                        Log.d("zzSuccess",it.exception.message.toString())

                    }

                    is Result.Loading -> {

                        Log.d("zzSuccess","Loading")


                    }
                }
            })
        }


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