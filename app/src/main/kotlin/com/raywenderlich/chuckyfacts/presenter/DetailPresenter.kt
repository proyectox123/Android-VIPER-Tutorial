package com.raywenderlich.chuckyfacts.presenter

import com.raywenderlich.chuckyfacts.DetailContract
import com.raywenderlich.chuckyfacts.entity.Joke

class DetailPresenter(private var view: DetailContract.View?) : DetailContract.Presenter {

    override fun backButtonClicked() {

    }

    override fun onViewCreated(joke: Joke) {
        view?.showJokeData(joke.id.toString(), joke.text)
    }

    override fun onDestroy() {
        view = null
    }
}