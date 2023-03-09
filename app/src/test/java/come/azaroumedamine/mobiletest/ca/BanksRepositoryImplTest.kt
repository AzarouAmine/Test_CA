package come.azaroumedamine.mobiletest.ca

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import come.azaroumedamine.mobiletest.ca.data.models.Bank
import come.azaroumedamine.mobiletest.ca.data.models.BankAccount
import come.azaroumedamine.mobiletest.ca.data.repository.BanksRepositoryImpl
import come.azaroumedamine.mobiletest.ca.data.source.network.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.io.File

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class BanksRepositoryImplTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var banksRepositoryImpl: BanksRepositoryImpl

    @Mock
    private lateinit var bankService: BanksService

    @Mock
    private lateinit var context: Context

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        val banksNetworkDataSourceImpl = BanksNetworkDataSourceImpl(bankService)
        val fakeBanksLocalDataSourceImpl = FakeBanksLocalDataSource(context)
        banksRepositoryImpl = BanksRepositoryImpl(banksNetworkDataSourceImpl, fakeBanksLocalDataSourceImpl)
    }

    @Test
    fun test_BanksRetrievedFromNetwork() {
        val dataList = listOf(Bank("name",1,
            accounts = listOf(BankAccount("1231230", "title",123f, operations = emptyList()))
        ))
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(bankService.getBanks()).thenReturn(Response.success(dataList))

            assertThat(dataList, `is`(banksRepositoryImpl.getBanks().data))
        }
    }

    @Test
    fun test_BanksNotRetrievedFromNetwork() {
        val errorCode = 404
        val response = Response.error<List<Bank>>(errorCode, ResponseBody.create(MediaType.get("application/json"), "Error"))
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(bankService.getBanks()).thenReturn(response)

            // Even though we mocked an error response, we will get a success and non null data from local source.
            assertThat(banksRepositoryImpl.getBanks().status, `is`(Resource.Status.SUCCESS))
            assertThat(banksRepositoryImpl.getBanks().data, `is`(notNullValue()))
        }
    }
}

class FakeBanksLocalDataSource(context: Context): BanksLocalDataSourceImpl(context) {

    override fun getJsonDataFromAsset(context: Context): String? {
        return File("src\\test\\assets\\banks.json").bufferedReader().use { it.readText() }
    }
}