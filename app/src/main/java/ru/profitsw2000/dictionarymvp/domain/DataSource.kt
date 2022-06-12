package ru.profitsw2000.dictionarymvp.domain

interface DataSource<T> {
    suspend fun getData(word: String): T
=======
import io.reactivex.rxjava3.core.Single

interface DataSource<T> {
    fun getData(word: String): Single<T>
}