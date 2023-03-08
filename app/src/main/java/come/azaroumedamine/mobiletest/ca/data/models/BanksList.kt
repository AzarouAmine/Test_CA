package come.azaroumedamine.mobiletest.ca.data.models

import com.google.gson.annotations.SerializedName

data class BanksList(
    @SerializedName("banks") val banks: List<Bank>
)
