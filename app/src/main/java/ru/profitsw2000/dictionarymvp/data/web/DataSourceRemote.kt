package ru.profitsw2000.dictionarymvp.data.web

import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.profitsw2000.dictionarymvp.data.entities.DataModel
import ru.profitsw2000.dictionarymvp.domain.DataSource

class DataSourceRemote(private val apiService: ApiService) : DataSource<List<DataModel>> {

    override fun getData(word: String): Single<List<DataModel>> {
        return apiService.search(word)
    }
}