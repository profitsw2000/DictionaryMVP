package ru.profitsw2000.dictionarymvp

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import org.koin.core.context.startKoin
import ru.profitsw2000.dictionarymvp.di.DaggerAppComponent
import ru.profitsw2000.dictionarymvp.di.appModule
import ru.profitsw2000.dictionarymvp.di.webModule
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()

        startKoin{
            modules(listOf(webModule, appModule))
        }

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }
}