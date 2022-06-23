package ru.profitsw2000.repository.domain

interface Repository<T> {
    suspend fun getData(word: String, remoteRepository: Boolean): T

    suspend fun getHistoryDataByWord(word: String): T
}