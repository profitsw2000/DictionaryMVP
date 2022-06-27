package ru.profitsw2000.core

interface Interactor<T> {
    suspend fun getData(word: String, remoteSource: Boolean): T

    suspend fun getHistoryDataByWord(word: String): T
}