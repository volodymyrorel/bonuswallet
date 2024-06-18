package uzhnu.deagle21.bonuswallet.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

const val mainRoute = "mainRoute"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onLogOutClick: () -> Unit = {}) {
    val innerNavController = rememberNavController()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "BonusWallet",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(Color(0x41525252))
            )
        },
        bottomBar = {
            val navBackStackEntry by innerNavController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            NavigationBar(
                containerColor = Color(0x41525252)
            ) {
                NavigationBarItem(
                    selected = currentDestination?.route == actionsRoute,
                    onClick = { innerNavController.navigate(actionsRoute) },
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
                    selected = currentDestination?.route == profileRoute,
                    onClick = { innerNavController.navigate(profileRoute) },
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
    ) { innerPadding ->

        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = innerNavController,
            startDestination = actionsRoute
        ) {
            composable(actionsRoute) {
                ActionsScreen(
                    navController = innerNavController
                )
            }
            composable(profileRoute) {
                ProfileScreen(
                    onLogOutClick = onLogOutClick
                )
            }
            composable("$operationRoute/{email}", listOf(navArgument("email"){type = NavType.StringType})) { backStackEntry ->
                OperationScreen(
                    targetEmail = backStackEntry.arguments?.getString("email").toString(),
                    onButtonClicked = {
                        innerNavController.navigate(actionsRoute)
                    }
                )
            }
        }
    }
}


@Composable
@Preview
fun MainScreenPreview() {
    MainScreen()
}
