package ru.profitsw2000.dictionarymvp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.profitsw2000.dictionarymvp.di.localdbModule
import ru.profitsw2000.dictionarymvp.di.webModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(listOf(webModule, localdbModule))
        }
    }
}