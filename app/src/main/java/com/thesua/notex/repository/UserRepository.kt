package com.thesua.notex.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.thesua.notex.db.AppDao
import com.thesua.notex.model.auth.Result
import com.thesua.notex.model.auth.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val appDao: AppDao
) {
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<User> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = User(authResult.user?.uid.orEmpty(), authResult.user?.email.orEmpty())
            appDao.insertUser(user)
            Result.Success(user)

        } catch (e: Exception) {
            Result.Error(e)
        }
    }



    suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<User> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = User(authResult.user?.uid.orEmpty(), authResult.user?.email.orEmpty())
            appDao.insertUser(user)
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }


    fun signOut(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}