package com.buiducha.firebasedemo.viewmodel.shared

import androidx.lifecycle.ViewModel
import com.buiducha.firebasedemo.data.model.TaskItem
import com.buiducha.firebasedemo.repository.FirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskViewModel : ViewModel() {
    private val firebaseRepository = FirebaseRepository.get()
    private val _taskItem = MutableStateFlow<TaskItem?>(null)
    val taskItem: StateFlow<TaskItem?> get() = _taskItem

    fun setTask(task: TaskItem) {
        _taskItem.value = task
    }

    fun deleteTask() {
        firebaseRepository.deleteTask(_taskItem.value?.id.toString()!!)
    }
}