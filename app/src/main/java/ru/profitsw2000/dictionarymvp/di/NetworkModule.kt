package ru.profitsw2000.dictionarymvp.di

import dagger.Module
import dagger.Provides
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.profitsw2000.dictionarymvp.data.RepositoryImpl
import ru.profitsw2000.dictionarymvp.data.local.DataSourceLocal
import ru.profitsw2000.dictionarymvp.data.web.ApiService
import ru.profitsw2000.dictionarymvp.data.web.DataSourceRemote
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Named("api_url")
    fun provideBaseUrl(): String {
        return "https://dictionary.skyeng.ru/api/public/v1/"
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(@Named("api_url") baseUrl: String, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(converterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiService: ApiService): DataSourceRemote {
        return DataSourceRemote()
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(): DataSourceLocal {
        return DataSourceLocal()
    }

    @Singleton
    @Provides
    fun provideRepository(dataSourceRemote: DataSourceRemote,
                          dataSourceLocal: DataSourceLocal): RepositoryImpl {
        return RepositoryImpl(dataSourceRemote, dataSourceLocal)
    }

}