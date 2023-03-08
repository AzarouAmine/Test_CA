package come.azaroumedamine.mobiletest.ca.utils

import java.text.NumberFormat
import java.util.Currency

object TextUtils {

    fun parseAmountToEur(amount: Float): String {
        val format = NumberFormat.getCurrencyInstance()
        format.currency = Currency.getInstance("EUR")
        return format.format(amount)
    }
}