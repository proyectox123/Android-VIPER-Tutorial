package com.raywenderlich.chuckyfacts.presenter

import com.raywenderlich.chuckyfacts.MainContract
import com.raywenderlich.chuckyfacts.entity.Joke

class MainPresenter(private var view: MainContract.View?):
        MainContract.Presenter, MainContract.InteractorOutput {   // 1

    private var interactor: MainContract.Interactor? = MainInteractor()   // 2

    override fun listItemClicked(joke: Joke?) {   // 3
    }

    override fun onViewCreated() {   // 4
    }

    override fun onQuerySuccess(data: List<Joke>) {   // 5
        view?.hideLoading()
        view?.publishDataList(data)
    }

    override fun onQueryError() {
        view?.hideLoading()
        view?.showInfoMessage("Error when loading data")
    }

    override fun onDestroy() {   // 6
        view = null
        interactor = null
    }
}