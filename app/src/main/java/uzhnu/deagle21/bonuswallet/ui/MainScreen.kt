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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import uzhnu.deagle21.bonuswallet.data.UserData

const val mainRoute = "mainRoute"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val firestore = Firebase.firestore
    val list = remember {
        mutableStateOf(emptyList<UserData>())
    }
    firestore.collection("users").whereNotEqualTo("email", Firebase.auth.currentUser?.email).get().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            list.value = task.result.toObjects(UserData::class.java)
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Bonus Actions",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(Color(0x41525252))
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0x41525252)
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { /*TODO*/ },
                    label = {
                        Text(text = "Actions")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Actions Tab",
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    label = {
                        Text(text = "Profile")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Tab"
                        )
                    }
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) { paddingValues ->
        Column {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
            ) {
                items(list.value) { userData ->
                    UserCard(name = userData.name, email = userData.email)
                }
            }
            Button(onClick = { Firebase.auth.signOut() }) {
                Text(text = "Log Out")
            }
        }
    }
}

@Composable
fun UserCard(
    name: String,
    email: String
) {
    Card(
        onClick = { /*TODO*/ },
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
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 16
                .dp)
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
fun MainScreenPreview() {
    MainScreen()
}
