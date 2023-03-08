package come.azaroumedamine.mobiletest.ca.data.source.network

import retrofit2.Response
import timber.log.Timber
import java.io.IOException

abstract class BaseNetworkDataSource {

    protected suspend fun <T> getResponse(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: IOException) {
            Timber.e(e)
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Timber.e(message)
        return Resource.error("Network call failure: $message\n. Falling back to local data.")
    }
}