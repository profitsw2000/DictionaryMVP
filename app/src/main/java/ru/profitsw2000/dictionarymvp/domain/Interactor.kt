package ru.profitsw2000.dictionarymvp.domain

interface Interactor<T> {
    suspend fun getData(word: String, remoteSource: Boolean): T
=======
import io.reactivex.rxjava3.core.Single

interface Interactor<T> {
    fun getData(word: String, remoteSource: Boolean): Single<T>
}