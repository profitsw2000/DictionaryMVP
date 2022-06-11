package ru.profitsw2000.dictionarymvp.domain

interface DataSource<T> {
    suspend fun getData(word: String): T
}