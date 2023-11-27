package com.thesua.notex.db

import androidx.room.RoomDatabase

abstract class AppDatabase :RoomDatabase(){

    abstract fun getDao():AppDao
}