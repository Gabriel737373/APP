package com.example.testing

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import com.google.accompanist.navigation.animation.AnimatedNavHost
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import java.util.Calendar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.launch
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testing.ui.theme.BotonesGen
import com.example.testing.ui.theme.TestingTheme
import com.example.testing.ui.theme.textoGeneral
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.launch
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            TestingTheme {
                MyAppNavigation()
        }
    }
}
@Composable//texto de bienvenida
fun TextoBienvenida(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Bienvenido a \n$name",
            color = textoGeneral,
            modifier = modifier.padding(10.dp)
        )
    }
@Composable//botones
fun AppButton(buttonText: String, modifier: Modifier = Modifier, onClickAction: () -> Unit) {
    Button(
        onClick = onClickAction,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = BotonesGen,
        )
    ){
        Text(
            text = buttonText,
            color = textoGeneral,
        )
    }
}
@OptIn(ExperimentalAnimationApi::class)
@Composable//Pantallas
fun MyAppNavigation() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination ="home",
        enterTransition = {
            slideInHorizontally(initialOffsetX = { -it })
                          },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -it / 3 })
                         },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it / 3 })
                             },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { it })
        }

    ) {
        composable(route = "home"){
            HomeScreen(navController)
        }
        composable(route = "second"){
            SecondScreen(navController)
        }
        composable(route = "third") {
            ThirdScreen(navController)
        }
        composable(route = "fourth") {
            FourthScreen(navController)
        }
    }
}
    @Composable
    fun HomeScreen(navController: NavController){
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        Scaffold (
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) {
            innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TextoBienvenida(
                    name = "Level-UP",
                )
                AppButton(
                    buttonText = "Iniciar sesion",
                    onClickAction = {
                        navController.navigate("second")
                    }
                )
                AppButton(
                    buttonText = "Crear cuenta",
                    onClickAction = {
                        navController.navigate("third")
                    }
                )
            }
        }
    }
    @Composable//Inicio de sesion
    fun SecondScreen(navController: NavController){
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val dataStore = remember { UserDataStore(context) }
        val savedEmail by dataStore.emailFlow.collectAsState(initial = null)
        val savedPassword by dataStore.passwordFlow.collectAsState(initial = null)
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Iniciar Sesión",
                    style = MaterialTheme.typography.headlineMedium,
                    color = textoGeneral,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.VisibilityOff
                        else
                            Icons.Filled.Visibility
                        val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = description)
                        }
                    }
                )
                AppButton(
                    buttonText = "Ingresar",
                    onClickAction = {
                        if (email.isNotBlank() && password.isNotBlank()) {
                            if (email == savedEmail && password == savedPassword) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("¡Inicio de sesión exitoso!")
                                    navController.navigate("fourth") { // Navegamos a la 4ta pantalla
                                        popUpTo("home") { inclusive = true }
                                    }
                                }
                            } else {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Email o contraseña incorrectos")
                                }
                            }
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar("Por favor, introduce email y contraseña")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Volver atrás")
                }
            }
        }
    }
    @Composable//Creacion de cuenta
    fun ThirdScreen(navController: NavController){

        var username by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val dataStore = remember { UserDataStore(context) }
        var birthDate by remember { mutableStateOf("") }
        var showDatePicker by remember { mutableStateOf(false) }

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Crear Nueva Cuenta",
                    style = MaterialTheme.typography.headlineMedium,
                    color = textoGeneral,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Nombre de usuario") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = birthDate,
                    onValueChange = {}, // Vacío para que no se pueda escribir
                    label = { Text("Fecha de nacimiento (DD/MM/AAAA)") },
                    readOnly = true, // Hacemos que sea de solo lectura
                    trailingIcon = { // Añadimos un icono de calendario
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Seleccionar fecha",
                            modifier = Modifier.clickable { showDatePicker = true }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable { showDatePicker = true }
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.VisibilityOff
                        else
                            Icons.Filled.Visibility
                        val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = description)
                        }
                    }
                )
                AppButton(
                    buttonText = "Registrarse",
                    onClickAction = {
                        if (username.isBlank() || email.isBlank() || password.isBlank() || birthDate.isBlank()) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Por favor, rellena todos los campos")
                            }
                            return@AppButton
                        }
                        val parts = birthDate.split("/")
                        val day = parts[0].toInt()
                        val month = parts[1].toInt()
                        val year = parts[2].toInt()
                        val birthCalendar = Calendar.getInstance().apply { set(year, month - 1, day) }
                        val today = Calendar.getInstance()
                        var age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)
                        if (today.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
                            age--
                        }
                        if (age < 18) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Debes ser mayor de 18 años para crear una cuenta")
                            }
                        } else {
                            scope.launch {
                                dataStore.saveUserAccount(username, email, password)
                                snackbarHostState.showSnackbar("¡Cuenta creada correctamente!")
                                navController.navigate("home") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Volver atrás")
                }
            }
        }
        if (showDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    Button(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selectedDate = Calendar.getInstance().apply { timeInMillis = millis }
                            val day = selectedDate.get(Calendar.DAY_OF_MONTH)
                            val month = selectedDate.get(Calendar.MONTH) + 1
                            val year = selectedDate.get(Calendar.YEAR)
                            birthDate = String.format("%02d/%02d/%d", day, month, year)
                        }
                        showDatePicker = false
                    }) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDatePicker = false }) {
                        Text("Cancelar")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
    @Composable//Ventana Principal
    fun FourthScreen(navController: NavController){
        //tengo que añadir cosas de la cuarta pantalla
    }
}