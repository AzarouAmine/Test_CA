package come.azaroumedamine.mobiletest.ca.data.source.network

import javax.inject.Inject

class BanksNetworkDataSourceImpl @Inject constructor(
    private val banksService: BanksService
): BaseNetworkDataSource(), BanksDataSource {

    override suspend fun getBanks() = getResponse { banksService.getBanks() }
}