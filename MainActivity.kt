package com.example.task11 // Change to your package name

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Define the Navigation Graph
    NavHost(navController = navController, startDestination = "home_screen") {

        // SCREEN 1: Home
        composable("home_screen") {
            HomeScreen(navController)
        }

        // SCREEN 2: Input Numbers (Requires 'name' argument)
        composable(
            route = "second_screen/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: "Guest"
            SecondScreen(navController, name)
        }

        // SCREEN 3: Display Result (Requires 'name', 'num1', 'num2')
        composable(
            route = "third_screen/{name}/{num1}/{num2}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("num1") { type = NavType.IntType },
                navArgument("num2") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: "Guest"
            val num1 = backStackEntry.arguments?.getInt("num1") ?: 0
            val num2 = backStackEntry.arguments?.getInt("num2") ?: 0
            ThirdScreen(navController, name, num1, num2)
        }
    }
}

// --- SCREEN 1: HOME ---
@Composable
fun HomeScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Step 1: Enter Name", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Your Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (name.isNotBlank()) {
                    // Navigate to Screen 2, passing the name
                    navController.navigate("second_screen/$name")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}

// --- SCREEN 2: NUMBERS ---
@Composable
fun SecondScreen(navController: NavController, name: String) {
    var num1Str by remember { mutableStateOf("") }
    var num2Str by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hello, $name!", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
        Text("Step 2: Enter Numbers", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = num1Str,
            onValueChange = { num1Str = it },
            label = { Text("Number 1") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = num2Str,
            onValueChange = { num2Str = it },
            label = { Text("Number 2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Next Button
        Button(
            onClick = {
                val n1 = num1Str.toIntOrNull() ?: 0
                val n2 = num2Str.toIntOrNull() ?: 0
                // Navigate to Screen 3, passing name and numbers
                navController.navigate("third_screen/$name/$n1/$n2")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Back Button
        OutlinedButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}

// --- SCREEN 3: RESULT ---
@Composable
fun ThirdScreen(navController: NavController, name: String, num1: Int, num2: Int) {
    val sum = num1 + num2

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Final Result", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Name: $name", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("$num1 + $num2 = $sum", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Go Home Button (Clears backstack to start)
        Button(
            onClick = {
                // Pop everything up to "home_screen" (inclusive = false means keep home_screen)
                navController.popBackStack("home_screen", inclusive = false)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go Home")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Back Button (Goes back to Screen 2)
        OutlinedButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}