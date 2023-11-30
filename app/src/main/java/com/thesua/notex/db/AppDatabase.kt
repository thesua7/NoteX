package com.thesua.notex.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thesua.notex.model.auth.User
import com.thesua.notex.model.notes.NoteModel


@Database(entities = [NoteModel::class,User::class], version = 2, exportSchema = false)
abstract class AppDatabase :RoomDatabase(){

    abstract fun getDao():AppDao
}