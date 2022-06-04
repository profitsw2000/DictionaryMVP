package ru.profitsw2000.dictionarymvp.ui.main

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import ru.profitsw2000.dictionarymvp.data.AppState
import ru.profitsw2000.dictionarymvp.domain.Interactor

class MainInteractor : Interactor<AppState> {
    override fun getData(word: String, remoteSource: Boolean): Single<AppState> {
        TODO("Not yet implemented")
    }
}