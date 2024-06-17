package uzhnu.deagle21.navigation

import androidx.compose.runtime.Stable

@Stable
interface Router {
    fun launch (route: Route)

    fun pop()

    fun restart(route: Route)
}