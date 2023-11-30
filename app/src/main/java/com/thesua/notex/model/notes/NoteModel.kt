package com.thesua.notex.model.notes

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
data class NoteModel(
    @PrimaryKey
    val id: String, val title: String, val description: String, val uid: String
){
    constructor():this("","","","")
}


