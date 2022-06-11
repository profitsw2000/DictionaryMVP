package ru.profitsw2000.dictionarymvp.ui.base

import ru.profitsw2000.dictionarymvp.data.AppState

interface View {
    fun renderData(appState: AppState)
}