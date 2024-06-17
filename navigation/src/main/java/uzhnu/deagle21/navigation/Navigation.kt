package uzhnu.deagle21.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember

@Stable
data class Navigation(
    val router: Router,
    val navigationState: NavigationState
)

@Composable
fun rememberNavigation(initialRoute: Route): Navigation {
    return remember {
        TODO()
    }
}