package ru.profitsw2000.repository.domain

interface DataSource<T> {
    suspend fun getData(word: String): T
}