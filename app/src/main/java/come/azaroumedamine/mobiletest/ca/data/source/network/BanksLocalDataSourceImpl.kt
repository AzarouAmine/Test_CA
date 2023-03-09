package come.azaroumedamine.mobiletest.ca.data.source.network

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import come.azaroumedamine.mobiletest.ca.data.models.Bank
import java.io.IOException
import javax.inject.Inject

open class BanksLocalDataSourceImpl @Inject constructor(
    private val context: Context
): BanksDataSource {

    override suspend fun getBanks() = run {
        val gson = Gson()
        val listBanks = object : TypeToken<List<Bank>>() {}.type

        val banks: List<Bank> = gson.fromJson(getJsonDataFromAsset(context), listBanks)
        Resource.success(banks)
    }

    protected open fun getJsonDataFromAsset(context: Context): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open("banks.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}