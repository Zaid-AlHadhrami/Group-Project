package com.example.groupproject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun Correct_Log_In_Screen(navController: NavHostController) {
    Column(
        Modifier.fillMaxSize().
        background(Color.Gray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier.width(300.dp).height(500.dp)
                .background(Color.Black)
                .clip(RoundedCornerShape(16.dp))
        ){
            Column(
                Modifier.fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Total Assests", color = Color.White)

                Text(text = "$150,257.98", color = Color.White)

                Spacer(modifier = Modifier.height(60.dp))

                Text("Distribution", color = Color.White)

                Row(

                ) {
                    Text("Stocks", color = Color.White)
                    Spacer(modifier = Modifier.width(25.dp))
                    Text("$85,250.90", color = Color.White)
                }

                Row(

                ) {
                    Text("Crypto", color = Color.White)
                    Spacer(modifier = Modifier.width(25.dp))
                    Text("62,054.27", color = Color.White)
                }
            }
        }
    }
}



@Composable
fun assests() {
    Text("This is the assets screen")
}

@Composable
fun crypto() {
        Text("This is the crypto screen")
}
@Composable
fun portfolio(){
    Text("This is the portfolio screen")
}
