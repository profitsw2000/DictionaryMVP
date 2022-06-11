package ru.profitsw2000.dictionarymvp.ui.main

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.profitsw2000.dictionarymvp.data.AppState
import ru.profitsw2000.dictionarymvp.domain.BaseViewModel

class MainViewModel(private val interactor: MainInteractor ) : BaseViewModel<AppState>() {

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
}