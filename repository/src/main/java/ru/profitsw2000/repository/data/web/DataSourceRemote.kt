package ru.profitsw2000.repository.data.web

import ru.profitsw2000.model.entities.DataModel
import ru.profitsw2000.repository.domain.DataSource

class DataSourceRemote(private val apiService: ApiService) : DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return apiService.asyncSearch(word).await()
    }
}