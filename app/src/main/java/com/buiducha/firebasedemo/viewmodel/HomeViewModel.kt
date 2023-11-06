package com.buiducha.firebasedemo.viewmodel

import androidx.lifecycle.ViewModel
import com.buiducha.firebasedemo.data.model.TaskItem
import com.buiducha.firebasedemo.repository.FirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val firebaseRepository = FirebaseRepository.get()
    private val _taskList = MutableStateFlow<List<TaskItem>>(emptyList())
    val taskList: StateFlow<List<TaskItem>> get() = _taskList

    fun getTasks() {
        firebaseRepository.getTasks() {
            _taskList.value = it
        }
    }
}