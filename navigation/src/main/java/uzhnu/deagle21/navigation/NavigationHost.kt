package uzhnu.deagle21.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NavigationHost(
    navigation: Navigation,
    modifier: Modifier,
    routeMapper: @Composable (Route) -> Unit
) {

}