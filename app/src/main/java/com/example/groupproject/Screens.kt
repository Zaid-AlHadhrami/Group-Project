package com.example.groupproject

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth


@Composable
fun home_screen(onLoginClick: () -> Unit, onCreateAccountClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Investment App",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFD700),
            modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
        )

        Spacer(modifier = Modifier.height(90.dp))

        Image(
            painter = painterResource(id = R.drawable.goldimage),
            contentDescription = "Investment Icon",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .size(335.dp)
                .padding(bottom = 32.dp)
        )

        Spacer(modifier = Modifier.height(90.dp))

        Button(
            onClick = onLoginClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700)),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Log in", color = Color.Black, fontSize = 18.sp)
        }

        Button(
            onClick = onCreateAccountClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700)),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Create account", color = Color.Black, fontSize = 18.sp)
        }
    }
}

@Composable
fun LoginScreen(
    onCreateAccountClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    navController: NavController,
    auth: FirebaseAuth,
    signInWithGoogle: () -> Unit) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(35.dp))

        log_in_facebook()
        log_in_google(signInWithGoogle)

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "or",
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(30.dp))

        email(emailState)
        password(passwordState)

        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                auth.signInWithEmailAndPassword(emailState.value, passwordState.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.navigate(Screen.CorrectLogIn.route){
                                popUpTo(Screen.Login.route) {inclusive = true}
                            }
                        } else {
                            Toast.makeText(
                                context, "Password or e-mail incorrect: ${task.exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(text = "Log in with your e-mail", color = Color.Black, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(60.dp))

        // Texto "Don't have an account?"
        Text(
            text = "Don't have an account?",
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(5.dp)
        )


        Button(
            onClick = onCreateAccountClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        ) {
            Text(text = "Create Account", color = Color(0xFFFFD700), fontSize = 18.sp)
        }


        TextButton(
            onClick = { onForgotPasswordClick() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Forgot your password?", color = Color(0xFFFFD700), fontSize = 18.sp)
        }
    }
}

@Composable
fun forgot_password(auth: FirebaseAuth, navController: NavController){
    val emailSent = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    Column (
        modifier = Modifier.fillMaxSize()
            .background(Color.Black)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(75.dp))

        Text(
            text = "Enter your e-mail address to reset your password",
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(75.dp))

        email(emailState)

        Spacer(modifier = Modifier.height(50.dp))

        Button(onClick = {
            if (emailState.value.isNotEmpty()){
                auth.sendPasswordResetEmail(emailState.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            emailSent.value = true
                            errorMessage.value = ""
                        }else{
                            errorMessage.value = task.exception?.message ?: "Error sending email"
                        }
                    }
            }else{
                errorMessage.value = "Please enter your email address"
            }
        },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(vertical = 8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Send", color = Color.Black, fontSize = 18.sp)
        }

        if (emailSent.value){
            Text(text = "Password reset email sent. Check your email",
                color = Color.White)
        }
        if (errorMessage.value.isNotEmpty()){
            Text(text = errorMessage.value, color = Color.Red)
        }
    }
}


@Composable
fun create_account(
    onLoginClick: () -> Unit,
    navController: NavController,
    auth: FirebaseAuth,
    signInWithGoogle: () -> Unit){
    val emailState  = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(35.dp))

        log_in_facebook()
        log_in_google(signInWithGoogle)

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "or",
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(30.dp))

        email(emailState)
        password(passwordState)

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {auth.createUserWithEmailAndPassword(emailState.value, passwordState.value)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        navController.navigate(Screen.CorrectLogIn.route)
                    }else{
                        errorMessage.value = "Error: ${task.exception?.message}"
                    }
                }},
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(text = "Register with your e-mail", color = Color.Black, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(60.dp))

        // Texto "Don't have an account?"
        Text(
            text = "Already have an account?",
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(5.dp)
        )

        Button(
            onClick = onLoginClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            shape = RoundedCornerShape(50),
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 5.dp)
        ){
            Text(text = "Log in", color = Color(0xFFFFD700), fontSize = 18.sp)
        }

    }
}

