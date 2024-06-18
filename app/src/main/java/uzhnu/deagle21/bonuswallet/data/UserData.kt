package uzhnu.deagle21.bonuswallet.data

import java.time.LocalDate

data class UserData(
    val email: String = "",
    val name: String = "",
    val createDate: String = LocalDate.now().toString(),
    val admin: Boolean = false,
    val bonusCount: Int = 0
)
