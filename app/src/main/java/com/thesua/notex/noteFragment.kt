package com.thesua.notex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.thesua.notex.databinding.FragmentNoteBinding
import com.thesua.notex.model.auth.Result
import com.thesua.notex.model.notes.NoteModel
import com.thesua.notex.viewModel.NoteViewModel
import com.thesua.notex.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID


@AndroidEntryPoint
class noteFragment : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private var note:NoteModel? = null

    private val viewModel:UserViewModel by viewModels()
    private val noteViewModel:NoteViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNoteBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitializeData()
        bindHandlers()
        bindObserver()
    }

    private fun bindHandlers() {

        binding.btnSubmit.setOnClickListener{

            lifecycleScope.launch {
                if (note==null){
                    noteViewModel.insertNote(binding.txtTitle.text.toString(),binding.txtDescription.text.toString(),viewModel.getCurrentUserUid())
                } else{
                    noteViewModel.updateNote(binding.txtTitle.text.toString(),binding.txtDescription.text.toString(),viewModel.getCurrentUserUid())

                }
            }
        }
    }

    private fun bindObserver() {

        if (note!=null){
            noteViewModel.getUpdateResponse.observe(viewLifecycleOwner, Observer {
                when(it){
                    is Result.Success->{
                        Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Error->{
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                    }
                    is Result.Loading ->{

                    }
                }
            })
        }else{
            noteViewModel.insertResult.observe(viewLifecycleOwner, Observer {
                when (it){
                    is Result.Success ->{
                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()

                    }
                    is Result.Error->{
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                    }
                    is Result.Loading ->{

                    }
                }
            })
        }


    }

    private fun setInitializeData(){
        val temp= arguments?.getSerializable("note") as? NoteModel
        if (temp!=null){
            note = temp

            note?.let {
                binding.txtTitle.setText(it.title)
                binding.txtDescription.setText(it.description)
            }

        }else{
            binding.addEditText.text = "Add Note"
        }
    }



}