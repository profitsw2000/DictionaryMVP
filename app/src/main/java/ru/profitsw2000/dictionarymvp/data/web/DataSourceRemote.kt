package ru.profitsw2000.dictionarymvp.data.web

import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.profitsw2000.dictionarymvp.data.entities.DataModel
import ru.profitsw2000.dictionarymvp.domain.DataSource

const val api_url = "https://dictionary.skyeng.ru/api/public/v1/"

class DataSourceRemote : DataSource<List<DataModel>> {

    //retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(api_url)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api: ApiService = retrofit.create(ApiService::class.java)

    override fun getData(word: String): Single<List<DataModel>> {
        return api.search(word)
    }
}