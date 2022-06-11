package ru.profitsw2000.dictionarymvp.data

import ru.profitsw2000.dictionarymvp.data.entities.DataModel

sealed class AppState{
    data class Success(val data: List<DataModel>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}

