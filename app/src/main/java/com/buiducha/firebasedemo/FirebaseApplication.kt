package com.buiducha.firebasedemo

import android.app.Application
import android.util.Log
import com.buiducha.firebasedemo.repository.FirebaseRepository
import com.google.firebase.FirebaseApp

class FirebaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("This is a log", "application called")
        FirebaseRepository.initialize(this)
        FirebaseApp.initializeApp(this);
    }
}