package come.azaroumedamine.mobiletest.ca.data.models

import com.google.gson.annotations.SerializedName

data class BankAccount(
    @SerializedName("id") val id: String,
    @SerializedName("label") val title: String,
    @SerializedName("balance") val balance: Float,
    @SerializedName("operations") val operations: List<AccountOperation>,
)
