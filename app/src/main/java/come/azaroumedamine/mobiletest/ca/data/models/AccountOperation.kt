package come.azaroumedamine.mobiletest.ca.data.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class AccountOperation(
    @SerializedName("title") val title: String,
    @SerializedName("amount") val amount: String,
    @SerializedName("date") val date: String,
)
