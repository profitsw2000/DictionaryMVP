package ru.profitsw2000.dictionarymvp.ui.main

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.profitsw2000.core.BaseViewModel
import ru.profitsw2000.model.AppState
import ru.profitsw2000.repository.MainInteractor

class MainViewModel(private val interactor: MainInteractor) : BaseViewModel<AppState>() {

    private val liveData: LiveData<AppState> = _mutableliveData

    fun subscribe(): LiveData<AppState> {
        return liveData
    }

    override fun getData(word: String, remoteSource: Boolean) {
        _mutableliveData.value = AppState.Loading
        cancelJob()
        viewModelCoroutineScope.launch { startAsyncSearch(word, remoteSource) }
    }

    fun getHistoryDataByWord(word: String) {
        _mutableliveData.postValue(AppState.Loading)
        cancelJob()
        viewModelCoroutineScope.launch { startAsyncSearchInHistory(word) }
    }

    private suspend fun startAsyncSearchInHistory(word: String) = withContext(Dispatchers.IO) {
        _mutableliveData.postValue(interactor.getHistoryDataByWord(word))
    }

    private suspend fun startAsyncSearch(word: String, remoteSource: Boolean) = withContext(Dispatchers.IO) {
        _mutableliveData.postValue(interactor.getData(word, remoteSource))
    }

    override fun handleError(error: Throwable) {
        _mutableliveData.postValue(AppState.Error(error))
    }
}