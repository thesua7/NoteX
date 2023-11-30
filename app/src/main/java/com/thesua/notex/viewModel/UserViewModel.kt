package com.thesua.notex.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.thesua.notex.model.auth.Result
import com.thesua.notex.model.auth.User
import com.thesua.notex.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _signInResult = MutableLiveData<Result<User>>()
    val signInResult: LiveData<Result<User>> get() = _signInResult


    private val _signUpResult = MutableLiveData<Result<User>>()
    val signUpResult: LiveData<Result<User>> get() = _signUpResult


//    private val _currentUserUid = MutableLiveData<String?>()
//    val currentUserUid: LiveData<String?> get() = _currentUserUid
    suspend fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            userRepository.signInWithEmailAndPassword(email, password) { result ->
                _signInResult.value = result

            }
        }
    }


    suspend fun signUpWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            userRepository.signUpWithEmailAndPassword(email, password) { result ->
                _signUpResult.value = result
            }

        }

    }

    fun signOut(): LiveData<Result<Unit>> {
        val resultLiveData = MutableLiveData<Result<Unit>>()
        viewModelScope.launch {
            val result = userRepository.signOut()
            resultLiveData.value = result
        }
        return resultLiveData
    }


    fun isAuthenticated(): Boolean {
        return userRepository.isAuthenticated()
    }

    fun getCurrentUserUid(): String {
        if (userRepository.getCurrentUser()==null){
            return ""
        }
        return userRepository.getCurrentUser()!!.uid
    }
}