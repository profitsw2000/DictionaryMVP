package ru.profitsw2000.dictionarymvp

import io.reactivex.rxjava3.core.Observable

interface DataSource<T> {
    fun getData(word: String): Observable<T>
}