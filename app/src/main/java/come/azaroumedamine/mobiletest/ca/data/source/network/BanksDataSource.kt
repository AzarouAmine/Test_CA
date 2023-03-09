package come.azaroumedamine.mobiletest.ca.data.source.network

import come.azaroumedamine.mobiletest.ca.data.models.Bank

interface BanksDataSource {
    suspend fun getBanks(): Resource<List<Bank>>
}