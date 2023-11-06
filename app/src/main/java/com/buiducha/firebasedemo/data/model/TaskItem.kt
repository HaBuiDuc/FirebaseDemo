package com.buiducha.firebasedemo.data.model

import java.util.Date
import java.util.UUID

data class TaskItem(
    val id: String = UUID.randomUUID().mostSignificantBits.toString(),
    val userId: String = "",
    val title: String = "",
    val description: String = "",
//    val dueDate: Date,
    val isCompleted: Boolean = false
)
