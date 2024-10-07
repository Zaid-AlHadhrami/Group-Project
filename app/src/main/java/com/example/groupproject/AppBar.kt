package com.example.groupproject
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(onBackClick: () -> Unit, title: String) {
    TopAppBar(
        modifier = Modifier.background(Color.Black),
        title = {
            Text(
                text = title,
                color = Color.Black // Cambia el color del texto a blanco
            )
        },
        // Cambia el fondo a gris
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.flecha),
                contentDescription = "Back Arrow",
                modifier = Modifier.clickable(onClick = onBackClick)
                    .width(50.dp)
                    .height(30.dp)
            )
        }
    )
}

@Composable
fun TopNavigationBar2(){

}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        Row{
            Column{
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.CorrectLogIn.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
                Text("Home", color = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))

            Column{
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Crypto",
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Crypto.route){
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}