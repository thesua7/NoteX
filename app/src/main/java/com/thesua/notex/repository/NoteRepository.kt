package com.thesua.notex.repository

import android.icu.text.CaseMap.Title
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.thesua.notex.db.AppDao
import com.thesua.notex.model.auth.Result
import com.thesua.notex.model.notes.NoteModel
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val appDao: AppDao, private val firebaseFirestore: FirebaseFirestore
) {
    suspend fun insertNoteDB(note: NoteModel) {
        appDao.insertNoteDB(note)
    }

    fun getAllNotesFromDB(): List<NoteModel> {
        return appDao.getAllNotes()
    }

    suspend fun insertIntoFirebase(note: NoteModel, onResult: (Result<String>) -> Unit) {
        try {
            onResult(Result.Loading)
            firebaseFirestore.collection("notes").document(note.uid).set(note).await()
            insertNoteDB(note)
            onResult(Result.Success("Success"))

        } catch (e: Exception) {
            onResult(Result.Error(e))
        }

    }

    suspend fun getNotesFromFirebase(uid: String, onResult: (Result<DocumentSnapshot>) -> Unit) {
        try {
            onResult(Result.Loading)
            val result = firebaseFirestore.collection("notes").document(uid).get().await()
//                firebaseFirestore.collection("notes").get().await().toObjects(NoteModel::class.java)
            onResult(Result.Success(result))
        } catch (e: Exception) {
            onResult(Result.Error(e))
        }
    }

    suspend fun updateIntoFirebase(
        uid: String, title: String, description: String, onResult: (Result<String>) -> Unit
    ) {
        try {
            onResult(Result.Loading)
            val updates = mapOf(
                "title" to title,
                "description" to description,
                // Add other fields to update as needed
            )
            firebaseFirestore.collection("notes").document(uid).update(updates).await()
            onResult(Result.Success("Success"))
        } catch (e: Exception) {
            onResult(Result.Error(e))
        }

    }

    suspend fun deleteInFirebase(uid: String, onResult: (Result<String>) -> Unit) {
        try {
            onResult(Result.Loading)
            firebaseFirestore.collection("notes").document(uid).delete().await()
            onResult(Result.Success("Deleted"))


        } catch (e:Exception){
            onResult(Result.Error(e))
        }

    }
}