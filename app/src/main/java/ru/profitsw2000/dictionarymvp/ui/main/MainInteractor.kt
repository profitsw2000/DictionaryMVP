package ru.profitsw2000.dictionarymvp.ui.main

import ru.profitsw2000.dictionarymvp.data.AppState
import ru.profitsw2000.dictionarymvp.data.entities.DataModel
import ru.profitsw2000.dictionarymvp.domain.Interactor
import ru.profitsw2000.dictionarymvp.domain.Repository

class MainInteractor(val repository: Repository<List<DataModel>>) : Interactor<AppState> {
    override suspend fun getData(word: String, remoteSource: Boolean): AppState {
        return AppState.Success(repository.getData(word, remoteSource))
    }

    override suspend fun getHistoryDataByWord(word: String): AppState {
        return AppState.Success(repository.getHistoryDataByWord(word))
    }
}