package come.azaroumedamine.mobiletest.ca.data.repository

import come.azaroumedamine.mobiletest.ca.data.models.Bank
import come.azaroumedamine.mobiletest.ca.data.source.network.Resource

interface BanksRepository {
    suspend fun getBanks(): Resource<List<Bank>>
}