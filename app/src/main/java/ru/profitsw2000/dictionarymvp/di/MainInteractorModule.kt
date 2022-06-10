package ru.profitsw2000.dictionarymvp.di

import dagger.Module
import dagger.Provides
import ru.profitsw2000.dictionarymvp.data.RepositoryImpl
import ru.profitsw2000.dictionarymvp.data.entities.DataModel
import ru.profitsw2000.dictionarymvp.domain.Repository
import ru.profitsw2000.dictionarymvp.ui.main.MainInteractor
import javax.inject.Named


@Module
class MainInteractorModule {

    @Provides
    internal fun provideInteractor(@Named(NAME_REPO) repository: RepositoryImpl) = MainInteractor(repository)
}