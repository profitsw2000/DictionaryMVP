package ru.profitsw2000.dictionarymvp.data.local

import ru.profitsw2000.dictionarymvp.data.entities.DataModel
import ru.profitsw2000.dictionarymvp.data.entities.Meanings
import ru.profitsw2000.dictionarymvp.data.entities.Translation
import ru.profitsw2000.dictionarymvp.domain.DataSource
import ru.profitsw2000.dictionarymvp.domain.DataSourceLocal
import ru.profitsw2000.dictionarymvp.room.HistoryDao
import ru.profitsw2000.dictionarymvp.utils.convertDataModelToEntity
import ru.profitsw2000.dictionarymvp.utils.mapHistoryEntityListToSearchResult
import ru.profitsw2000.dictionarymvp.utils.mapHistoryEntityToSearchResult

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