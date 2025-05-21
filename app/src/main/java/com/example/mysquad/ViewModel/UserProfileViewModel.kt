package com.example.mysquad.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysquad.data.localRoom.entity.UserProfileEntity
import com.example.mysquad.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<UserProfileEntity?>(null)
    val user: StateFlow<UserProfileEntity?> = _user

    fun loadUserFromRoom(uid: String) {
        viewModelScope.launch {
            userRepository.getLocalUser(uid).collect {
                _user.value = it
            }
        }
    }

    fun updateUser(updatedUser: UserProfileEntity) {
        viewModelScope.launch {
            userRepository.createOrUpdateUser(updatedUser)
            _user.value = updatedUser
        }
    }

    fun syncUserFromRemote(uid: String) {
        viewModelScope.launch {
            val remoteUser = userRepository.remote.fetchUserById(uid)
            if (remoteUser != null) {
                userRepository.createOrUpdateUser(remoteUser)
                _user.value = remoteUser
            }
        }
    }
}