package ru.profitsw2000.dictionarymvp

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.profitsw2000.dictionarymvp.di.DaggerAppComponent
import ru.profitsw2000.dictionarymvp.di.appModule
import ru.profitsw2000.dictionarymvp.di.webModule
import javax.inject.Inject

class App : Application() {

/*    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }*/

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(listOf(webModule, appModule))
        }
    }
}