package ru.profitsw2000.dictionarymvp

import io.reactivex.rxjava3.core.Observable

interface Interactor<T> {
    fun getData(word: String): Observable<T>
}