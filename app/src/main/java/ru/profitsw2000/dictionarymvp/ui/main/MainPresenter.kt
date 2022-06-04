package ru.profitsw2000.dictionarymvp.ui.main

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.profitsw2000.dictionarymvp.data.AppState
import ru.profitsw2000.dictionarymvp.data.RepositoryImpl
import ru.profitsw2000.dictionarymvp.data.local.DataSourceLocal
import ru.profitsw2000.dictionarymvp.data.web.DataSourceRemote
import ru.profitsw2000.dictionarymvp.domain.Interactor
import ru.profitsw2000.dictionarymvp.ui.base.View

class MainPresenter(private val interactor: MainInteractor = MainInteractor(RepositoryImpl(DataSourceRemote(), DataSourceLocal()))) : Presenter {

    private var currentView: View? = null
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onAttach(view: View) {
        if(view != currentView) {
            currentView = null
        }
    }

    override fun onDetach(view: View) {
        if(view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, remoteSource: Boolean) {
        currentView?.renderData(AppState.Loading)
        compositeDisposable.add(
            interactor.getData(word, remoteSource)
                .subscribeBy({
                    currentView?.renderData(AppState.Error(it))
                },{
                    currentView?.renderData(it)
                } )
        )
    }
}