package com.example.mysquad.ViewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mysquad.ViewModel.UserProfileViewModel
import com.example.mysquad.data.repository.UserRepository

class UserProfileViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            return UserProfileViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}