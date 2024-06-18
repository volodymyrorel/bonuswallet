package uzhnu.deagle21.bonuswallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import uzhnu.deagle21.bonuswallet.ui.LogInScreen
import uzhnu.deagle21.bonuswallet.ui.MainScreen
import uzhnu.deagle21.bonuswallet.ui.SignUpScreen
import uzhnu.deagle21.bonuswallet.ui.logInRoute
import uzhnu.deagle21.bonuswallet.ui.mainRoute
import uzhnu.deagle21.bonuswallet.ui.signUpRoute
import uzhnu.deagle21.bonuswallet.ui.theme.BonusWalletTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            var startDestination = logInRoute
            if (Firebase.auth.currentUser != null) startDestination = mainRoute
            BonusWalletTheme {
                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    composable(logInRoute) {
                        LogInScreen(
                            onSignUpClick = {
                                navController.navigate(signUpRoute)
                            },
                            onLogIn = {
                                navController.navigate(mainRoute)
                            })
                    }
                    composable(signUpRoute) {
                        SignUpScreen(
                            onSignUpClick = {
                                navController.navigate(mainRoute)
                            }
                        )
                    }
                    composable(mainRoute) {
                        MainScreen(
                            onLogOutClick = {
                                navController.navigate(logInRoute)
                            }
                        )
                    }
                }
            }
        }
    }
}
