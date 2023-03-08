package come.azaroumedamine.mobiletest.ca.data.source.network

import javax.inject.Inject

class BanksNetworkDataSource @Inject constructor(
    private val banksService: BanksService
): BaseNetworkDataSource() {

    suspend fun getBanks() = getResponse { banksService.getBanks() }
}