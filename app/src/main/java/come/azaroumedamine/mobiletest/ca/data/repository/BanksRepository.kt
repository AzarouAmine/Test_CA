package come.azaroumedamine.mobiletest.ca.data.repository

import come.azaroumedamine.mobiletest.ca.data.models.Bank
import come.azaroumedamine.mobiletest.ca.data.source.network.BanksNetworkDataSource
import come.azaroumedamine.mobiletest.ca.data.source.network.Resource
import javax.inject.Inject

class BanksRepository @Inject constructor(
    private val banksNetworkDataSource: BanksNetworkDataSource
) {

    suspend fun getBanks(): Resource<List<Bank>> {
        val resource = banksNetworkDataSource.getBanks()
        if (resource.status == Resource.Status.ERROR) {
            // TODO: Load json from assets
        }
        return resource
    }
}