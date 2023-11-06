package com.buiducha.firebasedemo.ui.screens.home_screen

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.buiducha.firebasedemo.data.model.TaskItem
import com.buiducha.firebasedemo.ui.navigation.Screen
import com.buiducha.firebasedemo.viewmodel.HomeViewModel
import com.buiducha.firebasedemo.viewmodel.shared.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    taskViewModel: TaskViewModel,
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel()
) {
    homeViewModel.getTasks()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddTaskScreen.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
            }
        },
        topBar = {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        homeViewModel.userLogout()
                        navController.popBackStack()
                        navController.navigate(Screen.LoginScreen.route)
                        Log.d("This is a log", "sign out: ")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Logout,
                        contentDescription = null
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Task list",
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(16.dp)
                    .align(CenterHorizontally)
            )
            LazyColumn {
                items(homeViewModel.taskList.value) {task ->
                    TaskItemView(
                        taskItem = task,
                        onItemSelected = {
                            taskViewModel.setTask(task)
                            navController.navigate(Screen.TaskDetailScreen.route)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItemView(
    taskItem: TaskItem,
    onItemSelected: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemSelected()
            }
            .border(
                width = 0.4.dp,
                color = Color.Gray,

                )
            .padding(
                vertical = 8.dp,
                horizontal = 4.dp
            )
    ) {
        Text(text = taskItem.title)
        if (taskItem.isCompleted) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = Color.Green
            )
        }
    }
}