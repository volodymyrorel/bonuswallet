package uzhnu.deagle21.bonuswallet.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import uzhnu.deagle21.bonuswallet.data.UserData

const val operationRoute = "operationRoute"

@Composable
fun OperationScreen(
    targetEmail: String,
    onButtonClicked: () -> Unit
) {
    val context = LocalContext.current
    val firestore = Firebase.firestore
    var currentUserData by remember {
        mutableStateOf(emptyList<UserData>())
    }
    firestore.collection("users")
        .whereEqualTo("email", Firebase.auth.currentUser?.email)
        .get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                currentUserData = task.result.toObjects(UserData::class.java)
            }
        }
    var currentUser = UserData()
    if (currentUserData.isNotEmpty()) {
        currentUser = currentUserData[0]
    }
    var targetUserData by remember {
        mutableStateOf(emptyList<UserData>())
    }
    firestore.collection("users")
        .whereEqualTo("email", targetEmail)
        .get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                targetUserData = task.result.toObjects(UserData::class.java)
            }
        }
    var targetUser = UserData()
    if (targetUserData.isNotEmpty()) {
        targetUser = targetUserData[0]
    }
    var bonusAmount by rememberSaveable {
        mutableIntStateOf(0)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 45.dp, end = 45.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = targetUser.name,
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(alignment = Alignment.Start)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = targetUser.email,
            fontSize = 22.sp,
            modifier = Modifier.align(alignment = Alignment.Start)
        )
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = bonusAmount.toString(),
            onValueChange = { newState ->
                bonusAmount = try {
                    newState.toInt()
                } catch (e: Throwable) {
                    0
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            label = {
                Text(text = "Enter amount of bonuses")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (currentUser.admin) {
                Button(
                    onClick = {
                        if (bonusAmount > 0) {
                            firestore.collection("users")
                                .whereEqualTo("email", targetEmail)
                                .get().addOnSuccessListener { querySnapshot ->
                                    for (doc in querySnapshot)
                                        doc.reference
                                            .update(
                                                mapOf(
                                                    "bonusCount" to
                                                            targetUser.bonusCount - bonusAmount
                                                )
                                            )
                                }
                            onButtonClicked()
                        } else
                            Toast.makeText(
                                context, "Not enough bonuses to take", Toast.LENGTH_SHORT
                            ).show()
                    },
                    modifier = Modifier.weight(5F)
                ) {
                    Text(text = "Take Bonuses")
                }
                Spacer(modifier = Modifier.weight(0.5F))
                Button(
                    onClick = {
                        if (bonusAmount > 0) {
                            firestore.collection("users")
                                .whereEqualTo("email", targetEmail)
                                .get().addOnSuccessListener { querySnapshot ->
                                    for (doc in querySnapshot)
                                        doc.reference
                                            .update(
                                                mapOf(
                                                    "bonusCount" to
                                                            targetUser.bonusCount + bonusAmount
                                                )
                                            )
                                }
                            onButtonClicked()
                        } else
                            Toast.makeText(
                                context, "Not enough bonuses to add", Toast.LENGTH_SHORT
                            ).show()
                    },
                    modifier = Modifier.weight(5F)
                ) {
                    Text(text = "Add Bonuses")
                }
            } else {
                Button(
                    onClick = {
                        if (currentUser.bonusCount >= bonusAmount
                            && bonusAmount > 0
                        ) {
                            firestore.collection("users")
                                .whereEqualTo("email", targetEmail)
                                .get().addOnSuccessListener { querySnapshot ->
                                    for (doc in querySnapshot)
                                        doc.reference
                                            .update(
                                                mapOf(
                                                    "bonusCount" to
                                                            targetUser.bonusCount + bonusAmount
                                                )
                                            )
                                    Toast.makeText(
                                        context, "Operation is Successful", Toast.LENGTH_SHORT
                                    ).show()
                                }
                            firestore.collection("users")
                                .whereEqualTo("email", currentUser.email)
                                .get().addOnSuccessListener { querySnapshot ->
                                    for (doc in querySnapshot)
                                        doc.reference
                                            .update(
                                                mapOf(
                                                    "bonusCount" to
                                                            currentUser.bonusCount - bonusAmount
                                                )
                                            )
                                    Toast.makeText(
                                        context, "Operation is successful", Toast.LENGTH_SHORT
                                    ).show()
                                }
                            onButtonClicked()
                        } else
                            Toast.makeText(
                                context, "Not enough bonuses to send", Toast.LENGTH_SHORT
                            ).show()
                    },
                ) {
                    Text(text = "Send Bonuses")
                }
            }
        }
    }
}

@Composable
@Preview
fun OperationScreenPreview() {
    OperationScreen(
        targetEmail = "email",
        onButtonClicked = {}
    )
}