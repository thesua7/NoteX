package com.thesua.notex.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thesua.notex.model.auth.User


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase :RoomDatabase(){

    abstract fun getDao():AppDao
}