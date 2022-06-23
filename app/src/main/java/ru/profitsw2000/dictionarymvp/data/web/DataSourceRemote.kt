package ru.profitsw2000.dictionarymvp.data.web

import ru.profitsw2000.dictionarymvp.data.entities.DataModel
import ru.profitsw2000.dictionarymvp.domain.DataSource

class DataSourceRemote(private val apiService: ApiService) : DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return apiService.asyncSearch(word).await()
    }
}