package com.buiducha.firebasedemo.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.buiducha.firebasedemo.repository.FirebaseRepository

class SignupViewModel : ViewModel() {
    private val firebaseRepository = FirebaseRepository.get()

    fun createUser(
        activity: Activity,
        email: String,
        password: String,
        onCreateSuccess: () -> Unit,
        onCreateFailure: () -> Unit
    ) {
        firebaseRepository.createUser(
            activity = activity,
            email = email,
            password = password,
            onSuccessful = onCreateSuccess,
            onFailure = onCreateFailure

        )
    }
}