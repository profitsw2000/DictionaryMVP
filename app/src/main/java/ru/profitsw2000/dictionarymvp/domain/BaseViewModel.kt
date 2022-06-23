package ru.profitsw2000.dictionarymvp.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.profitsw2000.dictionarymvp.data.AppState

abstract class BaseViewModel<T : AppState>(
    protected val liveData: MutableLiveData<T> = MutableLiveData()
) : ViewModel() {

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler{ _, throwable -> handleError(throwable)}
    )

    abstract fun getData(word: String, remoteSource: Boolean)

    abstract fun handleError(error: Throwable)

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }
}
