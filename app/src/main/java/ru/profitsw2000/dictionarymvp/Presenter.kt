package ru.profitsw2000.dictionarymvp

interface Presenter {
    fun onAttach(view: View)
    fun onDetach(view: View)
    fun getData(word: String)
}