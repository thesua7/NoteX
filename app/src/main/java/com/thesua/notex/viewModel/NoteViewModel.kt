package com.thesua.notex.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import com.thesua.notex.model.auth.Result
import com.thesua.notex.model.notes.NoteModel
import com.thesua.notex.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {
    private val _insertResults = MutableLiveData<Result<String>>()
    val insertResult: LiveData<Result<String>>
        get() = _insertResults

    private val _getUpdateResponse = MutableLiveData<Result<String>>()
    val getUpdateResponse: LiveData<Result<String>>
        get() =_getUpdateResponse

    private val _deleteResponse = MutableLiveData<Result<String>>()
    val deleteResponse :LiveData<Result<String>>
        get() = _deleteResponse


    private val _getResults = MutableLiveData<Result<DocumentSnapshot>>()
    val getResults: LiveData<Result<DocumentSnapshot>>
        get() =_getResults
    suspend fun insertNote(title: String,description: String,uid: String) {
        viewModelScope.launch {
            val noteId = UUID.randomUUID().toString()
            val note = NoteModel(noteId,title,description,uid)
            noteRepository.insertIntoFirebase(note) {
                _insertResults.value = it
            }
        }
    }

    suspend fun updateNote(title:String,description:String,id:String){
        viewModelScope.launch {
            noteRepository.updateIntoFirebase(id,title,description,){
                _getUpdateResponse.value = it
            }
        }
    }

    suspend fun deleteNote(uid: String){
        viewModelScope.launch {
            noteRepository.deleteInFirebase(uid){
                _deleteResponse.value = it
            }

        }
    }

    suspend fun getNotes(uid: String){
        viewModelScope.launch {
            noteRepository.getNotesFromFirebase(uid){
                _getResults.value= it
            }
        }
    }
}