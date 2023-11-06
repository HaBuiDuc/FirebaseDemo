package com.buiducha.firebasedemo.ui.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object SignupScreen : Screen("signup_screen")
    object HomeScreen : Screen("home_screen")
    object TaskDetailScreen : Screen("task_detail_screen")
    object AddTaskScreen : Screen("add_task_screen")
}
