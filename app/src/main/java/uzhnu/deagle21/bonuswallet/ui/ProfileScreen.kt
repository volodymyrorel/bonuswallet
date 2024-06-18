package uzhnu.deagle21.bonuswallet.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import uzhnu.deagle21.bonuswallet.data.UserData

const val profileRoute = "profileRoute"

@Composable
fun ProfileScreen(onLogOutClick: () -> Unit = {}) {
    var currentUserData by remember {
        mutableStateOf(emptyList<UserData>())
    }
    Firebase.firestore.collection("users")
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = currentUser.name,
            fontSize = 36.sp,
            fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = currentUser.email,
            fontSize = 18.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Joined on: ${currentUser.createDate}",
            fontSize = 18.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Bonuses: ${currentUser.bonusCount}",
            fontSize = 26.sp)
        Spacer(modifier = Modifier.height(400.dp))
        Button(onClick = {
            Firebase.auth.signOut()
            onLogOutClick()
        }) {
            Text(text = "Log Out")
        }
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen()
}