package come.azaroumedamine.mobiletest.ca.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import come.azaroumedamine.mobiletest.ca.data.repository.BanksRepositoryImpl
import come.azaroumedamine.mobiletest.ca.data.source.network.BanksLocalDataSourceImpl
import come.azaroumedamine.mobiletest.ca.data.source.network.BanksNetworkDataSourceImpl
import come.azaroumedamine.mobiletest.ca.data.source.network.BanksService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpclient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .baseUrl("https://cdf-test-mobile-default-rtdb.europe-west1.firebasedatabase.app/")
            .client(httpclient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideAccountsService(retrofit: Retrofit): BanksService = retrofit.create(BanksService::class.java)

    @Singleton
    @Provides
    fun provideAccountsNetworkDataSource(banksService: BanksService) = BanksNetworkDataSourceImpl(banksService)

    @Singleton
    @Provides
    fun provideAccountsLocalDataSource(@ApplicationContext context: Context) = BanksLocalDataSourceImpl(context)

    @Singleton
    @Provides
    fun provideAccountsRepository(
        banksNetworkDataSourceImpl: BanksNetworkDataSourceImpl, banksLocalDataSourceImpl: BanksLocalDataSourceImpl
    ) =  BanksRepositoryImpl(banksNetworkDataSourceImpl, banksLocalDataSourceImpl)
}