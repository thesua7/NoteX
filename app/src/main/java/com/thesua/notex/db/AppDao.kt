package com.thesua.notex.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thesua.notex.model.auth.UserModel

@Dao
interface AppDao {
    @Query("select * from user")
    fun observerUser():LiveData<List<UserModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(data:UserModel)
}