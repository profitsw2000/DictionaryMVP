package ru.profitsw2000.dictionarymvp.data.web

import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import ru.profitsw2000.dictionarymvp.data.entities.DataModel

interface ApiService {
    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Single<List<DataModel>>

    @GET("words/search")
    fun asyncSearch(@Query("search") wordToSearch: String): Deferred<List<DataModel>>
}