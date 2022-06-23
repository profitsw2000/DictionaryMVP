package ru.profitsw2000.dictionarymvp.data.web

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import ru.profitsw2000.dictionarymvp.data.entities.DataModel

interface ApiService {
    @GET("words/search")
    fun asyncSearch(@Query("search") wordToSearch: String): Deferred<List<DataModel>>
}