package ru.profitsw2000.dictionarymvp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.profitsw2000.dictionarymvp.ui.main.MainActivity

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}