package com.thesua.notex.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thesua.notex.model.auth.User
import com.thesua.notex.model.notes.NoteModel

@Dao
interface AppDao {
    @Query("SELECT * FROM user WHERE uid = :uid")
    suspend fun getUser(uid: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteDB(note: NoteModel)


    @Query("Select * from notes")
    fun getAllNotes(): List<NoteModel>
}