package ru.profitsw2000.dictionarymvp.ui.main

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.profitsw2000.dictionarymvp.data.AppState
import ru.profitsw2000.dictionarymvp.data.RepositoryImpl
import ru.profitsw2000.dictionarymvp.data.local.DataSourceLocal
import ru.profitsw2000.dictionarymvp.data.web.ApiService
import ru.profitsw2000.dictionarymvp.data.web.DataSourceRemote
import ru.profitsw2000.dictionarymvp.domain.BaseViewModel
import javax.inject.Inject

class MainViewModel(private val interactor: MainInteractor ) : BaseViewModel<AppState>() {
    private var appState: AppState? = null

    override fun getData(word: String, remoteSource: Boolean): LiveData<AppState> {
        liveData.postValue(AppState.Loading)
        compositeDisposable.add(
            interactor.getData(word, remoteSource)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy({
                    liveData.postValue(AppState.Error(it))
                },{
                    liveData.postValue(it)
                })
        )
        return super.getData(word, remoteSource)
    }
}