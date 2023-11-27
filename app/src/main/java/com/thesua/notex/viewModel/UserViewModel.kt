package com.thesua.notex.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesua.notex.model.auth.FirebaseUser
import com.thesua.notex.model.auth.UserModel
import com.thesua.notex.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    val authState : LiveData<FirebaseUser?> = userRepository.observeAuthState()
    val localUsers:LiveData<List<UserModel>> = userRepository.observeLocalUser()

    fun signIn(email:String,password:String){
        viewModelScope.launch {
            try {
                userRepository.signIn(email,password)
            }catch (e:Exception){

            }
        }
    }

    fun signUp(email: String,password: String){
        viewModelScope.launch {
            try {
                userRepository.signUp(email,password)
            }catch (e:Exception){

            }
        }
    }

    fun saveUserDataLocally(data:UserModel){
        viewModelScope.launch(Dispatchers.IO){
            try {
                userRepository.saveUserLocally(data)
            }catch (e:Exception){

            }
        }
    }
}