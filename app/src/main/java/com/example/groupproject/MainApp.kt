package com.example.groupproject

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth


@Composable
fun MainApp(auth: FirebaseAuth, googleSignInClient: GoogleSignInClient, signInWithGoogle: () -> Unit) {
    val navController = rememberNavController()
    val isUserLoggedIn = remember { mutableStateOf(false) } // Estado de autenticación

    Scaffold(
        topBar = {
            // Mostrar la TopNavigationBar solo en las pantallas de Login, Register y ForgotPassword
            val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentScreen in listOf(Screen.Login.route, Screen.CreateAccount.route, Screen.Password.route)) {
                val title = when (currentScreen) {
                    Screen.Login.route -> "Login"
                    Screen.CreateAccount.route -> "Register"
                    Screen.Password.route -> "Forgot Password"
                    else -> ""
                }
                TopNavigationBar(onBackClick = { navController.popBackStack() }, title = title)
            }
        },
        bottomBar = {
            if (isUserLoggedIn.value) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding -> // `innerPadding` para manejar el padding del Scaffold
        NavHost(
            navController = navController,
            startDestination = if (isUserLoggedIn.value) Screen.CorrectLogIn.route else Screen.Home.route, // Elige la ruta inicial dependiendo del estado de autenticación
            modifier = Modifier.padding(innerPadding) // Aplica el padding del Scaffold
        ) {
            composable(Screen.Home.route){
                home_screen(onLoginClick = {navController.navigate(Screen.Login.route)},
                    onCreateAccountClick = {navController.navigate(Screen.CreateAccount.route)})
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    navController = navController,
                    auth = auth,
                    signInWithGoogle = signInWithGoogle,
                    onCreateAccountClick = { navController.navigate(Screen.CreateAccount.route) },
                    onForgotPasswordClick = { navController.navigate(Screen.Password.route) }
                )
            }
            composable(Screen.CreateAccount.route) {
                create_account(
                    onLoginClick = { navController.navigate(Screen.Login.route) },
                    navController = navController,
                    auth = auth,
                    signInWithGoogle = signInWithGoogle)
            }
            composable(Screen.Password.route) {
                forgot_password()
            }
            composable(Screen.CorrectLogIn.route) {
                Correct_Log_In_Screen(navController = navController)
                isUserLoggedIn.value = true // Actualiza el estado a "iniciado sesión"
            }
            composable(Screen.Crypto.route) {
                crypto()
            }
            composable(Screen.Portfolio.route) {
                portfolio()
            }
            composable(Screen.Assets.route) {
                assests()
            }
        }
    }
}