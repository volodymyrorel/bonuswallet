package uzhnu.deagle21.bonuswallet.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import uzhnu.deagle21.bonuswallet.R


const val logInRoute = "logInRoute"

@Composable
fun LogInScreen(
    onSignUpClick: () -> Unit = {},
    onLogIn: () -> Unit = {}
) {
    val emailState = rememberSaveable {
        mutableStateOf("")
    }
    val passwordState = rememberSaveable {
        mutableStateOf("")
    }
    val auth = Firebase.auth

    var passwordVisibility by rememberSaveable {
        mutableStateOf(false)
    }
    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.design_ic_visibility)
    else
        painterResource(id = R.drawable.design_ic_visibility_off)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.tertiary,
                        MaterialTheme.colorScheme.secondary
                    ),
                    start = Offset(0F, 0F),
                    end = Offset.Infinite
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.log_in_to_account),
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = emailState.value,
            onValueChange = { newValue ->
                emailState.value = newValue
            },
            label = {
                Text(text = stringResource(id = R.string.email))
            },
            singleLine = true,
            modifier = Modifier.width(280.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = passwordState.value,
            onValueChange = { newValue ->
                passwordState.value = newValue
            },
            label = {
                Text(text = stringResource(id = R.string.password))
            },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Visibility Icon"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            modifier = Modifier.width(280.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                logIn(auth, emailState.value, passwordState.value) {
                    onLogIn()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = stringResource(id = R.string.log_in))
        }
        Spacer(modifier = Modifier.height(36.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(26.dp))
            Text(text = stringResource(id = R.string.no_account))
            Spacer(modifier = Modifier.width(26.dp))
            Button(
                onClick = {
                    onSignUpClick()
                },
                modifier = Modifier
                    .height(40.dp)
                    .align(
                        Alignment.CenterVertically
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Text(text = stringResource(id = R.string.sign_up))
            }
        }
    }
}

private fun logIn(
    auth: FirebaseAuth,
    email: String,
    password: String,
    loggedIn: () -> Unit = {}
) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("myLogs", "LogIn Successful!")
                loggedIn()
            } else Log.d("myLogs", "LogIn Failed!")
        }
}

@Composable
@Preview
fun LogInScreenPreview() {
    LogInScreen()
}