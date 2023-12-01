package com.thesua.notex

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.thesua.notex.databinding.FragmentMainBinding
import com.thesua.notex.model.auth.Result
import com.thesua.notex.model.notes.NoteModel
import com.thesua.notex.viewModel.NoteViewModel
import com.thesua.notex.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class mainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()
    private val noteViewModel: NoteViewModel by viewModels()

    private lateinit var noteAdapter: NoteAdapter



    private var token: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        noteAdapter = NoteAdapter(::onNoteClicked)


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.noteList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.noteList.adapter =  noteAdapter
        token = viewModel.getCurrentUserUid()
        Log.d("asd",token)
        binding.addNote.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_noteFragment)
        }



        if (viewModel.isAuthenticated()) {
            lifecycleScope.launch {
                bindObserver()
                noteViewModel.getNotes(token)
            }

            binding.logoutTv.setOnClickListener{
                viewModel.signOut().observe(viewLifecycleOwner) {
                    when (it) {
                        is Result.Success -> {
                            findNavController().navigate(R.id.signupFragment)
                        }

                        is Result.Error -> {

                        }
                        is Result.Loading ->{

                        }
                    }
                }
            }

        }


    }

    private fun bindObserver() {
        noteViewModel.getResults.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            when (it) {
                is Result.Success -> {

                    val list = mutableListOf<NoteModel>()

                    val documents = it.data.documents

                    for (item in documents) {
                        val temp = item.toObject(NoteModel::class.java)
                        if (temp != null) {
                            list.add(temp)
                        }
                    }

                    noteAdapter.submitList(list)


//                    it.data.data?.forEach { item ->
//                        Log.d("xxxx", ite)
//
//                    }


                }

                is Result.Error -> {

                    Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }

                is Result.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        }
    }

    private fun onNoteClicked(noteModel: NoteModel){

        val bundle = Bundle()
        bundle.putSerializable("note", noteModel)
        findNavController().navigate(R.id.action_mainFragment_to_noteFragment,bundle)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}