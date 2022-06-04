package ru.profitsw2000.dictionarymvp.data.local

import io.reactivex.rxjava3.core.Observable
import ru.profitsw2000.dictionarymvp.data.entities.DataModel
import ru.profitsw2000.dictionarymvp.domain.DataSource

class DataSourceLocal : DataSource<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("Not yet implemented")
    }
}