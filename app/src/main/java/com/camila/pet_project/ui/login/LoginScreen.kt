package com.camila.pet_project.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.camila.pet_project.R
import com.camila.pet_project.ui.navigation.NavigationEvent

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            when (event) {
                is NavigationEvent.NavigateToPetList -> {
                    navController.navigate("petList/${event.userId}") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }
        }
    }

    LoginContent(
        userName = uiState.userName,
        password = uiState.password,
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        isRegisterMode = uiState.isRegisterMode,
        isValid = uiState.isValid,
        onUserNameChanged = viewModel::updateUserName,
        onPasswordChanged = viewModel::updatePassword,
        onLoginClick = viewModel::login,
        onRegisterClick = viewModel::register,
        onToggleMode = viewModel::toggleMode,
        onClearError = viewModel::clearError
    )
}

@Composable
private fun LoginContent(
    userName: String,
    password: String,
    isLoading: Boolean,
    errorMessage: String?,
    isRegisterMode: Boolean,
    isValid: Boolean,
    onUserNameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onToggleMode: () -> Unit,
    onClearError: () -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Logo
            Image(
                painter = painterResource(id = R.drawable.pawprint),
                contentDescription = "Pet Passport Logo",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
            )

            // Title
            Text(
                text = if (isRegisterMode) "Create Account" else "Welcome Back",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (isRegisterMode) "Register to manage your pets" else "Login to continue",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Username Field
            OutlinedTextField(
                value = userName,
                onValueChange = onUserNameChanged,
                enabled = !isLoading,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text("Username") },
                singleLine = true,
                isError = errorMessage != null,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Gray,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChanged,
                enabled = !isLoading,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = { Text("Password") },
                singleLine = true,
                isError = errorMessage != null,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Gray,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Error Message
            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Primary Action Button (Login or Register)
            Button(
                onClick = {
                    if (isRegisterMode) {
                        onRegisterClick()
                    } else {
                        onLoginClick()
                    }
                },
                enabled = isValid && !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Black,
                    disabledContainerColor = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = if (isRegisterMode) "Register" else "Login",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Toggle Mode Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isRegisterMode)
                        "Already have an account?"
                    else
                        "Don't have an account?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                TextButton(
                    onClick = onToggleMode,
                    enabled = !isLoading
                ) {
                    Text(
                        text = if (isRegisterMode) "Login" else "Register",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginContent(
        userName = "",
        password = "",
        isLoading = false,
        errorMessage = null,
        isRegisterMode = false,
        isValid = false,
        onUserNameChanged = {},
        onPasswordChanged = {},
        onLoginClick = {},
        onRegisterClick = {},
        onToggleMode = {},
        onClearError = {}
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    LoginContent(
        userName = "testuser",
        password = "password123",
        isLoading = false,
        errorMessage = null,
        isRegisterMode = true,
        isValid = true,
        onUserNameChanged = {},
        onPasswordChanged = {},
        onLoginClick = {},
        onRegisterClick = {},
        onToggleMode = {},
        onClearError = {}
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenErrorPreview() {
    LoginContent(
        userName = "testuser",
        password = "wrong",
        isLoading = false,
        errorMessage = "Invalid username or password",
        isRegisterMode = false,
        isValid = true,
        onUserNameChanged = {},
        onPasswordChanged = {},
        onLoginClick = {},
        onRegisterClick = {},
        onToggleMode = {},
        onClearError = {}
    )
}
