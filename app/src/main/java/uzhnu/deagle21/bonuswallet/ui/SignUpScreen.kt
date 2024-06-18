package uzhnu.deagle21.bonuswallet.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import uzhnu.deagle21.bonuswallet.R
import uzhnu.deagle21.bonuswallet.data.UserData

const val signUpRoute = "signUpScreen"

@Composable
fun SignUpScreen(
    onSignUpClick: () -> Unit
) {

    val auth = Firebase.auth

    val context = LocalContext.current

    val nameState = rememberSaveable {
        mutableStateOf("")
    }
    val emailState = rememberSaveable {
        mutableStateOf("")
    }
    val passwordState = rememberSaveable {
        mutableStateOf("")
    }
    val passwordRepeatState = rememberSaveable {
        mutableStateOf("")
    }

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
            text = "Sign Up to BonusWallet",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(25.dp))

        TextField(
            value = nameState.value,
            onValueChange = { newValue ->
                nameState.value = newValue
            },
            label = {
                Text(text = "Name")
            },
            singleLine = true,
            modifier = Modifier.width(280.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = emailState.value,
            onValueChange = { newValue ->
                emailState.value = newValue
            },
            label = {
                Text(text = "Email")
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
                Text(text = "Password")
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

        TextField(
            value = passwordRepeatState.value,
            onValueChange = { newValue ->
                passwordRepeatState.value = newValue
            },
            label = {
                Text(text = "Password (repeat)")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.width(280.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                if (passwordState.value == passwordRepeatState.value
                    && nameState.value.length > 2
                    && emailState.value.length > 2
                    && passwordState.value.length > 5
                ) {
                    signUp(auth, emailState.value, passwordState.value, onSignUpClick)
                    val firestore = Firebase.firestore
                    firestore.collection("users")
                        .document().set(
                            UserData(
                                email = emailState.value,
                                name = nameState.value,
                                admin = false
                            )
                        )
                }
                else Toast.makeText(context, "Enter proper values", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "Sign Up")
        }
    }
}

@Composable
@Preview
fun SignUpScreenPreview() {
    SignUpScreen(
        onSignUpClick = {}
    )
}

private fun signUp(auth: FirebaseAuth, email: String, password: String, onSignUpClick: () -> Unit) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("myLogs", "Sign Up Successful!")
                onSignUpClick()
            }
            else {
                Log.d("myLogs", "Sign Up Failed!")
            }
        }
}
