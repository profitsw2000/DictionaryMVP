package ru.profitsw2000.dictionarymvp.domain

import ru.profitsw2000.dictionarymvp.data.entities.DataModel

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun getData(): List<DataModel>

    suspend fun saveToDB(word: String, dataModel: List<DataModel>)
}