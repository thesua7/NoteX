package com.thesua.notex.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.thesua.notex.db.AppDao
import com.thesua.notex.model.auth.FirebaseUser
import com.thesua.notex.model.auth.UserModel
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val appDao: AppDao
) {
    suspend fun signIn(email:String,password:String){
        firebaseAuth.signInWithEmailAndPassword(email,password)
    }

    suspend fun signUp(email: String,password: String){
        firebaseAuth.createUserWithEmailAndPassword(email,password)
    }

    fun signOut(){
        firebaseAuth.signOut()
    }

    fun observeAuthState():LiveData<FirebaseUser?>{
        val userLiveData = MutableLiveData<FirebaseUser?>()
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            userLiveData.value = firebaseUser?.let {
                FirebaseUser(it.uid ,it.displayName?:"",it.email?:" ")
            }
        }

        return userLiveData
    }

    fun observeLocalUser():LiveData<List<UserModel>>{
        return appDao.observerUser()
    }

    suspend fun saveUserLocally(data: UserModel){
        appDao.insertUser(data)
    }


}