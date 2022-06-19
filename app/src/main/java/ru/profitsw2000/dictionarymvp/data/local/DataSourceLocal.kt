package ru.profitsw2000.dictionarymvp.data.local

import ru.profitsw2000.dictionarymvp.data.entities.DataModel
import ru.profitsw2000.dictionarymvp.data.entities.Meanings
import ru.profitsw2000.dictionarymvp.data.entities.Translation
import ru.profitsw2000.dictionarymvp.domain.DataSource

class DataSourceLocal : DataSource<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> {
        return arrayListOf(DataModel("test", arrayListOf(Meanings(Translation("тест","no comment"),"www"))))
    }
}