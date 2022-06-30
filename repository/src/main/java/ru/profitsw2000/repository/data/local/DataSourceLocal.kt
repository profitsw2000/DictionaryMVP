package ru.profitsw2000.repository.data.local

import ru.profitsw2000.model.entities.DataModel
import ru.profitsw2000.repository.domain.DataSourceLocal
import ru.profitsw2000.repository.room.HistoryDao
import ru.profitsw2000.utils.convertDataModelToEntity
import ru.profitsw2000.utils.mapHistoryEntityListToSearchResult
import ru.profitsw2000.utils.mapHistoryEntityToSearchResult


class DataSourceLocal(private val historyDao: HistoryDao) : DataSourceLocal<List<DataModel>> {
    override suspend fun getData(): List<DataModel> {
        return mapHistoryEntityListToSearchResult(historyDao.all())
    }

    override suspend fun getData(word: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.getDataByWord(word))
    }

    override suspend fun saveToDB(word: String, dataModel: List<DataModel>) {
        convertDataModelToEntity(word, dataModel[0])?.let {
            historyDao.insert(it)
        }
    }
}