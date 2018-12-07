package com.raywenderlich.chuckyfacts.presenter

import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raywenderlich.chuckyfacts.BaseApplication
import com.raywenderlich.chuckyfacts.MainContract
import com.raywenderlich.chuckyfacts.entity.Joke
import com.raywenderlich.chuckyfacts.interactor.MainInteractor
import com.raywenderlich.chuckyfacts.view.activities.DetailActivity
import ru.terrakok.cicerone.Router

class MainPresenter(private var view: MainContract.View?):
        MainContract.Presenter, MainContract.InteractorOutput {   // 1

    private val router: Router? by lazy { BaseApplication.INSTANCE.cicerone.router }

    private var interactor: MainContract.Interactor? = MainInteractor()   // 2

    override fun listItemClicked(joke: Joke?) {   // 3
        router?.navigateTo(DetailActivity.TAG, joke)
    }

    override fun onViewCreated() {   // 4
        view?.showLoading()
        interactor?.loadJokesList { result ->
            when (result) {
                is Result.Failure -> {
                    this.onQueryError()
                }
                is Result.Success -> {
                    val jokesJsonObject = result.get().obj()

                    val type = object : TypeToken<List<Joke>>() {}.type
                    val jokesList: List<Joke> =
                            Gson().fromJson(jokesJsonObject.getJSONArray("value").toString(), type)

                    this.onQuerySuccess(jokesList)
                }
            }
        }
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