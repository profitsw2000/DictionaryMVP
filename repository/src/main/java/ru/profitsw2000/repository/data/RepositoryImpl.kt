package ru.profitsw2000.repository.data

import ru.profitsw2000.model.entities.DataModel
import ru.profitsw2000.repository.data.local.DataSourceLocal
import ru.profitsw2000.repository.data.web.DataSourceRemote
import ru.profitsw2000.repository.domain.Repository

class RepositoryImpl(
    private val dataSourceRemote: DataSourceRemote,
    private val dataSourceLocal: DataSourceLocal
)
    : Repository<List<DataModel>> {
    override suspend fun getData(word: String, remoteRepository: Boolean): List<DataModel> {
        val modelList: List<DataModel>
        if(remoteRepository) {
            modelList = dataSourceRemote.getData(word)
            dataSourceLocal.saveToDB(word, modelList)
        } else {
            modelList = dataSourceLocal.getData()
        }
        return modelList
    }

    override suspend fun getHistoryDataByWord(word: String): List<DataModel> {
        return dataSourceLocal.getData(word)
    }
}