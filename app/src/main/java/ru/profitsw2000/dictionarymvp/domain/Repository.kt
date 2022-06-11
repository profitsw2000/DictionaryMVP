package ru.profitsw2000.dictionarymvp.domain

import io.reactivex.rxjava3.core.Single

interface Repository<T> {
    suspend fun getData(word: String, remoteRepository: Boolean): T
}