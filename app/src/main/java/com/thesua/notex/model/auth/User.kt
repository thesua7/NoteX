package com.thesua.notex.model.auth

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val uid:String,
    val email:String
)
