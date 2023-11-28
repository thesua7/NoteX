package com.thesua.notex.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesua.notex.model.auth.Result
import com.thesua.notex.model.auth.User
import com.thesua.notex.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    suspend fun signInWithEmailAndPassword(
        email: String, password: String
    ): LiveData<Result<User>> {
        val resultLiveData = MutableLiveData<Result<User>>()
        viewModelScope.launch {
            val result = userRepository.signInWithEmailAndPassword(email, password)
            resultLiveData.value = result
        }
        return resultLiveData
    }

    suspend fun signUpWithEmailAndPassword(
        email: String, password: String
    ): LiveData<Result<User>> {
        val resultLiveData = MutableLiveData<Result<User>>()
        viewModelScope.launch {
            Log.d("Threads",Thread.currentThread().name)
            val result = userRepository.signUpWithEmailAndPassword(email, password)
            resultLiveData.value = result
        }
        return resultLiveData
    }

    fun signOut(): LiveData<Result<Unit>> {
        val resultLiveData = MutableLiveData<Result<Unit>>()
        viewModelScope.launch {
            val result = userRepository.signOut()
            resultLiveData.value = result
        }
        return resultLiveData
    }
}