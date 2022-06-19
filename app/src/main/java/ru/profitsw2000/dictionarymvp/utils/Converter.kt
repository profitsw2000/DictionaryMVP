package ru.profitsw2000.dictionarymvp.utils

import ru.profitsw2000.dictionarymvp.data.entities.DataModel
import ru.profitsw2000.dictionarymvp.data.entities.Meanings
import ru.profitsw2000.dictionarymvp.data.entities.Translation
import ru.profitsw2000.dictionarymvp.room.HistoryEntity

fun convertDataModelToEntity(word: String, dataModel: DataModel): HistoryEntity? {
    val translation = dataModel.meanings?.get(0)?.translation?.text
    val note = if (dataModel.meanings?.get(0)?.translation?.note.isNullOrEmpty()) "" else dataModel.meanings?.get(0)?.translation?.note

    return if (translation.isNullOrEmpty()) {
        null
    } else {
        HistoryEntity(word, translation, note)
    }
}

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<DataModel> {
    val searchResult = ArrayList<DataModel>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            searchResult.add(DataModel(entity.word,
                meanings = arrayListOf(Meanings(Translation(entity.translation, entity.note), null))))
        }
    }
    return searchResult
}