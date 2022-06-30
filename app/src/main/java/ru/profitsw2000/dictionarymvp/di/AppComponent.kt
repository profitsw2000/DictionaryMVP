package ru.profitsw2000.dictionarymvp.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import ru.profitsw2000.dictionarymvp.App
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    MainInteractorModule::class,
    ViewModelModule::class,
    ActivityModule::class,
    AndroidSupportInjectionModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}