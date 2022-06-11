package ru.profitsw2000.dictionarymvp.ui.main

import io.reactivex.rxjava3.core.Single
import ru.profitsw2000.dictionarymvp.data.AppState
import ru.profitsw2000.dictionarymvp.data.entities.DataModel
import ru.profitsw2000.dictionarymvp.di.NAME_REPO
import ru.profitsw2000.dictionarymvp.domain.Interactor
import ru.profitsw2000.dictionarymvp.domain.Repository
import javax.inject.Inject
import javax.inject.Named

class MainInteractor(val repository: Repository<List<DataModel>>) : Interactor<AppState> {
    override fun getData(word: String, remoteSource: Boolean): Single<AppState> {
        return repository.getData(word, remoteSource).map { AppState.Success(it) }
    }
}