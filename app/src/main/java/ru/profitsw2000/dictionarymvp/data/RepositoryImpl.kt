package ru.profitsw2000.dictionarymvp.data

import ru.profitsw2000.dictionarymvp.data.entities.DataModel
import ru.profitsw2000.dictionarymvp.data.local.DataSourceLocal
import ru.profitsw2000.dictionarymvp.data.web.DataSourceRemote
=======
import io.reactivex.rxjava3.core.Single
import ru.profitsw2000.dictionarymvp.data.entities.DataModel
import ru.profitsw2000.dictionarymvp.data.local.DataSourceLocal
import ru.profitsw2000.dictionarymvp.data.web.DataSourceRemote
=======
import ru.profitsw2000.dictionarymvp.domain.DataSource
import ru.profitsw2000.dictionarymvp.domain.Repository

class RepositoryImpl(
    private val dataSourceRemote: DataSourceRemote,
    private val dataSourceLocal: DataSourceLocal)
    : Repository<List<DataModel>> {
    override suspend fun getData(word: String, remoteRepository: Boolean): List<DataModel> {
=======
=======
    private val dataSourceRemote: DataSource<List<DataModel>>,
    private val dataSourceLocal: DataSource<List<DataModel>>)
    : Repository<List<DataModel>> {
    override fun getData(word: String, remoteRepository: Boolean): Single<List<DataModel>> {
        return if(remoteRepository) {
            dataSourceRemote.getData(word)
        } else {
            dataSourceLocal.getData(word)
        }
    }

}