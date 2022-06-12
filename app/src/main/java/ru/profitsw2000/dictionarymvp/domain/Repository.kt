package ru.profitsw2000.dictionarymvp.domain

interface Repository<T> {
    suspend fun getData(word: String, remoteRepository: Boolean): T
=======
import io.reactivex.rxjava3.core.Single

interface Repository<T> {
    fun getData(word: String, remoteRepository: Boolean): Single<T>
}