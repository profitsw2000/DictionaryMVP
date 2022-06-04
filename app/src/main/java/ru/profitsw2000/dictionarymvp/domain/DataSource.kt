package ru.profitsw2000.dictionarymvp.domain

import io.reactivex.rxjava3.core.Single

interface DataSource<T> {
    fun getData(word: String): Single<T>
}