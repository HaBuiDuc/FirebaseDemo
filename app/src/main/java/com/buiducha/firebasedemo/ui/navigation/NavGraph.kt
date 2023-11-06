package com.buiducha.firebasedemo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.buiducha.firebasedemo.ui.screens.add_task_screen.AddTaskScreen
import com.buiducha.firebasedemo.ui.screens.authentication.LoginScreen
import com.buiducha.firebasedemo.ui.screens.authentication.SignupScreen
import com.buiducha.firebasedemo.ui.screens.home_screen.HomeScreen
import com.buiducha.firebasedemo.ui.screens.task_detail_screen.TaskDetailScreen
import com.buiducha.firebasedemo.viewmodel.shared.TaskViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    val taskViewModel: TaskViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(
                taskViewModel = taskViewModel,
                navController = navController
            )
        }

        composable(
            route = Screen.LoginScreen.route
        ) {
            LoginScreen(navController)
        }

        composable(
            route = Screen.SignupScreen.route
        ) {
            SignupScreen(navController)
        }

        composable(
            route = Screen.TaskDetailScreen.route
        ) {
            TaskDetailScreen(
                taskViewModel = taskViewModel,
                navController = navController
            )
        }

        composable(
            route = Screen.AddTaskScreen.route
        ) {
            AddTaskScreen(navController)
        }
    }
}