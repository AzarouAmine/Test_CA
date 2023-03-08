package come.azaroumedamine.mobiletest.ca.ui.accounts

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import come.azaroumedamine.mobiletest.ca.data.models.Bank
import come.azaroumedamine.mobiletest.ca.data.repository.BanksRepository
import come.azaroumedamine.mobiletest.ca.data.source.network.Resource
import come.azaroumedamine.mobiletest.ca.utils.bool
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class BanksViewModel @Inject constructor(
    private val banksRepository: BanksRepository
): ViewModel() {

    private val banks: LiveData<Resource<List<Bank>>> = liveData(Dispatchers.IO) {
        emit(Resource.loading())

        val response = banksRepository.getBanks()

        if (response.status == Resource.Status.SUCCESS) {
            emit(response)
        } else if (response.status == Resource.Status.ERROR) {
            emit(Resource.error(response.message!!))
        }
    }

    fun caBanks(): LiveData<List<Bank>?> {
        return Transformations.map(banks) { res ->
            res.data?.filter { it.isCreditAgricole.bool }?.sortedBy { it.name }
        }
    }

    fun otherBanks(): LiveData<List<Bank>?> {
        return Transformations.map(banks) { res ->
            res.data?.filter { !it.isCreditAgricole.bool }?.sortedBy { it.name }
        }
    }
}