package ru.profitsw2000.repository.domain

import ru.profitsw2000.model.entities.DataModel

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun getData(): T

    suspend fun saveToDB(word: String, dataModel: List<DataModel>)
}