package ru.profitsw2000.dictionarymvp.domain

import io.reactivex.rxjava3.core.Single

interface Interactor<T : Any> {
    fun getData(word: String, remoteSource: Boolean): Single<T>
}