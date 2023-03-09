package come.azaroumedamine.mobiletest.ca.data.repository

import come.azaroumedamine.mobiletest.ca.data.models.Bank
import come.azaroumedamine.mobiletest.ca.data.source.network.BanksLocalDataSourceImpl
import come.azaroumedamine.mobiletest.ca.data.source.network.BanksNetworkDataSourceImpl
import come.azaroumedamine.mobiletest.ca.data.source.network.Resource
import javax.inject.Inject

class BanksRepositoryImpl @Inject constructor(
    private val banksNetworkDataSourceImpl: BanksNetworkDataSourceImpl,
    private val banksLocalDataSource: BanksLocalDataSourceImpl
): BanksRepository {

    override suspend fun getBanks(): Resource<List<Bank>> {
        val resource = banksNetworkDataSourceImpl.getBanks()
        if (resource.status == Resource.Status.ERROR) {
            return banksLocalDataSource.getBanks()
        }
        return resource
    }
}