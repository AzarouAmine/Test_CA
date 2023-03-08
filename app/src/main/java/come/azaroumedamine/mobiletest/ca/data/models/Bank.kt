package come.azaroumedamine.mobiletest.ca.data.models

import com.google.gson.annotations.SerializedName

data class Bank(
    @SerializedName("name") val name: String,
    @SerializedName("isCA") val isCreditAgricole: Int,
    @SerializedName("accounts") val accounts: List<BankAccount>,
)