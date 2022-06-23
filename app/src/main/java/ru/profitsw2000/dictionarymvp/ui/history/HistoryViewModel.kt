package ru.profitsw2000.dictionarymvp.ui.history

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.profitsw2000.repository.MainInteractor

class HistoryViewModel(private val interactor: ru.profitsw2000.repository.MainInteractor) : ru.profitsw2000.core.BaseViewModel<ru.profitsw2000.model.AppState>() {

    private val liveData: LiveData<ru.profitsw2000.model.AppState> = _mutableliveData

    fun subscribe(): LiveData<ru.profitsw2000.model.AppState> {
        return liveData
    }

    override fun getData(word: String, remoteSource: Boolean) {
        _mutableliveData.value = ru.profitsw2000.model.AppState.Loading
        cancelJob()
        viewModelCoroutineScope.launch { startAsyncSearch(word, remoteSource) }
    }

    private suspend fun startAsyncSearch(word: String, remoteSource: Boolean) = withContext(Dispatchers.IO) {
        _mutableliveData.postValue(interactor.getData(word, remoteSource))
    }

    override fun handleError(error: Throwable) {
        _mutableliveData.postValue(ru.profitsw2000.model.AppState.Error(error))
    }

    override fun onCleared() {
        _mutableliveData.value = ru.profitsw2000.model.AppState.Success(null)
        super.onCleared()
    }
}