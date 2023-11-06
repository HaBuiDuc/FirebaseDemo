package com.buiducha.firebasedemo.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.buiducha.firebasedemo.repository.FirebaseRepository

class LoginViewModel : ViewModel() {
    private val firebaseRepository = FirebaseRepository.get()

    fun userLogin(
        activity: Activity,
        email: String,
        password: String,
        onLoginSuccess: () -> Unit,
        onLoginFailure: (String) -> Unit
    ) {
        firebaseRepository.userLogin(
            activity = activity,
            email = email,
            password = password,
            onLoginSuccess = onLoginSuccess,
            onLoginFailure = onLoginFailure
        )
    }
}