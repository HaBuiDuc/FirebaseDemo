package com.buiducha.firebasedemo.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.NumberPicker
import android.widget.NumberPicker.OnValueChangeListener
import com.buiducha.firebasedemo.data.model.TaskItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.reflect.typeOf

class FirebaseRepository private constructor(context: Context){
    private var auth: FirebaseAuth = Firebase.auth
    private val database = Firebase.database.getReference("tasks")

    fun authListener() {

    }

    fun getCurrentUser() = auth.currentUser

    fun getTasks(
        onGetTasksListener: (List<TaskItem>) -> Unit
    ) {
        database.orderByChild("userId").equalTo(auth.currentUser?.uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val taskList = mutableListOf<TaskItem>()
                snapshot.children.forEach { shot ->
                    val data = shot.getValue(TaskItem::class.java) as TaskItem
                    taskList += data
                    Log.d(TAG, data.toString())
                }
                onGetTasksListener(taskList)
//                Log.d(TAG, tasks.toString())
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    fun deleteTask(
        taskId: String
    ) {
        Log.d(TAG, "deleteTask: $taskId")
        database.child(taskId).removeValue()
            .addOnSuccessListener {
                Log.d(TAG, "delete successfully")
            }
            .addOnFailureListener {
                Log.d(TAG, "delete failure")
            }
    }

    fun addTask(
        task: TaskItem,
        onItemAddSuccess: () -> Unit
    ) {
        database.push().setValue(task).addOnCompleteListener {
            if (it.isSuccessful) {
                onItemAddSuccess()
            } else if (it.isCanceled) {

            }
        }
    }

    fun userLogin(
        activity: Activity,
        email: String,
        password: String,
        onLoginSuccess: () -> Unit,
        onLoginFailure: (String) -> Unit,
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        if (it.isEmailVerified) {
                            onLoginSuccess()
                            Log.d(TAG, "login successfully")
                        } else {
                            onLoginFailure("Email not verified")
                            Log.d(TAG, "login failure")
                        }
                    }
                    Log.d(TAG, user?.uid.toString())
                } else if (task.isCanceled) {
                    Log.d(TAG, "login failure")
                }
            }
            .addOnFailureListener(activity) { _ ->
                onLoginFailure("Login failure")
                Log.d(TAG, "login failure")
            }
    }

    fun createUser(
        activity: Activity,
        email: String,
        password: String,
        onSuccessful: () -> Unit,
        onFailure: () -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    onSuccessful()
                    val user = auth.currentUser
                    auth.addAuthStateListener {firebaseAuth ->
                        val currentUser = firebaseAuth.currentUser
                        if (currentUser != null && currentUser.isEmailVerified) {
                            Log.d(TAG, "email verified")
                        }
                    }
                    user?.sendEmailVerification()
                    Log.d(TAG, "create user successfully")
                } else if (task.isCanceled) {
                    Log.d(TAG, "create user failure")
                }
            }
            .addOnFailureListener {
                onFailure()
                Log.d(TAG, "create failure")
            }

    }

    companion object {
        const val TAG = "FirebaseRepository"
        private var INSTANCE: FirebaseRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = FirebaseRepository(context)
            }
        }

        fun get(): FirebaseRepository {
            return INSTANCE?: throw IllegalStateException("repo must be init")
        }
    }
}