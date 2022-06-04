package ru.profitsw2000.dictionarymvp.ui.main

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.profitsw2000.dictionarymvp.data.AppState
import ru.profitsw2000.dictionarymvp.domain.Interactor
import ru.profitsw2000.dictionarymvp.ui.base.View

class MainPresenter(private val interactor: MainInteractor = MainInteractor()) : Presenter {

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

    private fun getObserver(): SingleObserver<AppState> {
        return object : SingleObserver<AppState> {
            override fun onSubscribe(d: Disposable) {
                TODO("Not yet implemented")
            }

            override fun onSuccess(appState: AppState) {
                currentView?.renderData(appState)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(AppState.Error(e))
            }
        }
    }
}