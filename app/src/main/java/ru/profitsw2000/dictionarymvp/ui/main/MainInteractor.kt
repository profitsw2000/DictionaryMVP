package ru.profitsw2000.dictionarymvp.ui.main

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import ru.profitsw2000.dictionarymvp.data.AppState
import ru.profitsw2000.dictionarymvp.data.entities.DataModel
import ru.profitsw2000.dictionarymvp.domain.Interactor
import ru.profitsw2000.dictionarymvp.domain.Repository

class MainInteractor (private val repository: Repository<List<DataModel>>) : Interactor<AppState> {
    override fun getData(word: String, remoteSource: Boolean): Single<AppState> {
        return repository.getData(word, remoteSource).map { AppState.Success(it) }
    }
}