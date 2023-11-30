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
import androidx.lifecycle.Observer
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
        binding.addNote.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_noteFragment)
        }

        if (viewModel.isAuthenticated()) {
            token = viewModel.getCurrentUserUid()

            lifecycleScope.launch {
                bindObserver()
                noteViewModel.getNotes(token)
            }

        }
//        val noteId = UUID.randomUUID().toString()
//        val nodeModel = sharedPreferences.getString("uid", "")
//            ?.let { NoteModel(noteId, "Test", "TEstDescc", it) }
//        Log.d("notxx", nodeModel.toString())

//        lifecycleScope.launch {
//
//            Log.d("IsAuthenticated", viewModel.isAuthenticated().toString())
//            viewModel.getCurrentUserUid()?.let { Log.d("CurrentUserx", it.uid) }
//
//
//
//            if (nodeModel != null) {
//                noteViewModel.insertNote(nodeModel)
//                noteViewModel.insertResult.observe(viewLifecycleOwner, Observer {
//                    when (it) {
//                        is Result.Success -> {
//                            Log.d("xxSuccess1", it.data.toString())
//
//                        }
//
//                        is Result.Error -> {
//                            Log.d("xxSuccessE", it.exception.message.toString())
//
//                        }
//
//                        is Result.Loading -> {
//
//                            Log.d("xxSuccess", "Loading")
//
//
//                        }
//                    }
//                })
//            }
//
//
//            viewModel.getCurrentUserUid()?.let { noteViewModel.getNotes(it.uid) }
//
//            noteViewModel.getResults.observe(viewLifecycleOwner, Observer {
//                when (it) {
//                    is Result.Success -> {
//                        val resultData = it.data.toObject(NoteModel::class.java)
//                        Log.d("ySuccess", resultData.toString())
//                    }
//
//                    is Result.Error -> {
//                        Log.d("yError", it.exception.message.toString())
//                    }
//
//                    is Result.Loading -> {
//                        Log.d("yLoading", "it.toString()")
//                    }
//                }
//            })
//
//            viewModel.getCurrentUserUid()?.let { noteViewModel.updateNote("heda", "heda", it.uid) }
//            noteViewModel.getUpdateResponse.observe(viewLifecycleOwner, Observer {
//                when (it) {
//                    is Result.Success -> {
//                        Log.d("zzSuccess", it.data.toString())
//
//                    }
//
//                    is Result.Error -> {
//                        Log.d("zzSuccess", it.exception.message.toString())
//
//                    }
//
//                    is Result.Loading -> {
//
//                        Log.d("zzSuccess", "Loading")
//
//
//                    }
//                }
//            })
//        }


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

    private fun bindObserver() {
        noteViewModel.getResults.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is Result.Success -> {

                    val list = mutableListOf<NoteModel>()

                    val documents = it.data.toObject(NoteModel::class.java)
                    if (documents != null) {
                        list.add(documents)
                    }

                    noteAdapter.submitList(list)

                    Log.d("list",list.toString())

//                    it.data.data?.forEach { item ->
//                        Log.d("xxxx", ite)
//
//                    }



                }

                is Result.Error -> {
                    Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    private fun onNoteClicked(noteModel: NoteModel){

        val bundle = Bundle()
        bundle.putSerializable("note", noteModel)
        findNavController().navigate(R.id.action_mainFragment_to_noteFragment,bundle)

        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}