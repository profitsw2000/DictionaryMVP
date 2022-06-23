package ru.profitsw2000.historyscreen

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.profitsw2000.core.BaseViewModel
import ru.profitsw2000.model.AppState
import ru.profitsw2000.repository.MainInteractor

class HistoryViewModel(private val interactor: MainInteractor) : BaseViewModel<AppState>() {

    private val liveData: LiveData<AppState> = _mutableliveData

    fun subscribe(): LiveData<AppState> {
        return liveData
    }

    override fun getData(word: String, remoteSource: Boolean) {
        _mutableliveData.value = AppState.Loading
        cancelJob()
        viewModelCoroutineScope.launch { startAsyncSearch(word, remoteSource) }
    }

    private suspend fun startAsyncSearch(word: String, remoteSource: Boolean) = withContext(Dispatchers.IO) {
        _mutableliveData.postValue(interactor.getData(word, remoteSource))
    }

    override fun handleError(error: Throwable) {
        _mutableliveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableliveData.value = AppState.Success(null)
        super.onCleared()
    }
}