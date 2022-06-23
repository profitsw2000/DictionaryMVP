package ru.profitsw2000.dictionarymvp.ui.history

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.profitsw2000.dictionarymvp.App
import ru.profitsw2000.dictionarymvp.data.AppState
import ru.profitsw2000.dictionarymvp.domain.BaseViewModel
import ru.profitsw2000.dictionarymvp.ui.main.MainInteractor

class HistoryViewModel(private val interactor: MainInteractor) : BaseViewModel<AppState>() {

    fun subscribe(): LiveData<AppState> {
        return liveData
    }

    override fun getData(word: String, remoteSource: Boolean) {
        liveData.postValue(AppState.Loading)
        cancelJob()
        viewModelCoroutineScope.launch { startAsyncSearch(word, remoteSource) }
    }

    private suspend fun startAsyncSearch(word: String, remoteSource: Boolean) = withContext(Dispatchers.IO) {
        liveData.postValue(interactor.getData(word, remoteSource))
    }

    override fun handleError(error: Throwable) {
        liveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        liveData.value = AppState.Success(null)
        super.onCleared()
    }
}