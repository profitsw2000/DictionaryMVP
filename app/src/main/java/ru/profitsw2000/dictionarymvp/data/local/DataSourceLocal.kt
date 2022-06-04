package ru.profitsw2000.dictionarymvp.data.local

import io.reactivex.rxjava3.core.Single
import ru.profitsw2000.dictionarymvp.data.entities.DataModel
import ru.profitsw2000.dictionarymvp.data.entities.Meanings
import ru.profitsw2000.dictionarymvp.data.entities.Translation
import ru.profitsw2000.dictionarymvp.domain.DataSource

class DataSourceLocal : DataSource<List<DataModel>> {
    override fun getData(word: String): Single<List<DataModel>> {
        return Single.just(arrayListOf(DataModel("test", arrayListOf(Meanings(Translation("тест"))))))
    }
}