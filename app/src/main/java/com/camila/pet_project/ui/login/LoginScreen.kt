package com.camila.pet_project.ui.login

import android.widget.Toast
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.camila.pet_project.R

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(uiState) {
        Toast.makeText(context, "Lauched Effect: Login Success!", Toast.LENGTH_SHORT).show()
    }

    LoginContent(
        title = uiState.userName,
        password = uiState.password,
        onNameChanged = viewModel::updateUserName,
        onPasswordChanged = viewModel::updatePassword,
        loginButtonClicked = {
            viewModel.login(uiState.userName, uiState.password)
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun LoginContent(
    title: String,
    password: String,
    onNameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    loginButtonClicked: () -> Unit,
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(100.dp))

            Image(
                painter = painterResource(id = R.drawable.pawprint),
                contentDescription = "Logo Image",
                modifier = Modifier
                    .size(180.dp)
                    .padding(bottom = 16.dp)

            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = onNameChanged,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = {
                    Text(
                        text = "Nome",
                        color = Black
                    )
                },
                singleLine = true,
                isError = false,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Black,
                    focusedBorderColor = Color.DarkGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChanged,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = {
                    Text(
                        text = "Senha",
                        color = Black
                    )
                },
                singleLine = true,
                isError = false,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Black,
                    focusedBorderColor = Color.DarkGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            RandomKeyboard()

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    loginButtonClicked()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Black),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
                    .height(60.dp),

                ) {
                Text(text = "Acessar")
            }
        }
    }
}

@Composable
private fun RandomKeyboard() {

    val buttonOptions = createNumberPairButtonOptions()

    Row {
        for (i in 0..2) {
            DrawButton(buttonOptions[i])
            buttonOptions.removeAt(i)
            Spacer(modifier = Modifier.padding(5.dp))
        }
    }

    Row {
        for (i in 0..1) {
            DrawButton(buttonOptions[i])
            Spacer(modifier = Modifier.padding(5.dp))
        }
        DrawClearButton()
    }
}

private fun createNumberPairButtonOptions(): MutableList<Pair<Int, Int>> {
    val pairs = mutableListOf<Pair<Int, Int>>()

    val randomNumbers = (0..9).shuffled()

    randomNumbers.forEachIndexed { index, value ->
        if (index % 2 == 0 && index + 1 < randomNumbers.size) {
            pairs.add(Pair(value, randomNumbers[index + 1]))
        }
    }
    return pairs
}

@Composable
private fun DrawButton(pair: Pair<Int, Int>) {

    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(containerColor = Black),
        modifier = Modifier
            .padding(bottom = 16.dp)
            .height(50.dp)

    ) {
        Text(text = "${pair.first} ou ${pair.second}")
    }
}

@Composable
private fun DrawClearButton() {

    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(containerColor = Black),
        modifier = Modifier
            .padding(bottom = 16.dp)
            .height(50.dp),
    ) {
        Text(text = "Limpar")
    }
}

//@Composable
//private fun extracted(buttonOption: Pair<Int, Int>, password: String) {
//    val context = LocalContext.current
//
//    if (buttonOption.first.toString() == password[0].toString() ||
//        buttonOption.second.toString() == password[0].toString()
//    ) {
//        Toast.makeText(context, "This is a toast!", Toast.LENGTH_SHORT).show()
//    } else {
//        Toast.makeText(context, "Try again!", Toast.LENGTH_SHORT).show()
//    }
//}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginContent(
        title = "Title",
        password = "Password",
        onNameChanged = {},
        onPasswordChanged = {},
        loginButtonClicked = {}
    )
}
