package uzhnu.deagle21.navigation

import androidx.compose.runtime.Stable

@Stable
interface NavigationState {
    val isRoot: Boolean
    val currentRoute: Route
}