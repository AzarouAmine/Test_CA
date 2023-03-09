package come.azaroumedamine.mobiletest.ca.utils

import androidx.core.text.isDigitsOnly
import timber.log.Timber

val Int.bool:Boolean get() = this != 0

fun String.toFloatUnformatted(): Float {
    val formattedString = this.replace(",", ".")
    try {
        return formattedString.toFloat()
    } catch (ex: NumberFormatException) {
        Timber.d("Error: %s", ex.message)
        return 0F
    }
}