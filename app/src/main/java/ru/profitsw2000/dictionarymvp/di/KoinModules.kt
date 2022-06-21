package ru.profitsw2000.dictionarymvp.di

import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.profitsw2000.dictionarymvp.data.RepositoryImpl
import ru.profitsw2000.dictionarymvp.data.entities.DataModel
import ru.profitsw2000.dictionarymvp.data.local.DataSourceLocal
import ru.profitsw2000.dictionarymvp.data.web.ApiService
import ru.profitsw2000.dictionarymvp.data.web.DataSourceRemote
import ru.profitsw2000.dictionarymvp.domain.Repository
import ru.profitsw2000.dictionarymvp.room.HistoryDataBase
import ru.profitsw2000.dictionarymvp.ui.history.HistoryViewModel
import ru.profitsw2000.dictionarymvp.ui.main.MainInteractor
import ru.profitsw2000.dictionarymvp.ui.main.MainViewModel

val webModule = module {
    single<String>(named(URL)) { "https://dictionary.skyeng.ru/api/public/v1/" }
    single(named("retrofitBuilder")) { Retrofit.Builder()
        .baseUrl(get<String>(named(URL)))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(get())
        .build() }
    single<ApiService>(named("apiService"))  { get<Retrofit>(named("retrofitBuilder")).create(ApiService::class.java) }
    single<DataSourceRemote>(named(NAME_REMOTE)) { DataSourceRemote(get(named("apiService"))) }
    single<DataSourceLocal>(named(NAME_LOCAL)) { DataSourceLocal(get(named("historyDAO"))) }
    single<Repository<List<DataModel>>>(named(NAME_REPO)) { RepositoryImpl(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }

    factory { MainInteractor(get(named(NAME_REPO))) }
    factory<Converter.Factory> { GsonConverterFactory.create() }
    factory { MainViewModel(get()) }
}

val localdbModule = module {
    single(named("historyDB")) { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single(named("historyDAO")) { get<HistoryDataBase>(named("historyDB")).historyDao() }
    factory { HistoryViewModel(get()) }
}