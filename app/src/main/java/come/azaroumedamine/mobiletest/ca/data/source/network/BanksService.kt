package come.azaroumedamine.mobiletest.ca.data.source.network

import come.azaroumedamine.mobiletest.ca.data.models.Bank
import retrofit2.Response
import retrofit2.http.GET

interface BanksService {

    @GET("banks.json")
    suspend fun getBanks(): Response<List<Bank>>
}