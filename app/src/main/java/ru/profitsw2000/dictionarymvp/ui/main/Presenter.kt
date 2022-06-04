package ru.profitsw2000.dictionarymvp.ui.main

import ru.profitsw2000.dictionarymvp.ui.base.View

interface Presenter {
    fun onAttach(view: View)
    fun onDetach(view: View)
    fun getData(word: String, remoteSource: Boolean)
}