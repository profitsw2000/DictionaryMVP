package ru.profitsw2000.repository

import ru.profitsw2000.model.entities.DataModel
import ru.profitsw2000.core.Interactor
import ru.profitsw2000.model.AppState
import ru.profitsw2000.repository.domain.Repository

class MainInteractor(val repository: Repository<List<DataModel>>) :
    Interactor<AppState> {
    override suspend fun getData(word: String, remoteSource: Boolean): AppState {
        return AppState.Success(repository.getData(word, remoteSource))
    }

    override suspend fun getHistoryDataByWord(word: String): AppState {
        return AppState.Success(repository.getHistoryDataByWord(word))
    }
}