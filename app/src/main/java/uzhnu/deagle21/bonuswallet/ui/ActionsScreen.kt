package uzhnu.deagle21.bonuswallet.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import uzhnu.deagle21.bonuswallet.data.UserData

const val actionsRoute = "actionsRoute"

@Composable
fun ActionsScreen(
    navController: NavController
) {
    val firestore = Firebase.firestore
    var listNotCurrent by remember {
        mutableStateOf(emptyList<UserData>())
    }
    firestore.collection("users")
        .whereNotEqualTo("email", Firebase.auth.currentUser?.email)
        .get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                listNotCurrent = task.result.toObjects(UserData::class.java)
            }
        }
    var listNotAdmin by remember {
        mutableStateOf(emptyList<UserData>())
    }
    firestore.collection("users")
        .whereNotEqualTo("admin", true)
        .get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                listNotAdmin = task.result.toObjects(UserData::class.java)
            }
        }
    val set: Set<UserData> = listNotCurrent.intersect(listNotAdmin)
    val list = set.toList()
    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(list) { userData ->
                UserCard(
                    name = userData.name,
                    email = userData.email,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun UserCard(
    navController: NavController,
    name: String,
    email: String
) {
    Card(
        onClick = { navController.navigate("$operationRoute/$email") },
        border = BorderStroke(
            width = 1.dp,
            Color.Black
        ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xCDE2E2E2)
        ),
        modifier = Modifier
            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(
                top = 10.dp, bottom = 10.dp, start = 16
                    .dp
            )
        ) {
            Text(
                text = name,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = email,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
@Preview
fun ActionsScreenPreview() {
    ActionsScreen(
        rememberNavController()
    )
}