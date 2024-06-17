package uzhnu.deagle21.bonuswallet.ui

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OperationScreen() {
    val isAdmin = false
    var state by rememberSaveable {
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
            text = "Name",
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(alignment = Alignment.Start)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "email",
            fontSize = 22.sp,
            modifier = Modifier.align(alignment = Alignment.Start)
        )
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = state.toString(),
            onValueChange = { newState ->
                state = newState.toInt()
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
            if (isAdmin) {
                Button(onClick = { /*TODO*/ },
                    modifier = Modifier.weight(5F)) {
                    Text(text = "Take Bonuses")
                }
                Spacer(modifier = Modifier.weight(1F))
                Button(onClick = { /*TODO*/ },
                    modifier = Modifier.weight(5F)) {
                    Text(text = "Add Bonuses")
                }
            } else {
                Button(
                    onClick = { /*TODO*/ },
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
    OperationScreen()
}